package com.shaan.oeis;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;

public class HomeDrawerAdapter extends DrawerAdapter {

    public HomeDrawerAdapter(Context context)
    {
        Resources resources = context.getResources();
        String titles[] = resources.getStringArray(R.array.home_drawer_item_titles);
        TypedArray icons = resources.obtainTypedArray(R.array.home_drawer_item_icons);

        assert titles.length == icons.length();

        for (int i = 0; i < titles.length; i++) {
            DrawerItem drawerItem =
                    new SimpleDrawerItem(context, titles[i], icons.getResourceId(i, -1));
            drawerItems.add(drawerItem);
        }
    }
}
