package com.example.fakebook.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.fakebook.R;
import com.example.fakebook.adapter.FriendRequestsAdapter;
import com.example.fakebook.model.FriendRequests;
import com.example.fakebook.model.Notification;
import com.example.fakebook.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.SuccessContinuation;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class FriendsFragment extends Fragment {

    ArrayList<FriendRequests> requestsArrayList;
    FriendRequestsAdapter adapter;
    RecyclerView recyclerView;

    DatabaseReference mData;

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
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        final FirebaseFirestore db = FirebaseFirestore.getInstance();
//        requestsArrayList.add(new FriendRequests("Minatozaki Sana","",R.color.design_default_color_primary_variant,"1 tiếng trước"));
//        requestsArrayList.add(new FriendRequests("Minatozaki Sana","",R.color.material_on_background_emphasis_high_type,"1 tiếng trước"));
//        requestsArrayList.add(new FriendRequests("Minatozaki Sana","",R.color.colorPrimary,"1 tiếng trước"));
//        requestsArrayList.add(new FriendRequests("Minatozaki Sana","",R.color.colorAccent,"1 tiếng trước"));
//        requestsArrayList.add(new FriendRequests("Minatozaki Sana","",R.color.design_default_color_secondary_variant,"1 tiếng trước"));
//        requestsArrayList.add(new FriendRequests("Minatozaki Sana","",R.color.material_on_primary_disabled,"1 tiếng trước"));
//        requestsArrayList.add(new FriendRequests("Minatozaki Sana","",R.color.design_default_color_on_secondary,"1 tiếng trước"));
        User user=new User("Tran Van Hien","","18/07/2000",true);
        user.setFriendRequestList(requestsArrayList);
//        db.collection("user")
//                .document("Trần Văn Hiền")
//                .set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
//            @Override
//            public void onSuccess(Void aVoid) {
//                Toast.makeText(getContext(), "probinh", Toast.LENGTH_SHORT).show();
//            }
//        });

        db.collection("user")
                .document("Trần Văn Hiền")
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                        ArrayList<FriendRequests> group= new ArrayList<>();
//                                group.addAll((Collection<? extends FriendRequests>) task.getResult().get("friendRequestList"));
                        //HashMap<Integer,FriendRequests> group= (HashMap<Integer, FriendRequests>) task.getResult().get("friendRequestList");
                        ArrayList<Object> group=(ArrayList<Object>) task.getResult().get("friendRequestList");
                        group.add(new FriendRequests("Hien","",12233,"1 h truoc"));
                        Toast.makeText(getContext(), group.get(0).toString(), Toast.LENGTH_SHORT).show();
                        Toast.makeText(getContext(), group.get(6).toString(), Toast.LENGTH_SHORT).show();
                    }
                });


//        mData= FirebaseDatabase.getInstance().getReference();
////        mData.child("-MMTxx9dvr8sE0gV98Tt").child("friendRequests").setValue(requestsArrayList);
//        mData.child("-MMTxx9dvr8sE0gV98Tt").child("friendRequests").addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(@NonNull final DataSnapshot snapshot, @Nullable String previousChildName) {
//                FriendRequests notification=snapshot.getValue(FriendRequests.class);
//                requestsArrayList.add(notification);
//                adapter.notifyDataSetChanged();
//            }
//            @Override
//            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
//
//            }
//
//            @Override
//            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
//
//            }
//
//            @Override
//            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });



    }
}