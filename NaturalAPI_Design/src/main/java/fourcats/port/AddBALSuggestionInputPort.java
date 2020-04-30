package fourcats.port;

public interface AddBALSuggestionInputPort {
    void addSuggestion(int idScenario, String suggestionName, String suggestionType);
    void addSuggestionByIdType(int idScenario, String suggestionName, int idType);
}
