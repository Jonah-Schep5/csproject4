/**
 * 3D Bintree data structure for storing AirObjects.
 * Uses Composite pattern with FlyWeight for empty nodes.
 *
 * @author Jonah Schepers
 * @author Rowan Muhoberac
 * @version December 8, 2025
 */
public class Bintree {
    private BintreeNode root;
    private static final int WORLD_SIZE = 1024;
    private static final int MAX_OBJECTS = 3;

    private static void calcChildRegions(
        int x,
        int y,
        int z,
        int xw,
        int yw,
        int zw,
        int dim,
        int[] r) {
        if (dim == 0) {
            int sp = x + xw / 2;
            r[0] = x;
            r[1] = y;
            r[2] = z;
            r[3] = xw / 2;
            r[4] = yw;
            r[5] = zw;
            r[6] = sp;
            r[7] = y;
            r[8] = z;
            r[9] = xw - xw / 2;
            r[10] = yw;
            r[11] = zw;
        }
        else if (dim == 1) {
            int sp = y + yw / 2;
            r[0] = x;
            r[1] = y;
            r[2] = z;
            r[3] = xw;
            r[4] = yw / 2;
            r[5] = zw;
            r[6] = x;
            r[7] = sp;
            r[8] = z;
            r[9] = xw;
            r[10] = yw - yw / 2;
            r[11] = zw;
        }
        else {
            int sp = z + zw / 2;
            r[0] = x;
            r[1] = y;
            r[2] = z;
            r[3] = xw;
            r[4] = yw;
            r[5] = zw / 2;
            r[6] = x;
            r[7] = y;
            r[8] = sp;
            r[9] = xw;
            r[10] = yw;
            r[11] = zw - zw / 2;
        }
    }


    private static void indent(StringBuilder sb, int depth) {
        for (int i = 0; i < depth; i++)
            sb.append("  ");
    }


    private static void appendRegion(
        StringBuilder sb,
        String prefix,
        int x,
        int y,
        int z,
        int xw,
        int yw,
        int zw,
        int depth) {
        sb.append(prefix).append(" (").append(x).append(", ").append(y).append(
            ", ").append(z).append(", ").append(xw).append(", ").append(yw)
            .append(", ").append(zw).append(") ").append(depth).append("\r\n");
    }


    private static void sortByName(AirObject[] arr, int count) {
        for (int i = 1; i < count; i++) {
            AirObject key = arr[i];
            int j = i - 1;
            while (j >= 0 && arr[j].getName().compareTo(key.getName()) > 0) {
                arr[j + 1] = arr[j];
                j--;
            }
            arr[j + 1] = key;
        }
    }


    private static boolean boxesIntersect(
        int x1,
        int y1,
        int z1,
        int w1,
        int h1,
        int d1,
        int x2,
        int y2,
        int z2,
        int w2,
        int h2,
        int d2) {
        return x1 < x2 + w2 && x1 + w1 > x2 && y1 < y2 + h2 && y1 + h1 > y2
            && z1 < z2 + d2 && z1 + d1 > z2;
    }

    private static class EmptyLeafNode implements BintreeNode {
        private static final EmptyLeafNode INSTANCE = new EmptyLeafNode();

        private EmptyLeafNode() {
        }


        public static EmptyLeafNode getInstance() {
            return INSTANCE;
        }


        public BintreeNode insert(
            AirObject obj,
            int x,
            int y,
            int z,
            int xw,
            int yw,
            int zw,
            int depth,
            int dim) {
            return new LeafNode(obj);
        }


        public void print(
            int x,
            int y,
            int z,
            int xw,
            int yw,
            int zw,
            int depth,
            StringBuilder sb,
            int[] cnt) {
            cnt[0]++;
            indent(sb, depth);
            appendRegion(sb, "E", x, y, z, xw, yw, zw, depth);
        }


        public void findCollisions(
            int x,
            int y,
            int z,
            int xw,
            int yw,
            int zw,
            int depth,
            StringBuilder sb) {
        }


        public void findIntersections(
            int x,
            int y,
            int z,
            int xw,
            int yw,
            int zw,
            int bx,
            int by,
            int bz,
            int bxw,
            int byw,
            int bzw,
            int depth,
            StringBuilder sb,
            int[] cnt) {
            cnt[0]++;
        }
    }


    private static class LeafNode implements BintreeNode {
        private AirObject[] objects;
        private int count;

        public LeafNode(AirObject obj) {
            this.objects = new AirObject[MAX_OBJECTS + 1];
            this.objects[0] = obj;
            this.count = 1;
        }


        public BintreeNode insert(
            AirObject obj,
            int x,
            int y,
            int z,
            int xw,
            int yw,
            int zw,
            int depth,
            int dim) {
            for (int i = 0; i < count; i++) {
                if (objects[i].getName().equals(obj.getName()))
                    return this;
            }
            if (count >= objects.length) {
                AirObject[] newArr = new AirObject[objects.length * 2];
                for (int i = 0; i < count; i++)
                    newArr[i] = objects[i];
                objects = newArr;
            }
            objects[count++] = obj;
            if (count > MAX_OBJECTS && !allObjectsIntersect()) {
                return split(x, y, z, xw, yw, zw, depth, dim);
            }
            return this;
        }


        private boolean allObjectsIntersect() {
            for (int i = 0; i < count; i++) {
                for (int j = i + 1; j < count; j++) {
                    if (!objects[i].intersects(objects[j]))
                        return false;
                }
            }
            return true;
        }


        private BintreeNode split(
            int x,
            int y,
            int z,
            int xw,
            int yw,
            int zw,
            int depth,
            int dim) {
            InternalNode internal = new InternalNode();
            int[] r = new int[12];
            calcChildRegions(x, y, z, xw, yw, zw, dim, r);
            int nextDim = (dim + 1) % 3;
            for (int i = 0; i < count; i++) {
                AirObject obj = objects[i];
                if (obj.intersects(r[0], r[1], r[2], r[3], r[4], r[5]))
                    internal.left = internal.left.insert(obj, r[0], r[1], r[2],
                        r[3], r[4], r[5], depth + 1, nextDim);
                if (obj.intersects(r[6], r[7], r[8], r[9], r[10], r[11]))
                    internal.right = internal.right.insert(obj, r[6], r[7],
                        r[8], r[9], r[10], r[11], depth + 1, nextDim);
            }
            return internal;
        }


        public void print(
            int x,
            int y,
            int z,
            int xw,
            int yw,
            int zw,
            int depth,
            StringBuilder sb,
            int[] cnt) {
            cnt[0]++;
            indent(sb, depth);
            sb.append("Leaf with ").append(count).append(" objects (").append(x)
                .append(", ").append(y).append(", ").append(z).append(", ")
                .append(xw).append(", ").append(yw).append(", ").append(zw)
                .append(") ").append(depth).append("\r\n");
            AirObject[] sorted = new AirObject[count];
            for (int i = 0; i < count; i++)
                sorted[i] = objects[i];
            sortByName(sorted, count);
            for (int i = 0; i < count; i++) {
                indent(sb, depth);
                sb.append("(").append(sorted[i].toString()).append(")\r\n");
            }
        }


        public void findCollisions(
            int x,
            int y,
            int z,
            int xw,
            int yw,
            int zw,
            int depth,
            StringBuilder sb) {
            sb.append("In leaf node (").append(x).append(", ").append(y).append(
                ", ").append(z).append(", ").append(xw).append(", ").append(yw)
                .append(", ").append(zw).append(") ").append(depth).append(
                    "\r\n");
            AirObject[] sorted = new AirObject[count];
            for (int i = 0; i < count; i++)
                sorted[i] = objects[i];
            sortByName(sorted, count);
            for (int i = 0; i < count; i++) {
                for (int j = i + 1; j < count; j++) {
                    if (sorted[i].intersects(sorted[j])) {
                        int ix = Math.max(sorted[i].getXorig(), sorted[j]
                            .getXorig());
                        int iy = Math.max(sorted[i].getYorig(), sorted[j]
                            .getYorig());
                        int iz = Math.max(sorted[i].getZorig(), sorted[j]
                            .getZorig());
                        if (ix >= x && ix < x + xw && iy >= y && iy < y + yw
                            && iz >= z && iz < z + zw) {
                            sb.append("(").append(sorted[i].toString()).append(
                                ") and (").append(sorted[j].toString()).append(
                                    ")\r\n");
                        }
                    }
                }
            }
        }


        public void findIntersections(
            int x,
            int y,
            int z,
            int xw,
            int yw,
            int zw,
            int bx,
            int by,
            int bz,
            int bxw,
            int byw,
            int bzw,
            int depth,
            StringBuilder sb,
            int[] cnt) {
            cnt[0]++;
            sb.append("In leaf node (").append(x).append(", ").append(y).append(
                ", ").append(z).append(", ").append(xw).append(", ").append(yw)
                .append(", ").append(zw).append(") ").append(depth).append(
                    "\r\n");
            AirObject[] matching = new AirObject[count];
            int matchCount = 0;
            for (int i = 0; i < count; i++) {
                if (objects[i].intersects(bx, by, bz, bxw, byw, bzw)) {
                    int ix = Math.max(objects[i].getXorig(), bx);
                    int iy = Math.max(objects[i].getYorig(), by);
                    int iz = Math.max(objects[i].getZorig(), bz);
                    if (ix >= x && ix < x + xw && iy >= y && iy < y + yw
                        && iz >= z && iz < z + zw) {
                        matching[matchCount++] = objects[i];
                    }
                }
            }
            sortByName(matching, matchCount);
            for (int i = 0; i < matchCount; i++) {
                sb.append(matching[i].toString()).append("\r\n");
            }
        }
    }


    private static class InternalNode implements BintreeNode {
        private BintreeNode left;
        private BintreeNode right;

        public InternalNode() {
            this.left = EmptyLeafNode.getInstance();
            this.right = EmptyLeafNode.getInstance();
        }


        public BintreeNode insert(
            AirObject obj,
            int x,
            int y,
            int z,
            int xw,
            int yw,
            int zw,
            int depth,
            int dim) {
            int[] r = new int[12];
            calcChildRegions(x, y, z, xw, yw, zw, dim, r);
            int nextDim = (dim + 1) % 3;
            if (obj.intersects(r[0], r[1], r[2], r[3], r[4], r[5]))
                left = left.insert(obj, r[0], r[1], r[2], r[3], r[4], r[5],
                    depth + 1, nextDim);
            if (obj.intersects(r[6], r[7], r[8], r[9], r[10], r[11]))
                right = right.insert(obj, r[6], r[7], r[8], r[9], r[10], r[11],
                    depth + 1, nextDim);
            return this;
        }


        public void print(
            int x,
            int y,
            int z,
            int xw,
            int yw,
            int zw,
            int depth,
            StringBuilder sb,
            int[] cnt) {
            cnt[0]++;
            indent(sb, depth);
            appendRegion(sb, "I", x, y, z, xw, yw, zw, depth);
            int[] r = new int[12];
            calcChildRegions(x, y, z, xw, yw, zw, depth % 3, r);
            left.print(r[0], r[1], r[2], r[3], r[4], r[5], depth + 1, sb, cnt);
            right.print(r[6], r[7], r[8], r[9], r[10], r[11], depth + 1, sb,
                cnt);
        }


        public void findCollisions(
            int x,
            int y,
            int z,
            int xw,
            int yw,
            int zw,
            int depth,
            StringBuilder sb) {
            int[] r = new int[12];
            calcChildRegions(x, y, z, xw, yw, zw, depth % 3, r);
            left.findCollisions(r[0], r[1], r[2], r[3], r[4], r[5], depth + 1,
                sb);
            right.findCollisions(r[6], r[7], r[8], r[9], r[10], r[11], depth
                + 1, sb);
        }


        public void findIntersections(
            int x,
            int y,
            int z,
            int xw,
            int yw,
            int zw,
            int bx,
            int by,
            int bz,
            int bxw,
            int byw,
            int bzw,
            int depth,
            StringBuilder sb,
            int[] cnt) {
            cnt[0]++;
            sb.append("In Internal node (").append(x).append(", ").append(y)
                .append(", ").append(z).append(", ").append(xw).append(", ")
                .append(yw).append(", ").append(zw).append(") ").append(depth)
                .append("\r\n");
            if (!boxesIntersect(x, y, z, xw, yw, zw, bx, by, bz, bxw, byw, bzw))
                return;
            int[] r = new int[12];
            calcChildRegions(x, y, z, xw, yw, zw, depth % 3, r);
            if (boxesIntersect(r[0], r[1], r[2], r[3], r[4], r[5], bx, by, bz,
                bxw, byw, bzw))
                left.findIntersections(r[0], r[1], r[2], r[3], r[4], r[5], bx,
                    by, bz, bxw, byw, bzw, depth + 1, sb, cnt);
            if (boxesIntersect(r[6], r[7], r[8], r[9], r[10], r[11], bx, by, bz,
                bxw, byw, bzw))
                right.findIntersections(r[6], r[7], r[8], r[9], r[10], r[11],
                    bx, by, bz, bxw, byw, bzw, depth + 1, sb, cnt);
        }
    }

    /** Constructor for Bintree */
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
        if (obj == null)
            return false;
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
        StringBuilder sb = new StringBuilder();
        int[] cnt = new int[1];
        root.print(0, 0, 0, WORLD_SIZE, WORLD_SIZE, WORLD_SIZE, 0, sb, cnt);
        sb.append(cnt[0]).append(" Bintree nodes printed");
        return sb.toString();
    }


    /**
     * Find all collisions in the bintree
     * 
     * @return String listing collisions
     */
    public String findCollisions() {
        StringBuilder sb = new StringBuilder();
        sb.append("The following collisions exist in the database:\r\n");
        root.findCollisions(0, 0, 0, WORLD_SIZE, WORLD_SIZE, WORLD_SIZE, 0, sb);
        return sb.toString();
    }


    /**
     * Find all objects intersecting a given box
     * 
     * @param bx
     *            The x-coordinate of the query box
     * @param by
     *            The y-coordinate of the query box
     * @param bz
     *            The z-coordinate of the query box
     * @param bxw
     *            The x width of the query box
     * @param byw
     *            The y width of the query box
     * @param bzw
     *            The z width of the query box
     * @return String listing intersecting objects
     */
    public String findIntersections(
        int bx,
        int by,
        int bz,
        int bxw,
        int byw,
        int bzw) {
        StringBuilder sb = new StringBuilder();
        sb.append("The following objects intersect (").append(bx).append(" ")
            .append(by).append(" ").append(bz).append(" ").append(bxw).append(
                " ").append(byw).append(" ").append(bzw).append("):\r\n");
        int[] cnt = new int[1];
        root.findIntersections(0, 0, 0, WORLD_SIZE, WORLD_SIZE, WORLD_SIZE, bx,
            by, bz, bxw, byw, bzw, 0, sb, cnt);
        sb.append(cnt[0]).append(" nodes were visited in the bintree");
        return sb.toString();
    }
}
