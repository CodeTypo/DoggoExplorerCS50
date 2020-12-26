package kupczyk.dawid.cs50.doggoexplorer;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class DogListAdapter extends RecyclerView.Adapter<DogListAdapter.ViewHolder> {

    private ArrayList<Dog> dataSet;
    private onDogListener mOnDogListener;

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
