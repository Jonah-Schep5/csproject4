import student.TestCase;

/**
 * Test class for AirObject base class
 * Tests all methods and ensures mutation coverage
 *
 * @author Jonah Schepers
 * @author Rowan Muhoberac
 * @version December 8, 2025
 */
public class AirObjectTest extends TestCase {

    /**
     * Test getter methods
     */
    public void testGetters() {
        Balloon b = new Balloon("Test", 10, 20, 30, 40, 50, 60, "hot", 5);
        int x = b.getXorig();
        int y = b.getYorig();
        int z = b.getZorig();
        int xw = b.getXwidth();
        int yw = b.getYwidth();
        int zw = b.getZwidth();
        String name = b.getName();
        String toString = b.toString();

        assertEquals(10, x);
        assertEquals(20, y);
        assertEquals(30, z);
        assertEquals(40, xw);
        assertEquals(50, yw);
        assertEquals(60, zw);
        assertEquals("Test", name);
        assertEquals("Balloon Test 10 20 30 40 50 60 hot 5", toString);
    }


    /**
     * Test compareTo method
     */
    public void testCompareTo() {
        Balloon b1 = new Balloon("A", 0, 0, 0, 1, 1, 1, "hot", 1);
        Balloon b2 = new Balloon("B", 0, 0, 0, 1, 1, 1, "hot", 1);
        Balloon b3 = new Balloon("A", 0, 0, 0, 1, 1, 1, "hot", 1);

        int comp1 = b1.compareTo(b2);
        int comp2 = b2.compareTo(b1);
        int comp3 = b1.compareTo(b3);
        int comp4 = b1.compareTo(null);
        String toString1 = b1.toString();
        String toString2 = b2.toString();
        String toString3 = b3.toString();

        assertTrue(comp1 < 0);
        assertTrue(comp2 > 0);
        assertEquals(0, comp3);
        assertTrue(comp4 > 0);
        assertEquals("Balloon A 0 0 0 1 1 1 hot 1", toString1);
        assertEquals("Balloon B 0 0 0 1 1 1 hot 1", toString2);
        assertEquals("Balloon A 0 0 0 1 1 1 hot 1", toString3);
    }


    /**
     * Test intersects method with another AirObject
     */
    public void testIntersectsAirObject() {
        Balloon b1 = new Balloon("B1", 0, 0, 0, 10, 10, 10, "hot", 1);
        Balloon b2 = new Balloon("B2", 5, 5, 5, 10, 10, 10, "hot", 1);
        Balloon b3 = new Balloon("B3", 20, 20, 20, 10, 10, 10, "hot", 1);

        String toString1 = b1.toString();
        String toString2 = b2.toString();
        String toString3 = b3.toString();
        assertEquals("Balloon B1 0 0 0 10 10 10 hot 1", toString1);
        assertEquals("Balloon B2 5 5 5 10 10 10 hot 1", toString2);
        assertEquals("Balloon B3 20 20 20 10 10 10 hot 1", toString3);

        // Overlapping boxes
        boolean result1 = b1.intersects(b2);
        boolean result2 = b2.intersects(b1);
        assertTrue(result1);
        assertTrue(result2);

        // Non-overlapping boxes
        boolean result3 = b1.intersects(b3);
        boolean result4 = b3.intersects(b1);
        assertFalse(result3);
        assertFalse(result4);

        // Adjacent but not overlapping
        Balloon b4 = new Balloon("B4", 10, 0, 0, 10, 10, 10, "hot", 1);
        String toString4 = b4.toString();
        assertEquals("Balloon B4 10 0 0 10 10 10 hot 1", toString4);
        boolean result5 = b1.intersects(b4);
        assertFalse(result5);

        // Test with null
        boolean result6 = b1.intersects(null);
        assertFalse(result6);
    }


    /**
     * Test intersects method with bounding box parameters
     */
    public void testIntersectsBox() {
        Balloon b = new Balloon("B1", 10, 20, 30, 40, 50, 60, "hot", 1);
        String toString = b.toString();
        assertEquals("Balloon B1 10 20 30 40 50 60 hot 1", toString);

        // Overlapping box
        boolean result1 = b.intersects(20, 30, 40, 50, 60, 70);
        assertTrue(result1);

        // Non-overlapping box
        boolean result2 = b.intersects(100, 100, 100, 10, 10, 10);
        assertFalse(result2);

        // Box that contains the object
        boolean result3 = b.intersects(0, 0, 0, 200, 200, 200);
        assertTrue(result3);

        // Box inside the object
        boolean result4 = b.intersects(20, 30, 40, 10, 10, 10);
        assertTrue(result4);

        // Adjacent but not overlapping
        boolean result5 = b.intersects(50, 20, 30, 10, 50, 60);
        assertFalse(result5);
    }


    /**
     * Test isContainedIn method
     */
    public void testIsContainedIn() {
        Balloon b = new Balloon("B1", 10, 20, 30, 40, 50, 60, "hot", 1);
        String toString = b.toString();
        assertEquals("Balloon B1 10 20 30 40 50 60 hot 1", toString);

        // Contained in larger box
        boolean result1 = b.isContainedIn(0, 0, 0, 200, 200, 200);
        assertTrue(result1);

        // Not contained (extends beyond)
        boolean result2 = b.isContainedIn(0, 0, 0, 30, 30, 30);
        assertFalse(result2);

        // Exactly contained
        boolean result3 = b.isContainedIn(10, 20, 30, 40, 50, 60);
        assertTrue(result3);

        // Partially contained
        boolean result4 = b.isContainedIn(15, 20, 30, 30, 50, 60);
        assertFalse(result4);

        // Test boundary conditions for arithmetic operations
        Balloon b2 = new Balloon("B2", 0, 0, 0, 1, 1, 1, "hot", 1);
        String toString2 = b2.toString();
        assertEquals("Balloon B2 0 0 0 1 1 1 hot 1", toString2);
        boolean result5 = b2.isContainedIn(0, 0, 0, 1, 1, 1);
        boolean result6 = b2.isContainedIn(1, 0, 0, 1, 1, 1);
        boolean result7 = b2.isContainedIn(0, 1, 0, 1, 1, 1);
        boolean result8 = b2.isContainedIn(0, 0, 1, 1, 1, 1);
        assertTrue(result5);
        assertFalse(result6);
        assertFalse(result7);
        assertFalse(result8);
    }


    /**
     * Test intersects with edge cases for arithmetic
     */
    public void testIntersectsEdgeCases() {
        // Test exact boundaries
        Balloon b1 = new Balloon("B1", 0, 0, 0, 10, 10, 10, "hot", 1);
        Balloon b2 = new Balloon("B2", 10, 0, 0, 10, 10, 10, "hot", 1);
        String toString1 = b1.toString();
        String toString2 = b2.toString();
        assertEquals("Balloon B1 0 0 0 10 10 10 hot 1", toString1);
        assertEquals("Balloon B2 10 0 0 10 10 10 hot 1", toString2);

        // Adjacent but not overlapping
        boolean result1 = b1.intersects(b2);
        assertFalse(result1);

        // Overlapping by 1 unit
        Balloon b3 = new Balloon("B3", 9, 0, 0, 10, 10, 10, "hot", 1);
        String toString3 = b3.toString();
        assertEquals("Balloon B3 9 0 0 10 10 10 hot 1", toString3);
        boolean result2 = b1.intersects(b3);
        assertTrue(result2);

        // Test with box parameters - exact boundaries
        boolean result3 = b1.intersects(10, 0, 0, 10, 10, 10);
        boolean result4 = b1.intersects(9, 0, 0, 10, 10, 10);
        assertFalse(result3);
        assertTrue(result4);
    }
}
