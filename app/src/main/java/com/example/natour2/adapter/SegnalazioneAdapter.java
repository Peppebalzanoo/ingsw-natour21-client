package com.example.natour2.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.natour2.R;

import java.util.List;

public class SegnalazioneAdapter extends RecyclerView.Adapter<SegnalazioneAdapter.ViewHolder>{

    private Context context;
    private List<String> fruitList;
    public Bundle savedInstanceState;
    public RecyclerView recyclerView;

    private OnDrawableListener mOnDrawableListener;

    public SegnalazioneAdapter(OnDrawableListener onDrawableListener, Context context, List<String> fruitList, Bundle savedInstanceState, RecyclerView recyclerView){
        this.context = context;
        this.fruitList = fruitList;
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
        String currentFruit = fruitList.get(position);

        holder.textFruit.setText(currentFruit);
    }

    @Override
    public int getItemCount() {
        return fruitList.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView textFruit;
        public ImageView iconImage;

        OnDrawableListener onDrawableListener;

        public ViewHolder(@NonNull View itemView, OnDrawableListener onDrawableListener) {
            super(itemView);
            initViewComponents(itemView);
            this.onDrawableListener = onDrawableListener;

            itemView.setOnClickListener(this);
        }

        private void initViewComponents(View itemView) {
            textFruit = itemView.findViewById(R.id.textView_CostumListView);
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


}
