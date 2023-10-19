package com.demandfarm.got.repository;

import com.demandfarm.got.entity.FamilyRelationsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface FamilyRelationsRepository extends JpaRepository<FamilyRelationsEntity, Long> {
    List<FamilyRelationsEntity> findByCharacterId(Long characterId);

}