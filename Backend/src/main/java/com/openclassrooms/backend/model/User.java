package com.openclassrooms.backend.model;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "USERS")
@Data
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@Column(name = "name")
	private String name;

	@Column(name = "password")
	private String password;

	@Column(name = "email")
    private String email;

	@CreationTimestamp
	@Column(name = "created_at")
	private LocalDateTime created_at;

	@UpdateTimestamp
	@Column(name = "updated_at")
	private LocalDateTime updated_at;
}