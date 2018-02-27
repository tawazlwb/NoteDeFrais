package com.ingetis.ikheiry.model;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import com.ingetis.ikheiry.model.enumeartion.Sexe;
import com.ingetis.ikheiry.model.enumeartion.TypeSalarie;

@SuppressWarnings("serial")
@Entity
@Table(name="SALARIE")
public class Salarie implements Serializable {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID_SAL")
	private Long id;
	@Column(name="NOM")
	@Size(min=2, max=20)
	@NotEmpty
	private String nom;
	@Column(name="PRENOM")
	@NotEmpty
	@Size(min=2, max=20)
	private String prenom;
	@Column(name="SEXE")
	@Enumerated(EnumType.STRING)
	@NotNull
	private Sexe sexe;
	@Temporal(TemporalType.DATE)
	@Column(name="DATE_NAISSANCE")
	@DateTimeFormat(pattern="yyyy-MM-dd")
	@NotNull
	private Date dateNaissance;
	@Column(name="ADRESSE")
	@NotNull
	private String adresse;
	@Column(name="NUMERO_TEL")
	@NotEmpty
	private String numeroTel;
	@Temporal(TemporalType.DATE)
	@Column(name="DATE_EMBAUCHE")
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date dateEmbauche;
	@Column(name="POSTE")
	@NotEmpty
	private String poste;
	@Column(name="SALAIRE", length=30)
	@Min(value=100)
	private float salaire;
	@Column(name="NUM_SECU_SOC")
	@NotNull
	private Long numeroSecuriteSociale;
	
	@Column(name="LOGIN", unique=true)
	@NotEmpty
	private String login;
	@Column(name="MOT_DE_PASS")
	@NotEmpty
	private String motDePasse;
	
	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name="TYPE_SAL")
	private TypeSalarie type;
	
	@OneToMany(mappedBy="salarie")
	private List<AvanceSurNotes> avancesSurNotes;
	@OneToMany(mappedBy="salarie")
	private List<NoteDeFrais> notesDeFrais;
	
	public Salarie() {
	}
		
	public Salarie(Long id) {
		this.id = id;
	}

	public Salarie(String nom, String prenom, Sexe sexe, Date dateNaissance, String adresse, String numeroTel,
			Date dateEmbauche, String poste, float salaire, Long numeroSecuriteSociale, String login, String motDePasse,
			TypeSalarie type) {
		super();
		this.nom = nom;
		this.prenom = prenom;
		this.sexe = sexe;
		this.dateNaissance = dateNaissance;
		this.adresse = adresse;
		this.numeroTel = numeroTel;
		this.dateEmbauche = dateEmbauche;
		this.poste = poste;
		this.salaire = salaire;
		this.numeroSecuriteSociale = numeroSecuriteSociale;
		this.login = login;
		this.motDePasse = motDePasse;
		this.type = type;
	}
	
	public void update(Salarie s, String mot){
		this.nom = s.getNom();
		this.prenom = s.getPrenom();
		this.sexe = s.getSexe();
		this.dateNaissance = s.getDateNaissance();
		this.adresse = s.getAdresse();
		this.numeroTel = s.getNumeroTel();
		this.dateEmbauche = s.getDateEmbauche();
		this.poste = s.getPoste();
		this.salaire = s.getSalaire();
		this.numeroSecuriteSociale = s.getNumeroSecuriteSociale();
		this.login = s.getLogin();
		if(!mot.isEmpty())
			s.setMotDePasse(mot);
		this.type = s.getType();
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public Sexe getSexe() {
		return sexe;
	}

	public void setSexe(Sexe sexe) {
		this.sexe = sexe;
	}

	public Date getDateNaissance() {
		return dateNaissance;
	}

	public void setDateNaissance(Date dateNaissance) {
		this.dateNaissance = dateNaissance;
	}

	public String getAdresse() {
		return adresse;
	}

	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}

	public String getNumeroTel() {
		return numeroTel;
	}

	public void setNumeroTel(String numeroTel) {
		this.numeroTel = numeroTel;
	}

	public Date getDateEmbauche() {
		return dateEmbauche;
	}

	public void setDateEmbauche(Date dateEmbauche) {
		this.dateEmbauche = dateEmbauche;
	}

	public String getPoste() {
		return poste;
	}

	public void setPoste(String poste) {
		this.poste = poste;
	}

	public float getSalaire() {
		return salaire;
	}

	public void setSalaire(float salaire) {
		this.salaire = salaire;
	}

	public Long getNumeroSecuriteSociale() {
		return numeroSecuriteSociale;
	}

	public void setNumeroSecuriteSociale(Long numeroSecuriteSociale) {
		this.numeroSecuriteSociale = numeroSecuriteSociale;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getMotDePasse() {
		return motDePasse;
	}

	public void setMotDePasse(String motDePasse) {
		this.motDePasse = motDePasse;
	}

	public TypeSalarie getType() {
		return type;
	}

	public void setType(TypeSalarie type) {
		this.type = type;
	}

	public List<AvanceSurNotes> getAvancesSurNotes() {
		return avancesSurNotes;
	}

	public void setAvancesSurNotes(List<AvanceSurNotes> avancesSurNotes) {
		this.avancesSurNotes = avancesSurNotes;
	}

	public List<NoteDeFrais> getNotesDeFrais() {
		return notesDeFrais;
	}

	public void setNotesDeFrais(List<NoteDeFrais> notesDeFrais) {
		this.notesDeFrais = notesDeFrais;
	}

	@Override
	public String toString() {
		return "Salarie [nom=" + nom + ", prenom=" + prenom + ", sexe=" + sexe + ", dateNaissance=" + dateNaissance
				+ ", adresse=" + adresse + ", numeroTel=" + numeroTel + ", dateEmbauche=" + dateEmbauche + ", poste="
				+ poste + ", salaire=" + salaire + ", numeroSecuriteSociale=" + numeroSecuriteSociale + ", login="
				+ login + ", motDePasse=" + motDePasse + ", type=" + type + ", avancesSurNotes=" + avancesSurNotes
				+ ", notesDeFrais=" + notesDeFrais + "]";
	}
	
	
	// note de frais - abus
		public boolean verifierDateSemaine(Date debutSem, Date d) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(debutSem);
			cal.add(Calendar.DATE, 6);
			Date finSem = cal.getTime();
			return debutSem.compareTo(d) * d.compareTo(finSem) >= 0;
		}
		
		public static boolean verifierDateMois(Date debutMois, Date d) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(debutMois);
			int nbreJourDuMois = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
			SimpleDateFormat formatterJour = new SimpleDateFormat("dd");
			String jourActuel = formatterJour.format(debutMois);
			cal.add(Calendar.DATE, nbreJourDuMois - Integer.valueOf(jourActuel));
			Date finMois = cal.getTime();
			return debutMois.compareTo(d) * d.compareTo(finMois) >= 0;
		}
		
		public int calculNbreDejeunerParSemaine(List<NoteDeFrais> notes, Date debutSem) {
			int nbre = 0;
			for(int i=0; i<notes.size(); i++) {
				if(notes.get(i).getClass().getSimpleName().equals(FraisDejeunerAffaire.class.getSimpleName())) {
					FraisDejeunerAffaire noteDej = (FraisDejeunerAffaire) notes.get(i); 
				    if(verifierDateSemaine(debutSem, noteDej.getDateDejeuner())) {
				    	nbre++;
				    }
				}
			}
			return nbre;
		}
		
		public int calculNbreDejeunerParMois(List<NoteDeFrais> notes, Date debutMois) {
			int nbre = 0;
			for(int i=0; i<notes.size(); i++) {
				if(notes.get(i).getClass().getSimpleName().equals(FraisDejeunerAffaire.class.getSimpleName())) {
					FraisDejeunerAffaire noteDej = (FraisDejeunerAffaire) notes.get(i); 
				    if(verifierDateMois(debutMois, noteDej.getDateDejeuner())) {
				    	nbre++;
				    }
				}
			}
			return nbre;
		}
		
		public Date getDebutSemaine(Date date) throws ParseException{
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			
			int day = cal.get(Calendar.DAY_OF_WEEK);
			if(day == 1){
				return date;
			}
			else{
				day -= 1;
				cal.add(Calendar.DATE, -day);
				return cal.getTime();
			}
		}
		
		public Date getDebutMois(Date date) throws ParseException{
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			
			int day = cal.get(Calendar.DAY_OF_MONTH);
			if(day == 1){
				return date;
			}
			else{
				day -= 1;
				cal.add(Calendar.DATE, -day);
				return cal.getTime();
			}
		}
		
		public Date getFinMois(Date d) throws ParseException {
			Date debutMois = getDebutMois(d);
			Calendar cal = Calendar.getInstance();
			cal.setTime(debutMois);
			int nbreJourDuMois = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
			SimpleDateFormat formatterJour = new SimpleDateFormat("dd");
			String jourActuel = formatterJour.format(debutMois);
			cal.add(Calendar.DATE, nbreJourDuMois - Integer.valueOf(jourActuel));
			return cal.getTime();
		}
		
		public List<Date> getDebutSemainesDuMois(Date date) throws ParseException{
			Calendar cal = Calendar.getInstance();
			cal.setTime(getFinMois(date));
			int nbre = cal.get(Calendar.DAY_OF_WEEK_IN_MONTH);
			List<Date> dates = new ArrayList<>();
			for(int i=0; i<nbre; ++i){
				Date d = getDebutSemaine(cal.getTime());
				cal.setTime(d);
				cal.add(Calendar.DATE, -7);
				dates.add(d);
			}
			return dates;
		}
		
		public List<NoteDeFrais> getDejeunersMois(List<NoteDeFrais> notes) throws ParseException{
			if(notes.size() != 0){
				Date d = ((FraisDejeunerAffaire) notes.get(0)).getDateDejeuner();
				List<NoteDeFrais> nts = new ArrayList<>();
				Date debutMois = getDebutMois(d);
				
				for(int i=0; i<notes.size(); ++i){
					if(verifierDateMois(debutMois, ((FraisDejeunerAffaire) notes.get(i)).getDateDejeuner())){
						nts.add(notes.get(i));
					}
				}
				return nts;
			}	
			return null;
		}
		
		public List<NoteDeFrais> getDejeunersHorsMois(List<NoteDeFrais> notes, List<NoteDeFrais> aEnlever) throws ParseException{
			List<NoteDeFrais> nts = new ArrayList<>();
			for(int i=0; i<notes.size(); ++i){
				int nbre = 0;
				for(int j=0; j<aEnlever.size(); ++j){
					if(((FraisDejeunerAffaire) notes.get(i)).getId() == ((FraisDejeunerAffaire) aEnlever.get(j)).getId()){
						++nbre;
					}
				}
				if(nbre == 0){
					nts.add(notes.get(i));
				}
				nbre = 0;
			}
			if(nts.size() != 0)
				return nts;
			return null;
		}
		
		public List<List<NoteDeFrais>> getAllDejeunersMois(List<NoteDeFrais> notes) throws ParseException{
			if(notes.size() != 0){
				List<List<NoteDeFrais>> ntsListe = new ArrayList<>();
				List<NoteDeFrais> nts = getDejeunersMois(notes);
				List<NoteDeFrais> newNts = getDejeunersHorsMois(notes, nts);
				System.out.println(newNts);
				ntsListe.add(nts);
				while(newNts != null){
					nts = getDejeunersMois(newNts);
					newNts = getDejeunersHorsMois(newNts, nts);
					ntsListe.add(nts);
				}
				return ntsListe;
			}	
			return null;
		}
		
		public int nbreDejeunerParMois(List<NoteDeFrais> notes) throws ParseException {
			Date debutMois = getDebutMois(((FraisDejeunerAffaire) notes.get(0)).getDateDejeuner());
			int nbre = 0;
			for(int i=0; i<notes.size(); i++) {
				if(notes.get(i).getClass().getSimpleName().equals(FraisDejeunerAffaire.class.getSimpleName())) {
					FraisDejeunerAffaire noteDej = (FraisDejeunerAffaire) notes.get(i); 
				    if(verifierDateMois(debutMois, noteDej.getDateDejeuner())) {
				    	nbre++;
				    }
				}
			}
			return nbre;
		}
		
}
