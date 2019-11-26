package org.northcoder.luceneanalyzertester.analyzers;

import java.io.IOException;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import org.apache.lucene.analysis.LowerCaseFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardTokenizer;
import org.apache.lucene.analysis.StopwordAnalyzerBase;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.miscellaneous.ASCIIFoldingFilter;
import org.apache.lucene.analysis.ngram.NGramTokenFilter;

public class DemoNgram3Analyzer extends StopwordAnalyzerBase {

    @Override
    protected StopwordAnalyzerBase.TokenStreamComponents createComponents(String fieldName) {        
        final Tokenizer source = new StandardTokenizer();
        TokenStream tokenStream = source;
        tokenStream = new LowerCaseFilter(tokenStream);
        tokenStream = new ASCIIFoldingFilter(tokenStream);
        // "true" means the original term is preserved:
        tokenStream = new NGramTokenFilter(tokenStream, 3, 3, true);
        return new TokenStreamComponents(source, tokenStream);
    }
    
    public List<TestHelper> getTests() throws IOException {

        List<TestHelper> testCases = new ArrayList();

        testCases.add(new TestHelper(
                "start of word found",
                "qui", 1,
                Arrays.asList("The quick brown fox."),
                this));

        testCases.add(new TestHelper(
                "middle of word found",
                "uic", 1,
                Arrays.asList("The quick brown fox."),
                this));

        testCases.add(new TestHelper(
                "end of word found",
                "ick", 1,
                Arrays.asList("The quick brown fox."),
                this));

        return testCases;
    }

}
