package kupczyk.dawid.cs50.doggoexplorer;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class DogListActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ArrayList<String> breedsList = new ArrayList<String>();
        Intent intent = getIntent();
        breedsList= intent.getStringArrayListExtra("breeds_list");

        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doglist);

        RecyclerView recyclerView = findViewById(R.id.rvDogList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        DogListAdapter adapter = new DogListAdapter(breedsList);
        recyclerView.setAdapter(adapter);
    }

}
