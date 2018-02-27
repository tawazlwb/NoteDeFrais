package com.ingetis.ikheiry.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import com.ingetis.ikheiry.model.enumeartion.EtatNoteDeFrais;

@Entity
@Table(name="FRAIS_TAXI")
@PrimaryKeyJoinColumn(name="ID_NOTE_FRAIS")
public class FraisTaxi extends FraisVehiculeDeTransport{
	@Column(name="MONTANT")
	private float montant;

	public FraisTaxi() {
	}
	public FraisTaxi(Date dateDemandeRemboursement, EtatNoteDeFrais etat, Salarie salarie, Date dateDeplacement,
			String lieuDepart, String lieuArrive) {
		super(dateDemandeRemboursement, etat, salarie, dateDeplacement, lieuDepart, lieuArrive);
	}
	public FraisTaxi(Date dateDemandeRemboursement, EtatNoteDeFrais etat, Salarie salarie) {
		super(dateDemandeRemboursement, etat, salarie);
	}
	public FraisTaxi(Date dateDemandeRemboursement, EtatNoteDeFrais etat, Salarie salarie, float montant) {
		super(dateDemandeRemboursement, etat, salarie);
		this.montant = montant;
	}
	public FraisTaxi(Date dateDemandeRemboursement, EtatNoteDeFrais etat, Salarie salarie, Date dateDeplacement,
			String lieuDepart, String lieuArrive, float montant) {
		super(dateDemandeRemboursement, etat, salarie, dateDeplacement, lieuDepart, lieuArrive);
		this.montant = montant;
	}
	public float getMontant() {
		return montant;
	}
	public void setMontant(float montant) {
		this.montant = montant;
	}
	
	// fonction
	public String getImgURL(Long id){
		return "/img/taxi.png";
	}
}
