import java.util.Random;
import student.TestCase;

/**
 * @author {Your Name Here}
 * @version {Put Something Here}
 */
public class AirControlTest extends TestCase {

    /**
     * Sets up the tests that follow. In general, used for initialization
     */
    public void setUp() {
        // Nothing here
    }

    /**
     * Get code coverage of the class declaration.
     *
     * @throws Exception
     */
    public void testRInit() throws Exception {
        AirControl recstore = new AirControl();
        assertNotNull(recstore);
    }

    // ----------------------------------------------------------
    /**
     * Test syntax: Sample Input/Output
     *
     * @throws Exception
     */
    public void testSampleInput() throws Exception {
        Random rnd = new Random();
        rnd.setSeed(0xCAFEBEEF);
        WorldDB w = new WorldDB(rnd);

        assertTrue(w.add(new Balloon("B1",
                10, 11, 11, 21, 12, 31, "hot_air", 15)));
        assertTrue(w.add(new AirPlane("Air1",
                0, 10, 1, 20, 2, 30, "USAir", 717, 4)));
        assertTrue(w.add(new Drone("Air2",
                100, 1010, 101, 924, 2, 900, "Droners", 3)));
        assertTrue(w.add(new Bird("pterodactyl",
                0, 100, 20, 10, 50, 50, "Dinosaur", 1)));
        assertFalse(w.add(new Bird("pterodactyl",
                0, 100, 20, 10, 50, 50, "Dinosaur", 1)));
        assertTrue(w.add(new Rocket("Enterprise",
                0, 100, 20, 10, 50, 50, 5000, 99.29)));

        assertFuzzyEquals(
                "Rocket Enterprise 0 100 20 10 50 50 5000 99.29",
                w.delete("Enterprise"));

        assertFuzzyEquals("Airplane Air1 0 10 1 20 2 30 USAir 717 4",
                w.print("Air1"));
        assertNull(w.print("air1"));

        assertFuzzyEquals(
                "I (0, 0, 0, 1024, 1024, 1024) 0\r\n"
                        + "  I (0, 0, 0, 512, 1024, 1024) 1\r\n"
                        + "    Leaf with 3 objects (0, 0, 0, 512, 512, 1024) 2\r\n"
                        + "    (Airplane Air1 0 10 1 20 2 30 USAir 717 4)\r\n"
                        + "    (Balloon B1 10 11 11 21 12 31 hot_air 15)\r\n"
                        + "    (Bird pterodactyl 0 100 20 10 50 50 Dinosaur 1)\r\n"
                        + "    Leaf with 1 objects (0, 512, 0, 512, 512, 1024) 2\r\n"
                        + "    (Drone Air2 100 1010 101 924 2 900 Droners 3)\r\n"
                        + "  Leaf with 1 objects (512, 0, 0, 512, 1024, 1024) 1\r\n"
                        + "  (Drone Air2 100 1010 101 924 2 900 Droners 3)\r\n"
                        + "5 Bintree nodes printed\r\n",
                w.printbintree());

        assertFuzzyEquals(
                "Node has depth 3, Value (null)\r\n"
                        + "Node has depth 3, "
                        + "Value (Airplane Air1 0 10 1 20 2 30 USAir 717 4)\r\n"
                        + "Node has depth 1, "
                        + "Value (Drone Air2 100 1010 101 924 2 900 Droners 3)\r\n"
                        + "Node has depth 2, "
                        + "Value (Balloon B1 10 11 11 21 12 31 hot_air 15)\r\n"
                        + "Node has depth 2, "
                        + "Value (Bird pterodactyl 0 100 20 10 50 50 Dinosaur 1)\r\n"
                        + "4 skiplist nodes printed\r\n",
                w.printskiplist());

        assertFuzzyEquals(
                "Found these records in the range a to z\r\n"
                        + "Bird pterodactyl 0 100 20 10 50 50 Dinosaur 1\r\n",
                w.rangeprint("a", "z"));
        assertFuzzyEquals(
                "Found these records in the range a to l\r\n",
                w.rangeprint("a", "l"));
        assertNull(w.rangeprint("z", "a"));

        assertFuzzyEquals(
                "The following collisions exist in the database:\r\n"
                        + "In leaf node (0, 0, 0, 512, 512, 1024) 2\r\n"
                        + "(Airplane Air1 0 10 1 20 2 30 USAir 717 4) "
                        + "and (Balloon B1 10 11 11 21 12 31 hot_air 15)\r\n"
                        + "In leaf node (0, 512, 0, 512, 512, 1024) 2\r\n"
                        + "In leaf node (512, 0, 0, 512, 1024, 1024) 1\r\n",
                w.collisions());

        assertFuzzyEquals(
                "The following objects intersect (0 0 0 1024 1024 1024):\r\n"
                        + "In Internal node (0, 0, 0, 1024, 1024, 1024) 0\r\n"
                        + "In Internal node (0, 0, 0, 512, 1024, 1024) 1\r\n"
                        + "In leaf node (0, 0, 0, 512, 512, 1024) 2\r\n"
                        + "Airplane Air1 0 10 1 20 2 30 USAir 717 4\r\n"
                        + "Balloon B1 10 11 11 21 12 31 hot_air 15\r\n"
                        + "Bird pterodactyl 0 100 20 10 50 50 Dinosaur 1\r\n"
                        + "In leaf node (0, 512, 0, 512, 512, 1024) 2\r\n"
                        + "Drone Air2 100 1010 101 924 2 900 Droners 3\r\n"
                        + "In leaf node (512, 0, 0, 512, 1024, 1024) 1\r\n"
                        + "5 nodes were visited in the bintree\r\n",
                w.intersect(0, 0, 0, 1024, 1024, 1024));
    }

    // ----------------------------------------------------------
    /**
     * Test syntax: Check various forms of bad input parameters
     *
     * @throws Exception
     */
    public void testBadInput() throws Exception {
        Random rnd = new Random();
        rnd.setSeed(0xCAFEBEEF);
        WorldDB w = new WorldDB(rnd);
        assertFalse(w.add(new AirPlane("a", 1, 1, 1, 1, 1, 1, null, 1, 1)));
        assertFalse(w.add(new AirPlane("a", 1, 1, 1, 1, 1, 1, "Alaska", 0, 1)));
        assertFalse(w.add(new AirPlane("a", 1, 1, 1, 1, 1, 1, "Alaska", 1, 0)));
        assertFalse(w.add(new Balloon(null, 1, 1, 1, 1, 1, 1, "hot", 5)));
        assertFalse(w.add(new Balloon("b", -1, 1, 1, 1, 1, 1, "hot", 5)));
        assertFalse(w.add(new Balloon("b", 1, -1, 1, 1, 1, 1, "hot", 5)));
        assertFalse(w.add(new Balloon("b", 1, 1, -1, 1, 1, 1, "hot", 5)));
        assertFalse(w.add(new Balloon("b", 1, 1, 1, 0, 1, 1, "hot", 5)));
        assertFalse(w.add(new Balloon("b", 1, 1, 1, 1, 0, 1, "hot", 5)));
        assertFalse(w.add(new Balloon("b", 1, 1, 1, 1, 1, 0, "hot", 5)));
        assertFalse(w.add(new Balloon("b", 1, 1, 1, 1, 1, 1, null, 5)));
        assertFalse(w.add(new Balloon("b", 1, 1, 1, 1, 1, 1, "hot", -1)));
        assertFalse(w.add(new Bird("b", 1, 1, 1, 1, 1, 1, null, 5)));
        assertFalse(w.add(new Bird("b", 1, 1, 1, 1, 1, 1, "Ostrich", 0)));
        assertFalse(w.add(new Drone("d", 1, 1, 1, 1, 1, 1, null, 5)));
        assertFalse(w.add(new Drone("d", 1, 1, 1, 1, 1, 1, "Droner", 0)));
        assertFalse(w.add(new Rocket("r", 1, 1, 1, 1, 1, 1, -1, 1.1)));
        assertFalse(w.add(new Rocket("r", 1, 1, 1, 1, 1, 1, 1, -1.1)));
        assertFalse(w.add(
                new AirPlane("a", 2000, 1, 1, 1, 1, 1, "Alaska", 1, 1)));
        assertFalse(w.add(
                new AirPlane("a", 1, 2000, 1, 1, 1, 1, "Alaska", 1, 1)));
        assertFalse(w.add(
                new AirPlane("a", 1, 1, 2000, 1, 1, 1, "Alaska", 1, 1)));
        assertFalse(w.add(
                new AirPlane("a", 1, 1, 1, 2000, 1, 1, "Alaska", 1, 1)));
        assertFalse(w.add(
                new AirPlane("a", 1, 1, 1, 1, 2000, 1, "Alaska", 1, 1)));
        assertFalse(w.add(
                new AirPlane("a", 1, 1, 1, 1, 1, 2000, "Alaska", 1, 1)));
        assertFalse(w.add(
                new AirPlane("a", 1000, 1, 1, 1000, 1, 1, "Alaska", 1, 1)));
        assertFalse(w.add(
                new AirPlane("a", 1, 1000, 1, 1, 1000, 1, "Alaska", 1, 1)));
        assertFalse(w.add(
                new AirPlane("a", 1, 1, 1000, 1, 1, 1000, "Alaska", 1, 1)));
        assertNull(w.delete(null));
        assertNull(w.print(null));
        assertNull(w.rangeprint(null, "a"));
        assertNull(w.rangeprint("a", null));
        assertNull(w.intersect(-1, 1, 1, 1, 1, 1));
        assertNull(w.intersect(1, -1, 1, 1, 1, 1));
        assertNull(w.intersect(1, 1, -1, 1, 1, 1));
        assertNull(w.intersect(1, 1, 1, -1, 1, 1));
        assertNull(w.intersect(1, 1, 1, 1, -1, 1));
        assertNull(w.intersect(1, 1, 1, 1, 1, -1));
        assertNull(w.intersect(2000, 1, 1, 1, 1, 1));
        assertNull(w.intersect(1, 2000, 1, 1, 1, 1));
        assertNull(w.intersect(1, 1, 2000, 1, 1, 1));
        assertNull(w.intersect(1, 1, 1, 2000, 1, 1));
        assertNull(w.intersect(1, 1, 1, 1, 2000, 1));
        assertNull(w.intersect(1, 1, 1, 1, 1, 2000));
        assertNull(w.intersect(1000, 1, 1, 1000, 1, 1));
        assertNull(w.intersect(1, 1000, 1, 1, 1000, 1));
        assertNull(w.intersect(1, 1, 1000, 1, 1, 1000));
    }

    // ----------------------------------------------------------
    /**
     * Test empty: Check various returns from commands on empty database
     *
     * @throws Exception
     */
    public void testEmpty() throws Exception {
        WorldDB w = new WorldDB(null);
        assertNull(w.delete("hello"));
        assertFuzzyEquals("SkipList is empty", w.printskiplist());
        assertFuzzyEquals(
                "E (0, 0, 0, 1024, 1024, 1024) 0\r\n"
                        + "1 Bintree nodes printed\r\n",
                w.printbintree());
        assertNull(w.print("hello"));
        assertFuzzyEquals("Found these records in the range begin to end\n",
                w.rangeprint("begin", "end"));
        assertFuzzyEquals("The following collisions exist in the database:\n",
                w.collisions());
        assertFuzzyEquals(
                "The following objects intersect (1, 1, 1, 1, 1, 1)\n" +
                        "1 nodes were visited in the bintree\n",
                w.intersect(1, 1, 1, 1, 1, 1));
    }

    // ----------------------------------------------------------
    /**
     * Test basic SkipList operations - insert and search
     *
     * @throws Exception
     */
    public void testSkipListBasic() throws Exception {
        Random rnd = new Random();
        rnd.setSeed(12345);
        WorldDB w = new WorldDB(rnd);

        // Add some objects
        assertTrue(w.add(new Balloon("Alpha", 10, 10, 10, 20, 20, 20, "hot", 5)));
        assertTrue(w.add(new Balloon("Beta", 50, 50, 50, 30, 30, 30, "cold", 3)));
        assertTrue(w.add(new Balloon("Gamma", 100, 100, 100, 25, 25, 25, "hot", 7)));

        // Verify we can find them
        assertNotNull(w.print("Alpha"));
        assertNotNull(w.print("Beta"));
        assertNotNull(w.print("Gamma"));

        // Verify print output
        assertFuzzyEquals("Balloon Alpha 10 10 10 20 20 20 hot 5", w.print("Alpha"));
        assertFuzzyEquals("Balloon Beta 50 50 50 30 30 30 cold 3", w.print("Beta"));
    }

    // ----------------------------------------------------------
    /**
     * Test SkipList duplicate handling
     *
     * @throws Exception
     */
    public void testSkipListDuplicates() throws Exception {
        Random rnd = new Random();
        rnd.setSeed(54321);
        WorldDB w = new WorldDB(rnd);

        // Add an object
        assertTrue(w.add(new Balloon("Delta", 10, 10, 10, 20, 20, 20, "hot", 5)));

        // Try to add duplicate - should fail
        assertFalse(w.add(new Balloon("Delta", 30, 30, 30, 40, 40, 40, "cold", 3)));

        // Original should still be there with original values
        String result = w.print("Delta");
        assertNotNull(result);
        assertFuzzyEquals("Balloon Delta 10 10 10 20 20 20 hot 5", result);
    }

    // ----------------------------------------------------------
    /**
     * Test SkipList delete operations
     *
     * @throws Exception
     */
    public void testSkipListDelete() throws Exception {
        Random rnd = new Random();
        rnd.setSeed(99999);
        WorldDB w = new WorldDB(rnd);

        // Add objects
        assertTrue(w.add(new Balloon("Echo", 10, 10, 10, 20, 20, 20, "hot", 5)));
        assertTrue(w.add(new Balloon("Foxtrot", 50, 50, 50, 30, 30, 30, "cold", 3)));

        // Delete one
        String deleted = w.delete("Echo");
        assertNotNull(deleted);
        assertFuzzyEquals("Balloon Echo 10 10 10 20 20 20 hot 5", deleted);

        // Verify it's gone
        assertNull(w.print("Echo"));

        // Other one should still be there
        assertNotNull(w.print("Foxtrot"));

        // Delete non-existent
        assertNull(w.delete("NonExistent"));
    }

    // ----------------------------------------------------------
    /**
     * Test SkipList range query
     *
     * @throws Exception
     */
    public void testSkipListRangeQuery() throws Exception {
        Random rnd = new Random();
        rnd.setSeed(11111);
        WorldDB w = new WorldDB(rnd);

        // Add objects with different names
        assertTrue(w.add(new Balloon("Apple", 10, 10, 10, 20, 20, 20, "hot", 5)));
        assertTrue(w.add(new Balloon("Banana", 50, 50, 50, 30, 30, 30, "cold", 3)));
        assertTrue(w.add(new Balloon("Cherry", 100, 100, 100, 25, 25, 25, "hot", 7)));
        assertTrue(w.add(new Balloon("Date", 150, 150, 150, 35, 35, 35, "cold", 2)));

        // Range query that includes some
        String result = w.rangeprint("Banana", "Date");
        assertNotNull(result);
        assertTrue(result.contains("Banana"));
        assertTrue(result.contains("Cherry"));
        assertTrue(result.contains("Date"));
        assertFalse(result.contains("Apple"));

        // Empty range
        String empty = w.rangeprint("X", "Y");
        assertNotNull(empty);
        assertFuzzyEquals("Found these records in the range X to Y\n", empty);
    }

    // ----------------------------------------------------------
    /**
     * Test SkipList alphabetical ordering
     *
     * @throws Exception
     */
    public void testSkipListOrdering() throws Exception {
        Random rnd = new Random();
        rnd.setSeed(22222);
        WorldDB w = new WorldDB(rnd);

        // Add objects in non-alphabetical order
        assertTrue(w.add(new Balloon("Zulu", 10, 10, 10, 20, 20, 20, "hot", 5)));
        assertTrue(w.add(new Balloon("Alpha", 50, 50, 50, 30, 30, 30, "cold", 3)));
        assertTrue(w.add(new Balloon("Mike", 100, 100, 100, 25, 25, 25, "hot", 7)));

        // Print skiplist and verify alphabetical ordering
        String skiplist = w.printskiplist();
        assertNotNull(skiplist);

        // Verify all are present
        assertTrue(skiplist.contains("Alpha"));
        assertTrue(skiplist.contains("Mike"));
        assertTrue(skiplist.contains("Zulu"));
    }

    // ----------------------------------------------------------
    /**
     * Test multiple object types in SkipList
     *
     * @throws Exception
     */
    public void testSkipListMultipleTypes() throws Exception {
        Random rnd = new Random();
        rnd.setSeed(33333);
        WorldDB w = new WorldDB(rnd);

        // Add different types of objects
        assertTrue(w.add(new Balloon("B1", 10, 10, 10, 20, 20, 20, "hot", 5)));
        assertTrue(w.add(new AirPlane("A1", 50, 50, 50, 30, 30, 30, "Delta", 123, 2)));
        assertTrue(w.add(new Drone("D1", 100, 100, 100, 25, 25, 25, "DJI", 4)));
        assertTrue(w.add(new Bird("Bird1", 150, 150, 150, 15, 15, 15, "Eagle", 1)));
        assertTrue(w.add(new Rocket("R1", 200, 200, 200, 40, 40, 40, 1000, 45.5)));

        // Verify all can be found
        assertNotNull(w.print("B1"));
        assertNotNull(w.print("A1"));
        assertNotNull(w.print("D1"));
        assertNotNull(w.print("Bird1"));
        assertNotNull(w.print("R1"));

        // Verify correct types in output
        assertTrue(w.print("B1").contains("Balloon"));
        assertTrue(w.print("A1").contains("Airplane"));
        assertTrue(w.print("D1").contains("Drone"));
        assertTrue(w.print("Bird1").contains("Bird"));
        assertTrue(w.print("R1").contains("Rocket"));
    }

    // ----------------------------------------------------------
    /**
     * Test case sensitivity in SkipList
     *
     * @throws Exception
     */
    public void testSkipListCaseSensitivity() throws Exception {
        Random rnd = new Random();
        rnd.setSeed(44444);
        WorldDB w = new WorldDB(rnd);

        // Add object with specific case
        assertTrue(w.add(new Balloon("TestCase", 10, 10, 10, 20, 20, 20, "hot", 5)));

        // Verify exact match works
        assertNotNull(w.print("TestCase"));

        // Verify case mismatch doesn't work
        assertNull(w.print("testcase"));
        assertNull(w.print("TESTCASE"));
        assertNull(w.print("TeStCaSe"));
    }

    // ----------------------------------------------------------
    /**
     * Test SkipList with many insertions and deletions
     *
     * @throws Exception
     */
    public void testSkipListStress() throws Exception {
        Random rnd = new Random();
        rnd.setSeed(55555);
        WorldDB w = new WorldDB(rnd);

        // Add many objects
        for (int i = 0; i < 20; i++) {
            String name = "Object" + i;
            assertTrue(w.add(new Balloon(name, i * 10, i * 10, i * 10,
                    20, 20, 20, "hot", 5)));
        }

        // Verify all are there
        for (int i = 0; i < 20; i++) {
            assertNotNull(w.print("Object" + i));
        }

        // Delete half of them
        for (int i = 0; i < 20; i += 2) {
            assertNotNull(w.delete("Object" + i));
        }

        // Verify deleted ones are gone
        for (int i = 0; i < 20; i += 2) {
            assertNull(w.print("Object" + i));
        }

        // Verify remaining ones are still there
        for (int i = 1; i < 20; i += 2) {
            assertNotNull(w.print("Object" + i));
        }
    }

    // ----------------------------------------------------------
    /**
     * Test range query edge cases
     *
     * @throws Exception
     */
    public void testRangeQueryEdgeCases() throws Exception {
        Random rnd = new Random();
        rnd.setSeed(66666);
        WorldDB w = new WorldDB(rnd);

        // Add objects
        assertTrue(w.add(new Balloon("A", 10, 10, 10, 20, 20, 20, "hot", 5)));
        assertTrue(w.add(new Balloon("M", 50, 50, 50, 30, 30, 30, "cold", 3)));
        assertTrue(w.add(new Balloon("Z", 100, 100, 100, 25, 25, 25, "hot", 7)));

        // Test backwards range (should return null or error)
        assertNull(w.rangeprint("Z", "A"));

        // Test range with same min and max
        String result = w.rangeprint("M", "M");
        assertNotNull(result);
        assertTrue(result.contains("M"));

        // Test range that includes everything
        String all = w.rangeprint("A", "Z");
        assertNotNull(all);
        assertTrue(all.contains("A"));
        assertTrue(all.contains("M"));
        assertTrue(all.contains("Z"));
    }

    // ----------------------------------------------------------
    /**
     * Test clear functionality (if implemented)
     *
     * @throws Exception
     */
    public void testClear() throws Exception {
        Random rnd = new Random();
        rnd.setSeed(77777);
        WorldDB w = new WorldDB(rnd);

        // Add objects
        assertTrue(w.add(new Balloon("Test1", 10, 10, 10, 20, 20, 20, "hot", 5)));
        assertTrue(w.add(new Balloon("Test2", 50, 50, 50, 30, 30, 30, "cold", 3)));

        // Clear
        w.clear();

        // Verify empty
        assertFuzzyEquals("SkipList is empty", w.printskiplist());
        assertNull(w.print("Test1"));
        assertNull(w.print("Test2"));

        // Should be able to add again
        assertTrue(w.add(new Balloon("Test1", 100, 100, 100, 20, 20, 20, "hot", 5)));
    }
}