package com.example.rent;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RentListAdapter extends RecyclerView.Adapter<RentListAdapter.viewHolder> {
    List<Rent> rentList;
    MainActivity context;
    public RentListAdapter(List<Rent> rentList){
        this.rentList = rentList;
    }

    public class viewHolder extends RecyclerView.ViewHolder{
        TextView place, day, timebefore, timeafter, reason, group, en_time;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            place = itemView.findViewById(R.id.rent_place);
            day = itemView.findViewById(R.id.day);
            timebefore = itemView.findViewById(R.id.timebefore);
            timeafter = itemView.findViewById(R.id.timeafter);
            reason = itemView.findViewById(R.id.reason);
            group = itemView.findViewById(R.id.which_group);
            en_time = itemView.findViewById(R.id.tv_time);
        }
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_rent, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RentListAdapter.viewHolder holder, int position) {
        Rent item =rentList.get(position);

        holder.place.setText(item.getPlace());
        holder.en_time.setText(item.getEn_time());
        holder.day.setText(item.getDay());
        holder.timebefore.setText(item.getTimebefore());
        holder.timeafter.setText(item.getTimeafter());
        holder.reason.setText(item.getReason());
        holder.group.setText(item.getGroup());
    }

    @Override
    public int getItemCount() {
        return rentList.size();
    }
}
