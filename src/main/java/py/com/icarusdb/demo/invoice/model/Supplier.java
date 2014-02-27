package py.com.icarusdb.demo.invoice.model;

// Generated Feb 8, 2014 3:17:30 PM by Hibernate Tools 4.0.0

import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import py.com.icarusdb.common.EntityInterface;
import py.com.icarusdb.common.IDBProperties;

/**
 * Supplier generated by hbm2java
 */
@Entity
@Table(name = "supplier", schema = "public")
public class Supplier implements EntityInterface
{
    /**
     * 
     */
    private static final long serialVersionUID = 8187248220826370850L;

    private static final String GENERATOR = "SUPPLIER_ID_GENERATOR";

    private Long id;
    private String name;
    private String ruc;
    private String address;
    private String telephone;
    private String contactName;
    private Set<Invoice> invoices = new HashSet<Invoice>(0);

    public Supplier() {}
    
    @Id
    @SequenceGenerator(name = GENERATOR, sequenceName = "public.supplier_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = GENERATOR)
    @Column(name = "id", unique = true, nullable = false)
    public Long getId()
    {
        return this.id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    @Column(name = "name", nullable = false, length = 100)
    public String getName()
    {
        return this.name;
    }

    public void setName(String name)
    {
        this.name = name;
    }
    
    @Column(name = "ruc" , length = 20)
    public String getRuc()
    {
        return ruc;
    }
    
    public void setRuc(String ruc)
    {
        this.ruc = ruc;
    }

    @Column(name = "address", length = 200)
    public String getAddress()
    {
        return this.address;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }

    @Column(name = "telephone", length = 20)
    public String getTelephone()
    {
        return this.telephone;
    }

    public void setTelephone(String telephone)
    {
        this.telephone = telephone;
    }

    @Column(name = "contact_name", length = 50)
    public String getContactName()
    {
        return this.contactName;
    }

    public void setContactName(String contactName)
    {
        this.contactName = contactName;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "supplier")
    public Set<Invoice> getInvoices()
    {
        return this.invoices;
    }

    public void setInvoices(Set<Invoice> invoices)
    {
        this.invoices = invoices;
    }

    @Override
    @Transient
    public Properties getProperties()
    {
        Properties properties = new IDBProperties();
        properties.put("id", id);
        return properties;
    }

}
