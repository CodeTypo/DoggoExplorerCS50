package kupczyk.dawid.cs50.doggoexplorer;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class DogListActivity extends AppCompatActivity implements DogListAdapter.onDogListener {
    private ArrayList<Dog> dogList;
    DogListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        dogList= (ArrayList<Dog>) intent.getSerializableExtra("list");
        //getSupportActionBar().hide();
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

    @Override
    public void onDogClicked(int position) {
        Intent intent = new Intent(this, LearnMoreActivity.class);
        intent.putExtra("dogObject",dogList.get(position));
        startActivity(intent);
    }
}
