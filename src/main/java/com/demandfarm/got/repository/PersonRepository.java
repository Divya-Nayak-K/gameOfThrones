package com.demandfarm.got.repository;

import com.demandfarm.got.entity.PersonEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface PersonRepository extends JpaRepository<PersonEntity, Long> {
    PersonEntity findByCharacterName(@Param("characterName") String characterName);

    List<PersonEntity> findByHouseName(@Param("houseName") String houseName);
}