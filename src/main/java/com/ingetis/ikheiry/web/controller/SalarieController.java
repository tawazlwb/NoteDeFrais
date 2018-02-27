package com.ingetis.ikheiry.web.controller;

import java.util.Date;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.ingetis.ikheiry.dao.INoteDeFraisRepository;
import com.ingetis.ikheiry.dao.ISalarieRepository;
import com.ingetis.ikheiry.model.NoteDeFrais;
import com.ingetis.ikheiry.model.Salarie;
import com.ingetis.ikheiry.model.enumeartion.EtatNoteDeFrais;
import com.ingetis.ikheiry.web.session.ConnexionHandler;

@Controller
@RequestMapping(value="/Salarie")
public class SalarieController {
	
	@Autowired
	private ISalarieRepository salarieRepository;
	
	@Autowired
	private INoteDeFraisRepository noteDeFraisRepository;
	
	@RequestMapping(value="/AddSalarie")
	public String addSalarie(Model model, HttpSession session){
		if(!SessionController.isSession(session))
			return "redirect:../Login";
		else if(SessionController.isEmploye(session))
			return "redirect:../Home";
		model.addAttribute("s", new Salarie());
		
		// admin
		Salarie s = salarieRepository.findByLogin(((ConnexionHandler) (session.getAttribute("agent"))).getLogin());
		model.addAttribute("salarie", s);
				
		return "addSalarie";
	}
	
	@RequestMapping(value="/SaveSalarie", method=RequestMethod.GET)
	public String saveSalarieGET(Model model, HttpSession session){
		if(!SessionController.isSession(session))
			return "redirect:../Login";
		else if(SessionController.isEmploye(session))
			return "redirect:../Home";
		model.addAttribute("salarie", new Salarie());
		return "addSalarie";
	}
	
	@RequestMapping(value="/SaveSalarie", method=RequestMethod.POST)
	public String saveSalariePOST(@Valid Salarie salarie, BindingResult bindingResult){
		if(bindingResult.hasErrors())
			return "addSalarie";
		salarie.setDateEmbauche(new Date());
		salarieRepository.save(salarie);
		return "redirect:ViewSalarie";
	}
	
	@RequestMapping(value="/ViewSalarie", method=RequestMethod.GET)
	public String viewSalariePOST(@RequestParam(name="page", defaultValue="1") int page,
			@RequestParam(name="size", defaultValue="13") int size, Model model, HttpSession session){
		if(!SessionController.isSession(session))
			return "redirect:../Login";
		else if(SessionController.isEmploye(session))
			return "redirect:../Home";
		
		Page<Salarie> pageSalaries = salarieRepository.findAll(new PageRequest(page - 1, size));
		
		int[] pages = new int[pageSalaries.getTotalPages()];
		for(int i=0; i<pages.length; ++i) 
			pages[i] = i + 1;
		
		model.addAttribute("pageCourante", page);
		model.addAttribute("pages", pages);
		model.addAttribute("pageSalaries", pageSalaries);
		
		// admin
		Salarie s = salarieRepository.findByLogin(((ConnexionHandler) (session.getAttribute("agent"))).getLogin());
		model.addAttribute("salarie", s);
		
		return "viewSalarie";
	}
	
	@RequestMapping(value="/EditSalarie")
	public String editSalarie(Long id, Model model){
		Salarie salarie = salarieRepository.getOne(id);
		model.addAttribute("salarie", salarie);
		return "editSalarie";
	}
	
	@RequestMapping(value="/UpdateSalarie", method=RequestMethod.POST)
	public String updateSalariePOST(@Valid Salarie salarie, BindingResult bindingResult, @RequestParam(name="mot") String mot, HttpSession session){
		if(bindingResult.hasErrors())
			return "editSalarie";
		
		Salarie s = salarieRepository.findOne(salarie.getId());
		s.update(salarie, mot);
		salarieRepository.save(s);
		return "redirect:ViewSalarie";
	}
	
	@RequestMapping(value="/DeleteSalarie")
	public String deleteSalarie(Long id, Model model){
		salarieRepository.delete(id);
		return "redirect:ViewSalarie";
	}
	
	@RequestMapping(value="/NotedeFrais")
	public String notedeFraisSalarie(Long id, @RequestParam(name="pageArchive", defaultValue="1") int pageArchive,
			@RequestParam(name="sizeArchive", defaultValue="7") int sizeArchive,
			@RequestParam(name="pagePasEncoreValide", defaultValue="1") int pagePasEncoreValide,
			@RequestParam(name="sizePasEncoreValide", defaultValue="7") int sizePasEncoreValide,
			Model model, HttpSession session){
		
		if(!SessionController.isSession(session))
			return "redirect:Login";
		
		// admin
		Salarie sal = salarieRepository.findByLogin(((ConnexionHandler) (session.getAttribute("agent"))).getLogin());
				
		Salarie s = salarieRepository.findOne(id);
		Page<NoteDeFrais> notesArchive;
		Page<NoteDeFrais> notesPasEncoreValide;
		
		notesArchive = noteDeFraisRepository.getNoteBySalarieIdAndEtatLikeOrEtatLikeOrderByIdDesc(s.getId(), EtatNoteDeFrais.REMBOUSSEE, EtatNoteDeFrais.REFUSEE, new PageRequest(pageArchive - 1, sizeArchive));
		notesPasEncoreValide = noteDeFraisRepository.getNoteBySalarieIdAndEtatNotLikeOrEtatNotLikeOrderByIdDesc(s.getId(), EtatNoteDeFrais.REMBOUSSEE, EtatNoteDeFrais.REFUSEE, new PageRequest(pagePasEncoreValide - 1, sizePasEncoreValide));
		
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
		
		model.addAttribute("s", s);
		model.addAttribute("salarie", sal);
				
		// ajout des pages courantes dans la session
		session.setAttribute("pageCouranteArchive", pageArchive);
		session.setAttribute("pageCourantePasEncoreValide", pagePasEncoreValide);
				
		return "viewSalarieNote";
	}
	
	// curseur eclipse Inser
}
