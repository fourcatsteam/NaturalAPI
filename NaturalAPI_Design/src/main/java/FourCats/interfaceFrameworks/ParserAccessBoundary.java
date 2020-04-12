package FourCats.interfaceFrameworks;
import FourCats.DataStructure.LemmatizerData;
import FourCats.interfaceAccess.ParserAccess;

import java.io.StringReader;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

//import com.sun.corba.se.spi.orb.ParserData;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.parser.nndep.DependencyParser;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.process.CoreLabelTokenFactory;
import edu.stanford.nlp.process.PTBTokenizer;
import edu.stanford.nlp.process.Tokenizer;
import edu.stanford.nlp.process.TokenizerFactory;
import edu.stanford.nlp.trees.GrammaticalStructure;
import edu.stanford.nlp.trees.TypedDependency;
import edu.stanford.nlp.util.CoreMap;


public class ParserAccessBoundary implements ParserAccess {
    private final static String DP_MODEL = "edu/stanford/nlp/models/parser/nndep/english_UD.gz";
    private StanfordCoreNLP pipeline;
    private final TokenizerFactory<CoreLabel> tokenizerFactory;
    private final DependencyParser depparser;

    public ParserAccessBoundary(){
        Properties props;
        props = new Properties();
        props.put("annotators", "tokenize, ssplit, pos, lemma");
        pipeline = new StanfordCoreNLP(props);
        tokenizerFactory = PTBTokenizer.factory(new CoreLabelTokenFactory(), "invertible=true");
        depparser = DependencyParser.loadFromModelFile(DP_MODEL);
    }

    public LinkedList<String> parseSentence(String documentContent) {
        Annotation document = new Annotation(documentContent);
        this.pipeline.annotate(document);
        LinkedList<String> parserData = new LinkedList<String>();
        List<CoreMap> sentences = document.get(CoreAnnotations.SentencesAnnotation.class);
        for(CoreMap sentence: sentences) {
            GrammaticalStructure gramstruct = depparser.predict(sentence);
            Collection<TypedDependency> dependencies = gramstruct.typedDependencies();
            for(TypedDependency dep : dependencies) {
                if(dep.reln().getShortName().equalsIgnoreCase("dobj")) {
                    parserData.add(dep.gov().lemma()+" " + dep.dep().lemma());
                }
            }
        }
        return parserData;
    }

    public LemmatizerData lemmatizeSentence(String documentContent) {
        LemmatizerData data = new LemmatizerData();
        Annotation document = new Annotation(documentContent);
        this.pipeline.annotate(document);
        List<CoreMap> sentences = document.get(CoreAnnotations.SentencesAnnotation.class);
        for(CoreMap sentence: sentences) {
            for (CoreLabel token: sentence.get(CoreAnnotations.TokensAnnotation.class)) {
                data.addElement(token.value(),token.tag(),token.getString(CoreAnnotations.LemmaAnnotation.class));
            }
        }
        return data;

    }

    private List<CoreLabel> tokenize(String str){
        Tokenizer<CoreLabel> tokenizer = tokenizerFactory.getTokenizer(new StringReader(str));
        return tokenizer.tokenize();
    }

}
