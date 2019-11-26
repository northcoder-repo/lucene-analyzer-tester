package org.northcoder.luceneanalyzertester.analyzers;

import java.io.IOException;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.LowerCaseFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardTokenizer;
import org.apache.lucene.analysis.StopwordAnalyzerBase;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.miscellaneous.ASCIIFoldingFilter;
import org.apache.lucene.analysis.shingle.ShingleFilter;

public class DemoShingleAnalyzer extends StopwordAnalyzerBase {

    @Override
    protected StopwordAnalyzerBase.TokenStreamComponents createComponents(String fieldName) {        
        final Tokenizer source = new StandardTokenizer();
        TokenStream tokenStream = source;
        tokenStream = new LowerCaseFilter(tokenStream);
        tokenStream = new ASCIIFoldingFilter(tokenStream);
        // default shingle size is 2:
        tokenStream = new ShingleFilter(tokenStream);
        return new Analyzer.TokenStreamComponents(source, tokenStream);
    }
    
    public List<TestHelper> getTests() throws IOException {

        List<TestHelper> testCases = new ArrayList();

        testCases.add(new TestHelper(
                "single word",
                "quick", 1,
                Arrays.asList("The quick brown fox."),
                this));

        testCases.add(new TestHelper(
                "two in a row",
                "quick brown", 1,
                Arrays.asList("The quick brown fox."),
                this));

        testCases.add(new TestHelper(
                "two not in a row",
                "quick fox", 1,
                Arrays.asList("The quick brown fox."),
                this));

        testCases.add(new TestHelper(
                "three in a row",
                "quick brown fox", 1,
                Arrays.asList("The quick brown fox."),
                this));

        testCases.add(new TestHelper(
                "two words, one does not exist in source",
                "quick jump", 0,
                new ArrayList(),
                this));

        testCases.add(new TestHelper(
                "three words, one word does not exist in source",
                "quick brown rabbit", 0,
                new ArrayList(),
                this));

        return testCases;
    }

}
