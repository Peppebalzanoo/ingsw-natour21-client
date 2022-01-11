package com.example.natour2.fragment.loginSignin;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.natour2.HomeActivity;
import com.example.natour2.MainActivity;
import com.example.natour2.R;
import com.example.natour2.controller.ControllerLoginSignin;
import com.example.natour2.databinding.FragmentLoginBinding;
import com.example.natour2.firebase.MessagingService;
import com.example.natour2.utilities.Constants;
import com.example.natour2.utilities.PreferanceManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.messaging.FirebaseMessaging;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends Fragment {

    // ############################################################# View Components
    private Button btnSignUp;     // For creating account
    private TextView txtForgetPass;     // For retrieving password
    private Button btnLogin;            // Button for Login
    private EditText etUsername;        // Username
    private EditText etPassword;        // Password
    private ProgressBar progressBar;
    // ############################################################# End View Components


    private final ControllerLoginSignin ctrl = ControllerLoginSignin.getInstance();

    //----------------------------------------
    private FragmentLoginBinding binding;
    private PreferanceManager preferanceManager;

    public LoginFragment() { }

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
                //Da vedere come fare il recupero password
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
    private void loading(Boolean isLoading){
        if(isLoading){
            btnLogin.setVisibility(View.INVISIBLE);
            progressBar.setVisibility(View.VISIBLE);
        }
        else{
            progressBar.setVisibility(View.INVISIBLE);
            btnLogin.setVisibility(View.VISIBLE);
        }
    }
}