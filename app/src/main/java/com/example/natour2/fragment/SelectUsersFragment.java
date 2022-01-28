package com.example.natour2.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.natour2.R;
import com.example.natour2.adapter.UserAdapter;
import com.example.natour2.controller.ControllerHomeActivity;
import com.example.natour2.controller.ControllerUser;
import com.example.natour2.listeners.UserListener;
import com.example.natour2.model.User;
import com.example.natour2.utilities.PreferanceManager;

import java.util.ArrayList;
import java.util.List;


public class SelectUsersFragment extends BaseFragment implements UserListener {

    private ProgressBar progressBar;
    private PreferanceManager preferanceManager;
    private TextView textErrorMessage;
    private RecyclerView userRecyclerView;
    private AppCompatImageView imageBackSelectUser;
    private List<User> users;
    private UserAdapter userAdapter;

    private final ControllerHomeActivity ctrl = ControllerHomeActivity.getInstance();
    private final ControllerUser ctrlUser = ControllerUser.getInstance();


    public SelectUsersFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //preferanceManager = new PreferanceManager(getActivity().getApplicationContext());
        ctrl.setActivity(getActivity());
        ctrl.setContext(getActivity().getApplicationContext());
        ctrl.setFragmentManager(getActivity().getSupportFragmentManager());
        ctrlUser.setActivity(getActivity());
        ctrlUser.setContext(getActivity().getApplicationContext());
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
        progressBar = view.findViewById(R.id.progessBar_SelectUserFragment);
        textErrorMessage = view.findViewById(R.id.textView_ErrorMessage_SelectUserFragment);

        userRecyclerView = view.findViewById(R.id.recyclerview_SelectUserFragment);
        userRecyclerView.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setReverseLayout(true);
        //linearLayoutManager.setStackFromEnd(true);
        userRecyclerView.setLayoutManager(linearLayoutManager);

        users = new ArrayList<>();
        userAdapter = new UserAdapter(users, this);
        userRecyclerView.setAdapter(userAdapter);
        userRecyclerView.setVisibility(View.VISIBLE);

        imageBackSelectUser = view.findViewById(R.id.imageView_Back_ShowSegnalazioneFragment);
        imageBackSelectUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //v -> getActivity().onBackPressed()
                ctrl.showUserFragment();
            }
        });

    }

    private void getUsers() {
        /*
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

         */

        loading(true);

        List<User> list = ctrlUser.getAllUsers();
        if(list == null){
            return;
        }
        users.addAll(list);
        userAdapter.notifyDataSetChanged();
        loading(false);


    }



    private void loading(Boolean isLoading){
        if(isLoading){
            progressBar.setVisibility(View.VISIBLE);
        }
        else{
            progressBar.setVisibility(View.INVISIBLE);
        }
    }
/*
    private void showErrorMessage(){
       textErrorMessage.setText(String.format("%s", "No user aviable"));
       textErrorMessage.setVisibility(View.VISIBLE);
    }

    private void showToast(String message){
        Toast.makeText(getActivity().getApplicationContext(), message,Toast.LENGTH_SHORT).show();

    }
    */

    @Override
    public void onUserClicked(User user) {
        ctrl.setUser(user);
        ctrl.showChatFragment();

    }

}