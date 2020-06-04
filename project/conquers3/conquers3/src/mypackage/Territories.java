
package mypackage;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}Teritory" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *       &lt;attribute name="default-profit" type="{http://www.w3.org/2001/XMLSchema}integer" />
 *       &lt;attribute name="default-army-threshold" type="{http://www.w3.org/2001/XMLSchema}integer" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "teritory"
})
@XmlRootElement(name = "Territories")
public class Territories {

    @XmlElement(name = "Teritory", required = true)
    protected List<Teritory> teritory;
    @XmlAttribute(name = "default-profit")
    protected BigInteger defaultProfit;
    @XmlAttribute(name = "default-army-threshold")
    protected BigInteger defaultArmyThreshold;

    /**
     * Gets the value of the teritory property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the teritory property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTeritory().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Teritory }
     * 
     * 
     */
    public List<Teritory> getTeritory() {
        if (teritory == null) {
            teritory = new ArrayList<Teritory>();
        }
        return this.teritory;
    }

    /**
     * Gets the value of the defaultProfit property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getDefaultProfit() {
        return defaultProfit;
    }

    /**
     * Sets the value of the defaultProfit property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setDefaultProfit(BigInteger value) {
        this.defaultProfit = value;
    }

    /**
     * Gets the value of the defaultArmyThreshold property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getDefaultArmyThreshold() {
        return defaultArmyThreshold;
    }

    /**
     * Sets the value of the defaultArmyThreshold property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setDefaultArmyThreshold(BigInteger value) {
        this.defaultArmyThreshold = value;
    }

}
