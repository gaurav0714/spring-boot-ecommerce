package in.nic.sb_ecom.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "addresses")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long addressId;

    @NotBlank
    @Size(min = 5, message = "Street name must be at least 5 characters")
    private String Street;

    @NotBlank
    @Size(min = 5, message = "Building  name must be at least 5 characters")
    private String buildingName;

    @NotBlank
    @Size(min = 4, message = "City  name must be at least 4 characters")
    private String city;

    @NotBlank
    @Size(min = 2, message = "State  name must be at least 2 characters")
    private String state;

    @NotBlank
    @Size(min = 2, message = "Country  name must be at least 2 characters")
    private String country;

    @NotBlank
    @Size(min = 6, message = "Pin code must be at least 6 characters")
    private String pincode;

    @ToString.Exclude
    @ManyToMany(mappedBy = "addresses")
    private List<User> users = new ArrayList<>();
}
