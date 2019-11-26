package org.northcoder.luceneanalyzertester.utils;

import java.math.BigDecimal;

/**
 *
 */
public class MatchedText {

    private final String rawText;
    private final String highlightedText;
    private final BigDecimal score;

    MatchedText(String rawText, String highlightedText, BigDecimal score) {
        this.rawText = rawText;
        this.highlightedText = highlightedText;
        this.score = score;
    }

    public String getRawText() {
        return rawText;
    }

    public String getHighlightedText() {
        return highlightedText;
    }

    public BigDecimal getScore() {
        return score;
    }

}
