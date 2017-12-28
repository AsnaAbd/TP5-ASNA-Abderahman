package org.opendevup.service;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import javax.websocket.server.PathParam;

import org.apache.commons.io.IOUtils;
import org.opendevup.dao.EtudiantRepository;
import org.opendevup.entities.Etudiant;
import org.opendevup.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class EtudiantRestService {
	@Value("${dir.images}")
	private String imageDir;
	@Autowired
	private EtudiantRepository etudiantRepository;

	@RequestMapping(value="/chercherEtudiants",method=RequestMethod.GET)
	public Page<Etudiant> chercher(String mc,
			@RequestParam(name="page",defaultValue="0")int page,
			@RequestParam(name="size",defaultValue="5")int size){
		return etudiantRepository.chercherEtudiants("%"+mc+"%", new PageRequest(page, size));
	}
	
	@RequestMapping(value="/listEtudiants",method=RequestMethod.GET)
	public List <Etudiant> listEtudiants(){
		return etudiantRepository.findAll();
	}
	
	@RequestMapping(value="/etudiants/{id}",method=RequestMethod.GET)
	public Etudiant getEtudiant(@PathVariable Long id){
		return etudiantRepository.findOne(id);
	}
	
	@RequestMapping(value="/etudiants/{id}",method=RequestMethod.DELETE)
	public void deleteEtudiant(@PathVariable Long id){
		etudiantRepository.delete(id);
	}
	
	@RequestMapping(value="/etudiants/{id}",method=RequestMethod.PUT)
	public Etudiant updateEtudiant(@RequestBody Etudiant e,@PathVariable ("id")Long id){
		e.setId(id);
		return etudiantRepository.saveAndFlush(e);
	}
	
	@Secured(value={"ROLE_ADMIN","ROLE_SCOLARITE","ROLE_ETUDIANT"})
	@RequestMapping(value="/getLogedUser")
	public Map<String, Object> getLogedUser(HttpServletRequest
			httpServletRequest) {
		HttpSession httpSession=httpServletRequest.getSession();
		SecurityContext securityContext=(SecurityContext)
		httpSession.getAttribute("STRING_SECURITY_CONTEXT");
		String username=securityContext.getAuthentication().getName();
		List<String> roles=new ArrayList<>();
		for(GrantedAuthority  ga:securityContext.getAuthentication().getAuthorities()){
			roles.add(ga.getAuthority());
		}
		Map<String, Object> params=new HashMap();
		params.put("username", username);
		params.put("roles", roles);
		return params;
	}
	
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
	
	@RequestMapping(value="/updateEtudiant",method=RequestMethod.POST)
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
	
/*	@RequestMapping(value="/SaveEtudiant",method=RequestMethod.POST)
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
	}*/
	
	/*@RequestMapping(value="/profiless")
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
	}*/
	
	
	@RequestMapping(value="/modifier")
	public String modifier(Long id,Model  model){
		Etudiant et=etudiantRepository.getOne(id);
		model.addAttribute("etudiant", et);
		return "editProfil";
	}
	
	@RequestMapping(value="/getPhoto", produces=org.springframework.http.MediaType.IMAGE_JPEG_VALUE)
	@ResponseBody
	public byte[] getPhoto(Long id) throws Exception {
		System.out.println("--------------ana------------------------");
		System.out.println("--------------"+id+"---------------");
		File f=new File(imageDir+id);
		
		return IOUtils.toByteArray(new FileInputStream(f));
		}

}
