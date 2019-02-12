
package com.example.dany.icar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.support.design.widget.Snackbar;

import java.util.ArrayList;

public class ListaMasini extends AppCompatActivity {

    ListView listView;
    private static CustomAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_masini);
        //     Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //     setSupportActionBar(toolbar);

        listView = (ListView) findViewById(R.id.lvMasini);


        adapter = new CustomAdapter(MeniulPrincipal.masini, getApplicationContext());

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Masina masina = MeniulPrincipal.masini.get(position);
                gotoFormular(view);
                Snackbar.make(view, masina.getDenumire() + "\n" + masina.getTipCombustibil() + "  " + masina.getMotorizare(), Snackbar.LENGTH_LONG)
                        .setAction("No action", null).show();
            }
        });
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void gotoFormular(View view)
    {
        Intent intent = new Intent(this, FormularRezervare.class);
        startActivity(intent);
    }

    public void goToGrafic(MenuItem menu){
        Intent intent=new Intent(this,GraficInchirieri.class);
        startActivity(intent);
    }

}