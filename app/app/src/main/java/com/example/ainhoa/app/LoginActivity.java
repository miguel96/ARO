package com.example.ainhoa.app;


import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.amazonaws.http.HttpClient;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.BufferedOutputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    private static final int RC_SIGN_IN = 9001;

    private GoogleSignInClient mGoogleSignInClient;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestIdToken(getString(R.string.server_client_id))
                //.requestId()
                //.requestServerAuthCode(SERVER_TOKEN,true)//TRUE force to get refresh_token all time
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        findViewById(R.id.sign_in_button).setOnClickListener(this);

        /*
        buttonHistorias.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(LoginActivity.this, MenuHistoriasActivity.class);
                startActivity(intent);
            }
        });
        */
        /*
        final Button btnUbicaciones = findViewById(R.id.btnMapAdmin);
        buttonHistorias.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(LoginActivity.this, MapAdminActivity.class);
                startActivity(intent);
            }
        });
        */
    }

    @Override
    protected void onStart() {
        super.onStart();
        GoogleSignIn.silentSignIn().addOnCompleteListener(this,new OnCompleteListener<GoogleSignInAccount>(){
            @Override
            public void onComplete(@NonNull Task<GoogleSignInAccount> task) {
                handleSignInResult(task);
            }
        });
        GoogleSignInAccount account= GoogleSignIn.getLastSignedInAccount(this);
        updateUI(account);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode== RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    @Override
    public void onClick(View v){
        switch(v.getId()){
            case R.id.sign_in_button:
                signIn();
                break;
        }
    }

    private void signIn() {
        Intent signInIntent=mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            String idToken= account.getIdToken();
            //TODO send ID token to server and validate
            sendTokenToServer(idToken);
            updateUI(account);
        } catch (ApiException e){
            Log.w("Error", "signInResult: failed code="+e.getStatusCode()+"Error:"+e);
            updateUI(null);
        }
    }

    private void sendTokenToServer(String token) {
        try{
            URL url = new URL(getString(R.string.server_send_token));
            HttpURLConnection urlConnection=(HttpURLConnection) url.openConnection();
            urlConnection.setDoOutput(true);
            urlConnection.setChunkedStreamingMode(0);

            OutputStream out=new BufferedOutputStream(urlConnection.getOutputStream());
            out.write((token.getBytes(Charset.forName("UTF-8"))));
            urlConnection.disconnect();
        }catch (Exception err){
            Log.d("Error",err.toString());
        }
    }

    private void updateUI(GoogleSignInAccount account) {
        if(account==null) {
            System.out.println("user not logged");
            //TODO: show login
        } else {
            System.out.println("Logged"+account);
            Intent intent = new Intent(LoginActivity.this, MenuHistoriasActivity.class);
            startActivity(intent);
        }
    }

}
