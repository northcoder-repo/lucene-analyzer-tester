package org.northcoder.luceneanalyzertester;

import org.northcoder.luceneanalyzertester.analyzers.TestHelper;
import java.util.List;
import java.util.ArrayList;
import static com.google.common.truth.Truth.assertThat;
import java.io.IOException;
import java.util.Properties;
import org.northcoder.luceneanalyzertester.analyzers.*;
import org.northcoder.luceneanalyzertester.utils.TestUtils;
import org.northcoder.luceneanalyzertester.utils.IndexBuilder;
import org.northcoder.luceneanalyzertester.utils.QueryRunner;
import org.northcoder.luceneanalyzertester.utils.SearchResults;
import org.northcoder.luceneanalyzertester.utils.TestResult;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.highlight.InvalidTokenOffsetsException;

public class Tester {

    private static final List<TestResult> TEST_RESULTS = new ArrayList();
    private static final Properties PROPS = new Properties();

    static {
        PROPS.put("INDEX_DIR", "/your/path/here/");
        PROPS.put("FIELD_NAME", "value");
    }
    ;
    
    public final List<String> indexRawData = new ArrayList();

    public Tester() {
        indexRawData.add("The quick brown fox.");
        indexRawData.add("To Be Or Not To Be"); // stop words
        indexRawData.add("hyphen-ated foo words");
        indexRawData.add("Église Ǽ ⅓ & ④"); // folding tests & symbols
        indexRawData.add("Breaker Breaking Broken"); // stemming tests
        // type Alt+0160 on a PC for a nbsp:
        indexRawData.add("non\u00A0breaking\u00A0spaces\u00A0here");
        indexRawData.add("ΜΆΪΟΣ");
        indexRawData.add("３ ％ Ｇ"); // fullwidth characters
        indexRawData.add("123 #456"); // numbers
        indexRawData.add("worries worrisome alumni elephantine"); // stemming tests
        indexRawData.add("zebra camel zebra camel zebra zebra"); // highlighting
    }

    public List<TestResult> runTests() throws IOException, ParseException, InvalidTokenOffsetsException {
        for (TestHelper testHelper : inputs()) {
            testAnalyzer(testHelper);
        }
        return TEST_RESULTS;
    }

    private static List<TestHelper> inputs() throws IOException {
        //
        // Our test cases are assembled here.  Each test aims to be small, 
        // to illustrate one aspect of the analyzer under test.
        //
        List<TestHelper> tests = new ArrayList();
        tests.addAll(DemoStandardAnalyzer.getTests());
        tests.addAll(DemoStandardAnalyzerWithStopWords.getTests());
        tests.addAll(new DemoCustomAnalyzerWithStopWords().getTests());
        tests.addAll(new DemoCustomUnicodeNormalizer().getTests());
        tests.addAll(new DemoCustomUnicodeAndAsciiNormalizer().getTests());
        tests.addAll(new DemoNgram3Analyzer().getTests());
        tests.addAll(new DemoCustomAnalyzerWithCustomTokenizer().getTests());
        tests.addAll(DemoStemmerAnalyzerUsingBuilderNames.getTests());
        tests.addAll(new DemoShingleAnalyzer().getTests());
        return tests;
    }

    private void testAnalyzer(TestHelper testHelper) throws IOException,
            ParseException, InvalidTokenOffsetsException {
        Analyzer analyzer = testHelper.getAnalyzer();
        IndexBuilder.build(indexRawData, analyzer, PROPS);

        String searchTerm = testHelper.getSearchTerm();
        int expectedResultCount = testHelper.getExpectedResultCount();
        List<String> expectedMatches = testHelper.getExpectedMatches();

        SearchResults searchResults = QueryRunner.search(searchTerm, analyzer, PROPS);
        assertThat(searchResults.getResults().size()).isEqualTo(expectedResultCount);
        assertThat(TestUtils.checkMatchLists(expectedMatches, searchResults
                .getRawTextResults())).isTrue();

        TestResult testResult = TestUtils.captureResult(testHelper, searchTerm,
                searchResults, TestUtils.getIndexedTerms(PROPS));

        testResult.printResult();

        TEST_RESULTS.add(testResult);
    }

}
