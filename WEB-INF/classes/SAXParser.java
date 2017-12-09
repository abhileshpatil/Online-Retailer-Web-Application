import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import java.util.Map.Entry;
import java.util.Map;

class SAXParser extends DefaultHandler {

	boolean bproduct = false;
	boolean bname = false;
	boolean bprice = false;

	boolean bretailer = false;
	boolean bretailerZip = false;
	boolean bquantity = false;
	boolean bretailerCity = false;
	boolean bretailerState = false;
	boolean bproductOnSale = false;
	boolean bmanufacturerName = false;
	
	boolean bdiscount = false;

	boolean brdiscount = false;

	boolean brwarranty = false;
	boolean brrebate = false;
	boolean bcategory = false;
	
	boolean baccname = false;
	boolean baccprice = false;

	boolean baccdiscount = false;
	boolean bacc = false;

	
	Product product;
	Accessory acc;
	HashMap<String, Product> fileproducts = new HashMap<String, Product>();
	HashMap<String, Accessory> productaccessory = new HashMap<String, Accessory>();


	public HashMap<String, Product> readDataFromXML(String fileName)
			throws ParserConfigurationException, SAXException, IOException {

		SAXParserFactory factory = SAXParserFactory.newInstance();
		javax.xml.parsers.SAXParser parser = factory.newSAXParser();

		parser.parse(new File(fileName), this);

		return fileproducts;
	}

	@Override
	public void startDocument() throws SAXException {
		System.out.println("Start document");
	}

	@Override
	public void endDocument() throws SAXException {
		System.out.println("End document");
	}

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException 
	{
			
		if(qName.equalsIgnoreCase("product"))
		{
			bproduct = true;
			product = new Product();
		}
		
		if(bproduct)
		{
		
			if (qName.equalsIgnoreCase("name")) {
				bname = true;
			}

			else if (qName.equalsIgnoreCase("discount")) {
				bdiscount = true;
			}

			else if (qName.equalsIgnoreCase("price")) {
				bprice = true;
			}

			else if (qName.equalsIgnoreCase("rdiscount")) {
				brdiscount = true;
			}

			else if (qName.equalsIgnoreCase("rwarranty")) {
				brwarranty = true;
			}
			else if (qName.equalsIgnoreCase("rrebate")) {
				brrebate = true;
			}
			else if (qName.equalsIgnoreCase("category")) {
				bcategory = true;
			} 
			
			else if (qName.equalsIgnoreCase("retailer")) {
				bretailer = true;
			}

			else if (qName.equalsIgnoreCase("retailerZip")) {
				bretailerZip = true;
			}

			else if (qName.equalsIgnoreCase("quantity")) {
				bquantity = true;
			}

			else if (qName.equalsIgnoreCase("retailerCity")) {
				bretailerCity = true;
			}
			else if (qName.equalsIgnoreCase("retailerState")) {
				bretailerState = true;
			}
			else if (qName.equalsIgnoreCase("productOnSale")) {
				bproductOnSale = true;
			}
			else if (qName.equalsIgnoreCase("manufacturerName")) {
				bmanufacturerName = true;
			}
			
			if(qName.equalsIgnoreCase("accessory"))
			{
				bacc = true;
				acc = new Accessory();
			}
			
			if(bacc)
			{
				if (qName.equalsIgnoreCase("accname")) {
					baccname = true;
				} else if (qName.equalsIgnoreCase("accprice")) {
					baccprice = true;
				} else if (qName.equalsIgnoreCase("accdiscount")) {
					baccdiscount = true;
				}

			}
		}
		
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		if (qName.equalsIgnoreCase("product")) {
			
			fileproducts.put(product.getName(), product);
			bproduct = false;
			
		}
		if (qName.equalsIgnoreCase("accessory")){
			//System.out.println("END of acc");
			productaccessory.put(product.getName(), acc);			
			product.setAccessories(productaccessory);
			productaccessory = new HashMap<>();
			bacc = false;

								int i=1;
for (Map.Entry<String,Accessory> entry : productaccessory.entrySet()) {
  String key = entry.getKey();
//   String value = entry.getValue();
  System.out.println("In sax parser function"+i+" "+key);
  System.out.println("In sax parser function"+i+" "+entry.getValue());
  i=i+1;
  // do stuff
}
		}
	
	}

	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		
			if (bname) {
				product.setName(new String(ch, start, length));
				bname = false;
			}
			
			else if (bretailer) {
				product.setRetailer(new String(ch, start, length));
				bretailer = false;
			}
			
			else if (bdiscount) {
				product.setDiscount(Double.parseDouble(new String(ch, start, length)));
				bdiscount = false;
			}
			else if (brdiscount) {
				product.setRdiscount(Double.parseDouble(new String(ch, start, length)));
				brdiscount = false;
			}
			else if (brwarranty) {
				product.setRwarranty(Double.parseDouble(new String(ch, start, length)));
				brwarranty = false;
			}
			else if (brrebate) {
				product.setrrebate(Double.parseDouble(new String(ch, start, length)));
				brrebate = false;
			}
			else if (bcategory) {
				product.setCategory(new String(ch, start, length));
				bcategory = false;
			}
			
			else if (bretailerZip) {
				product.setRetailerZip(Integer.parseInt(new String(ch, start, length)));
				bretailerZip = false;
			}
			else if (bquantity) {
				product.setquantity(Integer.parseInt(new String(ch, start, length)));
				bquantity = false;
			}
			else if (bretailerCity) {
				product.setRetailerCity(new String(ch, start, length));
				bretailerCity = false;
			}
			else if (bretailerState) {
				product.setRetailerState(new String(ch, start, length));
				bretailerState = false;
			}
			else if (bproductOnSale) {
				product.setProductOnSale(new String(ch, start, length));
				bproductOnSale = false;
			}
			else if (bmanufacturerName) {
				product.setManufacturerName(new String(ch, start, length));
				bmanufacturerName = false;
			}
			
			else if (bprice) {
				product.setPrice(Double.parseDouble(new String(ch, start, length)));
				bprice = false;
			}
			
			else if (baccname) {
				acc.setName(new String(ch, start, length));
				baccname = false;
			}	else if (baccprice) {
				acc.setPrice(Double.parseDouble(new String(ch, start, length)));
				baccprice = false;
			}else if (baccdiscount) {
				acc.setDiscount(Double.parseDouble(new String(ch, start, length)));
				baccdiscount = false;
			}
		
	}
	
}

