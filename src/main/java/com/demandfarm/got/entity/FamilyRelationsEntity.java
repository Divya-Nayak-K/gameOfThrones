package com.demandfarm.got.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "family_relations")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class FamilyRelationsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long relationId;
    private Long characterId;
    private Long relativeToCharacterId;
    private String relationType;
}
