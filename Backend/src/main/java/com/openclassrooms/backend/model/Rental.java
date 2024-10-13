package com.openclassrooms.backend.model;

import java.util.ArrayList;
import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "RENTALS")
@Data
public class Rental {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@OneToMany(mappedBy="rental")
	private ArrayList<Message> messages;

	@Column(name = "owner_id")
	private int owner_id;

	@Column(name = "name")
    private String name;

	@Column(name = "picture")
    private String picture;

	@Column(name = "description")
	private String description;

	@Column(name = "surface")
	private float surface;

	@Column(name = "price")
	private float price;

	@CreationTimestamp
	@Column(name = "created_at")
	private Date created_at;

	@UpdateTimestamp
	@Column(name = "updated_at")
	private Date updated_at;
}