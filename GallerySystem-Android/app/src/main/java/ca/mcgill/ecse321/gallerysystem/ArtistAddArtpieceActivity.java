package ca.mcgill.ecse321.gallerysystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class ArtistAddArtpieceActivity extends AppCompatActivity {

    private String error="";
    private String artistEmail = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        artistEmail = getIntent().getStringExtra("email");
        setContentView(R.layout.activity_artist_add_artpiece);
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

    private void addArtToDashboard(View v){
        Intent intent = new Intent(this, ArtistDashboardActivity.class);
        startActivity(intent);
    }

    public void artpieceSubmit(View v) {
        error = "";

        final TextView artNameInput = (TextView) findViewById(R.id.artName);
        final TextView quantityInput = (TextView) findViewById(R.id.quantity);
        final TextView priceInput = (TextView) findViewById(R.id.price);
        final TextView discountPercentageInput = (TextView) findViewById(R.id.discountPercentage);
        final TextView commissionPercentageInput = (TextView) findViewById(R.id.commissionPercentage);
        final TextView descriptionInput = (TextView) findViewById(R.id.description);

        String artName = artNameInput.getText().toString();
        String quantity = quantityInput.getText().toString();
        String price = priceInput.getText().toString();
        String discountPercentage = discountPercentageInput.getText().toString();
        String commissionPercentage = commissionPercentageInput.getText().toString();
        String description = descriptionInput.getText().toString();


        HttpUtils.post("artpiece/" + artName.replaceAll("\\s","_") + "?quantity=" + quantity + "&price=" + price
                + "&discountPercentage=" + discountPercentage + "&commissionPercentage=" + commissionPercentage + "&description=" + description.replaceAll("\\s", "_")
                + "&artistEmail=" + artistEmail, new RequestParams(), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                addArtToDashboard(v);
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                try {
                    error += errorResponse.get("message").toString();
                } catch (JSONException e) {
                    error += e.getMessage();
                }
                refreshErrorMessage();
            }
        });
    }
}