package com.PauloChaves.ProjetoCursoUdemy.services.validation;

import com.PauloChaves.ProjetoCursoUdemy.dto.ClienteNewDTO;
import com.PauloChaves.ProjetoCursoUdemy.entities.enums.TipoCliente;
import com.PauloChaves.ProjetoCursoUdemy.resources.exception.FieldMessage;
import com.PauloChaves.ProjetoCursoUdemy.services.validation.utils.BR;

import java.util.ArrayList;
import java.util.List;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ClienteInsertValidator implements ConstraintValidator<ClienteInsert, ClienteNewDTO> {
    @Override public void initialize(ClienteInsert ann) {
    }
    @Override
    public boolean isValid(ClienteNewDTO objDto, ConstraintValidatorContext context) {

        List<FieldMessage> list = new ArrayList<>();

        if (objDto.getTipo().equals(TipoCliente.PESSOAFISICA.getCod()) && !BR.isValidCpf(objDto.getCpfOuCnpj())){
            list.add(new FieldMessage("cpfOuCnpj","CPF invalido"));
        }
        if (objDto.getTipo().equals(TipoCliente.PESSOAJURIDICA.getCod()) && !BR.isValidCnpj(objDto.getCpfOuCnpj())){
            list.add(new FieldMessage("cpfOuCnpj","CNPJ invalido"));
        }



        for (FieldMessage e : list) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(e.getMessage())
                    .addPropertyNode(e.getFieldName()).addConstraintViolation();
        }
        return list.isEmpty();
    }
}
