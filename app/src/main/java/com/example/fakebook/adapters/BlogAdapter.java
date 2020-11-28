package com.example.fakebook.adapters;

import android.content.Context;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.fakebook.R;
import com.example.fakebook.model.BlogPost;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class BlogAdapter extends RecyclerView.Adapter<BlogAdapter.ViewHolder> {
    public List<BlogPost> blogList;
    public List<String> blogID;
    public Context context;
    private FirebaseUser cur_user=FirebaseAuth.getInstance().getCurrentUser();
    private String mEmail=cur_user.getEmail().substring(0,cur_user.getEmail().length()-"@gmail.com".length());
    public BlogAdapter(List<BlogPost> blogList,List<String> blogID){
        this.blogList=blogList;
        this.blogID=blogID;
    }
    private FirebaseFirestore firebaseFirestore;
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.blog_list_item,parent,false);
        context=parent.getContext();
        firebaseFirestore=FirebaseFirestore.getInstance();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        String text=blogList.get(position).getText();
        holder.setText(text);
        String downloadUrl=blogList.get(position).getImageUrl();
        holder.setBlogImage(downloadUrl);
        String userId=blogList.get(position).getUserId();
        FirebaseAuth mAuth=FirebaseAuth.getInstance();
//        String emailTmp=userId;
        final String email=userId;
        firebaseFirestore.collection("Users").document(email).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful()){
                            String userName=task.getResult().getString("name");
                            holder.setName(userName);
                            String image=task.getResult().getString("avatar");
                            holder.setAva(image);
                        }
                        else{
                            Toast.makeText(context, "error : "+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        try{
            long miliseconds=blogList.get(position).getTimestamp().getTime();
            String dateString= DateFormat.format("dd/MM/yyyy",new Date(miliseconds)).toString();
            holder.setBlogTime(dateString);
        }
        catch (Exception e){
            Log.d("error","error : "+e.getMessage()+" position : "+position);
        }
        boolean liked=blogList.get(position).isLiked();
        holder.setBtnBlogLike(liked);
        holder.setTvLikeCnt(liked);
        holder.btnBlogLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(blogList.get(position).isLiked()){
                    firebaseFirestore.collection("Users").document(mEmail)
                            .collection("friendPosts")
                            .document(blogID.get(position))
                            .update("liked",false).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            holder.setTvLikeCnt(false);
                            Toast.makeText(context, "dislike", Toast.LENGTH_SHORT).show();
                        }
                    });
                    blogList.get(position).setLiked(false);
                    holder.setBtnBlogLike(blogList.get(position).isLiked());
                }
                else{
                    firebaseFirestore.collection("Users").document(mEmail)
                            .collection("friendPosts")
                            .document(blogID.get(position))
                            .update("liked",true).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            holder.setTvLikeCnt(true);
                            Toast.makeText(context, "like", Toast.LENGTH_SHORT).show();
                        }
                    });
                    blogList.get(position).setLiked(true);
                    holder.setBtnBlogLike(blogList.get(position).isLiked());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return blogList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private View mView;
        private TextView tvText;
        private ImageView image;
        private TextView tvDate;
        private TextView tvName;
        private CircleImageView imgAva;
        private ImageView btnBlogLike;
        private TextView tvLikeCnt;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mView=itemView;
            btnBlogLike=mView.findViewById(R.id.btn_like);

        }
        public void setText(String text){
            tvText=mView.findViewById(R.id.blog_test);
            tvText.setText(text);
        }
        public void setBlogImage(String downloadUrl){
            image=mView.findViewById(R.id.blog_image);
            RequestOptions requestOptions=new RequestOptions();
            requestOptions.placeholder(R.drawable.default_image);
            Glide.with(context).load(downloadUrl).into(image);
        }
        public void setBlogTime(String date){
            tvDate=mView.findViewById(R.id.blog_date);
            tvDate.setText(date);
        }
        public void setName(String name){
            tvName=mView.findViewById(R.id.blog_user_name);
            tvName.setText(name);
        }
        public void setAva(String image){
            imgAva=mView.findViewById(R.id.blog_user_image);
            RequestOptions placeHolderOption=new RequestOptions();
            placeHolderOption.placeholder(R.drawable.default_image);
            Glide.with(context).applyDefaultRequestOptions(placeHolderOption)
                    .load(image).into(imgAva);
        }
        public void setBtnBlogLike(boolean check){
            if(check) btnBlogLike.setImageDrawable(context.getDrawable(R.drawable.ic_add_red));
            else btnBlogLike.setImageDrawable(context.getDrawable(R.drawable.ic_add));
        }
        public void setTvLikeCnt(boolean check){
            tvLikeCnt=mView.findViewById(R.id.tv_cnt);
            if(check) tvLikeCnt.setText("Liked");
            else tvLikeCnt.setText("don't like yet");
        }

    }
}
