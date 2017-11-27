package org.opendevup;


import java.text.ParseException;
import java.util.List;

import org.opendevup.dao.DiplomeRepository;
import org.opendevup.dao.EtudiantRepository;
import org.opendevup.entities.Diplome;
import org.opendevup.entities.Etudiant;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class TpSpringMvcApplication {

	public static void main(String[] args) throws ParseException {
		ApplicationContext ctx = SpringApplication.run(TpSpringMvcApplication.class, args);
		EtudiantRepository etudaintRepository=ctx.getBean(EtudiantRepository.class);
		DiplomeRepository diplomeRepository=ctx.getBean(DiplomeRepository.class);
	    /*etudaintRepository.save(new Etudiant("asna", "abderahman", "Développeur","abderahman.asna@etu.univ-amu.fr","M2 Génie Logiciel","Java JEE","picture","hh","hh"));
		diplomeRepository.save(new Diplome("Master GL", "bac+5", "AS","WEB"));*/
					
		List<Etudiant> etds=etudaintRepository.findAll();
		
		etds.forEach(e->System.out.println(e.getNom()));
	}
}
