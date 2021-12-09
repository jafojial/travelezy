package cm.intelso.dev.travelezi;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.DirectionsApi;
import com.google.maps.DirectionsApiRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.model.DirectionsLeg;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.DirectionsRoute;
import com.google.maps.model.DirectionsStep;
import com.google.maps.model.EncodedPolyline;

import org.json.JSONArray;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import cm.intelso.dev.travelezi.apiclient.RetrofitClient;
import cm.intelso.dev.travelezi.data.model.SharedPrefs;
import cm.intelso.dev.travelezi.dto.Bus;
import cm.intelso.dev.travelezi.dto.Line;
import cm.intelso.dev.travelezi.dto.LineItem;
import cm.intelso.dev.travelezi.dto.POI;
import cm.intelso.dev.travelezi.dto.StopItem;
import cm.intelso.dev.travelezi.dto.WaitingDetail;
import cm.intelso.dev.travelezi.json.JsonController;
import cm.intelso.dev.travelezi.utils.DataUtils;
import retrofit2.Call;
import retrofit2.Callback;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link NearBusFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link NearBusFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NearBusFragment extends Fragment implements OnMapReadyCallback {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private SharedPrefs settings;
    private String token;

    private GoogleMap mMap;
    private static final String MAPVIEW_BUNDLE_KEY = "MapViewBundleKey";
    private String busTake = "";

    Button btnSearch, btnTakeBus;
    TextView wbInfo, abInfo;
    Spinner departure, arrival, lineCodeName;
    MapView mapLine;
    LinearLayout pInfoLayout, infoLayout, actionLayout;
    Fragment newContent;

    ArrayAdapter<StopItem> depStopAdapter, arrStopAdater;
    ArrayAdapter<LineItem> lineAdapter;

    StopItem selDeparture = new StopItem();
    StopItem selArrival = new StopItem();
    LineItem selLine = new LineItem();

    ArrayList<LineItem> linesDetails;
    ArrayList<StopItem> departureStopsDetails, arrivalStopsDetails;
//    List<Bus> busList;

    // Progress dialog
    private ProgressDialog pDialog;

    private static String TAG = NearBusFragment.class.getSimpleName();
    String url = "http://192.168.25.179/touristique/web/index.php/apiv1/ville";
    private String jsonResponse;

    String[] villesDefaut = new String[]{"YAOUNDE", "DOUALA"};
    String[] departs = new String[]{"05:00", "06:00", "07:00", "08:30", "09:30", "11:00", "12:30", "14:00", "15:00", "16:00", "17:00", "18:45"};

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BusLineFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NearBusFragment newInstance(String param1, String param2) {
        NearBusFragment fragment = new NearBusFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public NearBusFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // *** IMPORTANT ***
        // MapView requires that the Bundle you pass contain _ONLY_ MapView SDK
        // objects or sub-Bundles.
        Bundle mapViewBundle = null;

        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_bus_line, container, false);
        if (savedInstanceState != null) {
            //Restore the fragment's state here
            mapViewBundle = savedInstanceState.getBundle(MAPVIEW_BUNDLE_KEY);
        }
        final View view = inflater.inflate(R.layout.fragment_near_bus, container, false);
        //getDialog().setTitle(getResources().getString(R.string.add_account_title));

        pDialog = DataUtils.createProgressDialog(getContext(), getContext().getString(R.string.wait), Boolean.FALSE);
        settings = new SharedPrefs();
        token = settings.getStringValue(getContext(), settings.PREFS_USER_TOKEN_KEY);

        wbInfo = (TextView) view.findViewById(R.id.tv_info_wb);
        abInfo = (TextView)view.findViewById(R.id.tv_info_ab);
        departure = (Spinner)view.findViewById(R.id.tv_line_departure);
        arrival = (Spinner)view.findViewById(R.id.tv_line_arrival);

        btnSearch = (Button) view.findViewById(R.id.btn_choose_line);
        btnTakeBus = (Button) view.findViewById(R.id.btn_take_bus);
        mapLine = (MapView)view.findViewById(R.id.map_line);
        mapLine.onCreate(mapViewBundle);
        mapLine.getMapAsync(this);

        mapLine.setVisibility(View.GONE);

        pInfoLayout = (LinearLayout) view.findViewById(R.id.p_info_layout);
        infoLayout = (LinearLayout) view.findViewById(R.id.info_layout);
        actionLayout = (LinearLayout) view.findViewById(R.id.action_layout);

        pInfoLayout.setVisibility(View.GONE);

        //makeJsonArrayRequest(url);
        departureStopsDetails = getListDepartureStops();
        populateDepStopDropDown(departureStopsDetails);

        // When user select a List-Item.
        this.departure.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                onDepartureStopItemSelectedHandler(parent, view, position, id);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        this.arrival.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                onArrivalStopItemSelectedHandler(parent, view, position, id);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selDeparture.getCode().isEmpty()){
                    Toast.makeText(getContext(), getResources().getString(R.string.select_dep_first), Toast.LENGTH_LONG).show();
                    return;
                } else if(selArrival.getCode().isEmpty()){
                    Toast.makeText(getContext(), getResources().getString(R.string.select_arr_first), Toast.LENGTH_LONG).show();
                    return;
                }

//                List<Bus> busList = new ArrayList<>();
                List<Bus> busList = getLineNextBus(selDeparture.getCode(), selArrival.getCode());


                Bundle bundle=new Bundle();
                bundle.putString("departureID", selDeparture.getCode());
                bundle.putString("arrivalID", selArrival.getCode());
                bundle.putString("departure", departure.getSelectedItem().toString());
                bundle.putString("arrival", arrival.getSelectedItem().toString());
                bundle.putString("lineID", selLine.getCode());

                /*newContent = new BusStopFragment();
                newContent.setArguments(bundle);
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.app_content_frame, newContent).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .addToBackStack(null).commit();*/

                /*mapLine.setVisibility(View.VISIBLE);
                pInfoLayout.setVisibility(View.VISIBLE);
                infoLayout.setVisibility(View.GONE);
                actionLayout.setVisibility(View.VISIBLE);

                if(mMap != null){
                    mMap.clear();

                    displayMap(mMap, busList);
                }*/

            }
        });

        btnTakeBus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final ScheduledExecutorService inBusTaskExecutor = Executors.newScheduledThreadPool(2);
                final AtomicInteger count = new AtomicInteger(10);
                final AtomicInteger wc = new AtomicInteger(60);

                // This schedule a runnable task every 30 seconds
                inBusTaskExecutor.scheduleAtFixedRate(new Runnable() {
                    public void run() {
                        Log.i(TAG, "In inBusTaskExecutor");
                        List<String> list = Arrays.asList(new String[] {"LT7837KI", "LT207BV", "LT951ZS", "LT451HG", "LT1320PM"});
                        List<Integer> mins = Arrays.asList(new Integer[] {new Random().nextInt(14), new Random().nextInt(24),
                                new Random().nextInt(34), new Random().nextInt(54), new Random().nextInt(40)});
                        String msg = "";
                        int it = new Random().nextInt(4) ;
                        int ct = new Random().nextInt(wc.get()) ;
                        wc.set(ct);
                        count.getAndDecrement();

                        if(it == 0 || count.get() == 0 || ct == 0){
                            msg = "Vous êtes arrivé à la station " + selArrival + " à bord du bus " + busTake;
                            Log.i(TAG, "In " + msg);
                            abInfo.setText(msg);
                            abInfo.setTextColor(getResources().getColor(R.color.primary_text));
                            btnTakeBus.setClickable(false);
                            btnTakeBus.setText("");
                            btnTakeBus.setBackgroundColor(getResources().getColor(R.color.gray_color));
//                            btnTakeBus.setBackground(getResources().getDrawable(R.drawable.component_gray_bg));
//                            infoLayout.setVisibility(View.GONE);
//                            actionLayout.setVisibility(View.VISIBLE);
                            Log.i(TAG, msg);
                            MediaPlayer mp = MediaPlayer.create(getContext(), RingtoneManager.getActualDefaultRingtoneUri(getContext(), RingtoneManager.TYPE_NOTIFICATION));
                            mp.start();
                            inBusTaskExecutor.shutdown();
                            // Toast.makeText(getContext(), msg, Toast.LENGTH_LONG).show();
                        } else{
                            msg = "En route dans le bus " + busTake + ". Arrivée à la station " + selArrival + " dans " + ct + " min";
                            abInfo.setText(msg);
                            abInfo.setTextColor(getResources().getColor(R.color.primary_text));
                            btnTakeBus.setClickable(false);
                            btnTakeBus.setText("");
                            btnTakeBus.setBackgroundColor(getResources().getColor(R.color.gray_color));
//                            btnTakeBus.setBackground(getResources().getDrawable(R.drawable.component_gray_bg));
//                            infoLayout.setVisibility(View.VISIBLE);
//                            actionLayout.setVisibility(View.GONE);
                            // Toast.makeText(getContext(), msg, Toast.LENGTH_LONG).show();
                            Log.i(TAG, msg);
                            MediaPlayer mp = MediaPlayer.create(getContext(), RingtoneManager.getActualDefaultRingtoneUri(getContext(), RingtoneManager.TYPE_NOTIFICATION));
                            mp.start();
                        }
                    }
                }, 0, 15, TimeUnit.SECONDS);
            }
        });

        //makeJsonArrayRequest(url);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        /*SupportMapFragment mapFragment = (SupportMapFragment) getFragmentManager()
                .findFragmentById(R.id.map_line);
        mapFragment.getMapAsync(this);*/


        return view;
    }

    private void onDepartureStopItemSelectedHandler(AdapterView<?> adapterView, View view, int position, long id) {
        Adapter adapter = adapterView.getAdapter();
        selDeparture = (StopItem) adapter.getItem(position);

        if(!selDeparture.getCode().isEmpty()) {
            Log.i(TAG, "DEP STOP : " + selDeparture.getCode());
            settings.save(getContext(), settings.PREFS_USER_CURRENT_STOP_KEY, selDeparture.getCode());
            arrivalStopsDetails = getListArrivalStops();
        } else{
            arrivalStopsDetails = initListArrivalStops();
        }
        populateArrStopDropDown(arrivalStopsDetails);

//        Toast.makeText(getContext(), "Selected departure StopItem: " + selDeparture.getName() ,Toast.LENGTH_SHORT).show();
    }


    private void onArrivalStopItemSelectedHandler(AdapterView<?> adapterView, View view, int position, long id) {
        Adapter adapter = adapterView.getAdapter();
        selArrival = (StopItem) adapter.getItem(position);

        if(!selArrival.getCode().isEmpty()) {
            Log.i(TAG, "ARR STOP : " + selArrival.getCode());
            settings.save(getContext(), settings.PREFS_USER_NEXT_STOP_KEY, selArrival.getCode());
        }

//        Toast.makeText(getContext(), "Selected arrival StopItem: " + selArrival.getName() ,Toast.LENGTH_SHORT).show();
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        Log.i(TAG, "On method onMapReady");
        mMap = googleMap;
        displayMap(mMap, new ArrayList<>());
    }


    private void displayMap(GoogleMap mMap, List<Bus> busList){

        if(selDeparture.getCode() != null && selArrival.getCode() != null){
            if(!selDeparture.getCode().isEmpty() && !selArrival.getCode().isEmpty()) {
                mMap.addMarker(new MarkerOptions().position(new LatLng(selDeparture.getRtLat(), selDeparture.getRtLng())).title(selDeparture.getName()));
//                mMap.addMarker(new MarkerOptions().position(new LatLng(selArrival.getRtLat(), selArrival.getRtLng())).title(selArrival.getName())
//                        .icon(BitmapFromVector(getContext(), R.drawable.ic_location_on_green)));
                mMap.addMarker(new MarkerOptions().position(new LatLng(selArrival.getRtLat(), selArrival.getRtLng())).title(selArrival.getName()));

                if(busList != null && !busList.isEmpty()) {
                    for (Bus bus : busList) {
                        Log.i(TAG, "BUS : " + bus.getImmatriculation() + " with coordinates " + bus.getLatitude() + ", " + bus.getLongitude());
                        // Calculer la distance et le temps d'attente approximatif

                        String busCoord = getLatLngString(new LatLng(Double.valueOf(bus.getLatitude()), Double.valueOf(bus.getLongitude())));
                        Log.i(TAG, "BUS COORD = " + busCoord);
                        mMap.addMarker(new MarkerOptions().position(new LatLng(Double.valueOf(bus.getLatitude()), Double.valueOf(bus.getLongitude())))
                                .title("Bus " + bus.getImmatriculation() + " à " + bus.getTimeText() + " (" + bus.getDistanceText() + ") de " + selArrival.getName())
                                .icon(BitmapFromVector(getContext(), R.drawable.ic_bus_24)));
                    }
                }
        //        LatLng bus4 = new LatLng(4.0430068,9.6906967);
        //        mMap.addMarker(new MarkerOptions().position(stopN2).title("Pont Joss").icon(BitmapFromVector(getContext(), R.drawable.ic_stop)));

        //        LatLng mapCenter = new LatLng(4.02535,9.692945500000002);
                LatLng mapCenter = new LatLng(selDeparture.getRtLat(), selDeparture.getRtLng());

                // Marche des fleurs
                String startPoint = "ChIJR6V9NEcTYRAR2nFJ3LdoKgQ";
                // CamWater
                String stop1 = "ChIJEabOr8ISYRARc9fLYw6i0V8";
                // Pont Joss
                String stop2 = "EhpSdWUgSm9zcywgRG91YWxhLCBDYW1lcm9vbiIuKiwKFAoSCePtD47wEmEQERltc7wsBTW3EhQKEglt_uHiixJhEBGLxIFHRKHakg";
                // Carrefour Leclerc
                String stop3 = "";
                // Carrefour Soudanais
        //        String endPoint = "ChIJ8QPF7aETYRARNUHFuNM0Xxk2MXV+6P2";
                String endPoint = "ChIJ8QPF7aETYRARNUHFuNM0Xxk";

                //Define list to get all latlng for the route
                List<LatLng> path = new ArrayList();

                String dep = getLatLngString(new LatLng(selDeparture.getRtLat(),selDeparture.getRtLng()));
                String arr = getLatLngString(new LatLng(selArrival.getRtLat(),selArrival.getRtLng()));

                String gmApiKey = getContext().getResources().getText(R.string.google_maps_key ).toString();
                Log.i(TAG, "API KEY = " + gmApiKey);

                //Execute Directions API request
                GeoApiContext context = new GeoApiContext.Builder()
                        .apiKey(gmApiKey)
                        .build();
                DirectionsApiRequest req = DirectionsApi.getDirections(context, dep, arr);
        //        DirectionsApiRequest req = DirectionsApi.getDirections(context, "place_id:"+startPoint, "place_id:"+endPoint);
                try {
                    Log.i(TAG, "DEPARTURE = " + dep);
                    Log.i(TAG, "ARRIVAL = " + arr);
                    DirectionsResult res = req.await();
                    Log.i(TAG, "DEPARTURE = " + dep);
                    Log.i(TAG, "ARRIVAL = " + arr);

                    //Loop through legs and steps to get encoded polylines of each step
                    if (res.routes != null && res.routes.length > 0) {
                        Log.i(TAG, "INFO : " + res.routes.length + " route found");
                        DirectionsRoute route = res.routes[0];

                        if (route.legs !=null) {
                            for(int i=0; i<route.legs.length; i++) {
                                DirectionsLeg leg = route.legs[i];
                                if (leg.steps != null) {
                                    for (int j=0; j<leg.steps.length;j++){
                                        DirectionsStep step = leg.steps[j];
                                        if (step.steps != null && step.steps.length >0) {
                                            for (int k=0; k<step.steps.length;k++){
                                                DirectionsStep step1 = step.steps[k];
                                                EncodedPolyline points1 = step1.polyline;
                                                if (points1 != null) {
                                                    //Decode polyline and add points to list of route coordinates
                                                    List<com.google.maps.model.LatLng> coords1 = points1.decodePath();
                                                    for (com.google.maps.model.LatLng coord1 : coords1) {
                                                        path.add(new LatLng(coord1.lat, coord1.lng));
                                                    }
                                                }
                                            }
                                        } else {
                                            EncodedPolyline points = step.polyline;
                                            if (points != null) {
                                                //Decode polyline and add points to list of route coordinates
                                                List<com.google.maps.model.LatLng> coords = points.decodePath();
                                                for (com.google.maps.model.LatLng coord : coords) {
                                                    path.add(new LatLng(coord.lat, coord.lng));
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                            Log.i(TAG, "INFO : " + path.size() + " points for path to draw");
                        }
                        else{
                            Log.e(TAG, "Exception : Route is null");
                        }
                    }
                    else{
                        Log.e(TAG, "Exception : No route found");
                    }
                } catch(Exception ex) {
                    Log.e(TAG, "Exception : " + ex.getLocalizedMessage());
                    Log.e(TAG, ex.getLocalizedMessage());
                }

                //Draw the polyline
                if (path.size() > 0) {
                    PolylineOptions opts = new PolylineOptions().addAll(path).color(getContext().getResources().getColor(R.color.primary)).width(12);
                    mMap.addPolyline(opts);
                }

                mMap.getUiSettings().setZoomControlsEnabled(true);

                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mapCenter, 14));
            }
        }

    }


    private String getLatLngString(LatLng coord){
        return '"' + String.valueOf(coord.latitude) + ',' + String.valueOf(coord.longitude) + '"';
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        Bundle mapViewBundle = outState.getBundle(MAPVIEW_BUNDLE_KEY);
        if (mapViewBundle == null) {
            mapViewBundle = new Bundle();
            outState.putBundle(MAPVIEW_BUNDLE_KEY, mapViewBundle);
        }

        mapLine.onSaveInstanceState(mapViewBundle);
    }

    @Override
    public void onResume() {
        super.onResume();
        mapLine.onResume();
    }

    @Override
    public void onStart() {
        super.onStart();
        mapLine.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        mapLine.onStop();
    }

    @Override
    public void onPause() {
        mapLine.onPause();
        super.onPause();
    }

    @Override
    public void onDestroy() {
        mapLine.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapLine.onLowMemory();
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }


    private void waitingNextBusNotification(){

        final ScheduledExecutorService waitingBusTaskExecutor = Executors.newScheduledThreadPool(1);
        final AtomicInteger count = new AtomicInteger(10);
        final AtomicInteger wc = new AtomicInteger(60);

        // This schedule a runnable task every 30 seconds
        waitingBusTaskExecutor.scheduleAtFixedRate(new Runnable() {
            public void run() {
                Log.i(TAG, "In waitingBusTaskExecutor");
                List<Bus> busList = getUpdatedBusDetails(selDeparture.getCode(), selArrival.getCode());
                String msg = "";

                for (Bus bus : busList) {
                    if(bus.getDistance() < 500 || bus.getTime() < 120){
                        msg = "Le bus " + bus.getImmatriculation() + " arrivé à la station " + selArrival;
                        Log.i(TAG, "In " + msg);
//                            infoLayout.setVisibility(View.GONE);
//                            actionLayout.setVisibility(View.VISIBLE);
                        abInfo.setText(msg);
                        abInfo.setTextColor(getResources().getColor(R.color.primary_text));
                        btnTakeBus.setClickable(true);
                        btnTakeBus.setText(getResources().getString(R.string.take_bus));
                        btnTakeBus.setBackground(getResources().getDrawable(R.drawable.component_gray_bg));
//                            pInfoLayout.refreshDrawableState();
//                            actionLayout.refreshDrawableState();
//                            btnTakeBus.invalidate();
//                            pInfoLayout.invalidate();
//                            actionLayout.invalidate();
                        busTake = bus.getImmatriculation();
                        Log.i(TAG, msg);
                        MediaPlayer mp = MediaPlayer.create(getContext(), RingtoneManager.getActualDefaultRingtoneUri(getContext(), RingtoneManager.TYPE_NOTIFICATION));
                        mp.start();
                        waitingBusTaskExecutor.shutdown();
                        // Toast.makeText(getContext(), msg, Toast.LENGTH_LONG).show();
                    } else{
                        msg = "En attente de bus " + bus.getImmatriculation() + " à la station " + selArrival + " dans " + bus.getTimeText();
//                            wbInfo.setText(msg);
//                            infoLayout.setVisibility(View.VISIBLE);
//                            actionLayout.setVisibility(View.GONE);
                        // Toast.makeText(getContext(), msg, Toast.LENGTH_LONG).show();
                        abInfo.setText(msg);
                        abInfo.setTextColor(getResources().getColor(R.color.red_color));
                        btnTakeBus.setClickable(false);
                        btnTakeBus.setText("");
                        btnTakeBus.setBackgroundColor(getResources().getColor(R.color.gray_color));
//                            btnTakeBus.setBackground(getResources().getDrawable(R.drawable.component_gray_bg));
                        Log.i(TAG, msg);
                        MediaPlayer mp = MediaPlayer.create(getContext(), RingtoneManager.getActualDefaultRingtoneUri(getContext(), RingtoneManager.TYPE_NOTIFICATION));
                        mp.start();
                    }
                    // Update map

                    try {
                        this.wait(10000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, 0, 60, TimeUnit.SECONDS);
    }


    private List<Bus> getUpdatedBusDetails(String startPoint, String endPoint){
        DataUtils.showProgressDialog(pDialog);

        List<Bus> list = new ArrayList<>();

        Call<List<Line>> call = RetrofitClient.getInstance(token).getMyApi().findLines(null, null, null, null, null, startPoint, endPoint);
        try {
            List<Line> lines = call.execute().body();
            if (lines != null && !lines.isEmpty()) {
                Log.i(TAG, "LINE SIZE : " + lines.size());
                for (Line line : lines) {
                    // if(line.getType().equals("STOP")
                    Call<List<Bus>> call2 = RetrofitClient.getInstance(token).getMyApi().getLineBus(line.getUuid());
                    List<Bus> busList = call2.execute().body();
                    if (busList != null && !busList.isEmpty()) {
                        Log.i(TAG, "BUS SIZE : " + busList.size());
                        for (Bus bus : busList) {
                            Call<WaitingDetail> call3 = RetrofitClient.getInstance(token).getMyApi().getWaitingTime(bus.getLatitude(), bus.getLongitude(),
                                    String.valueOf(selArrival.getRtLat()), String.valueOf(selArrival.getRtLng()));
                            WaitingDetail wdet = call3.execute().body();
                            if (wdet != null) {
                                Log.i(TAG, "WAITING DETAIL : " + wdet.toString());
                                WaitingDetail wd = getBusWaitingTime(bus.getLatitude(), bus.getLongitude(),
                                        String.valueOf(selArrival.getRtLat()), String.valueOf(selArrival.getRtLng()));
                                // if(line.getType().equals("STOP")
                                bus.setDistance(wd.getDistance());
                                bus.setDistanceText(wd.getDistance_text());
                                bus.setTime(wd.getTime());
                                bus.setTimeText(wd.getTime_text());
                                list.add(bus);
                            } else {
                                // sign-in failed
                                Toast.makeText(getContext(), "No line found!!", Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                }
            }
            else {
                // sign-in failed
                Toast.makeText(getContext(), "No line found!!", Toast.LENGTH_LONG).show();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return list;
    }


    private List<Bus> getLineNextBus(String startPoint, String endPoint){
        DataUtils.showProgressDialog(pDialog);

        List<Bus> list = new ArrayList<>();

        /*LineItem li = getLine(startPoint, endPoint);
        if(li != null){
            Log.i(TAG, "LINE TO DRAW : " + li.toString());
            list = getLineBusList(li.getCode());
        } else{
            Log.i(TAG, "NO LINE FOUND!!");
        }*/

        Call<List<Line>> call = RetrofitClient.getInstance(token).getMyApi().findLines(null, null, null, null, null, startPoint, endPoint);
        call.enqueue(new Callback<List<Line>>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(Call<List<Line>> call, retrofit2.Response<List<Line>> response) {

                Log.i(TAG, "URL : " + call.request().url().toString());
                List<Line> lines = response.body();
                if (lines != null && !lines.isEmpty()) {
                    Log.i(TAG, "LINE SIZE : " + lines.size());
                    for (Line line : lines) {
                        Call<List<Bus>> call2 = RetrofitClient.getInstance(token).getMyApi().getLineBus(line.getUuid());
                        call2.enqueue(new Callback<List<Bus>>() {
                            @RequiresApi(api = Build.VERSION_CODES.O)
                            @Override
                            public void onResponse(Call<List<Bus>> call2, retrofit2.Response<List<Bus>> response) {

                                Log.i(TAG, "URL : " + call2.request().url().toString());
                                List<Bus> busList = response.body();
                                if (busList != null && !busList.isEmpty()) {
                                    Log.i(TAG, "BUS SIZE : " + busList.size());
                                    for (Bus bus : busList) {
                                        /*WaitingDetail wd = getBusWaitingTime(bus.getLatitude(), bus.getLongitude(),
                                                String.valueOf(selArrival.getRtLat()), String.valueOf(selArrival.getRtLng()));
                                        // if(line.getType().equals("STOP")
                                        bus.setDistance(wd.getDistance());
                                        bus.setDistanceText(wd.getDistance_text());
                                        bus.setTime(wd.getTime());
                                        bus.setTimeText(wd.getTime_text());
                                        list.add(bus);*/
                                        Call<WaitingDetail> call3 = RetrofitClient.getInstance(token).getMyApi().getWaitingTime(bus.getLatitude(), bus.getLongitude(),
                                                String.valueOf(selArrival.getRtLat()), String.valueOf(selArrival.getRtLng()));
                                        call3.enqueue(new Callback<WaitingDetail>() {
                                            @RequiresApi(api = Build.VERSION_CODES.O)
                                            @Override
                                            public void onResponse(Call<WaitingDetail> call3, retrofit2.Response<WaitingDetail> response) {

                                                Log.i(TAG, "URL : " + call3.request().url().toString());
                                                WaitingDetail wd = response.body();
                                                if (wd != null) {
                                                    Log.i(TAG, "WAITING DETAIL : " + wd.toString());
//                                                    WaitingDetail wd = getBusWaitingTime(bus.getLatitude(), bus.getLongitude(),
//                                                            String.valueOf(selArrival.getRtLat()), String.valueOf(selArrival.getRtLng()));
                                                    // if(line.getType().equals("STOP")
                                                    bus.setDistance(wd.getDistance());
                                                    bus.setDistanceText(wd.getDistance_text());
                                                    bus.setTime(wd.getTime());
                                                    bus.setTimeText(wd.getTime_text());
                                                    list.add(bus);
                                                }
                                                else {
                                                    // sign-in failed
                                                    Toast.makeText(getContext(), "No line found!!", Toast.LENGTH_LONG).show();
                                                }
                                            }

                                            @Override
                                            public void onFailure(Call<WaitingDetail> call3, Throwable throwable) {
                                                Log.i(TAG, throwable.getMessage());
                                                Toast.makeText(getContext(), "An error has occured when fetch url " + call3.request().url() + ": " + throwable.getMessage(), Toast.LENGTH_LONG).show();
                                            }

                                        });

                                    }

                                }
                                else {
                                    // sign-in failed
                                    Toast.makeText(getContext(), "No bus found fir this line!!", Toast.LENGTH_LONG).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<List<Bus>> call2, Throwable throwable) {
                                Log.i(TAG, throwable.getMessage());
                                Toast.makeText(getContext(), "An error has occured when fetch url " + call2.request().url() + ": " + throwable.getMessage(), Toast.LENGTH_LONG).show();
                            }

                        });
                    }
                }
                else {
                    // sign-in failed
                    Toast.makeText(getContext(), "No line found!!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<Line>> call, Throwable throwable) {
                Log.i(TAG, throwable.getMessage());
                Toast.makeText(getContext(), "An error has occured when fetch url " + call.request().url() + ": " + throwable.getMessage(), Toast.LENGTH_LONG).show();
            }

        });
        DataUtils.hideProgressDialog(pDialog);

        Log.i(TAG, "BUS LIST SIZE : " + list.size());

        return list;
    }


    private  LineItem getLine(String startPoint, String endPoint) {
        final LineItem[] li = {null};
        List<LineItem> list = new ArrayList<>();

        Call<List<Line>> call = RetrofitClient.getInstance(token).getMyApi().findLines(null, null, null, null, null, startPoint, endPoint);
        call.enqueue(new Callback<List<Line>>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(Call<List<Line>> call, retrofit2.Response<List<Line>> response) {

                Log.i(TAG, "URL : " + call.request().url().toString());
                List<Line> lines = response.body();
                if (lines != null && !lines.isEmpty()) {
                    Log.i(TAG, "LINE SIZE : " + lines.size());
                    for (Line line : lines) {
                        // if(line.getType().equals("STOP")
                        list.add(new LineItem(line.getUuid(), line.getStartPoint().getName(),  line.getEndPoint().getName(), ""));
                    }
                }
                else {
                    // sign-in failed
                    Toast.makeText(getContext(), "No line found!!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<Line>> call, Throwable throwable) {
                Log.i(TAG, throwable.getMessage());
                Toast.makeText(getContext(), "An error has occured when fetch url " + call.request().url() + ": " + throwable.getMessage(), Toast.LENGTH_LONG).show();
            }

        });

        if(list.size() > 0)
            Log.i(TAG, "LINE : " + list.get(0).toString());

        return list.size() > 0 ? list.get(0) : null;
    }


    private  ArrayList<LineItem> getListLines() {
        ArrayList<LineItem> list = new ArrayList<LineItem>();
        LineItem line0 = new LineItem("", "Select one item", "", "");

        Call<List<Line>> call = RetrofitClient.getInstance(token).getMyApi().findLines(null, null, null, null, null, null, null);
        call.enqueue(new Callback<List<Line>>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(Call<List<Line>> call, retrofit2.Response<List<Line>> response) {

                Log.i(TAG, "URL : " + call.request().url().toString());
                List<Line> lines = response.body();
                if (lines != null && !lines.isEmpty()) {
                    Log.i(TAG, "LINE SIZE : " + lines.size());
                    String roles = null;
                    for (Line line : lines) {
                        // if(line.getType().equals("STOP")
                        list.add(new LineItem(line.getUuid(), line.getStartPoint().getName(),  line.getEndPoint().getName(), ""));
                    }
                    // hide the progress bar
//                    progressbar.setVisibility(View.GONE);

                }
                else {

                    // sign-in failed
                    Toast.makeText(getContext(), "No line found!!", Toast.LENGTH_LONG).show();

                    // hide the progress bar
//                    progressbar.setVisibility(View.GONE);
                }
                DataUtils.hideProgressDialog(pDialog);

            }

            @Override
            public void onFailure(Call<List<Line>> call, Throwable throwable) {
                Log.i(TAG, throwable.getMessage());
                Toast.makeText(getContext(), "An error has occured when fetch url " + call.request().url() + ": " + throwable.getMessage(), Toast.LENGTH_LONG).show();
                // hide the progress bar
//                progressbar.setVisibility(View.GONE);
                DataUtils.hideProgressDialog(pDialog);
            }

        });

        return list;
    }


    private  ArrayList<Bus> getLineBusList(String lineUuid) {
        ArrayList<Bus> list = new ArrayList<Bus>();
        Log.i(TAG, "LINE UUID : " + lineUuid);

        Call<List<Bus>> call = RetrofitClient.getInstance(token).getMyApi().getLineBus(lineUuid);
        call.enqueue(new Callback<List<Bus>>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(Call<List<Bus>> call, retrofit2.Response<List<Bus>> response) {

                Log.i(TAG, "URL : " + call.request().url().toString());
                List<Bus> busList = response.body();
                if (busList != null && !busList.isEmpty()) {
                    Log.i(TAG, "BUS SIZE : " + busList.size());
                    for (Bus bus : busList) {
                        // if(line.getType().equals("STOP")
                        list.add(new Bus(bus.getUuid(), bus.getNumChassis(), bus.getImmatriculation(),  bus.getLatitude(), bus.getLongitude()));
                    }
                }
                else {
                    // sign-in failed
                    Toast.makeText(getContext(), "No bus found fir this line!!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<Bus>> call, Throwable throwable) {
                Log.i(TAG, throwable.getMessage());
                Toast.makeText(getContext(), "An error has occured when fetch url " + call.request().url() + ": " + throwable.getMessage(), Toast.LENGTH_LONG).show();
            }

        });

        return list;
    }


    private WaitingDetail getBusWaitingTime(String startLat, String startLng, String endLat, String endLng) {
        final WaitingDetail[] waitingDetail = {null};

        Call<WaitingDetail> call = RetrofitClient.getInstance(token).getMyApi().getWaitingTime(startLat, startLng, endLat, endLng);
        call.enqueue(new Callback<WaitingDetail>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(Call<WaitingDetail> call, retrofit2.Response<WaitingDetail> response) {

                Log.i(TAG, "URL : " + call.request().url().toString());
                WaitingDetail wd = response.body();
                if (wd != null) {
                    Log.i(TAG, "WAITING DETAIL : " + wd.toString());
                    waitingDetail[0] = wd;
                }
                else {
                    // sign-in failed
                    Toast.makeText(getContext(), "No line found!!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<WaitingDetail> call, Throwable throwable) {
                Log.i(TAG, throwable.getMessage());
                Toast.makeText(getContext(), "An error has occured when fetch url " + call.request().url() + ": " + throwable.getMessage(), Toast.LENGTH_LONG).show();
            }

        });

        return waitingDetail[0];
    }


    /*private  ArrayList<Bus> getListBus() {
        ArrayList<Bus> list = new ArrayList<Bus>();
        Bus bus0 = new Bus("245", 4.02535, 9.6942605, "LT7837KI");
        Bus bus1 = new Bus("087", 4.031871499999999, 9.6906967, "LT207BV");
        Bus bus2 = new Bus("076", 4.0430068, 9.690278900000001, "LT951ZS");
        Bus bus3 = new Bus("012", 4.0480242,9.692945500000002, "LT451HG");
        Bus bus4 = new Bus("012", 4.031871499999999, 9.6942605, "LT1320PM");
//        4.02535,9.692945500000002);
//        4.0480242,9.6942605);
//        4.031871499999999,9.690278900000001);
//        4.0430068,9.6906967);
//        4.031871499999999,9.690278900000001);
//        4.0430068,9.6906967);
//        4.02535,9.692945500000002

        //list.add(bus0);
        list.add(bus1);
        list.add(bus2);
        list.add(bus3);
        list.add(bus4);

        return list;
    }*/

    private ArrayList<StopItem> getListDepartureStops() {
        DataUtils.showProgressDialog(pDialog);
        Log.i(TAG, "IN getListDepartureStops() ");

        ArrayList<StopItem> list = new ArrayList<StopItem>();
        list.add(new StopItem("", getResources().getString(R.string.current_pos),  "", 0d, 0d));

        Call<List<POI>> call = RetrofitClient.getInstance(token).getMyApi().findStops();
        call.enqueue(new Callback<List<POI>>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(Call<List<POI>> call, retrofit2.Response<List<POI>> response) {

                Log.i(TAG, "URL : " + call.request().url().toString());
                List<POI> pois = response.body();
                if (pois != null && !pois.isEmpty()) {
                    Log.i(TAG, "POI SIZE : " + pois.size());
                    String roles = null;
                    for (POI poi : pois) {
                        // if(poi.getType().equals("STOP")
                        list.add(new StopItem(poi.getUuid(), poi.getName(),  "", Double.valueOf(poi.getLatitude()), Double.valueOf(poi.getLongitude())));
                    }
                    // hide the progress bar
//                    progressbar.setVisibility(View.GONE);

                }
                else {

                    // sign-in failed
                    Toast.makeText(getContext(), "Login failed!!", Toast.LENGTH_LONG).show();

                    // hide the progress bar
//                    progressbar.setVisibility(View.GONE);
                }
                DataUtils.hideProgressDialog(pDialog);

            }

            @Override
            public void onFailure(Call<List<POI>> call, Throwable throwable) {
                Log.i(TAG, throwable.getMessage());
                Toast.makeText(getContext(), "An error has occured when fetch url " + call.request().url() + ": " + throwable.getMessage(), Toast.LENGTH_LONG).show();
                // hide the progress bar
//                progressbar.setVisibility(View.GONE);
                DataUtils.hideProgressDialog(pDialog);
            }

        });

        return list;
    }


    private ArrayList<StopItem> getListArrivalStops() {
        DataUtils.showProgressDialog(pDialog);

        ArrayList<StopItem> list = new ArrayList<StopItem>();
        list.add(new StopItem("", getResources().getString(R.string.next_stop),  "", 0d, 0d));

        Call<List<POI>> call = RetrofitClient.getInstance(token).getMyApi().findStops();
        call.enqueue(new Callback<List<POI>>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(Call<List<POI>> call, retrofit2.Response<List<POI>> response) {

                Log.i(TAG, "URL : " + call.request().url().toString());
                List<POI> pois = response.body();
                if (pois != null && !pois.isEmpty()) {
                    Log.i(TAG, "POI SIZE : " + pois.size());
                    String roles = null;
                    for (POI poi : pois) {
                        // if(poi.getType().equals("STOP")
                        if(!poi.getUuid().equals(settings.getStringValue(getContext(), settings.PREFS_USER_CURRENT_STOP_KEY))) {
                            list.add(new StopItem(poi.getUuid(), poi.getName(), "", Double.valueOf(poi.getLatitude()), Double.valueOf(poi.getLongitude())));
                        }
                    }
                    // hide the progress bar
//                    progressbar.setVisibility(View.GONE);

                }
                else {

                    // sign-in failed
                    Toast.makeText(getContext(), "Login failed!!", Toast.LENGTH_LONG).show();

                    // hide the progress bar
//                    progressbar.setVisibility(View.GONE);
                }
                DataUtils.hideProgressDialog(pDialog);

            }

            @Override
            public void onFailure(Call<List<POI>> call, Throwable throwable) {
                Log.i(TAG, throwable.getMessage());
                Toast.makeText(getContext(), "An error has occured when fetch url " + call.request().url() + ": " + throwable.getMessage(), Toast.LENGTH_LONG).show();
                // hide the progress bar
//                progressbar.setVisibility(View.GONE);
                DataUtils.hideProgressDialog(pDialog);
            }

        });

        return list;
    }


    private  ArrayList<StopItem> initListArrivalStops() {
        ArrayList<StopItem> list = new ArrayList<StopItem>();
        list.add(new StopItem("", getResources().getString(R.string.next_stop),  "", 0d, 0d));

        return list;
    }

    private void populateDepStopDropDown(ArrayList<StopItem> departureStopsDetails) {
        depStopAdapter = new ArrayAdapter<StopItem>(getContext(),
                android.R.layout.simple_spinner_item,
                departureStopsDetails);

        depStopAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.departure.setAdapter(depStopAdapter);
    }

    private void populateArrStopDropDown(ArrayList<StopItem> arrivalStopsDetails) {
        arrStopAdater = new ArrayAdapter<StopItem>(getContext(),
                android.R.layout.simple_spinner_item,
                arrivalStopsDetails);

        arrStopAdater.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.arrival.setAdapter(arrStopAdater);
    }




    /**
     * Method to make json array request where response starts with [
     * */
    private void makeJsonArrayRequest(String url) {

        showpDialog();

        JsonArrayRequest req = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, response.toString());


                        hidepDialog();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                Toast.makeText(getContext(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();
                hidepDialog();
            }
        });

        // Adding request to request queue
        JsonController.getInstance().addToRequestQueue(req);
    }

    private void showpDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hidepDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

    private BitmapDescriptor BitmapFromVector(Context context, int vectorResId) {
        // below line is use to generate a drawable.
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorResId);

        // below line is use to set bounds to our vector drawable.
        vectorDrawable.setBounds(0, 0, vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight());

        // below line is use to create a bitmap for our
        // drawable which we have added.
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);

        // below line is use to add bitmap in our canvas.
        Canvas canvas = new Canvas(bitmap);

        // below line is use to draw our
        // vector drawable in canvas.
        vectorDrawable.draw(canvas);

        // after generating our bitmap we are returning our bitmap.
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }

}
