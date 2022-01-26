package com.example.natour2.fragment.loginSignin;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.natour2.R;
import com.example.natour2.controller.ControllerLoginSignin;
import com.example.natour2.utilities.Constants;
import com.example.natour2.utilities.PreferanceManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.messaging.FirebaseMessaging;


public class LoginFragment extends Fragment {

    private Button btnSignUp;
    private TextView txtForgetPass;
    private Button btnLogin;
    private EditText etUsername;
    private EditText etPassword;
    private ProgressBar progressBar;
    private TextView bntAccessoAdmin;

    private final ControllerLoginSignin ctrl = ControllerLoginSignin.getInstance();
    private FirebaseAnalytics analytics;
    private PreferanceManager preferanceManager;


    public LoginFragment() { }

    public LoginFragment(FirebaseAnalytics analytics){
        this.analytics = analytics;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //*****************************************************************************************
        preferanceManager = new PreferanceManager(getActivity().getApplicationContext());
        //*****************************************************************************************

        ctrl.setActivity(getActivity());
        ctrl.setContext(getActivity().getApplicationContext());
        ctrl.setFragmentManager(getActivity().getSupportFragmentManager());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_login, container, false);

        initViewComponents(view);

        return view;
    }

    private void initViewComponents(View view){

        btnSignUp = view.findViewById(R.id.btnSignUp);
        txtForgetPass= view.findViewById(R.id.forgetPassword);
        btnLogin = view.findViewById(R.id.btnLogin);
        etUsername = view.findViewById(R.id.etUsernameLogin);
        etPassword = view.findViewById(R.id.etPasswordLogin);
        progressBar = view.findViewById(R.id.progessBarLogIn);
        bntAccessoAdmin = view.findViewById(R.id.buttonAdminLoginFragment);

        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w("TAG", "Fetching FCM registration token failed", task.getException());
                            return;
                        }
                        // Get new FCM registration token
                       // Firebasetoken = task.getResult();
                        preferanceManager.putString(Constants.KEY_FCM_TOKEN, task.getResult());
                        Log.i("FIREBASETOKEN", "******************************************* FIREBASE_TOKEN: " + task.getResult());
                    }
                });


        bntAccessoAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ctrl.showAdminFragment();
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                ctrl.showSignupFragment();
            }
        });


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               ctrl.login(etUsername.getText().toString().replace(" ", ""), etPassword.getText().toString());
               //***********************************************************************************
                /*if(isValidSignInDetails()){
                    signIn();
                }*/
            }
        });

        txtForgetPass.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                ctrl.showForgetPasswordFragment();
            }
        });

    }



    /* ****************************************************************************************** */
   /*


    private void showToast(String message){
        Toast.makeText(getActivity().getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    private void signIn(){
        loading(true);
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        database.collection(Constants.KEY_COLLECTION_USERS)
                .whereEqualTo(Constants.KEY_EMAIL, etUsername.getText().toString())
                .whereEqualTo(Constants.KEY_PASSWORD, etPassword.getText().toString())
                .get()
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful() && task.getResult() != null && task.getResult().getDocuments().size() > 0){
                        DocumentSnapshot documentSnapshot = task.getResult().getDocuments().get(0);
                        preferanceManager.putBoolean(Constants.KEY_IS_SIGNED_IN, true);
                        preferanceManager.putString(Constants.KEY_USER_ID, documentSnapshot.getId());
                        preferanceManager.putString(Constants.KEY_NAME, documentSnapshot.getString(Constants.KEY_NAME));

                        Intent intent = new Intent(getActivity().getApplicationContext(), HomeActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);



                        FirebaseMessaging.getInstance().getToken().addOnSuccessListener(token -> {
                            if (!TextUtils.isEmpty(token)) {
                                Log.d("TAG", "TOKEN ####################################################################: " + token);
                            } else{
                                Log.w("TAG", "TOKEN IS NULL ################################################################");
                            }
                        }).addOnFailureListener(e -> {
                            //handle e
                        }).addOnCanceledListener(() -> {
                            //handle cancel
                        }).addOnCompleteListener(task1 -> Log.v("TAG", "This is the token : " + task1.getResult()));

                    }
                    else{
                        loading(false);
                        showToast("Unable to sing in");
                    }
                });
    }
*/
    /*private void loading(Boolean isLoading){
        if(isLoading){
            btnLogin.setVisibility(View.INVISIBLE);
            progressBar.setVisibility(View.VISIBLE);
        }
        else{
            progressBar.setVisibility(View.INVISIBLE);
            btnLogin.setVisibility(View.VISIBLE);
        }
    }*/
}