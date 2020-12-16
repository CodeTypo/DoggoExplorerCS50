package kupczyk.dawid.cs50.doggoexplorer;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

public class LearnMoreActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acrivity_learnmore);



        Intent i = getIntent();
        String img_url = i.getStringExtra("img_url");
        int id = i.getIntExtra("dog_id",0);

        ImageView dogImg = (ImageView) findViewById(R.id.ivLearnMore);
        TextView breedName = (TextView) findViewById(R.id.tvLearnMoreBreedName);
        TextView bredFor = (TextView) findViewById(R.id.tvLearnMoreBredFor);
        TextView breedGroup = (TextView) findViewById(R.id.tvLearnMoreBreedGroup);
        TextView lifeSpan = (TextView) findViewById(R.id.tvLearnMoreLifeSpan);
        TextView temperament = (TextView) findViewById(R.id.tvLearnMoreTemperament);
        TextView origin = (TextView) findViewById(R.id.tvLearnMoreOrigin);
        Picasso.get().load(img_url).into(dogImg);


        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="https://api.thedogapi.com/v1/breeds/" + id;

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                response -> {

                    //Convert the response to JSONArray
                    try {
                        JSONObject entry = new JSONObject(response);

                        breedName.setText(entry.get("name").toString());
                        lifeSpan.append(" " + entry.get("life_span").toString());
                        temperament.append(" " + entry.get("temperament").toString());
                        origin.append(" " + entry.get("origin").toString());
                        bredFor.append(" " + entry.get("bred_for").toString());
                        breedGroup.append(" " + entry.get("breed_group").toString());

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }, error -> Log.println(Log.ERROR, "API call","API call failed")) {

            @Override
            public Map<String, String> getHeaders() {
                HashMap<String, String> header = new HashMap<>();
                header.put("x-api-key","9f8d5809-9aec-4cf5-925e-cd86acff7b6f");
                return header;
            }
        };

        // Add the request to the RequestQueue.
        queue.add(stringRequest);



    }
}
