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

import cm.intelso.dev.travelezi.adapter.FavStopListAdapter;
import cm.intelso.dev.travelezi.dto.StopItem;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FavStopFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FavStopFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 * This is the main (home) screen displayed with the user preferred
 * lines and stop and with the possibility to find more
 * There is also the near stop based on the user location
 */
public class FavStopFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    RecyclerView listFavStops;
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
    public static FavStopFragment newInstance(String param1, String param2) {
        FavStopFragment fragment = new FavStopFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public FavStopFragment() {
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
        final View view = inflater.inflate(R.layout.fragment_fav_stop, container, false);
        //getDialog().setTitle(getResources().getString(R.string.add_account_title));

        pDialog = new ProgressDialog(getContext());
        pDialog.setMessage(getString(R.string.wait));
        pDialog.setCancelable(false);

        ArrayList<StopItem> stopsDetails = getListStops();
        listFavStops = (RecyclerView) view.findViewById(R.id.fav_stops_list);
        FavStopListAdapter stopAdapter = new FavStopListAdapter(this.getContext(), stopsDetails, new FavStopListAdapter.OnItemClickListener(){
            @Override public void onItemClick(StopItem item) {
                Toast.makeText(getContext(), "Stop Item " + item.getCode() + "(" + item.getName() + ") Clicked", Toast.LENGTH_LONG).show();
            }
        });
        // Set adapter with RecyclerView
        listFavStops.setAdapter(stopAdapter);
        // Set LayoutManager
        listFavStops.setLayoutManager(new LinearLayoutManager(this.getContext(), RecyclerView.VERTICAL, false));

        return view;
    }

    private  ArrayList<StopItem> getListStops() {
        ArrayList<StopItem> list = new ArrayList<StopItem>();
        StopItem stop0 = new StopItem("", "Select one item",  "", 0d, 0d);
        StopItem stop1 = new StopItem("CCW", "Carrefour CamWater",  "087, 076, 046", 4.031871499999999,9.690278900000001);
        StopItem stop2 = new StopItem("PTJ", "Pont Joss",  "076, 087, 039, 054", 4.0430068,9.6906967);
        StopItem stop3 = new StopItem("CLC", "Carrefour Leclerc", "012, 076, 087, 064",4.0330068,9.690278900000001);
        StopItem stop4 = new StopItem("MDF", "Marché des fleurs",  "076, 087, 019, 058", 4.02535,9.692945500000002);
        StopItem stop5 = new StopItem("CSO", "Carrefour Soudanaise", "012, 046, 087, 046", 4.0480242,9.6942605);


        //list.add(stop0);
        list.add(stop1);
        list.add(stop2);
        list.add(stop3);
        list.add(stop4);
        list.add(stop5);

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
