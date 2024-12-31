package Bilanci.Bilanci;


import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name="bilanci")
public class BilanciEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String username;
    private float bilanci;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public float getBilanci() {
        return bilanci;
    }

    public void setBilanci(float bilanci) {
        this.bilanci = bilanci;
    }
    public String toString(){
        return "Id: "+this.id+"\n"
                +"Username: "+this.username+"\n"
                +"Bilanci: "+this.bilanci;
    }
}
