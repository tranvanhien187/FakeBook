package com.example.fakebook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.fakebook.fragments.FriendsFragment;
import com.example.fakebook.fragments.GamesFragment;
import com.example.fakebook.fragments.HomeFragment;
import com.example.fakebook.fragments.NotificationsFragment;
import com.example.fakebook.fragments.PersonalityFragment;
import com.example.fakebook.login.LoginActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    private Fragment containerFragment;
    private Toolbar toolbar;
    private EditText edtSearch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar=findViewById(R.id.new_post_toolbar);
        toolbar.setTitle("FakeBook");
        edtSearch=findViewById(R.id.edtSearch);
        BottomNavigationView bottomNav=findViewById(R.id.bottom_nav);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new HomeFragment()).commit(); // moi vo cho cai home la mac dinh
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.ac_logout:
                        SignOut();
//                        Toast.makeText(MainActivity.this, "logout", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.ac_search:
                        Toast.makeText(MainActivity.this, "search", Toast.LENGTH_SHORT).show();
                        String text=edtSearch.getText().toString().trim();
                        if(TextUtils.isEmpty(text)){
                            Toast.makeText(MainActivity.this, "Nhap ten can tim", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Intent searchIntent=new Intent(MainActivity.this, ListSearchActivity.class);
                            searchIntent.putExtra("name",text);
                            startActivity(searchIntent);
                        }
                        return true;
                    case R.id.ac_setting:
                        Toast.makeText(MainActivity.this, "setting", Toast.LENGTH_SHORT).show();
                        return true;
                }
                return false;
            }
        });
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new HomeFragment()).commit();
    }
    public void SignOut(){
        FirebaseAuth.getInstance().signOut();
        Intent loginIntent=new Intent(MainActivity.this, LoginActivity.class);
        startActivity(loginIntent);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener=new BottomNavigationView.OnNavigationItemSelectedListener() {
        // chuyen fragment
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            Fragment selectedFragment=null;
            switch (menuItem.getItemId()){
                case R.id.nav_home:
                    selectedFragment=new HomeFragment();
                    break;
                case R.id.nav_friend_request:
                    selectedFragment=new FriendsFragment();
                    break;
                case R.id.nav_personality:
                    selectedFragment=new PersonalityFragment();
                    break;
                case R.id.nav_notification:
                    selectedFragment=new NotificationsFragment();
                    break;
                case R.id.nav_game:
                    selectedFragment=new GamesFragment();
                    break;

            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,selectedFragment).commit();
            return true;
        }
    };

}