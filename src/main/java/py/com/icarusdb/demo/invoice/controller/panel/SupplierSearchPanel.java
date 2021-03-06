/**
 * 
 */
package py.com.icarusdb.demo.invoice.controller.panel;

import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Model;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import py.com.icarusdb.demo.invoice.data.DatabaseManager;
import py.com.icarusdb.demo.invoice.model.Supplier;
import py.com.icarusdb.demo.util.AppHelper;
import py.com.icarusdb.demo.util.MessageUtil;
import py.com.icarusdb.demo.util.SearchPanel;
import py.com.icarusdb.demo.util.SearchPanelHelper;

/**
 * @author rgamarra
 *
 */
@Model
@SessionScoped
public class SupplierSearchPanel extends SearchPanelHelper implements SearchPanel
{
    /**
     * 
     */
    private static final long serialVersionUID = 1369844255621007634L;

    @Inject
    private EntityManager em;
    
    @Inject
    private DatabaseManager manager;
    
    
    private final static String qlString = "select o from Supplier o ";
    
    private String ruc = null;
    private String name = null;
    
    private List<Supplier> resultList = null;
    
    private Supplier selectedEntity = null;

    
    @Override
    public List<Supplier> getResultList()
    {
        return resultList;
    }
    
    public String getName()
    {
        return name;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    public String getRuc()
    {
        return ruc;
    }
    
    public void setRuc(String ruc)
    {
        this.ruc = ruc;
    }
    
    public Supplier getSelectedEntity()
    {
        return selectedEntity;
    }
    
    public void setSelectedEntity(Supplier selected)
    {
        this.selectedEntity = selected;
    }
    
    @Override
    public void search()
    {
        String restrics = " where ";
        boolean addAnd = false;
        
        if(ruc != null && !ruc.isEmpty())
        {
            addAnd = true;
            restrics += "lower(ruc) like '%"+ruc.trim().toLowerCase()+"%' ";
        }
        
        if(name != null && !name.isEmpty())
        {
            if (addAnd) restrics += "and ";
            restrics += "lower(name) like '%"+name.trim().toLowerCase()+"%' ";
        }
        
        if(restrics.trim().length() == 5)
        {
            restrics = "order by name asc";
        }
        else
        {
            restrics += "order by name asc";
        }
        
        resultList = em.createQuery(qlString + restrics, Supplier.class).getResultList();
    
        MessageUtil.showResults(resultList);
    }
    
    @Override
    public void clear()
    {
        selectedEntity = null;
        edition = false;
    }
    
    private void partcialClear()
    {
        name = null;
        resultList = null;
    }

    @Override
    public boolean isSelected()
    {
        return selectedEntity != null;
    }

    @Override
    public void handleClose()
    {
        partcialClear();
    }

    public void prepareNew()
    {
        selectedEntity = new Supplier();
    }

    public void save()
    {
        if (errors()) return;
        
        try
        {
            manager.persist(selectedEntity);
        }
        catch (Exception e)
        {
            FacesMessage mess = new FacesMessage(e.getMessage());
            FacesContext.getCurrentInstance().addMessage(null, mess);
            e.printStackTrace();
        }
        
    }

    private boolean errors()
    {
        FacesMessage message = null;
        
        String required = AppHelper.getBundleMessage("error.required.value");
        
        if(AppHelper.nothing(selectedEntity.getName()))
        {
            message = new FacesMessage(required +": " +AppHelper.getBundleMessage("label.name"));
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
        
        if(AppHelper.nothing(selectedEntity.getRuc()))
        {
            message = new FacesMessage(required +": " +AppHelper.getBundleMessage("label.ruc"));
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
        
        return (message != null);
    }
    
}
