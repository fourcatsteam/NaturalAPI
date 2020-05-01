package fourcats.frameworks;

import fourcats.entities.Actor;
import fourcats.entities.BAL;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class BalAnalyzerImplementationTest {

    private BAL bal;
    private BalAnalyzerImplementation balAnalyzer;

    @Before public void CeateBalAnalyzer() {
        Actor actor = new Actor("Actor");
        ArrayList<Actor> list = new ArrayList<>();
        list.add(actor);
        bal = new BAL(list);
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
        assertEquals(result, "{\n" + "  \"actors\" : [ {\n" + "    \"name\" : \"Actor\",\n" +
                "    \"actions\" : [ ]\n" + "  } ]\n" + "}");
    }
}