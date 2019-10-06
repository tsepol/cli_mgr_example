package org.tsepol.cli_mgr_ex.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.tsepol.cli_mgr_ex.exceptions.*;

import javax.persistence.*;
import java.util.regex.Pattern;

@Entity
@Table(name = "clients")
public class Client extends BaseModel {

    public Client(){

    }

    @Id
    @Column(name = "nif", unique = true, nullable = false, updatable = false)
    private String nif;

    @Column
    private Integer phone;

    @Column
    private String name;

    @Column
    private String address;

    public String getAddress() {
        return address;
    }

    private void setAddress(String address) {
        this.address = address;
    }

    public String getnif() {
        return nif;
    }

    private Integer getPhone() {
        return phone;
    }

    private void setPhone(Integer phone) {
        this.phone = phone;
    }

    private String getName() {
        return name;
    }

    private void setName(String name) {
        this.name = name;
    }

    public Client (String nif, String new_address, Integer phone, String name) throws ModelException {
        Pattern nine_digit_pattern = Pattern.compile("[0-9]{9}");
        Pattern name_no_numeric = Pattern.compile("^[a-zA-Z\\u00C0-\\u017F\\s]*$");
        Pattern address_pattern = Pattern.compile("^[a-zA-Z\\u00C0-\\u017F\\s0-9%,.]*$");
        if(!address_pattern.matcher(new_address).matches()){
            throw new InvalidAddressException();
        }
        if(!nine_digit_pattern.matcher(nif).matches()){
            throw new InvalidNIFException();
        }
        if(!nine_digit_pattern.matcher(phone.toString()).matches()){
            throw new InvalidPhoneException();
        }
        if(!name_no_numeric.matcher(name).matches()){
            throw new InvalidNameException();
        }
        this.nif = nif;
        setAddress(new_address);
        setName(name);
        setPhone(phone);
    }

    @Override
    public String toString() {
        return getnif() + getPhone() + getName();
    }
}
