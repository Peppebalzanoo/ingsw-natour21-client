package com.example.natour2.auth;

import static android.content.ContentValues.TAG;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;


import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoDevice;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUser;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserAttributes;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserCodeDeliveryDetails;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserPool;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserSession;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.AuthenticationContinuation;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.AuthenticationDetails;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.ChallengeContinuation;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.MultiFactorAuthenticationContinuation;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.AuthenticationHandler;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.GenericHandler;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.SignUpHandler;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.util.CognitoJWTParser;
import com.amazonaws.regions.Regions;
import com.auth0.android.jwt.Claim;
import com.auth0.android.jwt.JWT;
import com.example.natour2.MainActivity;
import com.example.natour2.controller.ControllerHomeActivity;
import com.example.natour2.controller.ControllerItinerary;
import com.example.natour2.controller.ControllerLoginSignin;
import com.example.natour2.controller.ControllerUser;
import com.example.natour2.utilities.SharedPreferencesUtil;

import org.json.JSONException;

public class Cognito {


    private String userPoolUsersId = " ";
    private String clientId = " ";
    private String clientSecret= " ";
    private Regions cognitoRegion =  ;
    private CognitoUserPool userPoolUsers;
    private CognitoUserAttributes userAttributes;
    private Context context;
    private Activity activity;
    private String userPassword;


    private CognitoUserPool userPoolAdmins;
    private CognitoUserAttributes adminAttributes;
    private String adminPassword;

    private final ControllerLoginSignin ctrl = ControllerLoginSignin.getInstance();
    private final ControllerUser ctrlUser = ControllerUser.getInstance();
    static String tokenUser;
    static String tokenAdmin;

    //**********************************************************************************************
    public Cognito(Context context, Activity activity){
        this.context = context;
        userPoolUsers = new CognitoUserPool(context, this.userPoolUsersId, this.clientId, this.clientSecret, this.cognitoRegion);
        userAttributes = new CognitoUserAttributes();
        this.activity = activity;
        ctrlUser.setActivity(activity);
        ctrlUser.setContext(context);
    }

    public void addAttributes(String key, String value){
        userAttributes.addAttribute(key, value);
    }

    //**********************************************************************************************
    //SIGN-UP UTENTE - REGISTRAZIONE
    public void userSignUpInBackground(String userId, String password){
        userPoolUsers.signUpInBackground(userId, password, this.userAttributes, null, signUpCallBack);
    }

    SignUpHandler signUpCallBack = new SignUpHandler() {


        @Override
        public void onSuccess(CognitoUser user, boolean signUpConfirmationState, CognitoUserCodeDeliveryDetails cognitoUserCodeDeliveryDetails) {
            ctrl.showVerifyCodeFragment();
            //Log.i(TAG, "----------------------------------------------------------------------- [COGNITO]: REGISTRAZIONE EFFETTUATA CORRETTAMENTE!");
        }

        @Override
        public void onFailure(Exception exception) {
            Toast.makeText(context,"Registrazione fallita!", Toast.LENGTH_LONG).show();
        }
    };


    //LOGIN UTENTE - ACCESSO
    public void userLogIn(String userId, String password){
        if(!userId.isEmpty() && !password.isEmpty()) {

            // Callback handler for the sign-in process
            AuthenticationHandler authenticationHandler = new AuthenticationHandler() {
                @Override
                public void authenticationChallenge(ChallengeContinuation continuation) { }

                @Override
                public void onSuccess(CognitoUserSession userSession, CognitoDevice newDevice) {
                    Toast.makeText(context,"Accesso effettuato!", Toast.LENGTH_LONG).show();

                    String token = userSession.getAccessToken().getJWTToken();
                    String idToken = userSession.getIdToken().getJWTToken();
                    String refreshToken = userSession.getRefreshToken().getToken();
                    SharedPreferencesUtil.setStringPreference(activity, "IDTOKEN", idToken);
                    SharedPreferencesUtil.setStringPreference(activity, "TOKEN", token);
                    SharedPreferencesUtil.setStringPreference(activity, "REFRESHTOKEN", refreshToken);

                    ctrlUser.setUser(idToken);
                    String group = null;
                    try {
                        group = CognitoJWTParser.getPayload(idToken).getString("cognito:groups");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    if(group == null){
                        ctrl.showHomeActivity(context);
                    }
                    else if(group.equals("[\"admin\"]")){
                            ctrl.showHomeAdminActivity(context);
                    } else {
                        ctrl.showHomeActivity(context);
                    }
                    tokenUser = userSession.getIdToken().getJWTToken();
                }

                @Override
                public void getAuthenticationDetails(AuthenticationContinuation authenticationContinuation, String userId) {
                    // The API needs user sign-in credentials to continue
                    AuthenticationDetails authenticationDetails = new AuthenticationDetails(userId, userPassword, null);

                    // Pass the user sign-in credentials to the continuation
                    authenticationContinuation.setAuthenticationDetails(authenticationDetails);

                    // Allow the sign-in to continue
                    authenticationContinuation.continueTask();
                }

                @Override
                public void getMFACode(MultiFactorAuthenticationContinuation multiFactorAuthenticationContinuation) {
                    // Multi-factor authentication is required; get the verification code from user
                    //multiFactorAuthenticationContinuation.setMfaCode(mfaVerificationCode);
                    // Allow the sign-in process to continue
                    //multiFactorAuthenticationContinuation.continueTask();
                }

                @Override
                public void onFailure(Exception exception) {
                    // Sign-in failed, check exception for the cause
                    Toast.makeText(context,"Accesso fallito!", Toast.LENGTH_LONG).show();
                }
            };
            CognitoUser cognitoUser = userPoolUsers.getUser(userId);
            this.userPassword = password;
            cognitoUser.getSessionInBackground(authenticationHandler);

        }else{
            Toast.makeText(context,"Username o Password non validi!", Toast.LENGTH_LONG).show();
        }
    }






    public void firstUserLogIn(String userId, String password){
        if(!userId.isEmpty() && !password.isEmpty()) {

            // Callback handler for the sign-in process
            AuthenticationHandler authenticationHandler = new AuthenticationHandler() {
                @Override
                public void authenticationChallenge(ChallengeContinuation continuation) { }

                @Override
                public void onSuccess(CognitoUserSession userSession, CognitoDevice newDevice) {
                    Toast.makeText(context,"Accesso effettuato!", Toast.LENGTH_LONG).show();
                    String token = userSession.getAccessToken().getJWTToken();
                    String idToken = userSession.getIdToken().getJWTToken();
                    String refreshToken = userSession.getRefreshToken().getToken();
                    SharedPreferencesUtil.setStringPreference(activity, "IDTOKEN", idToken);
                    SharedPreferencesUtil.setStringPreference(activity, "TOKEN", token);
                    SharedPreferencesUtil.setStringPreference(activity, "REFRESHTOKEN", refreshToken);
                    ctrlUser.userSignUp();
                    ctrlUser.setUser(idToken);
                    ctrlUser.setSubscribe();
                    String group = null;
                    try {
                        group = CognitoJWTParser.getPayload(idToken).getString("cognito:groups");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    if(group == null){
                        ctrl.showHomeActivity(context);
                    }
                    else if(group.equals("[\"admin\"]")){
                        ctrl.showHomeAdminActivity(context);
                    } else {
                        ctrl.showHomeActivity(context);
                    }
                    tokenUser = userSession.getIdToken().getJWTToken();
                }

                @Override
                public void getAuthenticationDetails(AuthenticationContinuation authenticationContinuation, String userId) {
                    // The API needs user sign-in credentials to continue
                    AuthenticationDetails authenticationDetails = new AuthenticationDetails(userId, userPassword, null);

                    // Pass the user sign-in credentials to the continuation
                    authenticationContinuation.setAuthenticationDetails(authenticationDetails);

                    // Allow the sign-in to continue
                    authenticationContinuation.continueTask();
                }

                @Override
                public void getMFACode(MultiFactorAuthenticationContinuation multiFactorAuthenticationContinuation) {
                    // Multi-factor authentication is required; get the verification code from user
                    //multiFactorAuthenticationContinuation.setMfaCode(mfaVerificationCode);
                    // Allow the sign-in process to continue
                    //multiFactorAuthenticationContinuation.continueTask();
                }

                @Override
                public void onFailure(Exception exception) {
                    // Sign-in failed, check exception for the cause
                    Toast.makeText(context,"Accesso fallito!", Toast.LENGTH_LONG).show();
                }
            };
            CognitoUser cognitoUser = userPoolUsers.getUser(userId);
            this.userPassword = password;
            cognitoUser.getSessionInBackground(authenticationHandler);

        }else{
            Toast.makeText(context,"Username o Password non validi!", Toast.LENGTH_LONG).show();
        }
    }

    //**********************************************************************************************
    //VERICATION USER - VERIFICA UTENTE
    public void confirmVerificationCodeUser(String userId, String codiceDiVerifica){
        CognitoUser cognitoUser = userPoolUsers.getUser(userId);
        cognitoUser.confirmSignUpInBackground(codiceDiVerifica, false, verificationCodeCallBack);
    }

    GenericHandler verificationCodeCallBack = new GenericHandler() {
        @Override
        public void onSuccess() {
            ctrl.firstLogin();
            Toast.makeText(context,"Verificata Completata!", Toast.LENGTH_LONG).show();
        }

        @Override
        public void onFailure(Exception exception) {
            Toast.makeText(context,"Verifica Fallita!", Toast.LENGTH_LONG).show();
        }
    };
    //**********************************************************************************************
    public void forgetPasswordCognito(String userid, String vecchiaPassword, String nuovaPassword){
        CognitoUser cognitoUser = userPoolUsers.getUser(userid);
        cognitoUser.changePasswordInBackground(vecchiaPassword, nuovaPassword, forgotPasswordHandler);
    }

    GenericHandler forgotPasswordHandler = new GenericHandler() {
        @Override
        public void onSuccess() {
            Toast.makeText(context,"Password Modificata Correttamente!", Toast.LENGTH_LONG).show();
            ctrl.showLoginFragment();
        }

        @Override
        public void onFailure(Exception exception) {
            Toast.makeText(context,"Errore Modifca Password", Toast.LENGTH_LONG).show();
        }
    };

    public CognitoUserAttributes getCongitoUserAttributi(){
        return userAttributes;
    }
    public String getUserPoolId(){
        return userPoolUsersId;
    }
    public String getClientId() {
        return clientId;
    }
    public String getClientSecret() {
        return clientSecret;
    }
    public Regions getCognitoRegion() {
        return cognitoRegion;
    }
    public Context getContext() {
        return context;
    }


}
