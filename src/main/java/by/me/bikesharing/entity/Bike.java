package by.me.bikesharing.entity;

import java.util.Objects;

/**
 * The type Bike.
 */
public class Bike extends Entity {
    private long id;
    private double latitude;
    private double longitude;
    private double costPerHour;
    private int status;
    private long idOrganisation;

    /**
     * Instantiates a new Bike.
     */
    public Bike() {
    }

    /**
     * Instantiates a new Bike.
     *
     * @param id             the id
     * @param latitude       the latitude
     * @param longitude      the longitude
     * @param costPerHour    the cost per hour
     * @param status         the status
     * @param idOrganisation the id organisation
     */
    public Bike(long id, double latitude, double longitude, double costPerHour, int status, long idOrganisation) {
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.costPerHour = costPerHour;
        this.status = status;
        this.idOrganisation = idOrganisation;
    }

    /**
     * Instantiates a new Bike.
     *
     * @param latitude       the latitude
     * @param longitude      the longitude
     * @param costPerHour    the cost per hour
     * @param status         the status
     * @param idOrganisation the id organisation
     */
    public Bike(double latitude, double longitude, double costPerHour, int status, long idOrganisation) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.costPerHour = costPerHour;
        this.status = status;
        this.idOrganisation = idOrganisation;
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Gets latitude.
     *
     * @return the latitude
     */
    public double getLatitude() {
        return latitude;
    }

    /**
     * Sets latitude.
     *
     * @param latitude the latitude
     */
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    /**
     * Gets longitude.
     *
     * @return the longitude
     */
    public double getLongitude() {
        return longitude;
    }

    /**
     * Sets longitude.
     *
     * @param longitude the longitude
     */
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    /**
     * Gets cost per hour.
     *
     * @return the cost per hour
     */
    public double getCostPerHour() {
        return costPerHour;
    }

    /**
     * Sets cost per hour.
     *
     * @param costPerHour the cost per hour
     */
    public void setCostPerHour(double costPerHour) {
        this.costPerHour = costPerHour;
    }

    /**
     * Gets status.
     *
     * @return the status
     */
    public int getStatus() {
        return status;
    }

    /**
     * Sets status.
     *
     * @param status the status
     */
    public void setStatus(int status) {
        this.status = status;
    }

    /**
     * Gets id organisation.
     *
     * @return the id organisation
     */
    public long getIdOrganisation() {
        return idOrganisation;
    }

    /**
     * Sets id organisation.
     *
     * @param idOrganisation the id organisation
     */
    public void setIdOrganisation(long idOrganisation) {
        this.idOrganisation = idOrganisation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bike bike = (Bike) o;
        return Double.compare(bike.latitude, latitude) == 0 &&
                Double.compare(bike.longitude, longitude) == 0 &&
                Double.compare(bike.costPerHour, costPerHour) == 0 &&
                status == bike.status &&
                idOrganisation == bike.idOrganisation;
    }

    @Override
    public int hashCode() {
        return Objects.hash(latitude, longitude, costPerHour, status, idOrganisation);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Bike{");
        sb.append("id=").append(id);
        sb.append(", latitude=").append(latitude);
        sb.append(", longitude=").append(longitude);
        sb.append(", costPerHour=").append(costPerHour);
        sb.append(", status=").append(status);
        sb.append(", idOrganisation=").append(idOrganisation);
        sb.append('}');
        return sb.toString();
    }
}
