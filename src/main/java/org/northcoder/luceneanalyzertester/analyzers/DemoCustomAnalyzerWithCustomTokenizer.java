package org.northcoder.luceneanalyzertester.analyzers;

import java.io.IOException;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.Analyzer;
import org.northcoder.luceneanalyzertester.tokenizers.DemoCustomTokenizer;

public class DemoCustomAnalyzerWithCustomTokenizer extends Analyzer {

    @Override
    protected Analyzer.TokenStreamComponents createComponents(String fieldName) {        
        final Tokenizer source = new DemoCustomTokenizer();
        TokenStream tokenStream = source;
        return new TokenStreamComponents(source, tokenStream);
    }
    
    public List<TestHelper> getTests() throws IOException {

        List<TestHelper> testCases = new ArrayList();

        testCases.add(new TestHelper(
                "number found",
                "123", 1,
                Arrays.asList("123 #456"),
                this));

        testCases.add(new TestHelper(
                "number following hash not found",
                "456", 0,
                new ArrayList<>(),
                this));

        testCases.add(new TestHelper(
                "number following hash found",
                "#456", 1,
                Arrays.asList("123 #456"),
                this));

        return testCases;
    }

}
