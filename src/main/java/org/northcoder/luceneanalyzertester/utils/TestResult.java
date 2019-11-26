package org.northcoder.luceneanalyzertester.utils;

import java.util.List;
import java.util.ArrayList;
import java.math.BigDecimal;
import java.util.Arrays;

/**
 *
 */
public class TestResult {

    public TestResult(String testID,
            List<String> indexedTerms,
            String searchTerm,
            String query) {
        this.testID = testID;
        this.indexedTerms = indexedTerms;
        this.searchTerm = searchTerm;
        this.query = query;
    }

    private final String testID;
    private final List<String> indexedTerms;
    private final String searchTerm;
    private final String query;
    private final List<Match> matches = new ArrayList();

    public String getTestID() {
        return testID;
    }

    public String getIndexedTerms() {
        return Arrays.toString(indexedTerms.toArray());
    }

    public String getSearchTerm() {
        return searchTerm;
    }

    public String getQuery() {
        return query;
    }

    public List<Match> getMatches() {
        return matches;
    }

    public class Match {

        public Match(String match,
                String highlightedMatch,
                BigDecimal score) {
            this.match = match;
            this.highlightedMatch = highlightedMatch;
            this.score = score;
        }

        private final String match;
        private final String highlightedMatch;
        private final BigDecimal score;

        public String getMatch() {
            return match;
        }

        public String getHighlightedMatch() {
            return highlightedMatch;
        }

        public BigDecimal getScore() {
            return score;
        }
    }
    
    public void printResult() {
        System.out.println("");
        System.out.println("Test         : " + getTestID());
        System.out.println("Indexed terms: " + getIndexedTerms());
        System.out.println("Search term  : " + getSearchTerm());
        System.out.println("Query        : " + getQuery());

        if (getMatches().isEmpty()) {
            System.out.println("Match        : [no matches found]");
        } else {
            getMatches().forEach((match) -> {
                System.out.println("Match        : " + match.getMatch() + " -> [score: "
                        + match.getScore() + "]");
                System.out.println("Highlighted  : " + match.getHighlightedMatch());
            });
        }

        System.out.println("");
    }
}
