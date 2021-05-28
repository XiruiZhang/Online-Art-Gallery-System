package ca.mcgill.ecse321.gallerysystem;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.List;

public class CartAdapter extends BaseAdapter {

    private Context context;
    private List<HashMap<String, String>> list;
    private HashMap<String, Integer> itemSelectedFlagMap;

    public HashMap<String, Integer> getitemSelectedFlagMap() {
        return itemSelectedFlagMap;
    }

    public void setitemSelectedFlagMap(HashMap<String, Integer> itemSelectedFlagMap) {
        this.itemSelectedFlagMap = itemSelectedFlagMap;
    }

    public CartAdapter(Context context, List<HashMap<String, String>> list) {
        this.context = context;
        this.list = list;

        itemSelectedFlagMap = new HashMap<>();
        for (int i = 0; i < list.size(); i++) {
            itemSelectedFlagMap.put(list.get(i).get("id"), 0);
        }
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int index) {
        return list.get(index);
    }

    @Override
    public long getItemId(int index) {
        return index;
    }

    @Override

    /**
     * gets view
     */
    public View getView(final int index, View convertView, ViewGroup parent) {
        convertView = View.inflate(context, R.layout.cart_item, null);
        final CheckBox checkBox;
        ImageView icon;
        final TextView name, price, num, reduce, add;

        checkBox = convertView.findViewById(R.id.check_box);
        icon = convertView.findViewById(R.id.iv_adapter_list_pic);
        name = convertView.findViewById(R.id.tv_item_name);
        price = convertView.findViewById(R.id.tv_item_price);
        num = convertView.findViewById(R.id.tv_num);
        reduce = convertView.findViewById(R.id.tv_reduce);
        add = convertView.findViewById(R.id.tv_add);

        name.setText(list.get(index).get("name"));
        price.setText("$ " + (Integer.valueOf(list.get(index).get("price"))) * (Integer.valueOf(list.get(index).get("count"))));
        num.setText(list.get(index).get("count"));

        if(itemSelectedFlagMap.get(list.get(index).get("id"))== 0){
            checkBox.setChecked(false);
        }else{
            checkBox.setChecked(true);
        }

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(checkBox.isChecked()){
                    itemSelectedFlagMap.put(list.get(index).get("id"),1);
                }else{
                    itemSelectedFlagMap.put(list.get(index).get("id"), 0);
                }
                mrefreshPriceInterface.refreshPrice(itemSelectedFlagMap);
            }
        });

        //Reduce item count
        reduce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Integer.valueOf(list.get(index).get("count")) <= 1) {
                    Toast.makeText(context, "Can't reduce item count anymore, you should consider delete it", Toast.LENGTH_SHORT).show();
                } else {
                    list.get(index).put("count", (Integer.valueOf(list.get(index).get("count")) - 1) + "");
                    notifyDataSetChanged();
                }
                mrefreshPriceInterface.refreshPrice(itemSelectedFlagMap);
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.get(index).put("count", (Integer.valueOf(list.get(index).get("count")) + 1) + "");
                notifyDataSetChanged();
                mrefreshPriceInterface.refreshPrice(itemSelectedFlagMap);

            }

        });

        return convertView;
    }


    public interface RefreshPriceInterface {

        void refreshPrice(HashMap<String, Integer> itemSelectedFlagMap);
    }


    private RefreshPriceInterface mrefreshPriceInterface;


    public void setRefreshPriceInterface(RefreshPriceInterface refreshPriceInterface) {
        mrefreshPriceInterface = refreshPriceInterface;
    }


}