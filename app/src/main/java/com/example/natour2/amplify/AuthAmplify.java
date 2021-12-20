package com.example.natour2.amplify;

import com.amplifyframework.auth.AuthUserAttributeKey;
import com.amplifyframework.auth.options.AuthSignUpOptions;
import com.amplifyframework.core.Amplify;
import com.example.natour2.controller.ControllerLoginSignin;

public class AuthAmplify {

    public AuthAmplify(){ }

    public void signUp(String username, String email, String password){

        ControllerLoginSignin ctrl = ControllerLoginSignin.getInstance();
        AuthSignUpOptions options = AuthSignUpOptions.builder()
                .userAttribute(AuthUserAttributeKey.email(), email)
                .userAttribute(AuthUserAttributeKey.nickname(), username)
                .build();
        Amplify.Auth.signUp(email, password, options,
                result -> { ctrl.showVerifyCodeFragment(); },
                error -> { ctrl.printToast("Registrazioen Fallita"); }
        );
    }


    public void confirmSignUp(String email, String code){

        ControllerLoginSignin ctrl = ControllerLoginSignin.getInstance();
        Amplify.Auth.confirmSignUp(
                email,
                code,
                result -> { ctrl.showLoginFragment(); },
                error -> {  ctrl.printToast("Verifica Fallita!"); }
        );
    }


    public void signIn(String email, String password){

        ControllerLoginSignin ctrl = ControllerLoginSignin.getInstance();
        Amplify.Auth.signIn(
                email,
                password,
                result -> { ctrl.printToast("Accesso effettuato!");
                            ctrl.showHomeActivity(); },
                error -> { ctrl.printToast("Accesso Fallito!"); }
        );
    }

}
