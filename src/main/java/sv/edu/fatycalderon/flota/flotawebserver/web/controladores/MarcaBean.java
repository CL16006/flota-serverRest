/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.edu.fatycalderon.flota.flotawebserver.web.controladores;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import org.primefaces.PrimeFaces;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import sv.edu.fatycalderon.flota.flotawebserver.datos.acceso.MarcaFacade;
import sv.edu.uesocc.ingenieria.prn335_2018.flota.datos.definicion.Marca;


/**
 *
 * @author fatycalderon
 */
@Named(value = "marcaBean")
@ViewScoped
public class MarcaBean implements Serializable{  
    
    @EJB
    MarcaFacade marcaFacade;
    
    private Marca marca;
    private List<Marca> listaMarcas;
    private LazyDataModel<Marca> marcaLazy;
    private Marca selectedMarca;
    
    @PostConstruct
    public void postConstruct(){
        iniciarMarca();
        marcaLDM();
        this.marcaLazy.setRowIndex(-1);
    }
    public void iniciarMarca(){
        this.marca=new Marca();
    }
    
    public List<String> completeText(String query) {
        List<String> results = new ArrayList<String>();
        for(int i = 0; i < 10; i++) {
            results.add(query + i);
        }
         
        return results;
    }
    
    public List<Marca> complete(String query){
        List<Marca> listaTodos = marcaFacade.findAll();
        List<Marca> results = new ArrayList<Marca>();
       try{
           for (int i = 0; i < listaTodos.size(); i++) {
            Marca m = listaTodos.get(i);
            if(m.getNombre().toLowerCase().contains(query)) {
                results.add(m);
            }
        }
           
           return results;
           
       }catch(Exception e){
           Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
       }
        return results;
    }
    
    public void marcaLDM(){
        try{
            this.marcaLazy=new LazyDataModel<Marca>(){
                @Override
                public Object getRowKey(Marca object) {
                   if(object!=null){
                       return object.getIdMarca();
                   }
                   return null;
                }
                
                @Override
                public Marca getRowData(String rowKey) {
                    if(rowKey !=null && !rowKey.isEmpty() && this.getWrappedData()!=null){
                        try{
                           Integer search=new Integer(rowKey); 
                           for(Marca m: (List<Marca>)getWrappedData()){
                               if(m.getIdMarca().compareTo(search)==0){
                                   return m;
                               }
                           }
                        }catch(Exception e){
                              Logger.getLogger(getClass().getName()).log(Level.SEVERE,e.getMessage(),e);
                        }
                    }
                    return null;  
                }                                  

                @Override
                public List<Marca> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
                List<Marca>  salida=new ArrayList(); 
              
                 try{
                     if(marcaFacade!=null){
                         this.setRowCount(marcaFacade.count());
                         salida=marcaFacade.findRange2(first, pageSize);
                     }
                     
                     
                 }catch(Exception e){
                      Logger.getLogger(getClass().getName()).log(Level.SEVERE,e.getMessage(),e);
                 }
                 return salida;
                }

                @Override
                public int getRowIndex() {
                    return super.getRowIndex(); //To change body of generated methods, choose Tools | Templates.
                }
               
            };
        }catch(Exception e){
              Logger.getLogger(getClass().getName()).log(Level.SEVERE,e.getMessage(),e);
        }
    }
    
    
    /**
     * Si había una fila seleccionada se deselecciona, y cuando se seleccionan los botones hace que se actualice despues de presionarlos
     */
    public static void clearSelection() {
        PrimeFaces.current().executeScript("wdgList.clearSelection()");
    }
/**
 * este metodo es usado para mostrar un mensaje
 * @param summary
 * @param detail 
 */
    public void addMessage(String summary, String detail) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, detail);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
    
    /**
     * este metodo es ejecutado al seleccionar una fila.
     * @param event 
     */
    public void onRowSelect(SelectEvent event){
       this.marca= (Marca) event.getObject();
    }
    
    /**
     * este metodo es ejecutado al deseleccionar una fila.
     * @param event 
     */
    public void onRowUnselect(UnselectEvent event){
        this.marca=new Marca();
        this.marcaLazy.setRowIndex(-1);
    }
    
    public void postHandler(ActionEvent ae){
        try{
            if(this.marca!=null && this.marcaFacade!=null){
                marcaFacade.create(marca);
                postConstruct();
                addMessage("EXITO", "Se agrego un registro con éxito");
            }
            
        }catch(Exception e){
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
        }
    }
    
    public void deleteHandler(ActionEvent ae){
        try{
            if(this.marca!=null && this.marcaFacade!=null){
                marcaFacade.remove(marca);
                postConstruct();
                addMessage("EXITO", "Se ha eliminado un registro con éxito");
            }
            
        }catch(Exception e){
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
        }
    }
    
    public void editHandler(ActionEvent ae){
        try{
            if(this.marca!=null && this.marcaFacade!=null){
                marcaFacade.edit(marca);
                postConstruct();
                addMessage("EXITO", "Se ha modificado un registro con éxito");
            }
            
        }catch(Exception e){
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
        }
    }
    
    public void cancelarHandler(ActionEvent ae){
        if(this.marca!=null && this.marcaFacade!=null){
            this.marca=new Marca();
        }
    }
    //getters y setters

    public MarcaFacade getMarcaFacade() {
        return marcaFacade;
    }

    public void setMarcaFacade(MarcaFacade marcaFacade) {
        this.marcaFacade = marcaFacade;
    }

    public Marca getMarca() {
        return marca;
    }

    public void setMarca(Marca marca) {
        this.marca = marca;
    }

    public List<Marca> getListaMarcas() {
        return listaMarcas;
    }

    public void setListaMarcas(List<Marca> listaMarcas) {
        this.listaMarcas = listaMarcas;
    }

    public LazyDataModel<Marca> getMarcaLazy() {
        return marcaLazy;
    }

    public void setMarcaLazy(LazyDataModel<Marca> marcaLazy) {
        this.marcaLazy = marcaLazy;
    }

    public Marca getSelectedMarca() {
        return selectedMarca;
    }

    public void setSelectedMarca(Marca selectedMarca) {
        this.selectedMarca = selectedMarca;
    }
    
    
    
}
