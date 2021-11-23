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

import cm.intelso.dev.travelezi.adapter.LineListAdapter;
import cm.intelso.dev.travelezi.dto.LineItem;
import cm.intelso.dev.travelezi.dto.StopItem;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SearchBusLinesFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SearchBusLinesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchBusLinesFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    Button btnSearch, btnReset;
    TextView tvNotFound;
    Spinner departure, arrival, lineCodeName;
    RecyclerView listLines;
    Fragment newContent;
    ArrayAdapter<StopItem> stopAdapter;
    ArrayAdapter<LineItem> lineAdapter;

    StopItem selDeparture = new StopItem();
    StopItem selArrival = new StopItem();
    LineItem selLine = new LineItem();

    ArrayList<LineItem> linesDetails;
    ArrayList<StopItem> stopsDetails;

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
    public static SearchBusLinesFragment newInstance(String param1, String param2) {
        SearchBusLinesFragment fragment = new SearchBusLinesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public SearchBusLinesFragment() {
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
        final View view = inflater.inflate(R.layout.fragment_search_bus_lines, container, false);
        //getDialog().setTitle(getResources().getString(R.string.add_account_title));

        departure = (Spinner)view.findViewById(R.id.tv_departure);
        arrival = (Spinner)view.findViewById(R.id.tv_arrival);
        lineCodeName = (Spinner)view.findViewById(R.id.tv_start_date);
        btnReset = (Button) view.findViewById(R.id.btn_reset);
        btnSearch = (Button) view.findViewById(R.id.btn_search);

        tvNotFound = (TextView) view.findViewById(R.id.tv_not_found);
        listLines = (RecyclerView) view.findViewById(R.id.stops_list);

        //makeJsonArrayRequest(url);
        linesDetails = getListLines();
        stopsDetails = getListStops();
//        ArrayList<RideItem> ridesDetails = getListRides();

        stopAdapter = new ArrayAdapter<StopItem>(getContext(),
                android.R.layout.simple_spinner_item,
                stopsDetails);

        // Layout for All ROWs of Spinner.  (Optional for ArrayAdapter).
        stopAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        this.departure.setAdapter(stopAdapter);
        this.arrival.setAdapter(stopAdapter);

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

        lineAdapter = new ArrayAdapter<LineItem>(getContext(),
                android.R.layout.simple_spinner_item,
                linesDetails);

        // Layout for All ROWs of Spinner.  (Optional for ArrayAdapter).
        lineAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        this.lineCodeName.setAdapter(lineAdapter);

        this.lineCodeName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                onLineItemSelectedHandler(parent, view, position, id);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selDeparture = new StopItem();
                selArrival = new StopItem();
                selLine = new LineItem();
                departure.setSelection(0);
                arrival.setSelection(0);
                lineCodeName.setSelection(0);
            }
        });

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle=new Bundle();
                bundle.putString("departureID", selDeparture.getCode());
                bundle.putString("arrivalID", selArrival.getCode());
                bundle.putString("departure", departure.getSelectedItem().toString());
                bundle.putString("arrival", arrival.getSelectedItem().toString());
                bundle.putString("lineID", selLine.getCode());
                bundle.putString("line", lineCodeName.getSelectedItem().toString());

                /*newContent = new BusStopFragment();
                newContent.setArguments(bundle);
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.app_content_frame, newContent).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .addToBackStack(null).commit();*/

                searchLines();
                if(linesDetails != null && !linesDetails.isEmpty()){
                    tvNotFound.setVisibility(View.GONE);
                    listLines.setVisibility(View.VISIBLE);
                }else{
                    tvNotFound.setVisibility(View.VISIBLE);
                    listLines.setVisibility(View.GONE);
                }
            }
        });

        //searchLines();
        tvNotFound.setVisibility(View.VISIBLE);
        listLines.setVisibility(View.GONE);

        return view;
    }

    private void searchLines(){

        pDialog = new ProgressDialog(getContext());
        pDialog.setMessage(getString(R.string.wait));
        pDialog.setCancelable(false);

        // Instantiate SubjectAdapter with the data
        LineListAdapter lineAdapter = new LineListAdapter(this.getContext(), linesDetails, new LineListAdapter.OnItemClickListener(){
            @Override public void onItemClick(LineItem item) {
                Toast.makeText(getContext(), "Line Item " + item.getCode() + " from " + item.getDeparture() + " to " + item.getArrival() + " Clicked", Toast.LENGTH_LONG).show();
            }
        });
        // Set adapter with RecyclerView
        listLines.setAdapter(lineAdapter);
        // Set LayoutManager
        listLines.setLayoutManager(new LinearLayoutManager(this.getContext(), RecyclerView.VERTICAL, false));

    }

    private void onDepartureStopItemSelectedHandler(AdapterView<?> adapterView, View view, int position, long id) {
        Adapter adapter = adapterView.getAdapter();
        selDeparture = (StopItem) adapter.getItem(position);

        lineCodeName.setSelection(0);

        Toast.makeText(getContext(), "Selected departure StopItem: " + selDeparture.getName() ,Toast.LENGTH_SHORT).show();
    }

    private void onArrivalStopItemSelectedHandler(AdapterView<?> adapterView, View view, int position, long id) {
        Adapter adapter = adapterView.getAdapter();
        selArrival = (StopItem) adapter.getItem(position);

        lineCodeName.setSelection(0);

        Toast.makeText(getContext(), "Selected arrival StopItem: " + selArrival.getName() ,Toast.LENGTH_SHORT).show();
    }

    private void onLineItemSelectedHandler(AdapterView<?> adapterView, View view, int position, long id) {
        Adapter adapter = adapterView.getAdapter();
        selLine = (LineItem) adapter.getItem(position);

        departure.setSelection(0);
        arrival.setSelection(0);

        Toast.makeText(getContext(), "Selected LineItem: " + selLine.getDeparture() + " - " + selLine.getArrival() ,Toast.LENGTH_SHORT).show();
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


    private  ArrayList<LineItem> getListLines() {
        ArrayList<LineItem> list = new ArrayList<LineItem>();
        LineItem line0 = new LineItem("", "Select one item", "", "");
        LineItem line1 = new LineItem("087", "Marché des fleurs", "Carrefour Soudanaise", "MDF, CCW, PTJ, CLC, CSO");
        LineItem line2 = new LineItem("076", "Marché Sandaga", "Carrefour BASSONG", "BCF, EPD, AKN, RBD, PVT, RPP, RPL");
        LineItem line3 = new LineItem("012", "Salle des fêtes AKWA", "Rail BONABERI", "FRB, RPD, NRM, BAG");


        //list.add(line0);
        list.add(line1);
        list.add(line2);
        list.add(line3);

        return list;
    }

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
