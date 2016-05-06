package com.elderly.elderly;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by PC on 22/4/59.
 */
public class ItemAdapter extends BaseAdapter {
    ArrayList<String> name = new ArrayList<>();
    Context ctx;
    public ItemAdapter(Context ctx, ArrayList<String> name) {
        this.ctx = ctx;
        this.name = name;
    }

    @Override
    public int getCount() {
        return name.size();
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater)ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if(convertView == null)
            convertView = layoutInflater.inflate(R.layout.item_row, parent, false);

        TextView txtname = (TextView) convertView.findViewById(R.id.name);
        txtname.setText(name.get(position));
        return convertView;
    }
}
