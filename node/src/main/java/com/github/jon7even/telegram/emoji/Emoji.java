package com.github.jon7even.telegram.emoji;

import com.vdurmont.emoji.EmojiParser;
import lombok.AllArgsConstructor;

/**
 * Перечисление смайликов, которые поддерживаются ботом
 *
 * @author Jon7even
 * @version 2.0
 */
@AllArgsConstructor
public enum Emoji {

    /**
     * Улыбка с румянцем
     */
    SMAIL_BLUSH(EmojiParser.parseToUnicode(":blush:")),

    /**
     * Зеленая галочка подтверждено
     */
    HEAVY_CHECK(EmojiParser.parseToUnicode(":heavy_check_mark:")),

    /**
     * Красный крест не подтверждено
     */
    NO_CHECK(EmojiParser.parseToUnicode(":x:")),

    /**
     * Два восклицательных красных знака
     */
    BANG(EmojiParser.parseToUnicode(":bangbang:")),

    /**
     * Магия сияние
     */
    MAGIC(EmojiParser.parseToUnicode(":sparkles:")),

    /**
     * Красный вопрос
     */
    QUESTION(EmojiParser.parseToUnicode(":question:")),

    /**
     * Желтая звезда
     */
    STAR(EmojiParser.parseToUnicode(":star:")),

    /**
     * Сердце
     */
    HEART(EmojiParser.parseToUnicode(":heart:"));

    private final String emojiCode;

    @Override
    public String toString() {
        return emojiCode;
    }
}