package com.example.fakebook.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.fakebook.R;
import com.example.fakebook.model.Comment;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {
    private List<Comment> list;
    private Context context;
    public CommentAdapter(List<Comment> list, Context context){
        this.list=list;
        this.context=context;
    }
    @NonNull
    @Override
    public CommentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.cmt_on_line,parent,false);
        return new CommentAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentAdapter.ViewHolder holder, int position) {
        holder.name.setText(list.get(position).getName());
        holder.cmt.setText(list.get(position).getText());
        Glide.with(context).load(list.get(position).getUrl()).into(holder.ava);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private CircleImageView ava;
        private TextView name,cmt;
        private View mView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mView=itemView;
            ava=mView.findViewById(R.id.ava);
            name=mView.findViewById(R.id.tv_name);
            cmt=mView.findViewById(R.id.tv_cmt);
        }
    }
}
