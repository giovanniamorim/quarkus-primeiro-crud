package com.dev;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.dev.dto.CadastroProdutoDTO;
import com.dev.model.Produto;

@Path("/produtos")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ProdutoResource {
    
    @GET
    public List<Produto> listarTodos(){
        return Produto.listAll();
    }

    @POST
    @Transactional
    public void novoProduto(CadastroProdutoDTO dto){
        Produto p = new Produto();
        p.nome = dto.nome;
        p.valor = dto.valor;

        p.persist();
    }

    @PUT
    @Path("{id}")
    @Transactional
    public void atualizarProduto(@PathParam("id") Long id, CadastroProdutoDTO dto){
        Optional<Produto> produtoOpt = Produto.findByIdOptional(id);
        if(produtoOpt.isPresent()){
            Produto produto = produtoOpt.get();
            produto.nome = dto.nome;
            produto.valor = dto.valor;
            produto.persist();
        } else {
            throw new NotFoundException("Produto não encontrado");
        }
    }

    @DELETE
    @Path("{id}")
    @Transactional
    public void deletarproduto(@PathParam("id") Long id){
        Optional<Produto> produtoOptional = Produto.findByIdOptional(id);

        produtoOptional.ifPresentOrElse(Produto::delete, () -> {
            throw new NotFoundException("Produto não encontrado");
        });

    }
}
