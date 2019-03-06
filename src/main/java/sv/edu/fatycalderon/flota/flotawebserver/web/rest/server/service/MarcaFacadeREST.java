/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.edu.fatycalderon.flota.flotawebserver.web.rest.server.service;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import sv.edu.fatycalderon.flota.flotawebserver.datos.acceso.MarcaFacade;
import sv.edu.uesocc.ingenieria.prn335_2018.flota.datos.definicion.Marca;

/**
 *
 * @author fatycalderon
 */

@Path("Marca")
@RequestScoped
public class MarcaFacadeREST implements Serializable{
   @Inject
    MarcaFacade marcaFacade;

    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    public void create(Marca entity) {
        if(marcaFacade!=null && entity!=null){
            this.marcaFacade.create(entity);
        }
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Integer id,Marca entity) {
        if(marcaFacade!=null && entity!=null){
        this.marcaFacade.edit(entity);
    
        }
    }    

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Integer id,Marca entity) {
        if(marcaFacade!=null && entity!=null){
        this.marcaFacade.remove(entity);
    
        }
    }

    @GET
    @Path("id/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Marca find(@PathParam("id") Integer id) {
        if(marcaFacade!=null && id!=null){
       return this.marcaFacade.find(id);
    
        }
      return (Marca) Collections.EMPTY_LIST;
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Path("{findAll}")
    public List<Marca> findAll() {
        if(marcaFacade!=null){
       return this.marcaFacade.findAll();
    
        }
      return Collections.EMPTY_LIST;
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_JSON})
    public List<Marca> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        if(marcaFacade!=null){
        return this.marcaFacade.findRange(new int[]{from,to});
    
        }
       return Collections.EMPTY_LIST;
    }

    @GET
    @Path("count")
    @Produces(MediaType.APPLICATION_JSON)
    public String countREST() {
        if(marcaFacade!=null){
        return String.valueOf(this.marcaFacade.count());
        }
        return "0";
    }

    
}
