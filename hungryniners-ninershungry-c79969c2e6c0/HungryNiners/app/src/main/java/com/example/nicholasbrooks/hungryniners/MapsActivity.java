package com.example.nicholasbrooks.hungryniners;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ZoomControls;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.Map;

import javax.crypto.Mac;



public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, GoogleMap.OnMapClickListener, GoogleMap.OnMarkerClickListener {
    private String currentPin;
    MapsActivity mActivity;
    private ArrayList<LatLng> boundList;
    private CharSequence[] dialog = {"Add Event", "See Events","All Events", "Cancel"};
    private GoogleMap map;
    private GoogleApiClient api;
    private TextView Longitude;
    private TextView Lat;
    private GoogleApiClient client;
    private double unccX = 35.305373;
    private double unccY = -80.730964;
    private double studentUnionX = 35.308604;
    private double studentUnionY = -80.733698;
    private double woodwardX = 35.307552;
    private double woodwardY = -80.735735;
    private double BelkGymX = 35.305376;
    private double BelkGymY = -80.735688;
    private double BarnHardtSACX = 35.306228;
    private double BarnHardtSACY = -80.734339;
    private double CollegeEducationX = 35.307717;
    private double CollegeEducationY = -80.733978;
    private double CHHSX = 35.307424;
    private double CHHSY = -80.733354;
    private double BursonX = 35.307428;
    private double BursonY = -80.732500;
    private double MiltmoreWallisX = 35.306846;
    private double MiltmoreWallisY = -80.734546;
    private double ConeCenterX = 35.305093;
    private double ConeCenterY = -80.733245;
    private double ReeseX = 35.304658;
    private double ReeseY = -80.732525;
    private double KingX = 35.305078;
    private double KingY = -80.732551;
    private double ColvardX = 35.304633;
    private double ColvardY = -80.731757;
    private double AtkinsLibraryX = 35.305840;
    private double AtkinsLibraryY = -80.732064;
    private double RoweX = 35.304403;
    private double RoweY = -80.730719;
    private double WinninghamX = 35.305127;
    private double WinninghamY = -80.730421;
    private double GaringerX = 35.304990;
    private double GaringerY = -80.730031;
    private double DennyX = 35.305403;
    private double DennyY = -80.729802;
    private double BarnardX = 35.305793;
    private double BarnardY = -80.730028;
    private double MacyX = 35.305674;
    private double MacyY = -80.730418;
    private double RobinsonHallX = 35.303804;
    private double RobinsonHallY = -80.729976;
    private double StorrsX = 35.304588;
    private double StorrsY = -80.729124;
    private double CatoHallX = 35.305454;
    private double CatoHallY = -80.728707;
    private double FretwellX = 35.306179;
    private double FretwellY = -80.728971;
    private double KennedyX = 35.305966;
    private double KennedyY = -80.730937;
    private double McEniryX = 35.307103;
    private double McEniryY = -80.730073;
    private double SmithX = 35.306875;
    private double SmithY = -80.731593;
    private double ProspectorX = 35.307038;
    private double ProspectorY = -80.730691;
    private double AuxilaryServicesX = 35.307726;
    private double AuxilaryServicesY = -80.730515;
    private double McMillanGreenX = 35.307825;
    private double McMillanGreenY = -80.729696;
    private double ParkingServicesX = 35.308857;
    private double ParkingServicesY = -80.729794;

    ///Making the global MarkerOptions for each building
    MarkerOptions UNCC_Campus_Marker;
    MarkerOptions student_Union__Marker;
    MarkerOptions woodWard_Marker;
    MarkerOptions belk_Gym_Marker;
    MarkerOptions barn_Hardt_SAC_Marker;
    MarkerOptions college_Of_Edu;
    MarkerOptions CHHS_Marker;
    MarkerOptions burson_Marker;
    MarkerOptions miltmore_Wallis_Center_Marker;
    MarkerOptions cone_Center_Marker;
    MarkerOptions reese_Marker;
    MarkerOptions king_Marker;
    MarkerOptions colvard_Maker;
    MarkerOptions atkins_Library_Marker;
    MarkerOptions rowe_Marker;
    MarkerOptions winningham_Maker;
    MarkerOptions garinger_Marker;
    MarkerOptions denny_Marker;
    MarkerOptions barnard_Marker;
    MarkerOptions macy_Marker;
    MarkerOptions robinson_Hall_Marker;
    MarkerOptions storrs_Marker;
    MarkerOptions cato_Hall_Marker;
    MarkerOptions fretwell_Marker;
    MarkerOptions kennedy_Marker;
    MarkerOptions mcEniry_Marker;
    MarkerOptions smith_Marker;
    MarkerOptions prospector_Marker;
    MarkerOptions auxilary_Services_Marker;
    MarkerOptions mcMillian_Green_Marker;
    MarkerOptions parking_Services_Marker;

    ///Creating coordinates for for positioning of Markers
    LatLng UNC_Campus = new LatLng(unccX, unccY);
    LatLng Student_Union = new LatLng(studentUnionX, studentUnionY); ///Got it
    LatLng WoodWard = new LatLng(woodwardX, woodwardY);///Got it
    LatLng BelkGym = new LatLng(BelkGymX, BelkGymY);///Got it
    LatLng BarnHardtSAC = new LatLng(BarnHardtSACX, BarnHardtSACY);///Got it
    LatLng CollegeofEdu = new LatLng(CollegeEducationX, CollegeEducationY);///Got it
    LatLng CHHS = new LatLng(CHHSX, CHHSY);///Got it
    LatLng Burson = new LatLng(BursonX, BursonY);///Got it
    LatLng MiltmoreWallisCenter = new LatLng(MiltmoreWallisX, MiltmoreWallisY);///Got it
    LatLng ConeCenter = new LatLng(ConeCenterX, ConeCenterY);///Got it
    LatLng Reese = new LatLng(ReeseX, ReeseY);///Got it
    LatLng King = new LatLng(KingX, KingY);///Got it
    LatLng Colvard = new LatLng(ColvardX, ColvardY);///Got it
    LatLng AtkinsLibrary = new LatLng(AtkinsLibraryX, AtkinsLibraryY);///Got it
    LatLng Rowe = new LatLng(RoweX, RoweY); ///Got it
    LatLng Winningham = new LatLng(WinninghamX, WinninghamY);///Got it
    LatLng Garinger = new LatLng(GaringerX, GaringerY);///Got it
    LatLng Denny = new LatLng(DennyX, DennyY);///Got it
    LatLng Barnard = new LatLng(BarnardX, BarnardY);///Got it
    LatLng Macy = new LatLng(MacyX, MacyY);///Got it
    LatLng RobinsonHall = new LatLng(RobinsonHallX, RobinsonHallY);///Got it
    LatLng Storrs = new LatLng(StorrsX, StorrsY);///Got it
    LatLng CatoHall = new LatLng(CatoHallX, CatoHallY); ///Got it
    LatLng Fretwell = new LatLng(FretwellX, FretwellY); ///Got it
    LatLng Kennedy = new LatLng(KennedyX, KennedyY);///Got it
    LatLng McEniry = new LatLng(McEniryX, McEniryY);///Got it
    LatLng Smith = new LatLng(SmithX, SmithY);///Got it
    LatLng Prospector = new LatLng(ProspectorX, ProspectorY);///Got it
    LatLng AuxilaryServices = new LatLng(AuxilaryServicesX, AuxilaryServicesY);///Got it
    LatLng McMillianGreen = new LatLng(McMillanGreenX, McMillanGreenY);///Got it
    LatLng ParkingServices = new LatLng(ParkingServicesX, ParkingServicesY);///Got it

    public void markMap() {

        //adding markers to the map that will label buildings
        UNCC_Campus_Marker = new MarkerOptions().position(UNC_Campus).title("UNCC Campus");
        map.addMarker(UNCC_Campus_Marker);
        student_Union__Marker = new MarkerOptions().position(Student_Union).title("Student Union");
        map.addMarker(student_Union__Marker);
        woodWard_Marker = new MarkerOptions().position(WoodWard).title("WoodWard Hall");
        map.addMarker(woodWard_Marker);
        belk_Gym_Marker = new MarkerOptions().position(BelkGym).title("Belk Gymnasium");
        map.addMarker(belk_Gym_Marker);
        barn_Hardt_SAC_Marker = new MarkerOptions().position(BarnHardtSAC).title("Barnhardt Student Activity Center");
        map.addMarker(barn_Hardt_SAC_Marker);
        college_Of_Edu = new MarkerOptions().position(CollegeofEdu).title("College of Education");
        map.addMarker(college_Of_Edu);
        CHHS_Marker = new MarkerOptions().position(CHHS).title("College of Health and Human Services");
        map.addMarker(CHHS_Marker);
        burson_Marker = new MarkerOptions().position(Burson).title("Burson");
        map.addMarker(burson_Marker);
        miltmore_Wallis_Center_Marker = new MarkerOptions().position(MiltmoreWallisCenter).title("Miltimore-Wallis Center");
        map.addMarker(miltmore_Wallis_Center_Marker);
        cone_Center_Marker = new MarkerOptions().position(ConeCenter).title("Cone University Center");
        map.addMarker(cone_Center_Marker);
        reese_Marker = new MarkerOptions().position(Reese).title("Reese");
        map.addMarker(reese_Marker);
        king_Marker = new MarkerOptions().position(King).title("King");
        map.addMarker(king_Marker);
        colvard_Maker = new MarkerOptions().position(Colvard).title("Colvard");
        map.addMarker(colvard_Maker);
        atkins_Library_Marker = new MarkerOptions().position(AtkinsLibrary).title("Atkins Library ");
        map.addMarker(atkins_Library_Marker);
        rowe_Marker = new MarkerOptions().position(Rowe).title("Rowe");
        map.addMarker(rowe_Marker);
        winningham_Maker = new MarkerOptions().position(Winningham).title("Winningham");
        map.addMarker(winningham_Maker);
        garinger_Marker = new MarkerOptions().position(Garinger).title("Garinger");
        map.addMarker(garinger_Marker);
        denny_Marker = new MarkerOptions().position(Denny).title("Denny");
        map.addMarker(denny_Marker);
        barnard_Marker = new MarkerOptions().position(Barnard).title("Barnard");
        map.addMarker(barnard_Marker);
        macy_Marker = new MarkerOptions().position(Macy).title("Macy");
        map.addMarker(macy_Marker);
        robinson_Hall_Marker = new MarkerOptions().position(RobinsonHall).title("Robinson Hall");
        map.addMarker(robinson_Hall_Marker);
        storrs_Marker = new MarkerOptions().position(Storrs).title("Storrs");
        map.addMarker(storrs_Marker);
        cato_Hall_Marker = new MarkerOptions().position(CatoHall).title("Cato Hall");
        map.addMarker(cato_Hall_Marker);
        fretwell_Marker = new MarkerOptions().position(Fretwell).title("Fretwell");
        map.addMarker(fretwell_Marker);
        kennedy_Marker = new MarkerOptions().position(Kennedy).title("Kennedy");
        map.addMarker(kennedy_Marker);
        mcEniry_Marker = new MarkerOptions().position(McEniry).title("McEniry");
        map.addMarker(mcEniry_Marker);
        smith_Marker = new MarkerOptions().position(Smith).title("Smith");
        map.addMarker(smith_Marker);
        prospector_Marker = new MarkerOptions().position(Prospector).title("Prospector");
        map.addMarker(prospector_Marker);
        auxilary_Services_Marker = new MarkerOptions().position(AuxilaryServices).title("Auxilary Services");
        map.addMarker(auxilary_Services_Marker);
        mcMillian_Green_Marker = new MarkerOptions().position(McMillianGreen).title("McMillian Greenhouse");
        map.addMarker(mcMillian_Green_Marker);
        parking_Services_Marker = new MarkerOptions().position(ParkingServices).title("Parking Services");
        map.addMarker(parking_Services_Marker);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        mActivity = this;
        boundList = new ArrayList<LatLng>();
        boundList.add(UNC_Campus);
        boundList.add(Student_Union);
        boundList.add(WoodWard);
        boundList.add(BelkGym);
        boundList.add(BarnHardtSAC);
        boundList.add(CollegeofEdu);
        boundList.add(CHHS);
        boundList.add(Burson);
        boundList.add(MiltmoreWallisCenter);
        boundList.add(ConeCenter);
        boundList.add(Reese);
        boundList.add(King);
        boundList.add(Colvard);
        boundList.add(AtkinsLibrary);
        boundList.add(Rowe);
        boundList.add(Winningham);
        boundList.add(Garinger);
        boundList.add(Denny);
        boundList.add(Barnard);
        boundList.add(Macy);
        boundList.add(RobinsonHall);
        boundList.add(Storrs);
        boundList.add(CatoHall);
        boundList.add(Fretwell);
        boundList.add(Kennedy);
        boundList.add(McEniry);
        boundList.add(Smith);
        boundList.add(Prospector);
        boundList.add(AuxilaryServices);
        boundList.add(McMillianGreen);
        boundList.add(ParkingServices);



        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();

        if (api == null) {
            api = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        map.setOnMarkerClickListener(this);



      //  map.setLatLngBoundsForCameraTarget();
        //creating a circle/boundary for users to place markers(events) later
        Circle circle = map.addCircle(new CircleOptions()
                .center(new LatLng(unccX, unccY))
                .radius(1492.000)
                .strokeColor(Color.RED));

        CameraUpdate location = CameraUpdateFactory.newLatLng(new LatLng(unccX, unccY));
       CameraUpdate zoom = CameraUpdateFactory.zoomTo(16);
        map.moveCamera(location);
        map.animateCamera(zoom);





        //checking to see if we have permission to grab users location
        //to be continued in future sprints

        //this is to move to the current users location on the Google Map.
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        map.setMyLocationEnabled(true);
        map.getUiSettings().setZoomControlsEnabled(true);
        map.setMaxZoomPreference(18);
       map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        map.setBuildingsEnabled(true); ///Try to look into this.
        markMap();
    }




    @Override
    public void onStart() {
        super.onStart();
        api.connect();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();

        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Maps Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.example.nicholasbrooks.hungryniners/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();
        api.disconnect();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Maps Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.example.nicholasbrooks.hungryniners/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }

    @Override
    public void onConnected(@Nullable Bundle connectionHint) {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Location last_location = LocationServices.FusedLocationApi.getLastLocation(api);
            if(last_location != null){
                Longitude.setText(String.valueOf(last_location.getLatitude()));
                Lat.setText(String.valueOf(last_location.getLongitude()));
            }
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},1);
            return;
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        Toast.makeText(this, "Connection Suspended", Toast.LENGTH_SHORT);

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(this, "Connection Failed", Toast.LENGTH_SHORT);
    }

    @Override
    public void onMapClick(LatLng latLng) {
        Log.d("demo", "Got to the onclick");





        }

    @Override
    public boolean onMarkerClick(final Marker marker) {

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(marker.getTitle() + " Select an Option");
            builder.setItems(dialog, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int item) {
                    if(item == 0) {
                            currentPin = marker.getTitle();
                            Intent intent = new Intent(mActivity, EventFormActivity.class);
                            intent.putExtra("PIN", currentPin);
                            startActivity(intent);
                    }else if(item == 1){
                        currentPin = marker.getTitle();
                        Intent intent = new Intent(mActivity, DisplayActivity.class);
                        intent.putExtra("PIN", currentPin);
                        startActivity(intent);

                    }else if(item == 2){
                        currentPin = "UNCC Campus";
                        Intent intent = new Intent(mActivity, AllEventsDisplayActivity.class);
                        intent.putExtra("PIN", currentPin);
                        startActivity(intent);

                    }
                }
            });
            AlertDialog alert = builder.create();
            alert.show();

        return false;
    }
}


