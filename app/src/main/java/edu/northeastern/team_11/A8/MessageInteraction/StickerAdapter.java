package edu.northeastern.team_11.A8.MessageInteraction;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Objects;

import edu.northeastern.team_11.R;

public class StickerAdapter extends RecyclerView.Adapter<StickerAdapter.ViewHolder> {

    private final List<Sticker> stickerList;
    private final Context context;

    public StickerAdapter(List<Sticker> stickerList, Context context) {
        this.context = context;
        this.stickerList = stickerList;
    }

    @NonNull
    @Override
    public StickerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.firebase_sticker_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Sticker sticker = stickerList.get(position);
        ImageView goldStar = holder.itemView.findViewById(R.id.goldStar);
        ImageView platinumStar = holder.itemView.findViewById(R.id.platinumStar);
        holder.sticker.setTag(sticker.getPath());
//        holder.sticker.setTag(holder.getAdapterPosition());

        if(sticker.isSelected()){
            holder.sticker.setBackgroundColor(Color.parseColor("#00fff2"));
        } else {
            holder.sticker.setBackgroundColor(Color.parseColor("#FFFFFF"));
        }

        String path = sticker.getDirectory() + sticker.getPath();
        if(Objects.equals(sticker.getCategory(), "gold")){
            goldStar.setVisibility(View.VISIBLE);
        } else if (Objects.equals(sticker.getCategory(), "platinum")){
            platinumStar.setVisibility(View.VISIBLE);
        } else {
            goldStar.setVisibility(View.INVISIBLE);
            platinumStar.setVisibility(View.INVISIBLE);
        }

        Picasso.get().load(path).into(holder.sticker);

    }

    @Override
    public int getItemCount() {
        return stickerList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView sticker;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.sticker = itemView.findViewById(R.id.stickerListImage);
        }
    }
}
