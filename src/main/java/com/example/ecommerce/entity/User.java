package com.example.ecommerce.entity;

import com.vladmihalcea.hibernate.type.json.JsonStringType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Table(name = "user")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@TypeDef(
        name = "json",
        typeClass = JsonStringType.class
)
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "full_name", nullable = false, length = 200)
    private String fullName;

    @Column(name = "email", nullable = false, unique = true, length = 200)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "address")
    private String address;

    @Column(name = "phone")
    private String phone;

    @Column(name = "status", columnDefinition = "BOOLEAN")
    private boolean status;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @Type(type = "json")
    @Column(name = "roles", nullable = false, columnDefinition = "json")
    private List<String> roles;

    @OneToMany(mappedBy = "user")
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "buyer")
    private List<Order> orders = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<CartItem> cartItems = new ArrayList<>();

    public User(long id) {
        this.id = id;
    }
    @PreRemove
    public void preRemove() {
        comments.forEach(c -> c.setUser(null));
        comments.forEach(c -> c.setProduct(null));
        orders.forEach(o -> o.setBuyer(null));
        cartItems.forEach(ci -> ci.setUser(null));
        cartItems.forEach(ci -> ci.setProduct(null));
    }
}