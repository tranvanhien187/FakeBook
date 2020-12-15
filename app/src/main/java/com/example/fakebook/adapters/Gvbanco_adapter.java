package com.example.fakebook.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fakebook.R;

import java.net.Inet4Address;
import java.util.zip.Inflater;

public class Gvbanco_adapter extends BaseAdapter {
    Context context;
    public int icons[];
    public int tiencuoc[];


    public Gvbanco_adapter(Context context, int[] icons, int[] tiencuoc) {
        this.context = context;
        this.icons = icons;
        this.tiencuoc = tiencuoc;
    }

    @Override
    public int getCount() {
        return icons.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View gridview = convertView;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        gridview = inflater.inflate(R.layout.custom_item_bancobaucua, null);
        ImageView icon = gridview.findViewById(R.id.imgicon);
        TextView tiencuoc = gridview.findViewById(R.id.txttiencuoc);
        icon.setImageResource(icons[position]);
        tiencuoc.setText("0");
        return gridview;
    }
}
