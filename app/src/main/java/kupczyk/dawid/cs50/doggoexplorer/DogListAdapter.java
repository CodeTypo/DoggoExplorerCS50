package kupczyk.dawid.cs50.doggoexplorer;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * An adapter responsible for populating the RecyclerView with the data obtained from the ArrayList<Dog>
 */
public class DogListAdapter extends RecyclerView.Adapter<DogListAdapter.ViewHolder> implements Filterable {

    private final ArrayList<Dog> dataSet;
    private final ArrayList<Dog> dataSetFull;
    private final onDogListener mOnDogListener;

    public DogListAdapter(ArrayList<Dog> dataSet, onDogListener onDogListener){
        this.dataSet = dataSet;
        this.dataSetFull = new ArrayList<>(dataSet);
        this.mOnDogListener = onDogListener;
    }

    /**
     * @return a Filter with the capability of sorting the dogs regarding to their breed name
     */
    @Override
    public Filter getFilter() {
        return filter;
    }

    Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Dog> filteredList = new ArrayList<>();
            if(constraint.toString().isEmpty()){
                filteredList.addAll(dataSetFull);
            } else {
                for (Dog dog : dataSetFull){
                    if(dog.getName().toLowerCase().contains(constraint)){
                        filteredList.add(dog);
                    }
                }
            }
            FilterResults filterResults = new FilterResults();
            filterResults.values=filteredList;
            return filterResults;
        }

        /**
         * @param constraint
         * @param results
         * A method responsible for publishing the filtering results inside the RecyclerView
         */
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
               dataSet.clear();
               dataSet.addAll((Collection<? extends Dog>) results.values);
               notifyDataSetChanged();
        }
    };

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView textView;
        private final ImageView imageView;
        private final ImageButton favButton;
        onDogListener onDogListener;

        public ViewHolder(@NonNull View itemView,onDogListener onDogListener) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.tvDogName);
            imageView = (ImageView) itemView.findViewById(R.id.tvDogImage);
            favButton = (ImageButton) itemView.findViewById(R.id.favButton);
            this.onDogListener = onDogListener;
            itemView.setOnClickListener(this);
            favButton.setOnClickListener(this);
        }

        public TextView getTextView(){return textView;}
        public ImageView getImageView(){return imageView;}
        public ImageButton getFavButton() {
            return favButton;
        }

        @Override
        public void onClick(View v) {
            onDogListener.onDogClicked(v,getLayoutPosition());
        }
    } //end of viewholder


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_row,parent,false);
        return new ViewHolder(view, mOnDogListener);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String name = dataSet.get(holder.getLayoutPosition()).getName();
        //String name = dataSet.get(position).getName();
        holder.getTextView().setText(name);
        holder.getFavButton().setImageResource(R.drawable.ic_favorite_gray);
        Picasso.get().load(dataSet.get(position).getImageUrl()).into(holder.getImageView());
        if(mOnDogListener.isfavourite(name)){
            holder.getFavButton().setImageResource(R.drawable.ic_favorite_red);
        }
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public interface onDogListener{
        void onDogClicked(View view, int position);
        boolean isfavourite(String name);
    }
}
