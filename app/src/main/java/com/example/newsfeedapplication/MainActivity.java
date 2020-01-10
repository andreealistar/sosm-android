package com.example.newsfeedapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.SearchView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import java.util.List;
import java.util.Arrays;
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

        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="https://android-news-feed.azurewebsites.net/api/HttpTrigger1?code=sdpMUNHHaT0gk6kkhPfc35veQ5qoSn20JWOa3UYRxhNQKuaSUNmzWA==";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        News[] news = new Gson().fromJson(response, News[].class);
                        List<News> oldNews = NewsRoomDatabase.getDatabase(getApplicationContext()).newsDao().loadAll();
                        List<News> models = Arrays.asList(news);

                        for (News newNews : models) {
                            boolean exists = false;
                            for (News oldNew : oldNews) {
                                if (oldNew.id == newNews.id) {
                                    exists = true;
                                }
                            }
                            newNews.exists = exists;
                        }
                        NewsRoomDatabase.getDatabase(getApplicationContext()).newsDao().insertAll(news);
                        String mSortSetting = preferences.getString("Sort", "asceding");

                        if (mSortSetting.equals("ascending")) {
                            Collections.sort(models, News.By_TITLE_ASCEDING);
                        }
                        else if (mSortSetting.equals("descending")) {
                            Collections.sort(models, News.By_TITLE_DESCENDING);
                        }

                        mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                        myAdapter = new MyAdapter(getApplicationContext(), models);
                        mRecyclerView.setAdapter(myAdapter);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("news", error.getMessage());
            }
        });

        queue.add(stringRequest);
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
