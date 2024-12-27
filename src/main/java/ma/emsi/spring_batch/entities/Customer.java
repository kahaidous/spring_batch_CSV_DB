package ma.emsi.spring_batch.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data @AllArgsConstructor @NoArgsConstructor
public class Customer {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String customerId;
    private String firstName;
    private String lastName;
    private String company;
    private String city;
    private String country;
    private String phone;
    private String email;
    private Date subscriptionDate;
    private String website;
}
