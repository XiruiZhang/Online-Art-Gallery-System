package ca.mcgill.ecse321.gallerysystem;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class ViewArtpiecesActivity extends AppCompatActivity {

    private String error="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_artpieces);

        ArrayList<ArtPiece> artPiecesToDisplay = new ArrayList<ArtPiece>();
        ArtPieceAdapter adapter = new ArtPieceAdapter(this, artPiecesToDisplay);
        ListView listView = (ListView) findViewById(R.id.lv_ArtPieces);
        listView.setAdapter(adapter);

        //populate artpieces from database
        HttpUtils.get("/artpieces/", new RequestParams(), new JsonHttpResponseHandler(){
            @Override
            //when successful
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try{
                    JSONArray dbArtpieces = new JSONArray(response);
                    ArrayList<ArtPiece> toAddArtpieces = ArtPiece.convert(dbArtpieces);
                    adapter.addAll(toAddArtpieces);
                }catch (JSONException e){
                    error = e.getMessage();
                    refreshErrorMessage();
                }

            }

            @Override
            //when failure occurs
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

    /**
     * set the error message
     */
    private void refreshErrorMessage() {
        TextView tvError = (TextView) findViewById(R.id.error);
        tvError.setText(error);

        if (error == null || error.length() == 0) {
            tvError.setVisibility(View.GONE);
        } else {
            tvError.setVisibility(View.VISIBLE);
        }
    }


}
