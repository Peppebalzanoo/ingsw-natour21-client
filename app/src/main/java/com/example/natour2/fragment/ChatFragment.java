package com.example.natour2.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.natour2.R;
import com.example.natour2.adapter.ChatAdapter;
import com.example.natour2.controller.ControllerHomeAcrtivity;
import com.example.natour2.model.ChatMessage;
import com.example.natour2.model.User;
import com.example.natour2.utilities.PreferanceManager;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;


public class ChatFragment extends BaseFragment{

    private User receiverUser;
    private TextView textNameChat;
    private AppCompatImageView imageBackChat;
    private RecyclerView chatRecyclerView;
    private EditText inputMessage;
    private TextView textAvaibility;
    private FrameLayout layoutSend;
    private ProgressBar progressBar;

    private List<ChatMessage> chatMessages;
    private ChatAdapter chatAdapter;
    private PreferanceManager preferanceManager;
    private FirebaseFirestore database;
    private String conversionId = null;
    private Boolean isReceiverAvailable = false;

    private static User user = null;
    private ControllerHomeAcrtivity ctrl = new ControllerHomeAcrtivity();

    public ChatFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chat, container, false);
        initViewComponent(view);
        //loadReceiverDetails();
        //init();
        //listenMessage();
        return view;
    }

    private void initViewComponent(View view) {
        layoutSend = view.findViewById(R.id.layoutSend);
        textNameChat = view.findViewById(R.id.textNameChat);
        textAvaibility = view.findViewById(R.id.textAvailability);
        chatRecyclerView = view.findViewById(R.id.chatRecyclerView);
        inputMessage = view.findViewById(R.id.inputMessage);
        progressBar = view.findViewById(R.id.progessBarChatFragment);
        imageBackChat = view.findViewById(R.id.imageBackChat);
        imageBackChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //getActivity().onBackPressed();
                ctrl.showUserFragment(getFragmentManager());
            }
        });

       // layoutSend.setOnClickListener(v -> sendMessage());

    }

    public void pippo(User u){
        user = u;
    }

    private void loadReceiverDetails() {
        receiverUser = user;
        textNameChat.setText(receiverUser.nickname);
    }

    /*private void init() {
        preferanceManager = new PreferanceManager(getActivity().getApplicationContext());
        chatMessages = new ArrayList<>();
        chatAdapter = new ChatAdapter(chatMessages, preferanceManager.getString(Constants.KEY_USER_ID));
        chatRecyclerView.setAdapter(chatAdapter);
        database = FirebaseFirestore.getInstance();
    }

    private void sendMessage() {
        HashMap<String, Object> message = new HashMap<>();
        message.put(Constants.KEY_SENDER_ID, preferanceManager.getString(Constants.KEY_USER_ID));
        message.put(Constants.KEY_RECEIVER_ID, receiverUser.id);
        message.put(Constants.KEY_MESSAGE, inputMessage.getText().toString());
        message.put(Constants.KEY_TIMESTAMP, new Date());
        database.collection(Constants.KEY_COLLECTION_CHAT).add(message);
        if(conversionId != null){
            updateConversion(inputMessage.getText().toString(),
                    preferanceManager.getString(Constants.KEY_NAME),
                    receiverUser.name,
                    receiverUser.id,
                    preferanceManager.getString(Constants.KEY_USER_ID));
        }
        else{
            HashMap<String, Object> conversion = new HashMap<>();
            conversion.put(Constants.KEY_SENDER_ID, preferanceManager.getString(Constants.KEY_USER_ID));
            conversion.put(Constants.KEY_SENDER_NAME, preferanceManager.getString(Constants.KEY_NAME));
            //conversion.put(Constants.KEY_SENDER_IMAGE, preferanceManager.getString(Constants.KEY_IMAGE));

            conversion.put(Constants.KEY_RECEIVER_ID, receiverUser.id);
            conversion.put(Constants.KEY_RECEIVER_NAME, receiverUser.name);
            //conversion.put(Constants.KEY_RECEIVER_IMAGE, receiverUser.image);

            conversion.put(Constants.KEY_LAST_MESSAGE, inputMessage.getText().toString());
            conversion.put(Constants.KEY_TIMESTAMP, new Date());
            addConversion(conversion);
        }
        //if(!isReceiverAvailable){
            try{
                JSONArray tokens = new JSONArray();
                tokens.put(receiverUser.token);

                JSONObject data = new JSONObject();
                data.put(Constants.KEY_USER_ID, preferanceManager.getString(Constants.KEY_USER_ID));
                data.put(Constants.KEY_NAME, preferanceManager.getString(Constants.KEY_NAME));
                data.put(Constants.KEY_FCM_TOKEN, preferanceManager.getString(Constants.KEY_FCM_TOKEN));
                data.put(Constants.KEY_MESSAGE, inputMessage.getText().toString());

                JSONObject body = new JSONObject();
                body.put(Constants.REMOTE_MSG_DATA, data);
                body.put(Constants.REMOTE_MSG_REGISTRATION_IDS, tokens);

                sendNotificaiton(body.toString());

            }catch(Exception e){
                showToast(e.getMessage());
            }
        //}
        inputMessage.setText(null);
    }

    private void showToast(String message){
        Toast.makeText(getActivity().getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    private void sendNotificaiton(String messageBody){
        ApiClient.getInstance().create(ApiService.class).sendMessage(
                Constants.getInstanceRemoteMsgHeaders(),
                messageBody
        ).enqueue(new Callback<String>() {
            @Override
            public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                if(response.isSuccessful()){
                    try{
                        if(response.body() != null){
                            JSONObject responseJson = new JSONObject(response.body());
                            JSONArray results = responseJson.getJSONArray("results");
                            if(responseJson.getInt("failure") == 1){
                                JSONObject error = (JSONObject) results.get(0);
                                showToast(error.getString("error"));
                                return;
                            }
                        }
                    }catch (JSONException e){
                        e.printStackTrace();
                    }
                    showToast("Notification sent successfully");
                }
                else{
                    showToast("Error: "+ response.code());
                }
            }

            @Override
            public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
                showToast(t.getMessage());
            }
        });
    }

*/
    /*private void listenAvailabilityOfReceiver(){
        /*database.collection(Constants.KEY_COLLECTION_USERS)
                .document(receiverUser.id)
                .addSnapshotListener(
                        getActivity(),
                        (value, error) ->
                            {
                                Log.i("FRAGMENT_MANAGER", "######################################################## 3");
                                if(error != null) {
                                    return;
                                }
                                if(value != null){
                                    if(value.getLong(Constants.KEY_AVAILABILITY) != null){
                                        int availability = Objects.requireNonNull(value.getLong(Constants.KEY_AVAILABILITY).intValue());
                                        isReceiverAvailable = availability == 1;
                                    }
                                }
                                if(isReceiverAvailable){
                                    textAvaibility.setVisibility(View.VISIBLE);
                                }
                                else{
                                    textAvaibility.setVisibility(View.GONE);
                                }
                            }
                );*/
    /*
        database.collection(Constants.KEY_COLLECTION_USERS)
                .document(receiverUser.id)
                .addSnapshotListener((value, error) -> {
                    if(error != null) {
                        return;
                    }
                    if(value != null){
                        receiverUser.token = value.getString(Constants.KEY_FCM_TOKEN);
                    }
                });
    }*/





    /*private void listenMessage() {
        database.collection(Constants.KEY_COLLECTION_CHAT)
                .whereEqualTo(Constants.KEY_SENDER_ID, preferanceManager.getString(Constants.KEY_USER_ID))
                .whereEqualTo(Constants.KEY_RECEIVER_ID, receiverUser.id)
                .addSnapshotListener(eventListener);

        database.collection(Constants.KEY_COLLECTION_CHAT)
                .whereEqualTo(Constants.KEY_SENDER_ID, receiverUser.id)
                .whereEqualTo(Constants.KEY_RECEIVER_ID, preferanceManager.getString(Constants.KEY_USER_ID))
                .addSnapshotListener(eventListener);
    }

    private final EventListener<QuerySnapshot> eventListener = (value, error) -> {
        if (error != null) {
            return;
        }
        if (value != null) {
            int count = chatMessages.size();
            for (DocumentChange documentChange : value.getDocumentChanges()) {
                if (documentChange.getType() == DocumentChange.Type.ADDED) {
                    ChatMessage chatMessage = new ChatMessage();
                    chatMessage.senderId = documentChange.getDocument().getString(Constants.KEY_SENDER_ID);
                    chatMessage.receiverId = documentChange.getDocument().getString(Constants.KEY_RECEIVER_ID);
                    chatMessage.message = documentChange.getDocument().getString(Constants.KEY_MESSAGE);
                    chatMessage.dataTime = getReadableDateTime(documentChange.getDocument().getDate(Constants.KEY_TIMESTAMP));
                    chatMessage.dateObject = documentChange.getDocument().getDate(Constants.KEY_TIMESTAMP);
                    chatMessages.add(chatMessage);
                }
            }
            Collections.sort(chatMessages, (obj1, obj2) -> obj1.dateObject.compareTo(obj2.dateObject));
            if (count == 0) {
                chatAdapter.notifyDataSetChanged();
            } else {
                chatAdapter.notifyItemRangeInserted(chatMessages.size(), chatMessages.size());
                chatRecyclerView.smoothScrollToPosition(chatMessages.size() - 1);
            }
            chatRecyclerView.setVisibility(View.VISIBLE);
        }
        progressBar.setVisibility(View.GONE);
        if(conversionId == null){
            checkForConversion();
        }
    };


    private String getReadableDateTime(Date date) {
        return new SimpleDateFormat("MMMM dd, yyyy - hh:mm a", Locale.getDefault()).format(date);
    }

    private void addConversion(HashMap<String, Object> conversion){
        database.collection(Constants.KEY_COLLECTION_CONVERSATIONS)
                .add(conversion)
                .addOnSuccessListener(documentReference -> conversionId = documentReference.getId());
    }

    private void updateConversion(String message, String senderName, String receiverName, String receiverId, String senderId){
        DocumentReference documentReference = database.collection(Constants.KEY_COLLECTION_CONVERSATIONS).document(conversionId);
        documentReference.update (Constants.KEY_SENDER_NAME, senderName,
                                  Constants.KEY_RECEIVER_NAME, receiverName,
                                  Constants.KEY_RECEIVER_ID, receiverId,
                                  Constants.KEY_SENDER_ID , senderId,
                                  Constants.KEY_LAST_MESSAGE, message,
                                  Constants.KEY_TIMESTAMP, new Date());
    }*/


    /*private Bitmap getBitmapFromEncodedString(String encodedImage){
        byte[] bytes = Base64.decode(encodedImage, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }*/
    /*
    private void checkForConversion() {
        if (chatMessages.size() != 0) {
            checkForConversionRemotely(
                    preferanceManager.getString(Constants.KEY_USER_ID),
                    receiverUser.id
            );

            checkForConversionRemotely(
                    receiverUser.id,
                    preferanceManager.getString(Constants.KEY_USER_ID)
            );
        }
    }

    private void checkForConversionRemotely(String senderId, String receiverId) {
        database.collection(Constants.KEY_COLLECTION_CONVERSATIONS)
                .whereEqualTo(Constants.KEY_SENDER_ID, senderId)
                .whereEqualTo(Constants.KEY_RECEIVER_ID, receiverId)
                .get()
                .addOnCompleteListener(conversionOnCompleteListener);
    }

    private final OnCompleteListener<QuerySnapshot> conversionOnCompleteListener = task -> {
        if (task.isSuccessful() && task.getResult() != null && task.getResult().getDocuments().size() > 0) {
            DocumentSnapshot documentSnapshot = task.getResult().getDocuments().get(0);
            conversionId = documentSnapshot.getId();
        }
    };

    @Override
    public void onResume() {
        super.onResume();
        listenAvailabilityOfReceiver();
    }
     */
}