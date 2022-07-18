package edu.neu.firebasechatapp.Model;

public class ChatModel {
    private String sender;
    private String receiver;
    private String stickerId;
    private String stickerName;

    public ChatModel() {
    }

    public ChatModel(String sender, String receiver, String stickerId, String stickerName) {
        this.sender = sender;
        this.receiver = receiver;
        this.stickerId = stickerId;
        this.stickerName = stickerName;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getStickerId() {
        return stickerId;
    }

    public void setStickerId(String stickerId) {
        this.stickerId = stickerId;
    }

    public String getStickerName() {
        return stickerName;
    }

    public void setStickerName(String stickerName) {
        this.stickerName = stickerName;
    }
}
