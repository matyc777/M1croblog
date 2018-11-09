package com.example.nikita.m1croblog.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Pair;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.nikita.m1croblog.R;
import com.example.nikita.m1croblog.model.UserInformation;
import com.example.nikita.m1croblog.model.UserRecord;
import com.example.nikita.m1croblog.service.RecordListSorter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RecordsActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;

    private List<UserRecord> userRecordsList;
    private ListView recordsListView;

    private Button addRecordButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_records);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Your e-mail: " + mAuth.getCurrentUser().getEmail(), Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        addRecordButton = findViewById(R.id.addNewRecordButton);

        addRecordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecordsActivity.this, AddRecordActivity.class);
                startActivity(intent);
            }
        });

        recordsListView = findViewById(R.id.records_list);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        databaseReference = FirebaseDatabase.getInstance().getReference().child(user.getUid() + "/Records");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                userRecordsList = new ArrayList<>();
                for (DataSnapshot dataS: dataSnapshot.getChildren()){
                    userRecordsList.add(dataS.getValue(UserRecord.class));
                }
                updateUI();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.nav_home) {

        } else if (id == R.id.nav_add) {
            Intent intent = new Intent(RecordsActivity.this, AddRecordActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_profile) {
            Intent intent = new Intent(RecordsActivity.this, UserInfoActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_logout) {
            mAuth.signOut();
            Intent intent = new Intent(RecordsActivity.this, AuthenticationActivity.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void updateUI() {
        List<Pair<String, String>> list = new ArrayList<>();
        RecordListSorter sorter = new RecordListSorter();
        userRecordsList = sorter.sort(userRecordsList);
        for (UserRecord record : userRecordsList) {
            System.out.println(record.getDate() + " " + record.getText());
        }
        for (UserRecord userRecord: userRecordsList){
            list.add(new Pair<>(userRecord.getDate(),userRecord.getText()));
        }

        List<HashMap<String, String>> listItems = new ArrayList<>();

        SimpleAdapter adapter = new SimpleAdapter(this, listItems, R.layout.record_list_item,
                new String[]{"First Line", "Second Line"}, new int[]{R.id.item, R.id.sub_item});

        for (Pair<String, String> pair : list) {
            HashMap<String, String> resultMap = new HashMap<>();
            resultMap.put("First Line", pair.first);
            resultMap.put("Second Line", pair.second);
            listItems.add(resultMap);
        }
        recordsListView.setAdapter(adapter);
    }
}
