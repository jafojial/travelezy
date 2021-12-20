package cm.intelso.dev.travelezi;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.preference.EditTextPreference;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceCategory;
import androidx.preference.PreferenceFragmentCompat;

import cm.intelso.dev.travelezi.utils.DataUtils;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PreferencesFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PreferencesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PreferencesFragment extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private DataUtils dataUtils;
    //SharedPreferences shareprefs = getPreferenceManager().getDefaultSharedPreferences(getActivity().getApplicationContext());

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PreferencesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PreferencesFragment newInstance(String param1, String param2) {
        PreferencesFragment fragment = new PreferencesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public PreferencesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        addPreferencesFromResource(R.xml.pref_settings);
        // show the current value in the settings screen
        for (int i = 0; i < getPreferenceScreen().getPreferenceCount(); i++) {
            initSummary(getPreferenceScreen().getPreference(i));
        }
        //SharedPreferences shareprefs = getPreferenceManager().getSharedPreferences();
        //Resources resources = getActivity().getApplicationContext().getResources();
        ListPreference language_preference = (ListPreference) getPreferenceScreen().findPreference("LANGUAGE");

        language_preference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            SharedPreferences shareprefs = getPreferenceManager().getDefaultSharedPreferences(getActivity().getApplicationContext());
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                ListPreference listPref = (ListPreference) preference;
                Toast.makeText(getActivity().getApplicationContext(), "Something changed on PreferencesFragment", Toast.LENGTH_SHORT).show();
                shareprefs.edit().putString("LANGUAGE", (String) newValue).commit();
                if(newValue.toString().equalsIgnoreCase("en")){
                    Toast.makeText(getActivity().getApplicationContext(), "PreferencesFragment : English language selected", Toast.LENGTH_SHORT).show();
                    dataUtils.setLocale(getActivity().getApplicationContext(), newValue.toString());
                    refreshActivity();
                } else if(newValue.toString().equalsIgnoreCase("fr")){
                    Toast.makeText(getActivity().getApplicationContext(), "PreferencesFragment : French language selected", Toast.LENGTH_SHORT).show();
                    dataUtils.setLocale(getActivity().getApplicationContext(), newValue.toString());
                    refreshActivity();
                } else{
                    Toast.makeText(getActivity().getApplicationContext(), "PreferencesFragment : Default language selected", Toast.LENGTH_SHORT).show();
                    dataUtils.setLocale(getActivity().getApplicationContext(), Resources.getSystem().getConfiguration().locale.toString());
                    refreshActivity();
                }
                return true;
            }
        });
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {

    }

    /*
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            TextView textView = new TextView(getActivity());
            textView.setText(R.string.hello_blank_fragment);
            return textView;
        }
    */
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

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        Toast.makeText(getActivity().getApplicationContext(), "Something changed on PreferencesFragment", Toast.LENGTH_SHORT).show();
        //updatePreferences(findPreference(key));
    }

    private void initSummary(Preference p) {
        if (p instanceof PreferenceCategory) {
            PreferenceCategory cat = (PreferenceCategory) p;
            for (int i = 0; i < cat.getPreferenceCount(); i++) {
                initSummary(cat.getPreference(i));
            }
        } else {
            //updatePreferences(p);
        }
    }

    private void initializeSummary(Preference p)
    {
        if(p instanceof ListPreference) {
            ListPreference listPref = (ListPreference)p;
            p.setSummary(listPref.getEntry());
        }
        if(p instanceof EditTextPreference) {
            EditTextPreference editTextPref = (EditTextPreference)p;
            p.setSummary(editTextPref.getText());
        }
    }

    private void updatePreferences(Preference p) {
        if (p instanceof EditTextPreference) {
            EditTextPreference editTextPref = (EditTextPreference) p;
            p.setSummary(editTextPref.getText());
        }
        if (p instanceof ListPreference) {
            ListPreference listPref = (ListPreference) p;
            p.setSummary(listPref.getEntry());
        }
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

    public void refreshActivity(){
        Intent refresh = new Intent(getActivity().getApplicationContext(), MainActivity.class);
        startActivity(refresh);
        getActivity().finish();
    }

}
