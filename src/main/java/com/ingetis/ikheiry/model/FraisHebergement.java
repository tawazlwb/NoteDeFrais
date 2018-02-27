package com.ingetis.ikheiry.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import com.ingetis.ikheiry.model.enumeartion.EtatNoteDeFrais;

@Entity
@Table(name="FRAIS_HEBERGEMENT")
@PrimaryKeyJoinColumn(name="ID_NOTE_FRAIS")
public class FraisHebergement extends NoteDeFrais {
	@Column(name="NOM_HOTEL")
	@Size(min=3, message="Le nom de l'hotel doit contenir au moins 3 caractères")
	private String nomHotel;
	
	@Column(name="NBRE_ETOILES_HOTEL")
	private int nombreEtoiles;
	
	@Column(name="ADRESSE_HOTEL")
	private String adresse;
	
	@Temporal(TemporalType.DATE)
	@Column(name="DATE_HEBERG")
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date dateHebergement;
	
	@Column(name="NBRE_NUIT_JOUR_HEBERG")
	@Min(value=1, message="Le nombre de Nuits Ou de Jours doit être supérieur à 0")
	private float nombreDeNuitOuJour;
	
	@Column(name="PRIX_NUIT_JOUR_HEBERG")
	@Min(value=1, message="Le prix d'une Nuit ou d'une Journée à l'hotel ne doit pas être égale à 0")
	private float prixDeNuitOuJour;
	
	public FraisHebergement() {
		super();
	}
	public FraisHebergement(Date dateDemandeRemboursement, EtatNoteDeFrais etat, Salarie salarie) {
		super(dateDemandeRemboursement, etat, salarie);
	}
	public FraisHebergement(Date dateDemandeRemboursement, EtatNoteDeFrais etat, Salarie salarie, String nomHotel,
			int nombreEtoiles, String adresse, Date dateHebergement, float nombreDeNuitOuJour, float prixDeNuitOuJour) {
		super(dateDemandeRemboursement, etat, salarie);
		this.nomHotel = nomHotel;
		this.nombreEtoiles = nombreEtoiles;
		this.adresse = adresse;
		this.dateHebergement = dateHebergement;
		this.nombreDeNuitOuJour = nombreDeNuitOuJour;
		this.prixDeNuitOuJour = prixDeNuitOuJour;
	}
	public String getNomHotel() {
		return nomHotel;
	}
	public void setNomHotel(String nomHotel) {
		this.nomHotel = nomHotel;
	}
	public int getNombreEtoiles() {
		return nombreEtoiles;
	}
	public void setNombreEtoiles(int nombreEtoiles) {
		this.nombreEtoiles = nombreEtoiles;
	}
	public String getAdresse() {
		return adresse;
	}
	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}
	public Date getDateHebergement() {
		return dateHebergement;
	}
	public void setDateHebergement(Date dateHebergement) {
		this.dateHebergement = dateHebergement;
	}
	public float getNombreDeNuitOuJour() {
		return nombreDeNuitOuJour;
	}
	public void setNombreDeNuitOuJour(float nombreDeNuitOuJour) {
		this.nombreDeNuitOuJour = nombreDeNuitOuJour;
	}
	public float getPrixDeNuitOuJour() {
		return prixDeNuitOuJour;
	}
	public void setPrixDeNuitOuJour(float prixDeNuitOuJour) {
		this.prixDeNuitOuJour = prixDeNuitOuJour;
	}
	
	// fonction
	public String getImgURL(Long id){
		return "/img/hotel.png";
	}
}
