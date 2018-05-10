package com.app.aro.FindKhana;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;
import com.google.firebase.auth.GoogleAuthProvider;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    private static final int RC_SIGN_IN = 9001;
    Retrofit retrofit;
    LoginService loginService;
    ObjectsApplication objects;
    private FirebaseAuth mAuth;
    private GoogleSignInClient mGoogleSignInClient;
    private static final String TAG = "GoogleActivity";

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Snackbar.make(findViewById(R.id.layoutHistoria), "Authentication Failed.", Snackbar.LENGTH_SHORT).show();
                            updateUI(null);
                        }

                        // ...
                    }
                });
    }

    private void updateUI(FirebaseUser usuario){
        Intent intent = new Intent(LoginActivity.this, MenuHistoriasActivity.class);
        usuario.getIdToken(true).addOnSuccessListener(new OnSuccessListener<GetTokenResult>() {
            @Override
            public void onSuccess(GetTokenResult getTokenResult) {
                String idToken=getTokenResult.getToken();
                loginOrRegister(idToken);
            }
        });

    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        FirebaseApp.initializeApp(this);
        mAuth = FirebaseAuth.getInstance();
        objects = (ObjectsApplication)getApplication();
        Context context = getApplicationContext();
        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        String serverClientId = getString(R.string.server_client_id);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestIdToken(serverClientId)
                //.requestId()
                //.requestServerAuthCode(serverClientId, true)//TRUE force to get refresh_token all time
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        findViewById(R.id.sign_in_button).setOnClickListener(this);

        //Start retrofit
        retrofit=new Retrofit.Builder()
                .baseUrl(getString(R.string.hostBasePath))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        loginService = retrofit.create(LoginService.class);

        signIn();

    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser!=null) {
            updateUI(currentUser);
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
            try {
                GoogleSignInAccount account=task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch(ApiException e) {
                Log.w("Error", "signInResult: error code="+e.getStatusCode()+"\nError: "+e.toString());
            }
        }
    }


    private void signIn() {
        Intent signInIntent=mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void registerUser() {
        FirebaseUser user =mAuth.getCurrentUser();
        if (user != null) {
            // Name, email address, and profile photo Url
            String name = user.getDisplayName();
            String email = user.getEmail();
            Uri photoUrl = user.getPhotoUrl();
            // Check if user's email is verified
            boolean emailVerified = user.isEmailVerified();
            String uid = user.getUid();
            UserToRegister userToRegister=new UserToRegister(name,email,photoUrl,uid);
            registerOnServer(userToRegister);
        }
    }

    private void registerOnServer(UserToRegister user) {
        Call<User> call = loginService.registerUser(user);
        call.enqueue(new Callback<User>(){
            @Override
            public void onResponse(Call<User> call, Response<User> userInfo) {
                System.out.println("HERE");
                if(userInfo.code()<299){
                    System.out.println("AQUI");
                    System.out.println(userInfo.body().toString());
                    Intent intent = new Intent(LoginActivity.this, MenuHistoriasActivity.class);
                    objects.usuario = userInfo.body();
                    startActivity(intent);
                    System.out.println("AHORA AQUI");
                } else {
                    System.out.println("Error"+userInfo.code());
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                try {
                    System.out.println("ERROR ON FAILURE");
                    throw t;
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
                Toast.makeText(getApplicationContext(),"Error al logearte"+call,Toast.LENGTH_SHORT);
            }
        });
        Toast.makeText(getApplicationContext(),"Estás logeandote por favor espera",Toast.LENGTH_SHORT);
    }

    private void loginOrRegister(String token) {
        Call<User> call = loginService.loginUser(new Token(token));
        call.enqueue(new Callback<User>(){
            @Override
            public void onResponse(Call<User> call, Response<User> userInfo) {
                if(userInfo.code()==200){
                    System.out.println(userInfo.body().toString());
                    Intent intent = new Intent(LoginActivity.this, MenuHistoriasActivity.class);
                    objects.usuario = userInfo.body();
                    startActivity(intent);
                } else if(userInfo.code()==404){
                    System.out.println("Error 404");
                    registerUser();
                } else {
                    System.out.println("Error"+userInfo.code());
                }
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
