import student.TestCase;

/**
 * Test class for Balloon
 * Tests validation and toString with mutation coverage
 *
 * @author Jonah Schepers
 * @author Rowan Muhoberac
 * @version December 8, 2025
 */
public class BalloonTest extends TestCase {

    /**
     * Test valid Balloon creation
     */
    public void testValidBalloon() {
        Balloon b = new Balloon("B1", 10, 20, 30, 40, 50, 60, "hot_air", 15);
        boolean valid = b.isValid();
        String type = b.getType();
        int rate = b.getAscentRate();
        String toString = b.toString();

        assertTrue(valid);
        assertEquals("hot_air", type);
        assertEquals(15, rate);
        assertEquals("Balloon B1 10 20 30 40 50 60 hot_air 15", toString);
    }


    /**
     * Test validation - null/empty name
     */
    public void testInvalidName() {
        Balloon b1 = new Balloon(null, 1, 1, 1, 1, 1, 1, "hot", 5);
        assertFalse(b1.isValid());

        Balloon b2 = new Balloon("", 1, 1, 1, 1, 1, 1, "hot", 5);
        assertFalse(b2.isValid());
    }


    /**
     * Test validation - negative coordinates
     */
    public void testInvalidNegativeCoordinates() {
        Balloon b1 = new Balloon("B1", -1, 1, 1, 1, 1, 1, "hot", 5);
        assertFalse(b1.isValid());

        Balloon b2 = new Balloon("B1", 1, -1, 1, 1, 1, 1, "hot", 5);
        assertFalse(b2.isValid());

        Balloon b3 = new Balloon("B1", 1, 1, -1, 1, 1, 1, "hot", 5);
        assertFalse(b3.isValid());
    }


    /**
     * Test validation - coordinates too large
     */
    public void testInvalidCoordinatesTooLarge() {
        Balloon b1 = new Balloon("B1", 1024, 1, 1, 1, 1, 1, "hot", 5);
        assertFalse(b1.isValid());

        Balloon b2 = new Balloon("B1", 1, 1024, 1, 1, 1, 1, "hot", 5);
        assertFalse(b2.isValid());

        Balloon b3 = new Balloon("B1", 1, 1, 1024, 1, 1, 1, "hot", 5);
        assertFalse(b3.isValid());
    }


    /**
     * Test validation - zero widths
     */
    public void testInvalidZeroWidths() {
        Balloon b1 = new Balloon("B1", 1, 1, 1, 0, 1, 1, "hot", 5);
        assertFalse(b1.isValid());

        Balloon b2 = new Balloon("B1", 1, 1, 1, 1, 0, 1, "hot", 5);
        assertFalse(b2.isValid());

        Balloon b3 = new Balloon("B1", 1, 1, 1, 1, 1, 0, "hot", 5);
        assertFalse(b3.isValid());
    }


    /**
     * Test validation - width too large
     */
    public void testInvalidWidthTooLarge() {
        Balloon b = new Balloon("B1", 1, 1, 1, 1025, 1, 1, "hot", 5);
        assertFalse(b.isValid());
    }


    /**
     * Test validation - extends beyond world
     */
    public void testInvalidExtendsBeyond() {
        Balloon b1 = new Balloon("B1", 1000, 1, 1, 100, 1, 1, "hot", 5);
        assertFalse(b1.isValid());

        Balloon b2 = new Balloon("B1", 1, 1000, 1, 1, 100, 1, "hot", 5);
        assertFalse(b2.isValid());

        Balloon b3 = new Balloon("B1", 1, 1, 1000, 1, 1, 100, "hot", 5);
        assertFalse(b3.isValid());
    }


    /**
     * Test validation - null type
     */
    public void testInvalidNullType() {
        Balloon b = new Balloon("B1", 1, 1, 1, 1, 1, 1, null, 5);
        boolean valid = b.isValid();
        String toString = b.toString();
        assertFalse(valid);
        assertNotNull(toString);
    }


    /**
     * Test validation - negative ascent rate
     */
    public void testInvalidNegativeAscentRate() {
        Balloon b = new Balloon("B1", 1, 1, 1, 1, 1, 1, "hot", -1);
        boolean valid = b.isValid();
        String toString = b.toString();
        assertFalse(valid);
        assertNotNull(toString);
    }


    /**
     * Test validation - boundary values (valid)
     */
    public void testBoundaryValues() {
        // Minimum valid values
        Balloon b1 = new Balloon("B1", 0, 0, 0, 1, 1, 1, "hot", 0);
        boolean valid1 = b1.isValid();
        String toString1 = b1.toString();
        assertTrue(valid1);
        assertEquals("Balloon B1 0 0 0 1 1 1 hot 0", toString1);

        // Maximum valid values
        Balloon b2 = new Balloon("B2", 1023, 1023, 1023, 1, 1, 1, "hot", 1000);
        boolean valid2 = b2.isValid();
        String toString2 = b2.toString();
        assertTrue(valid2);
        assertEquals("Balloon B2 1023 1023 1023 1 1 1 hot 1000", toString2);

        // Maximum width at origin 0
        Balloon b3 = new Balloon("B3", 0, 0, 0, 1024, 1024, 1024, "hot", 1000);
        boolean valid3 = b3.isValid();
        String toString3 = b3.toString();
        assertTrue(valid3);
        assertEquals("Balloon B3 0 0 0 1024 1024 1024 hot 1000", toString3);
    }
}
