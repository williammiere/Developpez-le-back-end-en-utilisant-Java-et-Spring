package com.openclassrooms.backend.model;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "MESSAGES")
@Data
public class Message {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@ManyToOne
	@JoinColumn(name = "rental_id")
	private Rental rental_id;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user_id;

	@Column(name = "message")
	private String message;

	@CreationTimestamp
	@Column(name = "created_at")
	private LocalDateTime created_at;

	@UpdateTimestamp
	@Column(name = "updated_at")
	private LocalDateTime updated_at;
}