package com.example.newsfeedapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Display;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;
    MyAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        myAdapter = new MyAdapter(this, getMyList());
        mRecyclerView.setAdapter(myAdapter);
    }

    private ArrayList<Model> getMyList() {
        ArrayList<Model> models = new ArrayList<>();

        Model m = new Model();
        m.setTitle("News Feed - image_1");
        m.setDescription("This is image_1 description..");
        m.setImg(R.drawable.image_1);
        models.add(m);

        m = new Model();
        m.setTitle("News Feed - image_2");
        m.setDescription("This is image_2 description..");
        m.setImg(R.drawable.image_2);
        models.add(m);

        m = new Model();
        m.setTitle("News Feed - image_3");
        m.setDescription("This is image_3 description..");
        m.setImg(R.drawable.image_3);
        models.add(m);

        m = new Model();
        m.setTitle("News Feed - image_4");
        m.setDescription("This is image_4 description..");
        m.setImg(R.drawable.image_4);
        models.add(m);

        m = new Model();
        m.setTitle("News Feed - image_5");
        m.setDescription("This is image_5 description..");
        m.setImg(R.drawable.image_5);


        return models;
    }
}
