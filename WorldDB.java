import java.util.Random;

/**
 * The world for this project. We have a Skip List and a Bintree
 *
 * @author CS3114/5040 Staff
 * @version Fall 2025
 */
public class WorldDB implements ATC {
    /**
     * Random number generator for SkipList
     */
    private Random rnd;

    /**
     * SkipList for storing AirObjects by name
     */
    private SkipList<AirObject> skipList;

    /**
     * Bintree for storing AirObjects spatially
     */
    private Bintree bintree;

    /**
     * Create a brave new World.
     *
     * @param r
     *            A random number generator to use
     */
    public WorldDB(Random r) {
        rnd = r;
        if (rnd == null) {
            rnd = new Random();
        }
        clear();
    }

    /**
     * Clear the world
     */
    public void clear() {
        skipList = new SkipList<AirObject>(rnd);
        bintree = new Bintree();
    }

    /**
     * Validate an AirObject based on its type
     *
     * @param a
     *            The AirObject to validate
     * @return True if valid
     */
    private boolean validateAirObject(AirObject a) {
        if (a == null || a.getName() == null || a.getName().isEmpty()) {
            return false;
        }
        if (a.getXorig() < 0 || a.getXorig() > 1023 || a.getXwidth() < 1
            || a.getXwidth() > 1024 || a.getXorig() + a.getXwidth() > 1024) {
            return false;
        }
        if (a.getYorig() < 0 || a.getYorig() > 1023 || a.getYwidth() < 1
            || a.getYwidth() > 1024 || a.getYorig() + a.getYwidth() > 1024) {
            return false;
        }
        if (a.getZorig() < 0 || a.getZorig() > 1023 || a.getZwidth() < 1
            || a.getZwidth() > 1024 || a.getZorig() + a.getZwidth() > 1024) {
            return false;
        }

        if (a instanceof Balloon) {
            return ((Balloon) a).isValid();
        }
        if (a instanceof AirPlane) {
            return ((AirPlane) a).isValid();
        }
        if (a instanceof Drone) {
            return ((Drone) a).isValid();
        }
        if (a instanceof Bird) {
            return ((Bird) a).isValid();
        }
        if (a instanceof Rocket) {
            return ((Rocket) a).isValid();
        }
        return false;
    }

    // ----------------------------------------------------------
    /**
     * (Try to) insert an AirObject into the database
     *
     * @param a
     *            An AirObject.
     * @return True iff the AirObject is successfully entered into the database
     */
    public boolean add(AirObject a) {
        if (!validateAirObject(a)) {
            return false;
        }

        if (skipList.find(a) != null) {
            return false;
        }

        if (skipList.insert(a)) {
            bintree.insert(a);
            return true;
        }
        return false;
    }

    // ----------------------------------------------------------
    /**
     * The AirObject with this name is deleted from the database (if it exists).
     * Print the AirObject's toString value if one with that name exists. If no
     * such AirObject with this name exists, return null.
     *
     * @param name
     *            AirObject name.
     * @return A string representing the AirObject, or null if no such name.
     */
    public String delete(String name) {
        if (name == null) {
            return null;
        }

        AirObject found = findByName(name);
        if (found == null) {
            return null;
        }

        AirObject removed = skipList.remove(found);
        if (removed == null) {
            return null;
        }

        AirObject[] allObjects = getAllObjects();
        bintree = new Bintree();
        for (int i = 0; i < allObjects.length; i++) {
            if (allObjects[i] != null && !allObjects[i].getName().equals(name)) {
                bintree.insert(allObjects[i]);
            }
        }

        return removed.toString();
    }

    /**
     * Find an AirObject by name
     *
     * @param name
     *            The name to search for
     * @return The AirObject if found, null otherwise
     */
    private AirObject findByName(String name) {
        AirObject[] allObjects = getAllObjects();
        for (int i = 0; i < allObjects.length; i++) {
            if (allObjects[i] != null && allObjects[i].getName().equals(name)) {
                return allObjects[i];
            }
        }
        return null;
    }

    /**
     * Helper class for searching by name in SkipList
     */
    private static class SearchHelper extends AirObject {
        /**
         * Constructor
         *
         * @param name
         *            The name to search for
         */
        public SearchHelper(String name) {
            super(name, 0, 0, 0, 1, 1, 1);
        }

        @Override
        public String toString() {
            return "";
        }
    }

    /**
     * Get all objects from the skip list
     *
     * @return Array of all AirObjects
     */
    private AirObject[] getAllObjects() {
        // Use a very wide range to get all objects
        SearchHelper min = new SearchHelper("");
        SearchHelper max = new SearchHelper("zzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz");
        Comparable<AirObject>[] range = skipList.range(min, max);
        if (range == null) {
            return new AirObject[0];
        }
        AirObject[] result = new AirObject[range.length];
        for (int i = 0; i < range.length; i++) {
            result[i] = (AirObject) range[i];
        }
        return result;
    }

    // ----------------------------------------------------------
    /**
     * Return a listing of the Skiplist in alphabetical order on the names. See
     * the sample test cases for details on format.
     *
     * @return String listing the AirObjects in the Skiplist as specified.
     */
    public String printskiplist() {
        return skipList.toString();
    }

    // ----------------------------------------------------------
    /**
     * Return a listing of the Bintree nodes in preorder. See the sample test
     * cases for details on format.
     *
     * @return String listing the Bintree nodes as specified.
     */
    public String printbintree() {
        return bintree.print();
    }

    // ----------------------------------------------------------
    /**
     * Print an AirObject with a given name if it exists
     *
     * @param name
     *            The name of the AirObject to print
     * @return String showing the toString for the AirObject if it exists Return
     *         null if there is no such name
     */
    public String print(String name) {
        if (name == null) {
            return null;
        }

        AirObject found = findByName(name);
        if (found == null) {
            return null;
        }

        return found.toString();
    }

    // ----------------------------------------------------------
    /**
     * Return a listing of the AirObjects found in the database between the min
     * and max values for names. See the sample test cases for details on
     * format.
     *
     * @param start
     *            Minimum of range
     * @param end
     *            Maximum of range
     * @return String listing the AirObjects in the range as specified. Null if
     *         the parameters are bad
     */
    public String rangeprint(String start, String end) {
        if (start == null || end == null) {
            return null;
        }

        // Create temporary objects for range query
        SearchHelper minObj = new SearchHelper(start);
        SearchHelper maxObj = new SearchHelper(end);

        Comparable<AirObject>[] range = skipList.range(minObj, maxObj);
        if (range == null) {
            return null;
        }

        StringBuilder result = new StringBuilder();
        result.append("Found these records in the range ");
        result.append(start);
        result.append(" to ");
        result.append(end);
        result.append("\r\n");

        for (int i = 0; i < range.length; i++) {
            result.append(range[i].toString());
            result.append("\r\n");
        }

        return result.toString();
    }

    // ----------------------------------------------------------
    /**
     * Return a listing of all collisions between AirObjects bounding boxes that
     * are found in the database. See the sample test cases for details on
     * format. Note that the collision is only reported for the node that
     * contains the origin of the intersection box.
     *
     * @return String listing the AirObjects that participate in collisions.
     */
    public String collisions() {
        return bintree.findCollisions();
    }

    // ----------------------------------------------------------
    /**
     * Return a listing of all AirObjects whose bounding boxes that intersect
     * the given bounding box. Note that the collision is only reported for the
     * node that contains the origin of the intersection box. See the sample
     * test cases for details on format.
     *
     * @param x
     *            Bounding box upper left x
     * @param y
     *            Bounding box upper left y
     * @param z
     *            Bounding box upper left z
     * @param xwid
     *            Bounding box x width
     * @param ywid
     *            Bounding box y width
     * @param zwid
     *            Bounding box z width
     * @return String listing the AirObjects that intersect the given box.
     *         Return null if any input parameters are bad
     */
    public String intersect(int x, int y, int z, int xwid, int ywid, int zwid) {
        if (x < 0 || x > 1023 || xwid < 1 || xwid > 1024 || x + xwid > 1024) {
            return null;
        }
        if (y < 0 || y > 1023 || ywid < 1 || ywid > 1024 || y + ywid > 1024) {
            return null;
        }
        if (z < 0 || z > 1023 || zwid < 1 || zwid > 1024 || z + zwid > 1024) {
            return null;
        }
        return bintree.findIntersections(x, y, z, xwid, ywid, zwid);
    }
}
