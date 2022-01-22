package com.example.natour2.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.recyclerview.widget.RecyclerView;

import com.example.natour2.R;
import com.example.natour2.adapter.RecentConversationsAdapter;
import com.example.natour2.controller.ControllerHomeAcrtivity;
import com.example.natour2.listeners.ConversionListener;
import com.example.natour2.model.ChatMessage;
import com.example.natour2.model.User;
import com.example.natour2.utilities.PreferanceManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;


public class UserFragment extends BaseFragment implements ConversionListener {

    private PreferanceManager preferanceManager;
    private RecyclerView recyclerViewConversation;
    private List<ChatMessage> conversations;
    private RecentConversationsAdapter conversationsAdapter;
    private FirebaseFirestore database;
    private FloatingActionButton fabNewChat;
    private ProgressBar progressBarUserFragment;


    private final ControllerHomeAcrtivity ctrl = ControllerHomeAcrtivity.getInstance();


    public UserFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //preferanceManager = new PreferanceManager(getActivity().getApplicationContext());

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

        fabNewChat = view.findViewById(R.id.fabNewChat);
        fabNewChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               ctrl.showSelectUserFragment();
            }
        });
        recyclerViewConversation = view.findViewById(R.id.conversationRecyclerView);
        recyclerViewConversation.setAdapter(conversationsAdapter);

        progressBarUserFragment = view.findViewById(R.id.progessBarUserFragment);
        return view;
    }


    /*private final EventListener<QuerySnapshot> eventListener = (value, error) -> {
        if(error != null){
            return;
        }
        if(value != null){
            for(DocumentChange documentChange : value.getDocumentChanges()){
                if(documentChange.getType() == DocumentChange.Type.ADDED){
                    String senderId = documentChange.getDocument().getString(Constants.KEY_SENDER_ID);
                    String receiverId = documentChange.getDocument().getString(Constants.KEY_RECEIVER_ID);
                    ChatMessage chatMessage = new ChatMessage();
                    chatMessage.senderId = senderId;
                    chatMessage.receiverId = receiverId;
                    if(preferanceManager.getString(Constants.KEY_USER_ID).equals(senderId)){
                        //chatMessage.conversionImage = documentChange.getDocument().getString(Constants.KEY_RECEIVER_IMAGE);
                        chatMessage.conversionName = documentChange.getDocument().getString(Constants.KEY_RECEIVER_NAME);
                        chatMessage.conversionId = documentChange.getDocument().getString(Constants.KEY_RECEIVER_ID);
                    }
                    else{
                        //chatMessage.conversionImage = documentChange.getDocument().getString(Constants.KEY_SENDER_IMAGE);
                        chatMessage.conversionName = documentChange.getDocument().getString(Constants.KEY_SENDER_NAME);
                        chatMessage.conversionId = documentChange.getDocument().getString(Constants.KEY_SENDER_ID);
                    }
                    chatMessage.message = documentChange.getDocument().getString(Constants.KEY_LAST_MESSAGE);
                    chatMessage.dateObject = documentChange.getDocument().getDate(Constants.KEY_TIMESTAMP);
                    conversations.add(chatMessage);
                }
                else if(documentChange.getType() == DocumentChange.Type.MODIFIED){
                    for(int i = 0; i < conversations.size(); i++){
                        String senderId = documentChange.getDocument().getString(Constants.KEY_SENDER_ID);
                        String receiverId = documentChange.getDocument().getString(Constants.KEY_RECEIVER_ID);
                        if(conversations.get(i).senderId.equals(senderId) && conversations.get(i).receiverId.equals(receiverId)){
                            conversations.get(i).message = documentChange.getDocument().getString(Constants.KEY_LAST_MESSAGE);
                            conversations.get(i).dateObject = documentChange.getDocument().getDate(Constants.KEY_TIMESTAMP);
                            break;
                        }
                    }
                }
            }
            Collections.sort(conversations, (obj1, obj2) -> obj2.dateObject.compareTo(obj1.dateObject));
            conversationsAdapter.notifyDataSetChanged();
            recyclerViewConversation.smoothScrollToPosition(0);
            recyclerViewConversation.setVisibility(View.VISIBLE);
            progressBarUserFragment.setVisibility(View.GONE);
        }
    };
    */


    private void listenConversations(){
       /*
        database.collection(Constants.KEY_COLLECTION_CONVERSATIONS)
                .whereEqualTo(Constants.KEY_SENDER_ID, preferanceManager.getString(Constants.KEY_USER_ID))
                .addSnapshotListener(eventListener);
        database.collection(Constants.KEY_COLLECTION_CONVERSATIONS)
                .whereEqualTo(Constants.KEY_RECEIVER_ID, preferanceManager.getString(Constants.KEY_USER_ID))
                .addSnapshotListener(eventListener);
         */
        ChatMessage chat1 = new ChatMessage("Antonio", "Peppe", "Ciao Peppe da Antonio", "Peppe");
        ChatMessage chat2 = new ChatMessage("Antonio", "Simone", "Ciao Simone da Antonio", "Simone");

        conversations.add(chat1);
        conversations.add(chat2);
    }

    @Override
    public void onConversionClicked(User user) {
        ChatFragment c = ctrl.setUser(user);
        ctrl.showChatFragment(getFragmentManager(), c);
    }
}