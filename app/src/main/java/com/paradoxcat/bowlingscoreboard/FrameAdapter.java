package com.paradoxcat.bowlingscoreboard;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;


public class FrameAdapter extends RecyclerView.Adapter<FrameAdapter.MyViewHolder> {

    private List<FrameView> list;

    public FrameAdapter(List<FrameView> list) {

        this.list = list;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.single_frame_view, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        FrameView frameView = list.get(position);
        holder.pinsTv.setText(frameView.getPins());
        holder.totalTv.setText(frameView.getTotal());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView totalTv, pinsTv;


        public MyViewHolder(View itemView) {
            super(itemView);
            totalTv = itemView.findViewById(R.id.totalTv);
            pinsTv = itemView.findViewById(R.id.pinsTv);

        }
    }
}
