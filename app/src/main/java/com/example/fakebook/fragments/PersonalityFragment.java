package com.example.fakebook.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.fakebook.R;
import com.example.fakebook.adapters.BlogAdapter;
import com.example.fakebook.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import de.hdodenhof.circleimageview.CircleImageView;

public class PersonalityFragment extends Fragment {
    View view;
    CircleImageView imgAvatar;
    TextView txtName,txtDate,txtEmail;
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

        mAuth=FirebaseAuth.getInstance();
        final String emailTmp=mAuth.getCurrentUser().getEmail();
        String email=emailTmp.substring(0,emailTmp.length()-"@gmail.com".length());
        firebaseFirestore=FirebaseFirestore.getInstance();
        firebaseFirestore
                .collection("Users")
                .document(email)
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                User user=task.getResult().toObject(User.class);
                txtName.setText(user.getName());
                txtDate.setText(user.getDateOfBirth());
                txtEmail.setText(user.getEmail());
                Glide.with(getContext())
                        .load(user.getAvatar())
                        .fitCenter()
                        .placeholder(R.drawable.default_image)
                        .into(imgAvatar);
//                        RequestOptions placeHolderOption=new RequestOptions();
//                        placeHolderOption.placeholder(R.drawable.default_image);
//                        Glide.with(getContext()).applyDefaultRequestOptions(placeHolderOption)
//                                .load(user.getAvatar()).into(imgAvatar);
            }
        });
    }
    public void initWidget()
    {
        imgAvatar=(CircleImageView) view.findViewById(R.id.img_avatar_personality);
        txtName=(TextView) view.findViewById(R.id.txt_name_personality);
        txtDate=(TextView) view.findViewById(R.id.txt_date_of_birth_personality);
        txtEmail=(TextView) view.findViewById(R.id.txt_email_personality);
    }
}
