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

import java.util.ArrayList;

import cm.intelso.dev.travelezi.R;
import cm.intelso.dev.travelezi.json.JsonController;
import cm.intelso.dev.travelezi.utils.DataUtils;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TwoWayBusFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TwoWayBusFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TwoWayBusFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    Button buttonValidate, buttonCancel;
    Spinner heureDepart, heureRetour;
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
    String dateRetour = "date=";
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
     * @return A new instance of fragment TwoWayBusFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TwoWayBusFragment newInstance(String param1, String param2) {
        TwoWayBusFragment fragment = new TwoWayBusFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public TwoWayBusFragment() {
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
        if (getArguments() != null && getArguments().containsKey("villeDepart"))
            villeDepart = getArguments().getString("villeDepart");
        if (getArguments() != null && getArguments().containsKey("villeDestination"))
            villeDestination = getArguments().getString("villeDestination");
        if (getArguments() != null && getArguments().containsKey("dateDepart")){
            dateDepart+=getArguments().getString("dateDepart");
            dataUtils.dateDepart = getArguments().getString("dateDepart");
        }
        if (getArguments() != null && getArguments().containsKey("dateRetour")){
            dateRetour+=getArguments().getString("dateRetour");
            dataUtils.dateRetour = getArguments().getString("dateRetour");
        }
        Toast.makeText(getActivity(), "Bundle data : " + dateDepart
                + ", " + dateRetour
                + ", " + villeDepart
                + ", " + villeDestination, Toast.LENGTH_LONG).show();
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_two_way_bus, container, false);
        final View view = inflater.inflate(R.layout.fragment_two_way_bus, container, false);
        //getDialog().setTitle(getResources().getString(R.string.add_account_title));

        pDialog = new ProgressDialog(getContext());
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);

        heureDepart = (Spinner)view.findViewById(R.id.heure_depart_aller);
        heureRetour = (Spinner)view.findViewById(R.id.heure_depart_retour);
        /*
        adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, departsDefaut);
        heureDepart.setAdapter(adapter);
        heureRetour.setAdapter(adapter);
*/
        makeJsonArrayRequest(url + dateDepart + idVilleDepart + idVilleDestination, 0);
        makeJsonArrayRequest(url+dateRetour+idVilleDestination+idVilleDepart, 1);
        Toast.makeText(getContext(), "Bus : " + departsDefaut.toString(), Toast.LENGTH_LONG).show();

        buttonCancel = (Button) view.findViewById(R.id.button_cancel_two_way_bus);
        buttonValidate = (Button) view.findViewById(R.id.button_ok_two_way_bus);

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
                bundle.putString("typeVoyage", "1"); //1 pour Aller et Retour
                bundle.putString("classeVoyage", "1");//1 pour Prestige
                bundle.putString("villeDepart", villeDepart);
                bundle.putString("villeDestination", villeDestination);
                bundle.putString("dateDepart", dataUtils.dateDepart);
                bundle.putString("dateRetour", dataUtils.dateRetour);
                bundle.putString("heureDepart", dataUtils.getHeureOnly(heureDepart.getSelectedItem().toString()));
                bundle.putString("heureRetour", dataUtils.getHeureOnly(heureRetour.getSelectedItem().toString()));

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

    /**
     * This this method make  request to a webservice from url and travel way
     * @param url adress of webservice
     * @param allerOuRetour 0 for aller and 1 for retour
     */
    private void makeJsonArrayRequest(String url, final int allerOuRetour) {

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
                            departs = new ArrayList<String>();
                            for (int i = 0; i < response.length(); i++) {

                                JSONObject ville = (JSONObject) response.get(i);

                                int availablePlaces = ville.getInt("availablePlacesNumber");
                                String bus = ville.getString("bus");
                                departs.add(bus+" ("+availablePlaces+" places)");
                            }
                            adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, departs);
                            if(allerOuRetour==0){
                                heureDepart.setAdapter(adapter);
                            } else if(allerOuRetour==1){
                                heureRetour.setAdapter(adapter);
                            }
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
