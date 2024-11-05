package com.openclassrooms.backend.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "RENTALS")
@Data
public class Rental {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private int id;

  @ManyToOne
  @JoinColumn(name = "owner_id")
  private User owner_id;

  @Column(name = "name")
  private String name;

  @Column(name = "picture")
  private String picture;

  @Column(name = "description", length = 2000)
  private String description;

  @Column(name = "surface")
  private float surface;

  @Column(name = "price")
  private float price;

  @CreationTimestamp
  @Column(name = "created_at")
  private LocalDateTime created_at;

  @UpdateTimestamp
  @Column(name = "updated_at")
  private LocalDateTime updated_at;
}
