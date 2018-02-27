package com.ingetis.ikheiry.dao;

import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ingetis.ikheiry.model.NoteDeFrais;
import com.ingetis.ikheiry.model.Salarie;
import com.ingetis.ikheiry.model.enumeartion.EtatNoteDeFrais;

public interface INoteDeFraisRepository extends JpaRepository<NoteDeFrais, Long>{
	// Convention Spring Boot
	public Page<NoteDeFrais> findByDateDemandeRemboursement(Date date, Pageable pageable);
	public Page<NoteDeFrais> findBySalarie(Salarie salarie, Pageable pageable);
	public Page<NoteDeFrais> findByEtat(EtatNoteDeFrais etat, Pageable pageable);
	
	
	// salarie Id
	public Page<NoteDeFrais> findBySalarieIdOrderByIdDesc(Long id, Pageable pageable);
	public Page<NoteDeFrais> findBySalarieIdAndEtatLikeOrderByIdDesc(Long id, EtatNoteDeFrais etat, Pageable pageable);
	public Page<NoteDeFrais> findBySalarieIdAndEtatLikeOrEtatLikeOrderByIdDesc(Long id, EtatNoteDeFrais etat1, EtatNoteDeFrais etat2,  Pageable pageable);
	
	@Query("select n from NoteDeFrais n where n.salarie.id=:id and (n.etat like :etat1 or n.etat like :etat2) order by n.id desc")
	public Page<NoteDeFrais> getNoteBySalarieIdAndEtatLikeOrEtatLikeOrderByIdDesc(@Param("id") Long id, @Param("etat1") EtatNoteDeFrais etat1, @Param("etat2") EtatNoteDeFrais etat2,  Pageable pageable);
	public Page<NoteDeFrais> findBySalarieIdAndEtatNotLikeOrderByIdDesc(Long id, EtatNoteDeFrais etat, Pageable pageable);
	public Page<NoteDeFrais> findBySalarieIdAndEtatNotLikeOrEtatNotLikeOrderByIdDesc(Long id, EtatNoteDeFrais etat1, EtatNoteDeFrais etat2, Pageable pageable);
	@Query("select n from NoteDeFrais n where n.salarie.id=:id and n.etat not like :etat1 and n.etat not like :etat2 order by n.id desc")
	public Page<NoteDeFrais> getNoteBySalarieIdAndEtatNotLikeOrEtatNotLikeOrderByIdDesc(@Param("id") Long id, @Param("etat1") EtatNoteDeFrais etat1, @Param("etat2") EtatNoteDeFrais etat2,  Pageable pageable);
	
	
	
	// RH
	public Page<NoteDeFrais> findByEtatLikeOrderByIdDesc(EtatNoteDeFrais etat, Pageable pageable);
	public Page<NoteDeFrais> findByEtatLikeOrEtatLikeOrderByIdDesc(EtatNoteDeFrais etat1, EtatNoteDeFrais etat2, Pageable pageable);
	@Query("select n from NoteDeFrais n where n.etat like :etat1 or n.etat like :etat2 order by n.id desc")
	public Page<NoteDeFrais> getNoteByEtatLikeOrEtatLikeOrderByIdDesc(@Param("etat1") EtatNoteDeFrais etat1, @Param("etat2") EtatNoteDeFrais etat2,  Pageable pageable);
	public Page<NoteDeFrais> findByEtatNotLikeOrderByIdDesc(EtatNoteDeFrais etat, Pageable pageable);
	public Page<NoteDeFrais> findByEtatNotLikeOrEtatNotLikeOrderByIdDesc(EtatNoteDeFrais etat1, EtatNoteDeFrais etat2, Pageable pageable);
	@Query("select n from NoteDeFrais n where n.etat not like :etat1 and n.etat not like :etat2 order by n.id desc")
	public Page<NoteDeFrais> getNoteEtatNotLikeOrEtatNotLikeOrderByIdDesc(@Param("etat1") EtatNoteDeFrais etat1, @Param("etat2") EtatNoteDeFrais etat2,  Pageable pageable);
	
	
	@Query("select n from NoteDeFrais n where n.salarie.id=:id")
	public Page<NoteDeFrais> getNoteBySalarieId(@Param("id") Long id, Pageable pageable);
}
