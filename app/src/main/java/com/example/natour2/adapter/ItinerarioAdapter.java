package com.example.natour2.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.natour2.R;
import com.example.natour2.controller.ControllerHomeActivity;
import com.example.natour2.model.Itinerario;
import com.example.natour2.utilities.MapViewCustom;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.InputStream;
import java.util.List;

public class ItinerarioAdapter extends RecyclerView.Adapter<ItinerarioAdapter.ViewHolder> {

    public Context mContext;
    public List<Itinerario> mItinerario;
    public Bundle savedInstanceState;
    public RecyclerView recyclerView;

    public ItinerarioAdapter(Context context, List<Itinerario> mItinerario, Bundle savedInstanceState, RecyclerView recyclerView){
        this.mContext = context;
        this.mItinerario = mItinerario;
        this.savedInstanceState = savedInstanceState;
        this.recyclerView = recyclerView;
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
        publisherInfo(holder.username, holder.name, holder.durata, holder.diff, holder.descrizione, holder.mapView, itr);
    }

    @Override
    public int getItemCount() {
        return mItinerario.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView profileImage, infoDettaglio;
        public TextView username, name, durata, diff, descrizione, segnala, dettaglio;
        public MapViewCustom mapView;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            initViewComponents(itemView);
            mapView.setParent(recyclerView);
            mapView.onCreate(savedInstanceState);
        }

        private void initViewComponents(View itemView) {

            profileImage = itemView.findViewById(R.id.imageProfile);
            username = itemView.findViewById(R.id.username);
            name = itemView.findViewById(R.id.name);
            durata = itemView.findViewById(R.id.durata);
            diff = itemView.findViewById(R.id.difficolta);
            descrizione = itemView.findViewById(R.id.descrizione);
            segnala = itemView.findViewById(R.id.segnala);
            dettaglio = itemView.findViewById(R.id.textViewDettaglio);
            mapView = itemView.findViewById(R.id.mapView);
            infoDettaglio = itemView.findViewById(R.id.imageViewDettaglio);

            dettaglio.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //System.out.println("***************************************************+ Potition " + mItinerario.get(getAdapterPosition()).getName());
                    ControllerHomeActivity.getInstance().pippo();
                }
            });

            infoDettaglio.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    System.out.println("***************************************************+ Potition " + mItinerario.get(getAdapterPosition()).getName());
                }
            });


        }
    }



    private void publisherInfo(final TextView username, final TextView name, final TextView durata, final TextView diff, final TextView descrizione, final MapViewCustom mapView, Itinerario itr ){

        username.setText(itr.getAutore());
        name.setText(itr.getName());
        durata.setText(itr.getDurata());
        diff.setText(itr.getDiff());
        descrizione.setText("Il sentiero è motlo bello, basta solo essere un minimo allenato per poterlo finiere senza problemi");

        mapView.getMapAsync(new OnMapReadyCallback() {


            @Override
            public void onMapReady(@NonNull GoogleMap googleMap) {
                InputStream in = mContext.getResources().openRawResource(
                        mContext.getResources().getIdentifier("mapstogpx20220113_210828",
                                "raw", mContext.getPackageName()));

                LatLng start = new LatLng(40.7753594015775, 14.46880965563265);
                MarkerOptions options = new MarkerOptions();
                options.position(start);
                //options.icon(BitmapDescriptorFactory.fromResource(mContext.getResources().getIdentifier("ic_1","drawable", mContext.getPackageName())));
                options.icon(bitmapDescriptorFromVector(mContext, R.drawable.ic_home));
                googleMap.addMarker(options);

                mapView.getMap(googleMap, in);
                mapView.onResume();
                mapView.onEnterAmbient(null);
            }
        });
    }


    private BitmapDescriptor bitmapDescriptorFromVector(Context context, @DrawableRes int vectorDrawableResourceId) {
        Drawable background = ContextCompat.getDrawable(context, R.drawable.ic_home);
        background.setBounds(0, 0, background.getIntrinsicWidth(), background.getIntrinsicHeight());
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorDrawableResourceId);
        vectorDrawable.setBounds(40, 20, vectorDrawable.getIntrinsicWidth() + 40, vectorDrawable.getIntrinsicHeight() + 20);
        Bitmap bitmap = Bitmap.createBitmap(background.getIntrinsicWidth(), background.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        background.draw(canvas);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }

}
