package org.tsepol.cli_mgr_ex.models;

import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.Pattern;

@Entity
@Table(name = "clients")
public class Client extends BaseModel {

    public Client(){

    }

    @Id
    @Column(unique = true, length = 9, nullable = false, updatable = false)
    @Pattern(regexp = "\\d{9}")
    private String nif;

    @Column
    @Pattern(regexp = "\\d{9}")
    private String phone;

    @Column
    @Pattern(regexp = "^[a-zA-Z\\u00C0-\\u017F\\s]*$")
    private String name;

    @Column
    @Pattern(regexp = "^[a-zA-Z\\u00C0-\\u017F\\s0-9%,.\\-]*$")
    private String address;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return getNif() + getPhone() + getName();
    }

    public String getNif() {
        return nif;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }
}
