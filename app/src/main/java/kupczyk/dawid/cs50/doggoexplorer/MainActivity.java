package kupczyk.dawid.cs50.doggoexplorer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    JSONArray responseObject;
    ArrayList<String> dogBreeds = new ArrayList<String>();
    String imgUrl = "";
    int id;
    String name="";
    List<Dog> dogList = new ArrayList<Dog>();
    Dog dogOfTheMoment;


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
                        for(int i = 0; i < responseObject.length()-1;i++){
                            JSONObject entry = responseObject.getJSONObject(i);
                            name = entry.get("name").toString();
                            imgUrl = entry.getJSONObject("image").getString("url");
                            id = entry.getInt("id");
                            Dog dog = new Dog(id,name,imgUrl);
                            dogList.add(dog);
                        }

                        dogOfTheMoment = dogList.get((int)(dogList.size() * Math.random())-1);
                        testText.setText(dogOfTheMoment.getName());
                        Picasso.get().load(dogOfTheMoment.getImageUrl()).into(dogOfTheMomentIV);

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
        i.putExtra("list", (Serializable) dogList);
        startActivity(i);
    }

    public void learnMoreBtnClicked(View view) {
        Intent i = new Intent(this, LearnMoreActivity.class);
        i.putExtra("dogObject",dogOfTheMoment);
        startActivity(i);
    }
}