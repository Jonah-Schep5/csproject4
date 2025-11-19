/**
 * Represents a drone in the air traffic control system.
 *
 * @author CS3114/5040 Staff
 * @version Fall 2025
 */
public class Drone extends AirObject {
    /**
     * The brand of the drone
     */
    private String brand;

    /**
     * The number of engines
     */
    private int numEngines;

    /**
     * Constructor for Drone
     *
     * @param name
     *            The name of the drone
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
     * @param brand
     *            The brand of the drone
     * @param numEngines
     *            The number of engines
     */
    public Drone(String name, int xorig, int yorig, int zorig, int xwidth,
        int ywidth, int zwidth, String brand, int numEngines) {
        super(name, xorig, yorig, zorig, xwidth, ywidth, zwidth);
        this.brand = brand;
        this.numEngines = numEngines;
    }

    /**
     * Get the brand
     *
     * @return The brand
     */
    public String getBrand() {
        return brand;
    }

    /**
     * Get the number of engines
     *
     * @return The number of engines
     */
    public int getNumEngines() {
        return numEngines;
    }

    /**
     * Check if this drone is valid
     *
     * @return True if valid
     */
    public boolean isValid() {
        if (getName() == null || getName().isEmpty()) {
            return false;
        }
        if (getXorig() < 0 || getXorig() > 1023) {
            return false;
        }
        if (getYorig() < 0 || getYorig() > 1023) {
            return false;
        }
        if (getZorig() < 0 || getZorig() > 1023) {
            return false;
        }
        if (getXwidth() < 1 || getXwidth() > 1024) {
            return false;
        }
        if (getYwidth() < 1 || getYwidth() > 1024) {
            return false;
        }
        if (getZwidth() < 1 || getZwidth() > 1024) {
            return false;
        }
        if (getXorig() + getXwidth() > 1024) {
            return false;
        }
        if (getYorig() + getYwidth() > 1024) {
            return false;
        }
        if (getZorig() + getZwidth() > 1024) {
            return false;
        }
        return !(brand == null || numEngines < 1);
    }

    /**
     * Get string representation of drone
     *
     * @return String representation
     */
    @Override
    public String toString() {
        return "Drone " + getName() + " " + getXorig() + " " + getYorig()
            + " " + getZorig() + " " + getXwidth() + " " + getYwidth() + " "
            + getZwidth() + " " + brand + " " + numEngines;
    }
}

