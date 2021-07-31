package com.example.restaurantfinder2.adapters;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat;

import com.bumptech.glide.Glide;
import com.example.restaurantfinder2.R;
import com.example.restaurantfinder2.models.Restaurants;
import com.parse.ParseException;
import com.parse.SaveCallback;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;

import java.util.List;
//This is the restaurants adapter class that binds each item to it's recyclerView.
//Makes the view for each item in the data set.
public class RestaurantsAdapter extends RecyclerView.Adapter<RestaurantsAdapter.ViewHolder> {
    Context mContext;
    List<Restaurants> mRestaurant;
    public static final String TAG = "RestaurantsAdapter";


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
        TextView textViewPhone;
        ImageView imageView;
        ImageView imageViewHeart;
        AnimatedVectorDrawableCompat avd;
        AnimatedVectorDrawable avd2;
        //TextView likeCountTextView;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.tvName);
            imageView = itemView.findViewById(R.id.imageView);
            textViewPhone = itemView.findViewById(R.id.tvPhone);
            textViewAddress = itemView.findViewById(R.id.tvAddress);
            imageViewHeart = itemView.findViewById(R.id.ivHeart);

        }
        //Binds the data from the adapter to the recyclerView
        public void bind(Restaurants restaurant) throws JSONException{
            Glide.with(mContext).load(restaurant.getImageUrl()).into(imageView);
            //textViewAddress.setText(restaurant.getLocation().getString("address1"));
            //textViewPhone.setText(restaurant.getPhone());
            textViewName.setText(restaurant.getName());

            Log.i("RestaurantsAdapter", restaurant.getName());
            Drawable drawable = imageViewHeart.getDrawable();
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

//                    Restaurants object = new Restaurants();
//                    object.setParseId(restaurant.getId());
//                    object.setParseImageUrl(restaurant.getImageUrl());
//                    object.setParseId(restaurant.getParseId());
//                    object.setParseImageId(restaurant.getParseImageId());

                    Toast.makeText(mContext, "Restaurant ID" + restaurant.getImageUrl(), Toast.LENGTH_SHORT).show();
                    restaurant.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            if(e==null) {
                                Toast.makeText(mContext, "Object saved!", Toast.LENGTH_SHORT).show();
//                                restaurant.setParseId(restaurant.getId());
//                                o.setParseImageUrl(restaurant.getImageUrl());

                            } else {
                                Log.e("XXX", "Object not saved",e );
                                Toast.makeText(mContext, "Object not saved!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    imageViewHeart.setAlpha(0.70f);
                    if(drawable instanceof AnimatedVectorDrawableCompat){
                        avd = (AnimatedVectorDrawableCompat) drawable;
                        avd.start();


                    }else if(drawable instanceof AnimatedVectorDrawable){
                        avd2 = (AnimatedVectorDrawable) drawable;
                        avd2.start();

                    }
                }
            });
            //implementing portait and landscape mode capabilities
            if(mContext.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
                Glide.with(mContext).load(restaurant.getImageUrl()).into(imageView);
            }
            else {
                Glide.with(mContext).load(restaurant.getImageUrl()).into(imageView);
            }
        }
    }

    // Clean all elements of the recycler
    public void clear() {
        mRestaurant.clear();
        notifyDataSetChanged();
    }

    // Add a list of items -- change to type used
    public void addAll(List<Restaurants> list) {
        mRestaurant.addAll(list);
        notifyDataSetChanged();
    }
}
