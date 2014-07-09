package com.shaan.oeis;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.SearchView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class SearchFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.search_fragment, container, false);

        SearchView searchBox = (SearchView)view.findViewById(R.id.search_fragment_search_box);
        searchBox.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                Intent intent = new Intent(getActivity(), SearchActivity.class);
                intent.putExtra(SearchActivity.SEARCH_QUERY, s);
                getActivity().startActivity(intent);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return true;
            }
        });

        WebView hintsView = (WebView)view.findViewById(R.id.search_fragment_hints);
        String hintsHtml;
        try {
            hintsHtml = getHintHtml();
        }
        catch (Exception e){
            hintsHtml = e.getMessage();
        }
        hintsView.loadData(hintsHtml, "text/html", "UTF-8");
        hintsView.setBackgroundColor(0x000000);

        return view;
    }

    private String getHintHtml() throws IOException {
        StringBuilder htmlContentBuilder = new StringBuilder();
        char buffer[] = new char[1024];
        BufferedReader hintsReader = new BufferedReader(
                new InputStreamReader(getResources().openRawResource(R.raw.hints))
        );
        while (hintsReader.ready())
        {
            int read = hintsReader.read(buffer);
            htmlContentBuilder.append(buffer, 0, read);
        }
        return htmlContentBuilder.toString();
    }
}
