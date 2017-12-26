package org.opendevup.service;

import java.awt.PageAttributes.MediaType;
import java.io.File;
import java.io.FileInputStream;

import javax.validation.Valid;

import org.apache.commons.io.IOUtils;
import org.opendevup.dao.DiplomeRepository;
import org.opendevup.dao.EtudiantRepository;
import org.opendevup.entities.Diplome;
import org.opendevup.entities.Etudiant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


@Controller
public class DiplomeRestService {
	
	@Autowired
	private DiplomeRepository diplomeRepository;
		
	/*@RequestMapping(value="/createProfil")
	public String createProfil() {
		return "createProfil";
	}*/
	
/*	@RequestMapping(value="/createProfillll",method=RequestMethod.GET)
	public String fromEtudiant(Model model){
		model.addAttribute("etudiant", new Etudiant());
		return "createProfil";
	}*/
	
	
	@RequestMapping(value="/UpdateDiplome",method=RequestMethod.POST)
	public String update(@Valid Diplome et,BindingResult bindingResult) throws Exception{
		
		if(bindingResult.hasErrors()){
			return "editDiplome";
		}
		diplomeRepository.save(et);
		
		return "redirect:diplomes";
	}
	
	@RequestMapping(value="/consulterProfil")
	public String consulterProfil() {
		return "consulterProfil";
	}
	
	
	@RequestMapping(value="/diplomess")
	public String diplomes(Model model,Model model1,@RequestParam(name="page", defaultValue="0") int p,
									@RequestParam(name="motCle", defaultValue="") String mc) {
		Page<Diplome> pageDiplomes=diplomeRepository.chercherDiplomes("%"+mc+"%", new PageRequest(p, 5));
		int pagesCount=pageDiplomes.getTotalPages();
		int[] pages=new int[pagesCount];
		for(int i=0;i<pagesCount;i++) pages[i]=i;
		model.addAttribute("pages", pages);
		model.addAttribute("pageDiplomes", pageDiplomes);
		model.addAttribute("pageCourante", p);
		model.addAttribute("motCle", mc);
		
		model.addAttribute("pageDiplomes", pageDiplomes);
		model1.addAttribute("diplome", new Diplome());
		return "diplomes";
	}
	
	
	
	@RequestMapping(value="/supprimerDiplome")
	public String supprimerDiplome(Long id){
		diplomeRepository.delete(id);
		return "redirect:diplomes";
	}
	
	@RequestMapping(value="/modifierDiplome")
	public String update(Long id,Model  model){
		Diplome dp=diplomeRepository.getOne(id);
		model.addAttribute("diplome", dp);
		return "editDiplomes";
	}
	
	
	
	@RequestMapping(value="/SaveDiplome",method=RequestMethod.POST)
	public String saveDiplome(@Valid Diplome et,BindingResult bindingResult) throws Exception{
		if(bindingResult.hasErrors()){
			return "profiles";
		}
		diplomeRepository.save(et);
		return "redirect:diplomes";
	}

	
}
