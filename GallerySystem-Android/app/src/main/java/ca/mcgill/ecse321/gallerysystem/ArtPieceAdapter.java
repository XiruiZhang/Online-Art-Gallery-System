package ca.mcgill.ecse321.gallerysystem;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ArtPieceAdapter extends ArrayAdapter<ArtPiece> {
    public ArtPieceAdapter(Context context, ArrayList<ArtPiece> users) {
        super(context, 0, users);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        ArtPiece artPiece = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.art_piece_layout, parent, false);
        }
        // Lookup view for data population
        TextView tvArtName = (TextView) convertView.findViewById(R.id.artName);
        TextView tvArtDescription = (TextView) convertView.findViewById(R.id.artDescription);
        TextView tvArtPrice = (TextView) convertView.findViewById(R.id.artPrice);
        TextView tvArtQuantity = (TextView) convertView.findViewById(R.id.artQuantity);
        TextView tvArtArtist = (TextView) convertView.findViewById(R.id.artArtist);

        // Populate the data into the template view using the data object
        tvArtName.setText(artPiece.artName);
        tvArtDescription.setText(artPiece.artDescription);
        tvArtPrice.setText(artPiece.artPrice);
        tvArtQuantity.setText(artPiece.artQuantity);
        tvArtArtist.setText(artPiece.artArtist);
        // Return the completed view to render on screen
        return convertView;
    }
}
