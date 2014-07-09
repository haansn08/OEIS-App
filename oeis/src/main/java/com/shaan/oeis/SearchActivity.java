package com.shaan.oeis;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

public class SearchActivity extends Activity {
    public static final String SEARCH_QUERY = "com.shaan.oeis.SEARCH_QUERY";
    private boolean wasError = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_layout);

        final ListView searchResultsListView = (ListView)findViewById(R.id.search_results);
        searchResultsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (wasError)
                    return;
                Intent intent = new Intent(SearchActivity.this, SequenceDetailActivity.class);
                OEISSequence selectedSequence = (OEISSequence)
                        searchResultsListView.getAdapter().getItem(i);
                intent.putExtra(
                        SequenceDetailActivity.SEQUENCE_ID,
                        selectedSequence.getStringByName(OEISSequence.ID_NAME)
                );
                startActivity(intent);
            }
        });

        getActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.search_activity_menu, menu);

        MenuItem searchBoxItem = menu.findItem(R.id.search_menu_search_box);
        SearchView searchBox = (SearchView)searchBoxItem.getActionView();
        searchBox.setIconifiedByDefault(false);
        searchBox.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                findViewById(R.id.search_progress).setVisibility(View.VISIBLE);
                ListView searchResults = (ListView)findViewById(R.id.search_results);
                searchResults.setAdapter(null);

                SearchTask searchTask = new SearchTask();
                searchTask.execute(s);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return true;
            }
        });
        Intent intent = getIntent();
        searchBox.setQuery(intent.getStringExtra(SEARCH_QUERY), true);

        return super.onCreateOptionsMenu(menu);
    }

    class SearchTask extends AsyncTask<String, Void, ListAdapter>
    {
        @Override
        protected ListAdapter doInBackground(String... strings){
            try {
                //register search query in database
                SQLiteOpenHelper recentSearchesOpenHelper =
                        new RecentSearchesDatabase(SearchActivity.this);
                SQLiteDatabase recentSearchesDatabase =
                        recentSearchesOpenHelper.getWritableDatabase();
                ContentValues thisQueryRowData = new ContentValues();
                thisQueryRowData.put(RecentSearchesDatabase.COLUMN_SEARCH_QUERY, strings[0]);
                assert recentSearchesDatabase != null;
                recentSearchesDatabase.insert(RecentSearchesDatabase.TABLE_RECENT_SEARCHES, null,
                        thisQueryRowData);
                recentSearchesDatabase.close();

                RawDataProvider dataProvider = new WebContentProvider(SearchActivity.this);
                        //new MockDataProvider(SearchActivity.this); <-- Professional debugging
                ListAdapter resultsAdapter = new SearchResultsAdapter(SearchActivity.this, strings[0],
                        dataProvider);
                wasError = false;
                return resultsAdapter;
            }
            catch (Exception e)
            {
                ArrayAdapter errorAdapter =
                        new ArrayAdapter(SearchActivity.this,
                                R.layout.simple_text_item,
                                R.id.simple_text_item_text);
                errorAdapter.add(e.getMessage());
                wasError = true;
                return errorAdapter;
            }
        }

        @Override
        protected void onPostExecute(ListAdapter result) {
            findViewById(R.id.search_progress).setVisibility(View.GONE);
            ListView searchResults = (ListView)findViewById(R.id.search_results);
            searchResults.setAdapter(result);
            super.onPostExecute(result);
        }
    }
}
