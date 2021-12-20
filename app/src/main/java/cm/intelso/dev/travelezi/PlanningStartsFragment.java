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
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
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

import cm.intelso.dev.travelezi.adapter.StartListAdapter;
import cm.intelso.dev.travelezi.apiclient.RetrofitClient;
import cm.intelso.dev.travelezi.data.model.Path;
import cm.intelso.dev.travelezi.data.model.SharedPrefs;
import cm.intelso.dev.travelezi.data.model.Start;
import cm.intelso.dev.travelezi.dto.StartItem;
import cm.intelso.dev.travelezi.utils.DataUtils;
import retrofit2.Call;
import retrofit2.Callback;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PlanningStartsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PlanningStartsFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 * This is the main (home) screen displayed with the user preferred
 * lines and stop and with the possibility to find more
 * There is also the near stop based on the user location
 */
public class PlanningStartsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private SharedPrefs settings;
    private String token, planning, startPoint, endPoint;

    TextView tvPlanning;
    Spinner path;
    Button btnAddStart, btnPlanningMgt;
    RecyclerView listStart;
    Fragment newContent;

    StartListAdapter startAdapter;
    ArrayList<StartItem> startsDetails;

    ArrayAdapter<Path> pathAdapter;
    Path selPath = new Path();
    ArrayList<Path> pathsDetails;

    // Progress dialog
    private ProgressDialog pDialog;
    private static String TAG = PlanningStartsFragment.class.getSimpleName();
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
    public static PlanningStartsFragment newInstance(String param1, String param2) {
        PlanningStartsFragment fragment = new PlanningStartsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public PlanningStartsFragment() {
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
        if (getArguments() != null && getArguments().containsKey("planning"))
            planning = getArguments().getString("planning");
        if (getArguments() != null && getArguments().containsKey("startPoint"))
            startPoint = getArguments().getString("startPoint");
        if (getArguments() != null && getArguments().containsKey("endPoint"))
            endPoint = getArguments().getString("endPoint");
        
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_main, container, false);
        final View view = inflater.inflate(R.layout.fragment_planning_starts, container, false);
        //getDialog().setTitle(getResources().getString(R.string.add_account_title));

        pDialog = DataUtils.createProgressDialog(getContext(), getContext().getString(R.string.wait), Boolean.FALSE);
        settings = new SharedPrefs();
        token = settings.getStringValue(getContext(), settings.PREFS_USER_TOKEN_KEY);

        tvPlanning = (TextView) view.findViewById(R.id.tv_planning_line);
        btnAddStart = (Button) view.findViewById(R.id.btn_choose_line);
        path = (Spinner) view.findViewById(R.id.paths);
        btnAddStart = (Button) view.findViewById(R.id.btn_add_start);
        listStart = (RecyclerView) view.findViewById(R.id.start_list);

        btnPlanningMgt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(pathsDetails != null && !pathsDetails.isEmpty()){
                    Toast.makeText(getContext(), "Ready to start a new ride", Toast.LENGTH_LONG).show();

                }
            }
        });

        pathsDetails = getListPaths();
        populatePathDropDown(pathsDetails);

        // When user select a List-Item.
        this.path.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                onPathItemSelectedHandler(parent, view, position, id);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        startsDetails = new ArrayList<>();
        populateStartsList(startsDetails);

        getListStarts();

        btnAddStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(pathsDetails != null && !pathsDetails.isEmpty()){
                    Toast.makeText(getContext(), "Ready to start a new ride", Toast.LENGTH_LONG).show();

                }
            }
        });

        return view;
    }


    private void onPathItemSelectedHandler(AdapterView<?> adapterView, View view, int position, long id) {
        Adapter adapter = adapterView.getAdapter();
        selPath = (Path) adapter.getItem(position);

        if(!selPath.getUuid().isEmpty()) {
            Log.i(TAG, "SEL PATH : " + selPath.toString());
//            settings.save(getContext(), settings.PREFS_USER_NEXT_STOP_KEY, selPath.toString());
        }

//        Toast.makeText(getContext(), "Selected arrival Path: " + selArrival.getName() ,Toast.LENGTH_SHORT).show();
    }

    private  ArrayList<StartItem> getListStarts() {
        DataUtils.showProgressDialog(pDialog);
        ArrayList<StartItem> list = new ArrayList<StartItem>();

        Call<List<Start>> call = RetrofitClient.getInstance(token).getMyApi().findStarts(null, null, null, null, null, null,
                null, planning, null, null);
        call.enqueue(new Callback<List<Start>>() {
            @SuppressLint("SimpleDateFormat")
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(Call<List<Start>> call, retrofit2.Response<List<Start>> response) {

                Log.i(TAG, "URL : " + call.request().url().toString());
                List<Start> starts = response.body();
                if (starts != null && !starts.isEmpty()) {
                    Log.i(TAG, "START SIZE : " + starts.size());

                    for (Start start : starts) {
                        Log.i(TAG, "START PATH : " + start.getPath());
                        try {
                            list.add(new StartItem(start.getUuid(),
                                    start.getPath().toString(),
                                    new SimpleDateFormat("dd-MM-yyyy").parse(start.getDeparture_at()),
                                    new SimpleDateFormat("dd-MM-yyyy kk:mm").parse(start.getDeparture_at()),
                                    new SimpleDateFormat("dd-MM-yyyy kk:mm").parse(start.getArrival_at()), start.getStatus(),
                                    new SimpleDateFormat("dd-MM-yyyy kk:mm").parse(start.getStartedAt())));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                    populateStartsList(list);
                }
                else {
                    // sign-in failed
                    Toast.makeText(getContext(), "No start found!!", Toast.LENGTH_LONG).show();
                }
                // hide the progress bar
                DataUtils.hideProgressDialog(pDialog);

            }

            @Override
            public void onFailure(Call<List<Start>> call, Throwable throwable) {
                Log.i(TAG, throwable.getMessage());
                Toast.makeText(getContext(), "An error has occured when fetch url " + call.request().url() + ": " + throwable.getMessage(), Toast.LENGTH_LONG).show();
                // hide the progress bar
                DataUtils.hideProgressDialog(pDialog);
            }

        });

        return list;
    }
    
    
    private ArrayList<Path> getListPaths() {
        DataUtils.showProgressDialog(pDialog);

        ArrayList<Path> list = new ArrayList<Path>();

        Call<List<Path>> call = RetrofitClient.getInstance(token).getMyApi().findPaths(null, startPoint, endPoint);
        call.enqueue(new Callback<List<Path>>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(Call<List<Path>> call, retrofit2.Response<List<Path>> response) {

                Log.i(TAG, "URL : " + call.request().url().toString());
                List<Path> paths = response.body();
                if (paths != null && !paths.isEmpty()) {
                    Log.i(TAG, "PATH SIZE : " + paths.size());
                    for (Path path : paths) {
                        list.add(path);
                    }
                }
                else {
                    // Find paths request failed
                    Toast.makeText(getContext(), "Find paths request failed!!", Toast.LENGTH_LONG).show();
                }
                // hide the progress bar
                DataUtils.hideProgressDialog(pDialog);

            }

            @Override
            public void onFailure(Call<List<Path>> call, Throwable throwable) {
                Log.i(TAG, throwable.getMessage());
                Toast.makeText(getContext(), "An error has occured when fetch url " + call.request().url() + ": " + throwable.getMessage(), Toast.LENGTH_LONG).show();
                // hide the progress bar
                DataUtils.hideProgressDialog(pDialog);
            }

        });

        return list;
    }
    

    private void populateStartsList(ArrayList<StartItem> startsDetails) {
        startAdapter = new StartListAdapter(this.getContext(), startsDetails, new StartListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(StartItem item) {
                Toast.makeText(PlanningStartsFragment.this.getContext(), "Start Item " + item.getCode() + " started at " + item.getBeginHour() + " and ended at " +
                        item.getEndHour() + " the " + item.getDate() + " Clicked", Toast.LENGTH_LONG).show();
                // Switch to PlanningStart fragment
                newContent = new BusLineFragment();
                Bundle bundle=new Bundle();
                bundle.putString("lineCode", item.getCode());
                newContent.setArguments(bundle);
                FragmentManager fragmentManager = getChildFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.app_content_frame, newContent).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .addToBackStack(null).commit();
            }
        });
        // Set adapter with RecyclerView
        listStart.setAdapter(startAdapter);
        // Set LayoutManager
        listStart.setLayoutManager(new LinearLayoutManager(this.getContext(), RecyclerView.VERTICAL, false));

    }


    private void populatePathDropDown(ArrayList<Path> arrivalStopsDetails) {
        pathAdapter = new ArrayAdapter<Path>(getContext(),
                android.R.layout.simple_spinner_item,
                pathsDetails);

        pathAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.path.setAdapter(pathAdapter);
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
