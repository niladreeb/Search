package demo.app.search;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private ProgressDialog pDialog;
    private static EditText location;
    private static String locationName;
    private static String code;

    private static String BASE_URL = "https://api.foursquare.com/v2/venues/explore?";
    private String CLIENT_ID = "3KKAOPJTTUOWYWTCPCWO10KSHWUD5KMGL1DZPVTVFNVJM2LU";
    private String CLIENT_SECRET = "CCQ1PPMVIYZGLFXV4UPO1ATBAA2UQQBMODLLI03AV0XELDXV";

    private static final String TAG_META = "meta";
    private static final String TAG_CODE = "code";
    private static final String INVALID_CODE = "400";

    DateFormat df = new SimpleDateFormat("yyyyMMdd");
    Calendar calobj = Calendar.getInstance();
    String date = df.format(calobj.getTime());

    public static String url;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        location = (EditText)findViewById(R.id.location);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                locationName = location.getText().toString();

                if (locationName.equalsIgnoreCase("")) {

                    Snackbar.make(view, "Please enter location", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();

                    InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    if(inputManager.isActive() ){
                        inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    }

                } else {

                    url = BASE_URL +
                            "near=" + locationName +
                            "&client_id=" + CLIENT_ID +
                            "&client_secret=" + CLIENT_SECRET +
                            "&v=" + date;
                    new GetVenues().execute();

                }
            }
        });


    }


    /**
     * Async task class to get json by making HTTP call
     * */
    private class GetVenues extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            // Creating service handler class instance
            ServiceHandler sh = new ServiceHandler();

            // Making a request to url and getting response
            String jsonString = sh.makeServiceCall(url, ServiceHandler.GET);

            if (jsonString != null)
                try {

                    JSONObject jsonObj = new JSONObject(jsonString);
                    JSONObject meta = jsonObj.getJSONObject(TAG_META);
                    code = meta.getString(TAG_CODE);

                }  catch (JSONException ex) {
                    ex.printStackTrace();
                }

            if (code.equalsIgnoreCase(INVALID_CODE)) {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        Snackbar.make(getCurrentFocus(), "Please enter valid location", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();

                        InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        if(inputManager.isActive() ){
                            inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                        }
                    }
                });


            } else {
                Intent intent = new Intent(getApplicationContext(), VenueListActivity.class);
                intent.putExtra("JSON", jsonString);
                startActivity(intent);
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
