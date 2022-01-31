package com.example.natour2.adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.natour2.controller.ControllerUser;
import com.example.natour2.databinding.ItemContainerRecentConverationBinding;
import com.example.natour2.listeners.ConversionListener;
import com.example.natour2.model.ChatMessage;
import com.example.natour2.model.User;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class RecentConversationsAdapter extends RecyclerView.Adapter<RecentConversationsAdapter.ConversionViewHoled>{

    private final List<ChatMessage> chatMessages;
    private final ConversionListener conversionListener;
    private final ControllerUser ctrlUser = ControllerUser.getInstance();

    public RecentConversationsAdapter(List<ChatMessage> chatMessages, ConversionListener conversionListener) {
        this.chatMessages = chatMessages;
        this.conversionListener = conversionListener;
    }

    @NonNull
    @Override
    public ConversionViewHoled onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ConversionViewHoled(ItemContainerRecentConverationBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false
        ));
    }

    @Override
    public void onBindViewHolder(@NonNull ConversionViewHoled holder, int position) {
        holder.setData(chatMessages.get(position));
    }

    @Override
    public int getItemCount() {
        return chatMessages.size();
    }

    class ConversionViewHoled extends RecyclerView.ViewHolder{
        ItemContainerRecentConverationBinding binding;

        ConversionViewHoled(ItemContainerRecentConverationBinding itemContainerRecentConverationBinding){
            super(itemContainerRecentConverationBinding.getRoot());
            binding = itemContainerRecentConverationBinding;
        }

        void setData(ChatMessage chatMessage){
            //binding.imageProfile.setImageBitmap(getConversionImage(chatMessage.conversionImage));
            binding.textName.setText(chatMessage.getConversionName());
            binding.textRecentMessage.setText(chatMessage.getMessage());
            showImage(chatMessage.getConversionName(), binding.imageProfileRecentConversation);
            binding.getRoot().setOnClickListener(v -> {
                User user = new User();
                //user.setIdString(chatMessage.getConversionId());
                user.setUsername(chatMessage.getConversionName());
                //user.image = chatMessage.conversionImage;
                conversionListener.onConversionClicked(user);
            });
        }
    }



    private void showImage(String username,ImageView imageProfile){
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                   String imagePath = ctrlUser.getUserByUsername(username).getProfileImagePath();

                    URL url = null;
                    url = new URL(imagePath);
                    Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                    imageProfile.setImageBitmap(bmp);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        t1.start();
        try {
            t1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private Bitmap getConversionImage(String encodedImage){
        byte [] bytes = Base64.decode(encodedImage, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }
}
