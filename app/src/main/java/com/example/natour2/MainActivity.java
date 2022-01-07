package com.example.natour2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.amplifyframework.AmplifyException;
import com.amplifyframework.api.aws.AWSApiPlugin;
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.AWSDataStorePlugin;
import com.amplifyframework.datastore.generated.model.AmplifyModelProvider;
import com.example.natour2.controller.ControllerHomeAcrtivity;
import com.example.natour2.controller.ControllerLoginSignin;
import com.example.natour2.firebase.MessagingService;
import com.example.natour2.utilities.PreferanceManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.getSupportActionBar().hide();



        try {
            Amplify.addPlugin(new AWSCognitoAuthPlugin());
            Amplify.configure(getApplicationContext());

            //da verificare
            //AmplifyModelProvider modelProvider = AmplifyModelProvider.getInstance();
            //Amplify.addPlugin(new AWSDataStorePlugin(modelProvider));
            //Amplify.addPlugin(new AWSApiPlugin());
            //***


        } catch (AmplifyException error) {
            System.out.println(error.toString());
            error.printStackTrace();
        }


        ControllerLoginSignin ctrl = ControllerLoginSignin.getInstance();
        ctrl.setFragmentManager(getSupportFragmentManager());
        ctrl.showLoginFragment();
        //ctrl.showHomeActivity(getApplicationContext());

    }

}