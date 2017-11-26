package org.opendevup.dao;


import java.util.List;

import org.opendevup.entities.Diplome;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface DiplomeRepository extends 
			JpaRepository<Diplome, Long>{
	public List<Diplome> findByNomDiplome(String n);
	public Page<Diplome> findByNomDiplome(String n,Pageable pageable);
	@Query("select e from Diplome e where e.nomDiplome like :x")
	public Page<Diplome> chercherDiplomes(@Param("x")String mc,Pageable pageable);
	
}
