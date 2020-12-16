package kupczyk.dawid.cs50.doggoexplorer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;

import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    JSONArray responseObject;
    JSONObject entry;
    ArrayList<String> dogBreeds = new ArrayList<String>();
    String dogOfTheMomentImgUrl = "";
    int dogOfTheMomentId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView testText = (TextView) findViewById(R.id.testText);
        ImageView dogOfTheMomentIV = (ImageView) findViewById(R.id.dogOfTheMomentIV);

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="https://api.thedogapi.com/v1/breeds";

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                response -> {
                    // Display the response string.
                    testText.setText("Response is: "+ response.toString());

                    //Convert the response to JSONArray
                    try {
                        testText.setText("try");
                        responseObject = new JSONArray(response);
                        entry = (JSONObject) responseObject.get((int)((Math.random() * (responseObject.length())) + 0));
                        JSONObject weight = entry.getJSONObject("weight");
                        testText.setText(entry.get("name").toString());
                        dogOfTheMomentImgUrl = entry.getJSONObject("image").getString("url");
                        dogOfTheMomentId = entry.getInt("id");
                        Picasso.get().load(dogOfTheMomentImgUrl).into(dogOfTheMomentIV);

                        for(int i = 0; i < responseObject.length()-1; i++){
                            JSONObject sample = (JSONObject) responseObject.get(i);
                            dogBreeds.add(sample.get("name").toString());
                        }

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

    public void breedListBtnClicked(View view) {
        Intent i = new Intent(this, DogListActivity.class);
        i.putStringArrayListExtra("breeds_list",dogBreeds);
        startActivity(i);
    }

    public void learnMoreBtnClicked(View view) {
        Intent i = new Intent(this, LearnMoreActivity.class);
        i.putExtra("img_url",dogOfTheMomentImgUrl);
        i.putExtra("dog_id", dogOfTheMomentId);
        startActivity(i);
    }
}