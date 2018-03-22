package com.example.ainhoa.app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

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
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    private static final int RC_SIGN_IN = 9001;
    Retrofit retrofit;

    private GoogleSignInClient mGoogleSignInClient;

    private void loadUserInfo(String id) {
        this.retrofit=new Retrofit.Builder()
                .baseUrl(getString(R.string.hostBasePath))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        LoginService loginService = retrofit.create(LoginService.class);
        Call<User> call = loginService.loginUserGoogleId(new Token(id));
        call.enqueue(new Callback<User>(){
            @Override
            public void onResponse(Call<User> call, Response<User> userInfo) {
                System.out.println(userInfo.body());
                System.out.println("OK");
                Intent intent = new Intent(LoginActivity.this, UserLogged.class);
                intent.putExtra("user", userInfo.body());
                System.out.println(userInfo.toString());
                startActivity(intent);
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                try {
                    throw t;
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
                Toast.makeText(getApplicationContext(),"Error al logearte"+call,Toast.LENGTH_SHORT);
            }
        });
        Toast.makeText(getApplicationContext(),"Estás logeandote por favor espera",Toast.LENGTH_SHORT);
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Check if user has logged before
        GoogleSignInAccount account= GoogleSignIn.getLastSignedInAccount(this);
        if(account!=null) {
            System.out.println("OK");
            loadUserInfo(account.getId());
        }

        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        String serverClientId = getString(R.string.server_client_id);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                //.requestEmail()
                //.requestIdToken(getString(R.string.server_client_id))
                //.requestId()
                .requestServerAuthCode(serverClientId, true)//TRUE force to get refresh_token all time
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        findViewById(R.id.sign_in_button).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sign_in_button:
                signIn();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        GoogleSignInAccount account= GoogleSignIn.getLastSignedInAccount(this);
        if(account!=null){
            String serverAuthCode=account.getServerAuthCode();
            // If logs and havent a server auth code we dont want to make request
            if(serverAuthCode!=null){
                System.out.println(serverAuthCode);
                Toast.makeText(getApplicationContext(),"Estás logeandote por favor espera"+account,Toast.LENGTH_SHORT);
                sendTokenToServer(account.getServerAuthCode());
            }
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode== RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }


    private void signIn() {
        Intent signInIntent=mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            //String idToken= account.getIdToken();
            String authCode = account.getServerAuthCode();
            sendTokenToServer(authCode);
        } catch (ApiException e){
            Log.w("Error", "signInResult: failed code="+e.getStatusCode()+"Error:"+e);
        }
    }

    private void sendTokenToServer(String token) {
        if(token==null){
            System.out.println("null token");
        }

        this.retrofit=new Retrofit.Builder()
                .baseUrl(getString(R.string.hostBasePath))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        LoginService loginService = retrofit.create(LoginService.class);
        Call<User> call = loginService.loginUser(new Token(token));
        call.enqueue(new Callback<User>(){
            @Override
            public void onResponse(Call<User> call, Response<User> userInfo) {
                System.out.println(userInfo.body());
                System.out.println("OK");
                Intent intent = new Intent(LoginActivity.this, UserLogged.class);
                intent.putExtra("user", userInfo.body());
                System.out.println(userInfo.toString());
                startActivity(intent);
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                try {
                    throw t;
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
                Toast.makeText(getApplicationContext(),"Error al logearte"+call,Toast.LENGTH_SHORT);
            }
        });
        Toast.makeText(getApplicationContext(),"Estás logeandote por favor espera",Toast.LENGTH_SHORT);
    }

}
