package cm.intelso.dev.travelezi;

import android.app.Activity;
import android.app.ProgressDialog;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import cm.intelso.dev.travelezi.adapter.RideListAdapter;
import cm.intelso.dev.travelezi.dto.RideItem;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HistRideFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HistRideFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 * This is the main (home) screen displayed with the user preferred
 * lines and stop and with the possibility to find more
 * There is also the near stop based on the user location
 */
public class HistRideFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    RecyclerView listLastRides;
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
    public static HistRideFragment newInstance(String param1, String param2) {
        HistRideFragment fragment = new HistRideFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public HistRideFragment() {
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
        final View view = inflater.inflate(R.layout.fragment_hist_ride, container, false);
        //getDialog().setTitle(getResources().getString(R.string.add_account_title));

        pDialog = new ProgressDialog(getContext());
        pDialog.setMessage(getString(R.string.wait));
        pDialog.setCancelable(false);

        ArrayList<RideItem> ridesDetails = getListRides();

        listLastRides = (RecyclerView) view.findViewById(R.id.planning_list);

        RideListAdapter rideAdapter = new RideListAdapter(this.getContext(), ridesDetails, new RideListAdapter.OnItemClickListener(){
            @Override public void onItemClick(RideItem item) {
                Toast.makeText(getContext(), "Ride Item " + item.getCode() + " from " + item.getDeparture() + " to " + item.getArrival() +
                        ", started at " + item.getDepartureHour() + " and ended at " + item.getArrivalHour() + " the " + item.getDate() + " Clicked", Toast.LENGTH_LONG).show();
            }
        });
        // Set adapter with RecyclerView
        listLastRides.setAdapter(rideAdapter);
        // Set LayoutManager
        listLastRides.setLayoutManager(new LinearLayoutManager(this.getContext(), RecyclerView.VERTICAL, false));

        return view;
    }

    private  ArrayList<RideItem> getListRides() {
        ArrayList<RideItem> list = new ArrayList<RideItem>();
        RideItem ride1 = new RideItem("087", "Marché des fleurs", "Carrefour Soudanaise", "MDF, CCW, PTJ, CLC, CSO", "11-11-21", "07:30", "09:10");
        RideItem ride2 = new RideItem("076", "Marché Sandaga", "Carrefour BASSONG", "BCF, EPD, AKN, RBD, PVT, RPP, RPL", "23-10-21", "11:00", "12:25");
        RideItem ride3 = new RideItem("012", "Salle des fêtes AKWA", "Rail BONABERI", "FRB, RPD, NRM, BAG", "08-06-21", "17:20", "19:45");
        RideItem ride4 = new RideItem("087", "Marché des fleurs", "Carrefour Soudanaise", "MDF, CCW, PTJ, CLC, CSO", "05-11-21", "07:30", "08:33");
        RideItem ride5 = new RideItem("087", "Marché des fleurs", "Carrefour Soudanaise", "MDF, CCW, PTJ, CLC, CSO", "28-10-21", "07:30", "09:00");
        RideItem ride6 = new RideItem("087", "Marché des fleurs", "Carrefour Soudanaise", "MDF, CCW, PTJ, CLC, CSO", "18-10-21", "17:30", "19:00");
        RideItem ride7 = new RideItem("087", "Marché des fleurs", "Carrefour Soudanaise", "MDF, CCW, PTJ, CLC, CSO", "14-09-21", "07:30", "08:45");
        RideItem ride8 = new RideItem("087", "Marché des fleurs", "Carrefour Soudanaise", "MDF, CCW, PTJ, CLC, CSO", "21-08-21", "07:30", "09:20");
        RideItem ride9 = new RideItem("087", "Marché des fleurs", "Carrefour Soudanaise", "MDF, CCW, PTJ, CLC, CSO", "15-07-21", "07:30", "08:50");
        RideItem ride10 = new RideItem("087", "Marché des fleurs", "Carrefour Soudanaise", "MDF, CCW, PTJ, CLC, CSO", "15-07-21", "17:30", "18:28");


        list.add(ride1);
        list.add(ride4);
        list.add(ride5);
        list.add(ride2);
        list.add(ride1);
        list.add(ride6);
        list.add(ride7);
        list.add(ride8);
        list.add(ride9);
        list.add(ride10);
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
