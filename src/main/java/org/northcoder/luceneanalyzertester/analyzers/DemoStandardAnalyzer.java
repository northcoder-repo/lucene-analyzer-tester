package org.northcoder.luceneanalyzertester.analyzers;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;

public class DemoStandardAnalyzer {

    // No customizations here - just the out-of-the-box StandardAnalyzer:
    public static Analyzer getAnalyzer() {
        // StandardAnalyzer includes:
        //   - StandardTokenizer's word break rules from Unicode.
        //   - LowerCaseFilter.
        //   - StopFilter (empty to begin with - no stop words specified)
        return new StandardAnalyzer();
    }

    public static List<TestHelper> getTests() {

        List<TestHelper> testCases = new ArrayList();

        testCases.add(new TestHelper(
                "no ascii folding",
                "Föx", 0,
                new ArrayList<>(),
                getAnalyzer()));

        testCases.add(new TestHelper(
                "lower case handler",
                "FOX", 1,
                Arrays.asList("The quick brown fox."),
                getAnalyzer()));

        testCases.add(new TestHelper(
                "word break on non-breaking spaces",
                "spaces", 1,
                Arrays.asList("non breaking spaces here"),
                getAnalyzer()));

        testCases.add(new TestHelper(
                "word break on hyphens",
                "hyphen", 1,
                Arrays.asList("hyphen-ated foo words"),
                getAnalyzer()));
        
        testCases.add(new TestHelper(
                "stop word not removed",
                "THE", 1,
                Arrays.asList("The quick brown fox."),
                getAnalyzer()));
        
        testCases.add(new TestHelper(
                "multiple results found",
                "BreakING", 2,
                Arrays.asList(new String[] {
                    "non breaking spaces here", 
                    "Breaker Breaking Broken"}),
                getAnalyzer()));

        testCases.add(new TestHelper(
                "partial word not found",
                "Break", 0,
                new ArrayList<>(),
                getAnalyzer()));

        testCases.add(new TestHelper(
                "two words found",
                "FOX QUICK", 1,
                Arrays.asList("The quick brown fox."),
                getAnalyzer()));

        testCases.add(new TestHelper(
                "one word exists, one does not",
                "fox foople", 1,
                Arrays.asList("The quick brown fox."),
                getAnalyzer()));

        testCases.add(new TestHelper(
                "symbols not found",
                "⅓ & ④", 0,
                new ArrayList<>(),
                getAnalyzer()));

        testCases.add(new TestHelper(
                "obscure letter found",
                "Ǽ", 1,
                Arrays.asList("Église Ǽ ⅓ & ④"),
                getAnalyzer()));
        
        testCases.add(new TestHelper(
                "highlight multiple matches",
                "zebra", 1,
                Arrays.asList("zebra camel zebra camel zebra zebra"),
                getAnalyzer()));

        return testCases;
    }

}
