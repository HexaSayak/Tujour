package com.mit.tujour.HelperClasses.HomeAdapter;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ImageView;
import android.view.View;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.mit.tujour.R;

import java.util.ArrayList;

public class MostViewAdapter extends RecyclerView.Adapter<MostViewAdapter.MostViewedViewHolder> {

    ArrayList<MostViewedHelperClass> mostViewedLocations;
    public MostViewAdapter(ArrayList<MostViewedHelperClass> mostViewedLocations) {
        this.mostViewedLocations = mostViewedLocations;
}
    @NonNull
    @Override
    public MostViewedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.most_viewed_card_design, parent, false);
        MostViewedViewHolder mostViewedViewHolder = new MostViewedViewHolder(view);
        return mostViewedViewHolder;
    }
    @Override
    public void onBindViewHolder(@NonNull MostViewedViewHolder holder, int position) {
        MostViewedHelperClass helperClass = mostViewedLocations.get(position);
        holder.imageView.setImageResource(helperClass.getImageView());
        holder.textView.setText(helperClass.getTextView());
        holder.desc.setText(helperClass.getDescription());
        holder.relativeLayout.setBackground(helperClass.getGradient());
    }
    @Override
    public int getItemCount() {
        return mostViewedLocations.size();
    }
    public static class MostViewedViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;
        TextView desc;
        RelativeLayout relativeLayout;
        public MostViewedViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.mv_image);
            textView = itemView.findViewById(R.id.mv_title);
            desc=itemView.findViewById(R.id.mv_desc);
            relativeLayout=itemView.findViewById(R.id.gradiant_color_mv);
        }
    }
}