package com.PauloChaves.ProjetoCursoUdemy.services;


import com.PauloChaves.ProjetoCursoUdemy.dto.ClienteDTO;
import com.PauloChaves.ProjetoCursoUdemy.dto.ClienteNewDTO;
import com.PauloChaves.ProjetoCursoUdemy.entities.Cidade;
import com.PauloChaves.ProjetoCursoUdemy.entities.Cliente;
import com.PauloChaves.ProjetoCursoUdemy.entities.Endereco;
import com.PauloChaves.ProjetoCursoUdemy.entities.enums.Perfil;
import com.PauloChaves.ProjetoCursoUdemy.entities.enums.TipoCliente;
import com.PauloChaves.ProjetoCursoUdemy.repository.ClienteRepository;
import com.PauloChaves.ProjetoCursoUdemy.repository.EnderecoRepository;
import com.PauloChaves.ProjetoCursoUdemy.security.UserSS;
import com.PauloChaves.ProjetoCursoUdemy.services.exception.AuthorizationExceptions;
import com.PauloChaves.ProjetoCursoUdemy.services.exception.DatabaseExceptions;
import com.PauloChaves.ProjetoCursoUdemy.services.exception.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    S3Service s3Service;
    @Autowired
    private BCryptPasswordEncoder pe;

    @Autowired
    private ClienteRepository repo;

    @Autowired
    private EnderecoRepository enderecoRepository;

    public Cliente find(Long id){

        UserSS user = UserService.authenticated();
        if(user == null || !user.hasRole(Perfil.ADMIN) && !id.equals(user.getId())){
            throw new AuthorizationExceptions("Acesso Negado");
        }
        Optional<Cliente> obj = repo.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException(
                "Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName()));
    }

    public List<Cliente> findAll(){
        return repo.findAll();
    }

    @Transactional
    public Cliente insert(Cliente obj){
        obj.setId(null);
        repo.save(obj);
        enderecoRepository.saveAll(obj.getEnderecos());
        return obj;
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
            throw new DatabaseExceptions("Não é possível excluir existe entidades relacionadas");
        }
    }

    public Page<Cliente> findPage(Integer page, Integer LinesPerPage, String orderBy, String direction){
        PageRequest pageRequest = PageRequest.of(page,LinesPerPage, Sort.Direction.valueOf(direction),orderBy);
        return repo.findAll(pageRequest);
    }

    public Cliente fromDTO(ClienteDTO objDto){
         return  new Cliente(objDto.getId(),objDto.getNome(),objDto.getEmail(),null,null,null);
    }

    public Cliente fromDTO(ClienteNewDTO objDto){
        Cliente cli = new Cliente(null,objDto.getNome(),objDto.getEmail(),objDto.getCpfOuCnpj(), TipoCliente.toEnum(objDto.getTipo()),pe.encode(objDto.getSenha()));
        Cidade cid = new Cidade(objDto.getCidadeId(),null,null);
        Endereco end = new Endereco(null,objDto.getLogradouro(),objDto.getNumero(),objDto.getComplemento(),
                objDto.getBairro(),objDto.getCep(),cli,cid);
        cli.getEnderecos().add(end);
        cli.getTelefones().add(objDto.getTelefone());
        if (objDto.getTelefone2() !=null){
            cli.getTelefones().add(objDto.getTelefone2());
        }
        if (objDto.getTelefone3()!= null){
            cli.getTelefones().add(objDto.getTelefone3());
        }
        return cli;
    }

    public URI uploadProfilePicture(MultipartFile multipartFile){
        UserSS user = UserService.authenticated();
        if (user == null){
            throw new AuthorizationExceptions("Acesso negado");
        }

        URI uri = s3Service.uploadFile(multipartFile);

        Cliente cli = find(user.getId());
        cli.setImageUrl(uri.toString());
        repo.save(cli);


        return uri;
    }
}




