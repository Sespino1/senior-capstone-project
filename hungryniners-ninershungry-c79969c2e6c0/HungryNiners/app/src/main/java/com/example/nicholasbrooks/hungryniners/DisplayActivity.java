package com.example.nicholasbrooks.hungryniners;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Calendar;

public class DisplayActivity extends AppCompatActivity implements View.OnClickListener{
    DatabaseReference mDataBase;
    DatabaseReference events;
    ArrayList<Event> eventList = new ArrayList<Event>();
    ArrayAdapter<Event> adapter;
    ListView showExpenses;
    TextView buildingDisplay;
    ImageView imageViewBuilding;
    ImageButton exit;
    StorageReference storageRef = FirebaseStorage.getInstance().getReference();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);
        exit = (ImageButton)findViewById(R.id.imageButtonExitDisplay);
        exit.setOnClickListener(this);
        mDataBase = FirebaseDatabase.getInstance().getReference();
        Intent intent = getIntent();
        String building = intent.getStringExtra("PIN");
        buildingDisplay = (TextView)findViewById(R.id.textViewListBuildingName);
        buildingDisplay.setText(building + " Events");
        showExpenses = (ListView)findViewById(R.id.listViewExpenses);
        events  = mDataBase.child(building);
        events.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                eventList = new ArrayList<Event>();
                Event event = dataSnapshot.getValue(Event.class);
                for(DataSnapshot child: dataSnapshot.getChildren()){
                     event = child.getValue(Event.class);
                    if(Calendar.getInstance().getTimeInMillis() > event.getTime().getTime()+3600000) {
                        mDataBase.child(event.getBuildingName()).child(String.valueOf(event.getEventID())).removeValue();
                    }else{
                        eventList.add(event);

                    }
                }

                Log.d("events", eventList.toString());
                if(event != null) {
                    adapter = new listViewAdapter(DisplayActivity.this, R.layout.listviewadapter, eventList);
                    showExpenses.setAdapter(adapter);
                    adapter.setNotifyOnChange(true);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        imageViewBuilding = (ImageView)findViewById(R.id.imageViewBuildingDisplayActivity);
        String path = "photos/" + building + ".jpg";
        final StorageReference imageRef = storageRef.child(path);
        final long ONE_MEGABYTE = 1024 * 1024 * 5;
        imageRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                imageViewBuilding.setImageBitmap(bitmap);
                imageViewBuilding.setMaxHeight(200);
                imageViewBuilding.setMaxWidth(200);
                imageViewBuilding.setAdjustViewBounds(true);
                imageViewBuilding.refreshDrawableState();
                Log.d("images", "SUCCESS! Getting the image.");

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Log.d("images", "Failed");
            }
        });
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == exit.getId()){
            finish();
        }
    }
}
