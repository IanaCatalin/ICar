package com.example.dany.icar;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;

public class DataOra extends AppCompatActivity {

    public static String dateTime;
    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_ora);

        bundle=getIntent().getExtras();

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void setDataOra(View view)
    {
        DatePicker dp=(DatePicker)findViewById(R.id.datePicker);
        TimePicker tp=(TimePicker)findViewById(R.id.timePicker);

        String day, month, year, hour, minute;
        day=String.valueOf(dp.getDayOfMonth());
        month=String.valueOf(dp.getMonth()+1);
        year=String.valueOf(dp.getYear());
        hour=String.valueOf(tp.getHour());
        minute=String.valueOf(tp.getMinute());

        if(dp.getDayOfMonth()<10)
            day='0'+day;
        if(dp.getMonth()<10)
            month='0'+month;
        if(tp.getHour()<10)
            hour='0'+hour;
        if(tp.getMinute()<10)
            minute='0'+minute;

        dateTime = day + '.' + month + '.' + year + ' ' +
                hour  + ':' + minute;

        Intent intent=new Intent(this,FormularRezervare.class);

        if(bundle!=null)
        {
            intent.putExtra("functieApelatoare",bundle.getString("functieApelatoare"));
            intent.putExtra("selectieSpPreluare",bundle.getInt("selectieSpPreluare"));
            intent.putExtra("selectieSpPredare",bundle.getInt("selectieSpPredare"));
            intent.putExtra("selectieSpMasina",bundle.getInt("selectieSpMasina"));
            intent.putExtra("textEtPreluare",bundle.getString("textEtPreluare"));
            intent.putExtra("textEtPredare",bundle.getString("textEtPredare"));
        }
        startActivity(intent);
    }
}
