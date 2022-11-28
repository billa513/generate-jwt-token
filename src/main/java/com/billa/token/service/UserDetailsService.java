package com.billa.token.service;

import com.billa.token.model.Credentials;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserDetailsService {


    public boolean verifyUserCredentials(String userName,String password){
   List<Credentials> response=mockCredentials();
    return response.stream().filter(Objects::nonNull).anyMatch(user->userName.equalsIgnoreCase(user.getUsername())
   && password.equals(user.getPassword()));

    }

    public List<Credentials> mockCredentials()  {

        try {
            File  file = new ClassPathResource("json/mockCredentials.json").getFile();
            return new ObjectMapper().readValue(file, new TypeReference<>() {
            });

        } catch (IOException e) {
            e.printStackTrace();

            return new ArrayList<>();
        }

    }
}
