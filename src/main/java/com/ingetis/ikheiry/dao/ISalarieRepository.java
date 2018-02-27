package com.ingetis.ikheiry.dao;

import java.util.Date;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ingetis.ikheiry.model.Salarie;
import com.ingetis.ikheiry.model.enumeartion.TypeSalarie;

public interface ISalarieRepository extends JpaRepository<Salarie, Long> {
	// Convention Spring Boot
	public Page<Salarie> findByNom(String nom, Pageable pageable);
	public Page<Salarie> findByPrenom(String prenom, Pageable pageable);
	public Page<Salarie> findByDateNaissance(Date dateNaissance, Pageable pageable);
	public Page<Salarie> findByNumeroTel(String numeroTel, Pageable pageable);
	public Page<Salarie> findByDateEmbauche(Date dateEmbauche, Pageable pageable);
	public Page<Salarie> findByPoste(String poste, Pageable pageable);
	public Page<Salarie> findByNumeroSecuriteSociale(Long numeroSecuriteSociale, Pageable pageable);
	public Page<Salarie> findByType(TypeSalarie type, Pageable pageable);
	public Salarie findByLogin(String login);
	
	// Requete personnalisée 
	@Query("select s from Salarie s where s.login like :login and s.motDePasse like :motDePasse")
	public Salarie loginSalarie(@Param("login") String login, @Param("motDePasse") String motDePasse);
	
	@Query("select s from Salarie s where s.nom like :nom")
	public Page<Salarie> getSalarieParNom(@Param("nom") String nom, Pageable page);
	
	@Query("select s from Salarie s where s.prenom like :prenom")
	public Page<Salarie> getSalarieParPrenom(@Param("prenom") String prenom, Pageable page);
}
