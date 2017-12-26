package org.opendevup.entities;

import java.io.Serializable;
/*import java.util.Date;*/




import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

@Entity
public class Etudiant implements Serializable {
	@Id
	@GeneratedValue
	private Long id;
	@NotEmpty
	private String nom;
	@NotEmpty
	private String prenom;
	/*@NotEmpty*/
	private String titreposte;
	@Email
	private String email;
	private String diplome;
	private String promotion;
	private String competencesCles;
	private String experience;
	private String photo;
	/*private Date dateNaissance;*/

	

	/*private String photo;*/
	public Etudiant() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Etudiant(String nom, String prenom, String titreposte,
			String email, String diplome, String promotion,
			String competencesCles, String experience, String photo) {
		super();
		this.nom = nom;
		this.prenom = prenom;
		this.titreposte = titreposte;
		this.email = email;
		this.diplome = diplome;
		this.promotion = promotion;
		this.competencesCles = competencesCles;
		this.experience = experience;
		this.photo = photo;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getPrenom() {
		return prenom;
	}
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
	public String getTitreposte() {
		return titreposte;
	}
	public void setTitreposte(String titreposte) {
		this.titreposte = titreposte;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getDiplome() {
		return diplome;
	}
	public void setDiplome(String diplome) {
		this.diplome = diplome;
	}
	public String getPromotion() {
		return promotion;
	}
	public void setPromotion(String promotion) {
		this.promotion = promotion;
	}
	public String getCompetencesCles() {
		return competencesCles;
	}
	public void setCompetencesCles(String competencesCles) {
		this.competencesCles = competencesCles;
	}
	public String getExperience() {
		return experience;
	}
	public void setExperience(String experience) {
		this.experience = experience;
	}
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	
	
	
	
}
