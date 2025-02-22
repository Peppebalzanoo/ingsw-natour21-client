package com.example.natour2.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import com.example.natour2.controller.ControllerItinerary;
import com.example.natour2.model.Itinerary;
import com.example.natour2.model.PointOfInterest;
import com.example.natour2.utilities.MapViewCustom;
import com.example.natour2.utilities.SharedPreferencesUtil;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ItinerarioAdapter extends RecyclerView.Adapter<ItinerarioAdapter.ViewHolder> {

    private Activity activity;
    private Context mContext;
    private List<Itinerary> mItinerary;
    private Bundle savedInstanceState;
    private RecyclerView recyclerView;

    private final ControllerHomeActivity ctrl = ControllerHomeActivity.getInstance();
    private final ControllerItinerary ctrlItinerary = ControllerItinerary.getInstance();


    public ItinerarioAdapter(Activity activity, Context context, List<Itinerary> mItinerary, Bundle savedInstanceState, RecyclerView recyclerView){
        this.activity = activity;
        this.mContext = context;
        this.mItinerary = mItinerary;
        this.savedInstanceState = savedInstanceState;
        this.recyclerView = recyclerView;

        ctrlItinerary.setActivity(activity);
        ctrlItinerary.setContext(context);
    }

    public ItinerarioAdapter(Activity activity, Context context, Bundle savedInstanceState, RecyclerView recyclerView){
        this.activity = activity;
        this.mContext = context;
        this.savedInstanceState = savedInstanceState;
        this.recyclerView = recyclerView;
        mItinerary = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.itinerario_item, parent, false);
        return new ItinerarioAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Itinerary itr = mItinerary.get(position);
        publisherInfo(holder.username, holder.name, holder.durata, holder.diff, holder.descrizione, holder.mapView, holder.imageViewPointOfInterest, holder.profileImage, holder.segnala, holder.infoDettaglio, itr);
    }

    @Override
    public int getItemCount() {
        return mItinerary.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView profileImage, infoDettaglio, imageViewPointOfInterest;
        public TextView username, name, durata, diff, descrizione, segnala;
        public MapViewCustom mapView;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            initViewComponents(itemView);
            mapView.setParent(recyclerView);
            mapView.onCreate(savedInstanceState);
        }

        private void initViewComponents(View itemView) {

            profileImage = itemView.findViewById(R.id.imageProfileRecentConversation);
            username = itemView.findViewById(R.id.username);
            name = itemView.findViewById(R.id.name);
            durata = itemView.findViewById(R.id.durata);
            diff = itemView.findViewById(R.id.difficolta);
            descrizione = itemView.findViewById(R.id.descrizione);
            segnala = itemView.findViewById(R.id.textViewSegnala);
            mapView = itemView.findViewById(R.id.mapView);
            infoDettaglio = itemView.findViewById(R.id.imageViewDettaglio);
            imageViewPointOfInterest = itemView.findViewById(R.id.imageViewPointOfInterest);

            segnala.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ctrl.showInviaSegnalazioneFragment(mItinerary.get(getAdapterPosition()));
                }
            });

            infoDettaglio.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //System.out.println("***************************************************+ Potition " + mItinerary.get(getAdapterPosition()).getName());
                }
            });

            imageViewPointOfInterest.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ctrl.showAddPointOfInterestFragment(mItinerary.get(getAdapterPosition()));
                }
            });


        }
    }



    private void publisherInfo(final TextView username, final TextView name, final TextView durata, final TextView diff, final TextView descrizione, final MapViewCustom mapView, ImageView imageViewPointOfInterest, ImageView imageProfile, final TextView segnala, ImageView infoDettaglio, Itinerary itr ){

        username.setText(itr.getAuthor().getUsername());
        name.setText(itr.getName());
        durata.setText(setDuration(itr.getDuration()));
        diff.setText(setDifficulty(itr.getDifficulty()));
        descrizione.setText(itr.getDescription());

        showImage(itr.getAuthor().getProfileImagePath(), imageProfile);

        showPoitOfInterest(itr, imageViewPointOfInterest, segnala);

        showWarning(itr, infoDettaglio);

        mapView.getMapAsync(new OnMapReadyCallback() {


            @Override
            public void onMapReady(@NonNull GoogleMap googleMap) {
                InputStream in = convertStringToInputStream(itr.getGpx());
                mapView.getMap(googleMap, in);
                if(itr.getPointsOfInterest()!=null && itr.getPointsOfInterest().size() > 0){
                    for(PointOfInterest pointOfInterest: itr.getPointsOfInterest()){
                        LatLng start = new LatLng(pointOfInterest.getCoordY(), pointOfInterest.getCoordX());
                        MarkerOptions options = new MarkerOptions();
                        options.position(start);
                       // options.icon(bitmapDescriptorFromVector(mContext, R.drawable.ic_home));
                        retrievePointOfInterest(options, pointOfInterest);

                        googleMap.addMarker(options);
                    }
                }
                mapView.onResume();
                mapView.onEnterAmbient(null);
            }
        });
    }


    private void retrievePointOfInterest(MarkerOptions options, PointOfInterest pointOfInterest){
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = null;
                    url = new URL(pointOfInterest.getTypeIcon());
                    Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                    Bitmap smallMarker = Bitmap.createScaledBitmap(bmp, 70, 70, false);
                    options.icon(BitmapDescriptorFactory.fromBitmap(smallMarker));
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


    private void showWarning(Itinerary itinerary, ImageView infoDettaglio){
        if(itinerary.getReports().size() == 0){
            infoDettaglio.setVisibility(View.INVISIBLE);
            return;
        }
        infoDettaglio.setVisibility(View.VISIBLE);
    }

    private void showImage(String imagePath, ImageView imageProfile){
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
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

    private void showPoitOfInterest(Itinerary itr, ImageView imageViewPointOfInterest, TextView segnala){
        String s1 = itr.getAuthor().getUsername();
        String s2 = SharedPreferencesUtil.getStringPreference(activity, "USERNAME");
        if(s1.equals(s2)){
            imageViewPointOfInterest.setVisibility(View.VISIBLE);
            imageViewPointOfInterest.setClickable(true);
            segnala.setVisibility(View.INVISIBLE);
            segnala.setClickable(false);
        } else {
            imageViewPointOfInterest.setVisibility(View.INVISIBLE);
            imageViewPointOfInterest.setClickable(false);
            segnala.setVisibility(View.VISIBLE);
            segnala.setClickable(true);
        }
    }

    public String setDuration(int duration){
        if(duration <= 0 || duration > 1439){
            throw new IllegalArgumentException();
        }
        int hours = duration / 60;
        int minutes = duration % 60;
        String res = ""+minutes;
        if(minutes < 10){
            res = "0"+minutes;
        }
        return hours+":"+res;
    }

    private String setDifficulty(int difficuly){
        if(difficuly == 1){
            return "Facile";
        } else if(difficuly == 2){
            return "Normale";
        } else {
            return "Difficile";
        }
    }

    private InputStream convertStringToInputStream(String string) {
        InputStream inputStream = new ByteArrayInputStream(string.getBytes());
        return inputStream;
    }

    public void setItineraryList(List<Itinerary> itineraryList){
        this.mItinerary = itineraryList;
        notifyDataSetChanged();
    }

}
