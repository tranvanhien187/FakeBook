package com.example.fakebook.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.fakebook.R;
import com.example.fakebook.model.Notification;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

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
        CircleImageView imgAvatar;
        TextView tvContent, tvTime;
        FirebaseFirestore firebaseFirestore=FirebaseFirestore.getInstance();
        FirebaseAuth mAuth=FirebaseAuth.getInstance();
        String emailTmp=mAuth.getCurrentUser().getEmail();
        String emailCurUser=emailTmp.substring(0,emailTmp.length()-"@gmail.com".length());

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
        }

        public void binding(final Notification notification) {
            tvContent = (TextView) view.findViewById(R.id.tv_row_notification_content);
            tvTime = (TextView) view.findViewById(R.id.tv_row_notification_time);
            imgAvatar = (CircleImageView) view.findViewById(R.id.img_row_notification_avatar);
            //tvContent.setText(notification.getContent());
            tvTime.setText(notification.getTime());
            //imgAvatar.setImageResource(notification.getAvatar());
            firebaseFirestore.collection("Users").document(notification.getEmail())
                    .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    tvContent.setText(task.getResult().get("name")+notification.getContent());
                    Glide.with(context)
                            .load(task.getResult().get("avatar")+"")
                            .fitCenter()
                            .placeholder(R.drawable.default_image)
                            .into(imgAvatar);
                }
            });
        }
    }
}
