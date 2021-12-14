package com.example.natour2.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.natour2.R;
import com.example.natour2.model.Itinerario;

import java.util.List;

public class ItinerarioAdapter extends RecyclerView.Adapter<ItinerarioAdapter.ViewHolder> {

    public Context mContext;
    public List<Itinerario> mItinerario;

    public ItinerarioAdapter(Context context, List<Itinerario> mItinerario){
        this.mContext = context;
        this.mItinerario = mItinerario;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.itinerario_item, parent, false);
        return new ItinerarioAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Itinerario itr = mItinerario.get(position);
        publisherInfo(holder.username, holder.name, holder.durata, holder.diff, holder.descrizione);
    }

    @Override
    public int getItemCount() {
        return mItinerario.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public ImageView profileImage;
        public TextView username, name, durata, diff, descrizione, segnala, contatta;
        public Button dettaglio;


        public ViewHolder(@NonNull View itemView){
            super(itemView);

            profileImage = itemView.findViewById(R.id.imageProfile);
            username = itemView.findViewById(R.id.username);
            name = itemView.findViewById(R.id.name);
            durata = itemView.findViewById(R.id.durata);
            diff = itemView.findViewById(R.id.difficolta);
            descrizione = itemView.findViewById(R.id.descrizione);
            segnala = itemView.findViewById(R.id.segnala);
            contatta = itemView.findViewById(R.id.contatta);
            // dettaglio = itemView.findViewById(R.id.dettaglio);
        }
    }

    private void publisherInfo(final TextView username, final TextView name, final TextView durata, final TextView diff, final TextView descrizione ){
        username.setText("Antoino");
        name.setText("Nome: Sentiero degli dei");
        durata.setText("Durata: 01:20");
        diff.setText("Difficoltà: Difficile");
        descrizione.setText("Il sentiero è motlo bello, basta solo essere un minimo allenato per poterlo finiere senza problemi");

    }

}
