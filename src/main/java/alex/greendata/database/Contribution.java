package alex.greendata.database;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.util.Date;

@Entity
public class Contribution {

    @Id
    private int id;
    private int customer;
    private int bank;
    private Date opening;
    private int percentage;
    private int duration;

    public Contribution() {
    }

    public Contribution(int customer, int bank, Date opening, int percentage, int duration) {
        this.customer = customer;
        this.bank = bank;
        this.opening = opening;
        this.percentage = percentage;
        this.duration = duration;
    }

    public Contribution(int id, int customer, int bank, Date opening, int percentage, int duration) {
        this.id = id;
        this.customer = customer;
        this.bank = bank;
        this.opening = opening;
        this.percentage = percentage;
        this.duration = duration;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCustomer() {
        return customer;
    }

    public void setCustomer(int customer) {
        this.customer = customer;
    }

    public int getBank() {
        return bank;
    }

    public void setBank(int bank) {
        this.bank = bank;
    }

    public Date getOpening() {
        return opening;
    }

    public void setOpening(Date opening) {
        this.opening = opening;
    }

    public int getPercentage() {
        return percentage;
    }

    public void setPercentage(int percentage) {
        this.percentage = percentage;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}
