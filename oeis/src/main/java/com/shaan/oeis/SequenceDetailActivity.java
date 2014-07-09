package com.shaan.oeis;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SequenceDetailActivity extends Activity {
    public static final String SEQUENCE_ID = "com.shaan.oeis.SEQUENCE_DETAIL_ID";

    private OEISSequence itsSequence;
    private Menu itsMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sequence_detail_layout);
        getActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        String sequenceId = intent.getStringExtra(SEQUENCE_ID);
        setTitle(sequenceId);
        (new LoadSequenceTask()).execute(sequenceId);
    }

    private class LoadSequenceTask extends AsyncTask<String, Void, HashMap<String,List<View>>>
    {
        @Override
        protected HashMap<String,List<View>> doInBackground(String... strings) {
            try {
                OEISSearchQuery searchQuery = new OEISSearchQuery("id:" + strings[0],
                        new WebContentProvider(SequenceDetailActivity.this));

                //Construct HashMap
                OEISSequence oeisSequence =  searchQuery.getResults().get(0);
                itsSequence = oeisSequence;

                HashMap<String, List<View>> categories_collection = new HashMap<String, List<View>>();
                for (OEISAttribute attribute : oeisSequence.attributes) {
                    String category = attribute.getName();
                    if (!categories_collection.containsKey(category))
                    {
                        List<View> viewsOfThisCategory = new ArrayList<View>();
                        //Add header TextView
                        TextView headerTextView = new TextView(SequenceDetailActivity.this);
                        headerTextView.setText(category);
                        headerTextView.setTextAppearance(
                                SequenceDetailActivity.this,
                                R.style.Header
                        );
                        headerTextView.setPadding(0, 16, 0, 0);
                        viewsOfThisCategory.add(headerTextView);
                        categories_collection.put(category, viewsOfThisCategory);
                    }
                    TextView contentTextView = new TextView(SequenceDetailActivity.this);
                    contentTextView.setMovementMethod(LinkMovementMethod.getInstance());
                    contentTextView.setText(
                            Html.fromHtml(attribute.getString())
                    );
                    contentTextView.setPadding(0, 8, 0, 0);
                    categories_collection.get(category).add(contentTextView);
                }
                return categories_collection;
            }
            catch (Exception e)
            {
                return null;
            }
        }

        @Override
        protected void onPostExecute(HashMap<String,List<View>> category_views) {
            findViewById(R.id.sequence_detail_progress).setVisibility(View.GONE);

            LinearLayout mainContainer =
                    (LinearLayout)findViewById(R.id.sequence_detail_main_content);
            for (List<View> views : category_views.values()) {
                for (View view : views) {
                    mainContainer.addView(view);
                }
            }

            invalidateOptionsMenu();
        }
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.sequence_detail_menu, menu);
        itsMenu = menu;

        setFavButtonIcon(isFavorite(new FavoritesDatabase(this)));

        return super.onPrepareOptionsMenu(menu);
    }

    private void setFavButtonIcon (boolean isFav)
    {
        MenuItem favButton = itsMenu.findItem(R.id.sequence_detail_menu_favorite);
        assert favButton != null;
        if (isFav)
            favButton.setIcon(R.drawable.ic_action_important);
        else
            favButton.setIcon(R.drawable.ic_action_not_important);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.sequence_detail_menu_favorite:
                (new ToggleFavoriteTask()).execute();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private class ToggleFavoriteTask extends AsyncTask<Void, Void, Boolean>
    {
        FavoritesDatabase favoritesDatabaseHelper = new FavoritesDatabase(
                SequenceDetailActivity.this
        );
        @Override
        protected Boolean doInBackground(Void... voids) {
            Boolean isFav = isFavorite(favoritesDatabaseHelper);
            if (isFav)
                removeSequenceFromFavorites(favoritesDatabaseHelper);
            else
                addSequenceToFavorites(favoritesDatabaseHelper);
            return !isFav;
        }

        @Override
        protected void onPostExecute(Boolean isFav) {
            setFavButtonIcon(isFav);
        }
    }

    private void addSequenceToFavorites(SQLiteOpenHelper favoritesDatabaseHelper) {
        SQLiteDatabase favoritesDatabase = favoritesDatabaseHelper.getWritableDatabase();
        assert favoritesDatabase != null;
        ContentValues rowValues = new ContentValues();
        rowValues.put(FavoritesDatabase.COLUMN_SEQUENCE_ID,
                itsSequence.getStringByName(OEISSequence.ID_NAME));
        rowValues.put(FavoritesDatabase.COLUMN_SEQUENCE_NAME,
                itsSequence.getStringByName(OEISSequence.NAME_NAME));
        favoritesDatabase.insert(
                FavoritesDatabase.TABLE_FAVORITES,
                null,
                rowValues
        );
        favoritesDatabase.close();
    }

    private void removeSequenceFromFavorites(SQLiteOpenHelper favoritesDatabaseHelper) {
        SQLiteDatabase favoritesDatabase = favoritesDatabaseHelper.getWritableDatabase();
        assert favoritesDatabase != null;
        favoritesDatabase.execSQL("delete from " +
                FavoritesDatabase.TABLE_FAVORITES + " where " +
                FavoritesDatabase.COLUMN_SEQUENCE_ID + "='"+itsSequence.getStringByName(OEISSequence.ID_NAME)+"'"
        );
        favoritesDatabase.close();
    }

    private Boolean isFavorite(SQLiteOpenHelper favoritesDatabaseHelper) {
        if (itsSequence == null)
            return false;
        SQLiteDatabase favoritesDatabase = favoritesDatabaseHelper.getReadableDatabase();
        assert favoritesDatabase != null;
        String sqlQuery = "select * from " +
                FavoritesDatabase.TABLE_FAVORITES + " where " +
                FavoritesDatabase.COLUMN_SEQUENCE_ID + "='" +
                itsSequence.getStringByName(OEISSequence.ID_NAME) + "'";
        Cursor cursor = favoritesDatabase.rawQuery(sqlQuery, null);
        return cursor.getCount() != 0;
    }
}
