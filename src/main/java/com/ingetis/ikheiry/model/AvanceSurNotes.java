package com.ingetis.ikheiry.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.ingetis.ikheiry.model.enumeartion.EtatAvanceSurNotes;

@Entity
@Table(name="AVANCE_SUR_NOTE")
public class AvanceSurNotes {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID_AVANCE_SUR_NOTE")
	private Long id;
	@Temporal(TemporalType.DATE)
	@Column(name="DATE_AVANCE_SUR_NOTE")
	private Date dateAvance;
	@Column(name="MONTANT_AVANCE_SUR_NOTE")
	private float montant;
	@Column(name="ETAT_AVANCE_SUR_NOTE")
	private EtatAvanceSurNotes etat;
	
	@ManyToOne
	@JoinColumn(name="ID_SAL")
	private Salarie salarie;
	
	public AvanceSurNotes() {
	}
	public AvanceSurNotes(Date dateAvance, float montant, EtatAvanceSurNotes etat, Salarie salarie) {
		this.dateAvance = dateAvance;
		this.montant = montant;
		this.etat = etat;
		this.salarie = salarie;
	}
	public Long getId() {
		return id;
	}
	public Date getDateAvance() {
		return dateAvance;
	}
	public void setDateAvance(Date dateAvance) {
		this.dateAvance = dateAvance;
	}
	public float getMontant() {
		return montant;
	}
	public void setMontant(float montant) {
		this.montant = montant;
	}
	public EtatAvanceSurNotes getEtat() {
		return etat;
	}
	public void setEtat(EtatAvanceSurNotes etat) {
		this.etat = etat;
	}
	public Salarie getSalarie() {
		return salarie;
	}
	public void setSalarie(Salarie salarie) {
		this.salarie = salarie;
	}
}
