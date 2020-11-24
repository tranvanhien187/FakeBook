package com.example.fakebook.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fakebook.R;
import com.example.fakebook.model.Notification;

import java.util.ArrayList;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {

    ArrayList<Notification> notificationArrayList;
    Context context;

    public NotificationAdapter(ArrayList<Notification> notificationArrayList, Context context) {
        this.notificationArrayList = notificationArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.row_notification, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.binding(notificationArrayList.get(position));
    }

    @Override
    public int getItemCount() {
        return notificationArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        View view;
        ImageView imgAvatar;
        TextView tvContent, tvTime;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
        }

        public void binding(Notification notification) {
            tvContent = (TextView) view.findViewById(R.id.tv_row_notification_content);
            tvTime = (TextView) view.findViewById(R.id.tv_row_notification_time);
            imgAvatar = (ImageView) view.findViewById(R.id.img_row_notification_avatar);
            tvContent.setText(notification.getContent());
            tvTime.setText(notification.getTime());
            imgAvatar.setImageResource(notification.getAvatar());
        }
    }
}
