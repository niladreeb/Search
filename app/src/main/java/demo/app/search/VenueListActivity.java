package demo.app.search;


import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;

public class VenueListActivity extends ListActivity {



    // Hashmap for ListView
    ArrayList<HashMap<String, String>> venueList;


    // JSON Node tag
    private static final String TAG_RESPONSE = "response";
    private static final String TAG_GROUPS = "groups";
    private static final String TAG_ITEMS = "items";
    private static final String TAG_VENUE = "venue";
    private static final String TAG_LOCATION = "location";
    private static final String TAG_TIPS = "tips";

    private static final String TAG_NAME = "name";
    private static final String TAG_ADDRESS = "formattedAddress";
    private static final String TAG_IMAGE = "photourl";
    private static final String TAG_RATING = "rating";
    private static final String TAG_URL = "url";


    private static String jsonStr;

    private static String addressStr;
    private static String photoUrlStr;
    private static String ratingStr;
    private static String urlStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_venue_list);

        venueList = new ArrayList<HashMap<String, String>>();

        ListView lv = getListView();

        // Listview on item click listener
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // getting values from selected ListItem
                String name = ((TextView) view.findViewById(R.id.name))
                        .getText().toString();
                String address = ((TextView) view.findViewById(R.id.address))
                        .getText().toString();
                String photoUrl = ((TextView) view.findViewById(R.id.imageView))
                        .getText().toString();
                String rating = ((TextView) view.findViewById(R.id.rating))
                        .getText().toString();
                String url = ((TextView) view.findViewById(R.id.url))
                        .getText().toString();

                // Starting single contact activity
                Intent in = new Intent(getApplicationContext(),
                        SingleVenueActivity.class);
                in.putExtra(TAG_NAME, name);
                in.putExtra(TAG_ADDRESS, address);
                in.putExtra(TAG_IMAGE, photoUrl);
                in.putExtra(TAG_RATING, rating);
                in.putExtra(TAG_URL, url);
                startActivity(in);

            }
        });

        jsonStr = getIntent().getStringExtra("JSON");

        if (jsonStr != null)
            try {

                JSONObject jsonObj = new JSONObject(jsonStr);


                JSONObject response = jsonObj.getJSONObject(TAG_RESPONSE);
                JSONArray groups = response.getJSONArray(TAG_GROUPS);
                for (int i = 0; i < groups.length(); i++) {
                    JSONObject grp = groups.getJSONObject(i);
                    JSONArray items = grp.getJSONArray(TAG_ITEMS);
                    for (int j = 0; j < items.length(); j++) {

                        JSONObject itm = items.getJSONObject(j);
                        JSONObject venue = itm.getJSONObject(TAG_VENUE);
                        String name = venue.getString(TAG_NAME);

                        JSONObject location = venue.getJSONObject(TAG_LOCATION);
                        JSONArray address = location.getJSONArray(TAG_ADDRESS);
                        addressStr = "";
                        for (int k = 0; k < address.length(); k++) {
                            addressStr = addressStr+address.getString(k)+"\n";
                        }

                        JSONArray tips = itm.getJSONArray(TAG_TIPS);
                        for(int k = 0; k < tips.length(); k++){
                            if(tips.getString(k).contains(TAG_IMAGE)){
                                JSONObject tipObj = (JSONObject) tips.get(k);
                                photoUrlStr = tipObj.getString(TAG_IMAGE);
                                break;
                            }
                            else {
                                photoUrlStr = "http://www.aspneter.com/aspneter/wp-content/uploads/2016/01/no-thumb.jpg";
                            }
                        }

                            if(venue.has(TAG_RATING)) {
                                ratingStr = venue.getString(TAG_RATING);
                            }
                            else {
                                ratingStr = "Not Rated";
                            }

                            if(venue.has(TAG_URL)) {
                                urlStr = venue.getString(TAG_URL);
                            }
                            else {
                                urlStr = "hide";
                            }


                        HashMap<String, String> venues = new HashMap<String, String>();

                        venues.put(TAG_NAME, name);
                        venues.put(TAG_ADDRESS, addressStr);
                        venues.put(TAG_IMAGE, photoUrlStr);
                        venues.put(TAG_RATING, ratingStr);
                        venues.put(TAG_URL, urlStr);


                        venueList.add(venues);

                    }
                }




                ListAdapter adapter = new SimpleAdapter(
                        VenueListActivity.this, venueList,
                        R.layout.list_item, new String[] { TAG_NAME, TAG_ADDRESS, TAG_IMAGE, TAG_RATING, TAG_URL }, new int[] { R.id.name , R.id.address, R.id.imageView, R.id.rating, R.id.url});

                setListAdapter(adapter);

            }  catch (JSONException ex) {
        ex.printStackTrace();
    }
        else {
            Log.e("ServiceHandler", "Couldn't get any data from the url");
        }
    }
}
