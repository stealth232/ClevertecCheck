import ru.clevertec.check.utils.mylinkedlist.MyLinkedList;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MyLinkedListTest {

    MyLinkedList<String> myLinked;

    @BeforeEach
    void initList(){
        myLinked = new MyLinkedList<>();
        myLinked.add("0");
        myLinked.add("1");
        myLinked.add("2");
        myLinked.add("3");
        myLinked.add("4");
    }
    @AfterEach
    void done(){
        myLinked = null;
    }
    @Test
    void testAdding() {
        myLinked.add("5");
        assertEquals("5", myLinked.get(5));
        assertEquals(6, myLinked.size());
    }
    @Test
    void testAddingByIndex() {
        String newElem = "TEST";
        String oldElem = myLinked.get(1);
        int size = myLinked.size();
        myLinked.addByIndex(1,newElem );
        assertEquals(newElem, myLinked.get(1));
        assertEquals(oldElem, myLinked.get(2));
        assertEquals(size+1, myLinked.size());
    }
    @Test
    void testRemoving() {
        int size = myLinked.size();
        String test = myLinked.get(0);
        assertEquals(myLinked.remove(0), test);
        assertEquals(size-1,myLinked.size());
    }
    @Test
    void testRemovingFirst() {
        int size = myLinked.size();
        String oldValue = myLinked.get(1);
        myLinked.removeFirst();
        assertEquals(oldValue, myLinked.get(0));
        assertEquals(size-1,myLinked.size());
    }
    @Test
    void testRemovingException(){
        assertThrows(IndexOutOfBoundsException.class, () ->
        {
            myLinked.remove(7);
        });
    }
    @Test
    void testGettingException() {
        assertThrows(IndexOutOfBoundsException.class, () ->
        {
            myLinked.get(7);
        });
    }
    @Test
    void testSize(){
        assertEquals(5, myLinked.size());
    }
    @Test
    void testAddingFirst(){
        String newElem = "TEST";
        myLinked.addFirst(newElem);
        assertEquals(newElem, myLinked.get(0));
        assertEquals(6, myLinked.size());
    }
    @Test
    void testAddingLast(){
        String newElem = "TEST";
        myLinked.addLast(newElem);
        assertEquals(newElem, myLinked.get(myLinked.size()-1));
        assertEquals(6, myLinked.size());
    }
    @Test
    void testGettingFirst(){
        String first = myLinked.get(0);
        assertEquals(first, myLinked.getFirstElem());
    }
    @Test
    void testGettingLast(){
        String first = myLinked.get(myLinked.size()-1);
        assertEquals(first, myLinked.getLastElem());
    }
}