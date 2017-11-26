package org.opendevup.web;

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
import org.springframework.web.multipart.MultipartFile;

import scala.annotation.meta.setter;


@Controller
@RequestMapping(value="/home")
public class homeController {
	
	@Autowired
	private EtudiantRepository etudiantRepository;
	@Value("${dir.images}")
	private String imageDir;
	@Autowired
	private DiplomeRepository diplomeRepository;
	
	@RequestMapping(value="/index")
	public String Index(Model model,@RequestParam(name="page", defaultValue="0") int p,
									@RequestParam(name="motCle", defaultValue="") String mc) {
		Page<Etudiant> pageEtudiants=etudiantRepository.chercherEtudiants("%"+mc+"%", new PageRequest(p, 5));
		int pagesCount=pageEtudiants.getTotalPages();
		int[] pages=new int[pagesCount];
		for(int i=0;i<pagesCount;i++) pages[i]=i;
		model.addAttribute("pages", pages);
		model.addAttribute("pageEtudiants", pageEtudiants);
		model.addAttribute("pageCourante", p);
		model.addAttribute("motCle", mc);
		
		model.addAttribute("pageEtudiants", pageEtudiants);
		return "index";
	}
	@RequestMapping(value="/connectez-vous")
	public String connect(Model model,@RequestParam(name="page", defaultValue="0") int p,
			@RequestParam(name="motCle", defaultValue="") String mc) {
		return "connectez-vous";
	}
	
	@RequestMapping(value="/createProfil")
	public String createProfil() {
		return "createProfil";
	}
	
	@RequestMapping(value="/createProfil",method=RequestMethod.GET)
	public String fromEtudiant(Model model){
		model.addAttribute("etudiant", new Etudiant());
		return "createProfil";
	}
	@RequestMapping(value="/UpdateEtudiant",method=RequestMethod.POST)
	public String update(@Valid Etudiant et,BindingResult bindingResult,
			@RequestParam(name="picture")MultipartFile file) throws Exception{
		
		if(bindingResult.hasErrors()){
			return "editProfil";
		}
		if(!(file.isEmpty())){ et.setPhoto(file.getOriginalFilename());}
		etudiantRepository.save(et);
		
		if(!(file.isEmpty())){
			et.setPhoto(file.getOriginalFilename());
			file.transferTo(new File(imageDir+et.getId()));
		}
		
		return "redirect:profiles";
	}
	
	@RequestMapping(value="/UpdateDiplome",method=RequestMethod.POST)
	public String update(@Valid Diplome et,BindingResult bindingResult) throws Exception{
		
		if(bindingResult.hasErrors()){
			return "editDiplome";
		}
		diplomeRepository.save(et);
		
		return "redirect:diplomes";
	}
	
	@RequestMapping(value="/SaveEtudiant",method=RequestMethod.POST)
	public String save(@Valid Etudiant et,BindingResult bindingResult,
			@RequestParam(name="picture")MultipartFile file) throws Exception{
		
		if(bindingResult.hasErrors()){
			return "createProfil";
		}
		if(!(file.isEmpty())){ et.setPhoto(file.getOriginalFilename());}
		etudiantRepository.save(et);
		
		if(!(file.isEmpty())){
			et.setPhoto(file.getOriginalFilename());
			file.transferTo(new File(imageDir+et.getId()));
		}
		
		return "redirect:profiles";
	}
	
	
	@RequestMapping(value="/getPhoto", produces=org.springframework.http.MediaType.IMAGE_JPEG_VALUE)
	@ResponseBody
	public byte[] getPhoto(Long id) throws Exception {
		System.out.println("--------------ana------------------------");
		System.out.println("--------------"+id+"---------------");
		File f=new File(imageDir+id);
		
		return IOUtils.toByteArray(new FileInputStream(f));
		}
	
	@RequestMapping(value="/consulterProfil")
	public String consulterProfil() {
		return "consulterProfil";
	}
	
	@RequestMapping(value="/profiles")
	public String profiles(Model modelp,@RequestParam(name="page", defaultValue="0") int p,
			@RequestParam(name="motCle", defaultValue="") String mc) {
			Page<Etudiant> Etudiants=etudiantRepository.chercherEtudiants("%"+mc+"%", new PageRequest(p, 5));
			int pagesCount=Etudiants.getTotalPages();
			int[] pages=new int[pagesCount];
			for(int i=0;i<pagesCount;i++) pages[i]=i;
			modelp.addAttribute("pages", pages);
			modelp.addAttribute("Etudiants", Etudiants);
			modelp.addAttribute("pageCourante", p);
			modelp.addAttribute("motCle", mc);

			modelp.addAttribute("Etudiants", Etudiants);
		
		return "profiles";
	}
	@RequestMapping(value="/diplomes")
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
	
	@RequestMapping(value="/supprimer")
	public String supprimer(Long id){
		etudiantRepository.delete(id);
		return "redirect:profiles";
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
	
	@RequestMapping(value="/modifier")
	public String modifier(Long id,Model  model){
		Etudiant et=etudiantRepository.getOne(id);
		model.addAttribute("etudiant", et);
		return "editProfil";
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
