package com.example.dany.icar;



import android.content.Intent;
import android.os.Bundle;

import android.support.annotation.NonNull;

import android.text.TextUtils;

import android.util.Log;

import android.view.View;

import android.widget.EditText;

import android.widget.TextView;

import android.widget.Toast;



import com.google.android.gms.tasks.OnCompleteListener;

import com.google.android.gms.tasks.Task;

import com.google.firebase.auth.AuthResult;

import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class LogareEmail extends BaseActivity implements

        View.OnClickListener {



    private static final String TAG = "EmailPassword";



    private TextView mStatusTextView;

    private EditText mEmailField;

    private EditText mPasswordField;


    private DatabaseReference mDatabase;


    // [START declare_auth]

    private FirebaseAuth mAuth;

    // [END declare_auth]



    @Override

    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_logare_email);



        // Views

        mStatusTextView = findViewById(R.id.tvStatus);

        mEmailField = findViewById(R.id.etEmail);

        mPasswordField = findViewById(R.id.etPass);



        // Buttons

        findViewById(R.id.btnLogareEmail).setOnClickListener(this);

        findViewById(R.id.btnCreareCont).setOnClickListener(this);

        findViewById(R.id.btnIesireCont).setOnClickListener(this);

        findViewById(R.id.btnVerificareEmail).setOnClickListener(this);



        // [START initialize_auth]

        mAuth = FirebaseAuth.getInstance();

        // [END initialize_auth]

        mDatabase = FirebaseDatabase.getInstance().getReference();

    }



    // [START on_start_check_user]

    @Override

    public void onStart() {

        super.onStart();

        // Check if user is signed in (non-null) and update UI accordingly.

        FirebaseUser currentUser = mAuth.getCurrentUser();

        updateUI(currentUser);

    }

    // [END on_start_check_user]

    @Override
    public void onBackPressed()
    {
        Intent intent=new Intent(this,MeniulPrincipal.class);
        startActivity(intent);
    }


    private void createAccount(String email, String password) {

        Log.d(TAG, "createAccount:" + email);

        if (!validateForm()) {

            return;

        }



        showProgressDialog();



        // [START create_user_with_email]

        mAuth.createUserWithEmailAndPassword(email, password)

                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {

                    @Override

                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {

                            // Sign in success, update UI with the signed-in user's information

                            Log.d(TAG, "createUserWithEmail:success");

                            FirebaseUser user = mAuth.getCurrentUser();

                            updateUI(user);

                            Utilizator utilizator = new Utilizator();

                            mDatabase.child("utilizator").child(user.getUid()).setValue(utilizator.toMap());

                            //String [] email=mAuth.getCurrentUser().getEmail().split("@");

                            //String [] domain=email[1].split(".");

                            //mDatabase.child("utilizator").child(email[0]+'_'+domain[0]).setValue("Anonim");




                        } else {

                            // If sign in fails, display a message to the user.

                            Log.w(TAG, "createUserWithEmail:failure", task.getException());

                            Toast.makeText(LogareEmail.this, "User already registered.",

                                    Toast.LENGTH_SHORT).show();

                            updateUI(null);


                        }



                        // [START_EXCLUDE]

                        hideProgressDialog();

                        // [END_EXCLUDE]

                    }

                });

        // [END create_user_with_email]

    }



    private void signIn(String email, String password) {

        Log.d(TAG, "signIn:" + email);

        if (!validateForm()) {

            return;

        }



        showProgressDialog();



        // [START sign_in_with_email]

        mAuth.signInWithEmailAndPassword(email, password)

                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {

                    @Override

                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {

                            // Sign in success, update UI with the signed-in user's information

                            Log.d(TAG, "signInWithEmail:success");

                            FirebaseUser user = mAuth.getCurrentUser();

                            updateUI(user);

                        } else {

                            // If sign in fails, display a message to the user.

                            Log.w(TAG, "signInWithEmail:failure", task.getException());

                            Toast.makeText(LogareEmail.this, "Authentication failed.",

                                    Toast.LENGTH_SHORT).show();

                            updateUI(null);

                        }



                        // [START_EXCLUDE]

                        if (!task.isSuccessful()) {

                            mStatusTextView.setText("Autentificare esuata!");

                        }

                        hideProgressDialog();

                        // [END_EXCLUDE]

                    }

                });

        // [END sign_in_with_email]

    }



    private void signOut() {

        mAuth.signOut();

        MeniulPrincipal.utilizatorLogat = false;

        updateUI(null);

    }



    private void sendEmailVerification() {

        // Disable button

        findViewById(R.id.btnVerificareEmail).setEnabled(false);



        // Send verification email

        // [START send_email_verification]

        final FirebaseUser user = mAuth.getCurrentUser();

        user.sendEmailVerification()

                .addOnCompleteListener(this, new OnCompleteListener<Void>() {

                    @Override

                    public void onComplete(@NonNull Task<Void> task) {

                        // [START_EXCLUDE]

                        // Re-enable button

                        findViewById(R.id.btnVerificareEmail).setEnabled(true);



                        if (task.isSuccessful()) {

                            Toast.makeText(LogareEmail.this,

                                    "Verification email sent to " + user.getEmail(),

                                    Toast.LENGTH_SHORT).show();

                        } else {

                            Log.e(TAG, "sendEmailVerification", task.getException());

                            Toast.makeText(LogareEmail.this,

                                    "Failed to send verification email.",

                                    Toast.LENGTH_SHORT).show();

                        }

                        // [END_EXCLUDE]

                    }

                });

        // [END send_email_verification]

    }



    private boolean validateForm() {

        boolean valid = true;



        String email = mEmailField.getText().toString();

        if (TextUtils.isEmpty(email)) {

            mEmailField.setError("Required.");

            valid = false;

        } else {

            mEmailField.setError(null);

        }



        String password = mPasswordField.getText().toString();

        if (TextUtils.isEmpty(password)) {

            mPasswordField.setError("Required.");

            valid = false;

        } else {

            mPasswordField.setError(null);

        }



        return valid;

    }



    private void updateUI(FirebaseUser user) {

        hideProgressDialog();

        if (user != null) {

            mStatusTextView.setText(getString(R.string.emailpassword_status_fmt,

                    user.getEmail(), user.isEmailVerified()));




            findViewById(R.id.btnLogareEmail).setVisibility(View.GONE);

            findViewById(R.id.btnCreareCont).setVisibility(View.GONE);

            findViewById(R.id.etEmail).setVisibility(View.GONE);

            findViewById(R.id.etPass).setVisibility(View.GONE);

            findViewById(R.id.btnIesireCont).setVisibility(View.VISIBLE);

            findViewById(R.id.btnVerificareEmail).setVisibility(View.VISIBLE);

            findViewById(R.id.btnRezervarileMele).setVisibility(View.VISIBLE);

            findViewById(R.id.btnDateUtilizator).setVisibility(View.VISIBLE);

            findViewById(R.id.btnVerificareEmail).setEnabled(!user.isEmailVerified());

        } else {

            mStatusTextView.setText(R.string.signed_out);



            findViewById(R.id.btnLogareEmail).setVisibility(View.VISIBLE);

            findViewById(R.id.btnCreareCont).setVisibility(View.VISIBLE);

            findViewById(R.id.etEmail).setVisibility(View.VISIBLE);

            findViewById(R.id.etPass).setVisibility(View.VISIBLE);

            findViewById(R.id.btnIesireCont).setVisibility(View.GONE);

            findViewById(R.id.btnVerificareEmail).setVisibility(View.GONE);

            findViewById(R.id.btnRezervarileMele).setVisibility(View.GONE);

            findViewById(R.id.btnDateUtilizator).setVisibility(View.GONE);


        }

    }



    @Override

    public void onClick(View v) {

        int i = v.getId();

        if (i == R.id.btnCreareCont) {

            createAccount(mEmailField.getText().toString(), mPasswordField.getText().toString());

        } else if (i == R.id.btnLogareEmail) {

            signIn(mEmailField.getText().toString(), mPasswordField.getText().toString());

        } else if (i == R.id.btnIesireCont) {

            signOut();

        } else if (i == R.id.btnVerificareEmail) {

            sendEmailVerification();

        }

    }

    public void gotoRezervarileMele(View view){
        Intent intent = new Intent(this, RezervarileMele.class);
        startActivity(intent);
    }

    public void gotoDateUtilizator (View view)
    {
        Intent intent = new Intent(this, DateUtilizator.class);
        startActivity(intent);
    }

}