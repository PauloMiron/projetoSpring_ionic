package com.PauloChaves.ProjetoCursoUdemy.services;


import com.PauloChaves.ProjetoCursoUdemy.dto.ClienteDTO;
import com.PauloChaves.ProjetoCursoUdemy.entities.Cliente;
import com.PauloChaves.ProjetoCursoUdemy.repository.ClienteRepository;
import com.PauloChaves.ProjetoCursoUdemy.services.exception.DatabaseExceptions;
import com.PauloChaves.ProjetoCursoUdemy.services.exception.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository repo;

    public Cliente find(Long id){
        Optional<Cliente> obj = repo.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException(
                "Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName()));
    }

    public List<Cliente> findAll(){
        return repo.findAll();
    }

    public Cliente insert(Cliente obj){
        obj.setId(null);
        return repo.save(obj);
    }
    public Cliente update(Long id,Cliente obj){
        try {
            Cliente entity = repo.getOne(id);
            updateData(entity, obj);
            return repo.save(entity);
        }catch (EntityNotFoundException e){
            throw new ObjectNotFoundException(
                    "Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName());
        }
    }

     private void updateData(Cliente entity, Cliente obj) {
        entity.setNome(obj.getNome());
        entity.setEmail(obj.getEmail());
    }

    public void delete(Long id){
        try {
            repo.deleteById(id);
        }catch (EmptyResultDataAccessException e){
            throw new ObjectNotFoundException("Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName());
        }catch (DataIntegrityViolationException e){
            throw new DatabaseExceptions("Não é possível excluir uma categoria que possui produtos");
        }
    }

    public Page<Cliente> findPage(Integer page, Integer LinesPerPage, String orderBy, String direction){
        PageRequest pageRequest = PageRequest.of(page,LinesPerPage, Sort.Direction.valueOf(direction),orderBy);
        return repo.findAll(pageRequest);
    }

    public Cliente fromDTO(ClienteDTO objDto){
         return  new Cliente(objDto.getId(),objDto.getNome(),objDto.getEmail(),null,null);

    }
}




