package com.example.dany.icar;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Node;

import java.util.ArrayList;
import java.util.List;




public class MeniulPrincipal extends AppCompatActivity {

    static ArrayList<Masina> masini;
    TextView tvMesaj;
    Button btnRezervare;
    ImageView imgV_Rezervare;
    static boolean utilizatorLogat;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meniul_principal);

        tvMesaj = (TextView) findViewById(R.id.tvMesaj) ;
       // btnRezervare=(Button) findViewById(R.id.btnRezervare);
        imgV_Rezervare = (ImageView) findViewById(R.id.imgV_Rezervare);

        mAuth = FirebaseAuth.getInstance();

        masini = new ArrayList<>();
        String mDrawableName = "fordfiesta";
        int resID = getResources().getIdentifier(mDrawableName, "drawable", getPackageName());
        String mDrawableName1 = "bmw5";
        int resID1 = getResources().getIdentifier(mDrawableName1, "drawable", getPackageName());
        String mDrawableName2 = "audia6";
        int resID2 = getResources().getIdentifier(mDrawableName2, "drawable", getPackageName());
        String mDrawableName3 = "mercedese";
        int resID3 = getResources().getIdentifier(mDrawableName3, "drawable", getPackageName());
        String mDrawableName4 = "fordfocus";
        int resID4 = getResources().getIdentifier(mDrawableName4, "drawable", getPackageName());
        String mDrawableName5 = "mercedesvito";
        int resID5 = getResources().getIdentifier(mDrawableName5, "drawable", getPackageName());
        String mDrawableName6 = "opelinsigna";
        int resID6 = getResources().getIdentifier(mDrawableName6, "drawable", getPackageName());
        String mDrawableName7 = "dacialogan";
        int resID7 = getResources().getIdentifier(mDrawableName7, "drawable", getPackageName());
        String mDrawableName8 = "daciaduster";
        int resID8 = getResources().getIdentifier(mDrawableName8, "drawable", getPackageName());
        String mDrawableName9 = "bmwx3";
        int resID9 = getResources().getIdentifier(mDrawableName9, "drawable", getPackageName());

        masini.add(new Masina("Ford Fiesta", "Diesel", "1.6", "Incepand de la 100 RON/zi", resID, 100));
        masini.add(new Masina("Bmw Seria5", "Diesel", "2.0", "Incepand de la 200 RON/zi", resID1,250));
        masini.add(new Masina("Audi A6", "Diesel", "2.0", "Incepand de la 200 RON/zi", resID2,225));
        masini.add(new Masina("Mercedes E", "Diesel", "2.2", "Incepand de la 220 RON/zi", resID3,275));
        masini.add(new Masina("Ford Focus", "Diesel", "1.9", "Incepand de la 150 RON/zi", resID4,150));
        masini.add(new Masina("Mercedes Vito", "Diesel", "2.5", "Incepand de la 150 RON/zi", resID5,300));
        masini.add(new Masina("Opel Insigna", "Diesel", "1.9", "Incepand de la 170 RON/zi", resID6,150));
        masini.add(new Masina("Dacia Logan", "Diesel", "1.6", "Incepand de la 90 RON/zi", resID7,250));
        masini.add(new Masina("Dacia Duster", "Diesel", "1.9", "Incepand de la 130 RON/zi", resID8,150));
        masini.add(new Masina("Bmw X3", "Diesel", "3.0", "Incepand de la 200 RON/zi", resID9,200));


    }


    @Override

    public void onStart() {

        super.onStart();

        // Check if user is signed in (non-null) and update UI accordingly.

        FirebaseUser currentUser = mAuth.getCurrentUser();

        if(currentUser != null) {
            utilizatorLogat = true;
            tvMesaj.setText(currentUser.getEmail().toString());

        }
        else{
            utilizatorLogat = false;
           // btnRezervare.setVisibility(View.GONE);
            imgV_Rezervare.setVisibility(View.GONE);

        }


    }

    public void gotoFormular(View view)
    {
        Intent intent = new Intent(this, FormularRezervare.class);
        startActivity(intent);
    }

    public void gotoListaMasini(View view)
    {
        Intent intent = new Intent(this, ListaMasini.class);
        startActivity(intent);
    }


    public void gotoLogareEmail(View view)
    {
        Intent intent = new Intent(this, LogareEmail.class);
        startActivity(intent);

    }

    public void gotoLocatiileNoastre(View view)
    {
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
    }


    public void btnAutentificare(View view)
    {
        /*
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("utilizatori/"+etUtilizator.getText().toString());
        DataSnapshot dataSnapshot=new DataSnapshot(myRef);
        String parola=dataSnapshot.getValue(String.class);
        Toast.makeText(this,myRef.get,Toast.LENGTH_LONG).show();
    */
    }
}
