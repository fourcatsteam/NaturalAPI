package fourcats.frameworks;

import fourcats.entities.Actor;
import fourcats.entities.Bal;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class BalAnalyzerImplementationTest {

    private Bal bal;
    private BalAnalyzerImplementation balAnalyzer;

    @Before public void CeateBalAnalyzer() {
        Actor actor = new Actor("Actor");
        ArrayList<Actor> list = new ArrayList<>();
        list.add(actor);
        bal = new Bal(list);
        balAnalyzer = new BalAnalyzerImplementation();
    }

    @Test
    public void BalAnalyzerImplementationCreateJSON() {
        String result = "Not correct yet";
        try {
            result = balAnalyzer.createJsonFromBAL(bal);
        }
        catch (IOException e) {
            fail();
        }
       /* assertEquals( "{\n" +
                "  \"actors\" : [ {\n" +
                "    \"name\" : \"Actor\",\n" +
                "    \"actions\" : [ ]\n" +
                "  } ]\n" +
                "}",result);*/
    }
}