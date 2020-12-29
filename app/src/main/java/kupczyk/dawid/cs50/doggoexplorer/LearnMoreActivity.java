package kupczyk.dawid.cs50.doggoexplorer;

import android.content.Intent;
import android.graphics.text.LineBreaker;
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

        //Getting data from the Intent
        Intent i = getIntent();
        dog            = (Dog) i.getSerializableExtra("dogObject");
        //If the string URL was not added to an intent, an "image not found" picture is being displayed
        String img_url = dog.getImageUrl();
        if(img_url.isEmpty())img_url = "https://www.publicdomainpictures.net/pictures/280000/velka/not-found-image-15383864787lu.jpg";
        String dogName = dog.getName();
        final String[] info = {""};

        ImageView dogImg    = (ImageView) findViewById(R.id.ivLearnMore);
        TextView wiki       = (TextView) findViewById(R.id.wiki);
        wiki.setJustificationMode(LineBreaker.JUSTIFICATION_MODE_INTER_WORD);
        TextView breedName  = (TextView) findViewById(R.id.tvLearnMoreBreedName);

        breedName.setText(dogName);
        Picasso.get().load(img_url).into(dogImg);

        //Using Jsoup to browse through google search results for the "dogName dog wikipedia" query
        //This allows me to get the first search result which is the wikipedia entry and extract the
        //Wikipedia page title from it, the title is being used in making the Wikimedia API call
        new Thread(() -> {
            String wikiTitle="";
            StringBuilder sb = new StringBuilder();
            try {
                //Making the google query
                Document doc = Jsoup.connect("https://www.google.com/search?q="+ dogName +"+dog+wikipedia&oq="+ dogName +"+dog+wikipedia&sourceid=chrome&lr=lang_en&ie=UTF-8").get();
                //The results are being stored into "yuRUbf" divs, making a set of them
                Elements links = doc.select("div.yuRUbf a");
                //Getting the first one, this one is always the Wikipedia article
                Element link = links.get(0);
                //Getting the link which is being stored in "href" html tag
                wikiTitle = link.attr("href");
                //Using substring to get the word that is being stroed after the last "\" in the link
                // This word is the Wikipedia article title
                wikiTitle = wikiTitle.substring(wikiTitle.lastIndexOf("/") + 1);

            } catch (IOException e) {
                sb.append("Error: ").append(e.getMessage());
            }


            // Instantiate the RequestQueue.
            RequestQueue queue = Volley.newRequestQueue(getBaseContext());
            //Adding the article title to the url
            String url ="https://en.wikipedia.org/w/api.php?format=json&action=query&prop=extracts&exintro=explaintext&titles="+wikiTitle;


            // Request a string response from the provided URL.
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                    response -> {

                        //Convert the response to JSONArray
                        try {
                            //Parsing JSON
                            JSONObject entry = new JSONObject(response);
                            JSONObject entryDeeper = entry.getJSONObject("query").getJSONObject("pages");
                            JSONObject entryDeepest = entryDeeper.getJSONObject((String)entryDeeper.keys().next());
                            //A method used to add extra data if provided as the Dog class extra fields values
                            addExtraData(info);
                            //Making a string containing the Wikipedia description, getting rid of all the HTML tags inside it as well
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

    //A method checking if some additional fields in the Dog class are empty and where they are not,
    //adding the value stored underneath them to the dog description
    private void addExtraData(String[] info) {
        if(!dog.getHeight().isEmpty())
            info[0]+= "Height:\t\t" + dog.getHeight() +" [cm]\n";
        if(!dog.getWeight().isEmpty())
            info[0]+= "Weight:\t\t" + dog.getWeight() +" [kg]\n";
        if(!dog.getBred_for().isEmpty())
            info[0]+= "Bred for:\t\t" + dog.getBred_for() +"\n";
        if(!dog.getBreed_group().isEmpty())
            info[0]+= "Breed group:\t\t" + dog.getBreed_group() +"\n";
        if(!dog.getLife_span().isEmpty())
            info[0]+= "Life span:\t\t" + dog.getLife_span() +"\n";
        if(!dog.getOrigin().isEmpty())
            info[0]+= "Origin:\t\t" + dog.getOrigin() +"\n";
        if(!dog.getTemperament().isEmpty())
            info[0]+= "Temperament:\t\t" + dog.getTemperament() +"\n";

    }
}
