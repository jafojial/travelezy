package cm.intelso.dev.travelezi;

import android.app.Activity;
import android.app.ProgressDialog;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import cm.intelso.dev.travelezi.json.JsonController;
import cm.intelso.dev.travelezi.utils.DataUtils;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link BusStopFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link BusStopFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BusStopFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    Button buttonValidate, buttonCancel;
    Spinner heureDepart;
    Fragment newContent;
    ArrayAdapter<String> adapter;
    ArrayList<String> departs = new ArrayList<String>();
    DataUtils dataUtils = new DataUtils();

    // Progress dialog
    private ProgressDialog pDialog;

    private static String TAG = MainActivity.class.getSimpleName();
    //http://192.168.25.179/touristique/web/index.php/apiv1/voyage/search?date=2016-07-02&villeDepart=1&villeRetour=2
    String url = "http://192.168.25.179/touristique/web/index.php/apiv1/voyage/search?";
    String dateDepart = "date=";
    String idVilleDepart = "&villeDepart=";
    String idVilleDestination = "&villeRetour=";
    String villeDepart = "";
    String villeDestination = "";
    private String jsonResponse;

    String[] departsDefaut = new String[]{"05:00", "06:00", "07:00", "08:30", "09:30", "11:00", "12:30", "14:00", "15:00", "16:00", "17:00", "18:45"};

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BusStopFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BusStopFragment newInstance(String param1, String param2) {
        BusStopFragment fragment = new BusStopFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public BusStopFragment() {
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
        if (getArguments() != null && getArguments().containsKey("idVilleDepart"))
            idVilleDepart+=getArguments().getString("idVilleDepart");
        if (getArguments() != null && getArguments().containsKey("idVilleDestination"))
            idVilleDestination+=getArguments().getString("idVilleDestination");
        if (getArguments() != null && getArguments().containsKey("villeDepart")) {
            villeDepart = getArguments().getString("villeDepart");
            dataUtils.villeDepart = getArguments().getString("villeDepart");
        }
        if (getArguments() != null && getArguments().containsKey("villeDestination")){
            villeDestination = getArguments().getString("villeDestination");
            dataUtils.villeDestination=villeDestination;
        }
        if (getArguments() != null && getArguments().containsKey("dateDepart")){
            dateDepart+=getArguments().getString("dateDepart");
            dataUtils.dateDepart=getArguments().getString("dateDepart");
        }
        Toast.makeText(getActivity(), "Bundle data : "+dateDepart
                +", ("+idVilleDepart+")"+villeDepart
                +", ("+idVilleDestination+")"+villeDestination, Toast.LENGTH_LONG).show();
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_bus_stop, container, false);
        final View view = inflater.inflate(R.layout.fragment_bus_stop, container, false);
        //getDialog().setTitle(getResources().getString(R.string.add_account_title));


        pDialog = new ProgressDialog(getContext());
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);

        heureDepart = (Spinner)view.findViewById(R.id.heure_depart);
        /*
        adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, departsDefaut);
        heureDepart.setAdapter(adapter);
*/
        //makeJsonArrayRequest();
        loadStaticDatas();
        //Toast.makeText(getContext(), "Bus : " + departsDefaut.toString(), Toast.LENGTH_LONG).show();

        buttonCancel = (Button) view.findViewById(R.id.button_cancel_one_way_bus);
        buttonValidate = (Button) view.findViewById(R.id.button_ok_one_way_bus);

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        buttonValidate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle bundle=new Bundle();

                bundle.putString("typeVoyage", "0"); //0 pour Aller Simple
                bundle.putString("classeVoyage", "1"); //1 pour Prestige
                bundle.putString("villeDepart", villeDepart);
                bundle.putString("villeDestination", villeDestination);
                bundle.putString("dateDepart", dateDepart);
                bundle.putString("heureDepart", dataUtils.getHeureOnly(heureDepart.getSelectedItem().toString()));

                newContent = new PassengerInfosFragment();
                newContent.setArguments(bundle);
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
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

    private void loadStaticDatas() {

        showpDialog();
        departs = new ArrayList<String>();
        departsDefaut = new String[]{"05:00", "06:00", "07:00", "08:30", "09:30", "11:00", "12:30", "14:00", "15:00", "16:00", "17:00", "18:45"};
        if(timeNotReach("05:00:00")){
            departsDefaut = new String[]{"05:00", "06:00", "07:00", "08:30", "09:30", "11:00", "12:30", "14:00", "15:00", "16:00", "17:00", "18:45"};
        } else if(timeNotReach("06:00:00")){
            departsDefaut = new String[]{"06:00", "07:00", "08:30", "09:30", "11:00", "12:30", "14:00", "15:00", "16:00", "17:00", "18:45"};
        } else if(timeNotReach("07:00:00")){
            departsDefaut = new String[]{"07:00", "08:30", "09:30", "11:00", "12:30", "14:00", "15:00", "16:00", "17:00", "18:45"};
        } else if(timeNotReach("08:30:00")){
            departsDefaut = new String[]{"08:30", "09:30", "11:00", "12:30", "14:00", "15:00", "16:00", "17:00", "18:45"};
        } else if(timeNotReach("09:30:00")){
            departsDefaut = new String[]{"09:30", "11:00", "12:30", "14:00", "15:00", "16:00", "17:00", "18:45"};
        } else if(timeNotReach("11:00:00")){
            departsDefaut = new String[]{"11:00", "12:30", "14:00", "15:00", "16:00", "17:00", "18:45"};
        } else if(timeNotReach("12:30:00")){
            departsDefaut = new String[]{"12:30", "14:00", "15:00", "16:00", "17:00", "18:45"};
        } else if(timeNotReach("14:00:00")){
            departsDefaut = new String[]{"14:00", "15:00", "16:00", "17:00", "18:45"};
        } else if(timeNotReach("15:00:00")){
            departsDefaut = new String[]{"15:00", "16:00", "17:00", "18:45"};
        } else if(timeNotReach("16:00:00")){
            departsDefaut = new String[]{"16:00", "17:00", "18:45"};
        } else if(timeNotReach("17:00:00")){
            departsDefaut = new String[]{"17:00", "18:45"};
        } else if(timeNotReach("18:45:00")){
            departsDefaut = new String[]{"18:45"};
        } else {
            departsDefaut = new String[]{};
        }
        Toast.makeText(getContext(), "Bus : " + departsDefaut.toString(), Toast.LENGTH_LONG).show();

        for (String dep : departsDefaut) {

            int availablePlaces = new Random().nextInt(54) + 1;
            departs.add(dep+" ("+availablePlaces+" places)");
        }
        adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, departs);
        heureDepart.setAdapter(adapter);

        hidepDialog();
    }


    private Boolean timeNotReach(String hour){
        SimpleDateFormat  format = new SimpleDateFormat("HH:mm:ss");
        Date date = null;
        try {
            date = format.parse(hour);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        long convertedLong = date.getTime();

        long currentTimeLong = System.currentTimeMillis(); // will get you current time in milli
        return currentTimeLong < convertedLong;
    }


    private void makeJsonArrayRequest() {

        showpDialog();

        JsonArrayRequest req = new JsonArrayRequest(url+dateDepart+idVilleDepart+idVilleDestination,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, response.toString());

                        try {
                            // Parsing json array response
                            // loop through each json object
                            jsonResponse = "";
                            departs = new ArrayList<String>();
                            for (int i = 0; i < response.length(); i++) {

                                JSONObject ville = (JSONObject) response.get(i);

                                int availablePlaces = ville.getInt("availablePlacesNumber");
                                String bus = ville.getString("bus");
                                departs.add(bus+" ("+availablePlaces+" places)");
                            }
                            adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, departs);
                            heureDepart.setAdapter(adapter);
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
    }

    private void showpDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hidepDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

}
