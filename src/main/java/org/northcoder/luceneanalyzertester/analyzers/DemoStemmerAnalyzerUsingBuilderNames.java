package org.northcoder.luceneanalyzertester.analyzers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.custom.CustomAnalyzer;
import org.apache.lucene.analysis.standard.StandardTokenizerFactory;

public class DemoStemmerAnalyzerUsingBuilderNames {

    public static Analyzer getAnalyzer() throws IOException {
        // The list of names to be used for components can be looked up through:
        //   - TokenizerFactory.availableTokenizers()
        //   - TokenFilterFactory.availableTokenFilters()
        //   - CharFilterFactory.availableCharFilters()
        Analyzer analyzer = CustomAnalyzer.builder()
                .withTokenizer(StandardTokenizerFactory.NAME)
                .addTokenFilter("lowercase")
                .addTokenFilter("snowballPorter")
                .build();
        return analyzer;
    }

    public static List<TestHelper> getTests() throws IOException {

        List<TestHelper> testCases = new ArrayList();

        testCases.add(new TestHelper(
                "stemmed word found 1",
                "break", 2,
                Arrays.asList(new String[] {
                    "non breaking spaces here", 
                    "Breaker Breaking Broken"}),
                getAnalyzer()));
        
        testCases.add(new TestHelper(
                "stemmed word found 2 - FAILED",
                "elephants", 0,
                new ArrayList(),
                getAnalyzer()));
        
        testCases.add(new TestHelper(
                "stemmed word found 3",
                "worried", 1,
                Arrays.asList("worries worrisome alumni elephantine"),
                getAnalyzer()));
        
        testCases.add(new TestHelper(
                "stemmed word found 4 - FAILED",
                "alumna", 0,
                new ArrayList(),
                getAnalyzer()));
        
        return testCases;
    }

}
