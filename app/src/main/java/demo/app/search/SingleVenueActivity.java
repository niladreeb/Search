package demo.app.search;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class SingleVenueActivity extends AppCompatActivity {

    private static final String TAG_NAME = "name";
    private static final String TAG_ADDRESS = "formattedAddress";
    private static final String TAG_IMAGE = "photourl";
    private static final String TAG_RATING = "rating";
    private static final String TAG_URL = "url";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_venue);

        TextView venueName = (TextView)findViewById(R.id.venue_name);
        TextView venueAddress = (TextView)findViewById(R.id.venue_address);
        TextView venueRating = (TextView)findViewById(R.id.venue_rating);
        ImageButton imageButton = (ImageButton)findViewById(R.id.imageButton);
        ImageView imageView = (ImageView)findViewById(R.id.venue_imageView);


        venueName.setText(getIntent().getStringExtra(TAG_NAME));
        venueAddress.setText(getIntent().getStringExtra(TAG_ADDRESS));
        venueRating.setText("Rating: "+getIntent().getStringExtra(TAG_RATING));
        String imageUrl = getIntent().getStringExtra(TAG_IMAGE);

        Picasso.with(this).load(imageUrl).into(imageView);

        final String url = getIntent().getStringExtra(TAG_URL);
        if(url.equalsIgnoreCase("hide"))
            imageButton.setVisibility(View.GONE);
        else {

            imageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Uri uri = Uri.parse(url);
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);
                }
            });
        }



    }
}
