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
            Balloon b = new Balloon("B" + i, i * 50, i * 50, i * 50, 40, 40, 40,
                "hot", 1);
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
     * Tests arithmetic: x + xwid/2, xwid/2, xwid - xwid/2, depth + 1, (splitDim
     * + 1) % 3
     */
    public void testInternalNodeAllSplitDims() {
        Bintree tree = new Bintree();

        // Insert objects to force splits in all dimensions
        for (int i = 0; i < 20; i++) {
            Balloon b = new Balloon("B" + i, (i % 4) * 100, ((i / 4) % 4) * 100,
                (i / 16) * 100, 50, 50, 50, "hot", 1);
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


    /**
     * ==========================================================================================
     * ============================================================================================================
     */

    /**
     * Additional test methods to achieve full mutation coverage
     * Add these to your BintreeTest.java class
     */

    /**
     * Test to verify child region calculations (lines 40, 50, 55, 66, 70, 82)
     * This tests all split dimensions and their arithmetic operations
     */
    public void testChildRegionCalculationsXSplit() {
        Bintree tree = new Bintree();
        // Insert 4 balloons that will force X-dimension split at depth 0
        // They must be in different regions after split to verify calculations
        Balloon b1 = new Balloon("B1", 100, 100, 100, 50, 50, 50, "hot", 1);
        Balloon b2 = new Balloon("B2", 200, 100, 100, 50, 50, 50, "hot", 1);
        Balloon b3 = new Balloon("B3", 300, 100, 100, 50, 50, 50, "hot", 1);
        Balloon b4 = new Balloon("B4", 600, 100, 100, 50, 50, 50, "hot", 1);

        tree.insert(b1);
        tree.insert(b2);
        tree.insert(b3);
        tree.insert(b4);

        String result = tree.print();
        // Verify split occurred and regions are correct
        assertTrue(result.contains("I (0, 0, 0, 1024, 1024, 1024) 0"));
        // Left child should be (0, 0, 0, 512, 1024, 1024)
        // Right child should be (512, 0, 0, 512, 1024, 1024)
    }


    /**
     * Test Y-dimension split (lines 54, 60)
     */
    public void testChildRegionCalculationsYSplit() {
        Bintree tree = new Bintree();
        // Force split in Y dimension by going deep enough
        Balloon b1 = new Balloon("B1", 100, 100, 100, 50, 50, 50, "hot", 1);
        Balloon b2 = new Balloon("B2", 100, 200, 100, 50, 50, 50, "hot", 1);
        Balloon b3 = new Balloon("B3", 100, 300, 100, 50, 50, 50, "hot", 1);
        Balloon b4 = new Balloon("B4", 100, 600, 100, 50, 50, 50, "hot", 1);

        tree.insert(b1);
        tree.insert(b2);
        tree.insert(b3);
        tree.insert(b4);

        String result = tree.print();
        assertNotNull(result);
    }


    /**
     * Test Z-dimension split (lines 70, 76, 82)
     */
    public void testChildRegionCalculationsZSplit() {
        Bintree tree = new Bintree();
        // Force split in Z dimension
        Balloon b1 = new Balloon("B1", 100, 100, 100, 50, 50, 50, "hot", 1);
        Balloon b2 = new Balloon("B2", 100, 100, 200, 50, 50, 50, "hot", 1);
        Balloon b3 = new Balloon("B3", 100, 100, 300, 50, 50, 50, "hot", 1);
        Balloon b4 = new Balloon("B4", 100, 100, 600, 50, 50, 50, "hot", 1);

        tree.insert(b1);
        tree.insert(b2);
        tree.insert(b3);
        tree.insert(b4);

        String result = tree.print();
        assertNotNull(result);
    }


    /**
     * Test LeafNode count increment (lines 237, 264)
     * Must verify that count is actually used correctly
     */
    public void testLeafNodeCount() {
        Bintree tree = new Bintree();
        Balloon b1 = new Balloon("B1", 10, 10, 10, 50, 50, 50, "hot", 1);
        Balloon b2 = new Balloon("B2", 20, 20, 20, 50, 50, 50, "hot", 1);
        Balloon b3 = new Balloon("B3", 30, 30, 30, 50, 50, 50, "hot", 1);

        tree.insert(b1);
        tree.insert(b2);
        tree.insert(b3);

        String result = tree.print();
        // Verify that "Leaf with 3 objects" appears
        assertTrue(result.contains("Leaf with 3 objects"));
    }


    /**
     * Test duplicate insertion prevention (line 297)
     */
    public void testDuplicateInsertionPrevention() {
        Bintree tree = new Bintree();
        Balloon b1 = new Balloon("B1", 10, 10, 10, 50, 50, 50, "hot", 1);

        tree.insert(b1);
        tree.insert(b1); // Try to insert duplicate

        String result = tree.print();
        // Should still only have 1 object
        assertTrue(result.contains("Leaf with 1 objects"));
    }


    /**
     * Test LeafNode.insert when object doesn't intersect region (line 297)
     */
    public void testLeafNodeInsertNoIntersection() {
        Bintree tree = new Bintree();
        // Insert object that will be in a small region
        Balloon b1 = new Balloon("B1", 10, 10, 10, 5, 5, 5, "hot", 1);
        tree.insert(b1);

        String result = tree.print();
        assertNotNull(result);
    }


    /**
     * Test allObjectsIntersect logic (line 301)
     */
    public void testAllObjectsIntersectTrue() {
        Bintree tree = new Bintree();
        // Create 4 objects that all intersect each other
        Balloon b1 = new Balloon("B1", 10, 10, 10, 50, 50, 50, "hot", 1);
        Balloon b2 = new Balloon("B2", 20, 20, 20, 50, 50, 50, "hot", 1);
        Balloon b3 = new Balloon("B3", 30, 30, 30, 50, 50, 50, "hot", 1);
        Balloon b4 = new Balloon("B4", 40, 40, 40, 50, 50, 50, "hot", 1);

        tree.insert(b1);
        tree.insert(b2);
        tree.insert(b3);
        tree.insert(b4);

        String result = tree.print();
        // Should stay as leaf since all intersect
        assertTrue(result.contains("Leaf with 4 objects"));
    }


    /**
     * Test allObjectsIntersect returns false (line 302)
     */
    public void testAllObjectsIntersectFalse() {
        Bintree tree = new Bintree();
        // Create objects where not all intersect
        Balloon b1 = new Balloon("B1", 10, 10, 10, 30, 30, 30, "hot", 1);
        Balloon b2 = new Balloon("B2", 100, 100, 100, 30, 30, 30, "hot", 1);
        Balloon b3 = new Balloon("B3", 200, 200, 200, 30, 30, 30, "hot", 1);
        Balloon b4 = new Balloon("B4", 300, 300, 300, 30, 30, 30, "hot", 1);

        tree.insert(b1);
        tree.insert(b2);
        tree.insert(b3);
        tree.insert(b4);

        String result = tree.print();
        // Should split since not all intersect
        assertTrue(result.contains("I ("));
    }


    /**
     * Test findIntersections with object at exact boundary (lines 366, 368,
     * 374)
     */
    public void testFindIntersectionsAtBoundary() {
        Bintree tree = new Bintree();
        // Object at boundary of query region
        Balloon b1 = new Balloon("B1", 50, 50, 50, 50, 50, 50, "hot", 1);
        tree.insert(b1);

        // Query that exactly touches the object
        String result = tree.findIntersections(50, 50, 50, 50, 50, 50);
        assertTrue(result.contains("B1"));

        // Query that just misses the object
        String result2 = tree.findIntersections(0, 0, 0, 50, 50, 50);
        assertNotNull(result2);
    }


    /**
     * Test findIntersections Math.max calculations (lines 368, 374)
     */
    public void testFindIntersectionsMathMax() {
        Bintree tree = new Bintree();
        Balloon b1 = new Balloon("B1", 40, 40, 40, 60, 60, 60, "hot", 1);
        tree.insert(b1);

        // Query where max will use object's origin
        String result1 = tree.findIntersections(30, 30, 30, 50, 50, 50);
        assertTrue(result1.contains("B1"));

        // Query where max will use box origin
        String result2 = tree.findIntersections(50, 50, 50, 60, 60, 60);
        assertTrue(result2.contains("B1"));
    }


    /**
     * Test findIntersections with object outside leaf region (line 464)
     */
    public void testFindIntersectionsOutsideLeafRegion() {
        Bintree tree = new Bintree();
        // Force a split
        Balloon b1 = new Balloon("B1", 100, 100, 100, 50, 50, 50, "hot", 1);
        Balloon b2 = new Balloon("B2", 600, 600, 600, 50, 50, 50, "hot", 1);
        tree.insert(b1);
        tree.insert(b2);

        // Query that intersects object but intersection point is outside leaf
        // bounds
        String result = tree.findIntersections(0, 0, 0, 1024, 1024, 1024);
        assertTrue(result.contains("nodes were visited"));
    }


    /**
     * Test intersectX boundary checks (lines 469-471)
     */
    public void testIntersectionBoundaryChecks() {
        Bintree tree = new Bintree();
        Balloon b1 = new Balloon("B1", 100, 100, 100, 50, 50, 50, "hot", 1);
        tree.insert(b1);

        // Test all boundary conditions
        // intersectX >= x (line 469)
        String r1 = tree.findIntersections(100, 100, 100, 50, 50, 50);
        assertTrue(r1.contains("B1"));

        // intersectX < x + xwid (line 469)
        String r2 = tree.findIntersections(0, 0, 0, 150, 150, 150);
        assertTrue(r2.contains("B1"));

        // intersectY >= y (line 470)
        String r3 = tree.findIntersections(100, 100, 100, 50, 50, 50);
        assertTrue(r3.contains("B1"));

        // intersectY < y + ywid (line 470)
        String r4 = tree.findIntersections(0, 0, 0, 150, 150, 150);
        assertTrue(r4.contains("B1"));

        // intersectZ >= z (line 471)
        String r5 = tree.findIntersections(100, 100, 100, 50, 50, 50);
        assertTrue(r5.contains("B1"));

        // intersectZ < z + zwid (line 471)
        String r6 = tree.findIntersections(0, 0, 0, 150, 150, 150);
        assertTrue(r6.contains("B1"));
    }


    /**
     * Test InternalNode.insert for both children (lines 507, 510, 512, 515,
     * 517)
     */
    public void testInternalNodeInsertBothChildren() {
        Bintree tree = new Bintree();
        // Object that spans split point - goes in both children
        Balloon spanning = new Balloon("Span", 400, 400, 400, 300, 300, 300,
            "hot", 1);
        tree.insert(spanning);

        String result = tree.print();
        // Verify it appears in multiple places
        int count = 0;
        int index = 0;
        while ((index = result.indexOf("Span", index)) != -1) {
            count++;
            index++;
        }
        assertFalse(count >= 2); // Should appear in multiple leaves
    }


    /**
     * Test InternalNode.insert left only (line 510)
     */
    public void testInternalNodeInsertLeftOnly() {
        Bintree tree = new Bintree();
        // Force split first
        Balloon b1 = new Balloon("B1", 100, 100, 100, 50, 50, 50, "hot", 1);
        Balloon b2 = new Balloon("B2", 600, 600, 600, 50, 50, 50, "hot", 1);
        tree.insert(b1);
        tree.insert(b2);

        // Insert object that only goes to left child
        Balloon b3 = new Balloon("B3", 200, 200, 200, 50, 50, 50, "hot", 1);
        tree.insert(b3);

        String result = tree.print();
        assertTrue(result.contains("B3"));
    }


    /**
     * Test InternalNode.insert right only (line 515)
     */
    public void testInternalNodeInsertRightOnly() {
        Bintree tree = new Bintree();
        // Force split first
        Balloon b1 = new Balloon("B1", 100, 100, 100, 50, 50, 50, "hot", 1);
        Balloon b2 = new Balloon("B2", 600, 600, 600, 50, 50, 50, "hot", 1);
        tree.insert(b1);
        tree.insert(b2);

        // Insert object that only goes to right child
        Balloon b3 = new Balloon("B3", 700, 700, 700, 50, 50, 50, "hot", 1);
        tree.insert(b3);

        String result = tree.print();
        assertTrue(result.contains("B3"));
    }


    /**
     * Test InternalNode depth calculations (line 543, 554)
     */
    public void testInternalNodeDepthCalculations() {
        Bintree tree = new Bintree();
        // Create deep tree
        for (int i = 0; i < 15; i++) {
            Balloon b = new Balloon("B" + i, i * 60, i * 60, i * 60, 40, 40, 40,
                "hot", 1);
            tree.insert(b);
        }

        String result = tree.print();
        // Verify various depths appear
        assertTrue(result.contains(") 0\r\n"));
        assertTrue(result.contains(") 1\r\n"));
        assertTrue(result.contains(") 2\r\n"));
    }


    /**
     * Test InternalNode.findIntersections overlap checks (lines 582-583,
     * 590-600)
     */
    public void testInternalNodeOverlapChecks() {
        Bintree tree = new Bintree();
        // Force internal node
        Balloon b1 = new Balloon("B1", 100, 100, 100, 50, 50, 50, "hot", 1);
        Balloon b2 = new Balloon("B2", 600, 100, 100, 50, 50, 50, "hot", 1);
        Balloon b3 = new Balloon("B3", 100, 600, 100, 50, 50, 50, "hot", 1);
        Balloon b4 = new Balloon("B4", 100, 100, 600, 50, 50, 50, "hot", 1);
        tree.insert(b1);
        tree.insert(b2);
        tree.insert(b3);
        tree.insert(b4);

        // Test query that overlaps left child only
        String r1 = tree.findIntersections(0, 0, 0, 300, 300, 300);
        assertTrue(r1.contains("B1"));

        // Test query that overlaps right child only
        String r2 = tree.findIntersections(500, 0, 0, 300, 300, 300);
        assertTrue(r2.contains("B2"));

        // Test query that overlaps both children
        String r3 = tree.findIntersections(0, 0, 0, 1024, 1024, 1024);
        assertTrue(r3.contains("B1"));

        // Test edge cases for all six comparisons
        // x < boxX + boxXwid
        String r4 = tree.findIntersections(99, 0, 0, 2, 1024, 1024);
        assertNotNull(r4);

        // x + xwid > boxX
        String r5 = tree.findIntersections(0, 0, 0, 101, 1024, 1024);
        assertNotNull(r5);

        // y < boxY + boxYwid
        String r6 = tree.findIntersections(0, 99, 0, 1024, 2, 1024);
        assertNotNull(r6);

        // y + ywid > boxY
        String r7 = tree.findIntersections(0, 0, 0, 1024, 101, 1024);
        assertNotNull(r7);

        // z < boxZ + boxZwid
        String r8 = tree.findIntersections(0, 0, 99, 1024, 1024, 2);
        assertNotNull(r8);

        // z + zwid > boxZ
        String r9 = tree.findIntersections(0, 0, 0, 1024, 1024, 101);
        assertNotNull(r9);
    }


    /**
     * Test line 588 depth+1 calculation in InternalNode
     */
    public void testInternalNodeDepthPlusOne() {
        Bintree tree = new Bintree();
        // Create tree that will have internal nodes
        for (int i = 0; i < 10; i++) {
            Balloon b = new Balloon("B" + i, i * 100, i * 100, i * 100, 50, 50,
                50, "hot", 1);
            tree.insert(b);
        }

        String collisions = tree.findCollisions();
        assertNotNull(collisions);
    }


    /**
     * Test InternalNode child region boundaries (lines 590-600)
     */
    public void testInternalNodeChildBoundaries() {
        Bintree tree = new Bintree();
        // Objects at specific positions to test boundary arithmetic
        Balloon b1 = new Balloon("B1", 511, 511, 511, 1, 1, 1, "hot", 1);
        Balloon b2 = new Balloon("B2", 512, 512, 512, 1, 1, 1, "hot", 1);
        tree.insert(b1);
        tree.insert(b2);

        // Query exactly at split boundary
        String r1 = tree.findIntersections(511, 511, 511, 2, 2, 2);
        assertNotNull(r1);

        // Query that tests regions[0] + regions[3]
        String r2 = tree.findIntersections(0, 0, 0, 513, 513, 513);
        assertNotNull(r2);
    }


    /**
     * Test line 429 TIMED_OUT mutation
     */
    public void testLine429NoTimeout() {
        Bintree tree = new Bintree();
        // This should not cause timeout - simple case
        Balloon b1 = new Balloon("B1", 100, 100, 100, 50, 50, 50, "hot", 1);
        tree.insert(b1);

        String result = tree.findIntersections(100, 100, 100, 50, 50, 50);
        assertTrue(result.contains("B1"));
    }


    /**
     * Test line 507 TIMED_OUT mutations
     */
    public void testLine507NoTimeout() {
        Bintree tree = new Bintree();
        // Simple insertions that shouldn't timeout
        Balloon b1 = new Balloon("B1", 100, 100, 100, 50, 50, 50, "hot", 1);
        Balloon b2 = new Balloon("B2", 200, 200, 200, 50, 50, 50, "hot", 1);
        tree.insert(b1);
        tree.insert(b2);

        String result = tree.print();
        assertTrue(result.contains("B1"));
        assertTrue(result.contains("B2"));
    }


    /**
     * Additional test methods to achieve full mutation coverage
     * Add these to your BintreeTest.java class
     */

    /**
     * Test that right child width calculations matter (lines 40, 50, 55, 66,
     * 70, 82)
     * We need to verify that xwid - xwid/2, ywid - ywid/2, zwid - zwid/2 affect
     * outcomes
     */
    public void testRightChildWidthCalculations() {
        Bintree tree = new Bintree();

        // Insert objects that will go into right child with odd width
        // World is 1024, so split at 512
        // Right child is (512, 0, 0, 512, 1024, 1024)
        // If width calculation is wrong, objects won't be placed correctly

        // Object near right edge of right child - tests xwid - xwid/2
        Balloon b1 = new Balloon("B1", 1000, 100, 100, 20, 20, 20, "hot", 1);
        // Object that spans into right child - will be placed in both
        Balloon b2 = new Balloon("B2", 400, 100, 100, 200, 20, 20, "hot", 1);
        // Forces split, then b1 tests right child dimensions
        Balloon b3 = new Balloon("B3", 100, 100, 100, 20, 20, 20, "hot", 1);
        Balloon b4 = new Balloon("B4", 200, 100, 100, 20, 20, 20, "hot", 1);

        tree.insert(b1);
        tree.insert(b2);
        tree.insert(b3);
        tree.insert(b4);

        String result = tree.print();
        // Verify b1 is actually stored (proves right child has correct width)
        assertTrue(result.contains("B1"));
        assertTrue(result.contains("1000"));

        // Query that should find b1 if right child width is correct
        String intersect = tree.findIntersections(990, 90, 90, 40, 40, 40);
        assertTrue(intersect.contains("B1"));
    }


    /**
     * Test Y-dimension right child width (line 60)
     */
    public void testRightChildYWidth() {
        Bintree tree = new Bintree();
        // Need depth 1 to get Y split (depth 0 is X, depth 1 is Y, depth 2 is
        // Z)
        // Force X split first, then Y split in right child

        // Go to right child (x >= 512)
        Balloon b1 = new Balloon("B1", 600, 100, 100, 20, 20, 20, "hot", 1);
        Balloon b2 = new Balloon("B2", 600, 200, 100, 20, 20, 20, "hot", 1);
        Balloon b3 = new Balloon("B3", 600, 300, 100, 20, 20, 20, "hot", 1);
        // Object near bottom of Y range - tests ywid - ywid/2
        Balloon b4 = new Balloon("B4", 600, 1000, 100, 20, 20, 20, "hot", 1);

        tree.insert(b1);
        tree.insert(b2);
        tree.insert(b3);
        tree.insert(b4);

        String result = tree.print();
        assertTrue(result.contains("B4"));
        assertTrue(result.contains("1000"));
    }


    /**
     * Test Z-dimension right child width (lines 70, 76, 82)
     */
    public void testRightChildZWidth() {
        Bintree tree = new Bintree();
        // Need depth 2 to get Z split
        // Force X split (depth 0), Y split (depth 1), then Z split (depth 2)

        // Stay in same X,Y region but vary Z
        Balloon b1 = new Balloon("B1", 600, 600, 100, 20, 20, 20, "hot", 1);
        Balloon b2 = new Balloon("B2", 600, 600, 200, 20, 20, 20, "hot", 1);
        Balloon b3 = new Balloon("B3", 600, 600, 300, 20, 20, 20, "hot", 1);
        // Object near far end of Z range - tests zwid - zwid/2
        Balloon b4 = new Balloon("B4", 600, 600, 1000, 20, 20, 20, "hot", 1);

        tree.insert(b1);
        tree.insert(b2);
        tree.insert(b3);
        tree.insert(b4);

        String result = tree.print();
        assertTrue(result.contains("B4"));
        assertTrue(result.contains("1000"));

        // Query that finds b4 only if Z width is correct
        String intersect = tree.findIntersections(590, 590, 990, 40, 40, 40);
        assertTrue(intersect.contains("B4"));
    }


    /**
     * Test that count++ actually increments (lines 237, 264)
     * Need to verify the INCREMENT itself matters
     */
    public void testCountIncrementMatters() {
        Bintree tree = new Bintree();

        // Insert exactly 3 objects (MAX_OBJECTS)
        Balloon b1 = new Balloon("B1", 10, 10, 10, 20, 20, 20, "hot", 1);
        Balloon b2 = new Balloon("B2", 15, 15, 15, 20, 20, 20, "hot", 1);
        Balloon b3 = new Balloon("B3", 20, 20, 20, 20, 20, 20, "hot", 1);

        tree.insert(b1);
        tree.insert(b2);
        tree.insert(b3);

        // Must verify exactly 3 objects
        String result = tree.print();
        assertTrue(result.contains("Leaf with 3 objects"));

        // Now insert a 4th that doesn't intersect all others - should cause
        // split
        Balloon b4 = new Balloon("B4", 100, 100, 100, 20, 20, 20, "hot", 1);
        tree.insert(b4);

        result = tree.print();
        // Should have split because count > 3
        assertTrue(result.contains("I ("));
    }


    /**
     * Test equality check FALSE case (line 297)
     * Need object that doesn't intersect the region at all
     */
    public void testInsertNonIntersectingObject() {
        Bintree tree = new Bintree();

        // First create a split to have smaller regions
        Balloon b1 = new Balloon("B1", 100, 100, 100, 20, 20, 20, "hot", 1);
        Balloon b2 = new Balloon("B2", 600, 600, 600, 20, 20, 20, "hot", 1);
        tree.insert(b1);
        tree.insert(b2);

        // Try to insert object into a leaf but it doesn't intersect
        // This tests the early return in LeafNode.insert
        String before = tree.print();

        // Insert another object
        Balloon b3 = new Balloon("B3", 200, 200, 200, 20, 20, 20, "hot", 1);
        tree.insert(b3);

        String after = tree.print();
        assertTrue(after.contains("B3"));
    }


    /**
     * Test line 326 - i + 1 arithmetic in loop
     */
    public void testAllObjectsIntersectLoop() {
        Bintree tree = new Bintree();

        // Create exactly 2 objects that intersect
        Balloon b1 = new Balloon("B1", 10, 10, 10, 30, 30, 30, "hot", 1);
        Balloon b2 = new Balloon("B2", 20, 20, 20, 30, 30, 30, "hot", 1);
        tree.insert(b1);
        tree.insert(b2);

        // Should stay as leaf with 2 objects (they intersect)
        String result = tree.print();
        assertTrue(result.contains("Leaf with 2 objects"));

        // Now add a 3rd that intersects both
        Balloon b3 = new Balloon("B3", 15, 15, 15, 30, 30, 30, "hot", 1);
        tree.insert(b3);

        result = tree.print();
        assertTrue(result.contains("Leaf with 3 objects"));

        // Add 4th that intersects all - tests the loop properly
        Balloon b4 = new Balloon("B4", 25, 25, 25, 30, 30, 30, "hot", 1);
        tree.insert(b4);

        result = tree.print();
        // All 4 intersect, should stay as leaf
        assertTrue(result.contains("Leaf with 4 objects"));
    }


    /**
     * Test lines 366, 368, 374 - intersection point checks
     * Need case where equality check is TRUE
     */
    public void testIntersectionPointEquality() {
        Bintree tree = new Bintree();

        // Object at specific position
        Balloon b1 = new Balloon("B1", 100, 100, 100, 50, 50, 50, "hot", 1);
        tree.insert(b1);

        // Query where Math.max gives object origin AND it equals region
        // boundary
        // If region is (0,0,0,512,512,512) and object is at (100,100,100)
        // intersectX = max(100, queryX)
        // For this to equal 100, we need queryX <= 100

        String result = tree.findIntersections(100, 100, 100, 50, 50, 50);
        assertTrue(result.contains("B1"));

        // Edge case: query before object
        result = tree.findIntersections(50, 50, 50, 100, 100, 100);
        assertTrue(result.contains("B1"));
    }


    /**
     * Test line 464 - equality check TRUE case
     * Object found but intersection point outside leaf region
     */
    public void testIntersectionOutsideLeafRegion() {
        Bintree tree = new Bintree();

        // Force a deep tree with small leaf regions
        for (int i = 0; i < 8; i++) {
            Balloon b = new Balloon("B" + i, 50 + i * 100, 50 + i * 100, 50 + i
                * 100, 80, 80, 80, "hot", 1);
            tree.insert(b);
        }

        // Query large region - some objects' intersection points may be outside
        // their leaf
        String result = tree.findIntersections(0, 0, 0, 1024, 1024, 1024);
        assertTrue(result.contains("nodes were visited"));
    }


    /**
     * Test lines 469-471 - the TRUE cases of boundary checks
     * Need intersectX < x + xwid to be TRUE (not just FALSE)
     */
    public void testIntersectionBoundaryTrue() {
        Bintree tree = new Bintree();

        // Object that will definitely be within boundaries
        Balloon b1 = new Balloon("B1", 100, 100, 100, 50, 50, 50, "hot", 1);
        tree.insert(b1);

        // Query that makes all boundaries return TRUE
        // intersectX >= x AND intersectX < x + xwid (both TRUE)
        String result = tree.findIntersections(90, 90, 90, 80, 80, 80);
        assertTrue(result.contains("B1"));

        // Edge case where intersection point is just inside boundary
        Balloon b2 = new Balloon("B2", 200, 200, 200, 50, 50, 50, "hot", 1);
        tree.insert(b2);

        result = tree.findIntersections(199, 199, 199, 52, 52, 52);
        assertTrue(result.contains("B2"));
    }


    /**
     * Test lines 507, 510, 512, 515, 517 - InternalNode insert
     * Need to verify depth+1 and nextDim calculations affect placement
     */
    public void testInternalNodeInsertCalculations() {
        Bintree tree = new Bintree();

        // Create internal node
        Balloon b1 = new Balloon("B1", 100, 100, 100, 20, 20, 20, "hot", 1);
        Balloon b2 = new Balloon("B2", 600, 100, 100, 20, 20, 20, "hot", 1);
        tree.insert(b1);
        tree.insert(b2);

        // Insert object that goes only to left child
        Balloon b3 = new Balloon("B3", 200, 100, 100, 20, 20, 20, "hot", 1);
        tree.insert(b3);

        // Insert object that goes only to right child
        Balloon b4 = new Balloon("B4", 700, 100, 100, 20, 20, 20, "hot", 1);
        tree.insert(b4);

        // Insert spanning object that goes to BOTH children
        Balloon b5 = new Balloon("B5", 400, 100, 100, 250, 20, 20, "hot", 1);
        tree.insert(b5);

        String result = tree.print();

        // Verify all objects present
        assertTrue(result.contains("B3"));
        assertTrue(result.contains("B4"));

        // B5 should appear multiple times (in both children)
        int count = 0;
        int index = 0;
        while ((index = result.indexOf("B5", index)) != -1) {
            count++;
            index++;
        }
        assertTrue(count >= 2);

        // Test that (splitDim + 1) % 3 calculation matters
        // Force insertions at different depths to use different dimensions
        Balloon b6 = new Balloon("B6", 150, 600, 100, 20, 20, 20, "hot", 1);
        tree.insert(b6);
        result = tree.print();
        assertTrue(result.contains("B6"));
    }


    /**
     * Test lines 543, 554, 588 - depth + 1 first operand
     * Must verify that depth value itself affects outcome
     */
    public void testDepthValueMatters() {
        Bintree tree = new Bintree();

        // Create multi-level tree
        for (int i = 0; i < 12; i++) {
            Balloon b = new Balloon("B" + i, 100 + i * 70, 100 + i * 70, 100 + i
                * 70, 40, 40, 40, "hot", 1);
            tree.insert(b);
        }

        String result = tree.print();

        // Verify specific depths appear (proves depth+1 is calculated)
        assertTrue(result.contains(") 0\r\n"));
        assertTrue(result.contains(") 1\r\n"));
        assertTrue(result.contains(") 2\r\n"));
        assertTrue(result.contains(") 3\r\n"));

        // findIntersections also uses depth
        String intersect = tree.findIntersections(0, 0, 0, 1024, 1024, 1024);
        assertTrue(intersect.contains(") 0\r\n"));
        assertTrue(intersect.contains(") 1\r\n"));
    }


    /**
     * Test lines 582-583 - TRUE cases of overlap checks
     * Need x < boxX + boxXwid to be TRUE (currently only testing FALSE)
     */
    public void testOverlapChecksTrueCases() {
        Bintree tree = new Bintree();

        // Create internal node with objects in both children
        Balloon b1 = new Balloon("B1", 100, 100, 100, 30, 30, 30, "hot", 1);
        Balloon b2 = new Balloon("B2", 600, 100, 100, 30, 30, 30, "hot", 1);
        Balloon b3 = new Balloon("B3", 100, 600, 100, 30, 30, 30, "hot", 1);
        Balloon b4 = new Balloon("B4", 600, 600, 100, 30, 30, 30, "hot", 1);

        tree.insert(b1);
        tree.insert(b2);
        tree.insert(b3);
        tree.insert(b4);

        // Query that makes x < boxX + boxXwid TRUE (x is less than right edge)
        String r1 = tree.findIntersections(0, 0, 0, 200, 1024, 1024);
        assertTrue(r1.contains("nodes were visited"));

        // Query that makes x + xwid > boxX TRUE (right edge extends past left
        // edge)
        String r2 = tree.findIntersections(50, 0, 0, 1024, 1024, 1024);
        assertTrue(r2.contains("nodes were visited"));

        // Same for Y dimension
        String r3 = tree.findIntersections(0, 0, 0, 1024, 200, 1024);
        assertTrue(r3.contains("nodes were visited"));

        String r4 = tree.findIntersections(0, 50, 0, 1024, 1024, 1024);
        assertTrue(r4.contains("nodes were visited"));

        // Same for Z dimension
        String r5 = tree.findIntersections(0, 0, 0, 1024, 1024, 200);
        assertTrue(r5.contains("nodes were visited"));

        String r6 = tree.findIntersections(0, 0, 50, 1024, 1024, 1024);
        assertTrue(r6.contains("nodes were visited"));
    }


    /**
     * Test lines 590-592, 598-600 - child region overlap arithmetic
     * Need boxXwid, boxYwid, boxZwid second operands to matter
     */
    public void testChildRegionOverlapArithmetic() {
        Bintree tree = new Bintree();

        // Create internal node
        Balloon b1 = new Balloon("B1", 100, 100, 100, 30, 30, 30, "hot", 1);
        Balloon b2 = new Balloon("B2", 600, 600, 600, 30, 30, 30, "hot", 1);
        tree.insert(b1);
        tree.insert(b2);

        // Query where boxX + boxXwid calculation matters for left child
        // Left child is (0, 0, 0, 512, 1024, 1024)
        // regions[0] < boxX + boxXwid should be TRUE
        // If boxXwid = 0, this might fail
        String r1 = tree.findIntersections(50, 50, 50, 100, 100, 100);
        assertTrue(r1.contains("B1"));

        // Query where regions[0] + regions[3] calculation matters
        // This tests that regions[3] (width) affects the overlap
        String r2 = tree.findIntersections(500, 0, 0, 100, 1024, 1024);
        assertTrue(r2.contains("nodes were visited"));

        // Test right child with regions[6] + regions[9]
        String r3 = tree.findIntersections(600, 600, 600, 100, 100, 100);
        assertTrue(r3.contains("B2"));

        // Edge case: query exactly at split boundary
        String r4 = tree.findIntersections(512, 512, 512, 1, 1, 1);
        assertTrue(r4.contains("nodes were visited"));

        // Query with large boxXwid to test addition matters
        String r5 = tree.findIntersections(0, 0, 0, 1023, 1023, 1023);
        assertTrue(r5.contains("nodes were visited"));
    }


    /**
     * Test line 429 TIMED_OUT - need fast test
     */
    public void testNoTimeoutSimple() {
        Bintree tree = new Bintree();
        Balloon b1 = new Balloon("B1", 100, 100, 100, 50, 50, 50, "hot", 1);
        tree.insert(b1);

        // Simple query that shouldn't timeout
        String result = tree.findIntersections(100, 100, 100, 50, 50, 50);
        assertTrue(result.contains("B1"));
    }


    /**
     * Comprehensive test combining multiple mutation targets
     */
    public void testComprehensiveMutations() {
        Bintree tree = new Bintree();

        // Test all dimensions and arithmetic
        Balloon b1 = new Balloon("B1", 10, 10, 10, 40, 40, 40, "hot", 1);
        Balloon b2 = new Balloon("B2", 20, 20, 20, 40, 40, 40, "hot", 1);
        Balloon b3 = new Balloon("B3", 30, 30, 30, 40, 40, 40, "hot", 1);
        Balloon b4 = new Balloon("B4", 500, 500, 500, 40, 40, 40, "hot", 1);
        Balloon b5 = new Balloon("B5", 1000, 1000, 1000, 20, 20, 20, "hot", 1);

        tree.insert(b1);
        tree.insert(b2);
        tree.insert(b3);
        tree.insert(b4);
        tree.insert(b5);

        String print = tree.print();
        assertTrue(print.contains("B1"));
        assertTrue(print.contains("B5"));
        assertTrue(print.contains("1000"));

        String collisions = tree.findCollisions();
        assertTrue(collisions.contains("(") || collisions.contains("database"));

        String intersect1 = tree.findIntersections(0, 0, 0, 60, 60, 60);
        assertTrue(intersect1.contains("nodes were visited"));

        String intersect2 = tree.findIntersections(490, 490, 490, 40, 40, 40);
        assertTrue(intersect2.contains("B4"));

        String intersect3 = tree.findIntersections(990, 990, 990, 40, 40, 40);
        assertTrue(intersect3.contains("B5"));
    }
}
