package com.example.fakebook;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.fakebook.adapters.UserApdater;
import com.example.fakebook.model.SearchUser;
import com.example.fakebook.model.User;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ListSearchActivity extends AppCompatActivity {
    private RecyclerView rv;
    private List<SearchUser> mainList=new ArrayList<>();
    private UserApdater adapter;
    private FirebaseFirestore firebaseFirestore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_search);
        firebaseFirestore=FirebaseFirestore.getInstance();
        Intent intent=getIntent();
        String name=intent.getStringExtra("name");
        Toast.makeText(this, "name : "+name, Toast.LENGTH_SHORT).show();
        rv=findViewById(R.id.rv);
        adapter=new UserApdater(mainList);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(adapter);
        Query query=firebaseFirestore.collection("Users").orderBy("name")
                .startAt(name).endAt(name+"\uf8ff");
        query.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                for(DocumentChange doc:value.getDocumentChanges()){
                    if(doc.getType()==DocumentChange.Type.ADDED){
                        User user=doc.getDocument().toObject(User.class);
                        SearchUser cur=new SearchUser(user.getAvatar(),user.getName(),user.getEmail());
                        mainList.add(cur);
                        adapter.notifyDataSetChanged();
                    }
                }
            }
        });
    }
}