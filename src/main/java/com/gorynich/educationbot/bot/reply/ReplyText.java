package com.gorynich.educationbot.bot.reply;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public class ReplyText extends Reply {
    private String replyText;

    public ReplyText(Long chatID, String replyText) {
        super.chatID = chatID;
        super.sendMessage = new SendMessage();
        super.sendMessage.setChatId(chatID.toString());
        this.replyText = replyText;
    }

    @Override
    public SendMessage sendMsg() {
        super.sendMessage.setText(replyText);
        return super.sendMessage;
    }

    public void setReplyText(String replyText) {
        this.replyText = replyText;
    }
}
