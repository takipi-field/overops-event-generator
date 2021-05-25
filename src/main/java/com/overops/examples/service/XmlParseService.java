package com.overops.examples.service;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
			Random random = new Random(98765);
			int r = random.nextInt(5) + 1;

			String xmlString;

			if (r == 1) {
				xmlString = "\n" +
					"<Pets>\n" +
					"	<pet id=\"3\">\n" +
					"		<name>Rover</name>\n" +
					"		<time>2023-03-03T03:03:03</time>\n";
			} else {
				xmlString = "\n" +
					"<Pets>\n" +
					"	<pet id=\"1\">\n" +
					"		<name>Fluffy</name>\n" +
					"	   <time>2021-01-01T01:01:01</time>\n" +
					"	</pet>\n" +
					"	<pet id=\"2\">\n" +
					"		<name>Spot</name>\n" +
					"	   <time>2022-02-02T02:02:02</time>\n" +
					"	</pet>\n" +
					"</Pets>\n";
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

final class Pet {
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
		this.time = LocalDateTime.parse(string, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
	}
}

final class MyHandler extends DefaultHandler {

	private Pet pet = null;
	private StringBuilder data = null;

	boolean bName = false;
	boolean bTime = false;

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {

		if (qName.equalsIgnoreCase("Pet")) {
			String id = attributes.getValue("id");

			pet = new Pet();
			pet.setId(Integer.parseInt(id));
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
			pet.setName(data.toString());
			bName = false;
		} else if (bTime) {
			pet.setTime(data.toString());
			bTime = false;
		}
	}

	@Override
	public void characters(char ch[], int start, int length) throws SAXException {
		data.append(new String(ch, start, length));
	}
}
