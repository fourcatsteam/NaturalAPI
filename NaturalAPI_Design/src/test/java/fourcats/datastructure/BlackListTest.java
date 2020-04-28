package fourcats.datastructure;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Spy;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class BlackListTest {

    BlackList blackList = new BlackList();

    @Spy
    List<String> list = new ArrayList<>();

    @Before
    public void CreateBlackList() {
        blackList  = new BlackList();
    }

    @Test
    public void BlackListCreateEmpty() {
        assertTrue(true);
    }

    @Test
    public void BlackListCreateFromListWthNoDuplicated() {
        list.add("Last");
        blackList = new BlackList(list);
        assertTrue(blackList.contains("Last"));
    }

}