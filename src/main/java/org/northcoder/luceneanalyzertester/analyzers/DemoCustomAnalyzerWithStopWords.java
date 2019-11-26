package org.northcoder.luceneanalyzertester.analyzers;

import java.io.IOException;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import org.apache.lucene.analysis.CharArraySet;
import org.apache.lucene.analysis.LowerCaseFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardTokenizer;
import org.apache.lucene.analysis.StopFilter;
import org.apache.lucene.analysis.StopwordAnalyzerBase;
import org.apache.lucene.analysis.Tokenizer;

public class DemoCustomAnalyzerWithStopWords extends StopwordAnalyzerBase {

    @Override
    protected StopwordAnalyzerBase.TokenStreamComponents createComponents(String fieldName) {
        // 2 custom stop words, case insensitive.
        CharArraySet stopWords = new CharArraySet(2, true); 
        stopWords.add("foo");
        stopWords.add("bar");
        
        final Tokenizer source = new StandardTokenizer();
        TokenStream tokenStream = source;
        // lowercase filter applied, before stopwords removed:
        tokenStream = new LowerCaseFilter(tokenStream);
        tokenStream = new StopFilter(tokenStream, stopWords);
        return new TokenStreamComponents(source, tokenStream);
    }
    
    public List<TestHelper> getTests() throws IOException {

        List<TestHelper> testCases = new ArrayList();

        testCases.add(new TestHelper(
                "English stop word found",
                "THE", 1,
                Arrays.asList("The quick brown fox."),
                this));

        testCases.add(new TestHelper(
                "Custom stop word not found",
                "FOO", 0,
                new ArrayList<>(),
                this));

        testCases.add(new TestHelper(
                "stop set used but fox still found",
                "FOX", 1,
                Arrays.asList("The quick brown fox."),
                this));

        return testCases;
    }

}
