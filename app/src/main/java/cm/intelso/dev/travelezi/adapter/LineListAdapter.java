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
import cm.intelso.dev.travelezi.dto.LineItem;

public class LineListAdapter extends RecyclerView.Adapter<LineListAdapter.ViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(LineItem item);
    }

    // This is a template - give a direct reference to each view
    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView tvCode;
        public TextView tvDeparture;
        public TextView tvArrival;
        public TextView tvStops;

        // Constructor - accepts entire row item
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            // Find each view by id you set up in the list_item.xml
            tvCode = (TextView) itemView.findViewById(R.id.tv_stop_code);
            tvDeparture = (TextView) itemView.findViewById(R.id.tv_departure);
            tvArrival = (TextView) itemView.findViewById(R.id.tv_arrival);
            tvStops = (TextView) itemView.findViewById(R.id.tv_line_stops);
        }

        public void bind(final LineItem lineItem, final OnItemClickListener listener) {
            // Setting views with the corresponding data
            tvCode.setText(lineItem.getCode());
            tvDeparture.setText(lineItem.getDeparture());
            tvArrival.setText(lineItem.getArrival());
            tvStops.setText(context.getResources().getText(R.string.stops )+ ": " + lineItem.getStops());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    listener.onItemClick(lineItem);
                }
            });
        }
    }

    /*private List<LineItem> listLine;
    private LayoutInflater layoutInflater;
    private Context context;*/

    ArrayList<LineItem> listLine;
    private final Context context;
    private final OnItemClickListener listener;

    // Constructor
    public LineListAdapter(Context aContext,  ArrayList<LineItem> listLine, OnItemClickListener listener){
        this.context = aContext;
        this.listLine = listLine;
        this.listener = listener;
    }

    // Creating a viewHolder
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the layout
        View contactView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_line_item_layout, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView);

        return viewHolder;
    }

    // Assigning respective data for the views based on the position of the current item
    @Override
    public void onBindViewHolder(@NonNull LineListAdapter.ViewHolder holder, int position) {
        holder.bind(this.listLine.get(position), listener);
        // Get the Subject based on the current position
        /*LineItem lineItem = this.listLine.get(position, listener);

        // Setting views with the corresponding data
        holder.tvCode.setText(lineItem.getCode());
        holder.tvDeparture.setText(lineItem.getDeparture());
        holder.tvArrival.setText(lineItem.getArrival());
        holder.tvStops.setText(context.getResources().getText(R.string.stops )+ ": " + lineItem.getStops());*/

    }

    // Indicating how long your data is
    @Override
    public int getItemCount() {
        return listLine.size();
    }
/*
    public LineListAdapter(Context aContext,  List<LineItem> listLine) {
        this.context = aContext;
        this.listLine = listLine;
        layoutInflater = LayoutInflater.from(aContext);
    }

    @Override
    public int getCount() {
        return listLine.size();
    }

    @Override
    public Object getItem(int position) {
        return listLine.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.list_line_item_layout, null);
            holder = new ViewHolder();
            holder.tvCode = (TextView) convertView.findViewById(R.id.tv_line_code);
            holder.tvDeparture = (TextView) convertView.findViewById(R.id.tv_line_departure);
            holder.tvArrival = (TextView) convertView.findViewById(R.id.tv_line_arrival);
            holder.tvStops = (TextView) convertView.findViewById(R.id.tv_line_stops);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        LineItem lineItem = this.listLine.get(position);
        holder.tvCode.setText(lineItem.getCode());
        holder.tvDeparture.setText(lineItem.getDeparture());
        holder.tvArrival.setText(lineItem.getArrival());
        holder.tvArrival.setText(context.getResources().getIdentifier("stops", "string", null) + ": " + lineItem.getStops());

        return convertView;
    }

    static class ViewHolder {
        TextView tvCode;
        TextView tvDeparture;
        TextView tvArrival;
        TextView tvStops;
    }*/

}
