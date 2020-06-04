
package mypackage;

import java.math.BigInteger;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the mypackage package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _Purchase_QNAME = new QName("", "Purchase");
    private final static QName _FirePower_QNAME = new QName("", "FirePower");
    private final static QName _Type_QNAME = new QName("", "Type");
    private final static QName _ArmyThreshold_QNAME = new QName("", "ArmyThreshold");
    private final static QName _ConversionRate_QNAME = new QName("", "ConversionRate");
    private final static QName _InitialFunds_QNAME = new QName("", "InitialFunds");
    private final static QName _CompetenceReduction_QNAME = new QName("", "CompetenceReduction");
    private final static QName _Profit_QNAME = new QName("", "Profit");
    private final static QName _MaxFirePower_QNAME = new QName("", "MaxFirePower");
    private final static QName _GameType_QNAME = new QName("", "GameType");
    private final static QName _Name_QNAME = new QName("", "Name");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: mypackage
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Player }
     * 
     */
    public Player createPlayer() {
        return new Player();
    }

    /**
     * Create an instance of {@link Army }
     * 
     */
    public Army createArmy() {
        return new Army();
    }

    /**
     * Create an instance of {@link Unit }
     * 
     */
    public Unit createUnit() {
        return new Unit();
    }

    /**
     * Create an instance of {@link Territories }
     * 
     */
    public Territories createTerritories() {
        return new Territories();
    }

    /**
     * Create an instance of {@link Teritory }
     * 
     */
    public Teritory createTeritory() {
        return new Teritory();
    }

    /**
     * Create an instance of {@link Game }
     * 
     */
    public Game createGame() {
        return new Game();
    }

    /**
     * Create an instance of {@link Board }
     * 
     */
    public Board createBoard() {
        return new Board();
    }

    /**
     * Create an instance of {@link GameDescriptor }
     * 
     */
    public GameDescriptor createGameDescriptor() {
        return new GameDescriptor();
    }

    /**
     * Create an instance of {@link Players }
     * 
     */
    public Players createPlayers() {
        return new Players();
    }

    /**
     * Create an instance of {@link DynamicPlayers }
     * 
     */
    public DynamicPlayers createDynamicPlayers() {
        return new DynamicPlayers();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BigInteger }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "Purchase")
    public JAXBElement<BigInteger> createPurchase(BigInteger value) {
        return new JAXBElement<BigInteger>(_Purchase_QNAME, BigInteger.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BigInteger }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "FirePower")
    public JAXBElement<BigInteger> createFirePower(BigInteger value) {
        return new JAXBElement<BigInteger>(_FirePower_QNAME, BigInteger.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "Type")
    public JAXBElement<String> createType(String value) {
        return new JAXBElement<String>(_Type_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BigInteger }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "ArmyThreshold")
    public JAXBElement<BigInteger> createArmyThreshold(BigInteger value) {
        return new JAXBElement<BigInteger>(_ArmyThreshold_QNAME, BigInteger.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BigInteger }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "ConversionRate")
    public JAXBElement<BigInteger> createConversionRate(BigInteger value) {
        return new JAXBElement<BigInteger>(_ConversionRate_QNAME, BigInteger.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BigInteger }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "InitialFunds")
    public JAXBElement<BigInteger> createInitialFunds(BigInteger value) {
        return new JAXBElement<BigInteger>(_InitialFunds_QNAME, BigInteger.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BigInteger }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "CompetenceReduction")
    public JAXBElement<BigInteger> createCompetenceReduction(BigInteger value) {
        return new JAXBElement<BigInteger>(_CompetenceReduction_QNAME, BigInteger.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BigInteger }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "Profit")
    public JAXBElement<BigInteger> createProfit(BigInteger value) {
        return new JAXBElement<BigInteger>(_Profit_QNAME, BigInteger.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BigInteger }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "MaxFirePower")
    public JAXBElement<BigInteger> createMaxFirePower(BigInteger value) {
        return new JAXBElement<BigInteger>(_MaxFirePower_QNAME, BigInteger.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "GameType")
    public JAXBElement<String> createGameType(String value) {
        return new JAXBElement<String>(_GameType_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "Name")
    public JAXBElement<String> createName(String value) {
        return new JAXBElement<String>(_Name_QNAME, String.class, null, value);
    }

}
