package cm.intelso.dev.travelezi;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import cm.intelso.dev.travelezi.adapter.PlanningListAdapter;
import cm.intelso.dev.travelezi.apiclient.RetrofitClient;
import cm.intelso.dev.travelezi.data.model.Planning;
import cm.intelso.dev.travelezi.data.model.SharedPrefs;
import cm.intelso.dev.travelezi.dto.PlanningItem;
import cm.intelso.dev.travelezi.utils.DataUtils;
import retrofit2.Call;
import retrofit2.Callback;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PlanningFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PlanningFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 * This is the main (home) screen displayed with the user preferred
 * lines and stop and with the possibility to find more
 * There is also the near stop based on the user location
 */
public class PlanningFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private SharedPrefs settings;
    private String token;

    RecyclerView listPlanning;
    Fragment newContent;

    PlanningListAdapter planningAdapter;
    ArrayList<PlanningItem> planningsDetails;

    // Progress dialog
    private ProgressDialog pDialog;
    private static String TAG = PlanningFragment.class.getSimpleName();
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
    public static PlanningFragment newInstance(String param1, String param2) {
        PlanningFragment fragment = new PlanningFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public PlanningFragment() {
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
        final View view = inflater.inflate(R.layout.fragment_planning, container, false);
        //getDialog().setTitle(getResources().getString(R.string.add_account_title));

        pDialog = DataUtils.createProgressDialog(getContext(), getContext().getString(R.string.wait), Boolean.FALSE);
        settings = new SharedPrefs();
        token = settings.getStringValue(getContext(), settings.PREFS_USER_TOKEN_KEY);

        listPlanning = (RecyclerView) view.findViewById(R.id.planning_list);


        planningsDetails = new ArrayList<>();
        populatePlanningsList(planningsDetails);

        getListPlannings();

        return view;
    }

    private  ArrayList<PlanningItem> getListPlannings() {
        DataUtils.showProgressDialog(pDialog);
        ArrayList<PlanningItem> list = new ArrayList<PlanningItem>();

        Call<List<Planning>> call = RetrofitClient.getInstance(token).getMyApi().findPlannings(null, null, null, null, null, null,
                null, null, null, null, null, null, null, null, DataUtils.getConnectedEmail(token));
        call.enqueue(new Callback<List<Planning>>() {
            @SuppressLint("SimpleDateFormat")
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(Call<List<Planning>> call, retrofit2.Response<List<Planning>> response) {

                Log.i(TAG, "URL : " + call.request().url().toString());
                List<Planning> plannings = response.body();
                if (plannings != null && !plannings.isEmpty()) {
                    Log.i(TAG, "PLANNING SIZE : " + plannings.size());

                    for (Planning planning : plannings) {
                        Log.i(TAG, "PLANNING DATE : " + planning.getDate());
                        Log.i(TAG, "PLANNING START : " + planning.getStartAt());
                        Log.i(TAG, "PLANNING END : " + planning.getEndAt());
                        // String code, Date date, Date startHour, Date endHour, String status, String driver, String bus, String lineCode, String lineNumber, String lineName
                        try {
                            list.add(new PlanningItem(planning.getUuid(),
                                    new SimpleDateFormat("dd-MM-yyyy").parse(planning.getDate()),
                                    new SimpleDateFormat("dd-MM-yyyy kk:mm").parse(planning.getStartAt()),
                                    new SimpleDateFormat("dd-MM-yyyy kk:mm").parse(planning.getEndAt()), planning.getStatus(),
                                    planning.getDriver().getUuid(), planning.getBus().getUuid(), planning.getBus().getImmatriculation(),
                                    planning.getLine().getCode(), planning.getLine().getNumber(), planning.getLine().getName(),
                                    planning.getLine().getStartPoint().getUuid(), planning.getLine().getEndPoint().getUuid()));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                    populatePlanningsList(list);
                }
                else {
                    // sign-in failed
                    Toast.makeText(getContext(), "No planning found!!", Toast.LENGTH_LONG).show();
                }
                // hide the progress bar
                DataUtils.hideProgressDialog(pDialog);

            }

            @Override
            public void onFailure(Call<List<Planning>> call, Throwable throwable) {
                Log.i(TAG, throwable.getMessage());
                Toast.makeText(getContext(), "An error has occured when fetch url " + call.request().url() + ": " + throwable.getMessage(), Toast.LENGTH_LONG).show();
                // hide the progress bar
                DataUtils.hideProgressDialog(pDialog);
            }

        });

        return list;
    }

    private void populatePlanningsList(ArrayList<PlanningItem> planningsDetails) {
        planningAdapter = new PlanningListAdapter(this.getContext(), planningsDetails, new PlanningListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(PlanningItem item) {
                Toast.makeText(PlanningFragment.this.getContext(), "Planning Item " + item.getCode() + " started at " + item.getStartHour() + " and ended at " +
                        item.getEndHour() + " the " + item.getDate() + " with points " + item.getLineStart() + " and " + item.getLineEnd() + " Clicked", Toast.LENGTH_LONG).show();
                // Switch to PlanningStart fragment
                newContent = new PlanningStartsFragment();
                Bundle bundle=new Bundle();
                bundle.putString("planning", item.getCode());
                bundle.putString("startPoint", item.getLineStart());
                bundle.putString("endPoint", item.getLineEnd());
                newContent.setArguments(bundle);
                if(newContent != null){
                    FragmentManager fragmentManager = getChildFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.app_content_frame, newContent).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).addToBackStack(null).commit();
                    fragmentManager.executePendingTransactions();
                }
                else{
                    Log.i(TAG, "Unable to load PlanningStartsFragment");
                }
            }
        });
        // Set adapter with RecyclerView
        listPlanning.setAdapter(planningAdapter);
        // Set LayoutManager
        listPlanning.setLayoutManager(new LinearLayoutManager(this.getContext(), RecyclerView.VERTICAL, false));

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
