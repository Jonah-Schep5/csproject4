/**
 * Represents an airplane in the air traffic control system.
 *
 * @author CS3114/5040 Staff
 * @version Fall 2025
 */
public class AirPlane extends AirObject {
    /**
     * The airline carrier
     */
    private String carrier;

    /**
     * The flight number
     */
    private int flightNumber;

    /**
     * The number of engines
     */
    private int numEngines;

    /**
     * Constructor for AirPlane
     *
     * @param name
     *            The name of the airplane
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
     * @param carrier
     *            The airline carrier
     * @param flightNumber
     *            The flight number
     * @param numEngines
     *            The number of engines
     */
    public AirPlane(String name, int xorig, int yorig, int zorig, int xwidth,
        int ywidth, int zwidth, String carrier, int flightNumber,
        int numEngines) {
        super(name, xorig, yorig, zorig, xwidth, ywidth, zwidth);
        this.carrier = carrier;
        this.flightNumber = flightNumber;
        this.numEngines = numEngines;
    }

    /**
     * Get the carrier
     *
     * @return The carrier
     */
    public String getCarrier() {
        return carrier;
    }

    /**
     * Get the flight number
     *
     * @return The flight number
     */
    public int getFlightNumber() {
        return flightNumber;
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
     * Check if this airplane is valid
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
        if (carrier == null) {
            return false;
        }
        return !(flightNumber < 1 || numEngines < 1);
    }

    /**
     * Get string representation of airplane
     *
     * @return String representation
     */
    @Override
    public String toString() {
        return "Airplane " + getName() + " " + getXorig() + " " + getYorig()
            + " " + getZorig() + " " + getXwidth() + " " + getYwidth() + " "
            + getZwidth() + " " + carrier + " " + flightNumber + " "
            + numEngines;
    }
}

