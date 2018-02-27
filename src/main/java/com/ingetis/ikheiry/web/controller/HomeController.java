package com.ingetis.ikheiry.web.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.ingetis.ikheiry.dao.IClientRepository;
import com.ingetis.ikheiry.dao.INoteDeFraisRepository;
import com.ingetis.ikheiry.dao.ISalarieRepository;
import com.ingetis.ikheiry.model.Client;
import com.ingetis.ikheiry.model.FraisDejeunerAffaire;
import com.ingetis.ikheiry.model.FraisHebergement;
import com.ingetis.ikheiry.model.FraisTaxi;
import com.ingetis.ikheiry.model.FraisVehiculePersonnel;
import com.ingetis.ikheiry.model.NoteDeFrais;
import com.ingetis.ikheiry.model.Salarie;
import com.ingetis.ikheiry.model.enumeartion.EtatNoteDeFrais;
import com.ingetis.ikheiry.model.enumeartion.TypeClient;
import com.ingetis.ikheiry.model.enumeartion.TypeSalarie;
import com.ingetis.ikheiry.web.session.ConnexionHandler;

@Controller
@RequestMapping(value="/")
public class HomeController {
	
	@Autowired
	private ISalarieRepository salarieRepository;
	
	@Autowired
	private INoteDeFraisRepository noteDeFraisRepository;
	
	@Autowired
	private IClientRepository clientRepository; 
	
	@Value("${justif.folder}")
	private String justifFolder;
	
	@RequestMapping("/")
	public String verifySession(HttpSession session){
		if(!SessionController.isSession(session))
			return "redirect:Login";
		return "redirect:Home";
	}
	
	@RequestMapping(value="/Login")
	public String login(Model model, HttpSession session){
		if(!SessionController.isSession(session)){
			model.addAttribute("agent", new ConnexionHandler());
			return "login";
		}
		return "redirect:Home";
	}
	
	@RequestMapping(value="/Connexion", method=RequestMethod.GET)
	public String connexionGET(){
		return "redirect:Login";
	}
	
	@RequestMapping(value="/Connexion", method=RequestMethod.POST)
	public String connexionPOST(ConnexionHandler agent, HttpSession session){
		Salarie salarie = salarieRepository.loginSalarie(agent.getLogin(), agent.getMotDePasse());
		if(salarie != null){
			agent.setFonction(salarie.getType());
			session.setAttribute("agent", agent);
			return "redirect:Home";
		}
		return "redirect:Login";
	}
	
	@RequestMapping(value="/Home")
	public String home(@RequestParam(name="pageArchive", defaultValue="1") int pageArchive,
			@RequestParam(name="sizeArchive", defaultValue="7") int sizeArchive,
			@RequestParam(name="pagePasEncoreValide", defaultValue="1") int pagePasEncoreValide,
			@RequestParam(name="sizePasEncoreValide", defaultValue="7") int sizePasEncoreValide,
			Model model, HttpSession session){
		if(!SessionController.isSession(session))
			return "redirect:Login";
		
		Salarie salarie = salarieRepository.findByLogin(((ConnexionHandler) session.getAttribute("agent")).getLogin());
		Page<NoteDeFrais> notesArchive;
		Page<NoteDeFrais> notesPasEncoreValide;
		
		if(salarie.getType().equals(TypeSalarie.EMPLOYE)){
			notesArchive = noteDeFraisRepository.getNoteBySalarieIdAndEtatLikeOrEtatLikeOrderByIdDesc(salarie.getId(), EtatNoteDeFrais.REMBOUSSEE, EtatNoteDeFrais.REFUSEE, new PageRequest(pageArchive - 1, sizeArchive));
			notesPasEncoreValide = noteDeFraisRepository.getNoteBySalarieIdAndEtatNotLikeOrEtatNotLikeOrderByIdDesc(salarie.getId(), EtatNoteDeFrais.REMBOUSSEE, EtatNoteDeFrais.REFUSEE, new PageRequest(pagePasEncoreValide - 1, sizePasEncoreValide));
		}
		else{
			notesArchive = noteDeFraisRepository.getNoteByEtatLikeOrEtatLikeOrderByIdDesc(EtatNoteDeFrais.REMBOUSSEE, EtatNoteDeFrais.REFUSEE, new PageRequest(pageArchive - 1, sizeArchive));
			notesPasEncoreValide = noteDeFraisRepository.getNoteEtatNotLikeOrEtatNotLikeOrderByIdDesc(EtatNoteDeFrais.REMBOUSSEE, EtatNoteDeFrais.REFUSEE, new PageRequest(pagePasEncoreValide - 1, sizePasEncoreValide));
		}

		int[] pagesArchive = new int[notesArchive.getTotalPages()];
		for(int i=0; i<pagesArchive.length; ++i) 
			pagesArchive[i] = i + 1;
		
		int[] pagesPasEncoreValide = new int[notesPasEncoreValide.getTotalPages()];
		for(int i=0; i<pagesPasEncoreValide.length; ++i) 
			pagesPasEncoreValide[i] = i + 1;
		
		model.addAttribute("pageCouranteArchive", pageArchive);
		model.addAttribute("pagesArchive", pagesArchive);
		model.addAttribute("notesArchive", notesArchive.getContent());
		model.addAttribute("pageCourantePasEncoreValide", pagePasEncoreValide);
		model.addAttribute("pagesPasEncoreValide", pagesPasEncoreValide);
		model.addAttribute("notesPasEncoreValide", notesPasEncoreValide.getContent());
		model.addAttribute("salarie", salarie);
		
		// ajout des pages courantes dans la session
		session.setAttribute("pageCouranteArchive", pageArchive);
		session.setAttribute("pageCourantePasEncoreValide", pagePasEncoreValide);
		
		return "home";
	}
	
	@RequestMapping(value="/Logout")
	public String logout(HttpSession session){
		if(SessionController.isSession(session))
			session.removeAttribute("agent");
		return "redirect:Login";
	}	
	
	@RequestMapping(value="/NouvelNoteHebergement")
	public String noteHebergement(Model model, HttpSession session){
		if(!SessionController.isSession(session))
			return "redirect:Login";
		
		model.addAttribute("note", new FraisHebergement());
		return "hebergementForm";
	}
	
	@RequestMapping(value="/SaveNouvelNoteHebergement", method=RequestMethod.POST)
	public String saveNoteHebergement(@Valid @ModelAttribute("note") FraisHebergement note, BindingResult bindingResult, @RequestParam(name="justif") MultipartFile file,  HttpSession session) throws IllegalStateException, IOException{
		if(!SessionController.isSession(session))
			return "redirect:Login";
		
		if(bindingResult.hasErrors())
			return "hebergementForm";
		
		// TODO verifer nombre de notes par semaine et par mois
		
		if(!file.isEmpty()){
			note.setJustifUrl(file.getOriginalFilename());
		}
		
		Salarie s = salarieRepository.loginSalarie(((ConnexionHandler)(session.getAttribute("agent"))).getLogin(), ((ConnexionHandler)(session.getAttribute("agent"))).getMotDePasse());
		FraisHebergement n = new FraisHebergement(new Date(), EtatNoteDeFrais.EN_COURS, s, note.getNomHotel(), note.getNombreEtoiles(), note.getAdresse(), note.getDateHebergement(), note.getNombreDeNuitOuJour(), note.getPrixDeNuitOuJour());
		n = noteDeFraisRepository.save(n);
		
		if(!file.isEmpty()){
			n.setJustifUrl(n.getId() + file.getOriginalFilename());
			noteDeFraisRepository.save(n);
			file.transferTo(new File(justifFolder+n.getJustifUrl()));
		}
		return "redirect:Home";
	}
	
	@RequestMapping(value="/ModifierNote")
	public String modifierNoteHebergement(Long id, Model model, HttpSession session) {
		if(!SessionController.isSession(session))
			return "redirect:Login";
		
		NoteDeFrais n = noteDeFraisRepository.findOne(id);
		if(n != null){
			if(n.getClass().getSimpleName().equals(FraisHebergement.class.getSimpleName())){
				model.addAttribute("note", n);
				session.setAttribute("noteId", id);
				return "modifierHebergementForm";
			}
			else if(n.getClass().getSimpleName().equals(FraisVehiculePersonnel.class.getSimpleName())){
				model.addAttribute("noteVehicule", n);
				session.setAttribute("noteId", id);
				String cls = new String();
				for(int i=0; i<((FraisVehiculePersonnel) n).getClientsSocietes().size(); ++i){
					if(i != ((FraisVehiculePersonnel) n).getClientsSocietes().size()-1){
						cls += ((FraisVehiculePersonnel) n).getClientsSocietes().get(i).getNom() + ":" + ((FraisVehiculePersonnel) n).getClientsSocietes().get(i).getType() +",";
					}
					else {
						cls += ((FraisVehiculePersonnel) n).getClientsSocietes().get(i).getNom() + ":" + ((FraisVehiculePersonnel) n).getClientsSocietes().get(i).getType();
					}
				}
				model.addAttribute("clients", cls);
				return "modifierVehiculePersonnelForm";
			}
			else if(n.getClass().getSimpleName().equals(FraisTaxi.class.getSimpleName())){
				model.addAttribute("noteTaxi", n);
				session.setAttribute("noteId", id);
				String cls = new String();
				for(int i=0; i<((FraisTaxi) n).getClientsSocietes().size(); ++i){
					if(i != ((FraisTaxi) n).getClientsSocietes().size()-1){
						cls += ((FraisTaxi) n).getClientsSocietes().get(i).getNom() + ":" + ((FraisTaxi) n).getClientsSocietes().get(i).getType() +",";
					}
					else {
						cls += ((FraisTaxi) n).getClientsSocietes().get(i).getNom() + ":" + ((FraisTaxi) n).getClientsSocietes().get(i).getType();
					}
				}
				model.addAttribute("clients", cls);
				return "modifierTaxiForm";
			}
			else if(n.getClass().getSimpleName().equals(FraisDejeunerAffaire.class.getSimpleName())){
				model.addAttribute("note", n);
				session.setAttribute("noteId", id);
				String cls = new String();
				for(int i=0; i<((FraisDejeunerAffaire) n).getClientsSocietes().size(); ++i){
					if(i != ((FraisDejeunerAffaire) n).getClientsSocietes().size()-1){
						cls += ((FraisDejeunerAffaire) n).getClientsSocietes().get(i).getNom() + ":" + ((FraisDejeunerAffaire) n).getClientsSocietes().get(i).getType() +",";
					}
					else {
						cls += ((FraisDejeunerAffaire) n).getClientsSocietes().get(i).getNom() + ":" + ((FraisDejeunerAffaire) n).getClientsSocietes().get(i).getType();
					}
				}
				model.addAttribute("clients", cls);
				return "modifierRestaurantForm";
			}
		}
		
		return "redirect:Home";
	}
	
	@RequestMapping(value="/UpdateNoteHebergement", method=RequestMethod.POST)
	public String updateNoteHebergement(@Valid @ModelAttribute("note") FraisHebergement note, BindingResult bindingResult, @RequestParam(name="justif") MultipartFile file, HttpSession session) throws IOException {
		if(!SessionController.isSession(session))
			return "redirect:Login";
		
		if(bindingResult.hasErrors())
			return "hebergementForm";
		
		FraisHebergement n = (FraisHebergement) noteDeFraisRepository.findOne(Long.valueOf((session.getAttribute("noteId").toString())));
		session.removeAttribute("noteId");
		n.setNomHotel(note.getNomHotel());
		n.setNombreEtoiles(note.getNombreEtoiles());
		n.setAdresse(note.getAdresse());
		if(note.getDateDemandeRemboursement() != null)
			n.setDateDemandeRemboursement(note.getDateDemandeRemboursement());
		n.setNombreDeNuitOuJour(note.getNombreDeNuitOuJour());
		n.setPrixDeNuitOuJour(note.getPrixDeNuitOuJour());
		
		if(!file.isEmpty()){
			File f = new File(justifFolder+n.getJustifUrl());
			f.delete();
			n.setJustifUrl(n.getId() + file.getOriginalFilename());
			file.transferTo(new File(justifFolder+n.getJustifUrl()));
		}
		
		noteDeFraisRepository.save(n);
		return "redirect:Home";
	}
	
	@RequestMapping(value="/DeleteNote")
	public String deleteNoteHebergement(Long id, HttpSession session) {
		if(noteDeFraisRepository.findOne(id) != null)
			noteDeFraisRepository.delete(id);
		
		return "redirect:Home?pagePasEncoreValide=" + session.getAttribute("pageCourantePasEncoreValide") + "&pageArchive=" + session.getAttribute("pageCouranteArchive");
	}
	
	@RequestMapping(value="/NouvelNoteRestaurant")
	public String noteRestaurant(Model model, HttpSession session){
		if(!SessionController.isSession(session))
			return "redirect:Login";
		
		model.addAttribute("note", new FraisDejeunerAffaire());
		return "restaurantForm";
	}
	
	@RequestMapping(value="/SaveNouvelNoteFraisDejeunerAffaire", method=RequestMethod.POST)
	public String saveNoteFraisDejeunerAffaire(@Valid @ModelAttribute("note") FraisDejeunerAffaire note, BindingResult bindingResult, @RequestParam(name="clients") String clients, @RequestParam(name="justif") MultipartFile file, HttpSession session) throws IllegalStateException, IOException{
		if(!SessionController.isSession(session))
			return "redirect:Login";
		
		if(bindingResult.hasErrors())
			return "restaurantForm";
		
		// TODO verifer nombre de notes par semaine et par mois
		
		List<Client> clsSos = new ArrayList<>();
		String[] cls = clients.split(",");
		for(int i=0; i<cls.length; ++i){
			String[] cl = cls[i].split(":");
			if(cl[1].equalsIgnoreCase(TypeClient.PERSONNE.toString())){
				Client c = new Client(cl[0], TypeClient.PERSONNE, "");
				c = clientRepository.save(c);
				clsSos.add(c);
			}
			if(cl[1].equalsIgnoreCase(TypeClient.ENTREPRISE.toString())){
				Client c = new Client(cl[0], TypeClient.ENTREPRISE, "");
				c = clientRepository.save(c);
				clsSos.add(c);
			}
		}
		
		if(!file.isEmpty()){
			note.setJustifUrl(file.getOriginalFilename());
		}
		
		Salarie s = salarieRepository.loginSalarie(((ConnexionHandler)(session.getAttribute("agent"))).getLogin(), ((ConnexionHandler)(session.getAttribute("agent"))).getMotDePasse());
		FraisDejeunerAffaire n = new FraisDejeunerAffaire(new Date(), EtatNoteDeFrais.EN_COURS, s, note.getNomRestaurant(), note.getAdresse(), note.getDateDejeuner(), note.getMontant());
		n.setClientsSocietes(clsSos);
		n = noteDeFraisRepository.save(n);
		
		if(!file.isEmpty()){
			n.setJustifUrl(n.getId() + file.getOriginalFilename());
			noteDeFraisRepository.save(n);
			file.transferTo(new File(justifFolder+n.getJustifUrl()));
		}
		return "redirect:Home";
	}
	
	@RequestMapping(value="/UpdateNoteFraisDejeunerAffaire", method=RequestMethod.POST)
	public String updateNoteFraisDejeunerAffaire(@Valid @ModelAttribute("note") FraisDejeunerAffaire note, BindingResult bindingResult, @RequestParam(name="clients") String clients, @RequestParam(name="justif") MultipartFile file, HttpSession session) throws IOException {
		if(!SessionController.isSession(session))
			return "redirect:Login";
		
		if(bindingResult.hasErrors())
			return "restaurantForm";
		
		FraisDejeunerAffaire n = (FraisDejeunerAffaire) noteDeFraisRepository.findOne(Long.valueOf((session.getAttribute("noteId").toString())));
		session.removeAttribute("noteId");
		n.setNomRestaurant((note.getNomRestaurant()));
		n.setAdresse(note.getAdresse());
		if(note.getDateDemandeRemboursement() != null)
			n.setDateDejeuner(note.getDateDejeuner());
		n.setMontant((note.getMontant()));
		
		for(int i=0; i<n.getClientsSocietes().size(); ++i){
			clientRepository.delete(n.getClientsSocietes().get(i));
			n.getClientsSocietes().remove(i);
		}
		
		List<Client> clsSos = new ArrayList<>();
		String[] cls = clients.split(",");
		for(int i=0; i<cls.length; ++i){
			String[] cl = cls[i].split(":");
			if(cl[1].equalsIgnoreCase(TypeClient.PERSONNE.toString())){
				Client c = new Client(cl[0], TypeClient.PERSONNE, "");
				c = clientRepository.save(c);
				clsSos.add(c);
			}
			if(cl[1].equalsIgnoreCase(TypeClient.ENTREPRISE.toString())){
				Client c = new Client(cl[0], TypeClient.ENTREPRISE, "");
				c = clientRepository.save(c);
				clsSos.add(c);
			}
		}
		
		n.setClientsSocietes(clsSos);
		
		if(!file.isEmpty()){
			File f = new File(justifFolder+n.getJustifUrl());
			f.delete();
			n.setJustifUrl(n.getId() + file.getOriginalFilename());
			file.transferTo(new File(justifFolder+n.getJustifUrl()));
		}
		
		noteDeFraisRepository.save(n);
		return "redirect:Home";
	}
	
	@RequestMapping(value="/NouvelNoteFraisVehiculePersonnel")
	public String noteFraisVehiculePersonnel(Model model, HttpSession session){
		if(!SessionController.isSession(session))
			return "redirect:Login";
		
		model.addAttribute("noteVehicule", new FraisVehiculePersonnel());
		model.addAttribute("clientsSocietes", new String());
		return "vehiculePesonnelForm";
	}
	
	@RequestMapping(value="/SaveNouvelNoteFraisVehiculePersonnel", method=RequestMethod.POST)
	public String saveNoteFraisVehiculePersonnel(@Valid @ModelAttribute("noteVehicule") FraisVehiculePersonnel note, BindingResult bindingResult, @RequestParam(name="clients") String clients, @RequestParam(name="justif") MultipartFile file, HttpSession session) throws IllegalStateException, IOException{
		if(!SessionController.isSession(session))
			return "redirect:Login";
		
		if(bindingResult.hasErrors())
			return "vehiculePesonnelForm";
			
		// TODO verifer nombre de notes par semaine et par mois
		
		List<Client> clsSos = new ArrayList<>();
		String[] cls = clients.split(",");
		for(int i=0; i<cls.length; ++i){
			String[] cl = cls[i].split(":");
			if(cl[1].equalsIgnoreCase(TypeClient.PERSONNE.toString())){
				Client c = new Client(cl[0], TypeClient.PERSONNE, "");
				c = clientRepository.save(c);
				clsSos.add(c);
			}
			if(cl[1].equalsIgnoreCase(TypeClient.ENTREPRISE.toString())){
				Client c = new Client(cl[0], TypeClient.ENTREPRISE, "");
				c = clientRepository.save(c);
				clsSos.add(c);
			}
		}
		
		if(!file.isEmpty()){
			note.setJustifUrl(file.getOriginalFilename());
		}
		
		Salarie s = salarieRepository.loginSalarie(((ConnexionHandler)(session.getAttribute("agent"))).getLogin(), ((ConnexionHandler)(session.getAttribute("agent"))).getMotDePasse());		
		FraisVehiculePersonnel n = new FraisVehiculePersonnel(new Date(), EtatNoteDeFrais.EN_COURS, s, note.getDateDeplacement(), note.getLieuDepart(), note.getLieuArrive(), note.isCarteGrise(), note.getType(), note.getPuissanceFiscal(), note.getNbreKilometrage(), note.getCarburantBrule(), note.getPrixLitre());
		n.setClientsSocietes(clsSos);
		n = noteDeFraisRepository.save(n);
		
		if(!file.isEmpty()){
			n.setJustifUrl(n.getId() + file.getOriginalFilename());
			noteDeFraisRepository.save(n);
			file.transferTo(new File(justifFolder+n.getJustifUrl()));
		}
		return "redirect:Home";
	}
	
	@RequestMapping(value="/UpdateNoteFraisVehiculePersonnel", method=RequestMethod.POST)
	public String updateNoteFraisDejeunerAffaire(@Valid @ModelAttribute("noteVehicule") FraisVehiculePersonnel note, BindingResult bindingResult, @RequestParam(name="clients") String clients, @RequestParam(name="justif") MultipartFile file, HttpSession session) throws IOException {
		if(!SessionController.isSession(session))
			return "redirect:Login";
		
		if(bindingResult.hasErrors())
			return "restaurantForm";
		
		FraisVehiculePersonnel n = (FraisVehiculePersonnel) noteDeFraisRepository.findOne(Long.valueOf((session.getAttribute("noteId").toString())));
		session.removeAttribute("noteId");
		n.setCarteGrise(note.isCarteGrise());
		n.setType(note.getType());
		
		if(note.getDateDeplacement() != null)
			n.setDateDeplacement(note.getDateDeplacement());
		n.setLieuDepart(note.getLieuDepart());
		n.setLieuArrive(note.getLieuDepart());
		n.setPuissanceFiscal(note.getPuissanceFiscal());
		n.setNbreKilometrage(note.getNbreKilometrage());
		n.setCarburantBrule(note.getCarburantBrule());
		n.setPrixLitre(note.getPrixLitre());
		
		for(int i=0; i<n.getClientsSocietes().size(); ++i){
			clientRepository.delete(n.getClientsSocietes().get(i));
			n.getClientsSocietes().remove(i);
		}
		
		List<Client> clsSos = new ArrayList<>();
		String[] cls = clients.split(",");
		for(int i=0; i<cls.length; ++i){
			String[] cl = cls[i].split(":");
			if(cl[1].equalsIgnoreCase(TypeClient.PERSONNE.toString())){
				Client c = new Client(cl[0], TypeClient.PERSONNE, "");
				c = clientRepository.save(c);
				clsSos.add(c);
			}
			if(cl[1].equalsIgnoreCase(TypeClient.ENTREPRISE.toString())){
				Client c = new Client(cl[0], TypeClient.ENTREPRISE, "");
				c = clientRepository.save(c);
				clsSos.add(c);
			}
		}
		
		n.setClientsSocietes(clsSos);
		
		if(!file.isEmpty()){
			File f = new File(justifFolder+n.getJustifUrl());
			f.delete();
			n.setJustifUrl(n.getId() + file.getOriginalFilename());
			file.transferTo(new File(justifFolder+n.getJustifUrl()));
		}
		
		noteDeFraisRepository.save(n);
		return "redirect:Home";
	}
	
	@RequestMapping(value="/NouvelNoteTaxi")
	public String noteTaxi(Model model, HttpSession session){
		if(!SessionController.isSession(session))
			return "redirect:Login";
		
		model.addAttribute("noteTaxi", new FraisTaxi());
		return "taxiForm";
	}
	
	@RequestMapping(value="/SaveNouvelNoteFFraisTaxi", method=RequestMethod.POST)
	public String saveNoteFraisTaxi(@Valid @ModelAttribute("noteTaxi") FraisTaxi note, BindingResult bindingResult, @RequestParam(name="clients") String clients, @RequestParam(name="justif") MultipartFile file, HttpSession session) throws IllegalStateException, IOException{
		if(!SessionController.isSession(session))
			return "redirect:Login";
		
		if(bindingResult.hasErrors())
			return "taxiForm";
			
		// TODO verifer nombre de notes par semaine et par mois
		
		 List<Client> clsSos = new ArrayList<>();
		String[] cls = clients.split(",");
		for(int i=0; i<cls.length; ++i){
			String[] cl = cls[i].split(":");
			if(cl[1].equalsIgnoreCase(TypeClient.PERSONNE.toString())){
				Client c = new Client(cl[0], TypeClient.PERSONNE, "");
				c = clientRepository.save(c);
				clsSos.add(c);
			}
			if(cl[1].equalsIgnoreCase(TypeClient.ENTREPRISE.toString())){
				Client c = new Client(cl[0], TypeClient.ENTREPRISE, "");
				c = clientRepository.save(c);
				clsSos.add(c);
			}
		}
		
		System.out.println(clsSos.size());
		if(!file.isEmpty()){
			note.setJustifUrl(file.getOriginalFilename());
		}
		
		Salarie s = salarieRepository.loginSalarie(((ConnexionHandler)(session.getAttribute("agent"))).getLogin(), ((ConnexionHandler)(session.getAttribute("agent"))).getMotDePasse());		
		FraisTaxi n = new FraisTaxi(new Date(), EtatNoteDeFrais.EN_COURS, s, note.getDateDeplacement(), note.getLieuDepart(), note.getLieuArrive(), note.getMontant());
		n.setClientsSocietes(clsSos);
		n = noteDeFraisRepository.save(n);
		
		if(!file.isEmpty()){
			n.setJustifUrl(n.getId() + file.getOriginalFilename());
			noteDeFraisRepository.save(n);
			file.transferTo(new File(justifFolder+n.getJustifUrl()));
		}
		return "redirect:Home";
	}
	
	@RequestMapping(value="/UpdateNoteFraisTaxi", method=RequestMethod.POST)
	public String updateNoteFraisTaxi(@Valid @ModelAttribute("noteVehicule") FraisTaxi note, BindingResult bindingResult, @RequestParam(name="clients") String clients, @RequestParam(name="justif") MultipartFile file, HttpSession session) throws IOException {
		if(!SessionController.isSession(session))
			return "redirect:Login";
		
		if(bindingResult.hasErrors())
			return "modifierTaxiForm";
		
		FraisTaxi n = (FraisTaxi) noteDeFraisRepository.findOne(Long.valueOf((session.getAttribute("noteId").toString())));
		session.removeAttribute("noteId");
		if(note.getDateDeplacement() != null)
			n.setDateDeplacement(note.getDateDeplacement());
		n.setLieuDepart(note.getLieuDepart());
		n.setLieuArrive(note.getLieuDepart());
		n.setMontant(note.getMontant());
		
		for(int i=0; i<n.getClientsSocietes().size(); ++i){
			clientRepository.delete(n.getClientsSocietes().get(i));
			n.getClientsSocietes().remove(i);
		}
		
		List<Client> clsSos = new ArrayList<>();
		String[] cls = clients.split(",");
		for(int i=0; i<cls.length; ++i){
			String[] cl = cls[i].split(":");
			if(cl[1].equalsIgnoreCase(TypeClient.PERSONNE.toString())){
				Client c = new Client(cl[0], TypeClient.PERSONNE, "");
				c = clientRepository.save(c);
				clsSos.add(c);
			}
			if(cl[1].equalsIgnoreCase(TypeClient.ENTREPRISE.toString())){
				Client c = new Client(cl[0], TypeClient.ENTREPRISE, "");
				c = clientRepository.save(c);
				clsSos.add(c);
			}
		}
		
		n.setClientsSocietes(clsSos);
		
		if(!file.isEmpty()){
			File f = new File(justifFolder+n.getJustifUrl());
			f.delete();
			n.setJustifUrl(n.getId() + file.getOriginalFilename());
			file.transferTo(new File(justifFolder+n.getJustifUrl()));
		}
		
		noteDeFraisRepository.save(n);
		return "redirect:Home";
	}
	
	@RequestMapping(value="/getJustif", produces=MediaType.IMAGE_JPEG_VALUE)
	@ResponseBody
	public byte[] getJustif(Long id) throws FileNotFoundException, IOException{
		NoteDeFrais n = noteDeFraisRepository.findOne(id);
		File file = new File(justifFolder + n.getJustifUrl());
		if(file.exists()){
			FileInputStream fileInputStream = new FileInputStream(file);
			byte[] result = IOUtils.toByteArray(fileInputStream);
			fileInputStream.close();
			IOUtils.closeQuietly(fileInputStream);
			return result;
		}
		return IOUtils.toByteArray(new FileInputStream(new File(justifFolder + "vide.png")));
	}
	
	@RequestMapping(value="/ValiderNote")
	public String validerNote(Long id, HttpServletRequest request, HttpSession session) {
		NoteDeFrais n = noteDeFraisRepository.findOne(id);
		if(n != null){
			n.setEtat(EtatNoteDeFrais.REMBOUSSEE);
			noteDeFraisRepository.save(n);
		}
		//return "redirect:Home?pagePasEncoreValide=" + session.getAttribute("pageCourantePasEncoreValide") + "&pageArchive=" + session.getAttribute("pageCouranteArchive");
		return "redirect:" + request.getHeader("Referer");
	}
	
	@RequestMapping(value="/RefuserNote")
	public String refuserNote(Long id, HttpServletRequest request, HttpSession session) {
		NoteDeFrais n = noteDeFraisRepository.findOne(id);
		if(n != null){
			n.setEtat(EtatNoteDeFrais.REFUSEE);
			noteDeFraisRepository.save(n);
		}
		//return "redirect:Home?pagePasEncoreValide=" + session.getAttribute("pageCourantePasEncoreValide") + "&pageArchive=" + session.getAttribute("pageCouranteArchive");
		return "redirect:" + request.getHeader("Referer");
	}
	
}
