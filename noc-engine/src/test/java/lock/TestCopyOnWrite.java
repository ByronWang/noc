package lock;

import java.util.concurrent.CopyOnWriteArrayList;

import junit.framework.TestCase;

public class TestCopyOnWrite extends TestCase {

    public void test1() {
        CopyOnWriteArrayList<Object> ol = new CopyOnWriteArrayList<Object>();
        
        ol.add("dfsfdsf");
        Object[] oa= ol.toArray();
        assertEquals(1, oa.length);
        ol.add("wwww");
        oa=ol.toArray();
        assertEquals(2, oa.length);
        

    }
}
