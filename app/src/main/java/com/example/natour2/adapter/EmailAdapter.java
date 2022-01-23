package com.example.natour2.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.natour2.R;
import com.example.natour2.model.Email;

import java.util.List;

public class EmailAdapter extends RecyclerView.Adapter<EmailAdapter.ViewHolder> {

    private Context context;
    private List<Email> emailList;

    public Bundle savedInstanceState;
    public RecyclerView recyclerView;

    private OnEmailListener mOnEmailListener;


    public EmailAdapter(OnEmailListener onEmailListener, Context context, List<Email> emailList, Bundle savedInstanceState, RecyclerView recyclerView){
        this.context = context;
        this.emailList = emailList;
        this.savedInstanceState = savedInstanceState;
        this.recyclerView = recyclerView;
        this.mOnEmailListener = onEmailListener;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.email_item, parent, false);
        return new EmailAdapter.ViewHolder(view, mOnEmailListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Email currentEmail = emailList.get(position);

        holder.textEmail.setText(currentEmail.getEmail());
        holder.checkBox.setChecked(currentEmail.getCheckStatus());
    }

    @Override
    public int getItemCount() {
       return emailList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView textEmail;
        public CheckBox checkBox;

        OnEmailListener onEmailListener;

        public ViewHolder(@NonNull View itemView, OnEmailListener onEmailListener) {
            super(itemView);
            initViewComponents(itemView);
            this.onEmailListener = onEmailListener;

            itemView.setOnClickListener(this);
        }

        private void initViewComponents(View itemView) {
            textEmail = itemView.findViewById(R.id.textView_EmailItem);
            checkBox = itemView.findViewById(R.id.checkBox_EmailItem);
        }

        @Override
        public void onClick(View v) {
            onEmailListener.onEmailClick(getAbsoluteAdapterPosition());
        }
    }

    public interface OnEmailListener{
        void onEmailClick(int position);
    }


}
