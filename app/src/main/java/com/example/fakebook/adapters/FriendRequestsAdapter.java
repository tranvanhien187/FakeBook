package com.example.fakebook.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fakebook.R;
import com.example.fakebook.model.FriendRequests;

import java.util.ArrayList;

public class FriendRequestsAdapter extends RecyclerView.Adapter<FriendRequestsAdapter.ViewHolder> {

    ArrayList<FriendRequests> friendRequestsArrayList;
    Context context;

    public FriendRequestsAdapter(ArrayList<FriendRequests> friendRequestsArrayList, Context context) {
        this.friendRequestsArrayList = friendRequestsArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.row_friend_request,parent,false);
        ViewHolder vh=new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.binding(friendRequestsArrayList.get(position));
    }

    @Override
    public int getItemCount() {
        return friendRequestsArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        View view;
        ImageView imgAvatar;
        TextView tvTime,tvName,tvAcceptRequests,tvDeclineRequests;
        Button btnAccept,btnDecline;
        LinearLayout linearLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            view=itemView;
        }
        public void binding(FriendRequests friendRequests)
        {
            imgAvatar=(ImageView) view.findViewById(R.id.img_row_avatar_request);
            tvName=(TextView) view.findViewById(R.id.tv_row_name_request);
            tvTime=(TextView) view.findViewById(R.id.tv_row_time_request);
            tvAcceptRequests=(TextView) view.findViewById(R.id.tv_accept_request);
            tvDeclineRequests=(TextView) view.findViewById(R.id.tv_decline_request);
            btnAccept=(Button)  view.findViewById(R.id.btn_row_accept_request);
            btnDecline=(Button) view.findViewById(R.id.btn_row_decline_request);
            linearLayout=(LinearLayout) view.findViewById(R.id.row_wait_accept_request);

//            imgAvatar.setImageResource(friendRequests.getAvatar());
            tvName.setText(friendRequests.getName());
            tvTime.setText(friendRequests.getTime());
            btnAccept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    linearLayout.setVisibility(View.GONE);
                    tvAcceptRequests.setVisibility(View.VISIBLE);
                }
            });
            btnDecline.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    linearLayout.setVisibility(View.GONE);
                    tvDeclineRequests.setVisibility(View.VISIBLE);
                }
            });
        }
    }
}
