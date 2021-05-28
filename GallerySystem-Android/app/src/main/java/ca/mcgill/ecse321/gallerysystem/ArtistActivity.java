package ca.mcgill.ecse321.gallerysystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ArtistActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artist);
    }

    public void artistGoRegister(View v){
        Intent intent = new Intent(this, ArtistRegisterActivity.class);
        startActivity(intent);
    }

    public void artistGoLogin(View v){
        Intent intent = new Intent(this, ArtistLoginActivity.class);
        startActivity(intent);
    }
}