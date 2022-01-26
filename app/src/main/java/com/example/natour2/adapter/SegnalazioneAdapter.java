package com.example.natour2.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.natour2.R;
import com.example.natour2.model.Report;

import java.util.List;

public class SegnalazioneAdapter extends RecyclerView.Adapter<SegnalazioneAdapter.ViewHolder>{

    private Context context;
    private List<Report> reportList;
    public Bundle savedInstanceState;
    public RecyclerView recyclerView;

    private OnDrawableListener mOnDrawableListener;

    public SegnalazioneAdapter(OnDrawableListener onDrawableListener, Context context, List<Report> reportList, Bundle savedInstanceState, RecyclerView recyclerView){
        this.context = context;
        this.reportList = reportList;
        this.savedInstanceState = savedInstanceState;
        this.recyclerView = recyclerView;
        this.mOnDrawableListener = onDrawableListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.segnalazione_item, parent, false);
        return new SegnalazioneAdapter.ViewHolder(view, mOnDrawableListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Report report = reportList.get(position);
        publisherInfo(holder.textNomeItinerario, holder.textTitoloSegnalazione, holder.textReporter, holder.textSegnalato, report);

    }

    @Override
    public int getItemCount() {
        return reportList.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
         TextView textNomeItinerario;
         TextView textTitoloSegnalazione;
         TextView textReporter;
         TextView textSegnalato;
         ImageView iconImage;
         OnDrawableListener onDrawableListener;


        public ViewHolder(@NonNull View itemView, OnDrawableListener onDrawableListener) {
            super(itemView);
            initViewComponents(itemView);
            this.onDrawableListener = onDrawableListener;
            itemView.setOnClickListener(this);
        }

        private void initViewComponents(View itemView) {
            textNomeItinerario = itemView.findViewById(R.id.textViewNomeItinerario);
            textTitoloSegnalazione = itemView.findViewById(R.id.textViewTitoloSegnalazione);
            textReporter = itemView.findViewById(R.id.textViewReporter);
            textSegnalato = itemView.findViewById(R.id.textViewSegnalato);
            iconImage = itemView.findViewById(R.id.imageView_CostumListView);
            iconImage.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_2));
        }

        @Override
        public void onClick(View v) {
            onDrawableListener.onDrawableClick(getAbsoluteAdapterPosition());
        }
    }
    public interface OnDrawableListener{
        void onDrawableClick(int position);
    }

    private void publisherInfo(final TextView textNomeItinerario, final TextView textTitoloSegnalazione, final TextView textReporter, final TextView textSegnalato, Report report) {
        textNomeItinerario.setText(report.getItinerary().getName());
        textTitoloSegnalazione.setText(report.getReasonTitle());
        textReporter.setText(report.getReporter().getUsername());
        textSegnalato.setText(report.getItinerary().getAuthor().getUsername());

    }



}
