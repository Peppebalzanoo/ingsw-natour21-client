package com.example.natour2.utilities;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;

import io.ticofab.androidgpxparser.parser.GPXParser;
import io.ticofab.androidgpxparser.parser.domain.Gpx;
import io.ticofab.androidgpxparser.parser.domain.Track;
import io.ticofab.androidgpxparser.parser.domain.TrackPoint;
import io.ticofab.androidgpxparser.parser.domain.TrackSegment;
import io.ticofab.androidgpxparser.parser.domain.WayPoint;

public class MapViewCustom extends com.google.android.gms.maps.MapView{

    private RecyclerView parent;
    public MapViewCustom(@NonNull Context context) {
        super(context);
    }

    public MapViewCustom(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MapViewCustom(@NonNull Context context, @Nullable GoogleMapOptions options) {
        super(context, options);
    }

    public MapViewCustom(@NonNull Context context, @NonNull AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setParent(RecyclerView recyclerView){
        parent = recyclerView;
    }

    @Override
    public boolean onInterceptTouchEvent(final MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (null == parent) {
                    getParent().requestDisallowInterceptTouchEvent(true);
                } else {
                    parent.requestDisallowInterceptTouchEvent(true);
                }
                break;
            case MotionEvent.ACTION_UP:
                if (null == parent) {
                    getParent().requestDisallowInterceptTouchEvent(false);
                } else {
                    parent.requestDisallowInterceptTouchEvent(false);
                }
                break;
            default:
                break;
        }

        return super.onInterceptTouchEvent(event);
    }

    public void getMap(GoogleMap googleMap, InputStream in){
        GPXParser parser = new GPXParser(); // consider injection

        Gpx parsedGpx = null;
        try {

            parsedGpx = parser.parse(in); // consider using a background thread
        } catch (IOException | XmlPullParserException e) {
            e.printStackTrace();
        }
        if (parsedGpx == null) {
            return;
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
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(wayPoint1.getLatitude(), wayPoint1.getLongitude()), 12.0f));

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

}
