package com.example.natour2.fragment.loginSignin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.natour2.R;
import com.example.natour2.controller.ControllerLoginSignin;
import com.example.natour2.utilities.PreferanceManager;

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
    private PreferanceManager preferanceManager;

    public SignupFragment() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //******************************************************************************************

        //preferanceManager = new PreferanceManager(getActivity().getApplicationContext());

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

                if(passwordSignUp.getText().toString().equals(repeatPassSignUp.getText().toString())){
                    ctrl.signup(usernameSignUp.getText().toString().replace(" ", ""),
                            emailSignUp.getText().toString().replace(" ", ""),
                            passwordSignUp.getText().toString());
                } else {
                    Toast.makeText(getActivity(), "Errore! Le password non coencidono",
                            Toast.LENGTH_LONG).show();
                }

                /* ****************************************************************************** */
                /*if(isValidSignUpDetails()) {
                    signUp();
                }*/
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
    /*

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
*/
    /*private void loading(Boolean isLoading){
        if(isLoading){
            btnRegister.setVisibility(View.INVISIBLE);
            progressBar.setVisibility(View.VISIBLE);
        }
        else{
            progressBar.setVisibility(View.INVISIBLE);
            btnRegister.setVisibility(View.INVISIBLE);
        }
    }*/

}