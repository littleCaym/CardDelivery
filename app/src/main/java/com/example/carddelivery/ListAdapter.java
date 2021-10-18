package com.example.carddelivery;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ListAdapter extends BaseAdapter {
    Context context;
    LayoutInflater layoutInflater;
    ArrayList<Requistion> objects;

    public ListAdapter(Context context, ArrayList<Requistion> objects){
        this.context = context;
        this.objects = objects;
        layoutInflater = (LayoutInflater) context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return objects.size();
    }

    @Override
    public Object getItem(int position) {
        return objects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null)
            view = layoutInflater.inflate(R.layout.list_adapter_layout, parent, false);

        TextView textDate = view.findViewById(R.id.adapter_text_date);
        TextView textSurname = view.findViewById(R.id.adapter_text_surname);

        //TODO: вставить дату запроса и имя
        textDate.setText(objects.get(position).getDate_fil().toString());
        textSurname.setText(objects.get(position).getSurname());

        return view;
    }
}
