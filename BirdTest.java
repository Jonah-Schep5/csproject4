import student.TestCase;

/**
 * Test class for Bird
 * Tests validation and toString with mutation coverage
 *
 * @author CS3114/5040 Staff
 * @version Fall 2025
 */
public class BirdTest extends TestCase {

    /**
     * Test valid Bird creation
     */
    public void testValidBird() {
        Bird b = new Bird("B1", 10, 20, 30, 40, 50, 60, "Eagle", 5);
        assertTrue(b.isValid());
        assertEquals("Eagle", b.getType());
        assertEquals(5, b.getNumber());
    }


    /**
     * Test validation - null name
     */
    public void testInvalidNullName() {
        Bird b = new Bird(null, 1, 1, 1, 1, 1, 1, "Eagle", 1);
        assertFalse(b.isValid());
    }

    /**
     * Test validation - null type
     */
    public void testInvalidNullType() {
        Bird b = new Bird("B1", 1, 1, 1, 1, 1, 1, null, 1);
        assertFalse(b.isValid());
    }

    /**
     * Test validation - zero number
     */
    public void testInvalidZeroNumber() {
        Bird b = new Bird("B1", 1, 1, 1, 1, 1, 1, "Eagle", 0);
        assertFalse(b.isValid());
    }

    /**
     * Test validation - negative coordinates
     */
    public void testInvalidNegativeCoordinates() {
        Bird b1 = new Bird("B1", -1, 1, 1, 1, 1, 1, "Eagle", 1);
        assertFalse(b1.isValid());
        
        Bird b2 = new Bird("B1", 1, -1, 1, 1, 1, 1, "Eagle", 1);
        assertFalse(b2.isValid());
        
        Bird b3 = new Bird("B1", 1, 1, -1, 1, 1, 1, "Eagle", 1);
        assertFalse(b3.isValid());
    }

    /**
     * Test validation - coordinates too large
     */
    public void testInvalidCoordinatesTooLarge() {
        Bird b1 = new Bird("B1", 1024, 1, 1, 1, 1, 1, "Eagle", 1);
        assertFalse(b1.isValid());
        
        Bird b2 = new Bird("B1", 1, 1024, 1, 1, 1, 1, "Eagle", 1);
        assertFalse(b2.isValid());
        
        Bird b3 = new Bird("B1", 1, 1, 1024, 1, 1, 1, "Eagle", 1);
        assertFalse(b3.isValid());
    }

    /**
     * Test validation - zero widths
     */
    public void testInvalidZeroWidths() {
        Bird b1 = new Bird("B1", 1, 1, 1, 0, 1, 1, "Eagle", 1);
        assertFalse(b1.isValid());
        
        Bird b2 = new Bird("B1", 1, 1, 1, 1, 0, 1, "Eagle", 1);
        assertFalse(b2.isValid());
        
        Bird b3 = new Bird("B1", 1, 1, 1, 1, 1, 0, "Eagle", 1);
        assertFalse(b3.isValid());
    }

    /**
     * Test validation - extends beyond world
     */
    public void testInvalidExtendsBeyond() {
        Bird b1 = new Bird("B1", 1000, 1, 1, 100, 1, 1, "Eagle", 1);
        assertFalse(b1.isValid());
        
        Bird b2 = new Bird("B1", 1, 1000, 1, 1, 100, 1, "Eagle", 1);
        assertFalse(b2.isValid());
        
        Bird b3 = new Bird("B1", 1, 1, 1000, 1, 1, 100, "Eagle", 1);
        assertFalse(b3.isValid());
    }

    /**
     * Test validation - boundary values
     */
    public void testBoundaryValues() {
        Bird b1 = new Bird("B1", 0, 0, 0, 1, 1, 1, "Eagle", 1);
        assertTrue(b1.isValid());
        
        Bird b2 = new Bird("B2", 1023, 1023, 1023, 1, 1, 1, "Eagle", 100);
        assertTrue(b2.isValid());
    }
}

