package org.opendevup.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.opendevup.dao.EtudiantRepository;
import org.opendevup.entities.Etudiant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class EtudiantRestService {
	@Autowired
	private EtudiantRepository etudiantRepository;
	
	@Secured(value={"ROLE_ADMIN","ROLE_INVITE","ROLE_ETUDIANT"})
	@RequestMapping(value="/chercherEtudiants",method=RequestMethod.GET)
	public Page<Etudiant> chercher(String mc,
			@RequestParam(name="page",defaultValue="0")int page,
			@RequestParam(name="size",defaultValue="5")int size){
		return etudiantRepository.chercherEtudiants("%"+mc+"%", new PageRequest(page, size));
	}
	@Secured(value={"ROLE_ADMIN","ROLE_INVITE","ROLE_ETUDIANT"})
	@RequestMapping(value="/listEtudiants",method=RequestMethod.GET)
	public List <Etudiant> listEtudiants(){
		return etudiantRepository.findAll();
	}
	@Secured(value={"ROLE_ADMIN","ROLE_INVITE","ROLE_ETUDIANT"})
	@RequestMapping(value="/etudiants/{id}",method=RequestMethod.GET)
	public Etudiant getEtudiant(@PathVariable Long id){
		return etudiantRepository.findOne(id);
	}
	@Secured(value={"ROLE_ADMIN"})
	@RequestMapping(value="/etudiants/{id}",method=RequestMethod.DELETE)
	public void deleteEtudiant(@PathVariable Long id){
		etudiantRepository.delete(id);
	}
	@Secured(value={"ROLE_ADMIN","ROLE_ETUDIANT"})
	@RequestMapping(value="/etudiants/{id}",method=RequestMethod.PUT)
	public Etudiant updateEtudiant(@RequestBody Etudiant e,@PathVariable ("id")Long id){
		e.setId(id);
		return etudiantRepository.saveAndFlush(e);
	}
	
	@Secured(value={"ROLE_ADMIN","ROLE_INVITE","ROLE_ETUDIANT"})
	 @RequestMapping(value="/getLogedUser", method = RequestMethod.GET)
	  public String getLogedUser() {
	      Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	      String name = auth.getName(); //get logged in username
	      return name;
	  }
	@Secured(value={"ROLE_ADMIN"})
	@RequestMapping(value="/etudiants",method=RequestMethod.POST)
	public Object saveEtudiant(@RequestBody @Valid Etudiant e,BindingResult bindingResult){
		if(bindingResult.hasErrors()){
			Map<String, Object> errors=new HashMap<>();
			errors.put("errors", true);
			for(FieldError fe:bindingResult.getFieldErrors()){
				errors.put(fe.getField(), fe.getDefaultMessage());
			}
			return  errors;
		}
		return etudiantRepository.save(e);
	}
	@Secured(value={"ROLE_ADMIN","ROLE_ETUDIANT"})
	@RequestMapping(value="/updateEtudiant",method=RequestMethod.POST)
	public String update(@Valid Etudiant et,BindingResult bindingResult,
			@RequestParam(name="picture")MultipartFile file) throws Exception{
		
		if(bindingResult.hasErrors()){
			return "editProfil";
		}
		if(!(file.isEmpty())){ et.setPhoto(file.getOriginalFilename());}
		etudiantRepository.save(et);
		
		return "redirect:profiles";
	}

}
