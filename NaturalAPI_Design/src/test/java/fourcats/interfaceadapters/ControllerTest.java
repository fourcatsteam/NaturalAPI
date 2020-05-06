package fourcats.interfaceadapters;

import fourcats.port.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class ControllerTest {

    @Mock
    GenerateBalSuggestionsInputPort generateSuggestion;

    @Mock
    DeclineBalSuggestionInputPort declineSuggestion;

    @Mock
    GenerateBalInputPort generateBAL;

    @Mock
    ModifyBalSuggestionInputPort modifySuggestion;

    @Mock
    CreateCustomTypeInputPort createCustomType;

    @Mock
    ShowTypesInputPort showTypes;

    @Mock
    AddBalSuggestionInputPort addSuggestion;

    @Mock
    LoadBdlInputPort loadBdl;

    @InjectMocks
    Controller controller;

    @Before
    public void setup(){

        MockitoAnnotations.initMocks(this);

    }

    @Test
    public void generateSuggestions() {
        List<String> stringList;

        stringList = Arrays.asList();
        controller.generateSuggestions(stringList,true);
        verify(generateSuggestion).generateSuggestions(stringList, true);

        stringList = Arrays.asList();
        controller.generateSuggestions(stringList,false);
        verify(generateSuggestion).generateSuggestions(stringList, false);
    }

    @Test
    public void declineSuggestion() {
       controller.declineSuggestion("1","1");
       verify(declineSuggestion).declineSuggestion(Integer.parseInt("1"), Integer.parseInt("1"));
    }

    @Test
    public void generateBAL() throws IOException {
        controller.generateBAL("bal");
        verify(generateBAL).generateBAL("bal");
    }

    @Test
    public void modifyActionName() {
        controller.modifyActionName("1","1", "newActionName");
        verify(modifySuggestion).modifyActionName(Integer.parseInt("1"), Integer.parseInt("1"),"newActionName");
    }

    @Test
    public void modifyActionType() {
        controller.modifyActionType("1","1","Type");
        modifySuggestion.modifyActionType(Integer.parseInt("1"), Integer.parseInt("1"), "Type");
    }

    @Test
    public void modifyObjectName() {
        controller.modifyObjectName("1","1","1","newObjType");
        modifySuggestion.modifyObjectName(Integer.parseInt("1"), Integer.parseInt("1"), Integer.parseInt("1"), "newObjType");
    }

    @Test
    public void modifyObjectType() {
        controller.modifyObjectType("1","1","1","newType");
        modifySuggestion.modifyObjectType(Integer.parseInt("1"), Integer.parseInt("1"), Integer.parseInt("1"), "newType");
    }

    @Test
    public void modifyObjectTypeById() {
        controller.modifyObjectTypeById("1","1","1","1");
        modifySuggestion.modifyObjectTypeById(Integer.parseInt("1"), Integer.parseInt("1"), Integer.parseInt("1"), Integer.parseInt("1"));
    }

    @Test
    public void createCustomType() {
        Map<String,String> mAttributes = new HashMap<String, String>();

        controller.createCustomType("typeName", mAttributes);
        createCustomType.createType("typeName",mAttributes);
    }

    @Test
    public void showTypes() {
        controller.showTypes();
        verify(showTypes).showTypes();
    }

    @Test
    public void modifyActionTypeById() {
        controller.modifyActionTypeById("1","1","1");
        modifySuggestion.modifyActionTypeById(Integer.parseInt("1"), Integer.parseInt("1"), Integer.parseInt("1"));
    }

    @Test
    public void addObject() {
        controller.addObject("1","1","objName","1");
        modifySuggestion.addObject(Integer.parseInt("1"), Integer.parseInt("1"), "objName", Integer.parseInt("1"));
    }

    @Test
    public void removeObject() {
        controller.removeObject("1","1","1");
        modifySuggestion.removeObject(Integer.parseInt("1"), Integer.parseInt("1"),Integer.parseInt("1"));
    }

    @Test
    public void addSuggestion() {
        controller.addSuggestion("1","suggName","suggType");
        addSuggestion.addSuggestion(Integer.parseInt("1"),"suggName","suggType");
    }

    @Test
    public void addSuggestionByIdType() {
        controller.addSuggestionByIdType("1","suggName","1");
        addSuggestion.addSuggestionByIdType(Integer.parseInt("1"),"suggName",Integer.parseInt("1"));
    }

    @Test
    public void loadBdl() throws IOException {
        String[] namebdl = new String[1];
        controller.loadBdl(namebdl);
        verify(loadBdl).loadBdlFiles(namebdl);
    }

}