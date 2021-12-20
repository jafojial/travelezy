package cm.intelso.dev.travelezi;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import cm.intelso.dev.travelezi.data.model.SharedPrefs;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddFavoriteDialogFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddFavoriteDialogFragment extends DialogFragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    Button btnSave, btnCancel;
    public TextView tvLine, tvStart, tvEnd;
    public CheckBox cbLine, cbStart, cbEnd;
    Fragment newContent;

    SharedPrefs settings;
    private static String TAG = AddFavoriteDialogFragment.class.getSimpleName();
    private OnFragmentInteractionListener mListener;

    public AddFavoriteDialogFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddFavoriteDialogFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddFavoriteDialogFragment newInstance(String param1, String param2) {
        AddFavoriteDialogFragment fragment = new AddFavoriteDialogFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    /*@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_add_favorite_dialog, container, false);
        //getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
//        getDialog().setTitle(getResources().getString(R.string.add_fav_title));

        settings = new SharedPrefs();

        tvLine = (TextView) view.findViewById(R.id.tv_line);
        tvStart = (TextView) view.findViewById(R.id.tv_start_stop);
        tvEnd = (TextView) view.findViewById(R.id.tv_end_stop);
        cbLine = (CheckBox) view.findViewById(R.id.cb_line);
        cbStart = (CheckBox) view.findViewById(R.id.cb_start_stop);
        cbEnd = (CheckBox) view.findViewById(R.id.cb_end_stop);
        btnSave = (Button) view.findViewById(R.id.btn_save);
        btnCancel = (Button) view.findViewById(R.id.btn_cancel);

        *//*cbLine.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){

                } else{

                }
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cbLine.isChecked() && (cbLine.isActivated() || cbLine.isClickable())){
                    if (settings.getStringValue(getContext(), settings.PREFS_USER_FAV_LINES_KEY).equals(null) ||
                            settings.getStringValue(getContext(), settings.PREFS_USER_FAV_LINES_KEY).equals("")) {
                        settings.save(getContext(), settings.PREFS_USER_FAV_LINES_KEY, cbLine.getText().toString());
                    } else {
                        settings.getStringValue(getContext(), settings.PREFS_USER_FAV_LINES_KEY).concat("_").concat(cbLine.getText().toString());
                    }
                }
                if(cbStart.isChecked() && (cbStart.isActivated() || cbStart.isClickable())){
                    if (settings.getStringValue(getContext(), settings.PREFS_USER_FAV_STOPS_KEY).equals(null) ||
                            settings.getStringValue(getContext(), settings.PREFS_USER_FAV_STOPS_KEY).equals("")) {
                        settings.save(getContext(), settings.PREFS_USER_FAV_STOPS_KEY, cbStart.getText().toString());
                    } else {
                        settings.getStringValue(getContext(), settings.PREFS_USER_FAV_STOPS_KEY).concat("_").concat(cbStart.getText().toString());
                    }
                }
                if(cbEnd.isChecked() && (cbEnd.isActivated() || cbEnd.isClickable())){
                    if (settings.getStringValue(getContext(), settings.PREFS_USER_FAV_STOPS_KEY).equals(null) ||
                            settings.getStringValue(getContext(), settings.PREFS_USER_FAV_STOPS_KEY).equals("")) {
                        settings.save(getContext(), settings.PREFS_USER_FAV_STOPS_KEY, cbEnd.getText().toString());
                    } else {
                        settings.getStringValue(getContext(), settings.PREFS_USER_FAV_STOPS_KEY).concat("_").concat(cbEnd.getText().toString());
                    }
                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });*//*

        return view;

    }*/


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Title");
        builder.setView(R.layout.fragment_add_favorite_dialog);

        return builder.create();
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