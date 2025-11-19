/**
 * Represents a balloon in the air traffic control system.
 *
 * @author CS3114/5040 Staff
 * @version Fall 2025
 */
public class Balloon extends AirObject {
    /**
     * The type of balloon
     */
    private String type;

    /**
     * The ascent rate of the balloon
     */
    private int ascentRate;

    /**
     * Constructor for Balloon
     *
     * @param name
     *            The name of the balloon
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
     * @param type
     *            The type of balloon
     * @param ascentRate
     *            The ascent rate
     */
    public Balloon(String name, int xorig, int yorig, int zorig, int xwidth,
        int ywidth, int zwidth, String type, int ascentRate) {
        super(name, xorig, yorig, zorig, xwidth, ywidth, zwidth);
        this.type = type;
        this.ascentRate = ascentRate;
    }

    /**
     * Get the type of balloon
     *
     * @return The type
     */
    public String getType() {
        return type;
    }

    /**
     * Get the ascent rate
     *
     * @return The ascent rate
     */
    public int getAscentRate() {
        return ascentRate;
    }

    /**
     * Check if this balloon is valid
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
        return !((ascentRate < 0) || (type == null));
    }

    /**
     * Get string representation of balloon
     *
     * @return String representation
     */
    @Override
    public String toString() {
        return "Balloon " + getName() + " " + getXorig() + " " + getYorig()
            + " " + getZorig() + " " + getXwidth() + " " + getYwidth() + " "
            + getZwidth() + " " + type + " " + ascentRate;
    }
}

