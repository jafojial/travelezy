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
import cm.intelso.dev.travelezi.dto.StopItem;

public class StopListAdapter extends RecyclerView.Adapter<StopListAdapter.ViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(StopItem item);
    }

    // This is a template - give a direct reference to each view
    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView tvCode;
        public TextView tvName;
        public TextView tvLines;

        // Constructor - accepts entire row item
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            // Find each view by id you set up in the list_item.xml
            tvCode = (TextView) itemView.findViewById(R.id.tv_stop_code);
            tvName = (TextView) itemView.findViewById(R.id.tv_departure);
            tvLines = (TextView) itemView.findViewById(R.id.tv_line_stops);
        }

        public void bind(final StopItem stopItem, final StopListAdapter.OnItemClickListener listener) {
            // Setting views with the corresponding data
            tvCode.setText(stopItem.getCode());
            tvName.setText(stopItem.getName());
            tvLines.setText(context.getResources().getText(R.string.lines )+ ": " + stopItem.getLines());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    listener.onItemClick(stopItem);
                }
            });
        }
    }

    ArrayList<StopItem> listStop;
    private final Context context;
    private final OnItemClickListener listener;

    // Constructor
    public StopListAdapter(Context aContext, ArrayList<StopItem> listStop, OnItemClickListener listener){
        this.context = aContext;
        this.listStop = listStop;
        this.listener = listener;
    }

    // Creating a viewHolder
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the layout
        View contactView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_stop_item_layout, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView);

        return viewHolder;
    }

    // Assigning respective data for the views based on the position of the current item
    @Override
    public void onBindViewHolder(@NonNull StopListAdapter.ViewHolder holder, int position) {
        holder.bind(this.listStop.get(position), listener);

    }

    // Indicating how long your data is
    @Override
    public int getItemCount() {
        return listStop.size();
    }

}
