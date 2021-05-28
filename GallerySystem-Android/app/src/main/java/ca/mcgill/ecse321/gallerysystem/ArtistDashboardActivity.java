package ca.mcgill.ecse321.gallerysystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class ArtistDashboardActivity extends AppCompatActivity {

    String artistEmail = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        artistEmail = getIntent().getStringExtra("email");
        setContentView(R.layout.activity_artist_dashboard);
    }

    /**
     * Go to the manage profile activity
     * @param v
     */
    public void artistGoManageProfile(View v){
        Intent intent = new Intent(this, ArtistManageProfileActivity.class);
        intent.putExtra("email", artistEmail);
        Log.d("testing", artistEmail);
        startActivity(intent);
    }

    /**
     * Go to the manage artpiece activity
     * @param v
     */
    public void artistGoManageArtpiece(View v){
        Intent intent = new Intent(this, ArtistManageArtpieceActivity.class);
        intent.putExtra("email", artistEmail);
        Log.d("testing", artistEmail);
        startActivity(intent);
    }

    /**
     * Go to the add artpiece activity
     * @param v
     */
    public void artistGoAddArtpiece(View v){
        Intent intent = new Intent(this, ArtistAddArtpieceActivity.class);
        intent.putExtra("email", artistEmail);
        Log.d("testing", artistEmail);
        startActivity(intent);
    }

    /**
     * logs out
     * @param v
     */
    public void artistLogout(View v){
        Intent intent = new Intent(this, ArtistLoginActivity.class);
        startActivity(intent);
    }
}