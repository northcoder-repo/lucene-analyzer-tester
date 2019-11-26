package org.northcoder.luceneanalyzertester.tokenizers;

import java.util.Map;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.util.TokenizerFactory;
import org.apache.lucene.util.AttributeFactory;

public class DemoCustomTokenizerFactory extends TokenizerFactory {

    public DemoCustomTokenizerFactory(Map<String, String> args) {
        super(args);
    }

    @Override
    public Tokenizer create(AttributeFactory factory) {
        return new DemoCustomTokenizer();
    }

}
