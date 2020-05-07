package com.example.final_project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    private Context context;
    private List<News> newsList;


    public Adapter(Context ctx, List<News> news){
        this.context = ctx;
        this.newsList = news;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.listview,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder( ViewHolder holder, int position) {
        News news =  newsList.get(position);

        holder.textTitle.setText(String.valueOf(news.getTitle()));
        holder.textDescription.setText(String.valueOf(news.getDescription()));
        holder.textLink.setText(String.valueOf(news.getLink()));
    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView textTitle, textDescription, textLink;

        public ViewHolder(View itemView) {
            super(itemView);

            textTitle = itemView.findViewById(R.id.title);
            textDescription = itemView.findViewById(R.id.description);
            textLink = itemView.findViewById(R.id.link);
        }
    }
}
