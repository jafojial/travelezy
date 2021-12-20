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
import cm.intelso.dev.travelezi.dto.StartItem;

public class StartListAdapter extends RecyclerView.Adapter<StartListAdapter.ViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(StartItem item);
    }

    // This is a template - give a direct reference to each view
    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView tvDate;
        public TextView tvPeriod;
        public TextView tvPath;
        public TextView tvName;
        public TextView tvBus;
        public Button btnStartEnd;

        // Constructor - accepts entire row item
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            // Find each view by id you set up in the list_item.xml
            tvPath = (TextView) itemView.findViewById(R.id.tv_path_name);
//            tvName = (TextView) itemView.findViewById(R.id.tv_line_name);
//            tvBus = (TextView) itemView.findViewById(R.id.tv_bus);
            tvDate = (TextView) itemView.findViewById(R.id.tv_start_date);
            tvPeriod = (TextView) itemView.findViewById(R.id.tv_start_period);
            btnStartEnd = (Button) itemView.findViewById(R.id.btn_start_action);
        }

        public void bind(final StartItem startItem, final OnItemClickListener listener) {
            // Setting views with the corresponding data
            DateFormat df = new DateFormat();
            String period = "";
//            btnStartEnd.setText(startItem.getCode());
            tvPath.setText(startItem.getPath());
//            tvName.setText(startItem.getLineName());
//            tvBus.setText(context.getResources().getText(R.string.bus )+ " " + startItem.getBusImmat());
            tvDate.setText(DateFormat.format("dd-MM-yyyy", startItem.getDate()));
            if(startItem.getBeginHour() != null){
                if(startItem.getEndHour() != null){
                    period = DateFormat.format("kk:mm", startItem.getBeginHour()) + " - " + DateFormat.format("kk:mm", startItem.getEndHour());
                } else{
                    period = DateFormat.format("kk:mm", startItem.getBeginHour()) + " - ";
                }
            }
            tvPeriod.setText(period);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    listener.onItemClick(startItem);
                }
            });
        }
    }

    ArrayList<StartItem> planningList;
    private final Context context;
    private final OnItemClickListener listener;

    // Constructor
    public StartListAdapter(Context aContext, ArrayList<StartItem> planningList, OnItemClickListener listener){
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
    public void onBindViewHolder(@NonNull StartListAdapter.ViewHolder holder, int position) {
        holder.bind(this.planningList.get(position), listener);
    }

    // Indicating how long your data is
    @Override
    public int getItemCount() {
        return planningList.size();
    }

}
