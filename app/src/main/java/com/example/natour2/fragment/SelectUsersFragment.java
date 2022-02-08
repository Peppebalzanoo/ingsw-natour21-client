package com.example.natour2.fragment;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
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
    private TextView textErrorMessage;
    private RecyclerView userRecyclerView;
    private AppCompatImageView imageBackSelectUser;
    private UserAdapter userAdapter;

    private final ControllerHomeActivity ctrl = ControllerHomeActivity.getInstance();
    private final ControllerUser ctrlUser = ControllerUser.getInstance();


    public SelectUsersFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ctrl.setActivity(getActivity());
        ctrl.setContext(getActivity().getApplicationContext());
        ctrl.setFragmentManager(getActivity().getSupportFragmentManager());
        ctrlUser.setActivity(getActivity());
        ctrlUser.setContext(getActivity().getApplicationContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_selectusers, container, false);
        initViewComponents(view);

        getUsers();
        return view;

    }

    private void initViewComponents(View view){
        progressBar = view.findViewById(R.id.progessBar_SelectUserFragment);

        userRecyclerView = view.findViewById(R.id.recyclerview_SelectUserFragment);
        userRecyclerView.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setReverseLayout(true);
        userRecyclerView.setLayoutManager(linearLayoutManager);

        userAdapter = new UserAdapter(this);
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
        //loading(true);
        ctrlUser.getAllUsers1(userAdapter, this);
        //loading(false);
    }



    public void loading(Boolean isLoading){
        if(isLoading){
            progressBar.setVisibility(View.VISIBLE);
        }
        else{
            progressBar.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onUserClicked(User user) {
        ctrl.setUser(user);
        ctrl.showChatFragment();
    }

}