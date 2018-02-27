package com.ingetis.ikheiry.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import com.ingetis.ikheiry.model.enumeartion.EtatNoteDeFrais;
import com.ingetis.ikheiry.model.enumeartion.TypeCarburantMoteur;

@Entity
@Table(name="FRAIS_VEHICULE_PERSONNEL")
@PrimaryKeyJoinColumn(name="ID_NOTE_FRAIS")
public class FraisVehiculePersonnel extends FraisVehiculeDeTransport {
	@Column(name="CARTE_GRISE_ENREGIST")
	private boolean isCarteGrise;
	
	@Column(name="TYPE_CARBURANT")
	private TypeCarburantMoteur type;
	
	@Column(name="PUISSANCE_FISCALE")
	private float puissanceFiscal;
	
	@Column(name="NBRE_KILOMETRAGE")
	private float nbreKilometrage;
	
	@Column(name="CARBURANT")
	private float carburantBrule;
	
	@Column(name="PRIX_LITRE")
	private float prixLitre;
	
	public FraisVehiculePersonnel() {
	}
	public FraisVehiculePersonnel(Date dateDemandeRemboursement, EtatNoteDeFrais etat, Salarie salarie,
			Date dateDeplacement, String lieuDepart, String lieuArrive) {
		super(dateDemandeRemboursement, etat, salarie, dateDeplacement, lieuDepart, lieuArrive);
	}
	public FraisVehiculePersonnel(Date dateDemandeRemboursement, EtatNoteDeFrais etat, Salarie salarie) {
		super(dateDemandeRemboursement, etat, salarie);
	}
	public FraisVehiculePersonnel(Date dateDemandeRemboursement, EtatNoteDeFrais etat, Salarie salarie,
			boolean isCarteGrise, TypeCarburantMoteur type, float puissanceFiscal, float nbreKilometrage,
			float carburantBrule, float prixLitre) {
		super(dateDemandeRemboursement, etat, salarie);
		this.isCarteGrise = isCarteGrise;
		this.type = type;
		this.puissanceFiscal = puissanceFiscal;
		this.nbreKilometrage = nbreKilometrage;
		this.carburantBrule = carburantBrule;
		this.prixLitre = prixLitre;
	}
	public FraisVehiculePersonnel(Date dateDemandeRemboursement, EtatNoteDeFrais etat, Salarie salarie,
			Date dateDeplacement, String lieuDepart, String lieuArrive, boolean isCarteGrise, TypeCarburantMoteur type,
			float puissanceFiscal, float nbreKilometrage, float carburantBrule, float prixLitre) {
		super(dateDemandeRemboursement, etat, salarie, dateDeplacement, lieuDepart, lieuArrive);
		this.isCarteGrise = isCarteGrise;
		this.type = type;
		this.puissanceFiscal = puissanceFiscal;
		this.nbreKilometrage = nbreKilometrage;
		this.carburantBrule = carburantBrule;
		this.prixLitre = prixLitre;
	}
	public boolean isCarteGrise() {
		return isCarteGrise;
	}
	public void setCarteGrise(boolean isCarteGrise) {
		this.isCarteGrise = isCarteGrise;
	}
	public TypeCarburantMoteur getType() {
		return type;
	}
	public void setType(TypeCarburantMoteur type) {
		this.type = type;
	}
	public float getPuissanceFiscal() {
		return puissanceFiscal;
	}
	public void setPuissanceFiscal(float puissanceFiscal) {
		this.puissanceFiscal = puissanceFiscal;
	}
	public float getNbreKilometrage() {
		return nbreKilometrage;
	}
	public void setNbreKilometrage(float nbreKilometrage) {
		this.nbreKilometrage = nbreKilometrage;
	}
	public float getCarburantBrule() {
		return carburantBrule;
	}
	public void setCarburantBrule(float carburantBrule) {
		this.carburantBrule = carburantBrule;
	}
	public float getPrixLitre() {
		return prixLitre;
	}
	public void setPrixLitre(float prixLitre) {
		this.prixLitre = prixLitre;
	}
	
	// fonction
		public String getImgURL(Long id){
			return "/img/car.png";
		}
}
