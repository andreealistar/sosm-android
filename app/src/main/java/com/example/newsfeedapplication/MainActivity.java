package com.example.newsfeedapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.SearchView;
import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;
    MyAdapter myAdapter;
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = findViewById(R.id.recyclerView);
        preferences = this.getSharedPreferences("My_Pref", MODE_PRIVATE);

        getMyList();
    }

    private void getMyList() {
        ArrayList<Model> models = new ArrayList<>();

        Model m = new Model();
        m.setTitle("News Feed - image_1");
        m.setDescription("This is image_1 description..");
        m.setImg(R.drawable.first);
        models.add(m);

        m = new Model();
        m.setTitle("News Feed - image_2");
        m.setDescription("This is image_2 description..");
        m.setImg(R.drawable.second);
        models.add(m);

        m = new Model();
        m.setTitle("News Feed - image_3");
        m.setDescription("This is image_3 description..");
        m.setImg(R.drawable.first);
        models.add(m);

        m = new Model();
        m.setTitle("News Feed - image_4");
        m.setDescription("This is image_4 description..");
        m.setImg(R.drawable.second);
        models.add(m);

        m = new Model();
        m.setTitle("News Feed - image_5");
        m.setDescription("This is image_5 description..");
        m.setImg(R.drawable.first);
        models.add(m);

        String mSortSetting = preferences.getString("Sort", "asceding");

        if (mSortSetting.equals("ascending")) {
            Collections.sort(models, Model.By_TITLE_ASCEDING);
        }
        else if (mSortSetting.equals("descending")) {
            Collections.sort(models, Model.By_TITLE_DESCENDING);
        }

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        myAdapter = new MyAdapter(this, models);
        mRecyclerView.setAdapter(myAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);

        MenuItem item = menu.findItem(R.id.search);

        SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                myAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                myAdapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.sorting) {
            sortDailog();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void sortDailog() {
        String[] options = {"Ascending", "Descending"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Sort by");
        builder.setIcon(R.drawable.ic_action_sort);

        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("Sort", "asceding");
                    editor.apply();
                    getMyList();
                }
                if (which == 1) {
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("Sort", "descending");
                    editor.apply();
                    getMyList();
                }
            }
        });

        builder.create().show();
    }
}
