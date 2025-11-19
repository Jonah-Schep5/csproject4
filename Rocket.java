/**
 * Represents a rocket in the air traffic control system.
 *
 * @author CS3114/5040 Staff
 * @version Fall 2025
 */
public class Rocket extends AirObject {
    /**
     * The ascent rate
     */
    private int ascentRate;

    /**
     * The trajectory (floating point)
     */
    private double trajectory;

    /**
     * Constructor for Rocket
     *
     * @param name
     *            The name of the rocket
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
     * @param ascentRate
     *            The ascent rate
     * @param trajectory
     *            The trajectory
     */
    public Rocket(String name, int xorig, int yorig, int zorig, int xwidth,
        int ywidth, int zwidth, int ascentRate, double trajectory) {
        super(name, xorig, yorig, zorig, xwidth, ywidth, zwidth);
        this.ascentRate = ascentRate;
        this.trajectory = trajectory;
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
     * Get the trajectory
     *
     * @return The trajectory
     */
    public double getTrajectory() {
        return trajectory;
    }

    /**
     * Check if this rocket is valid
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
        return !((trajectory < 0) || (ascentRate < 0));
    }

    /**
     * Get string representation of rocket
     *
     * @return String representation
     */
    @Override
    public String toString() {
        return "Rocket " + getName() + " " + getXorig() + " " + getYorig()
            + " " + getZorig() + " " + getXwidth() + " " + getYwidth() + " "
            + getZwidth() + " " + ascentRate + " " + trajectory;
    }
}

