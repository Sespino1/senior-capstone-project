package com.example.nicholasbrooks.hungryniners;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.Random;

public class EventFormActivity extends AppCompatActivity implements View.OnClickListener{
    EditText eventName, roomNumber, description, opName;
    TextView buildingName;
    Button submit, cancel;
    String building;
    DatabaseReference mDataBase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_form);
        Intent intent = getIntent();
        building = (String)intent.getExtras().get("PIN");
        mDataBase = FirebaseDatabase.getInstance().getReference();
        eventName = (EditText)findViewById(R.id.editTextEventNameForm);
        buildingName = (TextView)findViewById(R.id.editTextBuildingNameForm);
        buildingName.setText(building);
        roomNumber = (EditText)findViewById(R.id.editTextRoomNumberForm);
        description = (EditText)findViewById(R.id.editTextSDescriptionForm);
        opName = (EditText)findViewById(R.id.editTextOPForm);

        submit = (Button)findViewById(R.id.buttonSubmitForm);
        submit.setOnClickListener(this);
        cancel = (Button)findViewById(R.id.buttonCancelForm);
        cancel.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if(v.getId() == submit.getId()){
            Random rand = new Random();
            int id = rand.nextInt(10000);
            Event event = new Event();
            event.setEventName(eventName.getText().toString());
            event.setBuildingName(building);
            event.setRoomNumber(roomNumber.getText().toString());
            event.setDescription(description.getText().toString());
            event.setOriginalPoster(opName.getText().toString());
            event.setEventID(String.valueOf(id));
            event.setTime(Calendar.getInstance().getTime());
            mDataBase.child(building).child(String.valueOf(id)).setValue(event);
            finish();
        }else if(v.getId() == cancel.getId()){
            finish();

        }
    }
}
