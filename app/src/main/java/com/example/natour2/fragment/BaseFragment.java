package com.example.natour2.fragment;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.natour2.utilities.Constants;
import com.example.natour2.utilities.PreferanceManager;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class BaseFragment extends Fragment {

    private DocumentReference documentReference;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        PreferanceManager preferanceManager =  new PreferanceManager(getActivity().getApplicationContext());;
        FirebaseFirestore database = FirebaseFirestore.getInstance();;
        super.onCreate(savedInstanceState);
        documentReference = database.collection(Constants.KEY_COLLECTION_USERS)
                                        .document(preferanceManager.getString(Constants.KEY_USER_ID));
    }

    @Override
    public void onPause() {
        super.onPause();
        documentReference.update(Constants.KEY_AVAILABILITY, 0);
    }

    @Override
    public void onResume() {
        super.onResume();
        documentReference.update(Constants.KEY_AVAILABILITY, 1);
    }

}
