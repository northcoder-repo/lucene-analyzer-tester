package org.northcoder.luceneanalyzertester.utils;

import java.util.ArrayList;
import java.util.List;
import java.io.IOException;
import java.util.Collection;
import java.util.Properties;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.search.spell.LuceneDictionary;
import org.apache.lucene.util.BytesRef;
import org.apache.lucene.util.BytesRefIterator;
import org.northcoder.luceneanalyzertester.analyzers.TestHelper;

/**
 *
 */
public class TestUtils {

    // Gets a list of terms in the index, so we can better see
    // what our selected analyzer did to the input data.
    public static List<String> getIndexedTerms(Properties props) throws IOException {
        IndexReader indexReader = QueryRunner.getIndexReader(props);
        LuceneDictionary dict = new LuceneDictionary(indexReader,
                props.getProperty("FIELD_NAME"));
        BytesRefIterator iterator = dict.getEntryIterator();
        BytesRef byteRef;
        List<String> terms = new ArrayList();
        while ((byteRef = iterator.next()) != null) {
            String term = byteRef.utf8ToString();
            terms.add(term);
        }
        return terms;
    }

    public static TestResult captureResult(TestHelper testHelper, String searchTerm,
            SearchResults searchResults, List<String> indexedTerms) {

        TestResult testResult = new TestResult(
                testHelper.getTestID(),
                indexedTerms,
                searchTerm,
                searchResults.getQuery());

        if (searchResults.getResults() != null) {
            searchResults.getResults().forEach((entry) -> {
                testResult.getMatches().add(testResult.new Match(
                        entry.getRawText(),
                        entry.getHighlightedText(),
                        entry.getScore()));
            });
        }

        return testResult;
    }

    public static boolean checkMatchLists(List<String> expected, Collection<String> actuals) {
        List<String> expectedMatches = new ArrayList();
        expectedMatches.addAll(expected);
        List<String> actualMatches = new ArrayList();
        actualMatches.addAll(actuals);
        actualMatches.removeAll(expectedMatches);
        List<String> actualMatches2 = new ArrayList();
        actualMatches2.addAll(actuals);
        expectedMatches.removeAll(actualMatches2);
        actualMatches.forEach((actualMatch) -> {
            System.out.println("*** Unexpected extra hit   : [" + actualMatch + "]");
        });
        expectedMatches.forEach((expectedMatch) -> {
            System.out.println("*** Failed to find expected: [" + expectedMatch + "]");
        });
        System.out.println("");
        return actualMatches.isEmpty() && expectedMatches.isEmpty();
    }

}
