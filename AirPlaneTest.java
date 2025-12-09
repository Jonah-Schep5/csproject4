import student.TestCase;

/**
 * Test class for AirPlane
 * Tests validation and toString with mutation coverage
 *
 * @author Jonah Schepers
 * @author Rowan Muhoberac
 * @version December 8, 2025
 */
public class AirPlaneTest extends TestCase {

    /**
     * Test valid AirPlane creation
     */
    public void testValidAirPlane() {
        AirPlane a = new AirPlane("A1", 10, 20, 30, 40, 50, 60, "USAir", 717,
            4);
        boolean valid = a.isValid();
        String carrier = a.getCarrier();
        int flightNum = a.getFlightNumber();
        int engines = a.getNumEngines();
        String toString = a.toString();

        assertTrue(valid);
        assertEquals("USAir", carrier);
        assertEquals(717, flightNum);
        assertEquals(4, engines);
        assertEquals("Airplane A1 10 20 30 40 50 60 USAir 717 4", toString);
    }


    /**
     * Test validation - null name
     */
    public void testInvalidNullName() {
        AirPlane a = new AirPlane(null, 1, 1, 1, 1, 1, 1, "USAir", 1, 1);
        boolean valid = a.isValid();
        String toString = a.toString();
        assertFalse(valid);
        assertNotNull(toString);
    }


    /**
     * Test validation - null carrier
     */
    public void testInvalidNullCarrier() {
        AirPlane a = new AirPlane("A1", 1, 1, 1, 1, 1, 1, null, 1, 1);
        boolean valid = a.isValid();
        String toString = a.toString();
        assertFalse(valid);
        assertNotNull(toString);
    }


    /**
     * Test validation - zero flight number
     */
    public void testInvalidZeroFlightNumber() {
        AirPlane a = new AirPlane("A1", 1, 1, 1, 1, 1, 1, "USAir", 0, 1);
        boolean valid = a.isValid();
        String toString = a.toString();
        assertFalse(valid);
        assertNotNull(toString);
    }


    /**
     * Test validation - zero engines
     */
    public void testInvalidZeroEngines() {
        AirPlane a = new AirPlane("A1", 1, 1, 1, 1, 1, 1, "USAir", 1, 0);
        boolean valid = a.isValid();
        String toString = a.toString();
        assertFalse(valid);
        assertNotNull(toString);
    }


    /**
     * Test validation - negative coordinates
     */
    public void testInvalidNegativeCoordinates() {
        AirPlane a1 = new AirPlane("A1", -1, 1, 1, 1, 1, 1, "USAir", 1, 1);
        assertFalse(a1.isValid());

        AirPlane a2 = new AirPlane("A1", 1, -1, 1, 1, 1, 1, "USAir", 1, 1);
        assertFalse(a2.isValid());

        AirPlane a3 = new AirPlane("A1", 1, 1, -1, 1, 1, 1, "USAir", 1, 1);
        assertFalse(a3.isValid());
    }


    /**
     * Test validation - coordinates too large
     */
    public void testInvalidCoordinatesTooLarge() {
        AirPlane a1 = new AirPlane("A1", 1024, 1, 1, 1, 1, 1, "USAir", 1, 1);
        assertFalse(a1.isValid());

        AirPlane a2 = new AirPlane("A1", 1, 1024, 1, 1, 1, 1, "USAir", 1, 1);
        assertFalse(a2.isValid());

        AirPlane a3 = new AirPlane("A1", 1, 1, 1024, 1, 1, 1, "USAir", 1, 1);
        assertFalse(a3.isValid());
    }


    /**
     * Test validation - zero widths
     */
    public void testInvalidZeroWidths() {
        AirPlane a1 = new AirPlane("A1", 1, 1, 1, 0, 1, 1, "USAir", 1, 1);
        assertFalse(a1.isValid());

        AirPlane a2 = new AirPlane("A1", 1, 1, 1, 1, 0, 1, "USAir", 1, 1);
        assertFalse(a2.isValid());

        AirPlane a3 = new AirPlane("A1", 1, 1, 1, 1, 1, 0, "USAir", 1, 1);
        assertFalse(a3.isValid());
    }


    /**
     * Test validation - widths too large
     */
    public void testInvalidWidthsTooLarge() {
        AirPlane a1 = new AirPlane("A1", 1, 1, 1, 1025, 1, 1, "USAir", 1, 1);
        assertFalse(a1.isValid());

        AirPlane a2 = new AirPlane("A1", 1, 1, 1, 1, 1025, 1, "USAir", 1, 1);
        assertFalse(a2.isValid());

        AirPlane a3 = new AirPlane("A1", 1, 1, 1, 1, 1, 1025, "USAir", 1, 1);
        assertFalse(a3.isValid());
    }


    /**
     * Test validation - extends beyond world
     */
    public void testInvalidExtendsBeyond() {
        AirPlane a1 = new AirPlane("A1", 1000, 1, 1, 100, 1, 1, "USAir", 1, 1);
        assertFalse(a1.isValid());

        AirPlane a2 = new AirPlane("A1", 1, 1000, 1, 1, 100, 1, "USAir", 1, 1);
        assertFalse(a2.isValid());

        AirPlane a3 = new AirPlane("A1", 1, 1, 1000, 1, 1, 100, "USAir", 1, 1);
        assertFalse(a3.isValid());
    }


    /**
     * Test validation - boundary values
     */
    public void testBoundaryValues() {
        // Minimum valid
        AirPlane a1 = new AirPlane("A1", 0, 0, 0, 1, 1, 1, "USAir", 1, 1);
        boolean valid1 = a1.isValid();
        String toString1 = a1.toString();
        assertTrue(valid1);
        assertEquals("Airplane A1 0 0 0 1 1 1 USAir 1 1", toString1);

        // Maximum valid
        AirPlane a2 = new AirPlane("A2", 1023, 1023, 1023, 1, 1, 1, "USAir",
            9999, 8);
        boolean valid2 = a2.isValid();
        String toString2 = a2.toString();
        assertTrue(valid2);
        assertEquals("Airplane A2 1023 1023 1023 1 1 1 USAir 9999 8",
            toString2);
    }
}
