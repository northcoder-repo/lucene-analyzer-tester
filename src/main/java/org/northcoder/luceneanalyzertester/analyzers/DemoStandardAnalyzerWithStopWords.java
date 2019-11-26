package org.northcoder.luceneanalyzertester.analyzers;

import java.io.IOException;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.en.EnglishAnalyzer;
import org.apache.lucene.analysis.CharArraySet;

public class DemoStandardAnalyzerWithStopWords {

    // This is just the standard analyzer but with stop words added:
    //   - Engish stop words
    //   - additional custom stop words
    public static Analyzer getAnalyzer() throws IOException {
        
        CharArraySet stopSet = CharArraySet.copy(EnglishAnalyzer.ENGLISH_STOP_WORDS_SET);
        stopSet.add("foo");
        stopSet.add("bar");
        return new StandardAnalyzer(stopSet);
    }

    public static List<TestHelper> getTests() throws IOException {

        List<TestHelper> testCases = new ArrayList();

        testCases.add(new TestHelper(
                "English stop word not found",
                "THE", 0,
                new ArrayList<>(),
                getAnalyzer()));
        
        testCases.add(new TestHelper(
                "Custom stop word not found",
                "FOO", 0,
                new ArrayList<>(),
                getAnalyzer()));
        
        testCases.add(new TestHelper(
                "stop set used but fox still found",
                "FOX", 1,
                Arrays.asList("The quick brown fox."),
                getAnalyzer()));
        
        return testCases;
    }

}
