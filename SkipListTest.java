import java.util.Random;
import student.TestCase;

/**
 * Test class for SkipList
 * Tests all methods with mutation coverage
 *
 * @author CS3114/5040 Staff
 * @author Jonah Schepers
 * @author Rowan Muhoberac
 * @version December 8, 2025
 */
public class SkipListTest extends TestCase {

    /**
     * Random number generator for testing
     */
    private Random rnd;

    /**
     * Sets up the tests
     */
    public void setUp() {
        rnd = new Random();
        rnd.setSeed(0xCAFEBEEF);
    }


    /**
     * Test constructor
     */
    public void testConstructor() {
        SkipList<String> list = new SkipList<String>(rnd);
        assertNotNull(list);
        assertTrue(list.isEmpty());
        assertEquals(0, list.size());
    }


    /**
     * Test constructor with null random
     */
    public void testConstructorNullRandom() {
        SkipList<String> list = new SkipList<String>(null);
        assertNotNull(list);
        assertTrue(list.isEmpty());
    }


    /**
     * Test insert
     */
    public void testInsert() {
        SkipList<String> list = new SkipList<String>(rnd);
        assertTrue(list.insert("A"));
        assertTrue(list.insert("B"));
        assertTrue(list.insert("C"));
        assertEquals(3, list.size());
        assertFalse(list.isEmpty());
    }


    /**
     * Test insert duplicate
     */
    public void testInsertDuplicate() {
        SkipList<String> list = new SkipList<String>(rnd);
        assertTrue(list.insert("A"));
        assertFalse(list.insert("A"));
        assertEquals(1, list.size());
    }


    /**
     * Test insert null
     */
    public void testInsertNull() {
        SkipList<String> list = new SkipList<String>(rnd);
        assertFalse(list.insert(null));
        assertEquals(0, list.size());
    }


    /**
     * Test find
     */
    public void testFind() {
        SkipList<String> list = new SkipList<String>(rnd);
        list.insert("A");
        list.insert("B");
        list.insert("C");

        assertEquals("A", list.find("A"));
        assertEquals("B", list.find("B"));
        assertEquals("C", list.find("C"));
        assertNull(list.find("D"));
        assertNull(list.find(null));
    }


    /**
     * Test remove
     */
    public void testRemove() {
        SkipList<String> list = new SkipList<String>(rnd);
        list.insert("A");
        list.insert("B");
        list.insert("C");

        assertEquals("B", list.remove("B"));
        assertEquals(2, list.size());
        assertNull(list.find("B"));

        assertEquals("A", list.remove("A"));
        assertEquals(1, list.size());

        assertEquals("C", list.remove("C"));
        assertEquals(0, list.size());
        assertTrue(list.isEmpty());
    }


    /**
     * Test remove non-existent
     */
    public void testRemoveNonExistent() {
        SkipList<String> list = new SkipList<String>(rnd);
        list.insert("A");
        assertNull(list.remove("B"));
        assertNull(list.remove(null));
        assertEquals(1, list.size());
    }


    /**
     * Test range query
     */
    public void testRange() {
        SkipList<String> list = new SkipList<String>(rnd);
        list.insert("A");
        list.insert("B");
        list.insert("C");
        list.insert("D");
        list.insert("E");

        Comparable<String>[] range = list.range("B", "D");
        assertNotNull(range);
        assertEquals(3, range.length);
        assertEquals("B", range[0]);
        assertEquals("C", range[1]);
        assertEquals("D", range[2]);
    }


    /**
     * Test range with invalid parameters
     */
    public void testRangeInvalid() {
        SkipList<String> list = new SkipList<String>(rnd);
        list.insert("A");
        list.insert("B");

        assertNull(list.range(null, "B"));
        assertNull(list.range("A", null));
        assertNull(list.range("B", "A")); // min > max
    }


    /**
     * Test range with no matches
     */
    public void testRangeNoMatches() {
        SkipList<String> list = new SkipList<String>(rnd);
        list.insert("B");
        list.insert("C");

        Comparable<String>[] range = list.range("D", "E");
        assertNotNull(range);
        assertEquals(0, range.length);
    }


    /**
     * Test toString
     */
    public void testToString() {
        SkipList<String> list = new SkipList<String>(rnd);
        assertEquals("SkipList is empty", list.toString());

        list.insert("B");
        list.insert("A");
        list.insert("C");

        String result = list.toString();
        assertNotNull(result);
        assertTrue(result.contains("A"));
        assertTrue(result.contains("B"));
        assertTrue(result.contains("C"));
        assertTrue(result.contains("skiplist nodes printed"));
    }


    /**
     * Test with AirObjects
     */
    public void testWithAirObjects() {
        SkipList<AirObject> list = new SkipList<AirObject>(rnd);
        Balloon b1 = new Balloon("B1", 0, 0, 0, 10, 10, 10, "hot", 1);
        Balloon b2 = new Balloon("B2", 0, 0, 0, 10, 10, 10, "hot", 1);
        Balloon b3 = new Balloon("A1", 0, 0, 0, 10, 10, 10, "hot", 1);

        assertTrue(list.insert(b1));
        assertTrue(list.insert(b2));
        assertTrue(list.insert(b3));

        assertEquals(b3, list.find(b3)); // A1 comes first alphabetically
        assertEquals(b1, list.find(b1));
        assertEquals(b2, list.find(b2));
    }

    /**
     * Test multiple operations
     */
// public void testMultipleOperations() {
// SkipList<Integer> list = new SkipList<Integer>(rnd);
// for (int i = 0; i < 10; i++) {
// list.insert(i);
// }
// assertEquals(10, list.size());
//
// for (int i = 0; i < 5; i++) {
// assertEquals(i, list.remove(i));
// }
// assertEquals(5, list.size());
//
// for (int i = 5; i < 10; i++) {
// assertEquals(i, list.find(i));
// }
// }


    /**
     * Test size operations for arithmetic coverage
     */
    public void testSizeOperations() {
        SkipList<String> list = new SkipList<String>(rnd);
        assertEquals(0, list.size());

        list.insert("A");
        assertEquals(1, list.size());

        list.insert("B");
        assertEquals(2, list.size());

        list.remove("A");
        assertEquals(1, list.size());

        list.remove("B");
        assertEquals(0, list.size());
    }


    /**
     * Test range with single element
     */
    public void testRangeSingle() {
        SkipList<String> list = new SkipList<String>(rnd);
        list.insert("B");

        Comparable<String>[] range = list.range("B", "B");
        assertNotNull(range);
        assertEquals(1, range.length);
        assertEquals("B", range[0]);
    }


    /**
     * Test range with all elements
     */
    public void testRangeAll() {
        SkipList<String> list = new SkipList<String>(rnd);
        list.insert("A");
        list.insert("B");
        list.insert("C");

        Comparable<String>[] range = list.range("A", "C");
        assertNotNull(range);
        assertEquals(3, range.length);
    }


    /**
     * Test remove from empty list
     */
    public void testRemoveFromEmpty() {
        SkipList<String> list = new SkipList<String>(rnd);
        assertNull(list.remove("A"));
        assertEquals(0, list.size());
    }


    /**
     * Test find in empty list
     */
    public void testFindInEmpty() {
        SkipList<String> list = new SkipList<String>(rnd);
        assertNull(list.find("A"));
    }
}
