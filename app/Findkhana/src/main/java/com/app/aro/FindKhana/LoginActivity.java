package com.app.aro.FindKhana;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.google.android.gms.tasks.Task;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    private static final int RC_SIGN_IN = 9001;
    Retrofit retrofit;
    ObjectsApplication objetos;
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
                Intent intent = new Intent(LoginActivity.this, MenuHistoriasActivity.class);
                objetos=(ObjectsApplication) getApplication();
                objetos.usuario=userInfo.body();
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
        objetos = (ObjectsApplication)getApplication();

        Context context = getApplicationContext();
        //TODO Borrar
        sendTokenToServer("auto");


        String googleUserId;
        googleUserId = context.getSharedPreferences(getString(R.string.preference_google_user_id), Context.MODE_PRIVATE).getString("googleUserId",null);
        System.out.println("***************************************");
        System.out.println(googleUserId);
        System.out.println("***************************************");

        if(googleUserId!=null) {
            loadUserInfo(googleUserId);
        } else {
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
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sign_in_button:
                signIn();
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
                Intent intent = new Intent(LoginActivity.this, MenuHistoriasActivity.class);
                objetos.usuario = userInfo.body();
                // We have to save user token on sharedPreferences
                objetos=(ObjectsApplication) getApplication();
                objetos.usuario=userInfo.body();
                System.out.println(objetos.usuario.toString());
                User user = userInfo.body();
                Context context = getApplicationContext();
                SharedPreferences.Editor editor= context.getSharedPreferences(getString(R.string.preference_google_user_id), Context.MODE_PRIVATE).edit();
                editor.putString("googleUserId",user.getGoogleId());
                System.out.println(editor.commit());
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
