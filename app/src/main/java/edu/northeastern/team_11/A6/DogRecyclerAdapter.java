package edu.northeastern.team_11.A6;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import edu.northeastern.team_11.R;

public class DogRecyclerAdapter extends RecyclerView.Adapter<DogRecyclerAdapter.ViewHolder> {

    private List<DogCard> dogCardList;
    private final Context context;
    private List<ViewHolder> viewHolders = new ArrayList<>();

    public DogRecyclerAdapter(List<DogCard> dogCardList, Context context) {
        this.dogCardList = dogCardList;
        this.context = context;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.dog_list_item, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        viewHolders.add(holder);
        DogCard dog = dogCardList.get(position);

        holder.breeds.setText(dog.getName());
        holder.height.setText("Height:\n"+ dog.getHeight() + " inches");
        holder.weight.setText("Weight:\n" + dog.getWeight() + " lbs");
        String path = dog.getUrl();
        Picasso.get().load(path).into(holder.image);
        holder.lifeSpan.setText("Lifespan:\n" + dog.getLifeSpan());
        holder.temperament.setText(dog.getTemperament());

        if(!Objects.equals(dog.getBreedGroup(), "")){
            holder.breedGroup.setText("Breed Group:\n"+ dog.getBreedGroup());
        }

        if(!Objects.equals(dog.getBredFor(), "")){
            holder.bredFor.setText("Bred For:\n " + dog.getBredFor());
        }



        holder.image.setId(Integer.parseInt(dog.getId()));
    }

    @Override
    public int getItemCount() {
        return dogCardList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView breeds;
        TextView height;
        TextView weight;
        ImageView image;
        TextView lifeSpan;
        TextView temperament;
        TextView bredFor;
        TextView breedGroup;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            this.breeds = itemView.findViewById(R.id.dogBreed);
            this.height = itemView.findViewById(R.id.dogHeight);
            this.weight = itemView.findViewById(R.id.dogWeight);
            this.image = itemView.findViewById(R.id.dogImage);
            this.lifeSpan = itemView.findViewById(R.id.dogLifeSpan);
            this.temperament = itemView.findViewById(R.id.dogTemperament);
            this.bredFor = itemView.findViewById(R.id.dogBredFor);
            this.breedGroup = itemView.findViewById(R.id.dogBreedGroup);
        }
    }
}
