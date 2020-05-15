package fourcats.interfaceaccess;

import fourcats.datastructure.AnalyzedData;

public interface TextAnalyzer {
    AnalyzedData parseDocumentContent(String doc);
}
