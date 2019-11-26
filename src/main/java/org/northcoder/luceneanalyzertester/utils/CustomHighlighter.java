package org.northcoder.luceneanalyzertester.utils;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Properties;
import org.apache.lucene.document.Document;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.TokenSources;
import org.apache.lucene.search.highlight.TextFragment;
import org.apache.lucene.search.highlight.InvalidTokenOffsetsException;

import org.apache.lucene.index.Fields;

/**
 *
 */
public class CustomHighlighter {
    
    private static final String PRE_TAG = "<span class=\"artem-hilite\">";
    private static final String POST_TAG = "</span>";

    public static MatchedText highlight(Query query, TopDocs results,
            IndexSearcher searcher, Analyzer analyzer, ScoreDoc hit,
            Properties props)
            throws IOException, InvalidTokenOffsetsException {
        SimpleHTMLFormatter htmlFormatter = new SimpleHTMLFormatter(PRE_TAG, POST_TAG);
        Highlighter highlighter = new Highlighter(htmlFormatter, new QueryScorer(query));
        int id = hit.doc;
        Document doc = searcher.doc(id);
        
        String text = doc.get(props.getProperty("FIELD_NAME"));
        String highlightedText = null;
        
        
        Fields fields = searcher.getIndexReader().getTermVectors(id);
        
        
        
        if (text != null) {
            // let's highlight that text:
            TokenStream tokenStream = TokenSources.getTokenStream(props.getProperty("FIELD_NAME"),
                    searcher.getIndexReader().getTermVectors(id), text, analyzer, -1);
            TextFragment[] frags = highlighter.getBestTextFragments(tokenStream, text, false, 10);
            
            for (TextFragment frag : frags) {
                if ((frag != null) && (frag.getScore() > 0)) {
                    highlightedText = frag.toString();
                }
            }
        } else {
            // the text was not stored with the index data - just the ID:
            text = doc.get(props.getProperty("ID_NAME"));
        }
        
        BigDecimal score = new BigDecimal(String.valueOf(hit.score))
                .setScale(6, RoundingMode.HALF_EVEN);

        return new MatchedText(text, highlightedText, score);
    }

}
