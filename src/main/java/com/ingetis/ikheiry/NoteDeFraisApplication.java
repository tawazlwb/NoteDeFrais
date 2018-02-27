package com.ingetis.ikheiry;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;
import org.apache.commons.io.IOUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import com.ingetis.ikheiry.dao.ISalarieRepository;
import com.ingetis.ikheiry.model.Salarie;
import com.ingetis.ikheiry.model.enumeartion.Sexe;
import com.ingetis.ikheiry.model.enumeartion.TypeSalarie;

@SpringBootApplication
public class NoteDeFraisApplication {
	
	public static void main(String[] args) throws IOException {
		//SpringApplication.run(NoteDeFraisApplication.class, args);
		
		// Test preparation
		ApplicationContext ctx = SpringApplication.run(NoteDeFraisApplication.class, args);
		ISalarieRepository salarieRepository = ctx.getBean(ISalarieRepository.class);
		
		Salarie employe = salarieRepository.findByLogin("ikheiry");
		Salarie rh = salarieRepository.findByLogin("sguerfi");
		
		if(employe == null){
			salarieRepository.save(new Salarie("KHEIRY", "Ismail", Sexe.MASCULIN, new Date(), "16 rue du Stand, 90400 Danjoutin", "+33 (0)6 78 08 17 84", new Date(), "Développeur", 950, 192129956802191L, "ikheiry", "123", TypeSalarie.EMPLOYE));
		}
		if(rh == null){
			salarieRepository.save(new Salarie("GUERFI", "Souhila", Sexe.MASCULIN, new Date(), "128 allée des Champs Elysées, Immeuble l’Européen, 91042 Evry Cedex", "+33 (0)1 60 79 18 81", new Date(), "Enseignante", 2500, 196129956802192L, "sguerfi", "123", TypeSalarie.RH));
		}
		
		String justifFolder = ctx.getEnvironment().getProperty("justif.folder");
		
		File file = new File(justifFolder + "vide1.png");
		if(!file.exists()){
			File f = new File("src/main/resources/static/img/vide.png");
			FileInputStream fileInputStream = new FileInputStream(f);
			MultipartFile multipartFile = new MockMultipartFile("file", "vide.png", "text/plain",  IOUtils.toByteArray(fileInputStream));
			fileInputStream.close();
			multipartFile.transferTo(new File(justifFolder + multipartFile.getOriginalFilename()));
		}
		
		//ApplicationContext ctx = SpringApplication.run(NoteDeFraisApplication.class, args);
		//INoteDeFraisRepository noteDeFraisRepository = ctx.getBean(INoteDeFraisRepository.class);
		//ISalarieRepository salarieRepository = ctx.getBean(ISalarieRepository.class);
		//IClientRepository clientRepository = ctx.getBean(IClientRepository.class);
		
		//noteDeFraisRepository.save(new FraisHebergement(new Date(), EtatNoteDeFrais.EN_COURS, new Salarie(3L), "Kyriad 1", 3, "128 allée", new Date(), 3, 80));
		//noteDeFraisRepository.save(new FraisHebergement(new Date(), EtatNoteDeFrais.MANQUE_DE_JUSTIFICATIF, new Salarie(4L), "Kyriad 2", 3, "128 allée", new Date(), 7, 75));
		
		//noteDeFraisRepository.save(new FraisDejeunerAffaire(new Date(), EtatNoteDeFrais.REMBOUSSEE, new Salarie(1L), "poulet mlaha", "evry courcouronne", new Date(), 75.75f));
		//noteDeFraisRepository.save(new FraisDejeunerAffaire(new Date(), EtatNoteDeFrais.REMBOUSSEE, new Salarie(1L), "burger king", "evry centre 2", new Date(), 75.75f));
		//noteDeFraisRepository.save(new FraisTaxi(new Date(), EtatNoteDeFrais.REMBOUSSEE, new Salarie(1L), new Date(), "Paris", "Evry", 69.50f));
		//noteDeFraisRepository.save(new FraisTaxi(new Date(), EtatNoteDeFrais.EN_COURS, new Salarie(1L), new Date(), "Paris", "Evry", 100.50f));
		//noteDeFraisRepository.save(new FraisTaxi(new Date(), EtatNoteDeFrais.REFUSEE, new Salarie(1L), new Date(), "Paris", "Evry", 150.50f));
		//noteDeFraisRepository.save(new FraisVehiculePersonnel(new Date(), etat, salarie, dateDeplacement, lieuDepart, lieuArrive, isCarteGrise, type, puissanceFiscal, nbreKilometrage, carburantBrule, prixLitre))
		//noteDeFraisRepository.save(new FraisVehiculePersonnel(new Date(), EtatNoteDeFrais.EN_COURS, new Salarie(1L), new Date(), "Paris", "Evry", true, TypeCarburantMoteur.DIESEL, 2f, 50f, 5f, 1.2f));
		//noteDeFraisRepository.save(new FraisVehiculePersonnel(new Date(), EtatNoteDeFrais.REFUSEE, new Salarie(1L), new Date(), "Saint-lazart", "corbeil essone", true, TypeCarburantMoteur.DIESEL, 2f, 60.5f, 5f, 1.2f));
		//noteDeFraisRepository.save(new FraisVehiculePersonnel(new Date(), EtatNoteDeFrais.MANQUE_DE_JUSTIFICATIF, new Salarie(1L), new Date(), "Evry", "Pontoise", true, TypeCarburantMoteur.DIESEL, 2f, 69f, 5f, 1.2f));
		
		/*FraisVehiculePersonnel n = new FraisVehiculePersonnel(new Date(), EtatNoteDeFrais.REMBOUSSEE, new Salarie(1L), new Date(), "Evry", "ermont- aubone", true, TypeCarburantMoteur.DIESEL, 2f, 69f, 5f, 1.2f);
		List<Client> cls = new ArrayList<>();
		cls.add(clientRepository.save(new Client("med", TypeClient.PERSONNE, "blabla")));
		cls.add(clientRepository.save(new Client("INGETIS", TypeClient.ENTREPRISE, "blabla")));
		n.setClientsSocietes(cls);
		System.out.println(n.getClientsSocietes().get(0).getId());
		*/
		//noteDeFraisRepository.save(n);
		
		
		//salarieRepository.getOne(1L);
		
		
		//List<NoteDeFrais> notes = noteDeFraisRepository.findBySalarieIdOrderByIdDesc(1L, new PageRequest(0, 5)).getContent();
		//List<NoteDeFrais> notes = noteDeFraisRepository.findBySalarieIdAndEtatLikeOrderByIdDesc(1L, EtatNoteDeFrais.MANQUE_DE_JUSTIFICATIF, new PageRequest(0, 5)).getContent();
		/*List<NoteDeFrais> notes = noteDeFraisRepository.findByEtatLikeOrderByIdDesc(EtatNoteDeFrais.EN_COURS, new PageRequest(0, 5)).getContent();
		
		for(NoteDeFrais note : notes){
			System.out.println("noteID: " + note.getId() + ", noteEtat: " + note.getEtat() + ", date crea: " + note.getDateDemandeRemboursement() + ", salarieID: " + note.getSalarie().getId() + ", SalarieName: " + note.getSalarie().getNom());
		}
		System.out.println("size :" + notes.size());*/
		
	}
}
