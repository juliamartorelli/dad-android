package com.example.julia.myapplication.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.julia.myapplication.Model.Event;
import com.example.julia.myapplication.R;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.util.ArrayList;
import java.util.List;

public class ListAdapter extends BaseAdapter {

    private ArrayList<Event> events;
    private Context context;
    private static LayoutInflater inflater;

    public ListAdapter(Context context, List<Event> events) {
        this.events = new ArrayList<>();
        this.events.addAll(events);
        this.context = context;
        inflater = ( LayoutInflater )context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return events.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;
        final ViewHolder holder;

        if (v == null) {
            v    = inflater.inflate(R.layout.item_list_row, null);

            holder = new ViewHolder();
            holder.nomeEvento = (TextView) v.findViewById(R.id.text_view_nome);
            holder.local = (TextView) v.findViewById(R.id.text_view_local);
            holder.image = (ImageView) v.findViewById(R.id.image_view);

            v.setTag(holder);
        } else {
            holder=(ViewHolder)v.getTag();
        }

        Event event = events.get(position);

        if(events.size() <= 0) {
            holder.nomeEvento.setText("Não há eventos.");
        } else {
            holder.nomeEvento.setText(event.getName());
            holder.local.setText(event.getData());

            final ImageLoader imageLoader = ImageLoader.getInstance();
            imageLoader.loadImage(event.getUrlImage(), new SimpleImageLoadingListener() {
                public void onLoadingComplete(final String imageUri, final View view, final Bitmap loadedImage) {

                    holder.image.setImageBitmap(loadedImage);
                }
            });
        }

        return v;
    }

    //guarda a referencia do objeto
    public static class ViewHolder{

        public TextView nomeEvento;
        public TextView local;
        public ImageView image;

    }
}