package com.paradoxcat.bowlingscoreboard;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.List;


public class NumberAdapter extends RecyclerView.Adapter<NumberAdapter.MyViewHolder> {

    private OnNumberClicked onNumberClicked;
    private List<Integer> acceptedNums;

    public NumberAdapter(List<Integer> acceptedNums, OnNumberClicked onNumberClicked) {
        this.onNumberClicked = onNumberClicked;
        this.acceptedNums = acceptedNums;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.single_num_view, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        int number = acceptedNums.get(holder.getAdapterPosition());

        if (number == 10)
            holder.numBtn.setText(String.valueOf("X"));
        else
            holder.numBtn.setText(String.valueOf(number));

        holder.numBtn.setOnClickListener(v -> onNumberClicked.onNumberClicked(number));
    }

    @Override
    public int getItemCount() {
        return acceptedNums.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        Button numBtn;


        MyViewHolder(View itemView) {
            super(itemView);
            numBtn = itemView.findViewById(R.id.button);

        }
    }

    interface OnNumberClicked {
        void onNumberClicked(int num);
    }
}
