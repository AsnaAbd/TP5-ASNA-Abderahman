package org.opendevup.entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
public class Diplome implements Serializable {
	@Id
	@GeneratedValue
	private Long id;
	@NotEmpty
	private String nomDiplome;
	private String niveau;
	private String specialite;
	@NotEmpty
	private String description;

	public Diplome() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Diplome(String nomDiplome, String niveau, String specialite,
			String description) {
		super();
		this.nomDiplome = nomDiplome;
		this.niveau = niveau;
		this.specialite = specialite;
		this.description = description;
	}



	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNomDiplome() {
		return nomDiplome;
	}

	public void setNomDiplome(String nomDiplome) {
		this.nomDiplome = nomDiplome;
	}

	public String getNiveau() {
		return niveau;
	}

	public void setNiveau(String niveau) {
		this.niveau = niveau;
	}

	public String getSpecialite() {
		return specialite;
	}

	public void setSpecialite(String specialite) {
		this.specialite = specialite;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	
}
