package com.example.fakebook.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.fakebook.R;
import com.example.fakebook.adapters.BlogAdapter;
import com.example.fakebook.adapters.FriendProfileAdapter;
import com.example.fakebook.model.BlogPost;
import com.example.fakebook.model.FriendRequests;
import com.example.fakebook.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileUser extends AppCompatActivity {

    View view;
    CircleImageView imgAvatarProfileUser;
    TextView txtNameProfileUser,txtDateProfileUser,txtEmailProfileUser,txtSexProfileUser;
    Button btnSendFriendRequest, btnCancelSendFriendRequest,btnFriend,btnAccept;
    RecyclerView recyclerViewFriendProfileUser,recyclerViewMyPostProfileUser;
    FriendProfileAdapter adapterFriend;

    ArrayList<String> list;
    private User user;
    private FirebaseFirestore firebaseFirestore=FirebaseFirestore.getInstance();;
    private BlogAdapter blogAdapter;
    private FirebaseAuth mAuth=FirebaseAuth.getInstance();
    String emailProfile;

    String emailTmp=mAuth.getCurrentUser().getEmail();
    String emailCurrentUser =emailTmp.substring(0,emailTmp.length()-"@gmail.com".length());

    private List<String> blogID = new ArrayList<>();
    private List<BlogPost> blogList;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_user);

        initWidget();
        Intent intent = getIntent();
        emailProfile = intent.getStringExtra("email");

        setTrangThaiBanBe();

        blogList = new ArrayList<>();
        blogAdapter = new BlogAdapter(blogList, blogID);
        recyclerViewMyPostProfileUser.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewMyPostProfileUser.setAdapter(blogAdapter);
        recyclerViewMyPostProfileUser.setNestedScrollingEnabled(false);

        firebaseFirestore.collection("Users").document(emailProfile)
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful())
                {
                    user=task.getResult().toObject(User.class);
                    txtNameProfileUser.setText(user.getName());
                    txtDateProfileUser.setText(user.getDateOfBirth());
                    txtEmailProfileUser.setText(user.getEmail());
                    if(user.getMale()) txtSexProfileUser.setText("Nam");
                    else txtSexProfileUser.setText("Nữ");
                    Glide.with(ProfileUser.this)
                            .load(user.getAvatar())
                            .fitCenter()
                            .placeholder(R.drawable.default_image)
                            .into(imgAvatarProfileUser);

                    adapterFriend =new FriendProfileAdapter((ArrayList<String>) user.getFriendList(),ProfileUser.this);
                    recyclerViewFriendProfileUser.setLayoutManager(new GridLayoutManager(ProfileUser.this,3));
                    recyclerViewFriendProfileUser.setAdapter(adapterFriend);
                }
            }
        });

        btnSendFriendRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnSendFriendRequest.setVisibility(View.GONE);
                btnCancelSendFriendRequest.setVisibility(View.VISIBLE);


                // thêm lời mời kết bạn vào người dùng được hiển thị profile ( lúc tìm kiếm )
                firebaseFirestore.collection("Users").document(emailProfile)
                        .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        HashMap<String,FriendRequests> friendRequestsHashMap= (HashMap<String, FriendRequests>) task.getResult().get("friendRequestList");
                        FriendRequests friendRequests=new FriendRequests(emailCurrentUser, Calendar.getInstance().getTime());
                        friendRequestsHashMap.put(emailCurrentUser,friendRequests);
                        firebaseFirestore.collection("Users").document(user.getEmail())
                                .update("friendRequestList",friendRequestsHashMap);

                    }
                });

                // thêm vào danh dách những lời mời kết bạn đã gửi của Người Dùng Hiện Tại
                firebaseFirestore.collection("Users").document(emailCurrentUser)
                        .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        HashMap<String,FriendRequests> sendFriendRequestHashMap= (HashMap<String, FriendRequests>) task.getResult().get("sendFriendRequestList");
                        FriendRequests sendFriendRequest=new FriendRequests(emailProfile, Calendar.getInstance().getTime());
                        sendFriendRequestHashMap.put(emailProfile,sendFriendRequest);
                        firebaseFirestore.collection("Users").document(emailCurrentUser)
                                .update("sendFriendRequestList",sendFriendRequestHashMap);
                    }
                });


            }
        });
        btnCancelSendFriendRequest.setOnClickListener(
                new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnCancelSendFriendRequest.setVisibility(View.GONE);
                btnSendFriendRequest.setVisibility(View.VISIBLE);

                firebaseFirestore.collection("Users").document(emailProfile)
                        .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        HashMap<String,FriendRequests> friendRequestsHashMap= (HashMap<String, FriendRequests>) task.getResult().get("friendRequestList");
                        friendRequestsHashMap.remove(emailCurrentUser);
                        firebaseFirestore.collection("Users").document(emailProfile)
                                .update("friendRequestList",friendRequestsHashMap);

                    }
                });

                firebaseFirestore.collection("Users").document(emailCurrentUser)
                        .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        HashMap<String,FriendRequests> sendFriendRequestHashMap= (HashMap<String, FriendRequests>) task.getResult().get("sendFriendRequestList");
                        sendFriendRequestHashMap.remove(emailProfile);
                        firebaseFirestore.collection("Users").document(emailCurrentUser)
                                .update("sendFriendRequestList",sendFriendRequestHashMap);
                    }
                });
            }
        });


        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnAccept.setVisibility(View.GONE);
                btnFriend.setVisibility(View.VISIBLE);

                firebaseFirestore.collection("Users").document(emailProfile)
                        .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        //thêm bạn bè vào người dùng hiển thị profile ( lúc tìm kiếm)
                        ArrayList<String> friendList = (ArrayList<String>) task.getResult().get("friendList");
                        friendList.add(emailCurrentUser);
                        firebaseFirestore.collection("Users").document(emailProfile)
                                .update("friendList",friendList);
                        // huỷ lời mời kết bạn khi đã chấp nhận kết bạn
                        HashMap<String,FriendRequests> friendRequestsHashMap= (HashMap<String, FriendRequests>) task.getResult().get("friendRequestList");
                        friendRequestsHashMap.remove(emailCurrentUser);
                        firebaseFirestore.collection("Users").document(emailProfile)
                                .update("friendRequestList",friendRequestsHashMap);
                    }
                });
                firebaseFirestore.collection("Users").document(emailCurrentUser)
                        .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        //thêm bạn bè vào người dùng hiện tại
                        ArrayList<String> friendList = (ArrayList<String>) task.getResult().get("friendList");
                        friendList.add(emailProfile);
                        firebaseFirestore.collection("Users").document(emailCurrentUser)
                                .update("friendList",friendList);
                        // huỷ gửi lời mời kết bạn khi đã được chấp nhận kết bạn
                        HashMap<String,FriendRequests> sendFriendRequestHashMap= (HashMap<String, FriendRequests>) task.getResult().get("sendFriendRequestList");
                        sendFriendRequestHashMap.remove(emailProfile);
                        firebaseFirestore.collection("Users").document(emailCurrentUser)
                                .update("sendFriendRequestList",sendFriendRequestHashMap);
                    }
                });
            }
        });

        initLoad(emailProfile);

    }
    public void initWidget()
    {
        imgAvatarProfileUser=(CircleImageView) findViewById(R.id.img_avatar_profile_user);
        txtNameProfileUser=(TextView) findViewById(R.id.txt_name_profile_user);
        txtDateProfileUser=(TextView) findViewById(R.id.txt_date_of_birth_profile_user);
        txtEmailProfileUser=(TextView) findViewById(R.id.txt_email_profile_user);
        txtSexProfileUser=(TextView) findViewById(R.id.txt_sex_profile_user);
        btnSendFriendRequest =(Button) findViewById(R.id.btn_add_friend_profile_user);
        btnCancelSendFriendRequest =(Button) findViewById(R.id.btn_cancel_add_friend_profile_user);
        btnFriend=(Button) findViewById(R.id.btn_friend_profile_user);
        btnAccept=(Button) findViewById(R.id.btn_accept_friend_request_profile_user);
        recyclerViewFriendProfileUser=(RecyclerView) findViewById(R.id.recycle_view_friend_profile_user);
        recyclerViewMyPostProfileUser=(RecyclerView) findViewById(R.id.recycle_view_my_post_profile_user);

    }
    public void setTrangThaiBanBe()
    {
        firebaseFirestore.collection("Users").document(emailProfile).
                get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful())
                {
                    ArrayList<String> friendList = (ArrayList<String>) task.getResult().get("friendList");
                    HashMap<String,FriendRequests> friendRequestsHashMap = (HashMap<String, FriendRequests>) task.getResult().get("friendRequestList");
                    HashMap<String,FriendRequests> sendFriendRequestsHashMap = (HashMap<String, FriendRequests>) task.getResult().get("sendFriendRequestList");
                    if(friendList.contains(emailCurrentUser))
                    {
                        btnSendFriendRequest.setVisibility(View.GONE);
                        btnCancelSendFriendRequest.setVisibility(View.GONE);
                        btnFriend.setVisibility(View.VISIBLE);
                        btnAccept.setVisibility(View.GONE);
                    }
                    else if(friendRequestsHashMap.containsKey(emailCurrentUser))
                    {
                        btnSendFriendRequest.setVisibility(View.GONE);
                        btnCancelSendFriendRequest.setVisibility(View.VISIBLE);
                        btnFriend.setVisibility(View.GONE);
                        btnAccept.setVisibility(View.GONE);
                    }
                    else if(sendFriendRequestsHashMap.containsKey(emailCurrentUser))
                    {
                        btnSendFriendRequest.setVisibility(View.GONE);
                        btnCancelSendFriendRequest.setVisibility(View.GONE);
                        btnFriend.setVisibility(View.GONE);
                        btnAccept.setVisibility(View.VISIBLE);
                    }
                }
                else {
                    Toast.makeText(ProfileUser.this, "Something wrong", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    public void initLoad(String emailProfile) {
        firebaseFirestore.collection("Users").document(emailProfile)
                .collection("Posts")
                .orderBy("timestamp", Query.Direction.DESCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        for (DocumentChange doc : value.getDocumentChanges()) {
                            if (doc.getType() == DocumentChange.Type.ADDED) {
                                blogID.add(doc.getDocument().getId());
                                BlogPost blogPost = doc.getDocument().toObject(BlogPost.class);
                                blogList.add(blogPost);
                                blogAdapter.notifyDataSetChanged();
                                Log.d("loglog", doc.getDocument().getId());
                            }
                        }
                    }
                });
    }
}