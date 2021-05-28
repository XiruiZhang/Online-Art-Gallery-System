package ca.mcgill.ecse321.gallerysystem;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import cz.msebera.android.httpclient.Header;

public class ShoppingCartActivity extends AppCompatActivity implements View.OnClickListener,CartAdapter.RefreshPriceInterface{

    String customerEmail="";

    private String error="";
    private LinearLayout top_bar;
    private ListView listview;
    private CheckBox all_checkbox;
    private TextView price;
    private TextView delete;
    private TextView tv_check_out;
    private List<HashMap<String,String>> listmap=new ArrayList<>();
    private CartAdapter adapter;

    private double totalPrice = 0.00;
    private int totalCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shoppingcart);
        //customerEmail = getIntent().getStringExtra("email");
        initView();
    }

    private void initView() {
        top_bar = (LinearLayout) findViewById(R.id.top_bar);
        listview = (ListView) findViewById(R.id.listview);
        all_checkbox = (CheckBox) findViewById(R.id.all_checkbox);
        price = (TextView) findViewById(R.id.tv_total_price);
        delete = (TextView) findViewById(R.id.tv_delete);
        tv_check_out = (TextView) findViewById(R.id.tv_check_out);

        all_checkbox.setOnClickListener(this);
        delete.setOnClickListener(this);
        tv_check_out.setOnClickListener(this);

        initData();
        adapter = new CartAdapter(ShoppingCartActivity.this, listmap);
        listview.setAdapter(adapter);
        adapter.setRefreshPriceInterface(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.all_checkbox:
                selectAll();
                break;
            case R.id.tv_delete:
                checkDelete(adapter.getitemSelectedFlagMap());
                break;
            case R.id.tv_check_out:
                if(totalCount<=0){
                    Toast.makeText(this,"Please Select Item(s) that you wish to check out",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(this,"Payment Successful",Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private void initData() {
        String email = customerEmail;
        List<HashMap<String,String>> itemList;
        itemList = new ArrayList<>();
        HashMap<String,String> map=new HashMap<>();
        //Example Data: Mona Lisa
        map.put("id", "114514");
        map.put("name", "Mona Lisa");
        map.put("price", "114514");
        map.put("count", "2");

        listmap.add((HashMap<String, String>) map.clone());
        map.clear();
        HttpUtils.get("/SelectedItem/" + email, new RequestParams(), new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try{
                    JSONArray responseArray = response.getJSONArray("data");
                    for (int i = 0; i < responseArray.length(); i++){
                        map.put("id", (String) responseArray.getJSONObject(i).get("itemID"));
                        map.put("name", (String) responseArray.getJSONObject(i).get("name"));
                        map.put("price", (String) responseArray.getJSONObject(i).get("price"));
                        map.put("count", (String) responseArray.getJSONObject(i).get("quantity"));
                        listmap.add((HashMap<String, String>) map.clone());
                        map.clear();
                    }

                }catch (JSONException e){
                    error = e.getMessage();
                    refreshErrorMessage();
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                try{
                    error = errorResponse.get("message").toString();
                }catch (JSONException e){
                    error = e.getMessage();
                }
                refreshErrorMessage();
            }
        });
    }

    private void refreshErrorMessage() {
        // set the error message
        TextView tvError = (TextView) findViewById(R.id.error);
        tvError.setText(error);

        if (error == null || error.length() == 0) {
            tvError.setVisibility(View.GONE);
        } else {
            tvError.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void refreshPrice(HashMap<String, Integer> itemSelectedFlagMap) {
        priceControl(itemSelectedFlagMap);
    }


    private void priceControl(Map<String, Integer> itemSelectedFlagMap){
        totalCount = 0;
        totalPrice = 0.00;
        for(int i=0;i<listmap.size();i++){
            if(itemSelectedFlagMap.get(listmap.get(i).get("id"))==1){
                totalCount=totalCount+Integer.valueOf(listmap.get(i).get("count"));
                double itemPrice=Integer.valueOf(listmap.get(i).get("count"))*Double.valueOf(listmap.get(i).get("price"));
                totalPrice=totalPrice+itemPrice;
            }
        }
        price.setText(" $ "+totalPrice);
        tv_check_out.setText("Place Order("+totalCount+")");
    }


    private void checkDelete(Map<String,Integer> map){
        List<HashMap<String,String>> waitDeleteList=new ArrayList<>();
        Map<String,Integer> waitDeleteMap =new HashMap<>();
        for(int i=0;i<listmap.size();i++){
            if(map.get(listmap.get(i).get("id"))==1){
                waitDeleteList.add(listmap.get(i));
                waitDeleteMap.put(listmap.get(i).get("id"),map.get(listmap.get(i).get("id")));
            }
        }
        listmap.removeAll(waitDeleteList);
        map.remove(waitDeleteMap);
        priceControl(map);
        adapter.notifyDataSetChanged();
    }
    private void selectAll(){
        HashMap<String,Integer> map=adapter.getitemSelectedFlagMap();
        boolean isChecked=false;
        boolean isUnchecked=false;
        Iterator iter = map.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();

            if(Integer.valueOf(entry.getValue().toString())==1){
                isChecked=true;
            }else{
                isUnchecked=true;
            }
        }
        if(isChecked==true&&isUnchecked==false){// All items selected, perform deselect all
            for(int i=0;i<listmap.size();i++){
                map.put(listmap.get(i).get("id"),0);
            }
            all_checkbox.setChecked(false);
        }else if(isChecked==true && isUnchecked==true){// Some items are selected, perform select all
            for(int i=0;i<listmap.size();i++){
                map.put(listmap.get(i).get("id"),1);
            }
            all_checkbox.setChecked(true);
        }else if(isChecked==false && isUnchecked==true){// Nothing selected, perform select all
            for(int i=0;i<listmap.size();i++){
                map.put(listmap.get(i).get("id"),1);
            }
            all_checkbox.setChecked(true);
        }
        priceControl(map);
        adapter.setitemSelectedFlagMap(map);
        adapter.notifyDataSetChanged();
    }

}
