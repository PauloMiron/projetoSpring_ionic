package com.PauloChaves.ProjetoCursoUdemy.dto;


import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

public class EmailDTO implements Serializable {

    @NotEmpty(message = "Preenchimento Obrigatorio")
    @Email(message = "Email invalido")
    private String email;

    public EmailDTO(){}

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
