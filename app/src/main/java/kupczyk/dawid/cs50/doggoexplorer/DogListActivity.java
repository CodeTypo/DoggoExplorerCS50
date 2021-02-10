package kupczyk.dawid.cs50.doggoexplorer;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * An activity consisting of a RecyclerView populated with clickable dog images as well as their
 * breed names and a heart icons indicating if the dog belongs to the favourites list
 */
public class DogListActivity extends AppCompatActivity implements DogListAdapter.onDogListener {
    private ArrayList<Dog> dogList;
    DogListAdapter adapter;
    private HashMap<String, Boolean> favourites = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        favourites = readFromSP();
        Intent intent = getIntent();
        dogList= (ArrayList<Dog>) intent.getSerializableExtra("list");
        if(favourites.isEmpty()) {
            for (Dog dog : dogList) {
                favourites.put(dog.getName(), false);
            }
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doglist);

        RecyclerView recyclerView = findViewById(R.id.rvDogList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new DogListAdapter(dogList,this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        MenuItem item = menu.findItem(R.id.action_search);
        androidx.appcompat.widget.SearchView searchView = (androidx.appcompat.widget.SearchView) item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * @param view the view that got clicked
     * @param position the dog's position in the RecyclerView
     *
     * A method that executes whenever the users clicks on something inside the RecyclerView
     * Handles different interactions (interactions vary depending on the view that got clicked)
     */
    @Override
    public void onDogClicked(View view, int position) {
        if(view instanceof ImageButton){
            ImageButton favButton = (ImageButton)view;
            String name = dogList.get(position).getName();
            if(favourites.containsKey(name)){
                if(favourites.get(name)){
                    favButton.setImageResource(R.drawable.ic_favorite_gray);
                    favourites.put(name, false);
                } else {
                    favButton.setImageResource(R.drawable.ic_favorite_red);
                    favourites.put(name, true);}
            }

        } else {
            Intent intent = new Intent(this, LearnMoreActivity.class);
            intent.putExtra("dogObject", dogList.get(position));
            startActivity(intent);
        }
    }

    /**
     * @param name The dog breed name
     * @return Boolean value indicating if the dog is in a list of favourites or not.
     */
    @Override
    public boolean isfavourite(String name) {
        return favourites.get(name);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        insertToSP(favourites);
    }

    /**
     * @param jsonMap A HashMap<String, Boolean> containing information whether the dog breed was
     *  selected as user's favourite or not.
     *
     *  This method is responsible of storing the HashMap of favourite dogs into SharedPreferences
     */
    private void insertToSP(HashMap<String, Boolean> jsonMap) {
        String jsonString = new Gson().toJson(jsonMap);
        SharedPreferences sharedPreferences = getSharedPreferences("HashMap", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("map", jsonString);
        editor.apply();
    }


    /**
     * This method is responsible of storing the HashMap of favourite dogs into SharedPreferences
     *
     * @return A HashMap<String, Boolean> containing information whether the dog breed was
     * selected as user's favourite or not.
     */
    private HashMap<String, Boolean> readFromSP(){
        SharedPreferences sharedPreferences = getSharedPreferences("HashMap", MODE_PRIVATE);
        String defValue = new Gson().toJson(new HashMap<String, Boolean>());
        String json=sharedPreferences.getString("map",defValue);
        TypeToken<HashMap<String,Boolean>> token = new TypeToken<HashMap<String,Boolean>>() {};
        return new Gson().fromJson(json,token.getType());
    }
}
