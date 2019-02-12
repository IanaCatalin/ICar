package com.example.dany.icar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class ModificaDate extends AppCompatActivity {

    EditText etNume, etPrenume, etVarsta, etCNP, etNrTelefon;
    Button btnModifica;

    FirebaseAuth mAuth;
    DatabaseReference mRezervariReference;
    DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modifica_date);

        etNume=(EditText)findViewById(R.id.etNumeUtilizator);
        etPrenume=(EditText)findViewById(R.id.etPrenumeUtilizator);
        etVarsta=(EditText)findViewById(R.id.etVarstaUtilizatori);
        etCNP=(EditText)findViewById(R.id.etCNP);
        etNrTelefon=(EditText)findViewById(R.id.etNrTelefon);

        mAuth = FirebaseAuth.getInstance();

        mDatabase = FirebaseDatabase.getInstance().getReference();

        FirebaseUser user = mAuth.getCurrentUser();

        mRezervariReference = FirebaseDatabase.getInstance().getReference().child("utilizator");

        Query queryRef = mRezervariReference.orderByKey().equalTo(user.getUid());

        //ArrayAdapter<String> adapter;

        ChildEventListener childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChild) {
                System.out.println(dataSnapshot.getValue());
                Utilizator utilizator = dataSnapshot.getValue(Utilizator.class);

                if(!utilizator.getNume().equals(""))
                {
                    etNume.setText(utilizator.getNume());
                    etPrenume.setText(utilizator.getPrenume());
                    etVarsta.setText(String.valueOf(utilizator.getVarsta()));
                    etCNP.setText(utilizator.getcnp());
                    etNrTelefon.setText(utilizator.getNrTelefon());
                }

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                System.out.println(dataSnapshot.getValue());
                Utilizator utilizator = dataSnapshot.getValue(Utilizator.class);

                if(!utilizator.getNume().equals(""))
                {
                    etNume.setText(utilizator.getNume());
                    etPrenume.setText(utilizator.getPrenume());
                    etVarsta.setText(String.valueOf(utilizator.getVarsta()));
                    etCNP.setText(utilizator.getcnp());
                    etNrTelefon.setText(utilizator.getNrTelefon());
                }

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
        queryRef.addChildEventListener(childEventListener);
    }

    public void modificaDate(View view)
    {
        FirebaseUser user = mAuth.getCurrentUser();

        Utilizator utilizator = new Utilizator(etNume.getText().toString(),etPrenume.getText().toString(),
                Integer.parseInt(etVarsta.getText().toString()),etNrTelefon.getText().toString(),etCNP.getText().toString());

        mDatabase.child("utilizator").child(user.getUid()).setValue(utilizator.toMap());
        Intent intent = new Intent(this, DateUtilizator.class);
        startActivity(intent);
    }
    @Override
    public void onBackPressed()
    {
        Intent intent = new Intent(this, DateUtilizator.class);
        startActivity(intent);
    }
}
