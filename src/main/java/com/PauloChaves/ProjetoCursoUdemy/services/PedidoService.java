package com.PauloChaves.ProjetoCursoUdemy.services;

import com.PauloChaves.ProjetoCursoUdemy.entities.*;
import com.PauloChaves.ProjetoCursoUdemy.entities.enums.EstadoPagamento;
import com.PauloChaves.ProjetoCursoUdemy.repository.ItemPedidoRepository;
import com.PauloChaves.ProjetoCursoUdemy.repository.PagamentoRepository;
import com.PauloChaves.ProjetoCursoUdemy.repository.PedidoRepository;
import com.PauloChaves.ProjetoCursoUdemy.repository.ProdutoRepository;
import com.PauloChaves.ProjetoCursoUdemy.security.UserSS;
import com.PauloChaves.ProjetoCursoUdemy.services.exception.AuthorizationExceptions;
import com.PauloChaves.ProjetoCursoUdemy.services.exception.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository repo;

    @Autowired
    private BoletoService boletoService;

    @Autowired
    private PagamentoRepository pagamentoRepository;

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private ItemPedidoRepository itemPedidoRepository;

    @Autowired
    ClienteService clienteService;

    @Autowired
    private EmailService emailService;

    public Pedido find(Long id){
        Optional<Pedido> obj = repo.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException(
                "Objeto não encontrado! Id: " + id + ", Tipo: " + Pedido.class.getName()));
    }

    public Pedido insert(Pedido obj) {
        obj.setId(null);
        obj.setInstante(new Date());
        obj.setCliente(clienteService.find(obj.getCliente().getId()));
        obj.getPagamento().setEstado(EstadoPagamento.PENDENTE);
        obj.getPagamento().setPedido(obj);
        if (obj.getPagamento() instanceof PagamentoComBoleto) {
            PagamentoComBoleto pagto = (PagamentoComBoleto) obj.getPagamento();
            boletoService.preencherPagamentoComBoleto(pagto, obj.getInstante());
        }
        obj = repo.save(obj);
        pagamentoRepository.save(obj.getPagamento());
        for (ItemPedido ip : obj.getItens()) {
            ip.setDesconto(0.0);
            ip.setProduto(produtoService.find(ip.getProduto().getId()));
            ip.setPreco(ip.getProduto().getPreco());
            ip.setPedido(obj);
        }
        itemPedidoRepository.saveAll(obj.getItens());
        emailService.sendOrderConfirmationEmail(obj);
        return obj;
    }

    public Page<Pedido> findPage(Integer page, Integer LinesPerPage, String orderBy, String direction){

        UserSS user = UserService.authenticated();
        if (user == null){
            throw new AuthorizationExceptions("Acesso Negado");
        }
        PageRequest pageRequest = PageRequest.of(page,LinesPerPage, Sort.Direction.valueOf(direction),orderBy);
        Cliente cliente = clienteService.find(user.getId());
        return repo.findByCliente(cliente,pageRequest);
    }

}

