package com.ingetis.ikheiry.model;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name="ENTREPRISE")
public class Entreprise {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID_ENTR")
	private Long id;
	
	@Column(name="NOM")
	private String nom;
	
	@Column(name="NUM_SIRET")
	private int numeroSiret;
	
	@Temporal(TemporalType.DATE)
	@Column(name="DATE_CREATION")
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date dateCreation;
	
	@Column(name="DIRECTEUR")
	private String directeurCEO;
	
	@Column(name="ADRESSE")
	private String adresse;
	
	@Column(name="CHIFFRE_AFFAIRE")
	private double chiffreAffaire;
	
	public Entreprise() {		
	}
	public Entreprise(String nom, int numeroSiret, Date dateCreation, String directeurCEO, String adresse,
			double chiffreAffaire) {
		this.nom = nom;
		this.numeroSiret = numeroSiret;
		this.dateCreation = dateCreation;
		this.directeurCEO = directeurCEO;
		this.adresse = adresse;
		this.chiffreAffaire = chiffreAffaire;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public int getNumeroSiret() {
		return numeroSiret;
	}
	public void setNumeroSiret(int numeroSiret) {
		this.numeroSiret = numeroSiret;
	}
	public Date getDateCreation() {
		return dateCreation;
	}
	public void setDateCreation(Date dateCreation) {
		this.dateCreation = dateCreation;
	}
	public String getDirecteurCEO() {
		return directeurCEO;
	}
	public void setDirecteurCEO(String directeurCEO) {
		this.directeurCEO = directeurCEO;
	}
	public String getAdresse() {
		return adresse;
	}
	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}
	public double getChiffreAffaire() {
		return chiffreAffaire;
	}
	public void setChiffreAffaire(double chiffreAffaire) {
		this.chiffreAffaire = chiffreAffaire;
	}
	
	public Long getId() {
		return id;
	}
}
