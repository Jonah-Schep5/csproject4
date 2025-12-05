/**
 * 3D Bintree data structure for storing AirObjects.
 * Uses Composite pattern with FlyWeight for empty nodes.
 *
 * @author CS3114/5040 Staff
 * @version Fall 2025
 */
public class Bintree {
    /**
     * The root node of the bintree
     */
    private BintreeNode root;

    /**
     * The size of the world in each dimension
     */
    private static final int WORLD_SIZE = 1024;

    /**
     * Maximum number of objects in a leaf before splitting
     */
    private static final int MAX_OBJECTS = 3;

    /**
     * Calculate child regions based on split dimension
     * 
     * @param x
     *            Region x coordinate
     * @param y
     *            Region y coordinate
     * @param z
     *            Region z coordinate
     * @param xwid
     *            Region x width
     * @param ywid
     *            Region y width
     * @param zwid
     *            Region z width
     * @param splitDim
     *            Split dimension (0=x, 1=y, 2=z)
     * @param result
     *            Array to store results: [leftX, leftY, leftZ, leftXwid,
     *            leftYwid, leftZwid,
     *            rightX, rightY, rightZ, rightXwid, rightYwid, rightZwid]
     */
    private static void calculateChildRegions(
        int x,
        int y,
        int z,
        int xwid,
        int ywid,
        int zwid,
        int splitDim,
        int[] result) {
        if (splitDim == 0) {
            int splitPoint = x + xwid / 2;
            result[0] = x;
            result[1] = y;
            result[2] = z;
            result[3] = xwid / 2;
            result[4] = ywid;
            result[5] = zwid;
            result[6] = splitPoint;
            result[7] = y;
            result[8] = z;
            result[9] = xwid - xwid / 2;
            result[10] = ywid;
            result[11] = zwid;
        }
        else if (splitDim == 1) {
            int splitPoint = y + ywid / 2;
            result[0] = x;
            result[1] = y;
            result[2] = z;
            result[3] = xwid;
            result[4] = ywid / 2;
            result[5] = zwid;
            result[6] = x;
            result[7] = splitPoint;
            result[8] = z;
            result[9] = xwid;
            result[10] = ywid - ywid / 2;
            result[11] = zwid;
        }
        else {
            int splitPoint = z + zwid / 2;
            result[0] = x;
            result[1] = y;
            result[2] = z;
            result[3] = xwid;
            result[4] = ywid;
            result[5] = zwid / 2;
            result[6] = x;
            result[7] = y;
            result[8] = splitPoint;
            result[9] = xwid;
            result[10] = ywid;
            result[11] = zwid - zwid / 2;
        }
    }

    /**
     * Interface for bintree nodes (Composite pattern)
     */
    private interface BintreeNode {
        /**
         * Insert an object into the tree
         *
         * @param obj
         *            The object to insert
         * @param x
         *            The x-coordinate of the region
         * @param y
         *            The y-coordinate of the region
         * @param z
         *            The z-coordinate of the region
         * @param xwid
         *            The x width of the region
         * @param ywid
         *            The y width of the region
         * @param zwid
         *            The z width of the region
         * @param depth
         *            The current depth
         * @param splitDim
         *            The dimension to split on (0=x, 1=y, 2=z)
         * @return The new node (may be different if split occurred)
         */
        BintreeNode insert(
            AirObject obj,
            int x,
            int y,
            int z,
            int xwid,
            int ywid,
            int zwid,
            int depth,
            int splitDim);


        /**
         * Print the tree in preorder
         *
         * @param x
         *            The x-coordinate of the region
         * @param y
         *            The y-coordinate of the region
         * @param z
         *            The z-coordinate of the region
         * @param xwid
         *            The x width of the region
         * @param ywid
         *            The y width of the region
         * @param zwid
         *            The z width of the region
         * @param depth
         *            The current depth
         * @param result
         *            StringBuilder to append results to
         * @param nodeCount
         *            Array to track node count
         */
        void print(
            int x,
            int y,
            int z,
            int xwid,
            int ywid,
            int zwid,
            int depth,
            StringBuilder result,
            int[] nodeCount);


        /**
         * Find all collisions in the tree
         *
         * @param x
         *            The x-coordinate of the region
         * @param y
         *            The y-coordinate of the region
         * @param z
         *            The z-coordinate of the region
         * @param xwid
         *            The x width of the region
         * @param ywid
         *            The y width of the region
         * @param zwid
         *            The z width of the region
         * @param depth
         *            The current depth
         * @param result
         *            StringBuilder to append results to
         */
        void findCollisions(
            int x,
            int y,
            int z,
            int xwid,
            int ywid,
            int zwid,
            int depth,
            StringBuilder result);


        /**
         * Find all objects intersecting a given box
         *
         * @param x
         *            The x-coordinate of the region
         * @param y
         *            The y-coordinate of the region
         * @param z
         *            The z-coordinate of the region
         * @param xwid
         *            The x width of the region
         * @param ywid
         *            The y width of the region
         * @param zwid
         *            The z width of the region
         * @param boxX
         *            The x-coordinate of the query box
         * @param boxY
         *            The y-coordinate of the query box
         * @param boxZ
         *            The z-coordinate of the query box
         * @param boxXwid
         *            The x width of the query box
         * @param boxYwid
         *            The y width of the query box
         * @param boxZwid
         *            The z width of the query box
         * @param depth
         *            The current depth
         * @param result
         *            StringBuilder to append results to
         * @param nodeCount
         *            Array to track visited node count
         */
        void findIntersections(
            int x,
            int y,
            int z,
            int xwid,
            int ywid,
            int zwid,
            int boxX,
            int boxY,
            int boxZ,
            int boxXwid,
            int boxYwid,
            int boxZwid,
            int depth,
            StringBuilder result,
            int[] nodeCount);
    }


    /**
     * Empty leaf node (FlyWeight pattern - single instance)
     */
    private static class EmptyLeafNode implements BintreeNode {
        /**
         * Single instance of empty leaf node
         */
        private static final EmptyLeafNode INSTANCE = new EmptyLeafNode();

        /**
         * Private constructor for FlyWeight
         */
        private EmptyLeafNode() {
        }


        /**
         * Get the instance
         *
         * @return The empty leaf node instance
         */
        public static EmptyLeafNode getInstance() {
            return INSTANCE;
        }


        @Override
        public BintreeNode insert(
            AirObject obj,
            int x,
            int y,
            int z,
            int xwid,
            int ywid,
            int zwid,
            int depth,
            int splitDim) {
            // Create a new leaf node with the object
            return new LeafNode(obj);
        }


        @Override
        public void print(
            int x,
            int y,
            int z,
            int xwid,
            int ywid,
            int zwid,
            int depth,
            StringBuilder result,
            int[] nodeCount) {
            nodeCount[0]++;
            result.append("E (");
            result.append(x);
            result.append(", ");
            result.append(y);
            result.append(", ");
            result.append(z);
            result.append(", ");
            result.append(xwid);
            result.append(", ");
            result.append(ywid);
            result.append(", ");
            result.append(zwid);
            result.append(") ");
            result.append(depth);
            result.append("\r\n");
        }


        @Override
        public void findCollisions(
            int x,
            int y,
            int z,
            int xwid,
            int ywid,
            int zwid,
            int depth,
            StringBuilder result) {
        }


        @Override
        public void findIntersections(
            int x,
            int y,
            int z,
            int xwid,
            int ywid,
            int zwid,
            int boxX,
            int boxY,
            int boxZ,
            int boxXwid,
            int boxYwid,
            int boxZwid,
            int depth,
            StringBuilder result,
            int[] nodeCount) {
            nodeCount[0]++;
        }
    }


    private static class LeafNode implements BintreeNode {
        private AirObject[] objects;
        private int count;

        public LeafNode(AirObject obj) {
            this.objects = new AirObject[MAX_OBJECTS + 1]; // Start with space
                                                           // for 4
            this.objects[0] = obj;
            this.count = 1;
        }


        @Override
        public BintreeNode insert(
            AirObject obj,
            int x,
            int y,
            int z,
            int xwid,
            int ywid,
            int zwid,
            int depth,
            int splitDim) {

            // Check for duplicate by name
            for (int i = 0; i < count; i++) {
                if (objects[i].getName().equals(obj.getName())) {
                    return this; // Duplicate found, don't insert
                }
            }

            // Expand array if needed (when all objects intersect)
            if (count >= objects.length) {
                AirObject[] newArray = new AirObject[objects.length * 2];
                for (int i = 0; i < count; i++) {
                    newArray[i] = objects[i];
                }
                objects = newArray;
            }

            // Add the object
            objects[count] = obj;
            count++;

            // Check if we need to split
            if (count > MAX_OBJECTS) {
                // Only split if not all objects intersect with each other
                if (!allObjectsIntersect()) {
                    return split(x, y, z, xwid, ywid, zwid, depth, splitDim);
                }
                // If all objects intersect, stay as leaf (array already
                // expanded above)
            }

            return this;
        }


        private boolean allObjectsIntersect() {
            for (int i = 0; i < count; i++) {
                for (int j = i + 1; j < count; j++) {
                    if (!objects[i].intersects(objects[j])) {
                        return false;
                    }
                }
            }
            return true;
        }


        private BintreeNode split(
            int x,
            int y,
            int z,
            int xwid,
            int ywid,
            int zwid,
            int depth,
            int splitDim) {
            InternalNode internal = new InternalNode();
            int[] regions = new int[12];
            calculateChildRegions(x, y, z, xwid, ywid, zwid, splitDim, regions);
            int nextDim = (splitDim + 1) % 3;

            // Reinsert all objects into the new internal node
            for (int i = 0; i < count; i++) {
                AirObject obj = objects[i];

                // Check left child
                if (obj.intersects(regions[0], regions[1], regions[2],
                    regions[3], regions[4], regions[5])) {
                    internal.left = internal.left.insert(obj, regions[0],
                        regions[1], regions[2], regions[3], regions[4],
                        regions[5], depth + 1, nextDim);
                }

                // Check right child
                if (obj.intersects(regions[6], regions[7], regions[8],
                    regions[9], regions[10], regions[11])) {
                    internal.right = internal.right.insert(obj, regions[6],
                        regions[7], regions[8], regions[9], regions[10],
                        regions[11], depth + 1, nextDim);
                }
            }

            return internal;
        }


        @Override
        public void print(
            int x,
            int y,
            int z,
            int xwid,
            int ywid,
            int zwid,
            int depth,
            StringBuilder result,
            int[] nodeCount) {
            nodeCount[0]++;
            result.append("Leaf with ");
            result.append(count);
            result.append(" objects (");
            result.append(x);
            result.append(", ");
            result.append(y);
            result.append(", ");
            result.append(z);
            result.append(", ");
            result.append(xwid);
            result.append(", ");
            result.append(ywid);
            result.append(", ");
            result.append(zwid);
            result.append(") ");
            result.append(depth);
            result.append("\r\n");
            for (int i = 0; i < count; i++) {
                result.append("    (");
                result.append(objects[i].toString());
                result.append(")\r\n");
            }
        }


        @Override
        public void findCollisions(
            int x,
            int y,
            int z,
            int xwid,
            int ywid,
            int zwid,
            int depth,
            StringBuilder result) {
            result.append("In leaf node (");
            result.append(x);
            result.append(", ");
            result.append(y);
            result.append(", ");
            result.append(z);
            result.append(", ");
            result.append(xwid);
            result.append(", ");
            result.append(ywid);
            result.append(", ");
            result.append(zwid);
            result.append(") ");
            result.append(depth);
            result.append("\r\n");

            for (int i = 0; i < count; i++) {
                for (int j = i + 1; j < count; j++) {
                    if (objects[i].intersects(objects[j])) {
                        result.append("(");
                        result.append(objects[i].toString());
                        result.append(") and (");
                        result.append(objects[j].toString());
                        result.append(")\r\n");
                    }
                }
            }
        }


        @Override
        public void findIntersections(
            int x,
            int y,
            int z,
            int xwid,
            int ywid,
            int zwid,
            int boxX,
            int boxY,
            int boxZ,
            int boxXwid,
            int boxYwid,
            int boxZwid,
            int depth,
            StringBuilder result,
            int[] nodeCount) {
            nodeCount[0]++;
            result.append("In leaf node (");
            result.append(x);
            result.append(", ");
            result.append(y);
            result.append(", ");
            result.append(z);
            result.append(", ");
            result.append(xwid);
            result.append(", ");
            result.append(ywid);
            result.append(", ");
            result.append(zwid);
            result.append(") ");
            result.append(depth);
            result.append("\r\n");

            for (int i = 0; i < count; i++) {
                if (objects[i].intersects(boxX, boxY, boxZ, boxXwid, boxYwid,
                    boxZwid)) {
                    // Calculate intersection box origin
                    int intersectX = Math.max(objects[i].getXorig(), boxX);
                    int intersectY = Math.max(objects[i].getYorig(), boxY);
                    int intersectZ = Math.max(objects[i].getZorig(), boxZ);

                    // Only report if intersection origin is in this node's
                    // region
                    if (intersectX >= x && intersectX < x + xwid
                        && intersectY >= y && intersectY < y + ywid
                        && intersectZ >= z && intersectZ < z + zwid) {
                        result.append(objects[i].toString());
                        result.append("\r\n");
                    }
                }
            }
        }
    }


    /**
     * Internal node with left and right children
     */
    private static class InternalNode implements BintreeNode {
        /**
         * Left child node
         */
        private BintreeNode left;

        /**
         * Right child node
         */
        private BintreeNode right;

        /**
         * Constructor for internal node
         */
        public InternalNode() {
            this.left = EmptyLeafNode.getInstance();
            this.right = EmptyLeafNode.getInstance();
        }


        @Override
        public BintreeNode insert(
            AirObject obj,
            int x,
            int y,
            int z,
            int xwid,
            int ywid,
            int zwid,
            int depth,
            int splitDim) {
            int[] regions = new int[12];
            calculateChildRegions(x, y, z, xwid, ywid, zwid, splitDim, regions);
            int nextDim = (splitDim + 1) % 3;

            if (obj.intersects(regions[0], regions[1], regions[2], regions[3],
                regions[4], regions[5])) {
                left = left.insert(obj, regions[0], regions[1], regions[2],
                    regions[3], regions[4], regions[5], depth + 1, nextDim);
            }
            if (obj.intersects(regions[6], regions[7], regions[8], regions[9],
                regions[10], regions[11])) {
                right = right.insert(obj, regions[6], regions[7], regions[8],
                    regions[9], regions[10], regions[11], depth + 1, nextDim);
            }
            return this;
        }


        @Override
        public void print(
            int x,
            int y,
            int z,
            int xwid,
            int ywid,
            int zwid,
            int depth,
            StringBuilder result,
            int[] nodeCount) {
            nodeCount[0]++;
            result.append("I (");
            result.append(x);
            result.append(", ");
            result.append(y);
            result.append(", ");
            result.append(z);
            result.append(", ");
            result.append(xwid);
            result.append(", ");
            result.append(ywid);
            result.append(", ");
            result.append(zwid);
            result.append(") ");
            result.append(depth);
            result.append("\r\n");

            int[] regions = new int[12];
            calculateChildRegions(x, y, z, xwid, ywid, zwid, depth % 3,
                regions);
            left.print(regions[0], regions[1], regions[2], regions[3],
                regions[4], regions[5], depth + 1, result, nodeCount);
            right.print(regions[6], regions[7], regions[8], regions[9],
                regions[10], regions[11], depth + 1, result, nodeCount);
        }


        @Override
        public void findCollisions(
            int x,
            int y,
            int z,
            int xwid,
            int ywid,
            int zwid,
            int depth,
            StringBuilder result) {
            int[] regions = new int[12];
            calculateChildRegions(x, y, z, xwid, ywid, zwid, depth % 3,
                regions);
            left.findCollisions(regions[0], regions[1], regions[2], regions[3],
                regions[4], regions[5], depth + 1, result);
            right.findCollisions(regions[6], regions[7], regions[8], regions[9],
                regions[10], regions[11], depth + 1, result);
        }


        @Override
        public void findIntersections(
            int x,
            int y,
            int z,
            int xwid,
            int ywid,
            int zwid,
            int boxX,
            int boxY,
            int boxZ,
            int boxXwid,
            int boxYwid,
            int boxZwid,
            int depth,
            StringBuilder result,
            int[] nodeCount) {
            nodeCount[0]++;
            result.append("In Internal node (");
            result.append(x);
            result.append(", ");
            result.append(y);
            result.append(", ");
            result.append(z);
            result.append(", ");
            result.append(xwid);
            result.append(", ");
            result.append(ywid);
            result.append(", ");
            result.append(zwid);
            result.append(") ");
            result.append(depth);
            result.append("\r\n");

            if (!((x < boxX + boxXwid) && (x + xwid > boxX) && (y < boxY
                + boxYwid) && (y + ywid > boxY) && (z < boxZ + boxZwid) && (z
                    + zwid > boxZ))) {
                return;
            }

            int[] regions = new int[12];
            calculateChildRegions(x, y, z, xwid, ywid, zwid, depth % 3,
                regions);

            if ((regions[0] < boxX + boxXwid) && (regions[0]
                + regions[3] > boxX) && (regions[1] < boxY + boxYwid)
                && (regions[1] + regions[4] > boxY) && (regions[2] < boxZ
                    + boxZwid) && (regions[2] + regions[5] > boxZ)) {
                left.findIntersections(regions[0], regions[1], regions[2],
                    regions[3], regions[4], regions[5], boxX, boxY, boxZ,
                    boxXwid, boxYwid, boxZwid, depth + 1, result, nodeCount);
            }

            if ((regions[6] < boxX + boxXwid) && (regions[6]
                + regions[9] > boxX) && (regions[7] < boxY + boxYwid)
                && (regions[7] + regions[10] > boxY) && (regions[8] < boxZ
                    + boxZwid) && (regions[8] + regions[11] > boxZ)) {
                right.findIntersections(regions[6], regions[7], regions[8],
                    regions[9], regions[10], regions[11], boxX, boxY, boxZ,
                    boxXwid, boxYwid, boxZwid, depth + 1, result, nodeCount);
            }
        }
    }

    /**
     * Constructor for Bintree
     */
    public Bintree() {
        this.root = EmptyLeafNode.getInstance();
    }


    /**
     * Insert an object into the bintree
     *
     * @param obj
     *            The object to insert
     * @return True if inserted successfully
     */
    public boolean insert(AirObject obj) {
        if (obj == null) {
            return false;
        }
        root = root.insert(obj, 0, 0, 0, WORLD_SIZE, WORLD_SIZE, WORLD_SIZE, 0,
            0);
        return true;
    }


    /**
     * Print the bintree in preorder
     *
     * @return String representation
     */
    public String print() {
        StringBuilder result = new StringBuilder();
        int[] nodeCount = new int[1];
        root.print(0, 0, 0, WORLD_SIZE, WORLD_SIZE, WORLD_SIZE, 0, result,
            nodeCount);
        result.append(nodeCount[0]);
        result.append(" Bintree nodes printed");
        return result.toString();
    }


    /**
     * Find all collisions in the bintree
     *
     * @return String listing collisions
     */
    public String findCollisions() {
        StringBuilder result = new StringBuilder();
        result.append("The following collisions exist in the database:\r\n");
        root.findCollisions(0, 0, 0, WORLD_SIZE, WORLD_SIZE, WORLD_SIZE, 0,
            result);
        return result.toString();
    }


    /**
     * Find all objects intersecting a given box
     *
     * @param boxX
     *            The x-coordinate of the query box
     * @param boxY
     *            The y-coordinate of the query box
     * @param boxZ
     *            The z-coordinate of the query box
     * @param boxXwid
     *            The x width of the query box
     * @param boxYwid
     *            The y width of the query box
     * @param boxZwid
     *            The z width of the query box
     * @return String listing intersecting objects
     */
    public String findIntersections(
        int boxX,
        int boxY,
        int boxZ,
        int boxXwid,
        int boxYwid,
        int boxZwid) {
        StringBuilder result = new StringBuilder();
        result.append("The following objects intersect (");
        result.append(boxX);
        result.append(" ");
        result.append(boxY);
        result.append(" ");
        result.append(boxZ);
        result.append(" ");
        result.append(boxXwid);
        result.append(" ");
        result.append(boxYwid);
        result.append(" ");
        result.append(boxZwid);
        result.append("):\r\n");
        int[] nodeCount = new int[1];
        root.findIntersections(0, 0, 0, WORLD_SIZE, WORLD_SIZE, WORLD_SIZE,
            boxX, boxY, boxZ, boxXwid, boxYwid, boxZwid, 0, result, nodeCount);
        result.append(nodeCount[0]);
        result.append(" nodes were visited in the bintree");
        return result.toString();
    }
}
