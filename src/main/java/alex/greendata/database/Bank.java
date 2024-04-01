package alex.greendata.database;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Bank {

    @Id
    private int id;
    private String name;
    private int bic;

    public Bank() {
    }

    public Bank(String name, int bic) {
        this.name = name;
        this.bic = bic;
    }

    public Bank(int id, String name, int bic) {
        this.id = id;
        this.name = name;
        this.bic = bic;
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

    public int getBic() {
        return bic;
    }

    public void setBic(int bic) {
        this.bic = bic;
    }
}
