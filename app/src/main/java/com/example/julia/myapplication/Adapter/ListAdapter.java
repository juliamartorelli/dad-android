package com.example.julia.myapplication.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.julia.myapplication.Model.Evento;
import com.example.julia.myapplication.R;
import java.util.List;

public class ListAdapter extends BaseAdapter {

    List<Evento> eventos;

    public ListAdapter(Context context, int resource, List<Evento> eventos) {
        this.eventos = eventos;
    }

    @Override
    public int getCount() {
        return eventos.size();
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

        if (v == null) {
            LayoutInflater vi;
            v = vi.inflate(R.layout.item_list_row, null);
        }

        TextView textViewNome = (TextView) v.findViewById(R.id.text_view_nome);
        TextView textViewLocal = (TextView) v.findViewById(R.id.text_view_local);

        Evento evento = eventos.get(position);

        if (evento != null) {
            textViewNome.setText(evento.getNome());
            textViewLocal.setText("Rua das aboboras, 120 - SP - Brasil");
        }

        return v;
    }

}