package com.demandfarm.got.model;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GOTCharacter {
    private String characterName;
    private boolean royal;
    private Object houseName;
    private List<String> parents;
    private String characterLink;
    private String actorName;
    private String actorLink;
    private List<String> siblings;
    private List<String> killedBy;
    private String characterImageThumb;
    private String characterImageFull;
    private String nickname;
    private List<String> killed;
    private List<String> servedBy;
    private List<String> parentOf;
    private List<String> marriedEngaged;
    private List<String> serves;
    private boolean kingsguard;
    private List<String> guardedBy;
    private List<Actor> actors;
    private List<String> guardianOf;
    private List<String> allies;
    private List<String> abductedBy;
    private List<String> abducted;
}
