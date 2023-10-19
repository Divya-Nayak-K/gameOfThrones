package com.demandfarm.got.model;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Actor {
    public String actorName;
    public String actorLink;
    public List<Integer> seasonsActive;
}
