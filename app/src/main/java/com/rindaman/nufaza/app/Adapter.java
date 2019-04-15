package com.rindaman.nufaza.app;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.rindaman.nufaza.R;

import java.util.List;


public class Adapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<Data> item;
    
    public Adapter(Activity activity, List<Data> item) {
        this.activity = activity;
        this.item = item;
    }

    @Override
    public int getCount() {
        return item.size();
    }

    @Override
    public Object getItem(int location) {
        return item.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null)
            convertView = inflater.inflate(R.layout.list_pekerjaan, null);

        TextView nama_pekerjaan = (TextView) convertView.findViewById(R.id.nama_pekerjaan);

        Data data;
        data = item.get(position);

        nama_pekerjaan.setText(data.getNama_pekerjaan());

        return convertView;
    }
}
