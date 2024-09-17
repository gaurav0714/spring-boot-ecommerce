package in.nic.sb_ecom.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long productId;
    @NotBlank
    @Size(min=3, message = "Product name contains at least 3 characters.")
    private String productName;
    private String image;
    @NotBlank
    @Size(min=6, message = "Product description contains at least 6 characters.")
    private String description;
    private Integer quantity;
    private double price;
    private double discount;
    private double specialPrice;

    @ManyToOne
    @JoinColumn(name = "category_d")
    private Category category;
}
