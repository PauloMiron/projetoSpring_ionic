package com.PauloChaves.ProjetoCursoUdemy.services;

import com.PauloChaves.ProjetoCursoUdemy.dto.CategoriaDTO;
import com.PauloChaves.ProjetoCursoUdemy.entities.Categoria;
import com.PauloChaves.ProjetoCursoUdemy.repository.CategoriaRepository;
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
public class CategoriaService {

    @Autowired
    private CategoriaRepository repo;

    public Categoria find(Long id){
            Optional<Categoria> obj = repo.findById(id);
            return obj.orElseThrow(() -> new ObjectNotFoundException(
                    "Objeto não encontrado! Id: " + id + ", Tipo: " + Categoria.class.getName()));
        }

    public List<Categoria> findAll(){
        return repo.findAll();
    }

     public Categoria insert(Categoria obj){
        obj.setId(null);
        return repo.save(obj);
     }
    public Categoria update(Long id,Categoria obj){
        try {
            Categoria entity = repo.getOne(id);
            updateData(entity, obj);
            return repo.save(entity);
        }catch (EntityNotFoundException e){
            throw new ObjectNotFoundException(
                    "Objeto não encontrado! Id: " + id + ", Tipo: " + Categoria.class.getName());
        }
    }

    private void updateData(Categoria entity, Categoria obj) {
        entity.setNome(obj.getNome());
    }

    public void delete(Long id){
        try {
            repo.deleteById(id);
        }catch (EmptyResultDataAccessException e){
            throw new ObjectNotFoundException("Objeto não encontrado! Id: " + id + ", Tipo: " + Categoria.class.getName());
        }catch (DataIntegrityViolationException e){
            throw new DatabaseExceptions("Não é possível excluir uma categoria que possui produtos");
        }
    }

    public Page<Categoria> findPage(Integer page,Integer LinesPerPage,String orderBy,String direction){
        PageRequest pageRequest = PageRequest.of(page,LinesPerPage, Sort.Direction.valueOf(direction),orderBy);
        return repo.findAll(pageRequest);
    }

    public Categoria fromDTO(CategoriaDTO objDto){
        return new Categoria(objDto.getId(),objDto.getNome());
    }

    }

