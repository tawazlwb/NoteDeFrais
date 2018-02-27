package com.ingetis.ikheiry.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.ingetis.ikheiry.model.enumeartion.TypeClient;

@Entity
@Table(name="CLIENT")
public class Client {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID_CLI")
	private Long id;
	@Column(name="NOM")
	private String nom;
	@Column(name="TYPE")
	@Enumerated(EnumType.STRING)
	private TypeClient type;
	@Column(name="DETAILS")
	private String details;
	
	public Client() {
	}
	public Client(String nom, TypeClient type, String details) {
		this.nom = nom;
		this.type = type;
		this.details = details;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public TypeClient getType() {
		return type;
	}
	public void setType(TypeClient type) {
		this.type = type;
	}
	public String getDetails() {
		return details;
	}
	public void setDetails(String details) {
		this.details = details;
	}
	public Long getId() {
		return id;
	}
}
