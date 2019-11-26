package org.northcoder.luceneanalyzertester.utils;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import java.util.ArrayList;
import java.util.Properties;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.highlight.InvalidTokenOffsetsException;
import org.apache.lucene.store.MMapDirectory;

/**
 *
 */
public class QueryRunner {

    public static SearchResults search(String searchTerm, Analyzer analyzer,
            Properties props) 
            throws IOException, ParseException, InvalidTokenOffsetsException {
        IndexReader reader = getIndexReader(props);
        IndexSearcher searcher = new IndexSearcher(reader);
        QueryParser parser = new QueryParser(props.getProperty("FIELD_NAME"), analyzer);
        Query query = parser.parse(searchTerm);
        TopDocs results = searcher.search(query, 100);
        ScoreDoc[] hits = results.scoreDocs;
        
        List<MatchedText> textMatches = new ArrayList();
        
        for (ScoreDoc hit : hits) {
            
            MatchedText textMatch = CustomHighlighter.highlight(query, results, 
                    searcher, analyzer, hit, props);
            textMatches.add(textMatch);
        }
        
        return new SearchResults(query.toString(), textMatches);
    }
    
    public static IndexReader getIndexReader(Properties props) throws IOException {
        return DirectoryReader.open(MMapDirectory
                .open(Paths.get(props.getProperty("INDEX_DIR"))));
    }
}
