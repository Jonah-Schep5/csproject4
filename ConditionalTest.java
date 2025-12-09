import student.TestCase;

/**
 * Test class for conditional mutations
 * Tests all if/else branches to kill RemoveConditionalMutator mutants
 *
 * @author Jonah Schepers
 * @author Rowan Muhoberac
 * @version December 8, 2025
 */
public class ConditionalTest extends TestCase {

    /**
     * Test conditional branches in validation - ORDER_IF mutations
     * Tests conditions like x < 0, x >= 1024, etc.
     */
    public void testValidationConditionals() {
        // Test x < 0 branch (ORDER_IF)
        Balloon b1 = new Balloon("B1", -1, 0, 0, 1, 1, 1, "hot", 1);
        boolean valid1 = b1.isValid();
        assertFalse(valid1);

        // Test x >= 0 branch (ORDER_IF)
        Balloon b2 = new Balloon("B2", 0, 0, 0, 1, 1, 1, "hot", 1);
        boolean valid2 = b2.isValid();
        assertTrue(valid2);

        // Test x >= 1024 branch (ORDER_IF)
        Balloon b3 = new Balloon("B3", 1024, 0, 0, 1, 1, 1, "hot", 1);
        boolean valid3 = b3.isValid();
        assertFalse(valid3);

        // Test x < 1024 branch (ORDER_IF)
        Balloon b4 = new Balloon("B4", 1023, 0, 0, 1, 1, 1, "hot", 1);
        boolean valid4 = b4.isValid();
        assertTrue(valid4);

        // Test y dimension conditionals
        Balloon b5 = new Balloon("B5", 0, -1, 0, 1, 1, 1, "hot", 1);
        assertFalse(b5.isValid());

        Balloon b6 = new Balloon("B6", 0, 1024, 0, 1, 1, 1, "hot", 1);
        assertFalse(b6.isValid());

        // Test z dimension conditionals
        Balloon b7 = new Balloon("B7", 0, 0, -1, 1, 1, 1, "hot", 1);
        assertFalse(b7.isValid());

        Balloon b8 = new Balloon("B8", 0, 0, 1024, 1, 1, 1, "hot", 1);
        assertFalse(b8.isValid());
    }


    /**
     * Test conditional branches in width validation
     */
    public void testWidthConditionals() {
        // Test width <= 0 branch (ORDER_IF)
        Balloon b1 = new Balloon("B1", 0, 0, 0, 0, 1, 1, "hot", 1);
        assertFalse(b1.isValid());

        // Test width > 0 branch (ORDER_IF)
        Balloon b2 = new Balloon("B2", 0, 0, 0, 1, 1, 1, "hot", 1);
        assertTrue(b2.isValid());

        // Test width > 1024 branch (ORDER_IF)
        Balloon b3 = new Balloon("B3", 0, 0, 0, 1025, 1, 1, "hot", 1);
        assertFalse(b3.isValid());

        // Test width <= 1024 branch (ORDER_IF)
        Balloon b4 = new Balloon("B4", 0, 0, 0, 1024, 1, 1, "hot", 1);
        assertTrue(b4.isValid());
    }


    /**
     * Test conditional branches in extends beyond world check
     */
    public void testExtendsBeyondConditionals() {
        // Test xorig + xwidth > WORLD_SIZE branch (ORDER_IF)
        Balloon b1 = new Balloon("B1", 1000, 0, 0, 100, 1, 1, "hot", 1);
        // 1000 + 100 = 1100 > 1024
        assertFalse(b1.isValid());

        // Test xorig + xwidth <= WORLD_SIZE branch (ORDER_IF)
        Balloon b2 = new Balloon("B2", 1000, 0, 0, 24, 1, 1, "hot", 1);
        // 1000 + 24 = 1024 <= 1024
        assertTrue(b2.isValid());

        // Test y dimension
        Balloon b3 = new Balloon("B3", 0, 1000, 0, 1, 100, 1, "hot", 1);
        assertFalse(b3.isValid());

        Balloon b4 = new Balloon("B4", 0, 1000, 0, 1, 24, 1, "hot", 1);
        assertTrue(b4.isValid());

        // Test z dimension
        Balloon b5 = new Balloon("B5", 0, 0, 1000, 1, 1, 100, "hot", 1);
        assertFalse(b5.isValid());

        Balloon b6 = new Balloon("B6", 0, 0, 1000, 1, 1, 24, "hot", 1);
        assertTrue(b6.isValid());
    }


    /**
     * Test conditional branches in intersection calculations
     */
    public void testIntersectionConditionals() {
        Balloon b1 = new Balloon("B1", 10, 20, 30, 40, 50, 60, "hot", 1);

        // Test xorig < x + xwid branch (ORDER_IF)
        // b1: x=10-50, query: x=0-60 (overlaps)
        boolean result1 = b1.intersects(0, 20, 30, 60, 50, 60);
        assertTrue(result1);

        // Test xorig >= x + xwid branch (ORDER_IF) - no overlap
        // b1: x=10-50, query: x=60-100 (no overlap)
        boolean result2 = b1.intersects(60, 20, 30, 40, 50, 60);
        assertFalse(result2);

        // Test xorig + xwidth > x branch (ORDER_IF)
        // b1: x=10-50, query: x=5-15 (overlaps)
        boolean result3 = b1.intersects(5, 20, 30, 10, 50, 60);
        assertTrue(result3);

        // Test xorig + xwidth <= x branch (ORDER_IF) - no overlap
        // b1: x=10-50, query: x=51-100 (no overlap)
        boolean result4 = b1.intersects(51, 20, 30, 50, 50, 60);
        assertFalse(result4);
    }


    /**
     * Test conditional branches in containment calculations
     */
    public void testContainmentConditionals() {
        Balloon b1 = new Balloon("B1", 10, 20, 30, 40, 50, 60, "hot", 1);

        // Test xorig >= x branch (ORDER_IF)
        boolean result1 = b1.isContainedIn(0, 0, 0, 200, 200, 200);
        assertTrue(result1);

        // Test xorig < x branch (ORDER_IF)
        boolean result2 = b1.isContainedIn(20, 0, 0, 200, 200, 200);
        assertFalse(result2);

        // Test xorig + xwidth <= x + xwid branch (ORDER_IF)
        boolean result3 = b1.isContainedIn(10, 20, 30, 40, 50, 60);
        assertTrue(result3);

        // Test xorig + xwidth > x + xwid branch (ORDER_IF)
        boolean result4 = b1.isContainedIn(10, 20, 30, 30, 50, 60);
        assertFalse(result4);
    }


    /**
     * Test conditional branches in compareTo (EQUAL_IF mutations)
     */
    public void testCompareToConditionals() {
        Balloon b1 = new Balloon("A", 0, 0, 0, 1, 1, 1, "hot", 1);
        Balloon b2 = new Balloon("B", 0, 0, 0, 1, 1, 1, "hot", 1);

        // Test name == null branch (EQUAL_IF)
        int comp1 = b1.compareTo(null);
        assertTrue(comp1 > 0);

        // Test name != null branch (EQUAL_IF)
        int comp2 = b1.compareTo(b2);
        assertTrue(comp2 < 0);

        // Test this.name == null branch (EQUAL_IF)
        Balloon b3 = new Balloon(null, 0, 0, 0, 1, 1, 1, "hot", 1);
        int comp3 = b3.compareTo(b1);
        assertTrue(comp3 < 0);

        // Test this.name != null branch (EQUAL_IF)
        int comp4 = b1.compareTo(b3);
        assertTrue(comp4 > 0);

        // Test this.name == null && other.name == null branch (EQUAL_IF)
        Balloon b4 = new Balloon(null, 0, 0, 0, 1, 1, 1, "hot", 1);
        int comp5 = b3.compareTo(b4);
        assertEquals(0, comp5);

        // Test name comparison == 0 branch (EQUAL_IF)
        Balloon b5 = new Balloon("A", 0, 0, 0, 1, 1, 1, "hot", 1);
        int comp6 = b1.compareTo(b5);
        assertEquals(0, comp6);

        // Test name comparison != 0 branch (EQUAL_IF)
        int comp7 = b1.compareTo(b2);
        assertTrue(comp7 != 0);
    }


    /**
     * Test conditional branches in SkipList operations
     */
    public void testSkipListConditionals() {
        java.util.Random rnd = new java.util.Random();
        rnd.setSeed(0xCAFEBEEF);
        SkipList<String> list = new SkipList<String>(rnd);

        // Test isEmpty() == true branch (EQUAL_IF)
        boolean empty1 = list.isEmpty();
        assertTrue(empty1);

        // Test isEmpty() == false branch (EQUAL_IF)
        list.insert("A");
        boolean empty2 = list.isEmpty();
        assertFalse(empty2);

        // Test size == 0 branch (EQUAL_IF)
        SkipList<String> list2 = new SkipList<String>(rnd);
        int size1 = list2.size();
        assertEquals(0, size1);

        // Test size != 0 branch (EQUAL_IF)
        list2.insert("B");
        int size2 = list2.size();
        assertTrue(size2 != 0);

        // Test find() == null branch (EQUAL_IF)
        String found1 = list2.find("C");
        assertNull(found1);

        // Test find() != null branch (EQUAL_IF)
        String found2 = list2.find("B");
        assertNotNull(found2);
        assertEquals("B", found2);
    }


    /**
     * Test conditional branches in Bintree operations
     */
    public void testBintreeConditionals() {
        Bintree tree = new Bintree();

        // Test empty tree conditionals
        String result1 = tree.print();
        assertNotNull(result1);
        assertTrue(result1.contains("E (0, 0, 0, 1024, 1024, 1024) 0"));

        // Test with objects
        Balloon b1 = new Balloon("B1", 10, 10, 10, 20, 20, 20, "hot", 1);
        tree.insert(b1);

        String result2 = tree.print();
        assertNotNull(result2);
        assertTrue(result2.contains("B1"));

        // Test collisions with no collisions
        String collisions1 = tree.findCollisions();
        assertNotNull(collisions1);

        // Test collisions with collisions
        Balloon b2 = new Balloon("B2", 15, 15, 15, 20, 20, 20, "hot", 1);
        tree.insert(b2);
        String collisions2 = tree.findCollisions();
        assertNotNull(collisions2);
    }


    /**
     * Test conditional branches in WorldDB operations
     */
    public void testWorldDBConditionals() {
        java.util.Random rnd = new java.util.Random();
        rnd.setSeed(0xCAFEBEEF);
        WorldDB world = new WorldDB(rnd);

        // Test add() == true branch (EQUAL_IF)
        boolean added1 = world.add(new Balloon("B1", 10, 20, 30, 40, 50, 60,
            "hot", 1));
        assertTrue(added1);

        // Test add() == false branch (EQUAL_IF) - duplicate
        boolean added2 = world.add(new Balloon("B1", 10, 20, 30, 40, 50, 60,
            "hot", 1));
        assertFalse(added2);

        // Test add() == false branch (EQUAL_IF) - invalid
        boolean added3 = world.add(new Balloon(null, 10, 20, 30, 40, 50, 60,
            "hot", 1));
        assertFalse(added3);

        // Test delete() == null branch (EQUAL_IF)
        String deleted1 = world.delete("Nonexistent");
        assertNull(deleted1);

        // Test delete() != null branch (EQUAL_IF)
        String deleted2 = world.delete("B1");
        assertNotNull(deleted2);

        // Test print() == null branch (EQUAL_IF)
        String printed1 = world.print("Nonexistent");
        assertNull(printed1);

        // Test print() != null branch (EQUAL_IF)
        world.add(new Balloon("B2", 10, 20, 30, 40, 50, 60, "hot", 1));
        String printed2 = world.print("B2");
        assertNotNull(printed2);
    }


    /**
     * Test conditional branches in AirObject type-specific validation
     */
    public void testTypeSpecificConditionals() {
        // Test Balloon type == null branch (EQUAL_IF)
        Balloon b1 = new Balloon("B1", 1, 1, 1, 1, 1, 1, null, 1);
        assertFalse(b1.isValid());

        // Test Balloon type != null branch (EQUAL_IF)
        Balloon b2 = new Balloon("B2", 1, 1, 1, 1, 1, 1, "hot", 1);
        assertTrue(b2.isValid());

        // Test Balloon ascentRate < 0 branch (ORDER_IF)
        Balloon b3 = new Balloon("B3", 1, 1, 1, 1, 1, 1, "hot", -1);
        assertFalse(b3.isValid());

        // Test Balloon ascentRate >= 0 branch (ORDER_IF)
        Balloon b4 = new Balloon("B4", 1, 1, 1, 1, 1, 1, "hot", 0);
        assertTrue(b4.isValid());

        // Test AirPlane carrier == null branch (EQUAL_IF)
        AirPlane a1 = new AirPlane("A1", 1, 1, 1, 1, 1, 1, null, 1, 1);
        assertFalse(a1.isValid());

        // Test AirPlane carrier != null branch (EQUAL_IF)
        AirPlane a2 = new AirPlane("A2", 1, 1, 1, 1, 1, 1, "USAir", 1, 1);
        assertTrue(a2.isValid());

        // Test AirPlane flightNumber == 0 branch (EQUAL_IF)
        AirPlane a3 = new AirPlane("A3", 1, 1, 1, 1, 1, 1, "USAir", 0, 1);
        assertFalse(a3.isValid());

        // Test AirPlane flightNumber != 0 branch (EQUAL_IF)
        AirPlane a4 = new AirPlane("A4", 1, 1, 1, 1, 1, 1, "USAir", 1, 1);
        assertTrue(a4.isValid());

        // Test AirPlane numEngines == 0 branch (EQUAL_IF)
        AirPlane a5 = new AirPlane("A5", 1, 1, 1, 1, 1, 1, "USAir", 1, 0);
        assertFalse(a5.isValid());

        // Test AirPlane numEngines != 0 branch (EQUAL_IF)
        AirPlane a6 = new AirPlane("A6", 1, 1, 1, 1, 1, 1, "USAir", 1, 1);
        assertTrue(a6.isValid());
    }


    /**
     * Test conditional branches in Bintree split dimension
     */
    public void testBintreeSplitDimensionConditionals() {
        Bintree tree = new Bintree();

        // Test splitDim == 0 branch (EQUAL_IF) - split on x
        // Insert objects that will cause split on x dimension
        Balloon b1 = new Balloon("B1", 100, 100, 100, 50, 50, 50, "hot", 1);
        Balloon b2 = new Balloon("B2", 200, 100, 100, 50, 50, 50, "hot", 1);
        Balloon b3 = new Balloon("B3", 300, 100, 100, 50, 50, 50, "hot", 1);
        Balloon b4 = new Balloon("B4", 400, 100, 100, 50, 50, 50, "hot", 1);
        tree.insert(b1);
        tree.insert(b2);
        tree.insert(b3);
        tree.insert(b4);

        String result1 = tree.print();
        assertNotNull(result1);

        // Test splitDim == 1 branch (EQUAL_IF) - split on y
        Bintree tree2 = new Bintree();
        Balloon b5 = new Balloon("B5", 100, 100, 100, 50, 50, 50, "hot", 1);
        Balloon b6 = new Balloon("B6", 100, 200, 100, 50, 50, 50, "hot", 1);
        Balloon b7 = new Balloon("B7", 100, 300, 100, 50, 50, 50, "hot", 1);
        Balloon b8 = new Balloon("B8", 100, 400, 100, 50, 50, 50, "hot", 1);
        tree2.insert(b5);
        tree2.insert(b6);
        tree2.insert(b7);
        tree2.insert(b8);

        String result2 = tree2.print();
        assertNotNull(result2);

        // Test splitDim == 2 branch (EQUAL_IF) - split on z
        Bintree tree3 = new Bintree();
        Balloon b9 = new Balloon("B9", 100, 100, 100, 50, 50, 50, "hot", 1);
        Balloon b10 = new Balloon("B10", 100, 100, 200, 50, 50, 50, "hot", 1);
        Balloon b11 = new Balloon("B11", 100, 100, 300, 50, 50, 50, "hot", 1);
        Balloon b12 = new Balloon("B12", 100, 100, 400, 50, 50, 50, "hot", 1);
        tree3.insert(b9);
        tree3.insert(b10);
        tree3.insert(b11);
        tree3.insert(b12);

        String result3 = tree3.print();
        assertNotNull(result3);
    }


    /**
     * Test conditional branches in intersection null checks
     */
    public void testIntersectionNullConditionals() {
        Balloon b1 = new Balloon("B1", 10, 20, 30, 40, 50, 60, "hot", 1);

        // Test other == null branch (EQUAL_IF)
        boolean result1 = b1.intersects((AirObject)null);
        assertFalse(result1);

        // Test other != null branch (EQUAL_IF)
        Balloon b2 = new Balloon("B2", 20, 30, 40, 40, 50, 60, "hot", 1);
        boolean result2 = b1.intersects(b2);
        assertTrue(result2);
    }
}
