package com.example.natour2.fragment;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import com.example.natour2.R;
import com.example.natour2.adapter.RecentConversationsAdapter;
import com.example.natour2.controller.ControllerHomeActivity;
import com.example.natour2.listeners.ConversionListener;
import com.example.natour2.model.ChatMessage;
import com.example.natour2.model.User;
import com.example.natour2.utilities.Constants;
import com.example.natour2.utilities.PreferanceManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class UserFragment extends BaseFragment implements ConversionListener {

    private PreferanceManager preferanceManager;
    private RecyclerView recyclerViewConversation;
    private List<ChatMessage> conversations;
    private RecentConversationsAdapter conversationsAdapter;
    private FirebaseFirestore database;
    private FloatingActionButton fabNewChat;
    private ProgressBar progressBarUserFragment;


    private final ControllerHomeActivity ctrl = ControllerHomeActivity.getInstance();


    public UserFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferanceManager = new PreferanceManager(getActivity().getApplicationContext());

        ctrl.setActivity(getActivity());
        ctrl.setContext(getActivity().getApplicationContext());
        ctrl.setFragmentManager(getActivity().getSupportFragmentManager());

        conversations = new ArrayList<>();
        conversationsAdapter = new RecentConversationsAdapter(conversations, this);
        database = FirebaseFirestore.getInstance();

        listenConversations();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_user, container, false);

        initComponentsView(view);

        setListeners();

        return view;
    }


    public void initComponentsView(View view){
        fabNewChat = view.findViewById(R.id.fabNewChat_UserFragment);
        recyclerViewConversation = view.findViewById(R.id.recyclerview_UserFragment);
        progressBarUserFragment = view.findViewById(R.id.progessBarUserFragment);

        recyclerViewConversation.setAdapter(conversationsAdapter);

    }

    public void setListeners(){
        fabNewChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ctrl.showSelectUserFragment();
            }
        });
    }


    private final EventListener<QuerySnapshot> eventListener = (value, error) -> {
        if(error != null){
            return;
        }
        if(value != null){
            for(DocumentChange documentChange : value.getDocumentChanges()){
                if(documentChange.getType() == DocumentChange.Type.ADDED){
                    String senderId = documentChange.getDocument().getString(Constants.KEY_SENDER_ID);
                    String receiverId = documentChange.getDocument().getString(Constants.KEY_RECEIVER_ID);
                    System.out.println("çççççççççççççççççççççççççççççççççççççççççççççççç senderId: " + senderId);
                    System.out.println("çççççççççççççççççççççççççççççççççççççççççççççççç receiverId: " + receiverId);
                    ChatMessage chatMessage = new ChatMessage();
                    chatMessage.setSenderId(senderId);
                    chatMessage.setReceiverId(receiverId);
                    if(preferanceManager.getString(Constants.KEY_USER_ID).equals(senderId)){
                        //chatMessage.conversionImage = documentChange.getDocument().getString(Constants.KEY_RECEIVER_IMAGE);
                        chatMessage.setConversionName(documentChange.getDocument().getString(Constants.KEY_RECEIVER_NAME));
                        chatMessage.setConversionId(documentChange.getDocument().getString(Constants.KEY_RECEIVER_ID));
                    }
                    else{
                        //chatMessage.conversionImage = documentChange.getDocument().getString(Constants.KEY_SENDER_IMAGE);
                        chatMessage.setConversionName(documentChange.getDocument().getString(Constants.KEY_SENDER_NAME));
                        chatMessage.setConversionId(documentChange.getDocument().getString(Constants.KEY_SENDER_ID));
                    }
                    chatMessage.setMessage(documentChange.getDocument().getString(Constants.KEY_LAST_MESSAGE));
                    chatMessage.setDateObject(documentChange.getDocument().getDate(Constants.KEY_TIMESTAMP));
                    conversations.add(chatMessage);
                }
                else if(documentChange.getType() == DocumentChange.Type.MODIFIED){
                    for(int i = 0; i < conversations.size(); i++){
                        String senderId = documentChange.getDocument().getString(Constants.KEY_SENDER_ID);
                        String receiverId = documentChange.getDocument().getString(Constants.KEY_RECEIVER_ID);
                        if(conversations.get(i).getSenderId().equals(senderId) && conversations.get(i).getReceiverId().equals(receiverId)){
                            conversations.get(i).setMessage(documentChange.getDocument().getString(Constants.KEY_LAST_MESSAGE));
                            conversations.get(i).setDateObject(documentChange.getDocument().getDate(Constants.KEY_TIMESTAMP));
                            break;
                        }
                    }
                }
            }
            Collections.sort(conversations, (obj1, obj2) -> obj2.getDateObject().compareTo(obj1.getDateObject()));
            conversationsAdapter.notifyDataSetChanged();
            recyclerViewConversation.smoothScrollToPosition(0);
            recyclerViewConversation.setVisibility(View.VISIBLE);
            progressBarUserFragment.setVisibility(View.GONE);
        }
    };



    private void listenConversations(){

        database.collection(Constants.KEY_COLLECTION_CONVERSATIONS)
                .whereEqualTo(Constants.KEY_SENDER_ID, preferanceManager.getString(Constants.KEY_USER_ID))
                .addSnapshotListener(eventListener);
        database.collection(Constants.KEY_COLLECTION_CONVERSATIONS)
                .whereEqualTo(Constants.KEY_RECEIVER_ID, preferanceManager.getString(Constants.KEY_USER_ID))
                .addSnapshotListener(eventListener);
    }

    @Override
    public void onConversionClicked(User user) {
        ChatFragment c = ctrl.setUser(user);
        ctrl.showChatFragment(getFragmentManager(), c);
    }
}