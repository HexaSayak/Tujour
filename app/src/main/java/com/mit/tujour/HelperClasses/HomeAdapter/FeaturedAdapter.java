package com.mit.tujour.HelperClasses.HomeAdapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.mit.tujour.R;

import java.util.ArrayList;

public class FeaturedAdapter extends RecyclerView.Adapter<FeaturedAdapter.FeaturedViewHolder> {

    private final FeaturedViewInterface featuredViewInterface;

    ArrayList<FeaturedHelperClass> featuredLocations;


    public FeaturedAdapter(ArrayList<FeaturedHelperClass> featuredLocations, FeaturedViewInterface featuredViewInterface) {        //


        this.featuredLocations = featuredLocations;
        this.featuredViewInterface = featuredViewInterface;
    }

    @NonNull
    @Override
    public FeaturedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.featured_card_design,parent,false);
        FeaturedViewHolder featuredViewHolder = new FeaturedViewHolder(view, featuredViewInterface);
        return featuredViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull FeaturedViewHolder holder, int position) {
        FeaturedHelperClass featuredHelperClass = featuredLocations.get(position);

        holder.image.setImageResource(featuredHelperClass.getImage());
        holder.title.setText(featuredHelperClass.getTitle());
        holder.desc.setText(featuredHelperClass.getDescription());

        //holder.desc.setBackground(featuredHelperClass.get);

    }

    @Override
    public int getItemCount() {
        return featuredLocations.size();
    }


    //Design
    public static class FeaturedViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        ImageView image;
        TextView title, desc;

        CardView cardView; //

        public FeaturedViewHolder(@NonNull View itemView, FeaturedViewInterface featuredViewInterface) {
            super(itemView);
            //Hooks
            image = itemView.findViewById(R.id.featured_image);
            title = itemView.findViewById(R.id.featured_title);
            desc = itemView.findViewById(R.id.featured_desc);

            cardView = itemView.findViewById(R.id.featured_container); //
            //cardView.setOnClickListener(this); //
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (featuredViewInterface != null){
                        int pos = getAdapterPosition();

                        if (pos != RecyclerView.NO_POSITION){
                            featuredViewInterface.onFeaturedItemClick(pos);
                        }
                    }
                }
            });
        }

        @Override  //
        public void onClick(View v) {
            int position = this.getAdapterPosition();
            Toast.makeText(cardView.getContext(), "The position is: " + String.valueOf(position), Toast.LENGTH_SHORT).show();
        }
    }
}
