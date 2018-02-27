package com.ingetis.ikheiry.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import com.ingetis.ikheiry.model.enumeartion.EtatNoteDeFrais;
import com.ingetis.ikheiry.model.enumeartion.TypeAutreNote;

@Entity
@Table(name="AUTRE_NOTE")
@PrimaryKeyJoinColumn(name="ID_NOTE_FRAIS")
public class AutreNote extends NoteDeFrais {
	@Column(name="TYPE")
	private TypeAutreNote type;
	@Column(name="DETAILS")
	private String details;
	@Column(name="MONTANT")
	private float montant;
	
	public AutreNote() {
	}
	public AutreNote(Date dateDemandeRemboursement, EtatNoteDeFrais etat, Salarie salarie) {
		super(dateDemandeRemboursement, etat, salarie);
	}
	public AutreNote(Date dateDemandeRemboursement, EtatNoteDeFrais etat, Salarie salarie, TypeAutreNote type,
			String details, float montant) {
		super(dateDemandeRemboursement, etat, salarie);
		this.type = type;
		this.details = details;
		this.montant = montant;
	}
	public TypeAutreNote getType() {
		return type;
	}
	public void setType(TypeAutreNote type) {
		this.type = type;
	}
	public String getDetails() {
		return details;
	}
	public void setDetails(String details) {
		this.details = details;
	}
	public float getMontant() {
		return montant;
	}
	public void setMontant(float montant) {
		this.montant = montant;
	}
	
	// fonction
		public String getImgURL(Long id){
			return "/img/car.png"; // autrepng
		}
}
