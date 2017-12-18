package com.example.asus.parkingidiots;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.github.pwittchen.infinitescroll.library.InfiniteScrollListener;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends Activity {

    private static RecyclerView.Adapter adapter;
    private LinearLayoutManager layoutManager;
    private static RecyclerView recyclerView;
    private static ArrayList<Post> data;
    static View.OnClickListener myOnClickListener;
    private static ArrayList<Integer> removedItems;
    private ArrayList<Post> testData;
    private Integer currentItem = 0;
    private Boolean scrolledtoEnd = false;
    private EndlessRecyclerViewScrollListener scrollListener;
    SwipeRefreshLayout swipeRefreshLayout;

    private SharedPreferences mPrefs;

    private String configFileName = "appConfiguration";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        ConfigContainer.init(getApplicationContext());

        setContentView(R.layout.activity_main);
        if(ConfigContainer.getUser() == null) {
            Intent myIntent = new Intent(MainActivity.this, InitialActivity.class);
            startActivityForResult(myIntent, 1);
           // finish();
        }


        myOnClickListener = new MyOnClickListener(this);
        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.simpleSwipeRefreshLayout);

        swipeRefreshLayout.setRefreshing(true);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                currentItem = 0;
                scrolledtoEnd  = false;
                testData = new ArrayList<Post>();
                adapter = new CustomAdapter(testData);
                recyclerView.setAdapter(adapter);
                new MyTask().execute(5);

                // implement Handler to wait for 3 seconds and then update UI means update value of TextView
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // cancle the Visual indication of a refresh
                       // swipeRefreshLayout.setRefreshing(false);

                    }
                }, 3000);
            }
        });

      testData = new ArrayList<Post>();
        adapter = new CustomAdapter(testData);
        recyclerView.setAdapter(adapter);
        new MyTask().execute(5);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        // Retain an instance so that you can call `resetState()` for fresh searches
        scrollListener = new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to the bottom of the list
                if(!scrolledtoEnd) {
                    new MyTask().execute(1);
                }
            }
        };
        // Adds the scroll listener to RecyclerView
        recyclerView.addOnScrollListener(scrollListener);

        //}




        recyclerView.setAdapter(adapter);

        System.out.println("Setting adapter again!");
    }


    private class MyTask extends AsyncTask<Integer, Integer, Integer> {

        // Runs in UI before background thread is called
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            // Do something like display a progress bar
        }

        // This is run in a background thread
        @Override
        protected Integer doInBackground(Integer... params) {
            // get the string from params, which is an array
            Integer itemTotal = params[0];
              Integer current;
            Post p = null;
            try {
                p = parseBestItemJSONObject(HttpConnector.getBestItems(currentItem));
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("Data added to dataset!");
            if(p != null ) {
                testData.add(p);
                currentItem++;
            }

            return itemTotal;
        }

        // This is called from background thread but runs in UI
        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);

            // Do things like update the progress bar
        }

        // This runs in UI when background thread finishes
        @Override
        protected void onPostExecute(Integer result) {
            super.onPostExecute(result);

            //recyclerView.setAdapter(adapter);

            adapter.notifyDataSetChanged();
            if(currentItem < result) {
                if(currentItem> 2) {
                    swipeRefreshLayout.setRefreshing(false);
                }

                System.out.println("Starting new task for " + currentItem);
                new MyTask().execute(result);
            }
            // Do things like hide the progress bar or change a TextView
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.take_photo:
                this.startTakePhotoActivity();
                return true;
            case R.id.settings:
                this.startInitialActivity();
                return true;
            case R.id.my_photos:
            return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    public void onViewCreated(View view, Bundle savedInstanceState) {
        int SDK_INT = android.os.Build.VERSION.SDK_INT;
        if (SDK_INT > 8) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
            //your codes here

        }
    }


    private InfiniteScrollListener createInfiniteScrollListener() {
        return new InfiniteScrollListener(1, layoutManager) {
            @Override public void onScrolledToEnd(final int firstVisibleItemPosition) {
                // load your items here
                // logic of loading items will be different depending on your specific use case

                if(!scrolledtoEnd) {
                    new MyTask().execute(1);
                    // when new items are loaded, combine old and new items, pass them to your adapter
                    // and call refreshView(...) method from InfiniteScrollListener class to refresh RecyclerView
                    //refreshView(recyclerView, new CustomAdapter(testData), firstVisibleItemPosition);
                }
            }
        };
    }



    private ConfigModel getConfigurationFile(){

        Gson gson = new Gson();
        String json = mPrefs.getString(configFileName, "");
        ConfigModel conf = gson.fromJson(json, ConfigModel.class);
        return conf;
    }

    private void saveConfigurationFile(ConfigModel conf) {
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        Gson gson = new Gson();
        String jsonConf = gson.toJson(conf);
        prefsEditor.putString(configFileName, jsonConf);
        prefsEditor.commit();
    }

    @Override
    public void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        if (requestCode == 2) { //InitialActivity
            if (resultCode == Activity.RESULT_OK) {
                ConfigModel configuration  = (ConfigModel) data.getSerializableExtra("result");
                saveConfigurationFile(configuration);
                Toast.makeText(MainActivity.this, "Settings saved!", Toast.LENGTH_SHORT).show();
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
        else if (requestCode == 1) { //Settings activity
            if (resultCode == Activity.RESULT_OK) {
                ConfigModel configuration  = (ConfigModel) data.getSerializableExtra("result");
                saveConfigurationFile(configuration);
                Toast.makeText(MainActivity.this, "Settings saved!", Toast.LENGTH_SHORT).show();
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }

    }

    public Post parseBestItemJSONObject(JSONObject o) throws JSONException {
        Boolean sucess = o.getBoolean("success");
        String message = o.getString("message");
        if (sucess) {
            JSONObject data = o.getJSONObject("data");
            String id = data.getString("Id");
            String authorID = data.getString("AuthorID");
            String authorName = data.getString("AuthorName");
            String text = data.getString("Description");
            String place = data.getString("Place");
            // String img64Base  = data.getString("Img64BaseString");
            String imgPath = data.getString("ImgPath");
            String created = data.getString("Created");
            Long viewed = data.getLong("Viewed");
            Long popularity = data.getLong("Popularity");
            Boolean obsolete = data.getBoolean("Obsolete");
            Post p = new Post(authorName, created, imgPath, text, popularity, viewed);
            return p;
        }
        if(message.equals("END"))
        {

            System.out.println("SCROLLED to END!");
            this.scrolledtoEnd = true;
        }
        return null;
    }



    private void startInitialActivity() {
        Intent myIntent = new Intent(MainActivity.this, InitialActivity.class);
        myIntent.putExtra("key", ""); //Optional parameters
        startActivityForResult(myIntent, 2);
    }

    private void startTakePhotoActivity() {
        Intent myIntent = new Intent(MainActivity.this, PhotoActivity.class);
        myIntent.putExtra("key", ""); //Optional parameters
        startActivityForResult(myIntent, 3);
    }

    private static class MyOnClickListener implements View.OnClickListener {

        private final Context context;

        private MyOnClickListener(Context context) {
            this.context = context;
        }

        @Override
        public void onClick(View v) {
            //removeItem(v);
        }

       /* private void removeItem(View v) {
            int selectedItemPosition = recyclerView.getChildPosition(v);
            RecyclerView.ViewHolder viewHolder
                    = recyclerView.findViewHolderForPosition(selectedItemPosition);
            TextView textViewName
                    = (TextView) viewHolder.itemView.findViewById(R.id.textViewName);
            String selectedName = (String) textViewName.getText();
            int selectedItemId = -1;
            for (int i = 0; i < MyData.nameArray.length; i++) {
                if (selectedName.equals(MyData.nameArray[i])) {
                    selectedItemId = MyData.id_[i];
                }
            }
            removedItems.add(selectedItemId);
            data.remove(selectedItemPosition);
            adapter.notifyItemRemoved(selectedItemPosition);
        }*/
    }




    /*private void addRemovedItemToList() {
        int addItemAtListPosition = 3;
        data.add(addItemAtListPosition, new Post(
                MyData.nameArray[removedItems.get(0)],
                MyData.versionArray[removedItems.get(0)],
                MyData.id_[removedItems.get(0)],
                MyData.drawableArray[removedItems.get(0)]
        ));
        adapter.notifyItemInserted(addItemAtListPosition);
        removedItems.remove(0);
    }*/
//}

}




