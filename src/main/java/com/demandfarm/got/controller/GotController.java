package com.demandfarm.got.controller;

import com.demandfarm.got.entity.PersonEntity;
import com.demandfarm.got.repository.FamilyRelationsRepository;
import com.demandfarm.got.repository.PersonRepository;
import com.demandfarm.got.service.GotService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/characters")
@AllArgsConstructor
public class GotController {
    private final GotService gotService;
    private final PersonRepository personRepository;
    private final FamilyRelationsRepository familyRelationsRepository;

    @GetMapping("/write-data")
    public ResponseEntity<String> writeCharacterData() {
        gotService.writeCharacterData();
        return ResponseEntity.status(HttpStatus.CREATED).body("Data saved successfully");
    }

    @GetMapping("/houses")
    public List<String> getHouses() {
        return personRepository.findAll().stream()
                .filter(person -> person.getHouseName() != null)
                .map(PersonEntity::getHouseName)
                .distinct()
                .collect(Collectors.toList());
    }

    @GetMapping("/familytree/{houseName}")
    public ResponseEntity<Map<String, Object>> getCharactersByHouseName(@PathVariable String houseName) {
        Map<String, Object> characters = gotService.getFamilyTree(houseName);

        if (characters.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(characters, HttpStatus.OK);
        }
    }

    @GetMapping("/card-info/{selectedCharacter}")
    public ResponseEntity<PersonEntity> getSelectedCharacterInfo(@PathVariable String selectedCharacter) {
        PersonEntity selectedCharacterInfo = gotService.getSelectedCharacterInfo(selectedCharacter);

        if (selectedCharacterInfo == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(selectedCharacterInfo, HttpStatus.OK);
        }
    }
}
