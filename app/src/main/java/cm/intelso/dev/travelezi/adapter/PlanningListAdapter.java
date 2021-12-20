package cm.intelso.dev.travelezi.adapter;

import android.content.Context;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import cm.intelso.dev.travelezi.R;
import cm.intelso.dev.travelezi.dto.PlanningItem;

public class PlanningListAdapter extends RecyclerView.Adapter<PlanningListAdapter.ViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(PlanningItem item);
    }

    // This is a template - give a direct reference to each view
    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView tvDate;
        public TextView tvPeriod;
        public TextView tvLine;
        public TextView tvName;
        public TextView tvBus;
        public Button btnStartEnd;

        // Constructor - accepts entire row item
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            // Find each view by id you set up in the list_item.xml
            tvLine = (TextView) itemView.findViewById(R.id.tv_line_code);
            tvName = (TextView) itemView.findViewById(R.id.tv_line_name);
            tvBus = (TextView) itemView.findViewById(R.id.tv_bus);
            tvDate = (TextView) itemView.findViewById(R.id.tv_planning_date);
            tvPeriod = (TextView) itemView.findViewById(R.id.tv_planning_period);
            btnStartEnd = (Button) itemView.findViewById(R.id.btn_planning_action);
        }

        public void bind(final PlanningItem planningItem, final OnItemClickListener listener) {
            // Setting views with the corresponding data
            DateFormat df = new DateFormat();
//            btnStartEnd.setText(planningItem.getCode());
            tvLine.setText(planningItem.getLineNumber() + " (" + planningItem.getLineCode() + ")");
            tvName.setText(planningItem.getLineName());
            tvBus.setText(context.getResources().getText(R.string.bus )+ " " + planningItem.getBusImmat());
            tvDate.setText(DateFormat.format("dd-MM-yyyy", planningItem.getDate()));
            tvPeriod.setText(DateFormat.format("kk:mm", planningItem.getStartHour()) + " - " + DateFormat.format("kk:mm", planningItem.getEndHour()));
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    listener.onItemClick(planningItem);
                }
            });
        }
    }

    ArrayList<PlanningItem> planningList;
    private final Context context;
    private final OnItemClickListener listener;

    // Constructor
    public PlanningListAdapter(Context aContext, ArrayList<PlanningItem> planningList, OnItemClickListener listener){
        this.context = aContext;
        this.planningList = planningList;
        this.listener = listener;
    }

    // Creating a viewHolder
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the layout
        View contactView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_planning_item_layout, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView);

        return viewHolder;
    }

    // Assigning respective data for the views based on the position of the current item
    @Override
    public void onBindViewHolder(@NonNull PlanningListAdapter.ViewHolder holder, int position) {
        holder.bind(this.planningList.get(position), listener);
    }

    // Indicating how long your data is
    @Override
    public int getItemCount() {
        return planningList.size();
    }

}
