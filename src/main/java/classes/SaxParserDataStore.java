package classes;
import org.xml.sax.InputSource;

import java.io.IOException;
import java.text.ParseException;
import java.util.*;
import  java.io.StringReader;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;


////////////////////////////////////////////////////////////

/**************

SAX parser use callback function  to notify client object of the XML document structure. 
You should extend DefaultHandler and override the method when parsin the XML document

***************/

////////////////////////////////////////////////////////////

public class SaxParserDataStore extends DefaultHandler {
    Console console;
    Game game;
    Tablet tablet;
    Accessory accessory;
	Lightning lightning;
	Thermostat thermostat;
	Store store;
    static HashMap<String,Console> consoles;
    static HashMap<String,Game> games;
    static HashMap<String,Tablet> tablets;
	static HashMap<String,Lightning> lightnings;
	static HashMap<String,Thermostat> thermostats;
	static HashMap<String,Store> stores;
    static HashMap<String,Accessory> accessories;
    String consoleXmlFileName;
	HashMap<String,String> accessoryHashMap;
    String elementValueRead;
	String currentElement="";
    public SaxParserDataStore()
	{
	}
	public SaxParserDataStore(String consoleXmlFileName) {
    this.consoleXmlFileName = consoleXmlFileName;
    consoles = new HashMap<String, Console>();
	games=new  HashMap<String, Game>();
	tablets=new HashMap<String, Tablet>();
	lightnings=new HashMap<String, Lightning>();
	thermostats=new HashMap<String, Thermostat>();
	stores=new HashMap<String,Store>();
	accessories=new HashMap<String, Accessory>();
	accessoryHashMap=new HashMap<String,String>();
	parseDocument();
	
    }

   //parse the xml using sax parser to get the data
    private void parseDocument() 
	{
        SAXParserFactory factory = SAXParserFactory.newInstance();
        try 
		{
	    SAXParser parser = factory.newSAXParser();
	    parser.parse(consoleXmlFileName, this);
		
        } catch (ParserConfigurationException e) {
            System.out.println("ParserConfig error");
        } catch (SAXException e) {
            System.out.println("SAXException : xml not well formed");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
	}

   

////////////////////////////////////////////////////////////

/*************

There are a number of methods to override in SAX handler  when parsing your XML document :

    Group 1. startDocument() and endDocument() :  Methods that are called at the start and end of an XML document. 
    Group 2. startElement() and endElement() :  Methods that are called  at the start and end of a document element.  
    Group 3. characters() : Method that is called with the text content in between the start and end tags of an XML document element.


There are few other methods that you could use for notification for different purposes, check the API at the following URL:

https://docs.oracle.com/javase/7/docs/api/org/xml/sax/helpers/DefaultHandler.html

***************/

////////////////////////////////////////////////////////////
	
	// when xml start element is parsed store the id into respective hashmap for console,games etc 
    @Override
    public void startElement(String str1, String str2, String elementName, Attributes attributes) throws SAXException {

        if (elementName.equalsIgnoreCase("console")) 
		{
			currentElement="console";
			console = new Console();
            console.setId(attributes.getValue("id"));
		}
        if (elementName.equalsIgnoreCase("tablet"))
		{
			currentElement="tablet";
			tablet = new Tablet();
            tablet.setId(attributes.getValue("id"));
        }
        if (elementName.equalsIgnoreCase("game"))
		{
			currentElement="game";
			game= new Game();
            game.setId(attributes.getValue("id"));
        }
		if (elementName.equalsIgnoreCase("lightning"))
		{
			currentElement="lightning";
			lightning= new Lightning();
            lightning.setId(attributes.getValue("id"));
        }
		if (elementName.equalsIgnoreCase("thermostat"))
		{
			currentElement="thermostat";
			thermostat= new Thermostat();
            thermostat.setId(attributes.getValue("id"));
        }
        if (elementName.equalsIgnoreCase("accessory") &&  !currentElement.equalsIgnoreCase("console"))
		{
			currentElement="accessory";
			accessory=new Accessory();
			accessory.setId(attributes.getValue("id"));
	    }

		if (elementName.equalsIgnoreCase("store"))
		{
			currentElement="store";
			store= new Store();
            store.setId(attributes.getValue("id"));
        }


    }
	// when xml end element is parsed store the data into respective hashmap for console,games etc respectively
    @Override
    public void endElement(String str1, String str2, String element) throws SAXException {
 
        if (element.equalsIgnoreCase("console")) {
			consoles.put(console.getId(),console);
			return;
        }
 
        if (element.equalsIgnoreCase("tablet")) {	
			tablets.put(tablet.getId(),tablet);
			return;
        }
        if (element.equalsIgnoreCase("game")) {	  
			games.put(game.getId(),game);
			return;
        }
		if (element.equalsIgnoreCase("lightning")) {	  
			lightnings.put(lightning.getId(),lightning);
			return;
        }
		if (element.equalsIgnoreCase("thermostat")) {	  
			thermostats.put(thermostat.getId(),thermostat);
			return;
        }
        if (element.equalsIgnoreCase("accessory") && currentElement.equalsIgnoreCase("accessory")) {
			accessories.put(accessory.getId(),accessory);       
			return; 
        }
		if (element.equalsIgnoreCase("accessory") && currentElement.equalsIgnoreCase("console")) 
		{
			accessoryHashMap.put(elementValueRead,elementValueRead);
		}
      	if (element.equalsIgnoreCase("accessories") && currentElement.equalsIgnoreCase("console")) {
			console.setAccessories(accessoryHashMap);
			accessoryHashMap=new HashMap<String,String>();
			return;
		}
		if (element.equalsIgnoreCase("store")) {   
            stores.put(store.getId(),store);
            return;
        }
        if (element.equalsIgnoreCase("image")) {
		    if(currentElement.equalsIgnoreCase("console"))
				console.setImage(elementValueRead);
        	if(currentElement.equalsIgnoreCase("game"))
				game.setImage(elementValueRead);
            if(currentElement.equalsIgnoreCase("tablet"))
				tablet.setImage(elementValueRead);
			if(currentElement.equalsIgnoreCase("lightning"))
				lightning.setImage(elementValueRead);
			if(currentElement.equalsIgnoreCase("thermostat"))
				thermostat.setImage(elementValueRead);
            if(currentElement.equalsIgnoreCase("accessory"))
				accessory.setImage(elementValueRead);         
			return;
        }
        

		if (element.equalsIgnoreCase("discount")) {
            if(currentElement.equalsIgnoreCase("console"))
				console.setDiscount(Double.parseDouble(elementValueRead));
        	if(currentElement.equalsIgnoreCase("game"))
				game.setDiscount(Double.parseDouble(elementValueRead));
            if(currentElement.equalsIgnoreCase("tablet"))
				tablet.setDiscount(Double.parseDouble(elementValueRead));
			if(currentElement.equalsIgnoreCase("lightning"))
				lightning.setDiscount(Double.parseDouble(elementValueRead));
			if(currentElement.equalsIgnoreCase("thermostat"))
				thermostat.setDiscount(Double.parseDouble(elementValueRead));
            if(currentElement.equalsIgnoreCase("accessory"))
				accessory.setDiscount(Double.parseDouble(elementValueRead));          
			return;
	    }


		if (element.equalsIgnoreCase("condition")) {
            if(currentElement.equalsIgnoreCase("console"))
				console.setCondition(elementValueRead);
        	if(currentElement.equalsIgnoreCase("game"))
				game.setCondition(elementValueRead);
            if(currentElement.equalsIgnoreCase("tablet"))
				tablet.setCondition(elementValueRead);
			if(currentElement.equalsIgnoreCase("lightning"))
				lightning.setCondition(elementValueRead);
			if(currentElement.equalsIgnoreCase("thermostat"))
				thermostat.setCondition(elementValueRead);
            if(currentElement.equalsIgnoreCase("accessory"))
				accessory.setCondition(elementValueRead);          
			return;  
		}

		if (element.equalsIgnoreCase("manufacturer")) {
            if(currentElement.equalsIgnoreCase("console"))
				console.setRetailer(elementValueRead);
        	if(currentElement.equalsIgnoreCase("game"))
				game.setRetailer(elementValueRead);
            if(currentElement.equalsIgnoreCase("tablet"))
				tablet.setRetailer(elementValueRead);
			if(currentElement.equalsIgnoreCase("lightning"))
				lightning.setRetailer(elementValueRead);
			if(currentElement.equalsIgnoreCase("thermostat"))
				thermostat.setRetailer(elementValueRead);
            if(currentElement.equalsIgnoreCase("accessory"))
				accessory.setRetailer(elementValueRead);          
			return;
		}

        if (element.equalsIgnoreCase("name")) {
            if(currentElement.equalsIgnoreCase("console"))
				console.setName(elementValueRead);
        	if(currentElement.equalsIgnoreCase("game"))
				game.setName(elementValueRead);
            if(currentElement.equalsIgnoreCase("tablet"))
				tablet.setName(elementValueRead);
			if(currentElement.equalsIgnoreCase("lightning"))
				lightning.setName(elementValueRead);
			if(currentElement.equalsIgnoreCase("thermostat"))
				thermostat.setName(elementValueRead);
            if(currentElement.equalsIgnoreCase("accessory"))
				accessory.setName(elementValueRead);          
			return;
	    }
	
        if(element.equalsIgnoreCase("price")){
			if(currentElement.equalsIgnoreCase("console"))
				console.setPrice(Double.parseDouble(elementValueRead));
        	if(currentElement.equalsIgnoreCase("game"))
				game.setPrice(Double.parseDouble(elementValueRead));
            if(currentElement.equalsIgnoreCase("tablet"))
				tablet.setPrice(Double.parseDouble(elementValueRead));
			if(currentElement.equalsIgnoreCase("lightning"))
				lightning.setPrice(Double.parseDouble(elementValueRead));
			if(currentElement.equalsIgnoreCase("thermostat"))
				thermostat.setPrice(Double.parseDouble(elementValueRead));
            if(currentElement.equalsIgnoreCase("accessory"))
				accessory.setPrice(Double.parseDouble(elementValueRead));          
			return;
        }

		if(element.equalsIgnoreCase("productOnSale")){
			if(currentElement.equalsIgnoreCase("console"))
				console.setproductOnSale(elementValueRead);
        	if(currentElement.equalsIgnoreCase("game"))
				game.setproductOnSale(elementValueRead);
            if(currentElement.equalsIgnoreCase("tablet"))
				tablet.setproductOnSale(elementValueRead);
			if(currentElement.equalsIgnoreCase("lightning"))
				lightning.setproductOnSale(elementValueRead);
			if(currentElement.equalsIgnoreCase("thermostat"))
				thermostat.setproductOnSale(elementValueRead);
            if(currentElement.equalsIgnoreCase("accessory"))
				accessory.setproductOnSale(elementValueRead);          
			return;
        }

		if(element.equalsIgnoreCase("productQuantity")){
			if(currentElement.equalsIgnoreCase("console"))
				console.setproductQuantity(Integer.parseInt(elementValueRead));
        	if(currentElement.equalsIgnoreCase("game"))
				game.setproductQuantity(Integer.parseInt(elementValueRead));
            if(currentElement.equalsIgnoreCase("tablet"))
				tablet.setproductQuantity(Integer.parseInt(elementValueRead));
			if(currentElement.equalsIgnoreCase("lightning"))
				lightning.setproductQuantity(Integer.parseInt(elementValueRead));
			if(currentElement.equalsIgnoreCase("thermostat"))
				thermostat.setproductQuantity(Integer.parseInt(elementValueRead));
            if(currentElement.equalsIgnoreCase("accessory"))
				accessory.setproductQuantity(Integer.parseInt(elementValueRead));          
			return;
        }

		if(currentElement.equalsIgnoreCase("store")){
			if(element.equalsIgnoreCase("name")){
				store.setName(elementValueRead);
			}
			if(element.equalsIgnoreCase("street")){
				store.setStreet(elementValueRead);
			}
			if(element.equalsIgnoreCase("city")){
				store.setCity(elementValueRead);
			}
			if(element.equalsIgnoreCase("state")){
				store.setState(elementValueRead);
			}
			if(element.equalsIgnoreCase("zip")){
				store.setZip(elementValueRead);
			}
		}
	}
	//get each element in xml tag
    @Override
    public void characters(char[] content, int begin, int end) throws SAXException {
        elementValueRead = new String(content, begin, end);
    }


    /////////////////////////////////////////
    // 	     Kick-Start SAX in main       //
    ////////////////////////////////////////
	
//call the constructor to parse the xml and get product details
        public static void addHashmap() {
		String TOMCAT_HOME = System.getProperty("catalina.home");	
		new SaxParserDataStore(TOMCAT_HOME+"/webapps/SmartHomes_2/ProductCatalog.xml");
    } 
	
	
	
}
