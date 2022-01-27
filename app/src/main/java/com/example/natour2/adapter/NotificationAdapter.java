package com.example.natour2.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.natour2.R;
import com.example.natour2.model.Report;

import java.util.List;

public class NotificationAdapter extends  RecyclerView.Adapter<NotificationAdapter.ViewHolder>{

    private Context context;
    private List<Report> reportList;

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

        //holder.textItinerario.setText(currenReport.getItinerary().getName());
        holder.textUserSegnalatore.setText(currenReport.getReporter().getUsername());
        //holder.imageProfileSegnalatore.setImageDrawable("Immagine del segnalatore");
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

}
