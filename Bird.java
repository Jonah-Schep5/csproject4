/**
 * Represents a bird in the air traffic control system.
 *
 * @author Jonah Schepers
 * @author Rowan Muhoberac
 * @version December 8, 2025
 */
public class Bird extends AirObject {
    /**
     * The type of bird
     */
    private String type;

    /**
     * The number of birds
     */
    private int number;

    /**
     * Constructor for Bird
     *
     * @param name
     *            The name of the bird
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
     *            The type of bird
     * @param number
     *            The number of birds
     */
    public Bird(
        String name,
        int xorig,
        int yorig,
        int zorig,
        int xwidth,
        int ywidth,
        int zwidth,
        String type,
        int number) {
        super(name, xorig, yorig, zorig, xwidth, ywidth, zwidth);
        this.type = type;
        this.number = number;
    }


    /**
     * Get the type
     *
     * @return The type
     */
    public String getType() {
        return type;
    }


    /**
     * Get the number
     *
     * @return The number
     */
    public int getNumber() {
        return number;
    }


    /**
     * Check if this bird is valid
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

        return !(number < 1 || type == null);
    }


    /**
     * Get string representation of bird
     *
     * @return String representation
     */
    @Override
    public String toString() {
        return "Bird " + getName() + " " + getXorig() + " " + getYorig() + " "
            + getZorig() + " " + getXwidth() + " " + getYwidth() + " "
            + getZwidth() + " " + type + " " + number;
    }
}
