package kupczyk.dawid.cs50.doggoexplorer;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class DogListActivity extends AppCompatActivity implements DogListAdapter.onDogListener {
    private ArrayList<Dog> dogList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        dogList= (ArrayList<Dog>) intent.getSerializableExtra("list");
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doglist);

        RecyclerView recyclerView = findViewById(R.id.rvDogList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        DogListAdapter adapter = new DogListAdapter(dogList,this);
        recyclerView.setAdapter(adapter);
    }


    @Override
    public void onDogClicked(int position) {
        Intent intent = new Intent(this, LearnMoreActivity.class);
        intent.putExtra("dogObject",dogList.get(position));
        startActivity(intent);
    }
}
