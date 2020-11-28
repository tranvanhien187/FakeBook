package com.example.fakebook.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.fakebook.R;
import com.example.fakebook.adapters.BlogAdapter;
import com.example.fakebook.adapters.FriendProfileAdapter;
import com.example.fakebook.model.FriendRequests;
import com.example.fakebook.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileUser extends AppCompatActivity {

    View view;
    CircleImageView imgAvatarProfileUser;
    TextView txtNameProfileUser,txtDateProfileUser,txtEmailProfileUser,txtSexProfileUser;
    Button btnAdd,btnCancel;
    RecyclerView recyclerViewFriendProfileUser,recyclerViewMyPostProfileUser;
    FriendProfileAdapter adapterFriend;

    ArrayList<String> list;
    private User user;
    private FirebaseFirestore firebaseFirestore;
    private BlogAdapter blogAdapter;
    private FirebaseAuth mAuth=FirebaseAuth.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_user);

        initWidget();
        Intent intent = getIntent();
        String email = intent.getStringExtra("email");

        firebaseFirestore=FirebaseFirestore.getInstance();
        firebaseFirestore.collection("Users").document(email)
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

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnAdd.setVisibility(View.GONE);
                btnCancel.setVisibility(View.VISIBLE);

                DateFormat dateFormatter = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
                dateFormatter.setLenient(false);
                Date today = new Date();
                String date = dateFormatter.format(today);

                String emailTmp=mAuth.getCurrentUser().getEmail();
                final String email=emailTmp.substring(0,emailTmp.length()-"@gmail.com".length());

                final FriendRequests friendRequests=new FriendRequests(email,date);
                firebaseFirestore.collection("Users").document(user.getEmail())
                        .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        ArrayList<FriendRequests> friendRequestsArrayList= (ArrayList<FriendRequests>) task.getResult().get("friendRequestList");
                        friendRequestsArrayList.add(friendRequests);
                        firebaseFirestore.collection("Users").document(user.getEmail())
                                .update("friendRequestList",friendRequestsArrayList);
                    }
                });

            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnCancel.setVisibility(View.GONE);
                btnAdd.setVisibility(View.VISIBLE);
            }
        });

    }
    public void initWidget()
    {
        imgAvatarProfileUser=(CircleImageView) findViewById(R.id.img_avatar_profile_user);
        txtNameProfileUser=(TextView) findViewById(R.id.txt_name_profile_user);
        txtDateProfileUser=(TextView) findViewById(R.id.txt_date_of_birth_profile_user);
        txtEmailProfileUser=(TextView) findViewById(R.id.txt_email_profile_user);
        txtSexProfileUser=(TextView) findViewById(R.id.txt_sex_profile_user);
        btnAdd=(Button) findViewById(R.id.btn_add_friend_profile_user);
        btnCancel=(Button) findViewById(R.id.btn_cancel_add_friend_profile_user);
        recyclerViewFriendProfileUser=(RecyclerView) findViewById(R.id.recycle_view_friend_profile_user);

    }
}