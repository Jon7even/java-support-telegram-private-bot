package com.github.jon7even.service.in.message;

public interface ReplyMessageService {
    String getReplyText(String replyText);
    String getReplyText(String replyText, Object... args);
}
