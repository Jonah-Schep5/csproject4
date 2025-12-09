import student.TestCase;

/**
 * Test class for Rocket
 * Tests validation and toString with mutation coverage
 *
 * @author Jonah Schepers
 * @author Rowan Muhoberac
 * @version December 8, 2025
 */
public class RocketTest extends TestCase {

    /**
     * Test valid Rocket creation
     */
    public void testValidRocket() {
        Rocket r = new Rocket("R1", 10, 20, 30, 40, 50, 60, 5000, 99.29);
        assertTrue(r.isValid());
        assertEquals(5000, r.getAscentRate());
        assertEquals(99.29, r.getTrajectory(), 0.001);
    }


    /**
     * Test validation - null name
     */
    public void testInvalidNullName() {
        Rocket r = new Rocket(null, 1, 1, 1, 1, 1, 1, 1, 1.0);
        assertFalse(r.isValid());
    }


    /**
     * Test validation - negative ascent rate
     */
    public void testInvalidNegativeAscentRate() {
        Rocket r = new Rocket("R1", 1, 1, 1, 1, 1, 1, -1, 1.0);
        assertFalse(r.isValid());
    }


    /**
     * Test validation - negative trajectory
     */
    public void testInvalidNegativeTrajectory() {
        Rocket r = new Rocket("R1", 1, 1, 1, 1, 1, 1, 1, -1.0);
        assertFalse(r.isValid());
    }


    /**
     * Test validation - zero ascent rate (valid)
     */
    public void testValidZeroAscentRate() {
        Rocket r = new Rocket("R1", 1, 1, 1, 1, 1, 1, 0, 1.0);
        assertTrue(r.isValid());
    }


    /**
     * Test validation - zero trajectory (valid)
     */
    public void testValidZeroTrajectory() {
        Rocket r = new Rocket("R1", 1, 1, 1, 1, 1, 1, 1, 0.0);
        assertTrue(r.isValid());
    }


    /**
     * Test validation - negative coordinates
     */
    public void testInvalidNegativeCoordinates() {
        Rocket r1 = new Rocket("R1", -1, 1, 1, 1, 1, 1, 1, 1.0);
        assertFalse(r1.isValid());

        Rocket r2 = new Rocket("R1", 1, -1, 1, 1, 1, 1, 1, 1.0);
        assertFalse(r2.isValid());

        Rocket r3 = new Rocket("R1", 1, 1, -1, 1, 1, 1, 1, 1.0);
        assertFalse(r3.isValid());
    }


    /**
     * Test validation - coordinates too large
     */
    public void testInvalidCoordinatesTooLarge() {
        Rocket r1 = new Rocket("R1", 1024, 1, 1, 1, 1, 1, 1, 1.0);
        assertFalse(r1.isValid());

        Rocket r2 = new Rocket("R1", 1, 1024, 1, 1, 1, 1, 1, 1.0);
        assertFalse(r2.isValid());

        Rocket r3 = new Rocket("R1", 1, 1, 1024, 1, 1, 1, 1, 1.0);
        assertFalse(r3.isValid());
    }


    /**
     * Test validation - zero widths
     */
    public void testInvalidZeroWidths() {
        Rocket r1 = new Rocket("R1", 1, 1, 1, 0, 1, 1, 1, 1.0);
        assertFalse(r1.isValid());

        Rocket r2 = new Rocket("R1", 1, 1, 1, 1, 0, 1, 1, 1.0);
        assertFalse(r2.isValid());

        Rocket r3 = new Rocket("R1", 1, 1, 1, 1, 1, 0, 1, 1.0);
        assertFalse(r3.isValid());
    }


    /**
     * Test validation - extends beyond world
     */
    public void testInvalidExtendsBeyond() {
        Rocket r1 = new Rocket("R1", 1000, 1, 1, 100, 1, 1, 1, 1.0);
        assertFalse(r1.isValid());

        Rocket r2 = new Rocket("R1", 1, 1000, 1, 1, 100, 1, 1, 1.0);
        assertFalse(r2.isValid());

        Rocket r3 = new Rocket("R1", 1, 1, 1000, 1, 1, 100, 1, 1.0);
        assertFalse(r3.isValid());
    }


    /**
     * Test validation - boundary values
     */
    public void testBoundaryValues() {
        Rocket r1 = new Rocket("R1", 0, 0, 0, 1, 1, 1, 0, 0.0);
        assertTrue(r1.isValid());

        Rocket r2 = new Rocket("R2", 1023, 1023, 1023, 1, 1, 1, 10000, 999.99);
        assertTrue(r2.isValid());
    }
}
