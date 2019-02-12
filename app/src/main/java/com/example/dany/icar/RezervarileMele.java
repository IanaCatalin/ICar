package com.example.dany.icar;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class RezervarileMele extends AppCompatActivity {

    private static final String TAG = "RezervarileMeleActivity";

    private DatabaseReference mRezervariReference;

    private FirebaseAuth mAuth;

    ArrayList<String> listaRezervari;

    ListView lvRezervarileMele;

    ArrayAdapter<String> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rezervarile_mele);

        listaRezervari = new ArrayList<String>();

        mRezervariReference = FirebaseDatabase.getInstance().getReference().child("rezervare");

        mAuth = FirebaseAuth.getInstance();

        final List<String> listaIdRezervare = new ArrayList<>();

        lvRezervarileMele = (ListView) findViewById(R.id.lvRezervarileMele);



/*
        ValueEventListener postListener = new ValueEventListener() {

            @Override

            public void onDataChange(DataSnapshot dataSnapshot) {

                // Get Post object and use the values to update the UI


                Rezervare rezervare = dataSnapshot.getValue(Rezervare.class);

                listaRezervari.add(rezervare.toString());

                lvRezervarileMele = (ListView) findViewById(R.id.lvRezervarileMele);
                final ArrayAdapter<String> adapter = new ArrayAdapter<String>(RezervarileMele.this, android.R.layout.simple_list_item_1, listaRezervari);
                lvRezervarileMele.setAdapter(adapter);


            }


            @Override

            public void onCancelled(DatabaseError databaseError) {

                // Getting Post failed, log a message

                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());

                // [START_EXCLUDE]

                Toast.makeText(RezervarileMele.this, "Failed to load post.",

                        Toast.LENGTH_SHORT).show();

                // [END_EXCLUDE]

            }

        };

        mRezervariReference.addValueEventListener(postListener);
*/
        FirebaseUser currentUser = mAuth.getCurrentUser();

        Query queryRef = mRezervariReference.orderByChild("email").equalTo(currentUser.getEmail().toString());

        //ArrayAdapter<String> adapter;

        ChildEventListener childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChild) {
                System.out.println(dataSnapshot.getValue());
                Rezervare rezervare = dataSnapshot.getValue(Rezervare.class);

                listaIdRezervare.add(dataSnapshot.getKey());

                listaRezervari.add(rezervare.toString());


                adapter = new ArrayAdapter<String>(RezervarileMele.this, android.R.layout.simple_list_item_1, listaRezervari);
                lvRezervarileMele.setAdapter(adapter);

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

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


        lvRezervarileMele.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {


                AlertDialog.Builder alertDialog = new AlertDialog.Builder(RezervarileMele.this);

                alertDialog.setCancelable(true);
                alertDialog.setTitle("Anulati rezervarea?");
                alertDialog.setMessage("Rezervarea va fi anulata. Sunteti sigur? \n");

                alertDialog.setPositiveButton("Da", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int e) {

                        DatabaseReference ref=FirebaseDatabase.getInstance().getReference().child("rezervare").child(listaIdRezervare.get(i));

                        ref.removeValue();

                        Toast.makeText(RezervarileMele.this, "Rezervarea a fost anulata cu succes!", Toast.LENGTH_SHORT).show();

                        listaRezervari.remove(i);

                        adapter.notifyDataSetChanged();
                    }
                });

                alertDialog.setNegativeButton("Nu", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int e) {

                    }
                });


                alertDialog.show();
            }
        });
    }
}


