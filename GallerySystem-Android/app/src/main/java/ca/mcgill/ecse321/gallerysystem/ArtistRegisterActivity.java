package ca.mcgill.ecse321.gallerysystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;


public class ArtistRegisterActivity extends AppCompatActivity {

    private String error="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artist_register);
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

    private void registerToLogin(View v){
        Intent intent = new Intent(this, ArtistLoginActivity.class);
        startActivity(intent);
    }

    /**
     * register the artist, with Httputils.post
     * @param v
     */
    public void register(View v){
        error = "";

        TextView emailInput = (TextView) findViewById(R.id.email);
        TextView usernameInput = (TextView) findViewById(R.id.password);
        TextView inputPasswordInput = (TextView) findViewById(R.id.inputPassword);
        TextView confirmPasswordInput = (TextView) findViewById(R.id.confirmPassword);

        String email = emailInput.getText().toString();
        String username = usernameInput.getText().toString();
        String inputPassword = inputPasswordInput.getText().toString();
        String confirmPassword = confirmPasswordInput.getText().toString();

        if(email == "" || username == "" || inputPassword == "" || confirmPassword ==""){
            error = "Please fill all the forms to complete registration!";
        }
        if(inputPassword.equals(confirmPassword) == false){
            error = "The passwords do not match with each other!";
        }

        if(error.length() != 0){
            refreshErrorMessage();
            return;
        }

        HttpUtils.post("/artist/" + username.replaceAll("\\s", "")+"?email="+email+"&password="+ inputPassword, new RequestParams(), new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                registerToLogin(v);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                try{
                    if(errorResponse.get("message") == ""){
                        error = "No details provided.";
                    }else {
                        error = errorResponse.get("message").toString();
                    }
                }catch (JSONException e){
                    error = e.getMessage();
                }
                refreshErrorMessage();
            }
        });

    }
}