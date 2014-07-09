package com.shaan.oeis;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class SimpleDrawerItem implements DrawerItem{
    protected String title;
    protected int iconResource;
    protected Context context;

    SimpleDrawerItem(Context context, String title, int icon)
    {
        this.context = context;
        this.title = title;
        this.iconResource = icon;
    }

    @Override
    public View constructItemView() {
        LayoutInflater layoutInflater =
                (LayoutInflater)context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View itemView = layoutInflater.inflate(R.layout.simple_drawer_item_layout, null);
        fillViewContent(itemView);
        return itemView;
    }

    private void fillViewContent(View itemView)
    {
        ImageView iconView = (ImageView)itemView.findViewById(R.id.simple_drawer_item_icon);
        TextView titleView = (TextView)itemView.findViewById(R.id.simple_drawer_item_title);

        iconView.setImageResource(iconResource);
        titleView.setText(title);
    }
}
