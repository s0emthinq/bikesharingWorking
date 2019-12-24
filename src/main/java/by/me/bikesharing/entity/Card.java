package by.me.bikesharing.entity;

import by.me.bikesharing.dao.CardDao;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * The type Card.
 */
public class Card extends Entity {
    private long id;
    private String serialNumber;
    private BigDecimal balance;
    private long idUser;

    /**
     * Instantiates a new Card.
     */
    public Card() {

    }

    /**
     * Instantiates a new Card.
     *
     * @param id           the id
     * @param serialNumber the serial number
     * @param balance      the balance
     * @param idUser       the id user
     */
    public Card(long id, String serialNumber, BigDecimal balance, long idUser) {
        this.id = id;
        this.serialNumber = serialNumber;
        this.balance = balance;
        this.idUser = idUser;
    }

    /**
     * Instantiates a new Card.
     *
     * @param serialNumber the serial number
     * @param balance      the balance
     * @param idUser       the id user
     */
    public Card(String serialNumber, BigDecimal balance, long idUser) {
        this.serialNumber = serialNumber;
        this.balance = balance;
        this.idUser = idUser;
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
     * Gets serial number.
     *
     * @return the serial number
     */
    public String getSerialNumber() {
        return serialNumber;
    }

    /**
     * Sets serial number.
     *
     * @param serialNumber the serial number
     */
    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    /**
     * Gets balance.
     *
     * @return the balance
     */
    public BigDecimal getBalance() {
        return balance;
    }

    /**
     * Sets balance.
     *
     * @param balance the balance
     */
    public void setBalance(BigDecimal balance) {
        this.balance = balance;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return idUser == card.idUser &&
                Objects.equals(serialNumber, card.serialNumber) &&
                Objects.equals(balance, card.balance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(serialNumber, balance, idUser);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Card{");
        sb.append("id=").append(id);
        sb.append(", serialNumber='").append(serialNumber).append('\'');
        sb.append(", balance=").append(balance);
        sb.append(", idUser=").append(idUser);
        sb.append('}');
        return sb.toString();
    }
}
