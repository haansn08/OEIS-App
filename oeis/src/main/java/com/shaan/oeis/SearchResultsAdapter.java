package com.shaan.oeis;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class SearchResultsAdapter extends BaseAdapter {
    private List<OEISSequence> sequences;
    private LayoutInflater inflater;
    private String highlight;
    public SearchResultsAdapter(Context context, String searchQuery, RawDataProvider dataProvider) throws Exception
    {
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        highlight = searchQuery;
        //fill adapter
        OEISSearchQuery oeisSearchQuery = new OEISSearchQuery(searchQuery, dataProvider);
        sequences = oeisSearchQuery.getResults();
    }
    @Override
    public int getCount() {
        return sequences.size();
    }

    @Override
    public Object getItem(int i) {
        return sequences.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null)
            view = inflater.inflate(R.layout.sequence_item_layout, null);

        OEISSequence sequence = sequences.get(i);

        TextView id = (TextView)view.findViewById(R.id.sequence_item_name);
        id.setText(sequence.getStringByName(OEISSequence.NAME_NAME));

        TextView name = (TextView)view.findViewById(R.id.sequence_item_sequence);
        name.setText(Html.fromHtml(
                sequence.getStringByName(OEISSequence.SEQUENCE_NAME).replace(highlight,"<b>"+highlight+"</b>")
        ));

        return view;
    }
}
