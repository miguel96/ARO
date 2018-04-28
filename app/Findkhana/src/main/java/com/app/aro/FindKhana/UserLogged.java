package com.app.aro.FindKhana;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class UserLogged extends AppCompatActivity {
    User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_logged);
        user = (User) getIntent().getExtras().getParcelable("user");
        TextView userText=findViewById(R.id.userInfo);
        userText.setText(user.toString());
    }
}
