package kupczyk.dawid.cs50.doggoexplorer;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class LearnMoreActivity extends AppCompatActivity {
    Dog dog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learnmore);

        Intent i = getIntent();
        dog = (Dog) i.getSerializableExtra("dogObject");
        String img_url = dog.getImageUrl();
        if(img_url.isEmpty())img_url = "https://www.publicdomainpictures.net/pictures/280000/velka/not-found-image-15383864787lu.jpg";
        String dogName = dog.getName();
        final String[] info = {""};

        ImageView dogImg = (ImageView) findViewById(R.id.ivLearnMore);
        TextView wiki = (TextView) findViewById(R.id.wiki);
        wiki.setMovementMethod(new ScrollingMovementMethod());
        TextView breedName = (TextView) findViewById(R.id.tvLearnMoreBreedName);
        breedName.setText(dogName);
        Picasso.get().load(img_url).into(dogImg);


        new Thread(() -> {
            String wikiTitle="";
            StringBuilder sb = new StringBuilder();
            try {
                Document doc = Jsoup.connect("https://www.google.com/search?q="+ dogName +"+dog+wikipedia&oq="+ dogName +"+dog+wikipedia&sourceid=chrome&lr=lang_en&ie=UTF-8").get();
                Elements links = doc.select("div.yuRUbf a");
                Element link = links.get(0);
                wikiTitle = link.attr("href");
                wikiTitle = wikiTitle.substring(wikiTitle.lastIndexOf("/") + 1);

            } catch (IOException e) {
                sb.append("Error: ").append(e.getMessage());
            }


            // Instantiate the RequestQueue.
            RequestQueue queue = Volley.newRequestQueue(getBaseContext());
            String url ="https://en.wikipedia.org/w/api.php?format=json&action=query&prop=extracts&exintro=explaintext&titles="+wikiTitle;


            // Request a string response from the provided URL.
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                    response -> {

                        //Convert the response to JSONArray
                        try {
                            JSONObject entry = new JSONObject(response);
                            JSONObject entryDeeper = entry.getJSONObject("query").getJSONObject("pages");
                            JSONObject entryDeepest = entryDeeper.getJSONObject((String)entryDeeper.keys().next());
                            addExtraData(info);
                            String wikiDesc = (entryDeepest.getString("extract").replaceAll("<.*?>", "").trim());
                            info[0] +="\n" + wikiDesc ;
                            wiki.setText(info[0]);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }, error -> Log.println(Log.ERROR, "API call","API call failed")) {

            };

            // Add the request to the RequestQueue.
            queue.add(stringRequest);

            runOnUiThread(() -> wiki.setText(info[0].trim()));

        }).start();


    }

    private void addExtraData(String[] info) {
        if(!dog.getHeight().isEmpty())
            info[0]+= "Height:\t" + dog.getHeight() +" [cm]\n";
        if(!dog.getWeight().isEmpty())
            info[0]+= "Weight:\t" + dog.getWeight() +" [kg]\n";
        if(!dog.getBred_for().isEmpty())
            info[0]+= "Bred for:\t" + dog.getBred_for() +"\n";
        if(!dog.getBreed_group().isEmpty())
            info[0]+= "Breed group:\t" + dog.getBreed_group() +"\n";
        if(!dog.getLife_span().isEmpty())
            info[0]+= "Life span:\t" + dog.getLife_span() +"\n";
        if(!dog.getOrigin().isEmpty())
            info[0]+= "Origin:\t" + dog.getOrigin() +"\n";
        if(!dog.getTemperament().isEmpty())
            info[0]+= "Temperament:\t" + dog.getTemperament() +"\n";

    }
}
