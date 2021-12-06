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
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cm.intelso.dev.travelezi.json.JsonController;
import cm.intelso.dev.travelezi.utils.DataUtils;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PassengerInfosFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PassengerInfosFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PassengerInfosFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    TextView prix;
    EditText nomPassager, cniPassager, numeroTelephonePassager, emailPassager;
    Spinner bagages;
    Button buttonValidate, buttonCancel;
    Fragment newContent;
    ArrayAdapter<String> adapter;
    ArrayList<String> departs = new ArrayList<String>();
    DataUtils dataUtils = new DataUtils();

    // Progress dialog
    private ProgressDialog pDialog;

    private static String TAG = MainActivity.class.getSimpleName();
    String url = "http://192.168.25.179/touristique/web/index.php/apiv1/prix/search?";
    String typeVoyage = "typeVoyage=";
    String classeVoyage = "&classe=";
    private String jsonResponse;

    String[] nbreBagages = new String[]{"0", "1", "2", "3", "4", "5", "Plus"};

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PassengerInfosFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PassengerInfosFragment newInstance(String param1, String param2) {
        PassengerInfosFragment fragment = new PassengerInfosFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public PassengerInfosFragment() {
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
        if (getArguments() != null && getArguments().containsKey("typeVoyage")){
            typeVoyage+=getArguments().getString("typeVoyage");
            if(getArguments().getString("typeVoyage").equalsIgnoreCase("1")){//1 pour Aller et Retour
                dataUtils.typeVoyage = getResources().getString(R.string.two_way_travel).toUpperCase();
                if (getArguments() != null && getArguments().containsKey("dateRetour"))
                    dataUtils.dateRetour=getArguments().getString("dateRetour");
                if (getArguments() != null && getArguments().containsKey("heureRetour"))
                    dataUtils.heureRetour=getArguments().getString("heureRetour");
            }else if(getArguments().getString("typeVoyage").equalsIgnoreCase("0")){//0 pour Aller Simple
                dataUtils.typeVoyage = getResources().getString(R.string.one_way_travel).toUpperCase();
            }
        }
        if (getArguments() != null && getArguments().containsKey("classeVoyage")){
            classeVoyage+=getArguments().getString("classeVoyage");
            if(getArguments().getString("classeVoyage").equalsIgnoreCase("1")){//1 pour Prestige
                dataUtils.classeVoyage = getResources().getString(R.string.premium_class);
            }
        }
        if (getArguments() != null && getArguments().containsKey("classeVoyage"))
            dataUtils.classeVoyage=getArguments().getString("classeVoyage");
        if (getArguments() != null && getArguments().containsKey("villeDepart"))
            dataUtils.villeDepart=getArguments().getString("villeDepart");
        if (getArguments() != null && getArguments().containsKey("villeDestination"))
            dataUtils.villeDestination=getArguments().getString("villeDestination");
        if (getArguments() != null && getArguments().containsKey("dateDepart"))
            dataUtils.dateDepart=getArguments().getString("dateDepart");
        if (getArguments() != null && getArguments().containsKey("dateRetour"))
            dataUtils.dateRetour=getArguments().getString("dateRetour");
        if (getArguments() != null && getArguments().containsKey("heureDepart"))
            dataUtils.heureDepart=getArguments().getString("heureDepart");
        if (getArguments() != null && getArguments().containsKey("heureRetour"))
            dataUtils.heureRetour=getArguments().getString("heureRetour");

        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_passenger_infos, container, false);
        final View view = inflater.inflate(R.layout.fragment_passenger_infos, container, false);
        //getDialog().setTitle(getResources().getString(R.string.add_account_title));

        pDialog = new ProgressDialog(getContext());
        pDialog.setMessage(getString(R.string.wait));
        pDialog.setCancelable(false);

        prix = (TextView) view.findViewById(R.id.prix);
        //prix.setText(getPriceJsonArrayRequest(url+typeVoyage+classeVoyage));
        getPriceJsonObjectRequest(url + typeVoyage + classeVoyage);
        nomPassager = (EditText) view.findViewById(R.id.name);
        cniPassager = (EditText) view.findViewById(R.id.cni_number);
        numeroTelephonePassager = (EditText) view.findViewById(R.id.phone_number);
        emailPassager = (EditText) view.findViewById(R.id.tv_email);

        bagages = (Spinner) view.findViewById(R.id.bagages);
        adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, nbreBagages);
        bagages.setAdapter(adapter);

        buttonCancel = (Button) view.findViewById(R.id.button_cancel_passenger_infos);
        buttonValidate = (Button) view.findViewById(R.id.button_ok_passenger_infos);



        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        buttonValidate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dataUtils.nomPassager = nomPassager.getText().toString();
                dataUtils.cniPassager = cniPassager.getText().toString();
                dataUtils.numeroTelephonePassager = numeroTelephonePassager.getText().toString();
                dataUtils.emailPassager = emailPassager.getText().toString();
                dataUtils.nombreBagages = bagages.getSelectedItem().toString();

                Bundle bundle=new Bundle();
                bundle.putString("typeVoyage", dataUtils.typeVoyage);
                if(dataUtils.typeVoyage.equalsIgnoreCase(getResources().getString(R.string.one_way_travel).toUpperCase())){

                } else if(dataUtils.typeVoyage.equalsIgnoreCase(getResources().getString(R.string.two_way_travel).toUpperCase())){
                    bundle.putString("dateRetour", dataUtils.dateRetour);
                    bundle.putString("heureRetour", dataUtils.heureRetour);
                }
                bundle.putString("classeVoyage", dataUtils.classeVoyage);
                bundle.putString("villeDepart", dataUtils.villeDepart);
                bundle.putString("villeDestination", dataUtils.villeDestination);
                bundle.putString("dateDepart", dataUtils.dateDepart);
                bundle.putString("heureDepart", dataUtils.heureDepart);
                bundle.putString("prix", dataUtils.prix);
                bundle.putString("nomPassager", dataUtils.nomPassager);
                bundle.putString("cniPassager", dataUtils.cniPassager);
                bundle.putString("numeroTelephonePassager", dataUtils.numeroTelephonePassager);
                bundle.putString("emailPassager", dataUtils.emailPassager);
                bundle.putString("nombreBagages", dataUtils.nombreBagages);

                //Facturer, imprimer et retourner à l'écran principal
                newContent = new ConfirmOperationFragment();
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
     * Method to make json object request where json response starts wtih {
     * */
    private void getPriceJsonObjectRequest(String url) {

        showpDialog();

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                url, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, response.toString());
                try {
                    // Parsing json array response
                    // loop through each json object
                    jsonResponse = "";
                    dataUtils.prix = response.getString("mon_montant");

                    prix.setText(dataUtils.prix+" "+getResources().getString(R.string.fcfa));
                    Toast.makeText(getContext(),
                            "Price: " + dataUtils.prix,
                            Toast.LENGTH_LONG).show();
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
                // hide the progress dialog
                hidepDialog();
            }
        });

        // Adding request to request queue
        JsonController.getInstance().addToRequestQueue(jsonObjReq);
    }

    /**
     * Method to make json array request where response starts with [
     * */
    private String getPriceJsonArrayRequest(String url) {

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
                            //villes = new ArrayList<String>();
                            for (int i = 0; i < response.length(); i++) {

                                JSONObject prices = (JSONObject) response.get(i);

                                dataUtils.prix = prices.getString("mon_montant");
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
        return dataUtils.prix;
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
