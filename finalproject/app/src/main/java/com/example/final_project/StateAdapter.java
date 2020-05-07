package com.example.final_project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class StateAdapter extends RecyclerView.Adapter<StateAdapter.ViewHolder> {

    private Context context;
    private List<State> list;

    public StateAdapter(Context context, List<State> list){
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.stateitems, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        State state = list.get(position);


        holder.textState.setText(state.getState());
        holder.textInfected.setText(state.getInfected());
        holder.textDeaths.setText(state.getDeaths());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView textState, textInfected, textDeaths;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textState = itemView.findViewById(R.id.state);
            textInfected = itemView.findViewById(R.id.infected);
            textDeaths = itemView.findViewById(R.id.deaths);
        }
    }


}
