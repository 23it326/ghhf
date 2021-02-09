package com.plantix.demo.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.plantix.demo.Adapter.CustomAdapter;
import com.plantix.demo.Beans.Recipe;
import com.plantix.demo.R;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        init();
    }

    private void init() {
        initView();
        initMembers();
    }

    private void initView() {
        // get the reference of RecyclerView
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
    }

    private void initMembers() {
        ArrayList<Recipe> recipes = getIntent().getParcelableArrayListExtra("list");
        // set a LinearLayoutManager with default vertical orientation
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        //  call the constructor of CustomAdapter to send the reference and data to Adapter
        CustomAdapter customAdapter = new CustomAdapter(ListActivity.this, recipes);
        recyclerView.setAdapter(customAdapter); // set the Adapter to RecyclerView
    }


}
