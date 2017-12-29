package org.opendevup.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.opendevup.dao.DiplomeRepository;
import org.opendevup.entities.Diplome;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@Secured(value={"ROLE_ADMIN"})
public class DiplomeRestService {

	@Autowired
	private DiplomeRepository diplomeRepository;

	@RequestMapping(value="/chercherDiplomes",method=RequestMethod.GET)
	public Page<Diplome> chercher(String mc,
			@RequestParam(name="page",defaultValue="0")int page,
			@RequestParam(name="size",defaultValue="5")int size){
		return diplomeRepository.chercherDiplomes("%"+mc+"%", new PageRequest(page, size));
	}
	
	@RequestMapping(value="/listDiplomes",method=RequestMethod.GET)
	public List <Diplome> listDiplomes(){
		return diplomeRepository.findAll();
	}
	
	@RequestMapping(value="/diplomes/{id}",method=RequestMethod.GET)
	public Diplome getDiplome(@PathVariable Long id){
		return diplomeRepository.findOne(id);
	}
	
	@RequestMapping(value="/diplomes/{id}",method=RequestMethod.DELETE)
	public void deleteDiplome(@PathVariable Long id){
		diplomeRepository.delete(id);
	}
	
	@RequestMapping(value="/diplomes/{id}",method=RequestMethod.PUT)
	public Diplome updateDiplome(@RequestBody Diplome e,@PathVariable ("id")Long id){
		e.setId(id);
		return diplomeRepository.saveAndFlush(e);
	}
	
		
	@RequestMapping(value="/diplomes",method=RequestMethod.POST)
	public Object saveDiplome(@RequestBody @Valid Diplome e,BindingResult bindingResult){
		if(bindingResult.hasErrors()){
			Map<String, Object> errors=new HashMap<>();
			errors.put("errors", true);
			for(FieldError fe:bindingResult.getFieldErrors()){
				errors.put(fe.getField(), fe.getDefaultMessage());
			}
			return  errors;
		}
		return diplomeRepository.save(e);
	}
	
	@RequestMapping(value="/updateDiplome",method=RequestMethod.POST)
	public String updateDiplome(@Valid Diplome dp,BindingResult bindingResult) throws Exception{
		 diplomeRepository.save(dp);
			return "";	
		
	}	
}
