package edu.northeastern.team_11.A8.MessageHistory;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import edu.northeastern.team_11.R;

public class FirebaseMessageAdapter extends RecyclerView.Adapter<FirebaseMessageAdapter.ViewHolder> {

    private final List<FbMessage> messageReceivedList;
    private final List<FbMessage> messageSentList;
    private final Context context;

    public FirebaseMessageAdapter(List<FbMessage> messageReceivedList, List<FbMessage> messageSentList, Context context) {
        this.context= context;
        this.messageReceivedList = messageReceivedList;
        this.messageSentList = messageSentList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.firebase_message_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        FbMessage message = messageReceivedList.get(position);

        String stickerPath =  message.getSticker().getDirectory() + message.getPath() + ".png";
        Picasso.get().load(stickerPath).into(holder.sticker);
        holder.sticker.setTag(stickerPath);
        holder.date.setText("Sent on: " + message.getDate().toString());
        holder.sender.setText("Sent By: " + message.getSender());
    }

    @Override
    public int getItemCount() {
        return messageReceivedList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView sticker;
        TextView sender;
        TextView date;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            this.sticker = itemView.findViewById(R.id.fbMessageImage);
            this.date = itemView.findViewById(R.id.fbMessageDate);
            this.sender = itemView.findViewById(R.id.fbMessageSender);

        }
    }
}
