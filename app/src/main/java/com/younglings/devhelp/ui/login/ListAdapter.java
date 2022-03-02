package com.younglings.devhelp.ui.login;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.younglings.devhelp.R;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class ListAdapter extends ArrayAdapter<Job> {


    public ListAdapter(Context context, ArrayList<Job> userArrayList){

        super(context,R.layout.fragment_item,userArrayList);

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Job job = getItem(position);

        if (convertView == null){

            convertView = LayoutInflater.from(getContext()).inflate(R.layout.fragment_item,parent,false);

        }

        TextView compName = (TextView) convertView.findViewById(R.id.item_number);
        TextView Description = (TextView) convertView.findViewById(R.id.content);;

        compName.setText(job.header);
        Description.setText(job.descrip);


        return convertView;
    }
}