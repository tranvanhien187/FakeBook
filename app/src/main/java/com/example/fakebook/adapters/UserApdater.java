package com.example.fakebook.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.fakebook.MainActivity;
import com.example.fakebook.R;
import com.example.fakebook.activities.ProfileUser;
import com.example.fakebook.model.SearchUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserApdater extends RecyclerView.Adapter<UserApdater.ViewHolder> {
    private List<SearchUser> mainList;
    public Context context;
    private View v;
    public UserApdater(List<SearchUser> mainList){
        this.mainList=mainList;
    }
    @NonNull
    @Override
    public UserApdater.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item,parent,false);
        context=parent.getContext();
        return new UserApdater.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserApdater.ViewHolder holder, final int position) {
        String avatar=mainList.get(position).getAvatar();
        String name=mainList.get(position).getName();
        holder.setUp(avatar,name);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent profileIntent=new Intent(v.getContext(), ProfileUser.class);
                Toast.makeText(context, "email : "+mainList.get(position).getEmail(), Toast.LENGTH_SHORT).show();
                profileIntent.putExtra("email",mainList.get(position).getEmail());
                v.getContext().startActivity(profileIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mainList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvName;
        private CircleImageView imgAvatar;
        private CardView cardView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            v=itemView;
            tvName=v.findViewById(R.id.tv_name);
            imgAvatar=v.findViewById(R.id.avatar);
            cardView=v.findViewById(R.id.cardview);
        }
        public void setUp(String avatar,String name){
            RequestOptions requestOptions=new RequestOptions();
            requestOptions.placeholder(R.drawable.default_image);
            Glide.with(context).load(avatar).into(imgAvatar);
            tvName.setText(name);
        }
    }
}
