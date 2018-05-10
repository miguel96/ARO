package com.app.aro.FindKhana;

import android.net.Uri;

/**
 * Created by miguel on 10/05/18.
 */

public class UserToRegister {
    String name;
    String email;
    Uri photoUrl;
    String uid;

    UserToRegister(String name,String email,Uri photoUrl,String uid) {
        this.name=name;
        this.email=email;
        this.photoUrl=photoUrl;
        this.uid=uid;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getUid() {
        return uid;
    }

    public Uri getPhotoUrl() {
        return photoUrl;
    }
}
