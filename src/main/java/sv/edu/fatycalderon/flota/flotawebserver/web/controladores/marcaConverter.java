/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.edu.fatycalderon.flota.flotawebserver.web.controladores;

import javax.el.ValueExpression;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import sv.edu.fatycalderon.flota.flotawebserver.datos.acceso.MarcaFacade;
import sv.edu.uesocc.ingenieria.prn335_2018.flota.datos.definicion.Marca;

/**
 *
 * @author fatycalderon
 */

@FacesConverter("marcaConverter")
public class marcaConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
        if(value != null && value.trim().length() > 0) {
            try {
                MarcaBean service = (MarcaBean) fc.getExternalContext().getApplicationMap().get("marcaBean");
                return service.getListaMarcas().get(Integer.parseInt(value));
            } catch(NumberFormatException e) {
                throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error", "Not a valid MARCA."));
            }
        }
        else {
            return null;
        }
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object t) {
        if(t != null) {
            return String.valueOf(((Marca) t).getIdMarca());
        }
        else {
            return null;
        }
        
        
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
