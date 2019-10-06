package org.tsepol.cli_mgr_ex.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.tsepol.cli_mgr_ex.exceptions.InvalidAddressException;

import javax.persistence.*;
import java.util.regex.Pattern;

@Entity
@Table(name = "addresses")
@JsonIgnoreProperties(
        value = {"address"},
        allowGetters = true
)
public class Address {
    @Id
    @GeneratedValue(generator = "address_id_generator")
    @SequenceGenerator(
            name = "address",
            sequenceName = "address_id_generator",
            initialValue = 1000
    )
    private Long id;

    @Column
    private String address;

    public Address(String new_address) throws InvalidAddressException {
        Pattern address_pattern = Pattern.compile("^[a-zA-Z\\u00C0-\\u017F\\s0-9]*$");
        if(!address_pattern.matcher(new_address).matches()){
            throw new InvalidAddressException();
        }
        setAddress(new_address);
    }

    public String getAddress() {
        return address;
    }

    private void setAddress(String address) {
        this.address = address;
    }
}
