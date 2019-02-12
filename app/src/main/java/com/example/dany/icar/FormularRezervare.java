package com.example.dany.icar;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;


public class FormularRezervare extends AppCompatActivity {

    Spinner spPreluare, spPredare, spMasini;
    Bundle bundle;
    EditText etPreluare, etPredare;

    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formular_rezervare);

        spPreluare=(Spinner)findViewById(R.id.spLocatiiPreluare);
        spPredare=(Spinner)findViewById(R.id.spLocatiePredare);
        spMasini=(Spinner)findViewById(R.id.spMasini);
        etPreluare=(EditText)findViewById(R.id.etDOPreluare);
        etPredare=(EditText)findViewById(R.id.etDOPredare);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        ArrayList<String> spinnerArray =  new ArrayList<String>();
        for(Masina masina:MeniulPrincipal.masini)
            spinnerArray.add(masina.denumire);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, spinnerArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spMasini.setAdapter(adapter);

        bundle=getIntent().getExtras();
        if(bundle!=null)
        {
            if(bundle.getString("functieApelatoare").equals("preluare"))
            {
                    etPreluare.setText(DataOra.dateTime);
                    etPredare.setText(bundle.getString("textEtPredare"));
                    spMasini.setSelection(bundle.getInt("selectieSpMasina"));
                    spPreluare.setSelection(bundle.getInt("selectieSpPreluare"));
                    spPredare.setSelection(bundle.getInt("selectieSpPredare"));

            }
            else
            {
                etPredare.setText(DataOra.dateTime);
                etPreluare.setText(bundle.getString("textEtPreluare"));
                spMasini.setSelection(bundle.getInt("selectieSpMasina"));
                spPreluare.setSelection(bundle.getInt("selectieSpPreluare"));
                spPredare.setSelection(bundle.getInt("selectieSpPredare"));
            }

        }

    }

    public void setPreluare(View view)
    {
        Intent intent=new Intent(this, DataOra.class);
        intent.putExtra("functieApelatoare","preluare");
        intent.putExtra("selectieSpPreluare",spPreluare.getSelectedItemPosition());
        intent.putExtra("selectieSpPredare",spPredare.getSelectedItemPosition());
        intent.putExtra("selectieSpMasina",spMasini.getSelectedItemPosition());
        intent.putExtra("textEtPreluare",etPreluare.getText().toString());
        intent.putExtra("textEtPredare",etPredare.getText().toString());
        startActivity(intent);
    }

    public void setPredare(View view)
    {
        Intent intent=new Intent(this, DataOra.class);
        intent.putExtra("functieApelatoare","predare");
        intent.putExtra("selectieSpPreluare",spPreluare.getSelectedItemPosition());
        intent.putExtra("selectieSpPredare",spPredare.getSelectedItemPosition());
        intent.putExtra("selectieSpMasina",spMasini.getSelectedItemPosition());
        intent.putExtra("textEtPreluare",etPreluare.getText().toString());
        intent.putExtra("textEtPredare",etPredare.getText().toString());
        startActivity(intent);
    }

    public void trimiteFormular(View view)
    {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        //mDatabase.setValue(currentUser.getEmail().toString());

        //String [] email=currentUser.getEmail().toString().split("@");

        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy hh:mm");
        Date dPreluare, dPredare;
        long zileRezervare;
        try{
            dPreluare = format.parse(etPreluare.getText().toString());

            dPredare = format.parse(etPredare.getText().toString());

            if(dPredare.equals(dPreluare))
            {
                throw new Exception();
            }

            if(dPredare.before(dPreluare))
            {
                throw new Exception();
            }

            long dif=dPredare.getTime()-dPreluare.getTime();

            zileRezervare= TimeUnit.DAYS.convert(dif,TimeUnit.MILLISECONDS);

            if(zileRezervare==0)
            {
                zileRezervare=1;
            }

            Rezervare rezervare=new Rezervare(etPredare.getText().toString(), etPreluare.getText().toString(),
                    currentUser.getEmail().toString(), spPredare.getSelectedItem().toString(), spPreluare.getSelectedItem().toString(),
                    spMasini.getSelectedItem().toString(),MeniulPrincipal.masini.get(spMasini.getSelectedItemPosition()).getPret()*zileRezervare);

            mDatabase.child("rezervare").push().setValue(rezervare.toMap());

        }
        catch(ParseException e){
            Toast.makeText(this,"Completati toate campurile!",Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
        catch (Exception e)
        {
            Toast.makeText(this,"Data predare este inainte de data preluare!",Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }

       /* Map<String, String> dateRezervare = new HashMap<>();

        dateRezervare.put("email",currentUser.getEmail().toString());
        dateRezervare.put("dataPredare", etPredare.getText().toString());
        dateRezervare.put("dataPreluare", etPreluare.getText().toString());
        dateRezervare.put("locatiePredare", spPredare.getSelectedItem().toString());
        dateRezervare.put("locatiePreluare", spPreluare.getSelectedItem().toString());
        dateRezervare.put("masina", spMasini.getSelectedItem().toString());
*/

    }

    @Override
    public void onBackPressed()
    {
        Intent intent=new Intent(this, MeniulPrincipal.class);
        startActivity(intent);
    }
}
