package com.example.fakebook.adapters;

import android.content.Context;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.fakebook.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class FriendProfileAdapter extends RecyclerView.Adapter<FriendProfileAdapter.MyViewHolder> {
    ArrayList<String> list;
    Context context;

    public FriendProfileAdapter(ArrayList<String> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.row_friend_profile,parent,false);
        MyViewHolder vh=new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.binding(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        View view;
        CircleImageView imgAvatar;
        TextView txtName;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            view=itemView;
        }
        public void binding(final String email)
        {
            imgAvatar=(CircleImageView) view.findViewById(R.id.img_avatar_friend_profile);
            txtName=(TextView) view.findViewById(R.id.txt_name_friend_profile);
//                    AsyncTask.execute(new Runnable() {
//                        @Override
//                        public void run() {
//                            FirebaseFirestore firebaseFirestore=FirebaseFirestore.getInstance();
//                            firebaseFirestore.collection("Users")
//                                    .document(email)
//                                    .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//                                @Override
//                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                                    User user=task.getResult().toObject(User.class);
//                                    txtName.setText(user.toString());
//                                    Glide.with(context)
//                                            .load(user.getAvatar())
//                                            .fitCenter()
//                                            .placeholder(R.drawable.default_image)
//                                            .into(imgAvatar);
//                                }
//                            });
//                        }
//                    });
        }
    }
}
