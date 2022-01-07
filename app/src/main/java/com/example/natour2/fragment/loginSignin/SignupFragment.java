package com.example.natour2.fragment.loginSignin;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;

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

import com.example.natour2.HomeActivity;
import com.example.natour2.R;
import com.example.natour2.controller.ControllerLoginSignin;
import com.example.natour2.databinding.FragmentSignupBinding;
import com.example.natour2.utilities.Constants;
import com.example.natour2.utilities.PreferanceManager;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SignupFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SignupFragment extends Fragment {


    // ############################################################# View Components
    private EditText usernameSignUp;        // Username
    private EditText emailSignUp;           // Email
    private EditText passwordSignUp;        // Password
    private EditText repeatPassSignUp;      // RepeatPassword
    private Button btnRegister;
    private TextView alreadyHaveAccount;
    private ProgressBar progressBar;
    // ############################################################# End View Components


    private final ControllerLoginSignin ctrl = ControllerLoginSignin.getInstance();


    //----------------------------------------
    private FragmentSignupBinding binding;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SignupFragment() { }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SignupFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SignupFragment newInstance(String param1, String param2) {
        SignupFragment fragment = new SignupFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        //******************************************************************************************
        //binding = FragmentSignupBinding.inflate(getLayoutInflater());
        preferanceManager = new PreferanceManager(getActivity().getApplicationContext());
        //getActivity().setContentView(binding.getRoot());
        //******************************************************************************************

        ctrl.setActivity(getActivity());
        ctrl.setContext(getActivity().getApplicationContext());
        ctrl.setFragmentManager(getActivity().getSupportFragmentManager());
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_signup, container, false);
        initViewComponents(view);
        return view;
    }


    private void initViewComponents(View view){

        usernameSignUp = view.findViewById(R.id.usernameSignUp) ;
        emailSignUp = view.findViewById(R.id.emailSignUp);
        passwordSignUp = view.findViewById(R.id.passwordSignUp);
        repeatPassSignUp = view.findViewById(R.id.repeatPassSignUp);;
        btnRegister = view.findViewById(R.id.btnRegister);
        alreadyHaveAccount = view.findViewById(R.id.alreadyHaveAccount);
        progressBar = view.findViewById(R.id.progessBar);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /*if(passwordSignUp.getText().toString().equals(repeatPassSignUp.getText().toString())){
                    ctrl.signup(usernameSignUp.getText().toString().replace(" ", ""),
                            emailSignUp.getText().toString().replace(" ", ""),
                            passwordSignUp.getText().toString());
                } else {
                    Toast.makeText(getActivity(), "Errore! Le password non coencidono",
                            Toast.LENGTH_LONG).show();
                }*/

                /* ****************************************************************************** */
                if(isValidSignUpDetails()) {
                    signUp();
                }
                //**********************************************************************************

            }
        });


        alreadyHaveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ctrl.showLoginFragment();
            }
        });

    }

    /* ****************************************************************************************** */
    private PreferanceManager preferanceManager;

    private void showToast(String message){
        Toast.makeText(getActivity().getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    private void signUp(){
        loading(true);
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        HashMap<String, Object> user = new HashMap<>();
        user.put(Constants.KEY_NAME, usernameSignUp.getText().toString());
        user.put(Constants.KEY_EMAIL, emailSignUp.getText().toString());
        user.put(Constants.KEY_PASSWORD, passwordSignUp.getText().toString());
        database.collection(Constants.KEY_COLLECTION_USERS)
                .add(user)
                .addOnSuccessListener(documentReference -> {
                    loading(false);
                    preferanceManager.putBoolean(Constants.KEY_IS_SIGNED_IN, true);
                    preferanceManager.putString(Constants.KEY_USER_ID, documentReference.getId());
                    preferanceManager.putString(Constants.KEY_NAME, usernameSignUp.getText().toString());

                   Log.i("TOKENSZZZZ","########################################################"+preferanceManager.getString(Constants.KEY_FCM_TOKEN));

                    Intent intent = new Intent(getActivity().getApplicationContext(), HomeActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                })
                .addOnFailureListener( exception -> {
                    loading(false);
                    showToast(exception.getMessage());
                });
    }

    private Boolean isValidSignUpDetails(){
        if(usernameSignUp.getText().toString().trim().isEmpty()){
            showToast("Enter username");
            return false;
        }else if(emailSignUp.getText().toString().trim().isEmpty()){
            showToast("Enter email");
            return false;
        }else if(!Patterns.EMAIL_ADDRESS.matcher(emailSignUp.getText().toString()).matches()){
            showToast("Enter valid email");
            return false;
        }else if(passwordSignUp.getText().toString().trim().isEmpty()){
            showToast("Enter password");
            return false;
        }else if(repeatPassSignUp.getText().toString().trim().isEmpty()){
            showToast("Confirm your password");
            return false;
        }else if(!passwordSignUp.getText().toString().equals(repeatPassSignUp.getText().toString())){
            showToast("Password & confirm password must be same");
            return false;
        }else{
            return true;
        }
    }

    private void loading(Boolean isLoading){
        if(isLoading){
            btnRegister.setVisibility(View.INVISIBLE);
            progressBar.setVisibility(View.VISIBLE);
        }
        else{
            progressBar.setVisibility(View.INVISIBLE);
            btnRegister.setVisibility(View.INVISIBLE);
        }
    }

}