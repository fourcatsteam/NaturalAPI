package fourcats.SuggestionBDLAlgorithm;

import fourcats.entities.Bdl;

public interface StrategyAlgorithm {
    boolean findActionInBdl(String nameAction, Bdl bdl); //predicati
    boolean findObjectInBdl(String nameObject, Bdl bdl); //nomi
}
