package com.shaan.oeis;

import android.app.Fragment;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;


public class RecentSearchesFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View view =  inflater.inflate(R.layout.recent_searches_fragment, container, false);

        assert view != null;
        ListView recentSearchesList = (ListView)view.findViewById(R.id.recent_searches_list);
        recentSearchesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TextView queryText = (TextView)view.findViewById(R.id.simple_text_item_text);
                Intent intent = new Intent(RecentSearchesFragment.this.getActivity(),
                        SearchActivity.class);
                intent.putExtra(SearchActivity.SEARCH_QUERY, queryText.getText());
                RecentSearchesFragment.this.getActivity().startActivity(intent);
            }
        });

        LoadRecentSearchesTask loadTask = new LoadRecentSearchesTask();
        loadTask.execute(view);
        return view;
    }

    private class LoadRecentSearchesTask extends AsyncTask<View, Void, Cursor>
    {
        private View view;
        @Override
        protected Cursor doInBackground(View... views) {
            view = views[0];
            //load the recent searches data sets
            SQLiteOpenHelper openHelper = new RecentSearchesDatabase(getActivity());
            SQLiteDatabase database = openHelper.getReadableDatabase();
            assert database != null;
            Cursor recentSearchesCursor =
                    database.query(RecentSearchesDatabase.TABLE_RECENT_SEARCHES,
                            new String[]{RecentSearchesDatabase.COLUMN_ID,
                                    RecentSearchesDatabase.COLUMN_SEARCH_QUERY},
                            null, null, null, null, RecentSearchesDatabase.COLUMN_ID + " desc");
            return recentSearchesCursor;
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            ListView recentSearchesList = (ListView)view.findViewById(R.id.recent_searches_list);
            ListAdapter recentSearchesCursorAdapter = new SimpleCursorAdapter(getActivity(),
                    R.layout.simple_text_item,
                    cursor,
                    new String[]{RecentSearchesDatabase.COLUMN_SEARCH_QUERY},
                    new int[]{R.id.simple_text_item_text});
            recentSearchesList.setAdapter(recentSearchesCursorAdapter);

            view.findViewById(R.id.recent_searches_progress).
                    setVisibility(View.GONE);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.recent_searches_menu, menu);
        MenuItem clearHistoryButton = menu.findItem(R.id.recent_searches_clear);
        assert clearHistoryButton != null;
        clearHistoryButton.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                //show progressbar
                getView().findViewById(R.id.recent_searches_progress)
                        .setVisibility(View.VISIBLE);
                //clear history
                (new ClearHistoryTask()).execute();
                return true;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }

    private class ClearHistoryTask extends AsyncTask<Void, Void, Void>
    {
        @Override
        protected Void doInBackground(Void... voids) {
            SQLiteOpenHelper recentSearchesDbOpenHelper =
                    new RecentSearchesDatabase(getActivity());
            SQLiteDatabase recentSearchesDb = recentSearchesDbOpenHelper.getWritableDatabase();
            assert recentSearchesDb != null;
            recentSearchesDb.execSQL("delete from " + RecentSearchesDatabase.TABLE_RECENT_SEARCHES);
            recentSearchesDb.close();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            //reload listview
            (new LoadRecentSearchesTask()).execute(getView());
        }
    }
}
