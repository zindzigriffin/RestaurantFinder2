package com.example.restaurantfinder2.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.restaurantfinder2.R;
import com.example.restaurantfinder2.models.Recommendations;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;
//This is the recommendations adapter that gets all the data needed for the recommendation fragment and binds it to the view
//An Adapter wraps an existing class with a new interface so that it becomes compatible with the client's interface.
public class RecommendationsAdapter extends RecyclerView.Adapter<RecommendationsAdapter.ViewHolder> {
    List<Recommendations> mRecommendations;
    public Context mContext;

    // Define listener member variable
    private RecommendationsAdapter.OnItemClickListener listener;

    // Define the listener interface
    public interface OnItemClickListener {
        void onItemClick(View itemView, int position);
    }

    // Define the method that allows the parent activity or fragment to define the listener
    public void setOnItemClickListener(RecommendationsAdapter.OnItemClickListener listener) {
        this.listener = (OnItemClickListener) listener;
    }
    @NonNull
    @NotNull
    @Override
    public RecommendationsAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View bookView = inflater.inflate(R.layout.item_recommend, parent, false);

        // Return a new holder instance
        RecommendationsAdapter.ViewHolder viewHolder = new RecommendationsAdapter.ViewHolder(bookView, listener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        // Get the data model based on position
        Recommendations recommendations = mRecommendations.get(position);
        try {
            holder.bind(recommendations);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return mRecommendations.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageViewRecommendImage;
        public TextView textViewRecommendName;
        public TextView textViewRecommendAddress;
        public TextView textViewRecommendCity;
        public TextView textViewPrice;

        public ViewHolder(@NonNull @NotNull View itemView, OnItemClickListener listener) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);
            //find the view by id of each attribute
            imageViewRecommendImage = (ImageView)itemView.findViewById(R.id.ivRecommendImage);
            textViewRecommendName = (TextView)itemView.findViewById(R.id.tvRecommendName);
            textViewRecommendAddress = (TextView)itemView.findViewById(R.id.tvRecommendAddress);
            textViewRecommendCity = (TextView)itemView.findViewById(R.id.tvRecommendCity);
            textViewPrice = (TextView)itemView.findViewById(R.id.tvPrice);

        }
        public void bind (Recommendations recommendations) throws JSONException {
            // Populate data into the template view using the data object
            textViewRecommendName.setText(recommendations.getName());
            textViewRecommendAddress.setText(recommendations.getLocation().getString("address1"));
            textViewRecommendCity.setText(recommendations.getLocation().getString("address1"));
            textViewPrice.setText(recommendations.getPrice());
            Glide.with(mContext).load(recommendations.getImageUrl()).into(imageViewRecommendImage);

        }

    }
    public RecommendationsAdapter(Context context, ArrayList<Recommendations> recommendationsArrayList) {
        mRecommendations = recommendationsArrayList;
        mContext = context;
    }

    // Clean all elements of the recycler
    public void clear() {
        mRecommendations.clear();
        notifyDataSetChanged();
    }

    // Add a list of items -- change to type used
    public void addAll(List<Recommendations> list) {
        mRecommendations.addAll(list);
        notifyDataSetChanged();
    }

}
