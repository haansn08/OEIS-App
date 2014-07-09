package com.shaan.oeis;

import android.app.Fragment;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class FavoritesFragment extends Fragment {
    private View itsView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.favorites_fragment, container, false);
        itsView = view;

        assert view != null;
        ListView favoritesList = (ListView)view.findViewById(R.id.favorites_list);
        favoritesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TextView idText = (TextView)view.findViewById(R.id.favorite_item_id);
                Intent intent = new Intent(getActivity(), SequenceDetailActivity.class);
                intent.putExtra(SequenceDetailActivity.SEQUENCE_ID, idText.getText());
                getActivity().startActivity(intent);
            }
        });

        (new LoadFavoritesTask()).execute();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        (new LoadFavoritesTask()).execute();
    }

    private class LoadFavoritesTask extends AsyncTask<Void, Void, Cursor>
    {

        @Override
        protected Cursor doInBackground(Void... voids) {
            FavoritesDatabase favoritesDatabaseHelper = new FavoritesDatabase(getActivity());
            SQLiteDatabase favoritesDatabase = favoritesDatabaseHelper.getReadableDatabase();
            assert favoritesDatabase != null;
            Cursor cursor = favoritesDatabase.query(
                    FavoritesDatabase.TABLE_FAVORITES,
                    new String[]{
                            FavoritesDatabase.COLUMN_ID,
                            FavoritesDatabase.COLUMN_SEQUENCE_ID,
                            FavoritesDatabase.COLUMN_SEQUENCE_NAME
                    },
                    null, null, null, null, FavoritesDatabase.COLUMN_ID + " desc", null
            );
            return cursor;
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            itsView.findViewById(R.id.favorites_progress).setVisibility(View.GONE);
            ListView favoritesList = (ListView)itsView.findViewById(R.id.favorites_list);
            favoritesList.setAdapter(
                    new SimpleCursorAdapter(
                            getActivity(),
                            R.layout.favorite_item_layout,
                            cursor,
                            new String[]{
                                    FavoritesDatabase.COLUMN_SEQUENCE_ID,
                                    FavoritesDatabase.COLUMN_SEQUENCE_NAME
                            },
                            new int[]{
                                    R.id.favorite_item_id,
                                    R.id.favorite_item_name
                            }
                    )
            );
        }
    }
}
