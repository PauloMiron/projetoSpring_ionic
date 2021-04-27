package com.PauloChaves.ProjetoCursoUdemy.entities;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.tomcat.jni.Local;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Objects;

@Entity
public class ItemPedido {

    @JsonIgnore
    @EmbeddedId
    private ItemPedido_PK id = new ItemPedido_PK();
    private Double desconto;
    private Integer quantidade;
    private Double preco;

    public ItemPedido(){}

    public ItemPedido(Pedido pedido,Produto produto, Double desconto, Integer quantidade, Double preco) {
        id.setPedido(pedido);
        id.setProduto(produto);
        this.desconto = desconto;
        this.quantidade = quantidade;
        this.preco = preco;
    }

    public double getSubTotal(){
        return (preco - desconto ) * quantidade;
    }
    @JsonIgnore
    public Pedido getPedido(){
        return id.getPedido();
    }

    public void setPedido(Pedido pedido){
        id.setPedido(pedido);
    }

    public Produto getProduto(){
        return id.getProduto();
    }

    public void setProduto(Produto produto){
        id.setProduto(produto);
    }
    public ItemPedido_PK getId() {
        return id;
    }

    public void setId(ItemPedido_PK id) {
        this.id = id;
    }

    public Double getDesconto() {
        return desconto;
    }

    public void setDesconto(Double desconto) {
        this.desconto = desconto;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemPedido that = (ItemPedido) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
      StringBuilder builder = new StringBuilder();
      builder.append(getProduto().getNome());
      builder.append(", Qte: ");
      builder.append(getQuantidade());
      builder.append(", Preço unitário: ");
      builder.append(nf.format(getPreco()));
      builder.append(", SubTotal: ");
      builder.append(nf.format(getSubTotal()));
      builder.append("\n");
      return builder.toString();
    }
}
