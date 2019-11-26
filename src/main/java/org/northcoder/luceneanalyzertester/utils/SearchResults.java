package org.northcoder.luceneanalyzertester.utils;

import java.util.List;
import java.util.ArrayList;

/**
 * Used to pass search results back to the searcher. The original search term
 * is not included - we assume the searcher has that data.
 */
public class SearchResults {

    private final String query;
    private final List<MatchedText> results;

    /**
     * 
     * @param query a string representation of the query after being parsed
     * using the provided analyzer.
     * @param results a list containing the matches found by the query. 
     */
    public SearchResults(String query, List<MatchedText> results) {
        this.query = query;
        this.results = results;
    }

    public String getQuery() {
        return query;
    }

    public List<MatchedText> getResults() {
        return results;
    }

    public List<String> getRawTextResults() {
        // a list of the uhighlighted text results
        List<String> rawTextResults = new ArrayList();
        results.forEach((textMatch) -> {
            rawTextResults.add(textMatch.getRawText());
        });
        return rawTextResults;
    }

}
