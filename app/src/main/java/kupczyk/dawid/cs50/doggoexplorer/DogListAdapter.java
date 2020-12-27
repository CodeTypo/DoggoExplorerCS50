package kupczyk.dawid.cs50.doggoexplorer;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DogListAdapter extends RecyclerView.Adapter<DogListAdapter.ViewHolder> implements Filterable {

    private final ArrayList<Dog> dataSet;
    private final ArrayList<Dog> dataSetFull;
    private final onDogListener mOnDogListener;

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

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
               dataSet.clear();
               dataSet.addAll((Collection<? extends Dog>) results.values);
               notifyDataSetChanged();
        }
    };

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView textView;
        onDogListener onDogListener;

        public ViewHolder(@NonNull View itemView,onDogListener onDogListener) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.tvDogName);
            this.onDogListener = onDogListener;
            itemView.setOnClickListener(this);
        }

        public TextView getTextView(){return textView;}

        @Override
        public void onClick(View v) {
            onDogListener.onDogClicked(getAdapterPosition());
        }
    } //end of viewholder

    public DogListAdapter(ArrayList<Dog> dataSet, onDogListener onDogListener){
        this.dataSet = dataSet;
        this.dataSetFull = new ArrayList<>(dataSet);
        this.mOnDogListener = onDogListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_row,parent,false);
        return new ViewHolder(view, mOnDogListener);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.getTextView().setText(dataSet.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public interface onDogListener{
        void onDogClicked(int position);
    }


}
