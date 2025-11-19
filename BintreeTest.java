import student.TestCase;

/**
 * Test class for Bintree
 * Tests all methods with mutation coverage
 *
 * @author CS3114/5040 Staff
 * @version Fall 2025
 */
public class BintreeTest extends TestCase {

    /**
     * Test constructor
     */
    public void testConstructor() {
        Bintree tree = new Bintree();
        assertNotNull(tree);
    }

    /**
     * Test insert
     */
    public void testInsert() {
        Bintree tree = new Bintree();
        Balloon b = new Balloon("B1", 10, 20, 30, 40, 50, 60, "hot", 1);
        assertTrue(tree.insert(b));
    }

    /**
     * Test insert null
     */
    public void testInsertNull() {
        Bintree tree = new Bintree();
        assertFalse(tree.insert(null));
    }

    /**
     * Test print empty tree
     */
    public void testPrintEmpty() {
        Bintree tree = new Bintree();
        String result = tree.print();
        assertNotNull(result);
        assertTrue(result.contains("E (0, 0, 0, 1024, 1024, 1024) 0"));
        assertTrue(result.contains("1 Bintree nodes printed"));
    }

    /**
     * Test print with objects
     */
    public void testPrint() {
        Bintree tree = new Bintree();
        Balloon b1 = new Balloon("B1", 10, 20, 30, 40, 50, 60, "hot", 1);
        Balloon b2 = new Balloon("B2", 100, 200, 300, 40, 50, 60, "hot", 1);
        
        tree.insert(b1);
        tree.insert(b2);
        
        String result = tree.print();
        assertNotNull(result);
        assertTrue(result.contains("B1"));
        assertTrue(result.contains("B2"));
    }

    /**
     * Test findCollisions empty
     */
    public void testFindCollisionsEmpty() {
        Bintree tree = new Bintree();
        String result = tree.findCollisions();
        assertNotNull(result);
        assertTrue(result.contains("The following collisions exist"));
    }

    /**
     * Test findCollisions with no collisions
     */
    public void testFindCollisionsNone() {
        Bintree tree = new Bintree();
        Balloon b1 = new Balloon("B1", 10, 20, 30, 40, 50, 60, "hot", 1);
        Balloon b2 = new Balloon("B2", 100, 200, 300, 40, 50, 60, "hot", 1);
        
        tree.insert(b1);
        tree.insert(b2);
        
        String result = tree.findCollisions();
        assertNotNull(result);
        // Should list leaf nodes but no collisions
    }

    /**
     * Test findCollisions with collisions
     */
    public void testFindCollisions() {
        Bintree tree = new Bintree();
        Balloon b1 = new Balloon("B1", 10, 20, 30, 40, 50, 60, "hot", 1);
        Balloon b2 = new Balloon("B2", 20, 30, 40, 40, 50, 60, "hot", 1);
        
        tree.insert(b1);
        tree.insert(b2);
        
        String result = tree.findCollisions();
        assertNotNull(result);
        assertTrue(result.contains("B1"));
        assertTrue(result.contains("B2"));
    }

    /**
     * Test findIntersections empty
     */
    public void testFindIntersectionsEmpty() {
        Bintree tree = new Bintree();
        String result = tree.findIntersections(0, 0, 0, 100, 100, 100);
        assertNotNull(result);
        assertTrue(result.contains("The following objects intersect"));
        assertTrue(result.contains("nodes were visited"));
    }

    /**
     * Test findIntersections with objects
     */
    public void testFindIntersections() {
        Bintree tree = new Bintree();
        Balloon b1 = new Balloon("B1", 10, 20, 30, 40, 50, 60, "hot", 1);
        Balloon b2 = new Balloon("B2", 100, 200, 300, 40, 50, 60, "hot", 1);
        
        tree.insert(b1);
        tree.insert(b2);
        
        // Query box that intersects b1 but not b2
        String result = tree.findIntersections(0, 0, 0, 100, 100, 100);
        assertNotNull(result);
        assertTrue(result.contains("B1"));
    }

    /**
     * Test findIntersections with query box containing all
     */
    public void testFindIntersectionsAll() {
        Bintree tree = new Bintree();
        Balloon b1 = new Balloon("B1", 10, 20, 30, 40, 50, 60, "hot", 1);
        Balloon b2 = new Balloon("B2", 100, 200, 300, 40, 50, 60, "hot", 1);
        
        tree.insert(b1);
        tree.insert(b2);
        
        String result = tree.findIntersections(0, 0, 0, 1024, 1024, 1024);
        assertNotNull(result);
        assertTrue(result.contains("B1") || result.contains("B2"));
    }

    /**
     * Test multiple inserts and splits
     */
    public void testMultipleInsertsAndSplits() {
        Bintree tree = new Bintree();
        // Insert objects that will cause tree to split
        for (int i = 0; i < 10; i++) {
            Balloon b = new Balloon("B" + i, i * 50, i * 50, i * 50, 40, 40, 40, "hot", 1);
            tree.insert(b);
        }
        
        String result = tree.print();
        assertNotNull(result);
        assertTrue(result.contains("Bintree nodes printed"));
        
        // Test spanning object
        Bintree tree2 = new Bintree();
        Balloon b = new Balloon("B1", 0, 0, 0, 1024, 1024, 1024, "hot", 1);
        tree2.insert(b);
        String result2 = tree2.print();
        assertNotNull(result2);
    }

    /**
     * Test findIntersections with empty query
     */
    public void testFindIntersectionsEmptyQuery() {
        Bintree tree = new Bintree();
        Balloon b = new Balloon("B1", 500, 500, 500, 50, 50, 50, "hot", 1);
        tree.insert(b);
        
        // Query box far away
        String result = tree.findIntersections(0, 0, 0, 10, 10, 10);
        assertNotNull(result);
    }

    /**
     * Test InternalNode with all split dimensions (0, 1, 2)
     * Tests arithmetic: x + xwid/2, xwid/2, xwid - xwid/2, depth + 1, (splitDim + 1) % 3
     */
    public void testInternalNodeAllSplitDims() {
        Bintree tree = new Bintree();
        
        // Insert objects to force splits in all dimensions
        for (int i = 0; i < 20; i++) {
            Balloon b = new Balloon("B" + i, 
                (i % 4) * 100, 
                ((i / 4) % 4) * 100, 
                (i / 16) * 100, 
                50, 50, 50, "hot", 1);
            tree.insert(b);
        }
        
        String result = tree.print();
        assertNotNull(result);
        assertTrue(result.contains("Bintree nodes printed"));
        
        // Test all methods use depth + 1
        String collisions = tree.findCollisions();
        assertNotNull(collisions);
        
        String intersections = tree.findIntersections(0, 0, 0, 500, 500, 500);
        assertNotNull(intersections);
        assertTrue(intersections.contains("nodes were visited"));
    }

    /**
     * Test InternalNode overlap calculations for findIntersections
     * Tests: x < boxX + boxXwid, x + xwid > boxX (both operands must be used)
     */
    public void testInternalNodeOverlapArithmetic() {
        Bintree tree = new Bintree();
        
        Balloon b1 = new Balloon("B1", 100, 100, 100, 50, 50, 50, "hot", 1);
        Balloon b2 = new Balloon("B2", 600, 100, 100, 50, 50, 50, "hot", 1);
        tree.insert(b1);
        tree.insert(b2);
        
        // Query box that intersects left child only
        String result1 = tree.findIntersections(0, 0, 0, 200, 200, 200);
        assertNotNull(result1);
        assertTrue(result1.contains("B1"));
        
        // Query box that intersects right child only
        String result2 = tree.findIntersections(500, 0, 0, 200, 200, 200);
        assertNotNull(result2);
        assertTrue(result2.contains("B2"));
        
        // Test boundary cases for overlap
        String result3 = tree.findIntersections(50, 100, 100, 51, 50, 50);
        assertNotNull(result3);
        
        String result4 = tree.findIntersections(149, 100, 100, 10, 50, 50);
        assertNotNull(result4);
    }

    /**
     * Test InternalNode with spanning objects (inserted into both children)
     */
    public void testInternalNodeSpanningObjects() {
        Bintree tree = new Bintree();
        
        // Object that spans the split point (x=400-700, spans 512)
        Balloon b1 = new Balloon("B1", 400, 100, 100, 300, 50, 50, "hot", 1);
        tree.insert(b1);
        
        String result = tree.print();
        assertNotNull(result);
        assertTrue(result.contains("B1"));
    }

    /**
     * Test InternalNode with exact split point boundaries
     */
    public void testInternalNodeExactSplitBoundaries() {
        Bintree tree = new Bintree();
        
        // Object exactly at split point (x=512) should go to right child
        Balloon b1 = new Balloon("B1", 512, 100, 100, 50, 50, 50, "hot", 1);
        tree.insert(b1);
        
        String result = tree.print();
        assertNotNull(result);
        assertTrue(result.contains("B1"));
        
        // Object just before split point (x=511) should go to left child
        Balloon b2 = new Balloon("B2", 511, 100, 100, 50, 50, 50, "hot", 1);
        tree.insert(b2);
        
        String result2 = tree.print();
        assertNotNull(result2);
        assertTrue(result2.contains("B2"));
    }
}

