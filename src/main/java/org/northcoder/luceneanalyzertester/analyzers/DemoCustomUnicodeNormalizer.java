package org.northcoder.luceneanalyzertester.analyzers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import org.apache.lucene.analysis.LowerCaseFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.icu.ICUNormalizer2Filter;
import org.apache.lucene.analysis.icu.segmentation.ICUTokenizer;

public class DemoCustomUnicodeNormalizer extends Analyzer {

    @Override
    protected Analyzer.TokenStreamComponents createComponents(String fieldName) {
        
        final Tokenizer source = new ICUTokenizer();
        TokenStream tokenStream = source;
        tokenStream = new LowerCaseFilter(tokenStream);
        tokenStream = new ICUNormalizer2Filter(tokenStream);
        return new TokenStreamComponents(source, tokenStream);
    }
    
    public List<TestHelper> getTests() throws IOException {

        List<TestHelper> testCases = new ArrayList();

        testCases.add(new TestHelper(
                "no ascii folding",
                "Föx", 0,
                new ArrayList<>(),
                this));
        
        testCases.add(new TestHelper(
                "word break on non-breaking spaces",
                "spaces", 1,
                Arrays.asList("non breaking spaces here"),
                this));

        testCases.add(new TestHelper(
                "word break on hyphens",
                "hyphen", 1,
                Arrays.asList("hyphen-ated foo words"),
                this));
        
        testCases.add(new TestHelper(
                "symbols not found",
                "⅓ & ④", 0,
                new ArrayList<>(),
                this));

        testCases.add(new TestHelper(
                "obscure letter found",
                "Ǽ", 1,
                Arrays.asList("Église Ǽ ⅓ & ④"),
                this));

        return testCases;
    }

}
