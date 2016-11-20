package com.example.julia.myapplication.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.julia.myapplication.Model.NavigationItem;
import com.example.julia.myapplication.R;

import java.util.ArrayList;

public class MenuListAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<NavigationItem> navigationItems;

    public MenuListAdapter(final Context context, final ArrayList<NavigationItem> navigationItems) {

        this.context = context;
        this.navigationItems = navigationItems;
    }

    @Override
    public int getCount() {

        return navigationItems.size();
    }

    @Override
    public Object getItem(final int position) {

        return navigationItems.get(position);
    }

    @Override
    public long getItemId(final int position) {

        return 0;
    }

    @Override
    public View getView(final int position, final View convertView, final ViewGroup parent) {

        final View view;

        if (convertView == null) {
            final LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.menu_item, null);
        }
        else {
            view = convertView;
        }

        final TextView titleView = (TextView) view.findViewById(R.id.text_view_title);
        //final ImageView iconView = (ImageView) view.findViewById(R.id.image_view_icon);

        titleView.setText(navigationItems.get(position).title);

        //iconView.setImageResource(navigationItems.get(position).icon);

        return view;
    }
}
