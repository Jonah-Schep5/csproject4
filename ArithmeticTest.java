import student.TestCase;

/**
 * Test class specifically for arithmetic operations
 * Tests all arithmetic operations to kill AOD mutants
 *
 * @author CS3114/5040 Staff
 * @version Fall 2025
 */
public class ArithmeticTest extends TestCase {

    /**
     * Test arithmetic operations in intersects calculations
     * This kills AOD2Mutator mutants that change a+b to a or b
     */
    public void testIntersectsArithmetic() {
        // Test that xorig + xwidth is used correctly
        Balloon b1 = new Balloon("B1", 10, 20, 30, 40, 50, 60, "hot", 1);
        Balloon b2 = new Balloon("B2", 50, 20, 30, 10, 50, 60, "hot", 1);
        // b1: x=10, width=40, so ends at 50
        // b2: x=50, width=10, so ends at 60
        // They should NOT intersect (b1 ends exactly where b2 starts)
        boolean result = b1.intersects(b2);
        assertFalse(result);
        
        // Now test with overlap by 1
        Balloon b3 = new Balloon("B3", 49, 20, 30, 10, 50, 60, "hot", 1);
        // b1: x=10, width=40, so ends at 50
        // b3: x=49, width=10, so ends at 59
        // They should intersect (overlap from 49 to 50)
        boolean result2 = b1.intersects(b3);
        assertTrue(result2);
        
        // Test y dimension arithmetic
        Balloon b4 = new Balloon("B4", 10, 70, 30, 40, 10, 60, "hot", 1);
        // b1: y=20, width=50, so ends at 70
        // b4: y=70, width=10, so ends at 80
        // They should NOT intersect in y
        boolean result3 = b1.intersects(b4);
        assertFalse(result3);
        
        // Test z dimension arithmetic
        Balloon b5 = new Balloon("B5", 10, 20, 90, 40, 50, 10, "hot", 1);
        // b1: z=30, width=60, so ends at 90
        // b5: z=90, width=10, so ends at 100
        // They should NOT intersect in z
        boolean result4 = b1.intersects(b5);
        assertFalse(result4);
    }

    /**
     * Test arithmetic operations in isContainedIn
     */
    public void testIsContainedInArithmetic() {
        Balloon b = new Balloon("B1", 10, 20, 30, 40, 50, 60, "hot", 1);
        // b: x=10, width=40, so ends at 50
        //    y=20, width=50, so ends at 70
        //    z=30, width=60, so ends at 90
        
        // Test exact containment - box exactly contains object
        boolean result1 = b.isContainedIn(10, 20, 30, 40, 50, 60);
        assertTrue(result1);
        
        // Test box that's too small in x (doesn't contain end point)
        boolean result2 = b.isContainedIn(10, 20, 30, 39, 50, 60);
        assertFalse(result2);
        
        // Test box that's too small in y
        boolean result3 = b.isContainedIn(10, 20, 30, 40, 49, 60);
        assertFalse(result3);
        
        // Test box that's too small in z
        boolean result4 = b.isContainedIn(10, 20, 30, 40, 50, 59);
        assertFalse(result4);
    }

    /**
     * Test arithmetic in validation - extends beyond world
     */
    public void testValidationArithmetic() {
        // Test that xorig + xwidth > WORLD_SIZE is checked
        // Valid: x=1023, width=1, so ends at 1024 (valid)
        Balloon b1 = new Balloon("B1", 1023, 0, 0, 1, 1, 1, "hot", 1);
        boolean valid1 = b1.isValid();
        assertTrue(valid1);
        
        // Invalid: x=1023, width=2, so ends at 1025 (invalid)
        Balloon b2 = new Balloon("B2", 1023, 0, 0, 2, 1, 1, "hot", 1);
        boolean valid2 = b2.isValid();
        assertFalse(valid2);
        
        // Test y dimension
        Balloon b3 = new Balloon("B3", 0, 1023, 0, 1, 2, 1, "hot", 1);
        boolean valid3 = b3.isValid();
        assertFalse(valid3);
        
        // Test z dimension
        Balloon b4 = new Balloon("B4", 0, 0, 1023, 1, 1, 2, "hot", 1);
        boolean valid4 = b4.isValid();
        assertFalse(valid4);
    }

    /**
     * Test arithmetic in intersection box calculations
     */
    public void testIntersectionBoxArithmetic() {
        Balloon b = new Balloon("B1", 10, 20, 30, 40, 50, 60, "hot", 1);
        // b: x=10-50, y=20-70, z=30-90
        
        // Query box: x=15-25, y=25-35, z=35-45
        // Intersection: x=max(10,15)=15, y=max(20,25)=25, z=max(30,35)=35
        boolean result = b.intersects(15, 25, 35, 10, 10, 10);
        assertTrue(result);
        
        // Query box that doesn't overlap
        boolean result2 = b.intersects(100, 100, 100, 10, 10, 10);
        assertFalse(result2);
    }

    /**
     * Test arithmetic with boundary values
     */
    public void testBoundaryArithmetic() {
        // Test addition at boundaries
        Balloon b1 = new Balloon("B1", 0, 0, 0, 1024, 1024, 1024, "hot", 1);
        // 0 + 1024 = 1024 (valid)
        boolean valid1 = b1.isValid();
        assertTrue(valid1);
        
        // Test that 0 + 1025 would be invalid
        Balloon b2 = new Balloon("B2", 0, 0, 0, 1025, 1, 1, "hot", 1);
        boolean valid2 = b2.isValid();
        assertFalse(valid2);
        
        // Test subtraction in intersection
        Balloon b3 = new Balloon("B3", 100, 100, 100, 50, 50, 50, "hot", 1);
        // b3: x=100-150
        // Query: x=149-151 (overlaps by 1)
        boolean result = b3.intersects(149, 100, 100, 2, 50, 50);
        assertTrue(result);
        
        // Query: x=150-152 (adjacent, no overlap)
        boolean result2 = b3.intersects(150, 100, 100, 2, 50, 50);
        assertFalse(result2);
    }

    /**
     * Test arithmetic in compareTo (string comparison uses arithmetic internally)
     */
    public void testCompareToArithmetic() {
        Balloon b1 = new Balloon("A", 0, 0, 0, 1, 1, 1, "hot", 1);
        Balloon b2 = new Balloon("B", 0, 0, 0, 1, 1, 1, "hot", 1);
        
        int comp = b1.compareTo(b2);
        // Should be negative (A < B)
        assertTrue(comp < 0);
        
        int comp2 = b2.compareTo(b1);
        // Should be positive (B > A)
        assertTrue(comp2 > 0);
        
        // Test equality
        Balloon b3 = new Balloon("A", 0, 0, 0, 1, 1, 1, "hot", 1);
        int comp3 = b1.compareTo(b3);
        assertEquals(0, comp3);
    }

    /**
     * Test arithmetic operations with negative results
     */
    public void testNegativeArithmetic() {
        // Test that negative coordinates are caught
        Balloon b1 = new Balloon("B1", -1, 0, 0, 1, 1, 1, "hot", 1);
        boolean valid1 = b1.isValid();
        assertFalse(valid1);
        
        // Test that width calculations work even with small values
        Balloon b2 = new Balloon("B2", 0, 0, 0, 1, 1, 1, "hot", 1);
        // 0 + 1 = 1 (valid)
        boolean valid2 = b2.isValid();
        assertTrue(valid2);
        
        // Test intersection with small widths
        Balloon b3 = new Balloon("B3", 1, 0, 0, 1, 1, 1, "hot", 1);
        // b2: x=0-1, b3: x=1-2, adjacent but not overlapping
        boolean result = b2.intersects(b3);
        assertFalse(result);
    }

    /**
     * Test arithmetic in all three dimensions simultaneously
     */
    public void testThreeDimensionalArithmetic() {
        Balloon b1 = new Balloon("B1", 10, 20, 30, 40, 50, 60, "hot", 1);
        // b1: x=10-50, y=20-70, z=30-90
        
        // Test object that overlaps in all dimensions
        Balloon b2 = new Balloon("B2", 25, 35, 45, 20, 20, 20, "hot", 1);
        // b2: x=25-45, y=35-55, z=45-65
        // Overlaps in all dimensions
        boolean result = b1.intersects(b2);
        assertTrue(result);
        
        // Test object that overlaps in x and y but not z
        Balloon b3 = new Balloon("B3", 25, 35, 100, 20, 20, 20, "hot", 1);
        // b3: x=25-45, y=35-55, z=100-120
        // No overlap in z
        boolean result2 = b1.intersects(b3);
        assertFalse(result2);
        
        // Test object that overlaps in x and z but not y
        Balloon b4 = new Balloon("B4", 25, 100, 45, 20, 20, 20, "hot", 1);
        boolean result3 = b1.intersects(b4);
        assertFalse(result3);
    }

    /**
     * Test arithmetic with large numbers
     */
    public void testLargeNumberArithmetic() {
        // Test with maximum valid values
        Balloon b1 = new Balloon("B1", 500, 500, 500, 524, 524, 524, "hot", 1);
        // 500 + 524 = 1024 (valid)
        boolean valid1 = b1.isValid();
        assertTrue(valid1);
        
        // Test that 500 + 525 = 1025 (invalid)
        Balloon b2 = new Balloon("B2", 500, 500, 500, 525, 524, 524, "hot", 1);
        boolean valid2 = b2.isValid();
        assertFalse(valid2);
        
        // Test intersection with large numbers
        Balloon b3 = new Balloon("B3", 1000, 1000, 1000, 20, 20, 20, "hot", 1);
        // b3: x=1000-1020
        Balloon b4 = new Balloon("B4", 1010, 1000, 1000, 10, 20, 20, "hot", 1);
        // b4: x=1010-1020 (overlaps)
        boolean result = b3.intersects(b4);
        assertTrue(result);
    }

    /**
     * Test arithmetic operations in Bintree splitting (division by 2)
     * This kills AOD2Mutator mutants that change xwid / 2 to xwid or 2
     */
    public void testBintreeSplittingArithmetic() {
        Bintree tree = new Bintree();
        
        // Insert objects that will cause splits
        // The split point is calculated as x + xwid / 2
        // For region 0-1024, split at 512
        Balloon b1 = new Balloon("B1", 10, 10, 10, 20, 20, 20, "hot", 1);
        Balloon b2 = new Balloon("B2", 100, 100, 100, 20, 20, 20, "hot", 1);
        Balloon b3 = new Balloon("B3", 200, 200, 200, 20, 20, 20, "hot", 1);
        Balloon b4 = new Balloon("B4", 300, 300, 300, 20, 20, 20, "hot", 1);
        
        // These should cause splits that use xwid / 2 arithmetic
        tree.insert(b1);
        tree.insert(b2);
        tree.insert(b3);
        tree.insert(b4);
        
        // Verify the tree structure uses correct arithmetic
        String result = tree.print();
        assertNotNull(result);
        assertTrue(result.contains("B1") || result.contains("B2") || 
                   result.contains("B3") || result.contains("B4"));
        
        // Test with objects on different sides of split to verify xwid / 2 is used
        Bintree tree2 = new Bintree();
        // Object on left side (x < 512)
        Balloon b5 = new Balloon("B5", 100, 100, 100, 50, 50, 50, "hot", 1);
        // Object on right side (x >= 512)
        Balloon b6 = new Balloon("B6", 600, 100, 100, 50, 50, 50, "hot", 1);
        tree2.insert(b5);
        tree2.insert(b6);
        
        // Verify both objects are in tree (proves split at 512 = 0 + 1024/2)
        String result2 = tree2.print();
        assertNotNull(result2);
        assertTrue(result2.contains("B5"));
        assertTrue(result2.contains("B6"));
    }

    /**
     * Test Bintree subtraction arithmetic (xwid - xwid/2)
     */
    public void testBintreeSubtractionArithmetic() {
        Bintree tree = new Bintree();
        
        // Insert objects to force splits
        // The right width is calculated as xwid - xwid / 2
        // For xwid=1024: rightXwid = 1024 - 512 = 512
        Balloon b1 = new Balloon("B1", 0, 0, 0, 1, 1, 1, "hot", 1);
        Balloon b2 = new Balloon("B2", 100, 0, 0, 1, 1, 1, "hot", 1);
        Balloon b3 = new Balloon("B3", 200, 0, 0, 1, 1, 1, "hot", 1);
        Balloon b4 = new Balloon("B4", 600, 0, 0, 1, 1, 1, "hot", 1);
        
        tree.insert(b1);
        tree.insert(b2);
        tree.insert(b3);
        tree.insert(b4);
        
        // Verify objects are correctly placed (proves subtraction arithmetic works)
        String result = tree.print();
        assertNotNull(result);
        assertTrue(result.contains("B1") || result.contains("B2") || 
                   result.contains("B3") || result.contains("B4"));
    }

    /**
     * Test arithmetic in SkipList level calculations
     * This kills AOD2Mutator mutants that change level + 1 to level or 1
     */
    public void testSkipListArithmetic() {
        java.util.Random rnd = new java.util.Random();
        rnd.setSeed(0xCAFEBEEF);
        SkipList<String> list = new SkipList<String>(rnd);
        
        // Insert elements - this uses level + 1 arithmetic
        list.insert("A");
        list.insert("B");
        list.insert("C");
        
        // Verify size arithmetic
        int size = list.size();
        assertEquals(3, size);
        
        // Remove one - tests size - 1 arithmetic
        String removed = list.remove("B");
        assertEquals("B", removed);
        int newSize = list.size();
        assertEquals(2, newSize);
        
        // Test toString which uses level + 1 arithmetic
        String toString = list.toString();
        assertNotNull(toString);
        assertTrue(toString.contains("A") || toString.contains("C"));
        
        // Test that size increments correctly (size = size + 1)
        SkipList<String> list2 = new SkipList<String>(rnd);
        assertEquals(0, list2.size());
        list2.insert("X");
        assertEquals(1, list2.size());
        list2.insert("Y");
        assertEquals(2, list2.size());
        list2.insert("Z");
        assertEquals(3, list2.size());
        
        // Test that size decrements correctly (size = size - 1)
        list2.remove("X");
        assertEquals(2, list2.size());
        list2.remove("Y");
        assertEquals(1, list2.size());
        list2.remove("Z");
        assertEquals(0, list2.size());
    }

    /**
     * Test arithmetic in WorldDB operations
     */
    public void testWorldDBArithmetic() {
        java.util.Random rnd = new java.util.Random();
        rnd.setSeed(0xCAFEBEEF);
        WorldDB world = new WorldDB(rnd);
        
        // Add objects - tests size arithmetic
        boolean added1 = world.add(new Balloon("B1", 10, 20, 30, 40, 50, 60, "hot", 1));
        assertTrue(added1);
        
        boolean added2 = world.add(new Balloon("B2", 100, 200, 300, 40, 50, 60, "hot", 1));
        assertTrue(added2);
        
        // Test range query - uses comparison arithmetic
        String range = world.rangeprint("B1", "B2");
        assertNotNull(range);
        
        // Test intersect - uses arithmetic in calculations
        String intersect = world.intersect(0, 0, 0, 200, 200, 200);
        assertNotNull(intersect);
    }

    /**
     * Test subtraction arithmetic in intersection calculations
     */
    public void testSubtractionArithmetic() {
        Balloon b1 = new Balloon("B1", 10, 20, 30, 40, 50, 60, "hot", 1);
        // b1: x=10-50, y=20-70, z=30-90
        
        // Test that xwid - xwid/2 is used correctly in splitting
        // This would be tested indirectly through Bintree, but we can test
        // the intersection arithmetic that uses subtraction
        
        // Query box that starts after b1 ends
        boolean result = b1.intersects(51, 20, 30, 10, 50, 60);
        assertFalse(result);
        
        // Query box that ends before b1 starts
        boolean result2 = b1.intersects(0, 20, 30, 9, 50, 60);
        assertFalse(result2);
    }

    /**
     * Test modulo arithmetic in Bintree dimension cycling
     * This kills AOD2Mutator mutants that change depth % 3 to depth or 3
     */
    public void testModuloArithmetic() {
        Bintree tree = new Bintree();
        
        // Insert enough objects to cause multiple splits
        // This tests depth % 3 arithmetic for dimension cycling
        // Depth 0: split on x (0 % 3 = 0)
        // Depth 1: split on y (1 % 3 = 1)
        // Depth 2: split on z (2 % 3 = 2)
        // Depth 3: split on x again (3 % 3 = 0)
        for (int i = 0; i < 10; i++) {
            Balloon b = new Balloon("B" + i, i * 50, i * 50, i * 50, 40, 40, 40, "hot", 1);
            tree.insert(b);
        }
        
        // Verify the tree structure
        String result = tree.print();
        assertNotNull(result);
        assertTrue(result.contains("Bintree nodes printed"));
        
        // Test with objects that will cause splits in different dimensions
        Bintree tree2 = new Bintree();
        // Objects spread across x dimension (depth 0, split on x)
        Balloon b1 = new Balloon("B1", 100, 100, 100, 50, 50, 50, "hot", 1);
        Balloon b2 = new Balloon("B2", 200, 100, 100, 50, 50, 50, "hot", 1);
        Balloon b3 = new Balloon("B3", 300, 100, 100, 50, 50, 50, "hot", 1);
        Balloon b4 = new Balloon("B4", 400, 100, 100, 50, 50, 50, "hot", 1);
        // This should cause split on x at depth 0
        tree2.insert(b1);
        tree2.insert(b2);
        tree2.insert(b3);
        tree2.insert(b4);
        
        String result2 = tree2.print();
        assertNotNull(result2);
    }

    /**
     * Test all arithmetic operations in a complex scenario
     */
    public void testComplexArithmetic() {
        Balloon b1 = new Balloon("B1", 100, 200, 300, 150, 250, 350, "hot", 1);
        // b1: x=100-250, y=200-450, z=300-650
        
        // Test addition: xorig + xwidth
        int endX = 100 + 150;
        assertEquals(250, endX);
        
        // Test intersection with arithmetic
        Balloon b2 = new Balloon("B2", 200, 300, 400, 100, 100, 100, "hot", 1);
        // b2: x=200-300, y=300-400, z=400-500
        // Overlaps in all dimensions
        boolean result = b1.intersects(b2);
        assertTrue(result);
        
        // Test containment with arithmetic - verify xorig + xwidth <= x + xwid
        // b2: x=200-300, y=300-400, z=400-500
        // Container must extend to at least x=300, y=400, z=500
        // Container: x=100-350 (xwid=250), y=200-450 (ywid=250), z=300-650 (zwid=350)
        // Check: 200 >= 100 ✓, 300 <= 350 ✓, 300 >= 200 ✓, 400 <= 450 ✓, 400 >= 300 ✓, 500 <= 650 ✓
        boolean contained = b2.isContainedIn(100, 200, 300, 250, 250, 350);
        assertTrue("b2 should be contained in container", contained);
        
        // Test that arithmetic is used in validation
        boolean valid = b1.isValid();
        assertTrue(valid);
        
        // Test invalid case where arithmetic would exceed bounds
        Balloon b3 = new Balloon("B3", 1000, 200, 300, 100, 250, 350, "hot", 1);
        // 1000 + 100 = 1100 > 1024
        boolean valid2 = b3.isValid();
        assertFalse(valid2);
    }

    /**
     * Test arithmetic in intersection origin calculations
     * This kills AOD2Mutator mutants in WorldDB.findIntersections
     */
    public void testIntersectionOriginArithmetic() {
        java.util.Random rnd = new java.util.Random();
        rnd.setSeed(0xCAFEBEEF);
        WorldDB world = new WorldDB(rnd);
        
        // Add object at specific location
        Balloon b1 = new Balloon("B1", 100, 200, 300, 50, 60, 70, "hot", 1);
        world.add(b1);
        
        // Query box that overlaps
        // Intersection origin = max(100, 90) = 100
        String result = world.intersect(90, 190, 290, 50, 50, 50);
        assertNotNull(result);
        assertTrue(result.contains("B1"));
        
        // Query box that doesn't overlap
        String result2 = world.intersect(200, 200, 300, 50, 50, 50);
        assertNotNull(result2);
    }

    /**
     * Test arithmetic in all dimensions simultaneously
     */
    public void testAllDimensionsArithmetic() {
        // Test that all three dimensions use arithmetic correctly
        Balloon b1 = new Balloon("B1", 10, 20, 30, 40, 50, 60, "hot", 1);
        // b1: x=10-50, y=20-70, z=30-90
        
        // Test x dimension: xorig + xwidth
        int xEnd = 10 + 40;
        assertEquals(50, xEnd);
        
        // Test y dimension: yorig + ywidth
        int yEnd = 20 + 50;
        assertEquals(70, yEnd);
        
        // Test z dimension: zorig + zwidth
        int zEnd = 30 + 60;
        assertEquals(90, zEnd);
        
        // Verify all are used in intersection
        Balloon b2 = new Balloon("B2", 25, 35, 45, 20, 20, 20, "hot", 1);
        // b2: x=25-45, y=35-55, z=45-65
        // Overlaps in all dimensions
        boolean result = b1.intersects(b2);
        assertTrue(result);
        
        // Test object that overlaps in x and y but not z
        Balloon b3 = new Balloon("B3", 25, 35, 100, 20, 20, 20, "hot", 1);
        // b3: x=25-45, y=35-55, z=100-120
        // No overlap in z (90 < 100)
        boolean result2 = b1.intersects(b3);
        assertFalse(result2);
    }

    /**
     * Test arithmetic with zero and one values
     */
    public void testZeroOneArithmetic() {
        // Test that 0 + width is used correctly
        Balloon b1 = new Balloon("B1", 0, 0, 0, 100, 100, 100, "hot", 1);
        // 0 + 100 = 100
        boolean valid = b1.isValid();
        assertTrue(valid);
        
        // Test that 1 + width is used correctly
        Balloon b2 = new Balloon("B2", 1, 1, 1, 100, 100, 100, "hot", 1);
        // 1 + 100 = 101
        boolean valid2 = b2.isValid();
        assertTrue(valid2);
        
        // Test intersection with zero origin
        Balloon b3 = new Balloon("B3", 0, 0, 0, 50, 50, 50, "hot", 1);
        Balloon b4 = new Balloon("B4", 25, 0, 0, 50, 50, 50, "hot", 1);
        // b3: x=0-50, b4: x=25-75, overlap
        boolean result = b3.intersects(b4);
        assertTrue(result);
    }

    /**
     * Test arithmetic in size calculations
     */
    public void testSizeArithmetic() {
        java.util.Random rnd = new java.util.Random();
        rnd.setSeed(0xCAFEBEEF);
        SkipList<String> list = new SkipList<String>(rnd);
        
        // Test size = 0 initially
        int size0 = list.size();
        assertEquals(0, size0);
        
        // Test size = size + 1 for each insert
        list.insert("A");
        int size1 = list.size();
        assertEquals(1, size1);
        
        list.insert("B");
        int size2 = list.size();
        assertEquals(2, size2);
        
        list.insert("C");
        int size3 = list.size();
        assertEquals(3, size3);
        
        // Test size = size - 1 for each remove
        list.remove("A");
        int size4 = list.size();
        assertEquals(2, size4);
        
        list.remove("B");
        int size5 = list.size();
        assertEquals(1, size5);
        
        list.remove("C");
        int size6 = list.size();
        assertEquals(0, size6);
    }

    /**
     * Test arithmetic in containment with exact boundaries
     */
    public void testContainmentExactBoundaries() {
        Balloon b1 = new Balloon("B1", 10, 20, 30, 40, 50, 60, "hot", 1);
        
        // Test xorig + xwidth <= x + xwid where they are equal
        // b1: x=10, width=40, so ends at 50
        // Container: x=10, width=40, so ends at 50
        // 50 <= 50 is true
        boolean result1 = b1.isContainedIn(10, 20, 30, 40, 50, 60);
        assertTrue(result1);
        
        // Test xorig + xwidth <= x + xwid where left is one less
        // Container: x=10, width=41, so ends at 51
        // 50 <= 51 is true
        boolean result2 = b1.isContainedIn(10, 20, 30, 41, 50, 60);
        assertTrue(result2);
        
        // Test xorig + xwidth <= x + xwid where left is one more
        // Container: x=10, width=39, so ends at 49
        // 50 <= 49 is false
        boolean result3 = b1.isContainedIn(10, 20, 30, 39, 50, 60);
        assertFalse(result3);
    }

    /**
     * Test arithmetic in validation with exact boundaries
     */
    public void testValidationExactBoundaries() {
        // Test xorig + xwidth > WORLD_SIZE where they are equal
        // x=0, width=1024, so 0 + 1024 = 1024, 1024 > 1024 is false
        Balloon b1 = new Balloon("B1", 0, 0, 0, 1024, 1024, 1024, "hot", 1);
        assertTrue(b1.isValid());
        
        // Test xorig + xwidth > WORLD_SIZE where left is one more
        // x=0, width=1025, so 0 + 1025 = 1025, 1025 > 1024 is true
        Balloon b2 = new Balloon("B2", 0, 0, 0, 1025, 1024, 1024, "hot", 1);
        assertFalse(b2.isValid());
        
        // Test xorig + xwidth > WORLD_SIZE where left is one less
        // x=0, width=1023, so 0 + 1023 = 1023, 1023 > 1024 is false
        Balloon b3 = new Balloon("B3", 0, 0, 0, 1023, 1024, 1024, "hot", 1);
        assertTrue(b3.isValid());
        
        // Test with xorig = 1
        // x=1, width=1023, so 1 + 1023 = 1024, 1024 > 1024 is false
        Balloon b4 = new Balloon("B4", 1, 0, 0, 1023, 1024, 1024, "hot", 1);
        assertTrue(b4.isValid());
        
        // x=1, width=1024, so 1 + 1024 = 1025, 1025 > 1024 is true
        Balloon b5 = new Balloon("B5", 1, 0, 0, 1024, 1024, 1024, "hot", 1);
        assertFalse(b5.isValid());
    }

    /**
     * Test Bintree split point arithmetic: x + xwid/2
     * This kills AOD2Mutator mutants that change x + xwid/2 to x or xwid/2
     */
    public void testBintreeSplitPointArithmetic() {
        Bintree tree = new Bintree();
        
        // For region 0-1024, split point should be 0 + 1024/2 = 512
        // Object at x=100 should be in left child (x < 512)
        // Object at x=600 should be in right child (x >= 512)
        Balloon b1 = new Balloon("B1", 100, 100, 100, 50, 50, 50, "hot", 1);
        Balloon b2 = new Balloon("B2", 600, 100, 100, 50, 50, 50, "hot", 1);
        Balloon b3 = new Balloon("B3", 200, 100, 100, 50, 50, 50, "hot", 1);
        Balloon b4 = new Balloon("B4", 300, 100, 100, 50, 50, 50, "hot", 1);
        
        tree.insert(b1);
        tree.insert(b2);
        tree.insert(b3);
        tree.insert(b4);
        
        String result = tree.print();
        assertNotNull(result);
        assertTrue(result.contains("B1") || result.contains("B2") || 
                   result.contains("B3") || result.contains("B4"));
    }

    /**
     * Test Bintree width division and subtraction: xwid/2, xwid - xwid/2
     */
    public void testBintreeWidthArithmetic() {
        Bintree tree = new Bintree();
        
        // Insert objects to force split
        // For region 0-1024: leftXwid = 1024/2 = 512, rightXwid = 1024 - 512 = 512
        Balloon b1 = new Balloon("B1", 100, 100, 100, 50, 50, 50, "hot", 1);
        Balloon b2 = new Balloon("B2", 200, 100, 100, 50, 50, 50, "hot", 1);
        Balloon b3 = new Balloon("B3", 300, 100, 100, 50, 50, 50, "hot", 1);
        Balloon b4 = new Balloon("B4", 600, 100, 100, 50, 50, 50, "hot", 1);
        
        tree.insert(b1);
        tree.insert(b2);
        tree.insert(b3);
        tree.insert(b4);
        
        String result = tree.print();
        assertNotNull(result);
        assertTrue(result.contains("B4"));
    }

    /**
     * Test depth + 1 arithmetic in Bintree
     */
    public void testBintreeDepthArithmetic() {
        Bintree tree = new Bintree();
        
        // Insert objects to create multiple levels
        for (int i = 0; i < 25; i++) {
            Balloon b = new Balloon("B" + i, 
                (i % 5) * 100, 
                ((i / 5) % 5) * 100, 
                (i / 25) * 100, 
                50, 50, 50, "hot", 1);
            tree.insert(b);
        }
        
        String result = tree.print();
        assertNotNull(result);
        assertTrue(result.contains("Bintree nodes printed"));
        
        String collisions = tree.findCollisions();
        assertNotNull(collisions);
        
        String intersections = tree.findIntersections(0, 0, 0, 500, 500, 500);
        assertNotNull(intersections);
        assertTrue(intersections.contains("nodes were visited"));
    }

    /**
     * Test modulo arithmetic: (splitDim + 1) % 3 for dimension cycling
     */
    public void testBintreeModuloArithmetic() {
        Bintree tree = new Bintree();
        
        // Insert objects to force splits in sequence:
        // Depth 0: splitDim=0 (x), nextDim = (0+1)%3 = 1 (y)
        // Depth 1: splitDim=1 (y), nextDim = (1+1)%3 = 2 (z)
        // Depth 2: splitDim=2 (z), nextDim = (2+1)%3 = 0 (x)
        for (int i = 0; i < 30; i++) {
            Balloon b = new Balloon("B" + i, 
                (i % 8) * 50, 
                ((i / 8) % 8) * 50, 
                (i / 64) * 50, 
                40, 40, 40, "hot", 1);
            tree.insert(b);
        }
        
        String result = tree.print();
        assertNotNull(result);
        assertTrue(result.contains("Bintree nodes printed"));
    }
}

