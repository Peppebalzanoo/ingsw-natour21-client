package com.example.natour2;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

/*
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.model.CreatePlatformEndpointRequest;
import com.amazonaws.services.sns.model.CreatePlatformEndpointResult;
import com.amazonaws.services.sns.model.PublishRequest;
*/
/*import com.amplifyframework.AmplifyException;
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin;
import com.amplifyframework.core.Amplify;*/
import com.example.natour2.controller.ControllerLoginSignin;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

public class MainActivity extends AppCompatActivity {

    private String Firebasetoken = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.getSupportActionBar().hide();



        /*try {
            Amplify.addPlugin(new AWSCognitoAuthPlugin());
            Amplify.configure(getApplicationContext());


        } catch (AmplifyException error) {
            System.out.println(error.toString());
            error.printStackTrace();
        }*/


        ControllerLoginSignin ctrl = ControllerLoginSignin.getInstance();
        ctrl.setFragmentManager(getSupportFragmentManager());
        ctrl.showLoginFragment();
        //ctrl.showHomeActivity(getApplicationContext());


        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w("TAG", "Fetching FCM registration token failed", task.getException());
                            return;
                        }
                        // Get new FCM registration token
                        Firebasetoken = task.getResult();
                        Log.i("FIREBASETOKEN", "******************************************* FIREBASE_TOKEN: "+ Firebasetoken);
                    }
                });




        String AWS_ACCESS_KEY = "AKIATQAAFEJQKY6S7ATV ";
        String AWS_SECRET_KEY = "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";
        String PLATFORM_APPLICATION_ARN = "arn:aws:sns:us-east-1:240518505056:app/GCM/NaTour21";

        /*
        AmazonSNS snsClient = AmazonSNSClient.builder().withRegion(Regions.US_EAST_1).withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(AWS_ACCESS_KEY, AWS_SECRET_KEY))).build();
        String arn  = null;

        CreatePlatformEndpointRequest createPlatformEndpointRequest = new CreatePlatformEndpointRequest().withCustomUserData("username").withToken("tokenfirebase").withPlatformApplicationArn(PLATFORM_APPLICATION_ARN);
        CreatePlatformEndpointResult platformEndpoint = snsClient.createPlatformEndpoint(createPlatformEndpointRequest);
        arn = platformEndpoint.getEndpointArn();

        //AmazonSNS snsClient = AmazonSNSClient.builder().withRegion(Regions.US_EAST_1).withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(AWS_ACCESS_KEY, AWS_SECRET_KEY))).build();

        try {
            PublishRequest publishReq = new PublishRequest().withMessage("message").withTargetArn(arn);
            snsClient.publish(publishReq);
        } catch (Exception e) {
            e.printStackTrace();
        }
        */

    }
}