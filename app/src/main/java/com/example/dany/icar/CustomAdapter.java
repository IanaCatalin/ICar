package com.example.dany.icar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.support.design.widget.Snackbar;
import android.view.ViewGroup;

import java.util.ArrayList;


public class CustomAdapter extends ArrayAdapter<Masina> implements View.OnClickListener{

    private ArrayList<Masina> dataSet;
    Context mContext;

    // View lookup cache
    private static class ViewHolder {
        TextView txtName;
        TextView txtType;
        TextView txtVersion;
        ImageView info;
    }



    public CustomAdapter(ArrayList<Masina> data, Context context) {
        super(context, R.layout.custom_row, data);
        this.dataSet = data;
        this.mContext=context;

    }


    @Override
    public void onClick(View v) {


        int position=(Integer) v.getTag();
        Object object= getItem(position);
        Masina Masina=(Masina)object;




        switch (v.getId())
        {

            case R.id.ivMasina:

                Snackbar.make(v, "Perioada de minim 3 zile : " +Masina.getDetaliiRezervare(), Snackbar.LENGTH_LONG)
                        .setAction("No action", null).show();

                break;


        }


    }

    private int lastPosition = -1;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Masina Masina = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {


            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.custom_row, parent, false);
            viewHolder.txtName = (TextView) convertView.findViewById(R.id.tvDenumire);
            viewHolder.txtType = (TextView) convertView.findViewById(R.id.tvCombustibil);
            viewHolder.txtVersion = (TextView) convertView.findViewById(R.id.tvMotorizare);
            viewHolder.info = (ImageView) convertView.findViewById(R.id.ivMasina);

            result=convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }

        Animation animation = AnimationUtils.loadAnimation(mContext, (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
        result.startAnimation(animation);
        lastPosition = position;


        viewHolder.txtName.setText(Masina.getDenumire());
        viewHolder.txtType.setText(Masina.getTipCombustibil());
        viewHolder.txtVersion.setText(Masina.getMotorizare());
        viewHolder.info.setImageResource(Masina.getImagine());
        viewHolder.info.setOnClickListener(this);
        viewHolder.info.setTag(position);
        // Return the completed view to render on screen
        return convertView;
    }


}