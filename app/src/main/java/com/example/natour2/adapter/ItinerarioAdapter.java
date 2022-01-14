package com.example.natour2.adapter;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
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
import com.example.natour2.utilities.MapViewCustom;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import io.ticofab.androidgpxparser.parser.GPXParser;
import io.ticofab.androidgpxparser.parser.domain.Gpx;
import io.ticofab.androidgpxparser.parser.domain.Track;
import io.ticofab.androidgpxparser.parser.domain.TrackPoint;
import io.ticofab.androidgpxparser.parser.domain.TrackSegment;

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
        System.out.println("***************************************** Sono in create");
        View view = LayoutInflater.from(mContext).inflate(R.layout.itinerario_item, parent, false);
        return new ItinerarioAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        System.out.println("***************************************** Sono in Bind");
        Itinerario itr = mItinerario.get(position);
        publisherInfo(holder.username, holder.name, holder.durata, holder.diff, holder.descrizione, holder.mapView, itr);
    }

    @Override
    public int getItemCount() {
        System.out.println("***************************************** Sono in Item");
        return mItinerario.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView profileImage;
        public TextView username, name, durata, diff, descrizione, segnala, contatta;
        public Button dettaglio;
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
            contatta = itemView.findViewById(R.id.contatta);
            // dettaglio = itemView.findViewById(R.id.dettaglio);
            mapView = itemView.findViewById(R.id.mapView);

        }
    }

    private void drawPrimaryLinePath(GoogleMap map, LatLng start, LatLng end )
    {
        if ( map == null )
        {
            return;
        }

        PolylineOptions options = new PolylineOptions();

        options.color( Color.parseColor( "#CC0000FF" ) );
        options.width( 5 );
        options.visible( true );


        options.add( start );
        options.add( end );

        map.addPolyline( options );

    }

    private void publisherInfo(final TextView username, final TextView name, final TextView durata, final TextView diff, final TextView descrizione, final MapViewCustom mapView, Itinerario itr ){

        username.setText(itr.getAutore());
        name.setText("Nome: " + itr.getName());
        durata.setText("Durata: " + itr.getDurata());
        diff.setText("Difficoltà: " + itr.getDiff());
        descrizione.setText("Il sentiero è motlo bello, basta solo essere un minimo allenato per poterlo finiere senza problemi");

        mapView.getMapAsync(new OnMapReadyCallback() {

            @Override
            public void onMapReady(@NonNull GoogleMap googleMap) {

                GPXParser parser = new GPXParser(); // consider injection
                Gpx parsedGpx = null;
                try {
                    InputStream in = mContext.getResources().openRawResource(
                            mContext.getResources().getIdentifier("mapstogpx20220113_210828",
                                    "raw", mContext.getPackageName()));

                    parsedGpx = parser.parse(in); // consider using a background thread
                } catch (IOException | XmlPullParserException e) {
                    // do something with this exception
                    e.printStackTrace();
                }
                if (parsedGpx == null) {
                    System.out.println("????????????????????????????????????????????????????????????Errore");
                } else {
                    LatLng prec = new LatLng(parsedGpx.getTracks().get(0).getTrackSegments().get(0).getTrackPoints().get(0).getLatitude(), parsedGpx.getTracks().get(0).getTrackSegments().get(0).getTrackPoints().get(0).getLongitude());
                    for(Track t: parsedGpx.getTracks()){
                        for(TrackSegment ts: t.getTrackSegments()){
                            for(TrackPoint tp: ts.getTrackPoints()){
                                LatLng curr = new LatLng(tp.getLatitude(), tp.getLongitude());
                                drawPrimaryLinePath(googleMap, prec, curr);
                                prec = curr;
                            }
                        }
                    }
                }
                WayPoint wayPoint1 = parsedGpx.getWayPoints().get(0);
                WayPoint wayPoint2 = parsedGpx.getWayPoints().get(1);
                MarkerOptions place1 = new MarkerOptions().position(new LatLng(wayPoint1.getLatitude(), wayPoint1.getLongitude())).title("Location 1");
                MarkerOptions place2 = new MarkerOptions().position(new LatLng(wayPoint2.getLatitude(), wayPoint2.getLongitude())).title("Location 2");
                googleMap.addMarker(place1);
                googleMap.addMarker(place2);
                googleMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(wayPoint1.getLatitude(), wayPoint1.getLongitude())));
                mapView.onResume();
                mapView.onEnterAmbient(null);
            }
        });
    }

}
