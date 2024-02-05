package com.github.jon7even.service;

public interface ReplyMessageService {
    String getReplyText(String replyText);
    String getReplyText(String replyText, Object... args);
}
