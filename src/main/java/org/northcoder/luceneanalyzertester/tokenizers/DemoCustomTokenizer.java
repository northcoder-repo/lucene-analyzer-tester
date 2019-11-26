package org.northcoder.luceneanalyzertester.tokenizers;

import org.apache.lucene.analysis.util.CharTokenizer;

/**
 *
 */
public class DemoCustomTokenizer extends CharTokenizer {

    @Override
    protected boolean isTokenChar(int c) {
        return Character.isLetter(c)
                || Character.isDigit(c)
                || c == '#';
    }

}
