package cm.intelso.dev.travelezi;

import android.app.Activity;
import android.app.ProgressDialog;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import cm.intelso.dev.travelezi.adapter.LineListAdapter;
import cm.intelso.dev.travelezi.adapter.RideListAdapter;
import cm.intelso.dev.travelezi.adapter.StopListAdapter;
import cm.intelso.dev.travelezi.dto.LineItem;
import cm.intelso.dev.travelezi.dto.RideItem;
import cm.intelso.dev.travelezi.dto.StopItem;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MainFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MainFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 * This is the main (home) screen displayed with the user preferred
 * lines and stop and with the possibility to find more
 * There is also the near stop based on the user location
 */
public class MainFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    Button btnMoreLine, btnMoreStop, btnMoreRide;
    RecyclerView listFavLines, listFavStops, listLastRides;
    //ListView listFavLines, listFavStops, listLastRides;
    Fragment newContent;

    // Progress dialog
    private ProgressDialog pDialog;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MainFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MainFragment newInstance(String param1, String param2) {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_main, container, false);
        final View view = inflater.inflate(R.layout.fragment_main, container, false);
        //getDialog().setTitle(getResources().getString(R.string.add_account_title));

        pDialog = new ProgressDialog(getContext());
        pDialog.setMessage(getString(R.string.wait));
        pDialog.setCancelable(false);

        ArrayList<LineItem> linesDetails = getListLines();
        ArrayList<StopItem> stopsDetails = getListStops();
        ArrayList<RideItem> ridesDetails = getListRides();

        listFavLines = (RecyclerView) view.findViewById(R.id.stops_list);
        listFavStops = (RecyclerView) view.findViewById(R.id.fav_stops_list);
        listLastRides = (RecyclerView) view.findViewById(R.id.fav_rides_list);
        //listFavLines = (ListView) view.findViewById(R.id.fav_lines_list);
        //listFavLines.setAdapter(new LineListAdapter(this.getContext(), linesDetails));
        // Instantiate SubjectAdapter with the data
        LineListAdapter lineAdapter = new LineListAdapter(this.getContext(), linesDetails, new LineListAdapter.OnItemClickListener(){
            @Override public void onItemClick(LineItem item) {
                Toast.makeText(getContext(), "Line Item " + item.getCode() + " from " + item.getDeparture() + " to " + item.getArrival() + " Clicked", Toast.LENGTH_LONG).show();
                newContent = new BusLineFragment();
                Bundle bundle=new Bundle();
                bundle.putString("lineCode", item.getCode());
                newContent.setArguments(bundle);
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.app_content_frame, newContent).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .addToBackStack(null).commit();
            }
        });
        // Set adapter with RecyclerView
        listFavLines.setAdapter(lineAdapter);
        // Set LayoutManager
        listFavLines.setLayoutManager(new LinearLayoutManager(this.getContext(), RecyclerView.HORIZONTAL, false));

        StopListAdapter stopAdapter = new StopListAdapter(this.getContext(), stopsDetails, new StopListAdapter.OnItemClickListener(){
            @Override public void onItemClick(StopItem item) {
                Toast.makeText(getContext(), "Stop Item " + item.getCode() + "(" + item.getName() + ") Clicked", Toast.LENGTH_LONG).show();
            }
        });
        // Set adapter with RecyclerView
        listFavStops.setAdapter(stopAdapter);
        // Set LayoutManager
        listFavStops.setLayoutManager(new LinearLayoutManager(this.getContext(), RecyclerView.HORIZONTAL, false));

        RideListAdapter rideAdapter = new RideListAdapter(this.getContext(), ridesDetails, new RideListAdapter.OnItemClickListener(){
            @Override public void onItemClick(RideItem item) {
                Toast.makeText(getContext(), "Ride Item " + item.getCode() + " from " + item.getDeparture() + " to " + item.getArrival() +
                        ", started at " + item.getDepartureHour() + " and ended at " + item.getArrivalHour() + " the " + item.getDate() + " Clicked", Toast.LENGTH_LONG).show();
            }
        });
        // Set adapter with RecyclerView
        listLastRides.setAdapter(rideAdapter);
        // Set LayoutManager
        listLastRides.setLayoutManager(new LinearLayoutManager(this.getContext(), RecyclerView.HORIZONTAL, false));

        //villeDepart = (Spinner)view.findViewById(R.id.ville_depart);
        //villeDestination = (Spinner)view.findViewById(R.id.ville_destination);
        //dateDepart = (TextView)view.findViewById(R.id.date_depart);
        btnMoreLine = (Button) view.findViewById(R.id.btn_fav_lines_more);
        btnMoreStop = (Button) view.findViewById(R.id.btn_fav_stops_more);
        btnMoreRide = (Button) view.findViewById(R.id.btn_fav_rides_more);

        btnMoreLine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newContent = new SearchBusLinesFragment();
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.app_content_frame, newContent).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .addToBackStack(null).commit();
            }
        });

        btnMoreStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newContent = new SearchBusStopsFragment();
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.app_content_frame, newContent).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .addToBackStack(null).commit();
            }
        });

        //makeJsonArrayRequest(url);
        //loadStaticDatas();

        btnMoreRide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getActivity(), "Date Picker event handle", Toast.LENGTH_LONG).show();
                newContent = new SearchBusRidesFragment();
                /*Bundle bundle=new Bundle();
                bundle.putString("idVilleDepart", String.valueOf(dataUtils.getSelectedID(villeDepart)));
                newContent.setArguments(bundle);*/
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.app_content_frame, newContent).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .addToBackStack(null).commit();
            }
        });

        /*
        buttonValidate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle=new Bundle();
                bundle.putString("idVilleDepart", String.valueOf(dataUtils.getSelectedID(villeDepart)));
                bundle.putString("idVilleDestination", String.valueOf(dataUtils.getSelectedID(villeDestination)));
                bundle.putString("villeDepart", villeDepart.getSelectedItem().toString());
                bundle.putString("villeDestination", villeDestination.getSelectedItem().toString());
                bundle.putString("dateDepart", dateDepart.getText().toString());

                newContent = new BusStopFragment();
                newContent.setArguments(bundle);
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.app_content_frame, newContent).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .addToBackStack(null).commit();
            }
        });*/

        return view;
    }

    private  ArrayList<LineItem> getListLines() {
        ArrayList<LineItem> list = new ArrayList<LineItem>();
        LineItem line1 = new LineItem("087", "Marché des fleurs", "Carrefour Soudanaise", "MDF, CCW, PTJ, CLC, CSO");
        LineItem line2 = new LineItem("076", "Marché Sandaga", "Carrefour BASSONG", "BCF, EPD, AKN, RBD, PVT, RPP, RPL");
        LineItem line3 = new LineItem("012", "Salle des fêtes AKWA", "Rail BONABERI", "FRB, RPD, NRM, BAG");


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
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}
