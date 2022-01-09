package com.example.natour2.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.natour2.R;
import com.example.natour2.adapter.UserAdapter;
import com.example.natour2.controller.ControllerHomeAcrtivity;
import com.example.natour2.listeners.UserListener;
import com.example.natour2.model.User;
import com.example.natour2.utilities.Constants;
import com.example.natour2.utilities.PreferanceManager;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SelectUsersFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SelectUsersFragment extends BaseFragment implements UserListener {

    private ProgressBar progressBar;
    private PreferanceManager preferanceManager;
    private TextView textErrorMessage;
    private RecyclerView userRecyclerView;
    private AppCompatImageView imageBackSelectUser;

    private final ControllerHomeAcrtivity ctrl = new ControllerHomeAcrtivity();


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SelectUsersFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UsersFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SelectUsersFragment newInstance(String param1, String param2) {
        SelectUsersFragment fragment = new SelectUsersFragment();
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
        preferanceManager = new PreferanceManager(getActivity().getApplicationContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_selectusers, container, false);
        initViewComponents(view);
        getUsers();
        return view;

    }

    private void initViewComponents(View view){
        progressBar = view.findViewById(R.id.progessBarSelectUserFragment);
        userRecyclerView = view.findViewById(R.id.userRecyclerView);
        textErrorMessage = view.findViewById(R.id.textErrorMessage);

        imageBackSelectUser = view.findViewById(R.id.imageBackSelectUser);
        imageBackSelectUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //v -> getActivity().onBackPressed()
                ctrl.showUserFragment(getFragmentManager());
            }
        });

    }

    private void getUsers() {
        loading(true);
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        database.collection(Constants.KEY_COLLECTION_USERS)
                .get()
                .addOnCompleteListener(task -> {
                    loading(false);
                    String currentUserId = preferanceManager.getString(Constants.KEY_USER_ID);
                    if (task.isSuccessful() && task.getResult() != null) {
                        List<User> users = new ArrayList<>();
                        for (QueryDocumentSnapshot queryDocumentSnapshot : task.getResult()) {
                            if (currentUserId.equals(queryDocumentSnapshot.getId())) {
                                continue;
                            }
                            User user = new User();
                            user.name = queryDocumentSnapshot.getString(Constants.KEY_NAME);
                            user.email = queryDocumentSnapshot.getString(Constants.KEY_EMAIL);
                            user.image = queryDocumentSnapshot.getString(Constants.KEY_IMAGE);
                            user.token = queryDocumentSnapshot.getString(Constants.KEY_FCM_TOKEN);
                            user.id = queryDocumentSnapshot.getId();
                            users.add(user);
                        }
                        if (users.size() > 0) {
                            UserAdapter userAdapter = new UserAdapter(users, this);
                            userRecyclerView.setAdapter(userAdapter);
                            userRecyclerView.setVisibility(View.VISIBLE);
                        } else {
                            showErrorMessage();
                        }
                    }
                });
    }


    private void loading(Boolean isLoading){
        if(isLoading){
            progressBar.setVisibility(View.VISIBLE);
        }
        else{
            progressBar.setVisibility(View.INVISIBLE);
        }
    }

    private void showErrorMessage(){
       textErrorMessage.setText(String.format("%s", "No user aviable"));
       textErrorMessage.setVisibility(View.VISIBLE);
    }

    private void showToast(String message){
        Toast.makeText(getActivity().getApplicationContext(), message,Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onUserClicked(User user) {
        ctrl.setUser(user);
        ctrl.showChatFragment(getFragmentManager());


        //Intent intent = new Intent(getActivity().getApplicationContext(), ChatFragment.class);
        //intent.putExtra(Constants.KEY_USER, user);
        //startActivity(intent);
        //getActivity().finish();
    }

}