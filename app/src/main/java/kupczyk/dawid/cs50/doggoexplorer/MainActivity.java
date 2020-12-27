package kupczyk.dawid.cs50.doggoexplorer;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    //An ArrayList of Dog objects thats going to be populated later on
    private final List<Dog> dogList = new ArrayList<>();
    //A JSONArray that is going to store the JSON's acquired as the response on API call
    private JSONArray responseObject;
    //The dog that's data is going to be displayed when we launch the app
    private Dog dogOfTheMoment;

    private String imgUrl = "";
    private String name="";
    private int id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView dogOfTheMomentNameTv   = (TextView)  findViewById(R.id.tv_main_activity_dog_name);
        ImageView dogOfTheMomentPicIv   = (ImageView) findViewById(R.id.iv_main_activity_dog_image);

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        //Creating an url which I'm going use to call the Aden Forshaw's dog API
        String url ="https://api.thedogapi.com/v1/breeds";

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                response -> {
                    //Convert the response to JSONArray
                    try {
                        responseObject = new JSONArray(response);
                        for(int i = 0; i < responseObject.length()-1;i++){
                            //Each object in the API has this properties, no need to check if they exist
                            JSONObject entry = responseObject.getJSONObject(i);
                            name    = entry.get("name").toString();
                            imgUrl  = entry.getJSONObject("image").getString("url");
                            id      = entry.getInt("id");

                            //Creating a new Dog ready to be added to the list
                            Dog dog = new Dog(id,name,imgUrl);

                            //Since some dogs may have some additional information provided as well as
                            //some may not, I need to check if the current JSONObject has some of those,
                            //If so, I'm adding them to the Dog object's fields
                            if(entry.has("bred_for"))
                                dog.setBred_for(entry.get("bred_for").toString());
                            if(entry.has("breed_group"))
                                dog.setBreed_group(entry.get("breed_group").toString());
                            if(entry.has("life_span"))
                                dog.setLife_span(entry.get("life_span").toString());
                            if(entry.has("temperament"))
                                dog.setTemperament(entry.get("temperament").toString());
                            if(entry.has("origin"))
                                dog.setOrigin(entry.get("origin").toString());
                            if(entry.has("weight")) {
                                JSONObject weight = entry.getJSONObject("weight");
                                dog.setWeight(weight.get("metric").toString());
                            }
                            if(entry.has("height")) {
                                JSONObject height = entry.getJSONObject("height");
                                dog.setHeight(height.get("metric").toString());
                            }
                            dogList.add(dog);//Adding the newly created Dog to the dog list.
                        }

                        dogOfTheMoment = dogList.get((int)(dogList.size() * Math.random())-1);
                        dogOfTheMomentNameTv.setText(dogOfTheMoment.getName());
                        Picasso.get().load(dogOfTheMoment.getImageUrl()).into(dogOfTheMomentPicIv);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, error -> Log.println(Log.ERROR, "API","API call failed")) {

            @Override
            //Since the API demands users to authenticate themselves, an API key is being added to the header
            public Map<String, String> getHeaders() {
                HashMap<String, String> header = new HashMap<>();
                header.put("x-api-key","9f8d5809-9aec-4cf5-925e-cd86acff7b6f");
                return header;
            }
        };

        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    //When the breedList button is being pressed, a new DogListActivity based on RecyclerView is being started
    public void breedListBtnClicked(View view) {
        Intent i = new Intent(this, DogListActivity.class);
        i.putExtra("list", (Serializable) dogList);
        startActivity(i);
    }

    //When the breedList button is being pressed, a new LearnMoreActivity is being started having
    //been passed the Dog of the moment object
    public void learnMoreBtnClicked(View view) {
        Intent i = new Intent(this, LearnMoreActivity.class);
        i.putExtra("dogObject",dogOfTheMoment);
        startActivity(i);
    }
}