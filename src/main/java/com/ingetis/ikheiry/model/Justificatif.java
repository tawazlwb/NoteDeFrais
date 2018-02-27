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

import com.ingetis.ikheiry.model.enumeartion.EtatJustificatif;
import com.ingetis.ikheiry.model.enumeartion.TypeJustificatif;

@Entity
@Table(name="JUSTIFICATIF")
public class Justificatif {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID_JUSTIF")
	private Long id;
	@Temporal(TemporalType.DATE)
	@Column(name="DATE_REMISE_JUSTIF")
	private Date dateRemise;
	@Column(name="ETAT_JUSTIF")
	private EtatJustificatif etat;
	@Column(name="TYPE_JUSTIF")
	private TypeJustificatif type;
	@Column(name="URL_JUSTIF")
	private String url;
	
	public Justificatif() {
	}
	public Justificatif(Date dateRemise, EtatJustificatif etat, TypeJustificatif type, String url) {
		this.dateRemise = dateRemise;
		this.etat = etat;
		this.type = type;
		this.url = url;
	}
	public Date getDateRemise() {
		return dateRemise;
	}
	public void setDateRemise(Date dateRemise) {
		this.dateRemise = dateRemise;
	}
	public EtatJustificatif getEtat() {
		return etat;
	}
	public void setEtat(EtatJustificatif etat) {
		this.etat = etat;
	}
	public TypeJustificatif getType() {
		return type;
	}
	public void setType(TypeJustificatif type) {
		this.type = type;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Long getId() {
		return id;
	}
}
