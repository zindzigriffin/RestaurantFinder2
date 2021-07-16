package com.example.restaurantfinder2.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.restaurantfinder2.R;
import com.example.restaurantfinder2.models.Restaurants;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class RestaurantsAdapter extends RecyclerView.Adapter<RestaurantsAdapter.ViewHolder> {
    Context mContext;
    List<Restaurants> mRestaurant;

    public RestaurantsAdapter(Context context, List<Restaurants> restaurant) {
        mContext= context;
        mRestaurant = restaurant;
    }

    @NonNull
    @NotNull
    @Override
    //Usually involves inflating a layout from XML and returning the holder
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView textViewName;
        TextView textViewNumReviews;
        TextView textViewAddress;
        TextView textViewCategory;
        TextView textViewDistance;
        TextView textViewPrice;
        ImageView imageView;
        RatingBar ratingBar;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.tvName);
            textViewNumReviews = itemView.findViewById(R.id.tvNumReviews);
            imageView = itemView.findViewById(R.id.imageView);
            textViewCategory = itemView.findViewById(R.id.tvCategory);
            textViewAddress = itemView.findViewById(R.id.tvAddress);
            textViewDistance = itemView.findViewById(R.id.tvDistance);
            textViewPrice = itemView.findViewById(R.id.tvPrice);
            ratingBar = itemView.findViewById(R.id.ratingBar);
        }
    }
}
