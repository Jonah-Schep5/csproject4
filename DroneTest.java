import student.TestCase;

/**
 * Test class for Drone
 * Tests validation and toString with mutation coverage
 *
 * @author Jonah Schepers
 * @author Rowan Muhoberac
 * @version December 8, 2025
 */
public class DroneTest extends TestCase {

    /**
     * Test valid Drone creation
     */
    public void testValidDrone() {
        Drone d = new Drone("D1", 10, 20, 30, 40, 50, 60, "Droners", 3);
        assertTrue(d.isValid());
        assertEquals("Droners", d.getBrand());
        assertEquals(3, d.getNumEngines());
    }


    /**
     * Test validation - null name
     */
    public void testInvalidNullName() {
        Drone d = new Drone(null, 1, 1, 1, 1, 1, 1, "Droners", 1);
        assertFalse(d.isValid());
    }


    /**
     * Test validation - null brand
     */
    public void testInvalidNullBrand() {
        Drone d = new Drone("D1", 1, 1, 1, 1, 1, 1, null, 1);
        assertFalse(d.isValid());
    }


    /**
     * Test validation - zero engines
     */
    public void testInvalidZeroEngines() {
        Drone d = new Drone("D1", 1, 1, 1, 1, 1, 1, "Droners", 0);
        assertFalse(d.isValid());
    }


    /**
     * Test validation - negative coordinates
     */
    public void testInvalidNegativeCoordinates() {
        Drone d1 = new Drone("D1", -1, 1, 1, 1, 1, 1, "Droners", 1);
        assertFalse(d1.isValid());

        Drone d2 = new Drone("D1", 1, -1, 1, 1, 1, 1, "Droners", 1);
        assertFalse(d2.isValid());

        Drone d3 = new Drone("D1", 1, 1, -1, 1, 1, 1, "Droners", 1);
        assertFalse(d3.isValid());
    }


    /**
     * Test validation - coordinates too large
     */
    public void testInvalidCoordinatesTooLarge() {
        Drone d1 = new Drone("D1", 1024, 1, 1, 1, 1, 1, "Droners", 1);
        assertFalse(d1.isValid());

        Drone d2 = new Drone("D1", 1, 1024, 1, 1, 1, 1, "Droners", 1);
        assertFalse(d2.isValid());

        Drone d3 = new Drone("D1", 1, 1, 1024, 1, 1, 1, "Droners", 1);
        assertFalse(d3.isValid());
    }


    /**
     * Test validation - zero widths
     */
    public void testInvalidZeroWidths() {
        Drone d1 = new Drone("D1", 1, 1, 1, 0, 1, 1, "Droners", 1);
        assertFalse(d1.isValid());

        Drone d2 = new Drone("D1", 1, 1, 1, 1, 0, 1, "Droners", 1);
        assertFalse(d2.isValid());

        Drone d3 = new Drone("D1", 1, 1, 1, 1, 1, 0, "Droners", 1);
        assertFalse(d3.isValid());
    }


    /**
     * Test validation - extends beyond world
     */
    public void testInvalidExtendsBeyond() {
        Drone d1 = new Drone("D1", 1000, 1, 1, 100, 1, 1, "Droners", 1);
        assertFalse(d1.isValid());

        Drone d2 = new Drone("D1", 1, 1000, 1, 1, 100, 1, "Droners", 1);
        assertFalse(d2.isValid());

        Drone d3 = new Drone("D1", 1, 1, 1000, 1, 1, 100, "Droners", 1);
        assertFalse(d3.isValid());
    }


    /**
     * Test validation - boundary values
     */
    public void testBoundaryValues() {
        Drone d1 = new Drone("D1", 0, 0, 0, 1, 1, 1, "Droners", 1);
        assertTrue(d1.isValid());

        Drone d2 = new Drone("D2", 1023, 1023, 1023, 1, 1, 1, "Droners", 10);
        assertTrue(d2.isValid());
    }
}
