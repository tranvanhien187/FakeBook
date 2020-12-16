package com.example.fakebook.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.fakebook.R;
import com.example.fakebook.model.BlogPost;
import com.example.fakebook.model.FriendRequests;
import com.example.fakebook.model.Notification;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.ScheduledExecutorService;

import de.hdodenhof.circleimageview.CircleImageView;

public class FriendRequestsAdapter extends RecyclerView.Adapter<FriendRequestsAdapter.ViewHolder> {

    HashMap<String,FriendRequests> friendRequestsListCurrent;
    Context context;
    ArrayList<FriendRequests> friendRequestsValues;



    public FriendRequestsAdapter(HashMap<String,FriendRequests> friendRequestsListCurrent, Context context) {
        this.friendRequestsListCurrent = friendRequestsListCurrent;
        this.context = context;
        friendRequestsValues=new ArrayList<>();
        friendRequestsValues.addAll(friendRequestsListCurrent.values());
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

        holder.binding(friendRequestsValues.get(position),position);
    }

    @Override
    public int getItemCount() {
        return friendRequestsValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        FirebaseFirestore firebaseFirestore=FirebaseFirestore.getInstance();
        FirebaseAuth mAuth=FirebaseAuth.getInstance();
        String emailTmp=mAuth.getCurrentUser().getEmail();
        String emailCurUser=emailTmp.substring(0,emailTmp.length()-"@gmail.com".length());
        View view;
        CircleImageView imgAvatar;
        TextView tvTime,tvName,tvAcceptRequests,tvDeclineRequests;
        Button btnAccept,btnDecline;
        LinearLayout linearLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            view=itemView;
        }

        public ArrayList<FriendRequests> newArray(ArrayList<FriendRequests> old,int pos){
            ArrayList<FriendRequests> now=new ArrayList<>();
            now.addAll(old);
            now.remove(pos);
            return now;
        }

        public void binding(final FriendRequests friendRequestCurrent, final int position)
        {
            imgAvatar=(CircleImageView) view.findViewById(R.id.img_row_avatar_request);
            tvName=(TextView) view.findViewById(R.id.tv_row_name_request);
            tvTime=(TextView) view.findViewById(R.id.tv_row_time_request);
            tvAcceptRequests=(TextView) view.findViewById(R.id.tv_accept_request);
            tvDeclineRequests=(TextView) view.findViewById(R.id.tv_decline_request);
            btnAccept=(Button)  view.findViewById(R.id.btn_row_accept_request);
            btnDecline=(Button) view.findViewById(R.id.btn_row_decline_request);
            linearLayout=(LinearLayout) view.findViewById(R.id.row_wait_accept_request);


            tvTime.setText(friendRequestCurrent.getTime().toString());
            firebaseFirestore.collection("Users").document(friendRequestCurrent.getEmail())
                    .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    tvName.setText(task.getResult().get("name")+"");
                    Glide.with(context)
                            .load(task.getResult().get("avatar")+"")
                            .fitCenter()
                            .placeholder(R.drawable.default_image)
                            .into(imgAvatar);
                }
            });


            btnAccept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // merge cur->friend
                    String myEmail=mAuth.getCurrentUser().getEmail();
                    final String friendEmail=friendRequestCurrent.getEmail();
                    Log.d("AAA",friendEmail);
                    myEmail=myEmail.substring(0,myEmail.length()-"@gmail.com".length());
                    final String finalFriendEmail = friendEmail;
                    firebaseFirestore.collection("Users").document(myEmail).collection("Posts")
                            .addSnapshotListener(new EventListener<QuerySnapshot>() {
                                @Override
                                public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
//                                    List<BlogPost> list=new ArrayList<>();
                                    for(DocumentChange doc:value.getDocumentChanges()){
                                        if(doc.getType()==DocumentChange.Type.ADDED){
                                            BlogPost now=doc.getDocument().toObject(BlogPost.class);
                                            firebaseFirestore.collection("Users").document(friendEmail)
                                                    .collection("friendPosts").add(now)
                                                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                                        @Override
                                                        public void onSuccess(DocumentReference documentReference) {
                                                            Log.d("AAA","added");
                                                        }
                                                    })
                                                    .addOnFailureListener(new OnFailureListener() {
                                                        @Override
                                                        public void onFailure(@NonNull Exception e) {
                                                            Log.d("AAA",e.getMessage());
                                                        }
                                                    });
                                        }
                                    }
                                }
                            });
                    final String finalMyEmail = myEmail;
                    firebaseFirestore.collection("Users")
                            .document(friendEmail).collection("Posts")
                            .addSnapshotListener(new EventListener<QuerySnapshot>() {
                                @Override
                                public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                                    for(DocumentChange doc:value.getDocumentChanges()){
                                        if(doc.getType()==DocumentChange.Type.ADDED){
                                            BlogPost now=doc.getDocument().toObject(BlogPost.class);
                                            firebaseFirestore.collection("Users").document(finalMyEmail)
                                                    .collection("friendPosts").add(now)
                                                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                                        @Override
                                                        public void onSuccess(DocumentReference documentReference) {
                                                            Log.d("AAA","added");
                                                        }
                                                    })
                                                    .addOnFailureListener(new OnFailureListener() {
                                                        @Override
                                                        public void onFailure(@NonNull Exception e) {
                                                            Log.d("AAA",e.getMessage());
                                                        }
                                                    });
                                        }
                                    }
                                }
                            });


                    linearLayout.setVisibility(View.GONE);
                    tvAcceptRequests.setVisibility(View.VISIBLE);

                    // xử lý ở người dùng hiện tại khi người dùng hiện tại nhấn đồng ý kết bạn
                    firebaseFirestore.collection("Users").document(emailCurUser)
                            .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                            // thêm bạn bè vào danh sách người dùng hiện tại khi nhấn đồng ý kết bạn
                            ArrayList<String> friendList= (ArrayList<String>) task.getResult().get("friendList");
                            friendList.add(friendRequestCurrent.getEmail());
                            firebaseFirestore.collection("Users").document(emailCurUser)
                                    .update("friendList",friendList);

                            // xoá lời mời kết bạn ở người dùng hiện tại khi nhấn đồng ý kết bạn
                            final HashMap<String,FriendRequests> friendRequestsHashMap = (HashMap<String, FriendRequests>) task.getResult().get("friendRequestList");
                            friendRequestsHashMap.remove(friendRequestCurrent.getEmail());
                            firebaseFirestore.collection("Users")
                                    .document(emailCurUser)
                                    .update("friendRequestList",friendRequestsHashMap ).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        friendRequestsValues.remove(position);
                                        notifyDataSetChanged();
                                        linearLayout.setVisibility(View.VISIBLE);
                                        tvAcceptRequests.setVisibility(View.GONE);
                                    }
                                }
                            });

                            // thêm thông báo đã trở thành bạn bè ở người dùng hiên tại khi đồng ý kết bạn
                            final ArrayList<Notification> notificationListUpdate = (ArrayList<Notification>) task.getResult().get("notificationList");
                            notificationListUpdate.add(new Notification(friendRequestCurrent.getEmail()," đã kết bạn với bạn",Calendar.getInstance().getTime()));
                            firebaseFirestore.collection("Users").document(emailCurUser)
                                    .update("notificationList",notificationListUpdate).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                }
                            });
                        }
                    });
                    // xử lý ở người gửi lời mời kết bạn khi người dùng hiện tại nhấn đồng ý kết bạn
                    firebaseFirestore.collection("Users").document(friendRequestCurrent.getEmail())
                            .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            //thêm bạn bè vào danh sách bạn bè của người gửi lời mời kết bạn khi nhấn đồng ý kết bạn
                            ArrayList<String> friendListUpdate= (ArrayList<String>) task.getResult().get("friendList");
                            friendListUpdate.add(emailCurUser);
                            firebaseFirestore.collection("Users").document(friendRequestCurrent.getEmail())
                                    .update("friendList",friendListUpdate);

                            //thêm thông báo vào danh sách thông báo của người gửi lời mời kết bạn khi nhấn đồng ý kết bạn
                            final ArrayList<Notification> notificationListUpdate = (ArrayList<Notification>) task.getResult().get("notificationList");
                            notificationListUpdate.add(new Notification(emailCurUser," đã kết bạn với bạn", Calendar.getInstance().getTime()));
                            firebaseFirestore.collection("Users").document(friendRequestCurrent.getEmail())
                                    .update("notificationList",notificationListUpdate);
                        }
                    });

                }
            });
            btnDecline.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    linearLayout.setVisibility(View.GONE);
                    tvDeclineRequests.setVisibility(View.VISIBLE);
                    firebaseFirestore.collection("Users")
                            .document(emailCurUser)
                            .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            final HashMap<String,FriendRequests> friendRequestsHashMap = (HashMap<String, FriendRequests>) task.getResult().get("friendRequestList");
                            friendRequestsHashMap.remove(friendRequestCurrent.getEmail());
                            firebaseFirestore.collection("Users")
                                    .document(emailCurUser)
                                    .update("friendRequestList",friendRequestsHashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        friendRequestsValues.remove(position);
                                        notifyDataSetChanged();
                                        linearLayout.setVisibility(View.VISIBLE);
                                        tvDeclineRequests.setVisibility(View.GONE);
                                    }
                                }
                            });

                        }
                    });
                }
            });
        }
    }
}
