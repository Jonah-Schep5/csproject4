/**
 * Base class for all air objects in the ATC system.
 * All air objects have a 3D bounding box defined by origin coordinates
 * and widths in each dimension.
 *
 * @author CS3114/5040 Staff
 * @version Fall 2025
 */
public abstract class AirObject implements Comparable<AirObject> {
    /**
     * The name of the air object
     */
    private String name;

    /**
     * The x-coordinate of the origin (upper left corner)
     */
    private int xorig;

    /**
     * The y-coordinate of the origin (upper left corner)
     */
    private int yorig;

    /**
     * The z-coordinate of the origin (upper left corner)
     */
    private int zorig;

    /**
     * The width in the x dimension
     */
    private int xwidth;

    /**
     * The width in the y dimension
     */
    private int ywidth;

    /**
     * The width in the z dimension
     */
    private int zwidth;

    /**
     * Constructor for AirObject
     *
     * @param name
     *            The name of the air object
     * @param xorig
     *            The x-coordinate of the origin
     * @param yorig
     *            The y-coordinate of the origin
     * @param zorig
     *            The z-coordinate of the origin
     * @param xwidth
     *            The width in the x dimension
     * @param ywidth
     *            The width in the y dimension
     * @param zwidth
     *            The width in the z dimension
     */
    public AirObject(String name, int xorig, int yorig, int zorig, int xwidth,
        int ywidth, int zwidth) {
        this.name = name;
        this.xorig = xorig;
        this.yorig = yorig;
        this.zorig = zorig;
        this.xwidth = xwidth;
        this.ywidth = ywidth;
        this.zwidth = zwidth;
    }

    /**
     * Get the x-coordinate of the origin
     *
     * @return The x-coordinate
     */
    public int getXorig() {
        return xorig;
    }

    /**
     * Get the y-coordinate of the origin
     *
     * @return The y-coordinate
     */
    public int getYorig() {
        return yorig;
    }

    /**
     * Get the z-coordinate of the origin
     *
     * @return The z-coordinate
     */
    public int getZorig() {
        return zorig;
    }

    /**
     * Get the width in the x dimension
     *
     * @return The x width
     */
    public int getXwidth() {
        return xwidth;
    }

    /**
     * Get the width in the y dimension
     *
     * @return The y width
     */
    public int getYwidth() {
        return ywidth;
    }

    /**
     * Get the width in the z dimension
     *
     * @return The z width
     */
    public int getZwidth() {
        return zwidth;
    }

    /**
     * Get the name of the air object
     *
     * @return The name
     */
    public String getName() {
        return name;
    }

    /**
     * Compare two AirObjects by name (alphabetically)
     *
     * @param other
     *            The other AirObject to compare
     * @return Negative if this name comes before other, positive if after, 0
     *         if equal
     */
    @Override
    public int compareTo(AirObject other) {
        if (other == null) {
            return 1;
        }
        if (this.name == null) {
            return other.name == null ? 0 : -1;
        }
        if (other.name == null) {
            return 1;
        }
        return this.name.compareTo(other.name);
    }

    /**
     * Check if this bounding box intersects with another bounding box
     *
     * @param other
     *            The other AirObject
     * @return True if the boxes intersect (not just adjacent)
     */
    public boolean intersects(AirObject other) {
        if (other == null) {
            return false;
        }
        return (this.xorig < other.xorig + other.xwidth)
            && (this.xorig + this.xwidth > other.xorig)
            && (this.yorig < other.yorig + other.ywidth)
            && (this.yorig + this.ywidth > other.yorig)
            && (this.zorig < other.zorig + other.zwidth)
            && (this.zorig + this.zwidth > other.zorig);
    }

    /**
     * Check if this bounding box intersects with a given bounding box
     *
     * @param x
     *            The x-coordinate of the box
     * @param y
     *            The y-coordinate of the box
     * @param z
     *            The z-coordinate of the box
     * @param xwid
     *            The x width of the box
     * @param ywid
     *            The y width of the box
     * @param zwid
     *            The z width of the box
     * @return True if the boxes intersect
     */
    public boolean intersects(int x, int y, int z, int xwid, int ywid,
        int zwid) {
        return (this.xorig < x + xwid) && (this.xorig + this.xwidth > x)
            && (this.yorig < y + ywid) && (this.yorig + this.ywidth > y)
            && (this.zorig < z + zwid) && (this.zorig + this.zwidth > z);
    }

    /**
     * Check if this bounding box is contained within a given region
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
     * @return True if this box is contained within the region
     */
    public boolean isContainedIn(int x, int y, int z, int xwid, int ywid,
        int zwid) {
        return (this.xorig >= x) && (this.yorig >= y) && (this.zorig >= z)
            && (this.xorig + this.xwidth <= x + xwid)
            && (this.yorig + this.ywidth <= y + ywid)
            && (this.zorig + this.zwidth <= z + zwid);
    }
}

