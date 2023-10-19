package com.demandfarm.got;

import com.demandfarm.got.model.GOTCharacter;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SpringBootApplication
public class GotApplication {

    public static void main(String[] args) {
        SpringApplication.run(GotApplication.class, args);
    }

}
