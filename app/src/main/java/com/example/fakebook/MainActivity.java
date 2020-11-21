package com.example.fakebook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.fakebook.fragments.FriendsFragment;
import com.example.fakebook.fragments.GamesFragment;
import com.example.fakebook.fragments.HomeFragment;

import com.example.fakebook.fragments.NotificationsFragment;
import com.example.fakebook.fragments.PersonalityFragment;
import com.example.fakebook.model.FriendRequests;
import com.example.fakebook.model.Notification;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static ArrayList<FriendRequests> friendRequestsArrayList=new ArrayList<>();
    public static ArrayList<Notification> notificationArrayList=new ArrayList<>();
    public static FirebaseFirestore DB = FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUser();
        BottomNavigationView bottomNav=findViewById(R.id.bottom_nav);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new HomeFragment()).commit(); // moi vo cho cai home la mac dinh
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.ac_logout:
                Toast.makeText(this, "Logout", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.ac_search:
                Toast.makeText(this, "Search", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.ac_setting:
                Toast.makeText(this, "Settings", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return false;
        }
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
                case R.id.nav_friend:
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
    public void setUser()
    {

//        setData();
//        user=new User("Tran Van Hien","","18/07/2000",true);
//        user.setFriendRequestList(friendRequestsArrayList);
//        user.setNotificationList(notificationArrayList);
//
//        DB.collection("user")
//                .document("Trần Văn Hiền")
//                .set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
//            @Override
//            public void onSuccess(Void aVoid) {
//                Toast.makeText(MainActivity.this ,"probinh", Toast.LENGTH_SHORT).show();
//            }
//        });


    }
    public void setData()
    {
        notificationArrayList.add(new Notification("Sana đã thích ảnh của bạn","1 giờ trước",R.color.design_default_color_error));
        notificationArrayList.add(new Notification("Momo đã thích ảnh của bạn","3 giờ trước",R.color.design_default_color_on_secondary));
        notificationArrayList.add(new Notification("Văn Hiền đã thích ảnh của bạn","5 giờ trước",R.color.colorPrimary));
        notificationArrayList.add(new Notification("Văn Hiền đã chia sẽ ảnh của bạn","6 giờ trước",R.color.material_on_background_emphasis_high_type));
        notificationArrayList.add(new Notification("Sana đã chia sẽ ảnh của bạn","10 giờ trước",R.color.material_on_surface_stroke));
        notificationArrayList.add(new Notification("Momo đã chia sẽ ảnh của bạn","12 giờ trước",R.color.design_default_color_primary_variant));
        notificationArrayList.add(new Notification("John đã chia sẽ ảnh của bạn","19 giờ trước",R.color.colorAccent));
        notificationArrayList.add(new Notification("Sana đã thích ảnh của bạn","20 giờ trước",R.color.design_default_color_error));
        notificationArrayList.add(new Notification("Sana đã chia sẽ ảnh của bạn","23 giờ trước",R.color.design_default_color_error));
        notificationArrayList.add(new Notification("Sana đã thích ảnh của bạn","1 giờ trước",R.color.design_default_color_error));
        notificationArrayList.add(new Notification("Momo đã thích ảnh của bạn","3 giờ trước",R.color.design_default_color_on_secondary));
        notificationArrayList.add(new Notification("Văn Hiền đã thích ảnh của bạn","5 giờ trước",R.color.colorPrimary));
        notificationArrayList.add(new Notification("Văn Hiền đã chia sẽ ảnh của bạn","6 giờ trước",R.color.material_on_background_emphasis_high_type));
        notificationArrayList.add(new Notification("Sana đã chia sẽ ảnh của bạn","10 giờ trước",R.color.material_on_surface_stroke));
        notificationArrayList.add(new Notification("Momo đã chia sẽ ảnh của bạn","12 giờ trước",R.color.design_default_color_primary_variant));
        notificationArrayList.add(new Notification("John đã chia sẽ ảnh của bạn","19 giờ trước",R.color.colorAccent));
        notificationArrayList.add(new Notification("Sana đã thích ảnh của bạn","20 giờ trước",R.color.design_default_color_error));
        notificationArrayList.add(new Notification("Sana đã chia sẽ ảnh của bạn","23 giờ trước",R.color.design_default_color_error));notificationArrayList.add(new Notification("Sana đã thích ảnh của bạn","1 giờ trước",R.color.design_default_color_error));
        notificationArrayList.add(new Notification("Momo đã thích ảnh của bạn","3 giờ trước",R.color.design_default_color_on_secondary));
        notificationArrayList.add(new Notification("Văn Hiền đã thích ảnh của bạn","5 giờ trước",R.color.colorPrimary));
        notificationArrayList.add(new Notification("Văn Hiền đã chia sẽ ảnh của bạn","6 giờ trước",R.color.material_on_background_emphasis_high_type));
        notificationArrayList.add(new Notification("Sana đã chia sẽ ảnh của bạn","10 giờ trước",R.color.material_on_surface_stroke));
        notificationArrayList.add(new Notification("Momo đã chia sẽ ảnh của bạn","12 giờ trước",R.color.design_default_color_primary_variant));
        notificationArrayList.add(new Notification("John đã chia sẽ ảnh của bạn","19 giờ trước",R.color.colorAccent));
        notificationArrayList.add(new Notification("Sana đã thích ảnh của bạn","20 giờ trước",R.color.design_default_color_error));
        notificationArrayList.add(new Notification("Sana đã chia sẽ ảnh của bạn","23 giờ trước",R.color.design_default_color_error));notificationArrayList.add(new Notification("Sana đã thích ảnh của bạn","1 giờ trước",R.color.design_default_color_error));
        notificationArrayList.add(new Notification("Momo đã thích ảnh của bạn","3 giờ trước",R.color.design_default_color_on_secondary));
        notificationArrayList.add(new Notification("Văn Hiền đã thích ảnh của bạn","5 giờ trước",R.color.colorPrimary));
        notificationArrayList.add(new Notification("Văn Hiền đã chia sẽ ảnh của bạn","6 giờ trước",R.color.material_on_background_emphasis_high_type));
        notificationArrayList.add(new Notification("Sana đã chia sẽ ảnh của bạn","10 giờ trước",R.color.material_on_surface_stroke));
        notificationArrayList.add(new Notification("Momo đã chia sẽ ảnh của bạn","12 giờ trước",R.color.design_default_color_primary_variant));
        notificationArrayList.add(new Notification("John đã chia sẽ ảnh của bạn","19 giờ trước",R.color.colorAccent));
        notificationArrayList.add(new Notification("Sana đã thích ảnh của bạn","20 giờ trước",R.color.design_default_color_error));
        notificationArrayList.add(new Notification("Sana đã chia sẽ ảnh của bạn","23 giờ trước",R.color.design_default_color_error));
        notificationArrayList.add(new Notification("Sana đã thích ảnh của bạn","1 giờ trước",R.color.design_default_color_error));
        notificationArrayList.add(new Notification("Momo đã thích ảnh của bạn","3 giờ trước",R.color.design_default_color_on_secondary));
        notificationArrayList.add(new Notification("Văn Hiền đã thích ảnh của bạn","5 giờ trước",R.color.colorPrimary));
        notificationArrayList.add(new Notification("Văn Hiền đã chia sẽ ảnh của bạn","6 giờ trước",R.color.material_on_background_emphasis_high_type));
        notificationArrayList.add(new Notification("Sana đã chia sẽ ảnh của bạn","10 giờ trước",R.color.material_on_surface_stroke));
        notificationArrayList.add(new Notification("Momo đã chia sẽ ảnh của bạn","12 giờ trước",R.color.design_default_color_primary_variant));
        notificationArrayList.add(new Notification("John đã chia sẽ ảnh của bạn","19 giờ trước",R.color.colorAccent));
        notificationArrayList.add(new Notification("Sana đã thích ảnh của bạn","20 giờ trước",R.color.design_default_color_error));
        notificationArrayList.add(new Notification("Sana đã chia sẽ ảnh của bạn","23 giờ trước",R.color.design_default_color_error));

        
        friendRequestsArrayList.add(new FriendRequests("Minatozaki Sana","",R.color.design_default_color_primary_variant,"1 tiếng trước"));
        friendRequestsArrayList.add(new FriendRequests("Hirai Momo","",R.color.material_on_background_emphasis_high_type,"2 tiếng trước"));
        friendRequestsArrayList.add(new FriendRequests("Im Nayeon","",R.color.colorPrimary,"3 tiếng trước"));
        friendRequestsArrayList.add(new FriendRequests("Minatozaki Sana","",R.color.colorAccent,"4 tiếng trước"));
        friendRequestsArrayList.add(new FriendRequests("Minatozaki Sana","",R.color.design_default_color_primary_variant,"1 tiếng trước"));
        friendRequestsArrayList.add(new FriendRequests("Hirai Momo","",R.color.material_on_background_emphasis_high_type,"2 tiếng trước"));
        friendRequestsArrayList.add(new FriendRequests("Im Nayeon","",R.color.colorPrimary,"3 tiếng trước"));
        friendRequestsArrayList.add(new FriendRequests("Minatozaki Sana","",R.color.colorAccent,"4 tiếng trước"));
        friendRequestsArrayList.add(new FriendRequests("Minatozaki Sana","",R.color.design_default_color_primary_variant,"1 tiếng trước"));
        friendRequestsArrayList.add(new FriendRequests("Hirai Momo","",R.color.material_on_background_emphasis_high_type,"2 tiếng trước"));
        friendRequestsArrayList.add(new FriendRequests("Im Nayeon","",R.color.colorPrimary,"3 tiếng trước"));
        friendRequestsArrayList.add(new FriendRequests("Minatozaki Sana","",R.color.colorAccent,"4 tiếng trước"));
        friendRequestsArrayList.add(new FriendRequests("Minatozaki Sana","",R.color.design_default_color_primary_variant,"1 tiếng trước"));
        friendRequestsArrayList.add(new FriendRequests("Hirai Momo","",R.color.material_on_background_emphasis_high_type,"2 tiếng trước"));
        friendRequestsArrayList.add(new FriendRequests("Im Nayeon","",R.color.colorPrimary,"3 tiếng trước"));
        friendRequestsArrayList.add(new FriendRequests("Minatozaki Sana","",R.color.colorAccent,"4 tiếng trước"));
        friendRequestsArrayList.add(new FriendRequests("Minatozaki Sana","",R.color.design_default_color_primary_variant,"1 tiếng trước"));
        friendRequestsArrayList.add(new FriendRequests("Hirai Momo","",R.color.material_on_background_emphasis_high_type,"2 tiếng trước"));
        friendRequestsArrayList.add(new FriendRequests("Im Nayeon","",R.color.colorPrimary,"3 tiếng trước"));
        friendRequestsArrayList.add(new FriendRequests("Minatozaki Sana","",R.color.colorAccent,"4 tiếng trước"));
        friendRequestsArrayList.add(new FriendRequests("Minatozaki Sana","",R.color.design_default_color_primary_variant,"1 tiếng trước"));
        friendRequestsArrayList.add(new FriendRequests("Hirai Momo","",R.color.material_on_background_emphasis_high_type,"2 tiếng trước"));
        friendRequestsArrayList.add(new FriendRequests("Im Nayeon","",R.color.colorPrimary,"3 tiếng trước"));
        friendRequestsArrayList.add(new FriendRequests("Minatozaki Sana","",R.color.colorAccent,"4 tiếng trước"));
        friendRequestsArrayList.add(new FriendRequests("Minatozaki Sana","",R.color.design_default_color_primary_variant,"1 tiếng trước"));
        friendRequestsArrayList.add(new FriendRequests("Hirai Momo","",R.color.material_on_background_emphasis_high_type,"2 tiếng trước"));
        friendRequestsArrayList.add(new FriendRequests("Im Nayeon","",R.color.colorPrimary,"3 tiếng trước"));
        friendRequestsArrayList.add(new FriendRequests("Minatozaki Sana","",R.color.colorAccent,"4 tiếng trước"));
        friendRequestsArrayList.add(new FriendRequests("Minatozaki Sana","",R.color.design_default_color_primary_variant,"1 tiếng trước"));
        friendRequestsArrayList.add(new FriendRequests("Hirai Momo","",R.color.material_on_background_emphasis_high_type,"2 tiếng trước"));
        friendRequestsArrayList.add(new FriendRequests("Im Nayeon","",R.color.colorPrimary,"3 tiếng trước"));
        friendRequestsArrayList.add(new FriendRequests("Minatozaki Sana","",R.color.colorAccent,"4 tiếng trước"));
    }
}