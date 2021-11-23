package cm.intelso.dev.travelezi;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import cm.intelso.dev.travelezi.utils.DataUtils;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TravelTypeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TravelTypeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TravelTypeFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    Button buttonAllerSimple, buttonAllerRetour;
    Fragment newContent;
    DataUtils dataUtils = new DataUtils();
    /*
    String url = "http://192.168.25.179/touristique/web/index.php/apiv1/ville";
    ArrayList<String> villes = new ArrayList<String>();
    ArrayList<Integer> idVilles = new ArrayList<Integer>();
    */

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TravelTypeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TravelTypeFragment newInstance(String param1, String param2) {
        TravelTypeFragment fragment = new TravelTypeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public TravelTypeFragment() {
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
        //return inflater.inflate(R.layout.fragment_travel_type, container, false);
        final View view = inflater.inflate(R.layout.fragment_travel_type, container, false);
        //getDialog().setTitle(getResources().getString(R.string.add_account_title));
/*
        dataUtils.pDialog = new ProgressDialog(getContext());
        dataUtils.pDialog.setMessage("Please wait...");
        dataUtils.pDialog.setCancelable(false);
*/
        buttonAllerSimple = (Button) view.findViewById(R.id.button_one_way_ticket);
        buttonAllerRetour = (Button) view.findViewById(R.id.button_two_way_ticket);

        buttonAllerSimple.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //new AsyncLoadCity().execute(0);

                Bundle bundle=new Bundle();
                bundle.putString("typeVoyage", "1");
                bundle.putString("classe", "prestige");

                newContent = new BusLineFragment();
                newContent.setArguments(bundle);
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.app_content_frame, newContent).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .addToBackStack(null).commit();
            }
        });

        buttonAllerRetour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //new AsyncLoadCity().execute(1);

                newContent = new TwoWayTicketFragment();
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.app_content_frame, newContent).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .addToBackStack(null).commit();
            }
        });

        return view;
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
/*
    private class AsyncLoadCity extends AsyncTask<Integer, Void, Void> {

        int type;

        @Override
        protected Void doInBackground(Integer... params) {

            type = params[0];

            // Obtain data from webservice
            for(Map.Entry<Integer, String> entry : dataUtils.getCityJsonArrayRequest(url).entrySet()) {
                //int key = entry.getKey();
                //String value = entry.getValue();
                idVilles.add(entry.getKey());
                villes.add(entry.getValue());
            }
            //villes = dataUtils.getCityJsonArrayRequest(url);
            return null;

        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            //start new activity or fragment with data received
            if(type==0){
                //voyage aller simple
                Bundle bundle=new Bundle();
                bundle.putStringArrayList("villes", villes);
                bundle.putIntegerArrayList("idVilles", idVilles);

                newContent = new BusLineFragment();
                newContent.setArguments(bundle);
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.app_content_frame, newContent).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .addToBackStack(null).commit();
            }else if(type==1){
                //voyage aller et retour
                Bundle bundle=new Bundle();
                bundle.putStringArrayList("villes", villes);
                bundle.putIntegerArrayList("idVilles", idVilles);

                newContent = new TwoWayTicketFragment();
                newContent.setArguments(bundle);
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.app_content_frame, newContent).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .addToBackStack(null).commit();
            }

        }

    }
*/
}
