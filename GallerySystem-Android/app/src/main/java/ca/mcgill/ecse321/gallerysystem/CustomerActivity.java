package ca.mcgill.ecse321.gallerysystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class CustomerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);
    }

    /**
     * go to the register page
     * @param v
     */
    public void customerGoRegister(View v){
        Intent intent = new Intent(this, CustomerRegisterActivity.class);
        startActivity(intent);
    }

    /**
     * go to the login page
     * @param v
     */
    public void customerGoLogin(View v){
        Intent intent = new Intent(this, CustomerLoginActivity.class);
        startActivity(intent);
    }
}