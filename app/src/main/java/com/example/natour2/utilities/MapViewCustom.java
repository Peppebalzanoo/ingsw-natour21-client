package com.example.natour2.utilities;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.maps.GoogleMapOptions;

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

}
