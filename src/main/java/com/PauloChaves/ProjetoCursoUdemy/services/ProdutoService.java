package com.PauloChaves.ProjetoCursoUdemy.services;

import com.PauloChaves.ProjetoCursoUdemy.entities.Categoria;
import com.PauloChaves.ProjetoCursoUdemy.entities.Pedido;
import com.PauloChaves.ProjetoCursoUdemy.entities.Produto;
import com.PauloChaves.ProjetoCursoUdemy.repository.CategoriaRepository;
import com.PauloChaves.ProjetoCursoUdemy.repository.PedidoRepository;
import com.PauloChaves.ProjetoCursoUdemy.repository.ProdutoRepository;
import com.PauloChaves.ProjetoCursoUdemy.services.exception.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository repo;
    @Autowired
    private CategoriaRepository categoriaRepository;

    public Produto find(Long id){
            Optional<Produto> obj = repo.findById(id);
            return obj.orElseThrow(() -> new ObjectNotFoundException(
                    "Objeto não encontrado! Id: " + id + ", Tipo: " + Pedido.class.getName()));
        }

        public Page<Produto> search(String nome, List<Long> ids,Integer page,Integer LinesPerPage,String orderBy,String direction){
            PageRequest pageRequest = PageRequest.of(page,LinesPerPage, Sort.Direction.valueOf(direction),orderBy);
            List<Categoria> categorias = categoriaRepository.findAllById(ids);
            return repo.search(nome,categorias,pageRequest);


        }
    }

