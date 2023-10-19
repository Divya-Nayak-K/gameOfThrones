package com.demandfarm.got.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@Entity
@Table(name = "person")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class PersonEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long characterId;
    private String characterName;
    private String houseName;
    private String characterLink;
    private String nickName;
    private String characterImageThumb;
    private String characterImageFull;
    private Boolean royal;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PersonEntity person = (PersonEntity) o;
        return Objects.equals(characterName, person.characterName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(characterName);
    }
}
