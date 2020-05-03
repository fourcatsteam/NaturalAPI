package fourcats.SuggestionBDLAlgorithm;

import fourcats.entities.Bdl;

public interface StrategyAlgorithm {
    int findActionInBdl(String nameAction, Bdl bdl); //predicati
    int findObjectInBdl(String nameObject, Bdl bdl); //nomi
}
