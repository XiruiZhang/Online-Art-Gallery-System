package ca.mcgill.ecse321.gallerysystem;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class CustomerLoginActivity extends AppCompatActivity {

    private String error="";
    private String customerEmail="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_login);
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

    private void loginToDashboard(View v){
        Intent intent = new Intent(this, CustomerDashboardActivity.class);
        intent.putExtra("email", customerEmail);
        startActivity(intent);
    }

    public void login(View v){
        error = "";

        TextView emailInput = (TextView) findViewById(R.id.email);
        TextView passwordInput = (TextView) findViewById(R.id.password);

        String email = emailInput.getText().toString();
        customerEmail = email;
        String inputPassword = passwordInput.getText().toString();

        if(email == "" || inputPassword == ""){
            error = "Please fill all the forms to complete registration!";
        }

        if(error.length() != 0){
            refreshErrorMessage();
            return;
        }

        HttpUtils.get("/customer/" + email, new RequestParams(), new JsonHttpResponseHandler(){
            @Override
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