package de.pifrasso.androidrestapp;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class WorkorderOverviewActivity extends Activity {

    static final String API_URL = "http://10.0.2.2:8080/";


    // All static variables
    static final String URL = "https://api.androidhive.info/music/music.xml";
    // XML node keys
    static final String KEY_SONG = "song"; // parent node
    static final String KEY_ID = "id";
    static final String KEY_TITLE = "title";
    static final String KEY_ARTIST = "artist";
    static final String KEY_DURATION = "duration";
    static final String KEY_THUMB_URL = "thumb_url";

    ListView list;
    LazyAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.workorder_overview_activity);
        Unbinder bind = ButterKnife.bind(this);


        ArrayList<HashMap<String, String>> songsList = new ArrayList<HashMap<String, String>>();


        //XMLParser parser = new XMLParser();
        //String xml = parser.getXmlFromUrl(URL); // getting XML from URL
        //Document doc = parser.getDomElement(xml); // getting DOM element

        //NodeList nl = doc.getElementsByTagName(KEY_SONG);
        // looping through all song nodes &lt;song&gt;
        for (int i = 0; i < 5; i++) {
            // creating new HashMap
            HashMap<String, String> map = new HashMap<String, String>();
            //Element e = (Element) nl.item(i);
            // adding each child node to HashMap key =&gt; value
            map.put(KEY_ID, ""+i);
            map.put(KEY_TITLE, i + "title");
            map.put(KEY_ARTIST, "artist" + i);
            //map.put(KEY_DURATION, parser.getValue(e, KEY_DURATION));
            //map.put(KEY_THUMB_URL, parser.getValue(e, KEY_THUMB_URL));

            // adding HashList to ArrayList
            songsList.add(map);
        }

        list = (ListView) findViewById(R.id.list);

        // Getting adapter by passing xml data ArrayList
        adapter = new LazyAdapter(this, songsList);
        list.setAdapter(adapter);

        // Click event for single list row
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

            }
        });


        //new WorkorderOverviewActivity().RetrieveFeedTask().execute();
    }




    class RetrieveFeedTask extends AsyncTask<Void, Void, String> {

        private Exception exception;

        protected void onPreExecute() {
          //  progressBar.setVisibility(View.VISIBLE);
            //responseView.setText("");
        }

        protected String doInBackground(Void... urls) {
            //String email = emailText.getText().toString();
            // Do some validation here

            try {
                java.net.URL url = new URL(API_URL + "workorders/");
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                try {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    StringBuilder stringBuilder = new StringBuilder();
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        stringBuilder.append(line).append("\n");
                    }
                    bufferedReader.close();
                    return stringBuilder.toString();
                }
                finally{
                    urlConnection.disconnect();
                }
            }
            catch(Exception e) {
                Log.e("ERROR", e.getMessage(), e);
                return null;
            }
        }

        protected void onPostExecute(String response) {
            if(response == null) {
                response = "THERE WAS AN ERROR";
            }
           // progressBar.setVisibility(View.GONE);
            Log.i("INFO", response);
           // responseView.setText(response);
            // TODO: check this.exception
            // TODO: do something with the feed

//            try {
//                JSONObject object = (JSONObject) new JSONTokener(response).nextValue();
//                String requestID = object.getString("requestId");
//                int likelihood = object.getInt("likelihood");
//                JSONArray photos = object.getJSONArray("photos");
//                .
//                .
//                .
//                .
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
        }
    }

}
