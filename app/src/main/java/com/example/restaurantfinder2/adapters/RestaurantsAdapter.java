package com.example.restaurantfinder2.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.restaurantfinder2.R;
import com.example.restaurantfinder2.models.Restaurants;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;

import java.util.List;

public class RestaurantsAdapter extends RecyclerView.Adapter<RestaurantsAdapter.ViewHolder> {
    Context mContext;
    List<Restaurants> mRestaurant;

    public RestaurantsAdapter(Context context, List<Restaurants> restaurant) {
        mContext= context;
        mRestaurant = restaurant;
    }

    /**
     *
     */
    @NonNull
    @NotNull
    @Override
    //Usually involves inflating a layout from XML and returning the holder
    public RestaurantsAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View restaurantView = LayoutInflater.from(mContext).inflate(R.layout.item_restaurant, parent, false);
        return new ViewHolder(restaurantView);
    }
    //Involved populating the data into the item through the viewHolders
    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        //Get the restaurant at a postion
        Restaurants restaurant = mRestaurant.get(position);
        //Bind the restaurant data into a viewholder
        try {
            holder.bind(restaurant);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return mRestaurant.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView textViewName;
        TextView textViewAddress;
        TextView textViewCategory;
        TextView textViewDistance;
        TextView textViewPrice;
        TextView textViewPhone;
        ImageView imageView;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.tvName);
            imageView = itemView.findViewById(R.id.imageView);
            textViewPhone = itemView.findViewById(R.id.tvPhone);
            //textViewCategory = itemView.findViewById(R.id.tvCategory);
            textViewAddress = itemView.findViewById(R.id.tvAddress);
            //textViewDistance = itemView.findViewById(R.id.tvDistance);
            //textViewPrice = itemView.findViewById(R.id.tvPrice);
        }

        public void bind(Restaurants restaurant) throws JSONException{
            textViewName.setText(restaurant.getName());
            Glide.with(mContext).load(restaurant.getImageUrl()).into(imageView);
            textViewAddress.setText(restaurant.getLocation().getString("address1"));
            textViewPhone.setText(restaurant.getPhone());
            Log.i("RestaurantsAdapter", restaurant.getName());
        }
    }
}
