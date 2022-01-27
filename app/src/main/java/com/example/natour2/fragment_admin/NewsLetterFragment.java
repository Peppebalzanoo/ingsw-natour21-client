package com.example.natour2.fragment_admin;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.natour2.R;
import com.example.natour2.adapter.EmailAdapter;
import com.example.natour2.controller.ControllerAdminActivity;
import com.example.natour2.controller.ControllerUser;
import com.example.natour2.model.Email;
import com.example.natour2.model.User;
import com.example.natour2.utilities.SpacingItem;

import java.util.ArrayList;
import java.util.List;


public class NewsLetterFragment extends Fragment implements EmailAdapter.OnEmailListener{

    private ImageView imageViewBack;
    private ImageView imageViewSend;
    private EditText editTextOggetto;
    private EditText editTextContenuto;
    private CheckBox checkBoxAll;

    private List<Email> emailList;

    private EmailAdapter emailAdapter;
    private RecyclerView recyclerView;

    private ControllerAdminActivity ctrl = ControllerAdminActivity.getInstance();
    private final ControllerUser ctrlUser = ControllerUser.getInstance();

    public NewsLetterFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ctrl.setActivity(requireActivity());
        ctrl.setContext(requireContext());
        ctrl.setFragmentManager(requireFragmentManager());

        ctrlUser.setActivity(requireActivity());
        ctrlUser.setContext(requireContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news_letter, container, false);
        initComponentsView(view);

        emailList = new ArrayList<>();

        emailAdapter = new EmailAdapter(this, getContext(), emailList, savedInstanceState, recyclerView);
        recyclerView.setAdapter(emailAdapter);
        SpacingItem spacingItem = new SpacingItem(20);
        recyclerView.addItemDecoration(spacingItem);

        DividerItemDecoration horizontalDecoration = new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL);
        Drawable horizontalDivider = ContextCompat.getDrawable(requireActivity(), R.drawable.horizontal_divider);
        assert horizontalDivider != null;
        horizontalDecoration.setDrawable(horizontalDivider);
        recyclerView.addItemDecoration(horizontalDecoration);

        setListeners();
        loadEmail();
        return view;
    }


    public void loadEmail(){
        List<User> list = ctrlUser.getAllUsers();
        if(list == null){
            return;
        }
        for(User u: list ){
            emailList.add(new Email(u.getEmail()));
        }
        emailAdapter.notifyDataSetChanged();

    }


    public void initComponentsView(View view){
        imageViewBack = view.findViewById(R.id.imageView_Back_NewsLetterFragment);
        imageViewSend = view.findViewById(R.id.imageView_Send_NewsLetterFragment);
        checkBoxAll = view.findViewById(R.id.checkBox_NewsLetterFragment);
        editTextOggetto = view.findViewById(R.id.editText_Oggetto_NewsLetterFragment);
        editTextContenuto = view.findViewById(R.id.editText_Contenuto_NewsLetterFragment);

        recyclerView = view.findViewById(R.id.recycler_view_NewsLetterFragment);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    public void setListeners(){

        imageViewSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /* Codice per inviare le NewsLetters */
                String oggetto = editTextOggetto.getText().toString();
                String contenuto = editTextContenuto.getText().toString();
            }
        });

        imageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ctrl.showControlPanelFragment(requireActivity().getSupportFragmentManager());
            }
        });

        checkBoxAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkBoxAll.isChecked()) {
                    for (int i = 0; i < emailList.size(); i++) {
                        if (!emailList.get(i).getCheckStatus()) {
                            emailList.get(i).setChecked();
                            emailAdapter.notifyItemChanged(i);
                        }
                    }
                }
                else{
                    for (int i = 0; i < emailList.size(); i++) {
                        if (emailList.get(i).getCheckStatus()) {
                            emailList.get(i).setUnChecked();
                            emailAdapter.notifyItemChanged(i);
                        }
                    }
                }
            }
        });

    }

    @Override
    public void onEmailClick(int position) {
        Email e = emailList.get(position);
        if(!e.getCheckStatus()){
            e.setChecked();
        }
        else{
            e.setUnChecked();
        }
        emailAdapter.notifyItemChanged(position);
    }
}