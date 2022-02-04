package com.example.natour2.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.natour2.R;
import com.example.natour2.controller.ControllerHomeActivity;
import com.example.natour2.controller.ControllerUser;
import com.example.natour2.model.Itinerary;
import com.example.natour2.model.Report;
import com.example.natour2.utilities.SharedPreferencesUtil;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class NotificationAdapter extends  RecyclerView.Adapter<NotificationAdapter.ViewHolder>{

    private Context context;
    private List<Report> reportList;
    private Activity activity;
    private final ControllerUser ctrlUser = ControllerUser.getInstance();

    public Bundle savedInstanceState;
    public RecyclerView recyclerView;

    private OnNotificationListener mOnNotificationListener;

    public NotificationAdapter(OnNotificationListener onNotificationListener, Context context, List<Report> reportList, Bundle savedInstanceState, RecyclerView recyclerView){
        this.context = context;
        this.reportList = reportList;
        this.savedInstanceState = savedInstanceState;
        this.recyclerView = recyclerView;
        this.mOnNotificationListener = onNotificationListener;
    }

    public NotificationAdapter(OnNotificationListener onNotificationListener, Context context, Activity activity, Bundle savedInstanceState, RecyclerView recyclerView){
        this.context = context;
        this.reportList = new ArrayList<>();
        this.savedInstanceState = savedInstanceState;
        this.recyclerView = recyclerView;
        this.mOnNotificationListener = onNotificationListener;
        this.activity = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_notification, parent, false);
        return new NotificationAdapter.ViewHolder(view, mOnNotificationListener);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Report currenReport = reportList.get(position);

        /* Momentanea */
        holder.textItinerario.setText(currenReport.getReasonTitle());
        /* ********** */

        //holder.textItinerario.setText(currenReport.getItinerary().getName());
        holder.textUserSegnalatore.setText(currenReport.getReporter().getUsername());
        //holder.imageProfileSegnalatore.setImageDrawable("Immagine del segnalatore");
        showImage(currenReport.getReporter().getProfileImagePath(), holder.imageProfileSegnalatore);

    }

    @Override
    public int getItemCount() {
        return reportList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView textItinerario;
        public TextView textUserSegnalatore;
        private ImageView imageProfileSegnalatore;

        OnNotificationListener onNotificationListener;

        public ViewHolder(@NonNull View itemView, OnNotificationListener onNotificationListener) {
            super(itemView);
            initViewComponents(itemView);
            this.onNotificationListener = onNotificationListener;

            itemView.setOnClickListener(this);
        }

        private void initViewComponents(View itemView) {
            textItinerario = itemView.findViewById(R.id.textView_Itinerario_ItemNotification);
            textUserSegnalatore = itemView.findViewById(R.id.textView_Segnalatore_ItemNotification);
            imageProfileSegnalatore = itemView.findViewById(R.id.circleImageView_itemNotification);
        }

        @Override
        public void onClick(View v) {
            onNotificationListener.onNotificationClick(getAbsoluteAdapterPosition());
        }
    }

    public interface OnNotificationListener{
        void onNotificationClick(int position);
    }

    public void setReportListFromItineraryList(List<Itinerary> ItineraryList){
        reportList.removeAll(reportList);
        for(Itinerary itr: ItineraryList){
            if(itr.getAuthor().getUsername().equals(SharedPreferencesUtil.getStringPreference(activity, "USERNAME"))){
                for(Report r: itr.getReports()) {
                    if(r.getProvidedExplanation() == null || r.getProvidedExplanation().equals("")){
                        reportList.add(r);
                    }
                }
            }
        }
        notifyDataSetChanged();
    }

    public void showReplayReport(ControllerHomeActivity ctrl, int position){
        ctrl.showShowSegnalazioneFragment(reportList.get(position));
        notifyItemChanged(position);
    }


    private void showImage(String imagePath,ImageView imageProfile){
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    //String imagePath = ctrlUser.getUserByUsername(username).getProfileImagePath();

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

}
