package com.example.restaurantfinder2.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.restaurantfinder2.R;
import com.example.restaurantfinder2.models.Recipes;

import java.util.ArrayList;
import java.util.List;

public class RecipesAdapter extends RecyclerView.Adapter<RecipesAdapter.ViewHolder> {
    List<Recipes> mRecipes;
    public Context mContext;

    // Define listener member variable
    private OnItemClickListener listener;

    // Define the listener interface
    public interface OnItemClickListener {
        void onItemClick(View itemView, int position);
    }

    // Define the method that allows the parent activity or fragment to define the listener
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    // View lookup cache
    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageViewRecipeImage;
        public TextView textViewFoodName;

        public ViewHolder(final View itemView, final OnItemClickListener clickListener) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            imageViewRecipeImage = (ImageView)itemView.findViewById(R.id.iVRecipeImage);
            textViewFoodName = (TextView)itemView.findViewById(R.id.tvFoodName);

        }

        public void bind(Recipes recipes) {
            // Populate data into the template view using the data object
            textViewFoodName.setText(recipes.getLabel());
            Glide.with(mContext).load(recipes.getImage()).into(imageViewRecipeImage);

        }
    }

    public RecipesAdapter(Context context, ArrayList<Recipes> aRecipes) {
        mRecipes = aRecipes;
        mContext = context;
    }

    // Usually involves inflating a layout from XML and returning the holder
    @Override
    public RecipesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View bookView = inflater.inflate(R.layout.item_recipe, parent, false);

        // Return a new holder instance
        RecipesAdapter.ViewHolder viewHolder = new RecipesAdapter.ViewHolder(bookView, listener);
        return viewHolder;
    }


    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // Get the data model based on position
        Recipes recipes = mRecipes.get(position);
        holder.bind(recipes);

    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return mRecipes.size();
    }

    // Easy access to the context object in the recyclerview
    private Context getContext() {
        return mContext;
    }

}