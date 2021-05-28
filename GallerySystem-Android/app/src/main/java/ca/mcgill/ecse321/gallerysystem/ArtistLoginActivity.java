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

public class ArtistLoginActivity extends AppCompatActivity {

    private String error="";
    private String artistEmail="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artist_login);
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

    /**
     * go to the artist dashboard
     * @param v
     */
    private void loginToDashboard(View v){
        Intent intent = new Intent(this, ArtistDashboardActivity.class);
        intent.putExtra("email", artistEmail);
        startActivity(intent);
    }

    /**
     * log the artist in
     * @param v
     */
    public void login(View v){
        error = "";

        TextView emailInput = (TextView) findViewById(R.id.email);
        TextView passwordInput = (TextView) findViewById(R.id.password);

        String email = emailInput.getText().toString();
        artistEmail = email;
        String inputPassword = passwordInput.getText().toString();

        if(email == "" || inputPassword == ""){
            error = "Please fill all the forms to complete registration!";
        }

        if(error.length() != 0){
            refreshErrorMessage();
            return;
        }

        //gets the registered account from the database
        HttpUtils.get("/artist/" + email, new RequestParams(), new JsonHttpResponseHandler(){
            @Override
            //when successful
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try{
                    String actualPassword = response.getString("password");
                    if(actualPassword.equals(inputPassword)){
                        loginToDashboard(v);
                    }
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
}