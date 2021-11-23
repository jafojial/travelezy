package cm.intelso.dev.travelezi;

import android.app.Activity;
import android.app.ProgressDialog;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import cm.intelso.dev.travelezi.adapter.RideListAdapter;
import cm.intelso.dev.travelezi.dto.RideItem;
import cm.intelso.dev.travelezi.utils.SelectDateFragment;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SearchBusRidesFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SearchBusRidesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchBusRidesFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    Button btnSearch;
    TextView tvNotFound, startDate, endDate;
    RecyclerView listRides;
    Fragment newContent;
    ArrayAdapter<RideItem> rideAdapter;

    RideItem selRide = new RideItem();

    ArrayList<RideItem> ridesDetails;

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
     * @return A new instance of fragment BusRideFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SearchBusRidesFragment newInstance(String param1, String param2) {
        SearchBusRidesFragment fragment = new SearchBusRidesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public SearchBusRidesFragment() {
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
        final View view = inflater.inflate(R.layout.fragment_search_bus_rides, container, false);
        //getDialog().setTitle(getResources().getString(R.string.add_account_title));

        startDate = (TextView)view.findViewById(R.id.tv_start_date);
        endDate = (TextView)view.findViewById(R.id.tv_end_date);
        btnSearch = (Button) view.findViewById(R.id.btn_search);

        tvNotFound = (TextView) view.findViewById(R.id.tv_not_found);
        listRides = (RecyclerView) view.findViewById(R.id.stops_list);

        startDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getActivity(), "Date Picker event handle", Toast.LENGTH_LONG).show();
                DialogFragment newFragment = new SelectDateFragment(startDate);
                newFragment.show(getFragmentManager(), "DatePicker");
            }
        });
        endDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getActivity(), "Date Picker event handle", Toast.LENGTH_LONG).show();
                DialogFragment newFragment = new SelectDateFragment(endDate);
                newFragment.show(getFragmentManager(), "DatePicker");
            }
        });

        //makeJsonArrayRequest(url);
        ridesDetails = getListRides();
        
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*newContent = new BusStopFragment();
                newContent.setArguments(bundle);
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.app_content_frame, newContent).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .addToBackStack(null).commit();*/

                searchRides();
                if(ridesDetails != null && !ridesDetails.isEmpty()){
                    tvNotFound.setVisibility(View.GONE);
                    listRides.setVisibility(View.VISIBLE);
                }else{
                    tvNotFound.setVisibility(View.VISIBLE);
                    listRides.setVisibility(View.GONE);
                }
            }
        });

        //searchRides();
        tvNotFound.setVisibility(View.VISIBLE);
        listRides.setVisibility(View.GONE);

        return view;
    }

    private void searchRides(){

        pDialog = new ProgressDialog(getContext());
        pDialog.setMessage(getString(R.string.wait));
        pDialog.setCancelable(false);

        // Instantiate SubjectAdapter with the data
        RideListAdapter lineAdapter = new RideListAdapter(this.getContext(), ridesDetails, new RideListAdapter.OnItemClickListener(){
            @Override public void onItemClick(RideItem item) {
                Toast.makeText(getContext(), "Ride Item " + item.getCode() + " from " + item.getDeparture() + " to " + item.getArrival() + " Clicked", Toast.LENGTH_LONG).show();
            }
        });
        // Set adapter with RecyclerView
        listRides.setAdapter(lineAdapter);
        // Set LayoutManager
        listRides.setLayoutManager(new LinearLayoutManager(this.getContext(), RecyclerView.VERTICAL, false));

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


    private  ArrayList<RideItem> getListRides() {
        ArrayList<RideItem> list = new ArrayList<RideItem>();

        RideItem ride1 = new RideItem("087", "Marché des fleurs", "Carrefour Soudanaise", "MDF, CCW, PTJ, CLC, CSO", "11-11-21", "07:30", "09:10");
        RideItem ride2 = new RideItem("076", "Marché Sandaga", "Carrefour BASSONG", "BCF, EPD, AKN, RBD, PVT, RPP, RPL", "23-10-21", "11:00", "12:25");
        RideItem ride3 = new RideItem("012", "Salle des fêtes AKWA", "Rail BONABERI", "FRB, RPD, NRM, BAG", "08-06-21", "17:20", "19:45");

        list.add(ride1);
        list.add(ride2);
        list.add(ride3);

        return list;
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
