package com.younglings.devhelp.ui.login;
import android.content.Context;

import android.view.LayoutInflater;

import com.younglings.devhelp.R;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ListViewExplore extends BaseAdapter{
    Context context;
    String nameComps[];
    String positions[];
    LayoutInflater inflater;

    public ListViewExplore(Context ctx, String [] nameComps, String [] positions){
        this.context =ctx;
        this.nameComps=nameComps;
        this.positions=positions;
        inflater = LayoutInflater.from(ctx);
    }

    @Override
    public int getCount(){
        return nameComps.length;
    }

    public Object getItem(int position){
        return null;
    }

    public long getItemId(int position){
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent){
        if (convertView==null) {
            convertView = inflater.inflate(R.layout.fragment_item, null);
        }
        TextView compName = (TextView) convertView.findViewById(R.id.item_number);
        TextView Description = (TextView) convertView.findViewById(R.id.content);

        compName.setText(nameComps[position]);
        Description.setText(positions[position]);

        return  convertView;
    }
}