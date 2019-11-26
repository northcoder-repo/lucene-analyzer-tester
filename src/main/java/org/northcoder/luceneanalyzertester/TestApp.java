package org.northcoder.luceneanalyzertester;

import java.io.IOException;
import java.util.List;
import org.northcoder.luceneanalyzertester.utils.TestResult;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.highlight.InvalidTokenOffsetsException;
import io.javalin.Javalin;
import io.javalin.http.Handler;
import static io.javalin.apibuilder.ApiBuilder.*; // before, get, post...
import io.javalin.plugin.rendering.JavalinRenderer;
import io.javalin.plugin.rendering.template.JavalinThymeleaf;
import java.util.Map;
import java.util.HashMap;

/**
 *
 */
public class TestApp {

    public static void main(String[] args) throws IOException,
            ParseException, InvalidTokenOffsetsException {

        Javalin app = Javalin.create(config -> {
            JavalinRenderer.register(JavalinThymeleaf.INSTANCE);
            //JavalinThymeleaf.configure(templateEngine);
        }).start(7000);

        app.routes(() -> {
            before(SETUP);
            get("/testresults", TEST_RESULTS);
        });
    }

    private static final Handler SETUP = (ctx) -> {
    };

    private static final Handler TEST_RESULTS = (ctx) -> {
        Tester test = new Tester();
        List<TestResult> results = test.runTests();
        
        Map<String, Object> model = new HashMap();
        model.put("rawData", test.indexRawData);
        model.put("results", results);
        ctx.render("results.html", model);
    };

}
