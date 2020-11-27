package com.example.fakebook.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.fakebook.R;
import com.example.fakebook.adapters.BlogAdapter;
import com.example.fakebook.adapters.FriendProfileAdapter;
import com.example.fakebook.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.ArrayList;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class PersonalityFragment extends Fragment {
    View view;
    CircleImageView imgAvatar,imgAvatarFriend1,imgAvatarFriend2;
    TextView txtName,txtDate,txtEmail;

    RecyclerView recyclerView;
    FriendProfileAdapter adapter;
    ArrayList<String> list;
    private FirebaseFirestore firebaseFirestore;
    private BlogAdapter blogAdapter;
    private FirebaseAuth mAuth;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_personality,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.view=view;
        initWidget();
        list=new ArrayList<>();
        mAuth=FirebaseAuth.getInstance();
        final String emailTmp=mAuth.getCurrentUser().getEmail();
        String email=emailTmp.substring(0,emailTmp.length()-"@gmail.com".length());
        firebaseFirestore=FirebaseFirestore.getInstance();

        firebaseFirestore.collection("Users").document(email)
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                Log.d("AAA",task.getResult().toString());
                final User user=task.getResult().toObject(User.class);
                txtName.setText(user.getName());
                txtDate.setText(user.getDateOfBirth());
                txtEmail.setText(user.getEmail());
                list=user.getFriendList();
                Glide.with(getContext())
                        .load(user.getAvatar())
                        .fitCenter()
                        .placeholder(R.drawable.default_image)
                        .into(imgAvatar);
              //  Toast.makeText(getContext(), user.getFriendList().get(0), Toast.LENGTH_SHORT).show();
                //Toast.makeText(getContext(), firebaseFirestore.collection("Users").document(user.getFriendList().get(1)).getId(), Toast.LENGTH_SHORT).show();
//                FirebaseFirestore db=FirebaseFirestore.getInstance();
                //cai ni t dung test thoi do i t mo comment doi ti t xem code ti
//                db.collection("Users").document(user.getFriendList().get(0))
//                        .addSnapshotListener(new EventListener<DocumentSnapshot>() {
//                            @Override
//                            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
//                                if(value!=null)
//                                {
//                                    if(value.exists())
//                                    {
//                                        Toast.makeText(getContext(), value.get("name")+"", Toast.LENGTH_SHORT).show();
//                                    }
//                                    else {
//                                        Log.d("AAA - Not exists", error.getMessage());
//                                    }
//                                }
//                                else {
//                                    Log.d("AAA - Null",error.getMessage());
//                                }
//                            }
//                        });
//                        .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                        Toast.makeText(getContext(), task.getResult().toString(), Toast.LENGTH_LONG).show();
//                        Log.d("AAA",task.getResult().toString());
//                        if(task.getResult().contains("name"))
//                        {
//                            User user1=task.getResult().toObject(User.class);
//                            Toast.makeText(getContext(), user.getFriendList().get(0), Toast.LENGTH_SHORT).show();
//                            Toast.makeText(getContext(), user1.getName(), Toast.LENGTH_SHORT).show();
//                        }
//                        else {
//                            Log.e
//                        }
//
//                    }
//                });
                Log.d("AAA","name"+list.get(0));
//                firebaseFirestore.collection("Users").document(list.get(0)).get()
//                        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//                            @Override
//                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                                Toast.makeText(getContext(), task.getResult().get("name")+"", Toast.LENGTH_SHORT).show();
//                            }
//                        });
                // cho nay tinh lay cai j lay gi cung dc name di
                firebaseFirestore.collection("Users").document(list.get(0))
                        .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful()){
                            Log.d("AAA",task.getResult().get("name").toString());
                        }
                        else{
                            Log.d("AAA",task.getException().getMessage());
                        }
                    }
                });
                // co cai khoang trang kia tao chiu

                // casi tranvan hien hoi trua t test do no cung nhu ri ma chua biet xu ly the nao cho doc=null la van de do

//                adapter=new FriendProfileAdapter(user.getFriendList(),getContext());
//                Toast.makeText(getContext(), user.getFriendList().size()+"", Toast.LENGTH_SHORT).show();
//                //Toast.makeText(getContext(), user.getFriendList().get(3), Toast.LENGTH_SHORT).show();
//                recyclerView.setLayoutManager(new GridLayoutManager(getContext(),3));
//                recyclerView.setAdapter(adapter);

            }
        });
    }
    public void initWidget()
    {
        imgAvatar=(CircleImageView) view.findViewById(R.id.img_avatar_personality);
        txtName=(TextView) view.findViewById(R.id.txt_name_personality);
        txtDate=(TextView) view.findViewById(R.id.txt_date_of_birth_personality);
        txtEmail=(TextView) view.findViewById(R.id.txt_email_personality);
        recyclerView=(RecyclerView) view.findViewById(R.id.recycle_view_friend);
        imgAvatarFriend1=(CircleImageView) view.findViewById(R.id.img_avatar_friend1);
        //imgAvatarFriend2=(CircleImageView) view.findViewById(R.id.img_avatar_friend2);
    }
}
