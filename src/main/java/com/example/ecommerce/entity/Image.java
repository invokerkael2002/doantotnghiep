package com.example.ecommerce.entity;

import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;

@Table(name = "image")
@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Image {
    @Id
    @Column(name = "id", nullable = false)
    private String id;

    @Column(name = "name")
    private String name;

    @Column(name = "type")
    private String type;

    @Column(name = "link", unique = true)
    private String link;

    @ManyToOne
    @JoinColumn(name = "uploaded_by")
    private User uploadedBy;

    @Column(name = "uploaded_at")
    private Timestamp uploadedAt;

}