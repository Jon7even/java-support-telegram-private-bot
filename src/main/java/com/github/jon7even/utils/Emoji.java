package com.github.jon7even.utils;

import com.vdurmont.emoji.EmojiParser;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum Emoji {
    SMAIL(EmojiParser.parseToUnicode(":blush:"));

    private String emojiCode;

    @Override
    public String toString() {
        return emojiCode;
    }
}
