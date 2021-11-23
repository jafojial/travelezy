package cm.intelso.dev.travelezi;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.nbbse.mobiprint3.Printer;

import java.util.Date;

import cm.intelso.dev.travelezi.R;
import cm.intelso.dev.travelezi.utils.DataUtils;
import cm.intelso.dev.travelezi.utils.Justprint;
import justtide.ThermalPrinter;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ConfirmOperationFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ConfirmOperationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ConfirmOperationFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Printer print;
    Justprint justprint = new Justprint();

    private String stateString = "";
    private String ticketString = "";
    private byte[] buffer;
    private int ilevel = 6;

    Button buttonValidate, buttonCancel;
    TextView infosOperation;
    Fragment newContent;
    DataUtils dataUtils = new DataUtils();

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ConfirmOperationFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ConfirmOperationFragment newInstance(String param1, String param2) {
        ConfirmOperationFragment fragment = new ConfirmOperationFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public ConfirmOperationFragment() {
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
        if (getArguments() != null && getArguments().containsKey("typeVoyage")){
            dataUtils.typeVoyage=getArguments().getString("typeVoyage");
            if(dataUtils.typeVoyage.equalsIgnoreCase("Aller et Retour")){
                if (getArguments() != null && getArguments().containsKey("dateRetour"))
                    dataUtils.dateRetour=getArguments().getString("dateRetour");
                if (getArguments() != null && getArguments().containsKey("heureRetour"))
                    dataUtils.heureRetour=getArguments().getString("heureRetour");
            }
        }
        if (getArguments() != null && getArguments().containsKey("classeVoyage"))
            dataUtils.classeVoyage=getArguments().getString("classeVoyage");
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
        if (getArguments() != null && getArguments().containsKey("prix"))
            dataUtils.prix=getArguments().getString("prix");
        if (getArguments() != null && getArguments().containsKey("nomPassager"))
            dataUtils.nomPassager=getArguments().getString("nomPassager");
        if (getArguments() != null && getArguments().containsKey("cniPassager"))
            dataUtils.cniPassager=getArguments().getString("cniPassager");
        if (getArguments() != null && getArguments().containsKey("numeroTelephonePassager"))
            dataUtils.numeroTelephonePassager=getArguments().getString("numeroTelephonePassager");
        if (getArguments() != null && getArguments().containsKey("emailPassager"))
            dataUtils.emailPassager=getArguments().getString("emailPassager");
        if (getArguments() != null && getArguments().containsKey("nombreBagages"))
            dataUtils.nombreBagages=getArguments().getString("nombreBagages");

        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_confirm_operation, container, false);
        final View view = inflater.inflate(R.layout.fragment_confirm_operation, container, false);

        buffer = new byte[48];
        print = Printer.getInstance();

        infosOperation = (TextView) view.findViewById(R.id.tv_infos_operation);

        String ticket = infosOperation.getText().toString()+
                "\n\nNom du Passager : "+dataUtils.nomPassager+
                "\nN° CNI : "+dataUtils.cniPassager+
                "\nTrajet : "+dataUtils.villeDepart+" - "+dataUtils.villeDestination+
                "\nVoyage : "+dataUtils.typeVoyage+
                "\nDate Départ : "+dataUtils.dateDepart+
                "\nHeure Départ : "+dataUtils.heureDepart;
        if(dataUtils.typeVoyage.equalsIgnoreCase(getResources().getString(R.string.two_way_travel).toUpperCase())){
            ticket+="\nDate Retour : "+dataUtils.dateRetour+
                    "\nHeure Retour : "+dataUtils.heureRetour;
        }
        ticket+="\nContact : "+dataUtils.numeroTelephonePassager+
                "\nMontant : "+dataUtils.prix+" "+getResources().getString(R.string.fcfa);

        infosOperation.setText(ticket);
        buttonCancel = (Button) view.findViewById(R.id.button_cancel_confirm_operation);
        buttonValidate = (Button) view.findViewById(R.id.button_ok_confirm_operation);

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        buttonValidate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Facturer, imprimer et retourner à l'écran principal
                /*
                String ticket = infosOperation.getText().toString()+
                        "\n\nNom du Passager : "+dataUtils.nomPassager+
                        "\nN° CNI : "+dataUtils.cniPassager+
                        "\nTrajet : "+dataUtils.villeDepart+" - "+dataUtils.villeDestination+
                        "\nVoyage : "+dataUtils.typeVoyage+
                        "\nDate Départ : "+dataUtils.dateDepart+
                        "\nHeure Départ :"+dataUtils.heureDepart;
                if(dataUtils.typeVoyage.equalsIgnoreCase("Aller et Retour")){
                    ticket+="\nDate Retour : "+dataUtils.dateRetour+
                            "\nHeure Retour : "+dataUtils.heureRetour;
                }
                ticket+="\nContact : "+dataUtils.numeroTelephonePassager+
                        "\nMontant : "+dataUtils.prix+" "+getResources().getString(R.string.fcfa);
*/
                Log.e("Mobiprint status = ", String.valueOf(print.getPrinterStatus()));
                Log.e("S1000 printer status = ", String.valueOf(print.getPrinterStatus()));
                if(print.getPrinterStatus() == 1){
                    Log.e("Into Mobiprint function", " : OK");
                    //InputStream is = getResources().openRawResource(R.raw.print);
                    print.printBitmap(getResources().openRawResource(R.raw.payway));
                    Log.e("After printBitmap", " : OK");
                    print.printText("\n");
                    //print.printText(tvSimInfoTitle.getText().toString(), 1);
                    print.printText("DATE : " + new Date()+"\n");
                    Log.e("After printText DATE", " : OK");
                    print.printText(infosOperation.getText().toString());
                    print.printEndLine();
                    Log.e("End of ticket printing", " : OK");
/*
                    newContent = new TravelTypeFragment();
                    FragmentManager fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.app_content_frame, newContent).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                            .addToBackStack(null).commit();
                    */
                } else if(justprint.isJustprinter()) {
                    ThermalPrinter thermalPrinter = ThermalPrinter.getInstance();
                    if (thermalPrinter.getState() == 0) {
                        Resources res= getResources();
                        //Bitmap bitmap1 =  BitmapFactory.decodeResource(res, R.drawable.justtide1);//getResources().openRawResource(R.raw.payway)
                        Bitmap bitmap1 =  BitmapFactory.decodeResource(res, R.raw.payway);
                        int result = justprint.printTicket(bitmap1, thermalPrinter, infosOperation.getText().toString());
                        if (result == 0) {
                            stateString = "Print success!";
                        } else if (result == ThermalPrinter.NO_PAPER) {
                            //stateString = getResources().getString(R.string.printdemo_show);
                            stateString = "No paper!";
                        } else if (result == ThermalPrinter.OVER_TEMPERATURE) {
                            stateString = "Over temperature!";
                        } else if (result == ThermalPrinter.OVER_VOLTAGE) {
                            stateString = "Over power!";
                        } else if (result == ThermalPrinter.WORK_CANCEL) {
                            stateString = "Print cancel!";
                        }
                    }
                } else {
                    Toast.makeText(getContext(), "There's not thermal printer available", Toast.LENGTH_LONG).show();
                }

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

}
