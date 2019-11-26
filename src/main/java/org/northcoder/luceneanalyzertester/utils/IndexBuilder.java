package org.northcoder.luceneanalyzertester.utils;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import java.util.Properties;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.FieldType;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexOptions;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.MMapDirectory;

/**
 *
 */
public class IndexBuilder {

    /**
     * Builds an index based on the test data and analyzer provided.If index
     * data already exists at the directory location, it is first removed, and
     * then a fresh index is built. Each index document consists of just a
     * single field - and therefore we choose to store the indexable data along
     * with the index documents (so it can be retrieved by searches).
     *
     * @param data list of words/phrases to be indexed as separate Lucene
     * documents.
     * @param analyzer the analyzer to use for indexing.
     * @param props 
     * @throws java.io.IOException
     */
    public static void build(List<String> data, Analyzer analyzer, Properties props)
            throws IOException {
        Directory directory = new MMapDirectory(Paths.get(props.getProperty("INDEX_DIR")));
        IndexWriterConfig config = new IndexWriterConfig(analyzer);
        // This mode deletes any existing index data first:
        config.setOpenMode(IndexWriterConfig.OpenMode.CREATE);

        String fieldName = props.getProperty("FIELD_NAME");

        try (IndexWriter writer1 = new IndexWriter(directory, config)) {
            for (String item : data) {
                Document doc = new Document();
                // We store the input data item in the index document:
                doc.add(new Field(fieldName, item, termVector()));
                //doc.add(new Field(fieldName, item, TextField.TYPE_STORED));
                writer1.addDocument(doc);
            }
        }
    }
    
    private static FieldType termVector() {
        // a term vector is a miniature inverted-index for a
        // field, which can be accessed in a document-oriented 
        // way from IndexReader.getTermVector(int,String).  It
        // can be used to help with processes such as highlighting,
        // or displaying context around a match. (But you can just
        // store all the text in the index, to do highlighting!)
        FieldType tvType = new FieldType();
        // Indexes documents, frequencies, positions and offsets:
        tvType.setIndexOptions(IndexOptions.DOCS_AND_FREQS_AND_POSITIONS_AND_OFFSETS);
        // field's value is stored in the index:
        tvType.setStored(true);
        // store index data in term vectors:
        tvType.setStoreTermVectors(true); 
        // store token character offsets in term vectors:
        tvType.setStoreTermVectorOffsets(true); 
        // store token positions in term vectors:
        tvType.setStoreTermVectorPositions(true);
        // store token payloads into the term vector:
        tvType.setStoreTermVectorPayloads(true);
        return tvType;
    }
}
