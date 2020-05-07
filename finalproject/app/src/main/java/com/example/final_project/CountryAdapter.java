package com.example.final_project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CountryAdapter extends RecyclerView.Adapter<CountryAdapter.ViewHolder> {

    private Context context;
    private List<Country> list;

    public CountryAdapter(Context context, List<Country> list){
        this.context = context;
        this.list = list;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.countryitems, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Country country = list.get(position);

        holder.textCountry.setText(country.getCountry());
        holder.textInfected.setText(country.getInfected());
        holder.textDeaths.setText(country.getDeaths());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView textCountry, textInfected, textDeaths;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textCountry = itemView.findViewById(R.id.country);
            textInfected = itemView.findViewById(R.id.ginfected);
            textDeaths = itemView.findViewById(R.id.gdeaths);
        }
    }
}
