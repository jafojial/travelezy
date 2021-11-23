package cm.intelso.dev.travelezi;

import android.app.Activity;
import android.app.ProgressDialog;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Random;

import cm.intelso.dev.travelezi.adapter.StopListAdapter;
import cm.intelso.dev.travelezi.dto.StopItem;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SearchBusStopsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SearchBusStopsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchBusStopsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    TextView tvNotFound;
    Spinner stop;
    Button btnNearStop;
    RecyclerView listStops;
    Fragment newContent;
    ArrayAdapter<StopItem> stopAdapter;

    StopItem selStop = new StopItem();

    ArrayList<StopItem> stopsDetails;
    ArrayList<StopItem> stopsList = new ArrayList<>();

    // Progress dialog
    private ProgressDialog pDialog;

    private static String TAG = MainActivity.class.getSimpleName();
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
    public static SearchBusStopsFragment newInstance(String param1, String param2) {
        SearchBusStopsFragment fragment = new SearchBusStopsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public SearchBusStopsFragment() {
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

        if (savedInstanceState != null) {
            //Restore the fragment's state here
        }
        final View view = inflater.inflate(R.layout.fragment_search_bus_stops, container, false);
        //getDialog().setTitle(getResources().getString(R.string.add_account_title));

        stop = (Spinner)view.findViewById(R.id.tv_start_date);
        btnNearStop = (Button) view.findViewById(R.id.btn_search);

        tvNotFound = (TextView) view.findViewById(R.id.tv_not_found);
        listStops = (RecyclerView) view.findViewById(R.id.stops_list);

        //makeJsonArrayRequest(url);
        stopsDetails = getListStops();
//        ArrayList<RideItem> ridesDetails = getListRides();

        stopAdapter = new ArrayAdapter<StopItem>(getContext(),
                android.R.layout.simple_spinner_item,
                stopsDetails);

        // Layout for All ROWs of Spinner.  (Optional for ArrayAdapter).
        stopAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        this.stop.setAdapter(stopAdapter);

        this.stop.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                onStopItemSelectedHandler(parent, view, position, id);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnNearStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                searchStops(999);
                showHideList();
            }
        });

        //searchLines();
        tvNotFound.setVisibility(View.VISIBLE);
        listStops.setVisibility(View.GONE);

        return view;
    }

    private void searchStops(int index){
        stopsList = new ArrayList<>();

        pDialog = new ProgressDialog(getContext());
        pDialog.setMessage(getString(R.string.wait));
        pDialog.setCancelable(false);

        if(index == 999){
            stopsList = stopsDetails;
        }
        else{
            //stopsList.add(stopsDetails.get(new Random().nextInt(4)));
            if(index < stopsDetails.size()){
                StopItem item = stopsDetails.get(index);
                stopsList.add(item);
            }else{
                stopsList.add(stopsDetails.get(new Random().nextInt(stopsDetails.size())));
            }
        }

        // Instantiate SubjectAdapter with the data
        StopListAdapter stopAdapter = new StopListAdapter(this.getContext(), stopsList, new StopListAdapter.OnItemClickListener(){
            @Override public void onItemClick(StopItem item) {
                Toast.makeText(getContext(), "Stop Item " + item.getCode() + " " + item.getName()  + " Clicked", Toast.LENGTH_LONG).show();
            }
        });
        // Set adapter with RecyclerView
        listStops.setAdapter(stopAdapter);
        // Set LayoutManager
        listStops.setLayoutManager(new LinearLayoutManager(this.getContext(), RecyclerView.VERTICAL, false));

    }

    private void onStopItemSelectedHandler(AdapterView<?> adapterView, View view, int position, long id) {
        Adapter adapter = adapterView.getAdapter();
        selStop = (StopItem) adapter.getItem(position);
        searchStops(position);
        showHideList();

        Toast.makeText(getContext(), "Selected StopItem: " + selStop.getName() ,Toast.LENGTH_SHORT).show();
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
/*
    public int getSelectedID(Spinner spinner){
        int id = 999999;
        for(Map.Entry<Integer, String> entry : dataUtils.villesData.entrySet()) {
            //int key = entry.getKey();
            //String value = entry.getValue();
            if(spinner.getSelectedItem().toString().equals(entry.getValue())){
                id = entry.getKey();
            }
        }
        return id;
    }
*/


    private  ArrayList<StopItem> getListStops() {
        ArrayList<StopItem> list = new ArrayList<StopItem>();
        StopItem stop0 = new StopItem("", "Select one item",  "");
        StopItem stop1 = new StopItem("CCW", "Carrefour CamWater",  "087, 076, 046");
        StopItem stop2 = new StopItem("PTJ", "Pont Joss",  "076, 087, 039, 054");
        StopItem stop3 = new StopItem("CLC", "Carrefour Leclerc", "012, 076, 087, 064");
        StopItem stop4 = new StopItem("MDF", "Marché des fleurs",  "076, 087, 019, 058");
        StopItem stop5 = new StopItem("CSO", "Carrefour Soudanaise", "012, 046, 087, 046");


        //list.add(stop0);
        list.add(stop1);
        list.add(stop2);
        list.add(stop3);
        list.add(stop4);
        list.add(stop5);

        return list;
    }


    private void showHideList(){
        if(stopsList != null && !stopsList.isEmpty()){
            tvNotFound.setVisibility(View.GONE);
            listStops.setVisibility(View.VISIBLE);
        }else{
            tvNotFound.setVisibility(View.VISIBLE);
            listStops.setVisibility(View.GONE);
        }
    }


    /**
     * Method to make json array request where response starts with [
     * */
    /*private void makeJsonArrayRequest(String url) {

        showpDialog();

        JsonArrayRequest req = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, response.toString());

                        try {
                            // Parsing json array response
                            // loop through each json object
                            jsonResponse = "";
                            villes = new ArrayList<String>();
                            for (int i = 0; i < response.length(); i++) {

                                JSONObject ville = (JSONObject) response.get(i);

                                int id = ville.getInt("id");
                                String name = ville.getString("lib_designation");
                                villes.add(name);
                                dataUtils.villesData.put(id, name);
                            }
                            adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, villes);
                            departure.setAdapter(adapter);
                            arrival.setAdapter(adapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getContext(),
                                    "Error: " + e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
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
    }*/

    private void showpDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hidepDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

}
