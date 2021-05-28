package ca.mcgill.ecse321.gallerysystem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ArtPiece {
    public String artName;
    public String artDescription;
    public Integer artPrice;
    public Integer artQuantity;
    public String artArtist;

    public ArtPiece(JSONObject object){
        try {
            this.artName = object.getString("artPieceName");
            this.artDescription = object.getString("artPieceDescription");
            this.artPrice = object.getInt("price");
            this.artQuantity = object.getInt("quantity");
            this.artArtist = object.getString("artPieceArtist");

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<ArtPiece> convert(JSONArray jsonObjects) {
        ArrayList<ArtPiece> artPieces = new ArrayList<ArtPiece>();
        for (int i = 0; i < jsonObjects.length(); i++) {
            try {
                artPieces.add(new ArtPiece(jsonObjects.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return artPieces;
    }
}

