import java.util.Random;
import student.TestCase;

/**
 * Test class for WorldDB
 * Tests all ATC interface methods with mutation coverage
 *
 * @author Jonah Schepers
 * @author Rowan Muhoberac
 * @version December 8, 2025
 */
public class WorldDBTest extends TestCase {

    /**
     * Random number generator
     */
    private Random rnd;

    /**
     * WorldDB instance
     */
    private WorldDB world;

    /**
     * Sets up the tests
     */
    public void setUp() {
        rnd = new Random();
        rnd.setSeed(0xCAFEBEEF);
        world = new WorldDB(rnd);
    }


    /**
     * Test constructor
     */
    public void testConstructor() {
        WorldDB w = new WorldDB(rnd);
        assertNotNull(w);
    }


    /**
     * Test constructor with null random
     */
    public void testConstructorNullRandom() {
        WorldDB w = new WorldDB(null);
        assertNotNull(w);
    }


    /**
     * Test clear
     */
    public void testClear() {
        Balloon b = new Balloon("B1", 10, 20, 30, 40, 50, 60, "hot", 1);
        world.add(b);
        world.clear();
        assertNull(world.print("B1"));
        assertTrue(world.printskiplist().contains("empty"));
    }


    /**
     * Test add valid objects
     */
    public void testAddValid() {
        assertTrue(world.add(new Balloon("B1", 10, 20, 30, 40, 50, 60, "hot",
            1)));
        assertTrue(world.add(new AirPlane("A1", 0, 10, 1, 20, 2, 30, "USAir",
            717, 4)));
        assertTrue(world.add(new Drone("D1", 100, 200, 300, 40, 50, 60,
            "Droners", 3)));
        assertTrue(world.add(new Bird("Bird1", 0, 100, 20, 10, 50, 50, "Eagle",
            1)));
        assertTrue(world.add(new Rocket("R1", 0, 100, 20, 10, 50, 50, 5000,
            99.29)));
    }


    /**
     * Test add duplicate
     */
    public void testAddDuplicate() {
        Balloon b = new Balloon("B1", 10, 20, 30, 40, 50, 60, "hot", 1);
        assertTrue(world.add(b));
        assertFalse(world.add(new Balloon("B1", 10, 20, 30, 40, 50, 60, "hot",
            1)));
    }


    /**
     * Test add invalid objects
     */
    public void testAddInvalid() {
        assertFalse(world.add(null));
        assertFalse(world.add(new Balloon(null, 1, 1, 1, 1, 1, 1, "hot", 1)));
        assertFalse(world.add(new Balloon("B1", -1, 1, 1, 1, 1, 1, "hot", 1)));
        assertFalse(world.add(new Balloon("B1", 1, 1, 1, 0, 1, 1, "hot", 1)));
        assertFalse(world.add(new Balloon("B1", 1, 1, 1, 1, 1, 1, null, 1)));
        assertFalse(world.add(new Balloon("B1", 1, 1, 1, 1, 1, 1, "hot", -1)));
    }


    /**
     * Test delete
     */
    public void testDelete() {
        Balloon b = new Balloon("B1", 10, 20, 30, 40, 50, 60, "hot", 1);
        world.add(b);
        String result = world.delete("B1");
        assertNotNull(result);
        assertTrue(result.contains("B1"));
        assertNull(world.delete("B1"));
    }


    /**
     * Test delete non-existent
     */
    public void testDeleteNonExistent() {
        assertNull(world.delete("Nonexistent"));
        assertNull(world.delete(null));
    }


    /**
     * Test print
     */
    public void testPrint() {
        Balloon b = new Balloon("B1", 10, 20, 30, 40, 50, 60, "hot", 1);
        world.add(b);
        String result = world.print("B1");
        assertNotNull(result);
        assertTrue(result.contains("B1"));
        assertNull(world.print("B2"));
        assertNull(world.print(null));
    }


    /**
     * Test printskiplist
     */
    public void testPrintSkipList() {
        assertTrue(world.printskiplist().contains("empty"));
        world.add(new Balloon("B1", 10, 20, 30, 40, 50, 60, "hot", 1));
        String result = world.printskiplist();
        assertNotNull(result);
        assertTrue(result.contains("B1"));
    }


    /**
     * Test printbintree
     */
    public void testPrintBinTree() {
        String result = world.printbintree();
        assertNotNull(result);
        assertTrue(result.contains("Bintree nodes printed"));

        world.add(new Balloon("B1", 10, 20, 30, 40, 50, 60, "hot", 1));
        result = world.printbintree();
        assertNotNull(result);
        assertTrue(result.contains("B1"));
    }


    /**
     * Test rangeprint
     */
    public void testRangePrint() {
        world.add(new Balloon("B1", 10, 20, 30, 40, 50, 60, "hot", 1));
        world.add(new AirPlane("A1", 0, 10, 1, 20, 2, 30, "USAir", 717, 4));
        world.add(new Drone("D1", 100, 200, 300, 40, 50, 60, "Droners", 3));

        String result = world.rangeprint("A", "C");
        assertNotNull(result);
        assertTrue(result.contains("A1"));

        result = world.rangeprint("X", "Z");
        assertNotNull(result);
        assertFalse(result.contains("A1"));
    }


    /**
     * Test rangeprint invalid
     */
    public void testRangePrintInvalid() {
        assertNull(world.rangeprint(null, "A"));
        assertNull(world.rangeprint("A", null));
        assertNull(world.rangeprint("Z", "A"));
    }


    /**
     * Test collisions
     */
    public void testCollisions() {
        String result = world.collisions();
        assertNotNull(result);
        assertTrue(result.contains("The following collisions exist"));

        // Add overlapping objects
        world.add(new Balloon("B1", 10, 20, 30, 40, 50, 60, "hot", 1));
        world.add(new AirPlane("A1", 20, 30, 40, 40, 50, 60, "USAir", 717, 4));

        result = world.collisions();
        assertNotNull(result);
    }


    /**
     * Test intersect
     */
    public void testIntersect() {
        world.add(new Balloon("B1", 10, 20, 30, 40, 50, 60, "hot", 1));

        String result = world.intersect(0, 0, 0, 100, 100, 100);
        assertNotNull(result);
        assertTrue(result.contains("The following objects intersect"));
        assertTrue(result.contains("nodes were visited"));
    }


    /**
     * Test intersect invalid parameters
     */
    public void testIntersectInvalid() {
        assertNull(world.intersect(-1, 1, 1, 1, 1, 1));
        assertNull(world.intersect(1, -1, 1, 1, 1, 1));
        assertNull(world.intersect(1, 1, -1, 1, 1, 1));
        assertNull(world.intersect(1, 1, 1, -1, 1, 1));
        assertNull(world.intersect(1, 1, 1, 1, -1, 1));
        assertNull(world.intersect(1, 1, 1, 1, 1, -1));
        assertNull(world.intersect(1024, 1, 1, 1, 1, 1));
        assertNull(world.intersect(1, 1024, 1, 1, 1, 1));
        assertNull(world.intersect(1, 1, 1024, 1, 1, 1));
        assertNull(world.intersect(1, 1, 1, 1025, 1, 1));
        assertNull(world.intersect(1, 1, 1, 1, 1025, 1));
        assertNull(world.intersect(1, 1, 1, 1, 1, 1025));
        assertNull(world.intersect(1000, 1, 1, 100, 1, 1));
        assertNull(world.intersect(1, 1000, 1, 1, 100, 1));
        assertNull(world.intersect(1, 1, 1000, 1, 1, 100));
    }


    /**
     * Test intersect boundary values
     */
    public void testIntersectBoundary() {
        // Valid boundary values
        String result = world.intersect(0, 0, 0, 1024, 1024, 1024);
        assertNotNull(result);

        result = world.intersect(1023, 1023, 1023, 1, 1, 1);
        assertNotNull(result);
    }


    /**
     * Test complex scenario
     */
    public void testComplexScenario() {
        world.add(new Balloon("B1", 10, 11, 11, 21, 12, 31, "hot_air", 15));
        world.add(new AirPlane("Air1", 0, 10, 1, 20, 2, 30, "USAir", 717, 4));
        world.add(new Drone("Air2", 100, 1010, 101, 924, 2, 900, "Droners", 3));

        assertNotNull(world.print("B1"));
        assertNotNull(world.printskiplist());
        assertNotNull(world.printbintree());
        assertNotNull(world.collisions());
        assertNotNull(world.intersect(0, 0, 0, 1024, 1024, 1024));

        String deleted = world.delete("B1");
        assertNotNull(deleted);
        assertNull(world.print("B1"));
    }


    /**
     * Test all object types
     */
    public void testAllObjectTypes() {
        assertTrue(world.add(new Balloon("Balloon1", 10, 20, 30, 40, 50, 60,
            "hot", 1)));
        assertTrue(world.add(new AirPlane("Plane1", 100, 200, 300, 40, 50, 60,
            "USAir", 717, 4)));
        assertTrue(world.add(new Drone("Drone1", 200, 300, 400, 40, 50, 60,
            "Droners", 3)));
        assertTrue(world.add(new Bird("Bird1", 300, 400, 500, 40, 50, 60,
            "Eagle", 1)));
        assertTrue(world.add(new Rocket("Rocket1", 400, 500, 600, 40, 50, 60,
            5000, 99.29)));

        assertNotNull(world.print("Balloon1"));
        assertNotNull(world.print("Plane1"));
        assertNotNull(world.print("Drone1"));
        assertNotNull(world.print("Bird1"));
        assertNotNull(world.print("Rocket1"));
    }


    /**
     * Test various scenarios: rangeprint, delete/re-add, collisions, intersect
     */
    public void testVariousScenarios() {
        // Test rangeprint with various ranges
        world.add(new Balloon("A1", 10, 20, 30, 40, 50, 60, "hot", 1));
        world.add(new Balloon("B1", 10, 20, 30, 40, 50, 60, "hot", 1));
        world.add(new Balloon("C1", 10, 20, 30, 40, 50, 60, "hot", 1));

        String result = world.rangeprint("A1", "A1");
        assertNotNull(result);
        assertTrue(result.contains("A1"));

        result = world.rangeprint("A", "D");
        assertNotNull(result);

        result = world.rangeprint("X", "Z");
        assertNotNull(result);

        // Test delete and re-add
        Balloon b = new Balloon("B1", 10, 20, 30, 40, 50, 60, "hot", 1);
        assertNotNull(world.delete("B1"));
        assertTrue(world.add(b));

        // Test collisions with multiple overlapping objects
        world.add(new AirPlane("A1", 20, 30, 40, 40, 50, 60, "USAir", 717, 4));
        world.add(new Bird("Bird1", 25, 35, 45, 40, 50, 60, "Eagle", 1));
        String collisions = world.collisions();
        assertNotNull(collisions);

        // Test intersect with various query boxes
        world.add(new Balloon("B2", 500, 600, 700, 50, 60, 70, "hot", 1));
        result = world.intersect(90, 190, 290, 100, 100, 100);
        assertNotNull(result);

        result = world.intersect(0, 0, 0, 1024, 1024, 1024);
        assertNotNull(result);
    }


  
    
    /**
     * Test boundary conditions for validation
     */
    public void testValidationBoundaries() {
        // Test all boundary valid values
        assertTrue(world.add(new Balloon("B1", 0, 0, 0, 1, 1, 1, "hot", 0)));
        assertTrue(world.add(new Balloon("B2", 1023, 1023, 1023, 1, 1, 1, "hot",
            0)));
        assertTrue(world.add(new Balloon("B3", 0, 0, 0, 1024, 1024, 1024, "hot",
            0)));

        // Test boundary invalid values
        assertFalse(world.add(new Balloon("B4", -1, 0, 0, 1, 1, 1, "hot", 0)));
        assertFalse(world.add(new Balloon("B5", 1024, 0, 0, 1, 1, 1, "hot",
            0)));
        assertFalse(world.add(new Balloon("B6", 0, 0, 0, 0, 1, 1, "hot", 0)));
        assertFalse(world.add(new Balloon("B7", 0, 0, 0, 1025, 1, 1, "hot",
            0)));
        assertFalse(world.add(new Balloon("B8", 1000, 0, 0, 100, 1, 1, "hot",
            0)));
    }


    /**
     * Test validation with empty string name
     */
    public void testAddEmptyName() {
        assertFalse(world.add(new Balloon("", 10, 20, 30, 40, 50, 60, "hot",
            1)));
    }


    /**
     * Test exact boundary cases for x coordinate sum
     */
    public void testXCoordinateBoundaries() {
        // Valid: xorig + xwidth = 1024 exactly
        assertTrue(world.add(new Balloon("B1", 1023, 0, 0, 1, 1, 1, "hot", 1)));
        assertTrue(world.add(new Balloon("B2", 512, 0, 0, 512, 1, 1, "hot",
            1)));
        assertTrue(world.add(new Balloon("B3", 0, 0, 0, 1024, 1, 1, "hot", 1)));

        // Invalid: xorig + xwidth > 1024
        assertFalse(world.add(new Balloon("B4", 1023, 0, 0, 2, 1, 1, "hot",
            1)));
        assertFalse(world.add(new Balloon("B5", 512, 0, 0, 513, 1, 1, "hot",
            1)));
    }


    /**
     * Test exact boundary cases for y coordinate sum
     */
    public void testYCoordinateBoundaries() {
        // Valid: yorig + ywidth = 1024 exactly
        assertTrue(world.add(new Balloon("B1", 0, 1023, 0, 1, 1, 1, "hot", 1)));
        assertTrue(world.add(new Balloon("B2", 0, 512, 0, 1, 512, 1, "hot",
            1)));
        assertTrue(world.add(new Balloon("B3", 0, 0, 0, 1, 1024, 1, "hot", 1)));

        // Invalid: yorig + ywidth > 1024
        assertFalse(world.add(new Balloon("B4", 0, 1023, 0, 1, 2, 1, "hot",
            1)));
        assertFalse(world.add(new Balloon("B5", 0, 512, 0, 1, 513, 1, "hot",
            1)));
    }


    /**
     * Test exact boundary cases for z coordinate sum
     */
    public void testZCoordinateBoundaries() {
        // Valid: zorig + zwidth = 1024 exactly
        assertTrue(world.add(new Balloon("B1", 0, 0, 1023, 1, 1, 1, "hot", 1)));
        assertTrue(world.add(new Balloon("B2", 0, 0, 512, 1, 1, 512, "hot",
            1)));
        assertTrue(world.add(new Balloon("B3", 0, 0, 0, 1, 1, 1024, "hot", 1)));

        // Invalid: zorig + zwidth > 1024
        assertFalse(world.add(new Balloon("B4", 0, 0, 1023, 1, 1, 2, "hot",
            1)));
        assertFalse(world.add(new Balloon("B5", 0, 0, 512, 1, 1, 513, "hot",
            1)));
    }


    /**
     * Test Rocket validation
     */
    public void testRocketValidation() {
        // Valid rocket
        assertTrue(world.add(new Rocket("R1", 0, 100, 20, 10, 50, 50, 5000,
            99.29)));

        // Test invalid rocket (if Rocket has validation)
        assertFalse(world.add(new Rocket("R2", 0, 100, 20, 10, 50, 50, -1,
            99.29)));
    }


    /**
     * Test rangeprint with exact matches
     */
    public void testRangePrintExactBoundaries() {
        world.add(new Balloon("A", 10, 20, 30, 40, 50, 60, "hot", 1));
        world.add(new Balloon("B", 10, 20, 30, 40, 50, 60, "hot", 1));
        world.add(new Balloon("C", 10, 20, 30, 40, 50, 60, "hot", 1));

        // Test where start equals end
        String result = world.rangeprint("B", "B");
        assertNotNull(result);
        assertTrue(result.contains("B"));

        // Test where range captures all
        result = world.rangeprint("A", "C");
        assertNotNull(result);
        assertTrue(result.contains("A"));
        assertTrue(result.contains("B"));
        assertTrue(result.contains("C"));
    }


    /**
     * Test delete and verify bintree rebuild
     */
    public void testDeleteVerifyBintreeRebuild() {
        world.add(new Balloon("B1", 10, 20, 30, 40, 50, 60, "hot", 1));
        world.add(new Balloon("B2", 100, 200, 300, 40, 50, 60, "hot", 1));
        world.add(new Balloon("B3", 500, 600, 700, 40, 50, 60, "hot", 1));

        String result = world.printbintree();
        assertNotNull(result);
        assertTrue(result.contains("B1") || result.contains("B2") || result
            .contains("B3"));

        world.delete("B2");

        result = world.printbintree();
        assertNotNull(result);
        assertFalse(result.contains("B2"));
    }


    /**
     * Test getAllObjects through multiple deletes
     */
    public void testMultipleDeletesGetAllObjects() {
        world.add(new Balloon("A", 10, 20, 30, 40, 50, 60, "hot", 1));
        world.add(new Balloon("B", 100, 200, 300, 40, 50, 60, "hot", 1));
        world.add(new Balloon("C", 500, 600, 700, 40, 50, 60, "hot", 1));
        world.add(new Balloon("D", 50, 60, 70, 40, 50, 60, "hot", 1));

        assertNotNull(world.delete("B"));
        assertNotNull(world.delete("D"));

        String result = world.printskiplist();
        assertNotNull(result);
        assertTrue(result.contains("B"));
        assertFalse(result.contains("D"));
        assertTrue(result.contains("A"));
        assertTrue(result.contains("C"));
    }


    /**
     * Test intersect with exact boundary at 1024
     */
    public void testIntersectExactBoundary1024() {
        world.add(new Balloon("B1", 1000, 1000, 1000, 23, 23, 23, "hot", 1));

        // Valid: origin + width = 1024
        String result = world.intersect(1000, 1000, 1000, 24, 24, 24);
        assertNotNull(result);

        // Invalid: origin + width = 1025
        result = world.intersect(1000, 1000, 1000, 25, 25, 25);
        assertNull(result);
    }


    /**
     * Test intersect with all coordinate boundary combinations
     */
    public void testIntersectCoordinateBoundaries() {
        // Test x boundary: x + xwid > 1024
        assertNull(world.intersect(1023, 0, 0, 2, 1, 1));
        assertNull(world.intersect(1020, 0, 0, 5, 1, 1));

        // Test y boundary: y + ywid > 1024
        assertNull(world.intersect(0, 1023, 0, 1, 2, 1));
        assertNull(world.intersect(0, 1020, 0, 1, 5, 1));

        // Test z boundary: z + zwid > 1024
        assertNull(world.intersect(0, 0, 1023, 1, 1, 2));
        assertNull(world.intersect(0, 0, 1020, 1, 1, 5));
    }


    /**
     * Test print with findByName edge cases
     */
    public void testPrintFindByName() {
        world.add(new Balloon("Alpha", 10, 20, 30, 40, 50, 60, "hot", 1));
        world.add(new Balloon("Beta", 100, 200, 300, 40, 50, 60, "hot", 1));

        // Verify we can find both
        assertNotNull(world.print("Alpha"));
        assertNotNull(world.print("Beta"));

        // Delete one and verify the other still works
        world.delete("Alpha");
        assertNull(world.print("Alpha"));
        assertNotNull(world.print("Beta"));
    }


    /**
     * Test maximum coordinate values
     */
    public void testMaxCoordinateValues() {
        // Test maximum valid x coordinate
        assertTrue(world.add(new Balloon("B1", 1023, 0, 0, 1, 1, 1, "hot", 1)));
        assertFalse(world.add(new Balloon("B2", 1024, 0, 0, 1, 1, 1, "hot",
            1)));

        // Test maximum valid y coordinate
        assertTrue(world.add(new Balloon("B3", 0, 1023, 0, 1, 1, 1, "hot", 1)));
        assertFalse(world.add(new Balloon("B4", 0, 1024, 0, 1, 1, 1, "hot",
            1)));

        // Test maximum valid z coordinate
        assertTrue(world.add(new Balloon("B5", 0, 0, 1023, 1, 1, 1, "hot", 1)));
        assertFalse(world.add(new Balloon("B6", 0, 0, 1024, 1, 1, 1, "hot",
            1)));
    }


    /**
     * Test minimum width values
     */
    public void testMinimumWidthValues() {
        // Valid: width = 1
        assertTrue(world.add(new Balloon("B1", 0, 0, 0, 1, 1, 1, "hot", 1)));

        // Invalid: width = 0
        assertFalse(world.add(new Balloon("B2", 0, 0, 0, 0, 1, 1, "hot", 1)));
        assertFalse(world.add(new Balloon("B3", 0, 0, 0, 1, 0, 1, "hot", 1)));
        assertFalse(world.add(new Balloon("B4", 0, 0, 0, 1, 1, 0, "hot", 1)));
    }


    /**
     * Test maximum width values
     */
    public void testMaximumWidthValues() {
        // Valid: width = 1024 at origin 0
        assertTrue(world.add(new Balloon("B1", 0, 0, 0, 1024, 1, 1, "hot", 1)));
        assertTrue(world.add(new Balloon("B2", 0, 0, 0, 1, 1024, 1, "hot", 1)));
        assertTrue(world.add(new Balloon("B3", 0, 0, 0, 1, 1, 1024, "hot", 1)));

        // Invalid: width = 1025
        assertFalse(world.add(new Balloon("B4", 0, 0, 0, 1025, 1, 1, "hot",
            1)));
        assertFalse(world.add(new Balloon("B5", 0, 0, 0, 1, 1025, 1, "hot",
            1)));
        assertFalse(world.add(new Balloon("B6", 0, 0, 0, 1, 1, 1025, "hot",
            1)));
    }


    /**
     * Test origin + width overflow cases
     */
    public void testOriginWidthOverflow() {
        // Test cases where origin + width = 1025 (invalid)
        assertFalse(world.add(new Balloon("B1", 1, 0, 0, 1024, 1, 1, "hot",
            1)));
        assertFalse(world.add(new Balloon("B2", 0, 1, 0, 1, 1024, 1, "hot",
            1)));
        assertFalse(world.add(new Balloon("B3", 0, 0, 1, 1, 1, 1024, "hot",
            1)));

        // Test cases where origin + width = 1030 (very invalid)
        assertFalse(world.add(new Balloon("B4", 10, 0, 0, 1020, 1, 1, "hot",
            1)));
        assertFalse(world.add(new Balloon("B5", 0, 10, 0, 1, 1020, 1, "hot",
            1)));
        assertFalse(world.add(new Balloon("B6", 0, 0, 10, 1, 1, 1020, "hot",
            1)));
    }


    /**
     * Test intersect with maximum valid parameters
     */
    public void testIntersectMaxValidParams() {
        // All parameters at maximum valid values
        String result = world.intersect(0, 0, 0, 1024, 1024, 1024);
        assertNotNull(result);

        // Origin at max, width at min
        result = world.intersect(1023, 1023, 1023, 1, 1, 1);
        assertNotNull(result);

        // Various combinations at boundary
        result = world.intersect(512, 512, 512, 512, 512, 512);
        assertNotNull(result);
    }


    /**
     * Test intersect x-coordinate validation specifically
     */
    public void testIntersectXValidation() {
        // Invalid x origin
        assertNull(world.intersect(-1, 0, 0, 10, 10, 10));
        assertNull(world.intersect(1024, 0, 0, 1, 1, 1));

        // Invalid x width
        assertNull(world.intersect(0, 0, 0, 0, 10, 10));
        assertNull(world.intersect(0, 0, 0, 1025, 10, 10));

        // Invalid x origin + width
        assertNull(world.intersect(1023, 0, 0, 2, 10, 10));
        assertNull(world.intersect(500, 0, 0, 525, 10, 10));
    }


    /**
     * Test intersect y-coordinate validation specifically
     */
    public void testIntersectYValidation() {
        // Invalid y origin
        assertNull(world.intersect(0, -1, 0, 10, 10, 10));
        assertNull(world.intersect(0, 1024, 0, 1, 1, 1));

        // Invalid y width
        assertNull(world.intersect(0, 0, 0, 10, 0, 10));
        assertNull(world.intersect(0, 0, 0, 10, 1025, 10));

        // Invalid y origin + width
        assertNull(world.intersect(0, 1023, 0, 10, 2, 10));
        assertNull(world.intersect(0, 500, 0, 10, 525, 10));
    }


    /**
     * Test intersect z-coordinate validation specifically
     */
    public void testIntersectZValidation() {
        // Invalid z origin
        assertNull(world.intersect(0, 0, -1, 10, 10, 10));
        assertNull(world.intersect(0, 0, 1024, 1, 1, 1));

        // Invalid z width
        assertNull(world.intersect(0, 0, 0, 10, 10, 0));
        assertNull(world.intersect(0, 0, 0, 10, 10, 1025));

        // Invalid z origin + width
        assertNull(world.intersect(0, 0, 1023, 10, 10, 2));
        assertNull(world.intersect(0, 0, 500, 10, 10, 525));
    }


    /**
     * Test rangeprint with string boundary conditions
     */
    public void testRangePrintStringBoundaries() {
        world.add(new Balloon("AAA", 10, 20, 30, 40, 50, 60, "hot", 1));
        world.add(new Balloon("BBB", 100, 200, 300, 40, 50, 60, "hot", 1));
        world.add(new Balloon("ZZZ", 500, 600, 700, 40, 50, 60, "hot", 1));

        // Test range that includes all
        String result = world.rangeprint("A", "Z");
        assertNotNull(result);
        assertTrue(result.contains("AAA"));
        assertTrue(result.contains("BBB"));

        // Test range with exact match
        result = world.rangeprint("BBB", "BBB");
        assertNotNull(result);
        assertTrue(result.contains("BBB"));

        // Test range that excludes some
        result = world.rangeprint("A", "B");
        assertNotNull(result);
        assertTrue(result.contains("AAA"));
    }


    /**
     * Test delete with multiple objects and verify skiplist
     */
    public void testDeleteMultipleVerifySkiplist() {
        world.add(new Balloon("A", 10, 20, 30, 40, 50, 60, "hot", 1));
        world.add(new Balloon("B", 100, 200, 300, 40, 50, 60, "hot", 1));
        world.add(new Balloon("C", 200, 300, 400, 40, 50, 60, "hot", 1));
        world.add(new Balloon("D", 300, 400, 500, 40, 50, 60, "hot", 1));

        // Delete middle elements
        assertNotNull(world.delete("B"));
        assertNotNull(world.delete("C"));

        // Verify remaining elements
        String skiplist = world.printskiplist();
        assertNotNull(skiplist);
        assertTrue(skiplist.contains("A"));
        assertTrue(skiplist.contains("D"));
        assertTrue(skiplist.contains("B"));
        assertFalse(skiplist.contains("C"));
    }


    /**
     * Test add after clear
     */
    public void testAddAfterClear() {
        world.add(new Balloon("B1", 10, 20, 30, 40, 50, 60, "hot", 1));
        world.add(new Balloon("B2", 100, 200, 300, 40, 50, 60, "hot", 1));

        world.clear();

        // Should be able to add objects with same names after clear
        assertTrue(world.add(new Balloon("B1", 10, 20, 30, 40, 50, 60, "hot",
            1)));
        assertTrue(world.add(new Balloon("B2", 100, 200, 300, 40, 50, 60,
            "hot", 1)));

        assertNotNull(world.print("B1"));
        assertNotNull(world.print("B2"));
    }


    /**
     * Test validation with all object types
     */
    public void testValidationAllTypes() {
        // Test each type with invalid coordinates
        assertFalse(world.add(new Balloon("B", 1024, 0, 0, 1, 1, 1, "hot", 1)));
        assertFalse(world.add(new AirPlane("A", 1024, 0, 0, 1, 1, 1, "US", 1,
            1)));
        assertFalse(world.add(new Drone("D", 1024, 0, 0, 1, 1, 1, "DJI", 1)));
        assertFalse(world.add(new Bird("Bi", 1024, 0, 0, 1, 1, 1, "Eagle", 1)));
        assertFalse(world.add(new Rocket("R", 1024, 0, 0, 1, 1, 1, 1000, 1.0)));

        // Test each type with valid coordinates
        assertTrue(world.add(new Balloon("B", 0, 0, 0, 1, 1, 1, "hot", 1)));
        assertTrue(world.add(new AirPlane("A", 0, 0, 0, 1, 1, 1, "US", 1, 1)));
        assertTrue(world.add(new Drone("D", 0, 0, 0, 1, 1, 1, "DJI", 1)));
        assertTrue(world.add(new Bird("Bi", 0, 0, 0, 1, 1, 1, "Eagle", 1)));
        assertTrue(world.add(new Rocket("R", 0, 0, 0, 1, 1, 1, 1000, 1.0)));
    }


    /**
     * Test findByName with no objects
     */
    public void testFindByNameEmpty() {
        assertNull(world.print("NonExistent"));
        assertNull(world.delete("NonExistent"));
    }


    /**
     * Test rangeprint with inverted range
     */
    public void testRangePrintInverted() {
        world.add(new Balloon("B", 10, 20, 30, 40, 50, 60, "hot", 1));
        
        // Start > End should return null
        assertNull(world.rangeprint("Z", "A"));
        assertNull(world.rangeprint("M", "A"));
    }


    /**
     * Test intersect arithmetic operations with edge values
     */
    public void testIntersectArithmeticEdges() {
        // Test where sum equals exactly 1024
        String result = world.intersect(1023, 0, 0, 1, 1, 1);
        assertNotNull(result);

        result = world.intersect(0, 1023, 0, 1, 1, 1);
        assertNotNull(result);

        result = world.intersect(0, 0, 1023, 1, 1, 1);
        assertNotNull(result);

        // Test where sum equals exactly 1024 with larger values
        result = world.intersect(512, 0, 0, 512, 1, 1);
        assertNotNull(result);

        result = world.intersect(0, 512, 0, 1, 512, 1);
        assertNotNull(result);

        result = world.intersect(0, 0, 512, 1, 1, 512);
        assertNotNull(result);
    }


    /**
     * Test getAllObjects through rangeprint
     */
    public void testGetAllObjectsThroughRange() {
        world.add(new Balloon("Apple", 10, 20, 30, 40, 50, 60, "hot", 1));
        world.add(new Balloon("Banana", 100, 200, 300, 40, 50, 60, "hot", 1));
        world.add(new Balloon("Cherry", 200, 300, 400, 40, 50, 60, "hot", 1));

        // Use wide range to get all objects
        String result = world.rangeprint("A", "Z");
        assertNotNull(result);
        assertTrue(result.contains("Apple"));
        assertTrue(result.contains("Banana"));
        assertTrue(result.contains("Cherry"));
    }

}
