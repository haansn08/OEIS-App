package com.shaan.oeis;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * A basic Adapter for DrawerItems that can be used to fill a ListView
 */
public class DrawerAdapter extends BaseAdapter {

    protected List<DrawerItem> drawerItems;

    protected DrawerAdapter()
    {
        drawerItems = new ArrayList<DrawerItem>();
    }

    @Override
    public int getCount() {
        return drawerItems.size();
    }

    @Override
    public Object getItem(int i) {
        return drawerItems.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup){
        return drawerItems.get(i).constructItemView();
    }
}
