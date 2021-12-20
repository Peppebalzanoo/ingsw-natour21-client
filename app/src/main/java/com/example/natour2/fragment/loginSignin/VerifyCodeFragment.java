package com.example.natour2.fragment.loginSignin;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.natour2.R;
import com.example.natour2.controller.ControllerLoginSignin;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link VerifyCodeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class VerifyCodeFragment extends Fragment {

    private EditText editTextNumberPassword;
    private Button btnVerify;
    private final ControllerLoginSignin ctrl = ControllerLoginSignin.getInstance();


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public VerifyCodeFragment() { }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment VerifyCodeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static VerifyCodeFragment newInstance(String param1, String param2) {
        VerifyCodeFragment fragment = new VerifyCodeFragment();
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

        ctrl.setActivity(getActivity());
        ctrl.setContext(getActivity().getApplicationContext());
        ctrl.setFragmentManager(getActivity().getSupportFragmentManager());
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_verify_code, container, false);
        initViewComponents(view);
        return view;

    }


    private void initViewComponents(View view){

        editTextNumberPassword = view.findViewById(R.id.editTextNumberPassword);
        btnVerify = view.findViewById(R.id.btnVerify);

        btnVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ctrl.verifyCode(editTextNumberPassword.getText().toString().replace(" ", ""));
            }
        });
    }
}