package com.ingetis.ikheiry.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.Min;

import org.springframework.format.annotation.DateTimeFormat;

import com.ingetis.ikheiry.model.enumeartion.EtatNoteDeFrais;

@Entity
@Table(name="FRAIS_DEJEUNER_AFFAIRE")
@PrimaryKeyJoinColumn(name="ID_NOTE_FRAIS")
public class FraisDejeunerAffaire extends NoteDeFrais{
	@Column(name="NOM_RESTAU")
	private String nomRestaurant;
	
	@Column(name="ADRESSE_RESTAU")
	private String adresse;
	
	@Column(name="DATE_DEJEUNER")
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date dateDejeuner;
	
	@Column(name="ADDITION")
	@Min(value=1, message="Le montant ne doit pas être égale à 0")
	private float montant;
	
	@ManyToMany
	@JoinTable(
			name="FRAIS_DEJ_CLIENT",
			joinColumns=@JoinColumn(name="ID_NOTE_FRAIS", referencedColumnName="ID_NOTE_FRAIS"),
			inverseJoinColumns=@JoinColumn(name="ID_CLI", referencedColumnName="ID_CLI"))
	private List<Client> clientsSocietes;

	public FraisDejeunerAffaire() {
	}
	public FraisDejeunerAffaire(Date dateDemandeRemboursement, EtatNoteDeFrais etat, Salarie salarie) {
		super(dateDemandeRemboursement, etat, salarie);
	}
	public FraisDejeunerAffaire(Date dateDemandeRemboursement, EtatNoteDeFrais etat, Salarie salarie,
			String nomRestaurant, String adresse, Date dateDejeuner, float montant) {
		super(dateDemandeRemboursement, etat, salarie);
		this.nomRestaurant = nomRestaurant;
		this.adresse = adresse;
		this.dateDejeuner = dateDejeuner;
		this.montant = montant;
	}
	public String getNomRestaurant() {
		return nomRestaurant;
	}
	public void setNomRestaurant(String nomRestaurant) {
		this.nomRestaurant = nomRestaurant;
	}
	public String getAdresse() {
		return adresse;
	}
	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}
	public Date getDateDejeuner() {
		return dateDejeuner;
	}
	public void setDateDejeuner(Date dateDejeuner) {
		this.dateDejeuner = dateDejeuner;
	}
	public float getMontant() {
		return montant;
	}
	public void setMontant(float montant) {
		this.montant = montant;
	}
	public List<Client> getClientsSocietes() {
		return clientsSocietes;
	}
	public void setClientsSocietes(List<Client> clientsSocietes) {
		this.clientsSocietes = clientsSocietes;
	}
	
	// fonction
	public String getImgURL(Long id){
		return "/img/restaurant.png";
	}
}
