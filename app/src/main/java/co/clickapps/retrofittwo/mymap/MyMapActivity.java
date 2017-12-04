package co.clickapps.retrofittwo.mymap;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.ClusterManager;

import co.clickapps.retrofittwo.R;

public class MyMapActivity extends AppCompatActivity implements OnMapReadyCallback {

    GoogleMap mMap;
    private final int DIALOG_ERROR_CODE = 1048;
    private ClusterManager<ClusterModel> mClusterManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_map);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        SupportMapFragment mapF = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.my_map_sub_fragment);


        if (isMapServiceOk()) {
            Toast.makeText(this, "Map okay!", Toast.LENGTH_LONG).show();
            mapF.getMapAsync(this);
        }
        else
            Toast.makeText(this, "Map not okay", Toast.LENGTH_LONG).show();


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

//                addMarket(30.703262,76.691970);
//                addMarket(30.703545,76.691855);//
//                addMarket(30.704165,6.691479);
//                mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(30.703545,76.691855)));



            }
        });
    }



    private boolean isMapServiceOk(){

        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(this);

        if (available == ConnectionResult.SUCCESS)
            return true;
        else if (GoogleApiAvailability.getInstance().isUserResolvableError(available)){
            GoogleApiAvailability.getInstance().getErrorDialog(this,available,DIALOG_ERROR_CODE).show();
            return false;
        }
        else
            return false;
    }


    private void addMarket(double lat,double lan){
        MarkerOptions marker = new MarkerOptions();
        marker.title("marker title");
        marker.position(new LatLng(lat,lan));

        mMap.addMarker(marker);
    }


    private void setUpClusterer() {
        // Position the map.
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(51.503186, -0.126446), 7));

        // Initialize the manager with the context and the map.
        // (Activity extends context, so we can pass 'this' in the constructor.)
        mClusterManager = new ClusterManager<ClusterModel>(this, mMap);

        // Point the map's listeners at the listeners implemented by the cluster
        // manager.
        mMap.setOnCameraIdleListener(mClusterManager);
        mMap.setOnMarkerClickListener(mClusterManager);

        // Add cluster items (markers) to the cluster manager.
        addItems();
    }


    private void addItems() {

        // Set some lat/lng coordinates to start with.
        double lat = 51.5145160;
        double lng = -0.1270060;

        // Add ten cluster items in close proximity, for purposes of this example.
        for (int i = 0; i < 10; i++) {
            double offset = i / 60d;
            lat = lat + offset;
            lng = lng + offset;
            ClusterModel offsetItem = new ClusterModel(lat, lng);
            mClusterManager.addItem(offsetItem);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        setUpClusterer();
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                Toast.makeText(MyMapActivity.this, marker.getPosition().latitude + "," +marker.getPosition().longitude , Toast.LENGTH_SHORT).show();
                return false;
            }
        });
    }
}
