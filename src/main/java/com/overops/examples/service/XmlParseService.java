package com.overops.examples.service;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.Random;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

@Service
public class XmlParseService extends AbstractEventService {

	@Override
	void fireEvent(boolean generateEvent) {

		if (!generateEvent) {
			return;
		}

		try {
			// try to parse bad XML 20% of the time
			Random random = new Random();
			int r = random.nextInt(5) + 1;

			String xmlString;

			if (r == 1) {
				xmlString = "\n" + 
					"<Foodstuffs>\n" + 
					"	<Food id=\"5\">\n" + 
					"		<name>Taco</name>\n" + 
					"		<time>" + LocalDateTime.now() + "</time>\n";
			} else {
				xmlString = "\n" + 
					"<Foodstuffs>\n" + 
					"	<Food id=\"1\">\n" + 
					"		<name>Pizza</name>\n" + 
					"	   <time>" + LocalDateTime.now() + "</time>\n" +
					"	</Food>\n" + 
					"	<Food id=\"2\">\n" + 
					"		<name>Banana</name>\n" + 
					"	   <time>" + LocalDateTime.now() + "</time>\n" +
					"	</Food>\n" + 
					"	<Food id=\"3\">\n" + 
					"		<name>Carrot</name>\n" + 
					"		<time>" + LocalDateTime.now() + "</time>\n" +
					"	</Food>\n" + 
					"	<Food id=\"4\">\n" + 
					"		<name>Ice Cream</name>\n" + 
					"		<time>" + LocalDateTime.now() + "</time>\n" +
					"	</Food>\n" + 
					"</Foodstuffs>\n";
			}

			InputStream xmlStream = IOUtils.toInputStream(xmlString, "UTF-8");
			SAXParser saxParser = SAXParserFactory.newInstance().newSAXParser();
			saxParser.parse(xmlStream, new MyHandler());

		} catch (IOException e) {
			log.error("Unable to convert string to input stream");
		} catch (ParserConfigurationException | SAXException e) {
			log.error("Unable to parse XML");
		}

	}
}

final class Food {
	private int id;
	private String name;
	private LocalDateTime time;

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public LocalDateTime getTime() {
		return time;
	}
	public void setTime(String string) {
		this.time = LocalDateTime.parse(string);
	}
}

final class MyHandler extends DefaultHandler {

	private Food food = null;
	private StringBuilder data = null;

	boolean bName = false;
	boolean bTime = false;

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {

		if (qName.equalsIgnoreCase("Food")) {
			String id = attributes.getValue("id");

			food = new Food();
			food.setId(Integer.parseInt(id));
		} else if (qName.equalsIgnoreCase("name")) {
			bName = true;
		} else if (qName.equalsIgnoreCase("time")) {
			bTime = true;
		}

		data = new StringBuilder();
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		if (bName) {
			food.setName(data.toString());
			bName = false;
		} else if (bTime) {
			food.setTime(data.toString());
			bTime = false;
		}
	}

	@Override
	public void characters(char ch[], int start, int length) throws SAXException {
		data.append(new String(ch, start, length));
	}
}
