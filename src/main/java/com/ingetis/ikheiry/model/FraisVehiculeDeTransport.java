package com.ingetis.ikheiry.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

import com.ingetis.ikheiry.model.enumeartion.EtatNoteDeFrais;

@Entity
@Table(name="FRAIS_VEHICULE_TRANSPORT")
@Inheritance(strategy=InheritanceType.JOINED)
@PrimaryKeyJoinColumn(name="ID_NOTE_FRAIS")
public abstract class FraisVehiculeDeTransport extends NoteDeFrais {
	@Temporal(TemporalType.DATE)
	@Column(name="DATE_DEPLACEMENT")
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date dateDeplacement;
	
	@Column(name="DEPART")
	private String LieuDepart;
	
	@Column(name="ARRIVE")
	private String LieuArrive;
	
	@ManyToMany
	@JoinTable(
			name="FRAIS_TRANSPORT_CLIENT",
			joinColumns=@JoinColumn(name="ID_NOTE_FRAIS", referencedColumnName="ID_NOTE_FRAIS"),
			inverseJoinColumns=@JoinColumn(name="ID_CLI", referencedColumnName="ID_CLI"))
	private List<Client> clientsSocietes;

	public FraisVehiculeDeTransport() {
	}
	public FraisVehiculeDeTransport(Date dateDemandeRemboursement, EtatNoteDeFrais etat, Salarie salarie) {
		super(dateDemandeRemboursement, etat, salarie);
	}
	public FraisVehiculeDeTransport(Date dateDemandeRemboursement, EtatNoteDeFrais etat, Salarie salarie,
			Date dateDeplacement, String lieuDepart, String lieuArrive) {
		super(dateDemandeRemboursement, etat, salarie);
		this.dateDeplacement = dateDeplacement;
		LieuDepart = lieuDepart;
		LieuArrive = lieuArrive;
	}
	public Date getDateDeplacement() {
		return dateDeplacement;
	}
	public void setDateDeplacement(Date dateDeplacement) {
		this.dateDeplacement = dateDeplacement;
	}
	public String getLieuDepart() {
		return LieuDepart;
	}
	public void setLieuDepart(String lieuDepart) {
		LieuDepart = lieuDepart;
	}
	public String getLieuArrive() {
		return LieuArrive;
	}
	public void setLieuArrive(String lieuArrive) {
		LieuArrive = lieuArrive;
	}
	public List<Client> getClientsSocietes() {
		return clientsSocietes;
	}
	public void setClientsSocietes(List<Client> clientsSocietes) {
		this.clientsSocietes = clientsSocietes;
	}
}
