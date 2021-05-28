package ca.mcgill.ecse321.gallerysystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class CustomerDashboardActivity extends AppCompatActivity {

    String customerEmail="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        customerEmail = getIntent().getStringExtra("email");
        setContentView(R.layout.activity_customer_dashboard);
    }

    /**
     * Go to the manage profile activity
     * @param v
     */
    public void customerGoShoppingCart(View v){
        Intent intent = new Intent(this, ShoppingCartActivity.class);
        intent.putExtra("email", customerEmail);
        startActivity(intent);
    }


    /**
     * logs out
     * @param v
     */
    public void customerLogout(View v){
        Intent intent = new Intent(this, CustomerLoginActivity.class);
        startActivity(intent);
    }

}