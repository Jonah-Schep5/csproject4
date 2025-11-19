import java.util.Random;
import student.TestCase;

/**
 * Test class for WorldDB
 * Tests all ATC interface methods with mutation coverage
 *
 * @author CS3114/5040 Staff
 * @version Fall 2025
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
        assertTrue(world.add(new Balloon("B1", 10, 20, 30, 40, 50, 60, "hot", 1)));
        assertTrue(world.add(new AirPlane("A1", 0, 10, 1, 20, 2, 30, "USAir", 717, 4)));
        assertTrue(world.add(new Drone("D1", 100, 200, 300, 40, 50, 60, "Droners", 3)));
        assertTrue(world.add(new Bird("Bird1", 0, 100, 20, 10, 50, 50, "Eagle", 1)));
        assertTrue(world.add(new Rocket("R1", 0, 100, 20, 10, 50, 50, 5000, 99.29)));
    }

    /**
     * Test add duplicate
     */
    public void testAddDuplicate() {
        Balloon b = new Balloon("B1", 10, 20, 30, 40, 50, 60, "hot", 1);
        assertTrue(world.add(b));
        assertFalse(world.add(new Balloon("B1", 10, 20, 30, 40, 50, 60, "hot", 1)));
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
        assertTrue(world.add(new Balloon("Balloon1", 10, 20, 30, 40, 50, 60, "hot", 1)));
        assertTrue(world.add(new AirPlane("Plane1", 100, 200, 300, 40, 50, 60, "USAir", 717, 4)));
        assertTrue(world.add(new Drone("Drone1", 200, 300, 400, 40, 50, 60, "Droners", 3)));
        assertTrue(world.add(new Bird("Bird1", 300, 400, 500, 40, 50, 60, "Eagle", 1)));
        assertTrue(world.add(new Rocket("Rocket1", 400, 500, 600, 40, 50, 60, 5000, 99.29)));
        
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
        assertTrue(world.add(new Balloon("B2", 1023, 1023, 1023, 1, 1, 1, "hot", 0)));
        assertTrue(world.add(new Balloon("B3", 0, 0, 0, 1024, 1024, 1024, "hot", 0)));
        
        // Test boundary invalid values
        assertFalse(world.add(new Balloon("B4", -1, 0, 0, 1, 1, 1, "hot", 0)));
        assertFalse(world.add(new Balloon("B5", 1024, 0, 0, 1, 1, 1, "hot", 0)));
        assertFalse(world.add(new Balloon("B6", 0, 0, 0, 0, 1, 1, "hot", 0)));
        assertFalse(world.add(new Balloon("B7", 0, 0, 0, 1025, 1, 1, "hot", 0)));
        assertFalse(world.add(new Balloon("B8", 1000, 0, 0, 100, 1, 1, "hot", 0)));
    }
}

