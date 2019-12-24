package by.me.bikesharing.entity;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.Objects;

/**
 * The type Order.
 */
public class Order extends Entity {

    private long id;
    private long idUser;
    private long idBike;
    private LocalTime beginTime;
    private LocalTime endTime;
    private BigDecimal cost;
    private LocalTime returnTime;
    private BigDecimal debt;
    private int status;
    private long idCard;

    /**
     * Instantiates a new Order.
     */
    public Order() {
    }

    /**
     * Instantiates a new Order.
     *
     * @param id         the id
     * @param idUser     the id user
     * @param idBike     the id bike
     * @param beginTime  the begin time
     * @param endTime    the end time
     * @param cost       the cost
     * @param returnTime the return time
     * @param debt       the debt
     * @param status     the status
     * @param idCard     the id card
     */
    public Order(long id, long idUser, long idBike, LocalTime beginTime, LocalTime endTime,
                 BigDecimal cost, LocalTime returnTime, BigDecimal debt, int status, long idCard) {
        this.id = id;
        this.idUser = idUser;
        this.idBike = idBike;
        this.beginTime = beginTime;
        this.endTime = endTime;
        this.cost = cost;
        this.returnTime = returnTime;
        this.debt = debt;
        this.status = status;
        this.idCard = idCard;
    }

    /**
     * Instantiates a new Order.
     *
     * @param idUser     the id user
     * @param idBike     the id bike
     * @param beginTime  the begin time
     * @param endTime    the end time
     * @param cost       the cost
     * @param returnTime the return time
     * @param debt       the debt
     * @param status     the status
     * @param idCard     the id card
     */
    public Order(long idUser, long idBike, LocalTime beginTime, LocalTime endTime,
                 BigDecimal cost, LocalTime returnTime, BigDecimal debt, int status, long idCard) {
        this.idUser = idUser;
        this.idBike = idBike;
        this.beginTime = beginTime;
        this.endTime = endTime;
        this.cost = cost;
        this.returnTime = returnTime;
        this.debt = debt;
        this.status = status;
        this.idCard = idCard;
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
     * Gets id user.
     *
     * @return the id user
     */
    public long getIdUser() {
        return idUser;
    }

    /**
     * Sets id user.
     *
     * @param idUser the id user
     */
    public void setIdUser(long idUser) {
        this.idUser = idUser;
    }

    /**
     * Gets id bike.
     *
     * @return the id bike
     */
    public long getIdBike() {
        return idBike;
    }

    /**
     * Sets id bike.
     *
     * @param idBike the id bike
     */
    public void setIdBike(long idBike) {
        this.idBike = idBike;
    }

    /**
     * Gets begin time.
     *
     * @return the begin time
     */
    public LocalTime getBeginTime() {
        return beginTime;
    }

    /**
     * Sets begin time.
     *
     * @param beginTime the begin time
     */
    public void setBeginTime(LocalTime beginTime) {
        this.beginTime = beginTime;
    }

    /**
     * Gets end time.
     *
     * @return the end time
     */
    public LocalTime getEndTime() {
        return endTime;
    }

    /**
     * Sets end time.
     *
     * @param endTime the end time
     */
    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    /**
     * Gets cost.
     *
     * @return the cost
     */
    public BigDecimal getCost() {
        return cost;
    }

    /**
     * Sets cost.
     *
     * @param cost the cost
     */
    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    /**
     * Gets return time.
     *
     * @return the return time
     */
    public LocalTime getReturnTime() {
        return returnTime;
    }

    /**
     * Sets return time.
     *
     * @param returnTime the return time
     */
    public void setReturnTime(LocalTime returnTime) {
        this.returnTime = returnTime;
    }

    /**
     * Gets debt.
     *
     * @return the debt
     */
    public BigDecimal getDebt() {
        return debt;
    }

    /**
     * Sets debt.
     *
     * @param debt the debt
     */
    public void setDebt(BigDecimal debt) {
        this.debt = debt;
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
     * Gets id card.
     *
     * @return the id card
     */
    public long getIdCard() {
        return idCard;
    }

    /**
     * Sets id card.
     *
     * @param idCard the id card
     */
    public void setIdCard(long idCard) {
        this.idCard = idCard;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return idUser == order.idUser &&
                idBike == order.idBike &&
                status == order.status &&
                idCard == order.idCard &&
                Objects.equals(beginTime, order.beginTime) &&
                Objects.equals(endTime, order.endTime) &&
                Objects.equals(cost, order.cost) &&
                Objects.equals(returnTime, order.returnTime) &&
                Objects.equals(debt, order.debt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idUser, idBike, beginTime, endTime, cost, returnTime, debt, status, idCard);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Order{");
        sb.append("id=").append(id);
        sb.append(", idUser=").append(idUser);
        sb.append(", idBike=").append(idBike);
        sb.append(", beginTime=").append(beginTime);
        sb.append(", endTime=").append(endTime);
        sb.append(", cost=").append(cost);
        sb.append(", returnTime=").append(returnTime);
        sb.append(", debt=").append(debt);
        sb.append(", status=").append(status);
        sb.append(", idCard=").append(idCard);
        sb.append('}');
        return sb.toString();
    }
}
