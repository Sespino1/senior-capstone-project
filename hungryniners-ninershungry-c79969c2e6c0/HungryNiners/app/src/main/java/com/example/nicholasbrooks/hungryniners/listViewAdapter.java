package com.example.nicholasbrooks.hungryniners;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.nearby.messages.PublishCallback;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.ocpsoft.prettytime.PrettyTime;

import java.sql.Time;
import java.util.Calendar;
import java.util.List;

/**
 * Created by nicholasbrooks on 10/24/16.
 */
public class listViewAdapter extends ArrayAdapter<Event> {
    TextView eventName, buildingName, buildingRoom, time;
    Bitmap bitmap;
    Boolean image = false;
    List<Event> mData;
    Context mContext;
    int mResource;
    ImageView imageView;
    DatabaseReference mDataBase;
    StorageReference storageRef = FirebaseStorage.getInstance().getReference();


    public listViewAdapter(Context context, int resource, List<Event> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.mData = objects;
        this.mResource =resource;


    }

    public static class ImageMngr{
        ImageView imageView;
        StorageReference imageRef;
        Bitmap bitmap;
        public ImageMngr(ImageView imageView, StorageReference imageRef){
            this.imageRef = imageRef;
            this.imageView = imageView;
            getImage();
        }

        public void getImage(){
            long ONE_MEGABYTE = 1024 * 1024 * 5;
            imageRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                @Override
                public void onSuccess(byte[] bytes) {
                    bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                    imageView.setImageBitmap(bitmap);
                    imageView.setMaxHeight(200);
                    imageView.setMaxWidth(200);
                    imageView.setAdjustViewBounds(true);
                    imageView.refreshDrawableState();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    Log.d("images", "Failed");
                }
            }).addOnCompleteListener(new OnCompleteListener<byte[]>() {
                @Override
                public void onComplete(@NonNull Task<byte[]> task) {
                    task.isSuccessful();
                }
            });
        }

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(mResource, parent, false);
        }
        mDataBase = FirebaseDatabase.getInstance().getReference();
        Event event = mData.get(position);

        ///imageView = (ImageView)convertView.findViewById(R.id.imageViewBuildingDisplay);
        ///imageView.setTag(new ImageMngr(imageView, ));



//        imageView = (ImageView)convertView.findViewById(R.id.imageViewBuildingDisplay);
//        String path = "photos/" + event.getBuildingName() + ".jpg";
//        final StorageReference imageRef = storageRef.child(path);
//        final long ONE_MEGABYTE = 1024 * 1024 * 5;
//        imageRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
//            @Override
//            public void onSuccess(byte[] bytes) {
//                bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
//                imageView.setImageBitmap(bitmap);
//                imageView.setMaxHeight(200);
//                imageView.setMaxWidth(200);
//                imageView.setAdjustViewBounds(true);
//                imageView.refreshDrawableState();
//                image = true;
//                Log.d("images", "SUCCESS! Getting the image.");
//
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception exception) {
//                Log.d("images", "Failed");
//            }
//        }).addOnCompleteListener(new OnCompleteListener<byte[]>() {
//            @Override
//            public void onComplete(@NonNull Task<byte[]> task) {
//                task.isSuccessful();
//                image = true;
//            }
//        });
        ///if(event.getTime())


        eventName = (TextView )convertView.findViewById(R.id.textViewNameEventDisplay);
        eventName.setText("Event: " + event.getEventName());
        buildingName = (TextView)convertView.findViewById(R.id.textViewBuldingDisplay);
        buildingName.setText("Building: " + event.getBuildingName());
        buildingRoom = (TextView) convertView.findViewById(R.id.textViewRoomNameDIsplay);
        buildingRoom.setText("Room #: " + event.getRoomNumber());
        time = (TextView)convertView.findViewById(R.id.textViewTimeBounds);
        time.setText(new PrettyTime().format(event.getTime()));

      //  imageView = (ImageView)convertView.findViewById(R.id.imageViewBuildingDisplay);


        this.notifyDataSetChanged();

        return convertView;


    }
}
