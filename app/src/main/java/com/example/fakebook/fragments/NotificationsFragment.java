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
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

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
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

//        notifications.add(new Notification("Sana đã thích ảnh của bạn","1 giờ trước",R.color.design_default_color_error));
//        notifications.add(new Notification("Momo đã thích ảnh của bạn","3 giờ trước",R.color.design_default_color_on_secondary));
//        notifications.add(new Notification("Văn Hiền đã thích ảnh của bạn","5 giờ trước",R.color.colorPrimary));
//        notifications.add(new Notification("Văn Hiền đã chia sẽ ảnh của bạn","6 giờ trước",R.color.material_on_background_emphasis_high_type));
//        notifications.add(new Notification("Sana đã chia sẽ ảnh của bạn","10 giờ trước",R.color.material_on_surface_stroke));
//        notifications.add(new Notification("Momo đã chia sẽ ảnh của bạn","12 giờ trước",R.color.design_default_color_primary_variant));
//        notifications.add(new Notification("John đã chia sẽ ảnh của bạn","19 giờ trước",R.color.colorAccent));
//        notifications.add(new Notification("Sana đã thích ảnh của bạn","20 giờ trước",R.color.design_default_color_error));
//        notifications.add(new Notification("Sana đã chia sẽ ảnh của bạn","23 giờ trước",R.color.design_default_color_error));

        mData= FirebaseDatabase.getInstance().getReference();
        //mData.push().setValue(new User("Tran Van Hien","","18-07-2000",true,notifications));
        //mData.child("-MMTxx9dvr8sE0gV98Tt").child("notifications").setValue(notifications);
        mData.child("-MMTxx9dvr8sE0gV98Tt").child("notifications").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull final DataSnapshot snapshot, @Nullable String previousChildName) {
                        Notification notification=snapshot.getValue(Notification.class);
                        notifications.add(notification);
                        adapter.notifyDataSetChanged();
            }
            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}
