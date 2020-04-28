package fourcats.frameworks;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.parser.nndep.DependencyParser;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.trees.GrammaticalStructure;
import edu.stanford.nlp.trees.TypedDependency;
import edu.stanford.nlp.util.CoreMap;
import fourcats.datastructure.AnalyzedData;
import fourcats.interfaceaccess.TextAnalyzer;

import java.util.Collection;
import java.util.List;
import java.util.Properties;


public class StanfordNlp implements TextAnalyzer {
    private static final String DP_MODEL = "edu/stanford/nlp/models/parser/nndep/english_UD.gz";
    private StanfordCoreNLP pipeline;
    private final DependencyParser depparser;
    private Properties props;

    public StanfordNlp() {
        props = new Properties();
        props.put("annotators", "tokenize, ssplit, pos, lemma");
        pipeline = new StanfordCoreNLP(props);
        depparser = DependencyParser.loadFromModelFile(DP_MODEL);
    }

    public AnalyzedData parseDocumentContent(String documentContent) {
        //Parsing della frase
        AnalyzedData data = new AnalyzedData();

        Annotation document = new Annotation(documentContent);
        this.pipeline.annotate(document);

        List<CoreMap> sentences = document.get(CoreAnnotations.SentencesAnnotation.class);

        for(CoreMap sentence: sentences) {
            GrammaticalStructure gramstruct = depparser.predict(sentence);
            Collection<TypedDependency> dependencies = gramstruct.typedDependencies();
            for(TypedDependency dep : dependencies) {
                data.addDependency(dep.gov().lemma(), dep.dep().lemma(), dep.reln().getShortName());
            }
            //Lemmatizzazione della frase
            for (CoreLabel token: sentence.get(CoreAnnotations.TokensAnnotation.class)) {
                data.addLemmaData(token.value(),token.tag(),token.getString(CoreAnnotations.LemmaAnnotation.class));
            }
        }

        return data;
    }
}
