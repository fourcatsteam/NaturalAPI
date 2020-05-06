package FourCats.UseCaseInteractor;

import FourCats.DataStructure.WordCounter;
import FourCats.Entities.Bdl;
import FourCats.Entities.Document;
import FourCats.InterfaceAccess.RepositoryAccess;
import FourCats.Port.CreateBdlOutputPort;
import FourCats.Port.ViewBdlInputPort;
import FourCats.Port.ViewBdlOutputPort;
import FourCats.UseCaseUtilities.AnalyzeDocument;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class ViewBdl implements ViewBdlInputPort {

    private RepositoryAccess repository;
    private ViewBdlOutputPort output;

    public ViewBdl(RepositoryAccess ra,ViewBdlOutputPort p){
        this.repository = ra;
        this.output = p;
    }

    @Override
    public void view(String nameBdl, Integer viewType) {
        Bdl bdl = repository.readBdl(nameBdl);
        if(bdl==null){
            output.showError("BDL \""+nameBdl+"\" not found");
        } else{
            output.showViewBdlOutput(this.filter(bdl,viewType));
        }
    }

    private Bdl filter(Bdl bdl, Integer type) {
        Bdl filteredBdl;
        switch (type) {
            case 1: {
                //Keep only the words with a probability higher than the threshold
                //threshold set to 1%
                Double probThreshold = 0.01;
                Integer numberOfNouns = bdl.getTotalNounsOccurrences();
                List<WordCounter> nouns = bdl.getNouns().stream()
                        .filter(w -> (double)w.getCount()/(double)numberOfNouns>=probThreshold)
                        .collect(Collectors.toList());

                Integer numberOfVerbs = bdl.getTotalVerbsOccurrences();
                List<WordCounter> verbs = bdl.getVerbs().stream()
                        .filter(w -> (double)w.getCount()/(double)numberOfVerbs>=probThreshold)
                        .collect(Collectors.toList());

                Integer numberOfPredicates = bdl.getTotalPredicatesOccurrences();
                List<WordCounter> predicates = bdl.getPredicates().stream()
                        .filter(w -> (double)w.getCount()/(double)numberOfPredicates>=probThreshold)
                        .collect(Collectors.toList());
                filteredBdl = new Bdl(bdl.getName(),nouns,verbs,predicates);
                break;
            }
            case 2: {
                Integer limit = 15;
                List<WordCounter> nouns = bdl.getNouns().stream().limit(limit).collect(Collectors.toList());
                List<WordCounter> verbs = bdl.getVerbs().stream().limit(limit).collect(Collectors.toList());
                List<WordCounter> predicates = bdl.getPredicates().stream().limit(limit).collect(Collectors.toList());
                filteredBdl = new Bdl(bdl.getName(),nouns,verbs,predicates);
                break;
            }

            default: filteredBdl = bdl;
        }
        return filteredBdl;
    }
}
