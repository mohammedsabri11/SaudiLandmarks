package com.guideteee.myapplication;


import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.amlcurran.showcaseview.OnShowcaseEventListener;
import com.github.amlcurran.showcaseview.ShowcaseView;
import com.github.amlcurran.showcaseview.targets.Target;
import com.github.amlcurran.showcaseview.targets.ViewTarget;
import com.mapbox.android.core.permissions.PermissionsListener;
import com.mapbox.android.core.permissions.PermissionsManager;
import com.mapbox.api.directions.v5.models.DirectionsResponse;
import com.mapbox.api.directions.v5.models.DirectionsRoute;
import com.mapbox.geojson.Point;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.annotations.Icon;
import com.mapbox.mapboxsdk.annotations.IconFactory;
import com.mapbox.mapboxsdk.annotations.Marker;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.location.LocationComponent;
import com.mapbox.mapboxsdk.location.modes.CameraMode;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.services.android.navigation.ui.v5.NavigationLauncher;
import com.mapbox.services.android.navigation.ui.v5.NavigationLauncherOptions;
import com.mapbox.services.android.navigation.ui.v5.route.NavigationMapRoute;
import com.mapbox.services.android.navigation.v5.navigation.NavigationRoute;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback,LocationListener,MapboxMap.OnMarkerClickListener,MapboxMap.OnMapClickListener, PermissionsListener {

    //<com.mapbox.mapboxsdk.maps.MapView


    private MapView mapView;
    private long[] markerin;
    ArrayList<Long> markerinfo;
    ArrayList<String> Markertitless;
    ArrayList<LatLng> MarkerPoints;
    // variables for adding location layer
    private MapboxMap mapboxMap;
    private PermissionsManager permissionsManager;
    private Location originLocation;
    // variables for adding a marker
    private Marker destinationMarker;
    private LatLng originCoord;
    private Button button;
    private LatLng destinationCoord;
    // variables for calculating and drawing a route
    private Point originPosition;
    private Point destinationPosition;
    private DirectionsRoute currentRoute;
    private static final String TAG = "DirectionsActivity";
    private NavigationMapRoute navigationMapRoute;
    Menu menu;
    //FloatingActionButton  mFloatingActionButton;
    MenuItem menloginitem ;
    MenuItem mensigninitem ;
    MenuItem menslogoutitem ;
    boolean iahelper=false;
    FragmentManager fragmentManager;

  Context context;
    View view;
    boolean isshow;
    boolean providerstate;
    DbHandler db;

    //cd
    private TextView mTextMessage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            LayoutInflater inflater;
           // inflater=new  LayoutInflater();
          // View view=View.inflate()
                  // inflater(R.layout.contentmain,null);
            switch (item.getItemId()) {
                case R.id.location:
                    mapboxMap.clear();

                    enableLocationComponent();
                 fragmentManager.beginTransaction().replace(R.id.frag, new SingnIn()).commit();
                    return true;
                case R.id.places:

                   // fragmentManager.beginTransaction().replace(R.id.frag, new LogIn()).commit();
                case R.id.direction:
                    boolean simulateRoute = true;
                    NavigationLauncherOptions options = NavigationLauncherOptions.builder()
                            .directionsRoute(currentRoute)
                            .shouldSimulateRoute(simulateRoute)
                            .build();
                    // Call this method with Context from within an Activity
                    NavigationLauncher.startNavigation(MainActivity.this, options);
                    return true;
            }
            return false;
        }
    };


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id){
            case   R.id.menulogin:
               // fragmentManager.beginTransaction().replace(R.id.frag, new LogIn()).commit();
            case   R.id.menulogout:
                //logout_fun();
            case   R.id.menusignin:
                // fragmentManager.beginTransaction().replace(R.id.content_frame, new LogIn()).commit();

        }



        return super.onOptionsItemSelected(item);
    }


    public void showmarker(ArrayList<LatLng> markerPoints, ArrayList<String> helper) {
        boolean uflage=false;
        if (mapboxMap!=null) {
            // Toast.makeText(MainActivity.this, "showmarker", Toast.LENGTH_LONG).show();
            Icon icon = IconFactory.getInstance(MainActivity.this).fromResource(R.drawable.map_marker_dark);
            for (int i = 0; i < markerPoints.size(); i++) {
                if (helper.get(i).equals("N")) {
                    uflage = true;
                    mapboxMap.addMarker(new MarkerOptions().position(markerPoints.get(i)));
                }else {
                    mapboxMap.addMarker(new MarkerOptions().position(markerPoints.get(i)).icon(icon));
                }
                //  Toast.makeText(MainActivity.this, "showmarker   : " + i, Toast.LENGTH_LONG).show();
            }
            if (uflage) {
                //methodWhereFabIsShown();
               // mFloatingActionButton.show();
            }
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        getMenuInflater().inflate(R.menu.main, menu);
        this.menu = menu;

        menloginitem   = menu.findItem(R.id.menulogin);
        menslogoutitem= menu.findItem(R.id.menulogout);
        mensigninitem   = menu.findItem(R.id.menusignin);
        return true;
    }
    public void setOptionsItemVisible(MenuItem item) {
        item.setVisible(true);
    }
    public void setOptionsItemNonVisible(MenuItem item) {
        item.setVisible(false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Mapbox.getInstance(this, getString(R.string.access_token));
        setContentView(R.layout.activity_main);
        mapView = findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);
DbObject dbObject=new DbObject(this);
db=dbObject.getDb();
context=MainActivity.this;
        isshow=false;
        providerstate=true;

        //showplacesnoVisted();

/* <com.mapbox.mapboxsdk.maps.MapView
        android:id="@+id/mapView"
        android:layout_width="match_parent"
        android:layout_height="555dp"

        app:layout_constraintBottom_toTopOf="@id/navigation"
        app:layout_constraintEnd_toEndOf="parent">

    </com.mapbox.mapboxsdk.maps.MapView>*/
        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
       // singledilogemes( open);
        //MessageDiloage(usebutton);



        Target viewTarget1= new ViewTarget(R.id.location, this);  // Add the control you need to focus by the ShowcaseView
        new ShowcaseView.Builder(this)
                .setTarget(viewTarget1)
                .setContentTitle(R.string.locationmesstitle)        // Add your string file (title_single_shot) in values/strings.xml
                .setContentText(R.string.locationmes) // Add your string file (R_strings_desc_single_shot) in values/strings.xml
                .singleShot(42)
                .build();




    }



    @Override
    public void onMapReady(MapboxMap mapboxMap) {
        if (isGPSEnabled(MainActivity.this)) {
            this.mapboxMap = mapboxMap;


            // originCoord =   new LatLng(21.51565, 39.1451);//LatLng(originLocation.getLatitude(), originLocation.getLongitude());


            mapboxMap.setOnMarkerClickListener(this);

            enableLocationComponent();
            originCoord = new LatLng(originLocation.getLatitude(), originLocation.getLongitude());

            mapboxMap.addOnMapClickListener(this);


        }else{
            Toast.makeText(MainActivity.this,"location provider is off please switch on",Toast.LENGTH_LONG);
        }

    }



   /* public void getInfo(Marker marker) {

        // The info window layout is created dynamically, parent is the info window
        // container
        informclass nn = new informclass();


            switch (marker.getTitle()) {
                case "King Fahad Fountain":
                    nn.setTitle("Fakieh Aquarium");
                    nn.setDetalis("King Fahad Fountain Jeddah, Makkah Province: The King Fahad Fountain is also known as the Jeddah Fountain and is the tallest fountain in the whole world. This fountain was created in 1980 and raises the water to about 260 meters or 853 feet above the sea level.\n" +
                            "\n" +
                            "This magnificent view can be easily seen from anywhere in Jeddah city. The fountain uses the salt water from the red sea and does not waste fresh water. Recommended: World’s Tallest Fountains – Jeddah Fountain at No. 1 GPS Coordinates: 21.515639,39.145068\n" +
                            "\n"
                    );
                    nn.setImgid(R.mipmap.ic_place1);
                    break;
                case "Fakieh Aquarium":
                    nn.setTitle("Fakieh Aquarium");
                    nn.setDetalis("King Fahad Fountain Jeddah, Makkah Province:");
                    nn.setImgid(R.mipmap.ic_place2);
                    break;

                case "Masjid Al Rahmah-Floating Mosque":
                    nn.setTitle("Masjid Al Rahmah-Floating Mosque");
                    nn.setDetalis("King Fahad Fountain Jeddah, Makkah Province:");
                    nn.setImgid(R.mipmap.ic_place3);
                    break;

                case "Museum of Arts Home":
                    nn.setTitle("Masjid Al Rahmah-Floating Mosque");
                    nn.setDetalis("Museum of Arts Home");
                    nn.setImgid(R.mipmap.ic_place3);
                    break;
                case "Al Arab Mall":
                    nn.setTitle("Al Arab Mall");
                    nn.setDetalis("Al Arab Mall");
                    nn.setImgid(R.mipmap.ic_place3);
                    break;

                case "GINGER LEAF":
                    nn.setTitle("GINGER LEAF");
                    nn.setDetalis("GINGER LEAF");
                    nn.setImgid(R.mipmap.ic_place3);
                    break;

                case "King Abdullah Sports City":
                    nn.setTitle("King Abdullah Sports City");
                    nn.setDetalis("King Abdullah Sports City");
                    nn.setImgid(R.mipmap.ic_place66);
                    break;


                default:


                    break;
            }

     FragmentManager manager=getFragmentManager();
            windowinfoShow ff=new windowinfoShow();
            ff.show(manager,null);



    }*/


        @Override
    public void onMapClick(@NonNull LatLng point) {

        // LatLng originCoo = new LatLng(21.57245, 39.10932);////LatLng(originLocation.getLatitude(), originLocation.getLongitude());

        // / check(originCoo);
        if (destinationMarker != null) {
            mapboxMap.removeMarker(destinationMarker);
        }
        destinationCoord = point;
        destinationMarker = mapboxMap.addMarker(new MarkerOptions()
                .position(destinationCoord)
        );
        destinationPosition = Point.fromLngLat(destinationCoord.getLongitude(), destinationCoord.getLatitude());
        originPosition = Point.fromLngLat(originCoord.getLongitude(), originCoord.getLatitude());

        getRoute(originPosition, destinationPosition);


    }
    //button.setEnabled(true);
    //setEnabled(true);

    // button.setBackgroundResource(R.color.cardview_dark_background);




    private void getRoute(Point origin, Point destination) {
        try {
            NavigationRoute.builder(this)
                    .accessToken(Mapbox.getAccessToken())
                    .origin(origin)
                    .destination(destination)
                    .build()
                    .getRoute(new Callback<DirectionsResponse>() {
                        @Override
                        public void onResponse(Call<DirectionsResponse> call, Response<DirectionsResponse> response) {
                            // You can get the generic HTTP info about the response
                            Log.d(TAG, "Response code: " + response.code());
                            if (response.body() == null) {
                                Log.e(TAG, "No routes found, make sure you set the right user and access token.");
                                return;
                            } else if (response.body().routes().size() < 1) {
                                Log.e(TAG, "No routes found");
                                return;
                            }

                            currentRoute = response.body().routes().get(0);

                            // Draw the route on the map
                            if (navigationMapRoute != null) {
                                navigationMapRoute.removeRoute();
                            } else {
                                navigationMapRoute = new NavigationMapRoute(null, mapView, mapboxMap, R.style.NavigationMapRoute);
                            }
                            navigationMapRoute.addRoute(currentRoute);

                        }

                        @Override
                        public void onFailure(Call<DirectionsResponse> call, Throwable throwable) {
                            Log.e(TAG, "Error: " + throwable.getMessage());
                        }
                    });
        } catch (Exception e) {
            Log.d(TAG, "getRoute: route faild please check internet");
        }
    }
/*
*/

    @SuppressWarnings({"MissingPermission"})
    private void enableLocationComponent() {
        //Check if permissions are enabled and if not request
        try {
            if (PermissionsManager.areLocationPermissionsGranted(this)) {
                // Activate the MapboxMap LocationComponent to show user location
                // Adding in LocationComponentOptions is also an optional parameter
                LocationComponent locationComponent = mapboxMap.getLocationComponent();
                locationComponent.activateLocationComponent(this);
                locationComponent.setLocationComponentEnabled(true);
                // Set the component's camera mode
                locationComponent.setCameraMode(CameraMode.TRACKING);
                originLocation = locationComponent.getLastKnownLocation();


                originCoord = new LatLng(originLocation.getLatitude(), originLocation.getLongitude());

            } else {
                permissionsManager = new PermissionsManager(this);
                permissionsManager.requestLocationPermissions(this);
            }
        } catch (Exception e) {
            Log.d(TAG, "enableLocationComponent: faild please check d");
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        permissionsManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onExplanationNeeded(List<String> permissionsToExplain) {

        Toast.makeText(getApplicationContext(), R.string.user_location_permission_explanation, Toast.LENGTH_LONG).show();
    }


    @Override
    public void onPermissionResult(boolean granted) {
        if (granted) {
            //enableLocationComponent();
        } else {
            Toast.makeText(getApplicationContext(), R.string.user_location_permission_not_granted, Toast.LENGTH_LONG).show();
            finish();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        mapView.onStart();

    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();

    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }



    //####################################################################################

    //^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

//@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@22222

    @Override
    public boolean onMarkerClick(@NonNull Marker marker) {
        //showinfowind(marker);

        marker.hideInfoWindow();
        return false;
    }


    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {
        if (providerstate==true)
        {
            providerstate=false;
            Toast.makeText(MainActivity.this,"program cant reach to yor location please check to enable your location track ",Toast.LENGTH_LONG);
        }
    }

    @Override
    public void onProviderEnabled(String s) {
        providerstate=true;
    }

    @Override
    public void onProviderDisabled(String s) {
        providerstate=false;
    }

    public boolean isGPSEnabled(Context mContext)
    {
        LocationManager lm = (LocationManager)
                mContext.getSystemService(Context.LOCATION_SERVICE);
        return lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }
    //android:name="com.google.android.gms.maps.SupportMapFragment"

}
