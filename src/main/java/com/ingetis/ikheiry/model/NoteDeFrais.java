package com.ingetis.ikheiry.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

import com.ingetis.ikheiry.model.enumeartion.EtatNoteDeFrais;

@Entity
@Table(name="NOTE_DE_FRAIS")
@Inheritance(strategy=InheritanceType.JOINED)
public abstract class NoteDeFrais {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID_NOTE_FRAIS")
	private Long id;
	@Temporal(TemporalType.DATE)
	@Column(name="DATE_DEMANDE_REMB")
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date dateDemandeRemboursement;
	@Enumerated(EnumType.STRING)
	@Column(name="ETAT_NOTE_FRAIS")
	private EtatNoteDeFrais etat;
	@ManyToOne
 	@JoinColumn(name="ID_SAL")
	private Salarie salarie;

	@Column(name="JUSTIF_URL_NOTE_FRAIS")
	private String justifUrl;
	
	public NoteDeFrais() {
	}
	public NoteDeFrais(Date dateDemandeRemboursement, EtatNoteDeFrais etat, Salarie salarie) {
		this.dateDemandeRemboursement = dateDemandeRemboursement;
		this.etat = etat;
		this.salarie = salarie;
	}
	public Date getDateDemandeRemboursement() {
		return dateDemandeRemboursement;
	}
	public void setDateDemandeRemboursement(Date dateDemandeRemboursement) {
		this.dateDemandeRemboursement = dateDemandeRemboursement;
	}
	public EtatNoteDeFrais getEtat() {
		return etat;
	}
	public void setEtat(EtatNoteDeFrais etat) {
		this.etat = etat;
	}
	public Salarie getSalarie() {
		return salarie;
	}
	
	public void setSalarie(Salarie salarie) {
		this.salarie = salarie;
	}
	public Long getId() {
		return id;
	}	
	public void setId(Long id) {
		this.id = id;
	}
	public String getJustifUrl() {
		return justifUrl;
	}
	public void setJustifUrl(String justifUrl) {
		this.justifUrl = justifUrl;
	}
	public abstract String getImgURL(Long id);
}
