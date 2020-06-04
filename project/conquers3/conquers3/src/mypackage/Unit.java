
package mypackage;

import java.math.BigInteger;
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
 *         &lt;element ref="{}Purchase"/>
 *         &lt;element ref="{}MaxFirePower"/>
 *         &lt;element ref="{}CompetenceReduction"/>
 *       &lt;/sequence>
 *       &lt;attribute name="rank" use="required" type="{http://www.w3.org/2001/XMLSchema}byte" />
 *       &lt;attribute name="type" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "purchase",
    "maxFirePower",
    "competenceReduction"
})
@XmlRootElement(name = "Unit")
public class Unit {

    @XmlElement(name = "Purchase", required = true)
    protected BigInteger purchase;
    @XmlElement(name = "MaxFirePower", required = true)
    protected BigInteger maxFirePower;
    @XmlElement(name = "CompetenceReduction", required = true)
    protected BigInteger competenceReduction;
    @XmlAttribute(name = "rank", required = true)
    protected byte rank;
    @XmlAttribute(name = "type", required = true)
    protected String type;

    /**
     * Gets the value of the purchase property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getPurchase() {
        return purchase;
    }

    /**
     * Sets the value of the purchase property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setPurchase(BigInteger value) {
        this.purchase = value;
    }

    /**
     * Gets the value of the maxFirePower property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getMaxFirePower() {
        return maxFirePower;
    }

    /**
     * Sets the value of the maxFirePower property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setMaxFirePower(BigInteger value) {
        this.maxFirePower = value;
    }

    /**
     * Gets the value of the competenceReduction property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getCompetenceReduction() {
        return competenceReduction;
    }

    /**
     * Sets the value of the competenceReduction property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setCompetenceReduction(BigInteger value) {
        this.competenceReduction = value;
    }

    /**
     * Gets the value of the rank property.
     * 
     */
    public byte getRank() {
        return rank;
    }

    /**
     * Sets the value of the rank property.
     * 
     */
    public void setRank(byte value) {
        this.rank = value;
    }

    /**
     * Gets the value of the type property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the value of the type property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setType(String value) {
        this.type = value;
    }

}
