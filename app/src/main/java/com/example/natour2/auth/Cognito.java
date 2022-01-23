package com.example.natour2.auth;

import static android.content.ContentValues.TAG;

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
import com.amazonaws.regions.Regions;
import com.example.natour2.controller.ControllerHomeActivity;
import com.example.natour2.controller.ControllerLoginSignin;

public class Cognito {


    //private String userPoolUsersId = "eu-west-3_40SDNNCeI";
    //private String clientId = "1fpi4iq7v0c9nauceoij11gnht";
    //private String clientSecret= "rdg4q87k7b750tpsk18mgh32hivu854h0mg1eahoa9r1m4ar2rf";

    private String userPoolUsersId = " ";
    private String clientId = " ";
    private String clientSecret= " ";
    private Regions cognitoRegion =  ;
    private CognitoUserPool userPoolUsers;
    private CognitoUserAttributes userAttributes;
    private Context context;
    private String userPassword;





    private String userPoolAdminsId = "eu-west-3_3VRktEGN7";
    private String clientIdAdmin = "78f9mtuk5q1n6t4r3129087ffr";
    private String clientSecretAdmin = "racb42nv6p31upep7pr3i42u669mr41avcj1g1gm1tqg3hv8ibj";
    private CognitoUserPool userPoolAdmins;
    private CognitoUserAttributes adminAttributes;
    private String adminPassword;

    private final ControllerLoginSignin ctrl = ControllerLoginSignin.getInstance();
    static String tokenUser;
    static String tokenAdmin;

    //pippo
    //Pippo90!



    //**********************************************************************************************
    public Cognito(Context context){
        this.context = context;
        userPoolUsers = new CognitoUserPool(context, this.userPoolUsersId, this.clientId, this.clientSecret, this.cognitoRegion);
        userAttributes = new CognitoUserAttributes();
    }

    public Cognito(Context context, String type){
        this.context = context;
        userPoolAdmins = new CognitoUserPool(context, this.userPoolAdminsId, this.clientIdAdmin, this.clientSecretAdmin, this.cognitoRegion);
        adminAttributes = new CognitoUserAttributes();
    }



    public void addAttributesAdmin(String key, String value){
        adminAttributes.addAttribute(key, value);
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
            Log.i(TAG, "----------------------------------------------------------------------- [COGNITO]: REGISTRAZIONE EFFETTUATA CORRETTAMENTE!");
        }

        @Override
        public void onFailure(Exception exception) {
            //Registrazione fallita, il metodo cattura l'eccezione che provoca il fallimento
            Toast.makeText(context,"Registrazione fallita!", Toast.LENGTH_LONG).show();
            Log.i(TAG, "----------------------------------------------------------------------- [COGNITO]: REGISTRAZIONE FALLITA EXCEPTION: "+exception);
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
                    Log.i(TAG, "----------------------------------------------------------------------- [COGNITO]: ACCESSO EFFETTUATO CORRETTAMENTE!");
                    ctrl.setUser(userSession.getIdToken().getJWTToken());
                    ctrl.showHomeActivity(context);
                    ControllerHomeActivity ctrl1 = ControllerHomeActivity.getInstance();
                    ctrl1.setToken(userSession.getIdToken().getJWTToken());
                    Log.i("COGNITO TOKEN ID USER","******************************************************** ID: "+userSession.getIdToken().getJWTToken());
                    Log.i("COGNITO TOKEN USER","******************************************************** ACCESS: "+userSession.getAccessToken().getJWTToken());
                    Log.i("COGNITO TOKEN USER","******************************************************** REFRESH.get: "+userSession.getRefreshToken().getToken());

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
                    Log.i(TAG, "----------------------------------------------------------------------- [COGNITO]: ACCESSO FALLITO EXCEPTION: "+exception.getLocalizedMessage());
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
            // User was successfully confirmed
            ctrl.showLoginFragment();
            Toast.makeText(context,"Verificata Completata!", Toast.LENGTH_LONG).show();
            Log.i(TAG, "----------------------------------------------------------------------- [COGNITO]: VERIFICA CODICE EFFETTUATA CORRETTAMENTE");

        }

        @Override
        public void onFailure(Exception exception) {
            // User confirmation failed. Check exception for the cause.
            Toast.makeText(context,"Verifica Fallita!", Toast.LENGTH_LONG).show();
            Log.i(TAG, "----------------------------------------------------------------------- [COGNITO]: VERIFICA CODICE FALLITA EXCEPTION: "+exception.getLocalizedMessage());

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
            Log.i(TAG, "----------------------------------------------------------------------- [COGNITO]: PASSWORD CAMBIATA CORRETTAMENTE!");
            ctrl.showLoginFragment();
        }

        @Override
        public void onFailure(Exception exception) {
            Toast.makeText(context,"Errore Modifca Password", Toast.LENGTH_LONG).show();
            Log.i(TAG, "----------------------------------------------------------------------- [COGNITO]: ERRORE CAMBIO PASSWORD!"+exception.getLocalizedMessage());
        }
    };

    //**********************************************************************************************
    //SIGN-UP ADMIN - REGISTRAZIONE
    public void adminSignUpInBackground(String userId, String password){
        userPoolAdmins.signUpInBackground(userId, password, this.adminAttributes, null, signUpCallBackAdmin);
    }

    SignUpHandler signUpCallBackAdmin = new SignUpHandler() {

        @Override
        public void onSuccess(CognitoUser user, boolean signUpConfirmationState, CognitoUserCodeDeliveryDetails cognitoUserCodeDeliveryDetails) {
            Log.i(TAG, "----------------------------------------------------------------------- [COGNITO]: REGISTRAZIONE EFFETTUATA CORRETTAMENTE!");

        }

        @Override
        public void onFailure(Exception exception) {
            //Registrazione fallita, il metodo cattura l'eccezione che provoca il fallimento
            Toast.makeText(context,"Registrazione fallita!", Toast.LENGTH_LONG).show();
            Log.i(TAG, "----------------------------------------------------------------------- [COGNITO]: REGISTRAZIONE FALLITA EXCEPTION: "+exception);
        }
    };
    //**********************************************************************************************

    public void adminLogIn(String matricolaAdmin, String passwordAdmin) {
        if(!matricolaAdmin.isEmpty()  && !passwordAdmin.isEmpty()){
            CognitoUser cognitoUserAdmin = userPoolAdmins.getUser(matricolaAdmin);
            cognitoUserAdmin.getSessionInBackground(authenticationHandlerAdmin);
            this.adminPassword = passwordAdmin;
        }else{
            Toast.makeText(context,"Username o Password non validi!", Toast.LENGTH_LONG).show();
        }
    }
    AuthenticationHandler authenticationHandlerAdmin = new AuthenticationHandler() {
        @Override
        public void onSuccess(CognitoUserSession userSession, CognitoDevice newDevice) {
            Toast.makeText(context,"Accesso effettuato!", Toast.LENGTH_LONG).show();
            Log.i(TAG, "----------------------------------------------------------------------- [COGNITO]: ACCESSO EFFETTUATO CORRETTAMENTE!");
            ctrl.showHomeAdminActivity(context);
            Log.i("COGNITO TOKEN ADMIN","******************************************************** COGNITO_TOKEN: "+userSession.getIdToken().getJWTToken());
            tokenAdmin = userSession.getIdToken().getJWTToken();
        }

        @Override
        public void getAuthenticationDetails(AuthenticationContinuation authenticationContinuation, String userId) {
            // The API needs user sign-in credentials to continue
            AuthenticationDetails authenticationDetails = new AuthenticationDetails(userId, adminPassword, null);

            // Pass the user sign-in credentials to the continuation
            authenticationContinuation.setAuthenticationDetails(authenticationDetails);

            // Allow the sign-in to continue
            authenticationContinuation.continueTask();
        }

        @Override
        public void getMFACode(MultiFactorAuthenticationContinuation continuation) {

        }

        @Override
        public void authenticationChallenge(ChallengeContinuation continuation) {

        }

        @Override
        public void onFailure(Exception exception) {
            Toast.makeText(context,"Accesso fallito!", Toast.LENGTH_LONG).show();
            Log.i(TAG, "----------------------------------------------------------------------- [COGNITO]: ACCESSO FALLITO EXCEPTION: "+exception.getLocalizedMessage());
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
