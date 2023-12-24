package edu.northeastern.team_11.A8.MessageHistory;

import java.time.LocalDate;

import edu.northeastern.team_11.A8.MessageInteraction.Sticker;
import edu.northeastern.team_11.A8.StickerCategories;

public class FbMessage {

    private String sender;
    private String recipient;
    private final Sticker sticker;
    private final LocalDate date;
    private final String category;

    public FbMessage(LocalDate date, String sender, String recipient, Sticker sticker) {
        this.sender = sender;
        this.recipient = recipient;
        this.sticker = sticker;
        this.date = date;
        this.category = sticker.getCategory();
    }

    public String getSender() {
        return sender;
    }

    public String getRecipient() {
        return recipient;
    }

    public Sticker getSticker() {
        return sticker;
    }

    public String getPath() {
        return sticker.getPath();
    }

    public LocalDate getDate() {
        return date;
    }

    public String getCategory(){return category;}

    @Override
    public String toString() {
        return sticker.getPath();
    }
}
