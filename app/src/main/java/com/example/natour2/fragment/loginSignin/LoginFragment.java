package com.example.natour2.fragment.loginSignin;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.natour2.R;
import com.example.natour2.auth.Google;
import com.example.natour2.controller.ControllerLoginSignin;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;


public class LoginFragment extends Fragment {

    private Button btnSignUp;
    private TextView txtForgetPass;
    private Button btnLogin;
    private EditText etUsername;
    private EditText etPassword;
    private ProgressBar progressBar;
    private Button bntAccessoAdmin;



    private final ControllerLoginSignin ctrl = ControllerLoginSignin.getInstance();
    private FirebaseAnalytics analytics;
    private Google googleInstance;

    //----------------------------------------
    //private FragmentLoginBinding binding;
    //private PreferanceManager preferanceManager;

    public LoginFragment() { }

    public LoginFragment(FirebaseAnalytics analytics){
        this.analytics = analytics;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //*****************************************************************************************
        //preferanceManager = new PreferanceManager(getActivity().getApplicationContext());
        //*****************************************************************************************

        ctrl.setActivity(getActivity());
        ctrl.setContext(getActivity().getApplicationContext());
        ctrl.setFragmentManager(getActivity().getSupportFragmentManager());

        googleInstance = new Google(getActivity().getApplicationContext());
        ctrl.setGoogleSignInClient(googleInstance.getGoogleSignInClient());
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

        SignInButton signInButton = view.findViewById(R.id.sign_in_button);
        signInButton.setSize(SignInButton.SIZE_STANDARD);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if(googleInstance.checkAlreadySignIn(getActivity().getApplicationContext())){
//                    ctrl.showHomeActivity(getActivity().getApplicationContext());
//                }
//                else{
//                    signIn();
//                }
                signIn();
            }
        });

    }

    private void signIn(){
        Intent signInIntent = googleInstance.getGoogleSignInClient().getSignInIntent();
        startActivityForResult(signInIntent, Google.RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == Google.RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            // Signed in successfully
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            googleInstance.setGoogleSignInClient(account);

            GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(getActivity().getApplicationContext());
            if (acct != null) {
                googleInstance.storeInformationAccount(acct);
            }
            ctrl.showHomeActivity(getActivity().getApplicationContext());

            Bundle bundle = new Bundle();
            analytics.logEvent("signin_google", bundle);

        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("TAG", "signInResult:failed code=" + e.getStatusCode());
        }
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