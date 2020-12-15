package com.example.fakebook.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.fakebook.R;
import com.example.fakebook.adapters.BlogAdapter;
import com.example.fakebook.adapters.FriendProfileAdapter;
import com.example.fakebook.model.BlogPost;
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
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class PersonalityFragment extends Fragment {
    View view;
    CircleImageView imgAvatar, imgAvatarFriend1, imgAvatarFriend2;
    TextView txtName, txtDate, txtEmail, txtSex;

    RecyclerView recyclerViewFriend, recyclerViewMyPost;
    FriendProfileAdapter adapterFriend;

    BlogAdapter adapterMyPost;
    ArrayList<String> list;
    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();;
    private BlogAdapter blogAdapter;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();;
    private List<String> blogID = new ArrayList<>();
    private List<BlogPost> blogList;

    String emailTmp = mAuth.getCurrentUser().getEmail();
    String email = emailTmp.substring(0, emailTmp.length() - "@gmail.com".length());

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_personality, container, false);
        // răng m ko clone vê vkl chư
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.view = view;
        initWidget();

        blogList = new ArrayList<>();
        blogAdapter = new BlogAdapter(blogList, blogID);
        recyclerViewMyPost.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewMyPost.setAdapter(blogAdapter);
        recyclerViewMyPost.setNestedScrollingEnabled(false);

        list = new ArrayList<>();


        firebaseFirestore.collection("Users").document(email)
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                final User user = task.getResult().toObject(User.class);
                txtName.setText(user.getName());
                txtDate.setText(user.getDateOfBirth());
                txtEmail.setText(user.getEmail());
                if (user.getMale()) txtSex.setText("Nam");
                else txtSex.setText("Nữ");
                list = (ArrayList<String>) user.getFriendList();
                Glide.with(getContext())
                        .load(user.getAvatar())
                        .fitCenter()
                        .placeholder(R.drawable.default_image)
                        .into(imgAvatar);
                adapterFriend = new FriendProfileAdapter((ArrayList<String>) user.getFriendList(), getContext());
                recyclerViewFriend.setLayoutManager(new GridLayoutManager(getContext(), 3));
                recyclerViewFriend.setAdapter(adapterFriend);

                //adapterMyPost =new BlogAdapter()

            }
        });

        initLoad();
    }

    public void initWidget() {
        imgAvatar = (CircleImageView) view.findViewById(R.id.img_avatar_personality);
        txtName = (TextView) view.findViewById(R.id.txt_name_personality);
        txtDate = (TextView) view.findViewById(R.id.txt_date_of_birth_personality);
        txtEmail = (TextView) view.findViewById(R.id.txt_email_personality);
        txtSex = (TextView) view.findViewById(R.id.txt_sex_personality);
        recyclerViewFriend = (RecyclerView) view.findViewById(R.id.recycle_view_friend);
        recyclerViewMyPost = (RecyclerView) view.findViewById(R.id.recycle_view_my_post);
    }

    public void initLoad() {
        firebaseFirestore.collection("Users").document(email)
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
