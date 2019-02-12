package com.example.dany.icar;

import java.util.ArrayList;
import java.util.List;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.Legend.LegendPosition;
import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.DataSet;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IPieDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.MPPointF;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
//import com.github.mikephil.charting.utils.Highlight;
//import com.github.mikephil.charting.utils.PercentFormatter;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class GraficInchirieri extends Activity {

    private RelativeLayout mainLayout;
    private PieChart mChart;
    DatabaseReference mRezervariReference;
    // we're going to display pie chart for smartphones martket shares

    private ArrayList<String> numeMasini = new ArrayList<String>();
    private int contor;

    private float[] yData = { 5, 10, 15, 30, 40 };
    private String[] xData = { "Sony", "Huawei", "LG", "Apple", "Samsung" };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grafic_inchirieri);
        mainLayout = (RelativeLayout) findViewById(R.id.layoutGrafic);
        mChart = new PieChart(this);
        // add pie chart to main layout
        mainLayout.addView(mChart, RelativeLayout.LayoutParams.MATCH_PARENT,1300);
        mainLayout.setBackgroundColor(Color.parseColor("#55656C"));

        // configure pie chart
        mChart.setUsePercentValues(true);
        Description descriere=new Description();
        descriere.setText("Inchirieri masini");
        mChart.setDescription(descriere);

        // enable hole and configure
        mChart.setDrawHoleEnabled(true);
        mChart.setHoleColor(R.color.colorWhite);
        mChart.setHoleRadius(7);
        mChart.setTransparentCircleRadius(10);


        // enable rotation of the chart by touch
        mChart.setRotationAngle(0);
        mChart.setRotationEnabled(true);

        // set a chart value selected listener
        mChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {

            @Override
            public void onValueSelected(Entry e, Highlight h) {
                // display msg when value selected
                if (e == null)
                    return;

                Toast.makeText(GraficInchirieri.this,
                        "Au fost inchiriate "+e.getY()+".", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected() {

            }
        });

        mRezervariReference = FirebaseDatabase.getInstance().getReference().child("rezervare");

        //ArrayAdapter<String> adapter;

        contor=0;

        ChildEventListener childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChild) {
                System.out.println(dataSnapshot.getValue());
                Rezervare rezervare = dataSnapshot.getValue(Rezervare.class);

                numeMasini.add(rezervare.getMasina());
                addData();

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                System.out.println(dataSnapshot.getValue());
                Rezervare rezervare = dataSnapshot.getValue(Rezervare.class);

                numeMasini.add(rezervare.getMasina());
                addData();

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }


        };
        mRezervariReference.addChildEventListener(childEventListener);


        // add data
        addData();

        // customize legends
        Legend l = mChart.getLegend();
        l.setPosition(LegendPosition.BELOW_CHART_CENTER);
        l.setXEntrySpace(7);
        l.setYEntrySpace(5);
    }

    private void addData() {
        ArrayList<String> xVals=new ArrayList<String>();
        for(String n:numeMasini)
        {
            if(!xVals.contains(n))
            {
                xVals.add(n);
            }
        }

        ArrayList<PieEntry> yVals1 = new ArrayList<PieEntry>();

        int i = 0;
        for(String n:xVals)
        {
            int aparitii=0;
            for(String s:numeMasini)
                if(s.equals(n))
                    aparitii++;
            yVals1.add(new PieEntry(aparitii, i));
            i++;
        }

        // create pie data set
        PieDataSet dataSet = new PieDataSet(yVals1, "Masini inchiriate: ");
        dataSet.setSliceSpace(3);
        dataSet.setSelectionShift(5);

        // add many colors
        ArrayList<Integer> colors = new ArrayList<Integer>();

        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);

        colors.add(ColorTemplate.getHoloBlue());
        dataSet.setColors(colors);
        mChart.getLegend().setExtra(colors,xVals);
        mChart.getLegend().setWordWrapEnabled(true);

        // instantiate pie data object now
        PieDataSet dataset= new PieDataSet(yVals1,"Inchirieri masini");
        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.GRAY);

        mChart.setData(data);

        // undo all highlights
        mChart.highlightValues(null);

        // update pie chart
        mChart.invalidate();
    }

}