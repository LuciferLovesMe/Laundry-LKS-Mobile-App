package com.abim.laundrylks;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class NotifAdapter extends RecyclerView.Adapter<NotifAdapter.ViewHolder> {
    List<Notif> notifs;
    Context ctx;

    public NotifAdapter(List<Notif> notifs, Context ctx) {
        this.notifs = notifs;
        this.ctx = ctx;
    }

    @NonNull
    @Override
    public NotifAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.card_row, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull  NotifAdapter.ViewHolder holder, int position) {
        holder.tv_desc.setText(notifs.get(position).getName() + " Telah Selesai.");
        holder.tv_date.setText(String.valueOf(notifs.get(position).getDate()));
    }

    @Override
    public int getItemCount() {
        return notifs.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tv_desc, tv_date;

        public ViewHolder( View itemView) {
            super(itemView);

            tv_desc = itemView.findViewById(R.id.tv_desc);
            tv_date = itemView.findViewById(R.id.tv_date);
        }
    }
}
