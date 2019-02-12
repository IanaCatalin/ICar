package com.example.dany.icar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class DateUtilizator extends AppCompatActivity {

    TextView tvNume,tvPrenume, tvVarsta, tvCNP, tvNrTelefon;
    Button btnModificaDate;

    FirebaseAuth mAuth;
    DatabaseReference mRezervariReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_utilizator);



        tvNume = (TextView) findViewById(R.id.tvNume);
        tvPrenume = (TextView) findViewById(R.id.tvPrenume);
        tvVarsta = (TextView) findViewById(R.id.tvVarsta);
        tvCNP = (TextView) findViewById(R.id.tvCNP);
        tvNrTelefon = (TextView) findViewById(R.id.tvNrTelefon);
        btnModificaDate = (Button) findViewById(R.id.btnModificaDate);

        mAuth = FirebaseAuth.getInstance();

        FirebaseUser user = mAuth.getCurrentUser();

        mRezervariReference = FirebaseDatabase.getInstance().getReference().child("utilizator");

        Query queryRef = mRezervariReference.orderByKey().equalTo(user.getUid());

        //ArrayAdapter<String> adapter;

        ChildEventListener childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChild) {
                System.out.println(dataSnapshot.getValue());
                Utilizator utilizator = dataSnapshot.getValue(Utilizator.class);

                if(utilizator.getNume().equals("")){

                    tvNume.setText("Nume: necompletat");
                    tvPrenume.setText("Prenume: necompletat");
                    tvVarsta.setText("Varsta: necompletat");
                    tvCNP.setText("CNP: necompletat");
                    tvNrTelefon.setText("Nr. telefon: necompletat");

                }
                else {
                    tvNume.setText("Nume:         "+utilizator.getNume());
                    tvPrenume.setText("Prenume:    " +utilizator.getPrenume());
                    tvVarsta.setText("Varsta:        " +utilizator.getVarsta());
                    tvCNP.setText("CNP:           " +utilizator.getcnp());
                    tvNrTelefon.setText("Nr. telefon: " +utilizator.getNrTelefon());
                }

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                System.out.println(dataSnapshot.getValue());
                Utilizator utilizator = dataSnapshot.getValue(Utilizator.class);

                if(utilizator.getNume().equals("")){

                    tvNume.setText("Nume: necompletat");
                    tvPrenume.setText("Prenume: necompletat");
                    tvVarsta.setText("Varsta: necompletat");
                    tvCNP.setText("CNP: necompletat");
                    tvNrTelefon.setText("Nr. telefon: necompletat");

                }
                else {
                    tvNume.setText("Nume:         "+utilizator.getNume());
                    tvPrenume.setText("Prenume:    " +utilizator.getPrenume());
                    tvVarsta.setText("Varsta:        " +utilizator.getVarsta());
                    tvCNP.setText("CNP:           " +utilizator.getcnp());
                    tvNrTelefon.setText("Nr. telefon: " +utilizator.getNrTelefon());
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

    @Override
    public void onBackPressed()
    {
        Intent intent = new Intent(this, LogareEmail.class);
        startActivity(intent);
    }

    public void goToModificaDate(View view)
    {
        Intent intent = new Intent(this, ModificaDate.class);
        startActivity(intent);
    }

}
