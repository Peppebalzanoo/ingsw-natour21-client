package com.example.natour2.auth;

import android.content.Context;
import android.net.Uri;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

public class Google {

    private Context contex;
    private String personName;
    private String personGivenName;
    private String personFamilyName;
    private String personEmail;
    private String personId;
    private String idToken;
    private Uri personPhoto;

    private GoogleSignInAccount account;
    private GoogleSignInClient mGoogleSignInClient;
    private GoogleSignInOptions mGso;
    public static int RC_SIGN_IN = 100;


    public Google(){}

    public Google(Context context){
        // Configure sign-in to request the user's ID, email address, and basic profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        mGso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        //.requestIdToken("getString(R.string.server_client_id)")

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(context, mGso);
    }


    //  Check for existing Google Sign In account, if the user is already signed in the GoogleSignInAccount will be non-null.
    public boolean checkAlreadySignIn(Context context){
        account = GoogleSignIn.getLastSignedInAccount(context);
        if(account != null){
            return true;
        }
        return false;
    }

    public void storeInformationAccount(GoogleSignInAccount acct) {
        this.personName = acct.getDisplayName();
        this.personGivenName = acct.getGivenName();
        this.personFamilyName = acct.getFamilyName();
        this.personEmail = acct.getEmail();
        this.personId = acct.getId();
        this.personPhoto = acct.getPhotoUrl();
        this.idToken = acct.getIdToken();
    }

    public GoogleSignInClient getGoogleSignInClient(){
        return this.mGoogleSignInClient;
    }

    public void setGoogleSignInClient(GoogleSignInAccount googleAccount){
        this.account = googleAccount;
    }

}
