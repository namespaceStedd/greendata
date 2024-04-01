package alex.greendata.database;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Customer {

    @Id
    private int id;
    private String name;
    private String shortname;
    private String address;
    private int organizationForm;

    public Customer() {
    }

    public Customer(String name, String shortname, String address, int organizationForm) {
        this.name = name;
        this.shortname = shortname;
        this.address = address;
        this.organizationForm = organizationForm;
    }

    public Customer(int id, String name, String shortname, String address, int organizationForm) {
        this.id = id;
        this.name = name;
        this.shortname = shortname;
        this.address = address;
        this.organizationForm = organizationForm;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortname() {
        return shortname;
    }

    public void setShortname(String shortname) {
        this.shortname = shortname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getOrganizationForm() {
        return organizationForm;
    }

    public void setOrganizationForm(int organizationForm) {
        this.organizationForm = organizationForm;
    }
}