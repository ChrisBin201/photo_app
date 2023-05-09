package com.example.photo_app;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.photo_app.adapter.ViewPagerAdapter;
import com.example.photo_app.model.notification.NotificationModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.MetadataChanges;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavi;

    private ViewPager viewPager;
    List<NotificationModel> notifications = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // khởi tạo shared preferences flickr = false
        SharedPreferences sharedPreferences = getSharedPreferences("flickr", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("flickr", false);
        editor.apply();

        setContentView(R.layout.activity_main);
        bottomNavi = findViewById(R.id.bottomNavi);
        viewPager = findViewById(R.id.viewPager);

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(),
                FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);

        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        bottomNavi.getMenu().findItem(R.id.mHome).setChecked(true);
                        break;
                    case 1:
                        bottomNavi.getMenu().findItem(R.id.mSearch).setChecked(true);
                        break;
                    case 2:
                        bottomNavi.getMenu().findItem(R.id.mUpload).setChecked(true);
                        break;
                    case 3:
                        bottomNavi.getMenu().findItem(R.id.mProfile).setChecked(true);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        bottomNavi.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.mHome:
                    viewPager.setCurrentItem(0);
                    break;
                case R.id.mSearch:
                    viewPager.setCurrentItem(1);
                    break;
                case R.id.mUpload:
                    viewPager.setCurrentItem(2);
                    break;
                case R.id.mProfile:
                    viewPager.setCurrentItem(3);
                    break;
            }
            return true;
        });

        // Listen for metadata changes to the document.
        FirebaseFirestore db = FirebaseFirestore.getInstance();
//        CollectionReference collectionRef  = db.collection("notifications");
        db.collection("notifications")
                .whereEqualTo("objectReceiveId", 3)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    Log.w("MainActivity", "Listen failed.", error);
                    return;
                }

                List<NotificationModel> notificationModels = new ArrayList<>();
                for (QueryDocumentSnapshot doc : value) {
//                    if (doc.get("name") != null) {
                    NotificationModel notification = new NotificationModel(
                            doc.getLong("id"),
                            doc.getLong("objectReceiveId"),
                            doc.getString("type"),
                            doc.getLong("objectSendId"),
                            doc.getString("content"),
                            doc.getString("status")
                    );
                    notificationModels.add(notification);
//                    }
                }
                notifications.clear();
                notifications.addAll(notificationModels);
                bottomNavi.getOrCreateBadge(R.id.mNoti).setNumber(notifications.size());
                Log.d("MainActivity", "Current notifications : " + notifications.size());
            }
        });
    }


}


