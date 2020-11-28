package com.example.fakebook.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.fakebook.R;
import com.example.fakebook.activities.ProfileUser;
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
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

public class FriendRequestsAdapter extends RecyclerView.Adapter<FriendRequestsAdapter.ViewHolder> {

    ArrayList<FriendRequests> friendRequestsArrayList;
    Context context;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;

    public FriendRequestsAdapter(ArrayList<FriendRequests> friendRequestsArrayList, Context context) {
        this.friendRequestsArrayList = friendRequestsArrayList;
        this.context = context;
        firebaseFirestore=FirebaseFirestore.getInstance();
        firebaseAuth=FirebaseAuth.getInstance();
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
        holder.binding(friendRequestsArrayList.get(position),position);
    }

    @Override
    public int getItemCount() {
        return friendRequestsArrayList.size();
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

        public void binding(final FriendRequests friendRequest, final int position)
        {
            imgAvatar=(CircleImageView) view.findViewById(R.id.img_row_avatar_request);
            tvName=(TextView) view.findViewById(R.id.tv_row_name_request);
            tvTime=(TextView) view.findViewById(R.id.tv_row_time_request);
            tvAcceptRequests=(TextView) view.findViewById(R.id.tv_accept_request);
            tvDeclineRequests=(TextView) view.findViewById(R.id.tv_decline_request);
            btnAccept=(Button)  view.findViewById(R.id.btn_row_accept_request);
            btnDecline=(Button) view.findViewById(R.id.btn_row_decline_request);
            linearLayout=(LinearLayout) view.findViewById(R.id.row_wait_accept_request);


            tvTime.setText(friendRequest.getTime());
            firebaseFirestore.collection("Users")
                    .document(friendRequest.getEmail())
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
                    String myEmail=firebaseAuth.getCurrentUser().getEmail();
                    final String friendEmail=friendRequest.getEmail();
                    Log.d("AAA",friendEmail);
                    myEmail=myEmail.substring(0,myEmail.length()-"@gmail.com".length());
                    final String finalFriendEmail = friendEmail;
                    firebaseFirestore.collection("Users")
                            .document(myEmail).collection("Posts")
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
//                                    List<BlogPost> list=new ArrayList<>();
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
                    firebaseFirestore.collection("Users")
                            .document(emailCurUser)
                            .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            ArrayList<String> friends= (ArrayList<String>) task.getResult().get("friendList");
                            friends.add(friendRequest.getEmail());
                            firebaseFirestore.collection("Users")
                                    .document(emailCurUser)
                                    .update("friendList",friends);

                            //add loi moi ket ban
                            final ArrayList<FriendRequests> friendRequestsArrayListUpdate = (ArrayList<FriendRequests>) task.getResult().get("friendRequestList");
                            firebaseFirestore.collection("Users")
                                    .document(emailCurUser)
                                    .update("friendRequestList",newArray(friendRequestsArrayListUpdate,position)).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        friendRequestsArrayList.remove(position);
                                        notifyDataSetChanged();
                                        linearLayout.setVisibility(View.VISIBLE);
                                        tvAcceptRequests.setVisibility(View.GONE);
                                    }
                                }
                            });

                            //add thong bao
                            final ArrayList<Notification> notificationArrayList = (ArrayList<Notification>) task.getResult().get("notificationList");
                            DateFormat dateFormatter = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
                            dateFormatter.setLenient(false);
                            Date today = new Date();
                            String date = dateFormatter.format(today);
                            notificationArrayList.add(new Notification(friendRequest.getEmail()," đã kết bạn với bạn",date));
                            firebaseFirestore.collection("Users").document(emailCurUser)
                                    .update("notificationList",notificationArrayList).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Log.d("AAA",notificationArrayList.size()+"  asdasd");
                                }
                            });
                        }
                    });
                    //add ban be o nguoi gui
                    Log.d("AAA",friendRequest.getEmail()) ;
                    firebaseFirestore.collection("Users")
                            .document(friendRequest.getEmail())
                            .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            ArrayList<String> friendList= (ArrayList<String>) task.getResult().get("friendList");
                            friendList.add(emailCurUser);
                            firebaseFirestore.collection("Users")
                                    .document(friendRequest.getEmail())
                                    .update("friendList",friendList);

                            //add thong bao
                            final ArrayList<Notification> notificationArrayList = (ArrayList<Notification>) task.getResult().get("notificationList");
                            DateFormat dateFormatter = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
                            dateFormatter.setLenient(false);
                            Date today = new Date();
                            String date = dateFormatter.format(today);
                            Log.d("AAA",emailCurUser+"  ddssd");
                            notificationArrayList.add(new Notification(emailCurUser," đã kết bạn với bạn",date));
                            firebaseFirestore.collection("Users").document(friendRequest.getEmail())
                                    .update("notificationList",notificationArrayList);
                        }
                    });
                    //add ban be o nguoi gui ban
                    firebaseFirestore.collection("Users")
                            .document(emailCurUser)
                            .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            Toast.makeText(context, "before : "+position, Toast.LENGTH_SHORT).show();
                            final ArrayList<FriendRequests> friendRequestsArrayList = (ArrayList<FriendRequests>) task.getResult().get("friendRequestList");
                            firebaseFirestore.collection("Users")
                                    .document(emailCurUser)
                                    .update("friendRequestList",newArray(friendRequestsArrayList,position)).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        friendRequestsArrayList.remove(position);
                                    }
                                }
                            });

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
                            final ArrayList<FriendRequests> friendRequestsArrayListUpdate = (ArrayList<FriendRequests>) task.getResult().get("friendRequestList");
                            firebaseFirestore.collection("Users")
                                    .document(emailCurUser)
                                    .update("friendRequestList",newArray(friendRequestsArrayListUpdate,position)).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        friendRequestsArrayList.remove(position);
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
