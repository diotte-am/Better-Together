package edu.northeastern.team_11.BetterTogether.Tabs;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Random;

import edu.northeastern.team_11.R;

public class PostRecyclerAdapter extends RecyclerView.Adapter<PostRecyclerAdapter.ViewHolder> {
    private final List<BTPost> BTPostList;
    private final Context context;

    public PostRecyclerAdapter(List<BTPost> BTPostList, Context context) {
        this.BTPostList = BTPostList;
        this.context = context;
        populateColors();
        // Implement population of BTPostList from database here or in
        // a seperate function. FUnction must randomly assign a color
        // to each post when the adapter is created.


    }

    public void populateColors(){
        for(BTPost post: BTPostList){
            post.setColor();
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.bt_post_item, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //holder.profilePicture.setImageResource(R.drawable.pleasant_reaction);
        holder.username.setText(BTPostList.get(position).getUsername());
        holder.notes.setText(BTPostList.get(position).getMessage());
        holder.date.setText(BTPostList.get(position).getDate());
        holder.activity.setText(BTPostList.get(position).getActivity());
     //   holder.container.setCardBackgroundColor(BTPostList.get(position).getColor());
    //\\    holder.container.setCardBackgroundColor(Integer.parseInt(BTPostList.get(position).getColor(), 16));
    }

    @Override
    public int getItemCount() {
        return BTPostList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView profilePicture;
        TextView username, notes, date, activity;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
          //  profilePicture.findViewById(R.id.bt_item_profile_pic);
            username = itemView.findViewById(R.id.bt_item_person_name);
            notes = itemView.findViewById(R.id.bt_item_notes);
            date = itemView.findViewById(R.id.bt_item_date);
            activity = itemView.findViewById(R.id.bt_item_activity);

        }
    }
}
