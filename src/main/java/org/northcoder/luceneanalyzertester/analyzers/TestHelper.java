package org.northcoder.luceneanalyzertester.analyzers;

import java.util.List;
import org.apache.lucene.analysis.Analyzer;

public class TestHelper {

    private final String testName;
    private final String searchTerm;
    private final int expectedResultCount;
    private final List<String> expectedMatches;
    private final Analyzer analyzer;

    public TestHelper(String testName, 
            String searchTerm, 
            int expectedResultCount, 
            List<String> expectedMatches, 
            Analyzer analyzer) {
        this.testName = testName;
        this.searchTerm = searchTerm;
        this.expectedResultCount = expectedResultCount;
        this.expectedMatches = expectedMatches;
        this.analyzer = analyzer;
    }

    public String getSearchTerm() {
        return searchTerm;
    }

    public int getExpectedResultCount() {
        return expectedResultCount;
    }

    public List<String> getExpectedMatches() {
        return expectedMatches;
    }
    
    public Analyzer getAnalyzer() {
        return analyzer;
    }
    
    public String getTestName() {
        return testName;
    }
    
    public String getTestID() {
        return analyzer.getClass().getSimpleName() + " - " + testName;
    }

    @Override
    public String toString() {
        return getTestID() + "\nSearch term: [" + searchTerm + "]\n";
    }
}
