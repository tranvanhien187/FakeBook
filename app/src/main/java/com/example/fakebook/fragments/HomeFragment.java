package com.example.fakebook.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fakebook.MainActivity;
import com.example.fakebook.R;
import com.example.fakebook.activities.NewPostActivity;
import com.example.fakebook.adapters.BlogAdapter;
import com.example.fakebook.model.BlogPost;
import com.example.fakebook.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import static java.util.Objects.requireNonNull;

public class HomeFragment extends Fragment {
    private FloatingActionButton fabAdd;
    private RecyclerView blogListView;
    private View v;
    private List<BlogPost> blogList;
    private FirebaseFirestore firebaseFirestore;
    private BlogAdapter blogAdapter;
    private FirebaseAuth mAuth;
    private List<String> blogID=new ArrayList<>();
    private List<String> friendList;
    private List<BlogPost> cur=new ArrayList<>();
    private final int LIM=10;
    private int lim=0;
    private boolean mIsLoadingData=false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v=inflater.inflate(R.layout.fragment_home,container,false);
        fabAdd=v.findViewById(R.id.fab_add);
        blogListView=v.findViewById(R.id.rv_list);
        blogList=new ArrayList<>();
        blogAdapter=new BlogAdapter(blogList,blogID);
        blogListView.setLayoutManager(new LinearLayoutManager(v.getContext()));
        blogListView.setAdapter(blogAdapter);
        firebaseFirestore=FirebaseFirestore.getInstance();
        mAuth=FirebaseAuth.getInstance();
        String emailTmp=mAuth.getCurrentUser().getEmail();
        final String email=emailTmp.substring(0,emailTmp.length()-"@gmail.com".length());
//        Query firstQuery=firebaseFirestore.collection("Users")
//                .document(email)
//                .collection("Posts").orderBy("timestamp",Query.Direction.DESCENDING);
//        firstQuery.addSnapshotListener(new EventListener<QuerySnapshot>() {
//            @Override
//            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
//                for(DocumentChange doc:value.getDocumentChanges()){
//                    if(doc.getType()==DocumentChange.Type.ADDED){
//                        BlogPost blogPost=doc.getDocument().toObject(BlogPost.class);
//                        blogList.add(blogPost);
//                        blogAdapter.notifyDataSetChanged();
//                    }
//                }
//            }
//        });
//        friendList=new ArrayList<>();
//        firebaseFirestore.collection("Users").document(email)
//                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                if(task.isSuccessful()){
//                    ArrayList<String> strings= (ArrayList<String>) task.getResult().get("friendList");
////                    Toast.makeText(getContext(), strings.size()+"", Toast.LENGTH_SHORT).show();
//                    friendList.addAll(strings);
//                    Toast.makeText(getContext(), "friendList : "+friendList.size(), Toast.LENGTH_SHORT).show();
//                    for(String x:friendList){
//                        Toast.makeText(getContext(),x, Toast.LENGTH_SHORT).show();
//
//                    }
//                    for(String x:strings) {
////                        Toast.makeText(getContext(), "name"+x, Toast.LENGTH_SHORT).show();
//                        firebaseFirestore.collection("Users").document(x)
//                                .collection("Posts").orderBy("timestamp", Query.Direction.DESCENDING)
//                                .addSnapshotListener(new EventListener<QuerySnapshot>() {
//                                    @Override
//                                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
//                                        for (DocumentChange doc : value.getDocumentChanges()) {
//                                            if (doc.getType() == DocumentChange.Type.ADDED) {
//                                                BlogPost blogPost = doc.getDocument().toObject(BlogPost.class);
//                                                blogList.add(blogPost);
//                                                blogAdapter.notifyDataSetChanged();
//                                            }
//                                        }
//                                    }
//                                });
//                    }
//                }
//            }
//        });
//        firebaseFirestore.collection("Users").document(email)
//                .collection("friendPosts")
//                .orderBy("timestamp",Query.Direction.DESCENDING)
//                .addSnapshotListener(new EventListener<QuerySnapshot>() {
//                    @Override
//                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
//                        for (DocumentChange doc : value.getDocumentChanges()) {
//                            if (doc.getType() == DocumentChange.Type.ADDED) {
//                                BlogPost blogPost = doc.getDocument().toObject(BlogPost.class);
//                                blogList.add(blogPost);
//                                blogAdapter.notifyDataSetChanged();
//                            }
//                        }
//                    }
//                });

//        firebaseFirestore.collection("Users").document("tranvantri2000")
//                .collection("Posts")
//                .addSnapshotListener(new EventListener<QuerySnapshot>() {
//                    @Override
//                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
//                        for(DocumentChange doc:value.getDocumentChanges()){
//                            if(doc.getType()==DocumentChange.Type.ADDED){
//                                BlogPost blogPost=doc.getDocument().toObject(BlogPost.class);
//                                blogList.add(blogPost);
//                                blogAdapter.notifyDataSetChanged();
//                            }
//                        }
//                    }
//                });
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(v.getContext(), "Go to post news !!", Toast.LENGTH_SHORT).show();
                // do something
                Intent postIntent=new Intent(v.getContext(), NewPostActivity.class);
                v.getContext().startActivity(postIntent);
            }
        });
        initLoad(email);
        blogListView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager linearLayoutManager=(LinearLayoutManager)recyclerView.getLayoutManager();
                if(!mIsLoadingData){
                    if(linearLayoutManager!=null&&linearLayoutManager.findLastCompletelyVisibleItemPosition()==blogList.size()-1){
                        blogListView.post(new Runnable() {
                            @Override
                            public void run() {
                                LazyLoad(email);
                            }
                        });
                    }
                }
            }
        });
        return v;
    }
    public void initLoad(final String email){
        firebaseFirestore.collection("Users").document(email)
                .collection("friendPosts")
                .orderBy("timestamp",Query.Direction.DESCENDING)
                .limit(LIM)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        for(DocumentChange doc:value.getDocumentChanges()){
                            if(doc.getType()==DocumentChange.Type.ADDED){
                                blogID.add(doc.getDocument().getId());
                                BlogPost blogPost=doc.getDocument().toObject(BlogPost.class);
                                blogList.add(blogPost);
                                blogAdapter.notifyDataSetChanged();
                                Log.d("loglog",doc.getDocument().getId());
                                ++lim;
                            }
                        }
                    }
                });
    }
    public void LazyLoad(final String email){
        Toast.makeText(getContext(), "something", Toast.LENGTH_SHORT).show();
        firebaseFirestore.collection("Users").document(email)
                .collection("friendPosts")
                .orderBy("timestamp",Query.Direction.DESCENDING)
                .limit(lim).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                final DocumentSnapshot lastVisible=queryDocumentSnapshots.getDocuments()
                        .get(queryDocumentSnapshots.size()-1);
                Query next=firebaseFirestore.collection("Users").document(email)
                        .collection("friendPosts")
                        .orderBy("timestamp",Query.Direction.DESCENDING)
                        .startAfter(lastVisible)
                        .limit(LIM);
                next.addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        Toast.makeText(v.getContext(), "lastVisible : "+lastVisible.toObject(BlogPost.class).getUserId(), Toast.LENGTH_SHORT).show();
                        for(DocumentChange doc:value.getDocumentChanges()){
                            if(doc.getType()==DocumentChange.Type.ADDED){
                                blogID.add(doc.getDocument().getId());
                                BlogPost blogPost=doc.getDocument().toObject(BlogPost.class);
                                blogList.add(blogPost);
                                blogAdapter.notifyDataSetChanged();
                                Log.d("loglog",doc.getDocument().getId());
                                ++lim;
                            }
                        }
                    }
                });
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}
