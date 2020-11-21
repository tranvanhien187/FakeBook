package com.example.fakebook.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.fakebook.R;
import com.example.fakebook.adapter.NotificationAdapter;

import com.example.fakebook.model.Notification;
import com.example.fakebook.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;

import static com.example.fakebook.MainActivity.DB;

public class NotificationsFragment extends Fragment {
    RecyclerView recyclerView;
    NotificationAdapter adapter;
    ArrayList<Notification> notifications;

    DatabaseReference mData;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_notifications,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        notifications=new ArrayList<>();
        recyclerView =(RecyclerView) view.findViewById(R.id.recycle_view);
        adapter=new NotificationAdapter(notifications,getContext());
        DB.collection("user")
                .document("Trần Văn Hiền")
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        User user=task.getResult().toObject(User.class);
                        for(Notification x : user.getNotificationList()){
                            notifications.add(x);
                            adapter.notifyDataSetChanged();
                        }
                    }
                });
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
    }
}
