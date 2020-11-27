package com.example.fakebook.fragments;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.fakebook.MainActivity;
import com.example.fakebook.R;

import com.example.fakebook.adapters.FriendRequestsAdapter;
import com.example.fakebook.model.FriendRequests;
import com.example.fakebook.model.Notification;
import com.example.fakebook.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.SuccessContinuation;
import com.google.android.gms.tasks.Task;


import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;


public class FriendsFragment extends Fragment {

    ArrayList<FriendRequests> requestsArrayList;
    FriendRequestsAdapter adapter;
    RecyclerView recyclerView;
    private boolean mIsLoading;
    FirebaseFirestore DB = FirebaseFirestore.getInstance();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_friends, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView=(RecyclerView) view.findViewById(R.id.friend_request_recycle_view);
        requestsArrayList=new ArrayList<>();
        adapter=new FriendRequestsAdapter(requestsArrayList,getContext());
        DB.collection("Users")
                .document("tri1")
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        User user=task.getResult().toObject(User.class);
                        for(FriendRequests x : user.getFriendRequestList()){
                            requestsArrayList.add(x);
                            adapter.notifyDataSetChanged();
                        }
                    }
                });


        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);


    }

    public void loadDataWhenScroll() {
        LinearLayoutManager linearLayoutManager;
        linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        if (!mIsLoading) {
            if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() + 1 == requestsArrayList.size()) {
                new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        adapter.notifyDataSetChanged();
                        mIsLoading = false;
                    }
                }, 60);
            }
        }
    }
}