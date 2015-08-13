package ipreomobile.core;

import java.io.IOException;

import org.openqa.selenium.By;
import ipreomobile.core.SenchaWebElement;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

class UICardException extends Exception {
    UICardException() {
        super("Invalid XML mapping file structure.");
    }
    UICardException(String message) {
        super(message);
    }
}

public class UICard {

    private Document xmlDoc;
    private String fileName;
    private Element root;
    private Element cardInfo;
    private NodeList cardElements;
    private String product;
    private Boolean isReady = false;
    private long loadingTimeout;
    private long animationTimeout;
    //private WebDriver driver;

    public UICard(String screenName) {
        product = System.getProperty("test.product");
        fileName = (screenName.endsWith(".xml") ? screenName : screenName + ".xml");
        parseXmlFile();
    }

    public void waitCardReady() throws UICardException {
        Element cardReady = readElementData("");
        NodeList locators = readLocatorChain(cardReady);
        //String locatorType = cardReady.getElementsByTagName("locatorsChain");
    }

    private Element readElementData(String elementName) throws UICardException {
        Element el = null;
        if (elementName == "" || elementName == "root") {
            el = (Element)cardInfo.getElementsByTagName("element").item(0);
        }
        else {
            for (int i=0; i < cardElements.getLength(); i++) {
                if (((Element)cardElements.item(i)).getAttribute("name") == elementName) {
                    el = (Element)cardElements.item(i);
                }
            }
        }
        if (el == null) {
            throw new UICardException("No data found for element with name='"+elementName+"' in mapping file '"+fileName+"'.");
        }
        return el;
    }

    private SenchaWebElement getWebElementByLocatorChain(NodeList locatorChain) {
       SenchaWebElement el = null;
        By locator;
        for (int i = 0; i < locatorChain.getLength(); i++) {

        }
        return el;
    }

    private NodeList readLocatorChain(Element el) throws UICardException {
        NodeList locatorChains = el.getElementsByTagName("locatorChain");
        NodeList locators = null;
        String elementProduct;
        if (locatorChains.getLength() < 1) {
            throw new UICardException("No locator chain defined for element '"+el.getAttribute("name")+"' defined in '"+fileName+".");
        }
        else if (locatorChains.getLength() == 1) {
            locators = locatorChains.item(0).getChildNodes();
        }
        else {
            //Find locator chain suitable for the current product
            for (int i=0; i<locatorChains.getLength(); i++) {
                elementProduct = ((Element)locatorChains.item(i)).getAttribute("product");
                if (elementProduct == this.product) {
                    locators = locatorChains.item(i).getChildNodes();
                }
            }
            if (locators == null) {
                //Find general locator chain (product = "base" or undefined)
                for (int i = 0; i < locatorChains.getLength(); i++) {
                    elementProduct = ((Element) locatorChains.item(i)).getAttribute("product");
                    if (elementProduct == null || elementProduct == "base") {
                        locators = locatorChains.item(i).getChildNodes();
                    }
                }
            }
            if (locators == null) {
                throw new UICardException("Unable to find any locator chains defined for element '"+el.getAttribute("name")+"', product = '"+product+"' defined in '"+fileName+".");
            }
        }
        return locators;
    }

    private void parseXmlFile() {
        //get the factory
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder db = dbf.newDocumentBuilder();
            xmlDoc = db.parse(fileName);
            root = xmlDoc.getDocumentElement();
            cardInfo = (Element)root.getElementsByTagName("card").item(0);
            cardElements = root.getElementsByTagName("element");

        } catch (ParserConfigurationException | SAXException | IOException e) {
            Logger.logError(e);
        }
    }

    private void getCardInfo() {
        NodeList nodeList = root.getElementsByTagName("card");
        if (nodeList.getLength() == 1) {
            cardInfo = (Element)nodeList.item(0);
            loadingTimeout = Long.parseLong(cardInfo.getElementsByTagName("loadingTimeout").item(0).getNodeValue());
            animationTimeout = Long.parseLong(cardInfo.getElementsByTagName("animationTimeout").item(0).getNodeValue());
        }
        else {
            Logger.logError("XML document '"+fileName+"' contains no 'card' node with card information");
        }

    }

    private By getLocator(String locatorType, String locatorValue) {
        switch (locatorType.toLowerCase()) {
            case "id":
                return By.id(locatorValue);
            case "name":
                return By.name(locatorValue);
            case "classname": case "class":
                return By.className(locatorValue);
            case "tagname" : case "tag":
                return By.tagName(locatorValue);
            case "linktext" : case "link":
                return By.linkText(locatorValue);
            case "partiallinktext":
                return By.partialLinkText(locatorValue);
            case "cssselector" : case "css":
                return By.cssSelector(locatorValue);
            case "xpath":
                return By.xpath(locatorValue);
            case "text":
                return By.xpath("//*[text()='"+locatorValue+"']");
            case "partialtext":
                return By.xpath("//*[contains(text(), '"+locatorValue+"')]");
            default:
                Logger.logError("Unable to retrieve locator by type '"+locatorType+"'.");
        }
	    return null;
    }


}

//public class UICard {
//	private static final String MAPPING_FILE = "";
//	private String page = "";
//	private Properties mappingData;
//	private String product = "base";
//
//	public UICard(String pageName){
//		page = pageName;
//		product = System.getProperty("test.product");
//		mappingData = new Properties();
//		try {
//			FileInputStream fileReader = new FileInputStream(MAPPING_FILE);
//			mappingData.load(fileReader);
//			fileReader.close();
//		}
//		catch (IOException e) {
//			Logger.logError("Failed to load properties file with UI objects mapping: " + MAPPING_FILE);
//			Logger.logError(e);
//		}
//	}
//
//	public By getLocator(String elementName) {
//		String fullElementName = product + "." + page + "." + elementName;
//		String locatorData = mappingData.getProperty(fullElementName);
//		if (locatorData == null) {
//			fullElementName = System.getProperty("test.defaultProduct") + page + "." + elementName;
//			locatorData = mappingData.getProperty(fullElementName);
//		}
//		String locatorType = locatorData.split(":")[0];
//		String locatorValue = locatorData.split(":")[1];
//		switch (locatorType.toLowerCase()) {
//		case "id":
//			return By.id(locatorValue);
//		case "name":
//			return By.name(locatorValue);
//		case "classname": case "class":
//			return By.className(locatorValue);
//		case "tagname" : case "tag":
//			return By.tagName(locatorValue);
//		case "linktext" : case "link":
//			return By.linkText(locatorValue);
//		case "partiallinktext":
//			return By.partialLinkText(locatorValue);
//		case "cssselector" : case "css":
//			return By.cssSelector(locatorValue);
//		case "xpath":
//			return By.xpath(locatorValue);
//		case "innertext":
//			return By.xpath("//*[text()='"+locatorValue+"']");
//		}
//		return null;
//	}
//}
