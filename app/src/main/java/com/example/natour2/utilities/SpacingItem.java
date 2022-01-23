package com.example.natour2.utilities;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SpacingItem extends RecyclerView.ItemDecoration{
    private int veritcalSpace;

    public SpacingItem(int veritcalSpace){
        this.veritcalSpace = veritcalSpace;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        outRect.bottom = veritcalSpace;
        outRect.top = veritcalSpace;
    }
}
