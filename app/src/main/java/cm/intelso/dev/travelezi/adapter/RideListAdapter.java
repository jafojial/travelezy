package cm.intelso.dev.travelezi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import cm.intelso.dev.travelezi.R;
import cm.intelso.dev.travelezi.dto.RideItem;

public class RideListAdapter extends RecyclerView.Adapter<RideListAdapter.ViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(RideItem item);
    }

    // This is a template - give a direct reference to each view
    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView tvCode;
        public TextView tvDeparture;
        public TextView tvArrival;
        public TextView tvStops;
        public TextView tvDate;
        public TextView tvDepartureHour;
        public TextView tvArrivalHour;

        // Constructor - accepts entire row item
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            // Find each view by id you set up in the list_item.xml
            tvCode = (TextView) itemView.findViewById(R.id.tv_stop_code);
            tvDeparture = (TextView) itemView.findViewById(R.id.tv_departure);
            tvArrival = (TextView) itemView.findViewById(R.id.tv_arrival);
            tvStops = (TextView) itemView.findViewById(R.id.tv_line_stops);
            tvDate = (TextView) itemView.findViewById(R.id.tv_ride_date);
            tvDepartureHour = (TextView) itemView.findViewById(R.id.tv_ride_dep_hour);
            tvArrivalHour = (TextView) itemView.findViewById(R.id.tv_ride_arr_hour);
        }

        public void bind(final RideItem rideItem, final OnItemClickListener listener) {
            // Setting views with the corresponding data
            tvCode.setText(rideItem.getCode());
            tvDeparture.setText(rideItem.getDeparture());
            tvArrival.setText(rideItem.getArrival());
            tvStops.setText(context.getResources().getText(R.string.stops )+ ": " + rideItem.getStops());
            tvDate.setText(rideItem.getDate());
            tvDepartureHour.setText(rideItem.getDepartureHour());
            tvArrivalHour.setText(rideItem.getArrivalHour());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    listener.onItemClick(rideItem);
                }
            });
        }
    }

    ArrayList<RideItem> rideItem;
    private final Context context;
    private final OnItemClickListener listener;

    // Constructor
    public RideListAdapter(Context aContext, ArrayList<RideItem> rideItem, OnItemClickListener listener){
        this.context = aContext;
        this.rideItem = rideItem;
        this.listener = listener;
    }

    // Creating a viewHolder
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the layout
        View contactView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_ride_item_layout, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView);

        return viewHolder;
    }

    // Assigning respective data for the views based on the position of the current item
    @Override
    public void onBindViewHolder(@NonNull RideListAdapter.ViewHolder holder, int position) {
        holder.bind(this.rideItem.get(position), listener);
    }

    // Indicating how long your data is
    @Override
    public int getItemCount() {
        return rideItem.size();
    }

}
