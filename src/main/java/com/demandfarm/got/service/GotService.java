package com.demandfarm.got.service;

import com.demandfarm.got.entity.FamilyRelationsEntity;
import com.demandfarm.got.entity.PersonEntity;
import com.demandfarm.got.model.GOTCharacter;
import com.demandfarm.got.model.RelationType;
import com.demandfarm.got.repository.FamilyRelationsRepository;
import com.demandfarm.got.repository.PersonRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class GotService {
    private final PersonRepository personRepository;
    private final FamilyRelationsRepository familyRelationsRepository;

    public void writeCharacterData() {
        ObjectMapper objectMapper = new ObjectMapper();
        File jsonFile = new File("src/main/resources/got.json");

        try {

            List<GOTCharacter> gotCharacterList = objectMapper.readValue(jsonFile, new TypeReference<List<GOTCharacter>>() {
            });

            var gotCharacterEntities = gotCharacterList.stream().map(gotCharacter -> PersonEntity.builder().characterName(gotCharacter.getCharacterName()).characterLink(gotCharacter.getCharacterLink()).nickName(gotCharacter.getNickname()).royal(gotCharacter.isRoyal()).characterImageThumb(gotCharacter.getCharacterImageThumb()).characterImageFull(gotCharacter.getCharacterImageFull()).houseName(getHouseName(gotCharacter.getHouseName())).build()).distinct().toList();

            personRepository.saveAll(gotCharacterEntities);

            gotCharacterList.forEach(gotCharacter -> {
                var mainCharacter = getCharacterId(gotCharacter.getCharacterName());
                if (gotCharacter.getParents() != null && !gotCharacter.getParents().isEmpty() && mainCharacter != null) {
                    var parentEntities = gotCharacter.getParents().stream().filter(gotParent -> getCharacterId(gotParent) != null).map(gotParent -> FamilyRelationsEntity.builder().characterId(mainCharacter).relativeToCharacterId(getCharacterId(gotParent)).relationType(String.valueOf(RelationType.PARENT)).build()).toList();
                    familyRelationsRepository.saveAll(parentEntities);
                }
                if (gotCharacter.getSiblings() != null && !gotCharacter.getSiblings().isEmpty() && mainCharacter != null) {
                    var siblingEntities = gotCharacter.getSiblings().stream().filter(sibling -> getCharacterId(sibling) != null).map(sibling -> FamilyRelationsEntity.builder().characterId(mainCharacter).relativeToCharacterId(getCharacterId(sibling)).relationType(String.valueOf(RelationType.SIBLING)).build()).toList();
                    familyRelationsRepository.saveAll(siblingEntities);
                }
                if (gotCharacter.getMarriedEngaged() != null && !gotCharacter.getMarriedEngaged().isEmpty() && mainCharacter != null) {
                    var marriedEntities = gotCharacter.getMarriedEngaged().stream().filter(gotSpouse -> getCharacterId(gotSpouse) != null).map(gotSpouse -> FamilyRelationsEntity.builder().characterId(mainCharacter).relativeToCharacterId(getCharacterId(gotSpouse)).relationType(String.valueOf(RelationType.MARRIED)).build()).toList();
                    familyRelationsRepository.saveAll(marriedEntities);
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Long getCharacterId(String gotCharacter) {
        var person = personRepository.findByCharacterName(gotCharacter);
        return person != null ? person.getCharacterId() : null;
    }

    private String getHouseName(Object houseName) {
        if (houseName instanceof ArrayList) {
            // It's an array
            var houseNameArrayVal = (ArrayList<String>) houseName;
            return houseNameArrayVal.get(0);
        }
        return (String) houseName;
    }

    public Map<String, Object> getFamilyTree(String houseName) {
        Map<String, Object> treeData = new HashMap<>();
        treeData.put("name", houseName); // Root node name

        List<PersonEntity> characters = personRepository.findByHouseName(houseName);
        List<FamilyRelationsEntity> relations = new ArrayList<>();

        for (PersonEntity character : characters) {
            // Find family relations for each character
            List<FamilyRelationsEntity> characterRelations = familyRelationsRepository.findByCharacterId(character.getCharacterId());
            relations.addAll(characterRelations);
        }

        List<Map<String, Object>> children = new ArrayList<>();

        for (FamilyRelationsEntity relation : relations) {
            Map<String, Object> child = new HashMap<>();
            child.put("relation", relation.getRelationType());
            child.put("relatedTo", personRepository.findById(relation.getRelativeToCharacterId()).stream().map(personEntity -> personEntity.getCharacterName()));
            child.put("character", personRepository.findById(relation.getCharacterId()).stream().map(personEntity -> personEntity.getCharacterName()));
            children.add(child);
        }
        System.out.println(children);

        
        treeData.put("children", children);

        return treeData;
    }

    public PersonEntity getSelectedCharacterInfo(String selectedCharacter) {
       return personRepository.findByCharacterName(selectedCharacter);
    }
}
