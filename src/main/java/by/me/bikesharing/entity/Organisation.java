package by.me.bikesharing.entity;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * The type Organisation.
 */
public class Organisation extends Entity {
    private long id;
    private String name;
    private BigDecimal balance;

    /**
     * Instantiates a new Organisation.
     */
    public Organisation() {
    }

    /**
     * Instantiates a new Organisation.
     *
     * @param id      the id
     * @param name    the name
     * @param balance the balance
     */
    public Organisation(long id, String name, BigDecimal balance) {
        this.id = id;
        this.name = name;
        this.balance = balance;
    }

    /**
     * Instantiates a new Organisation.
     *
     * @param name    the name
     * @param balance the balance
     */
    public Organisation(String name, BigDecimal balance) {
        this.name = name;
        this.balance = balance;
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
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name.
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Organisation that = (Organisation) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Organisation{");
        sb.append("id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append(", balance=").append(balance);
        sb.append('}');
        return sb.toString();
    }
}
