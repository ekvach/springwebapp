package com.nix.xml;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class CustomSaxXmlHandler extends DefaultHandler {

	private static final Logger logger = LoggerFactory.getLogger(CustomSaxXmlHandler.class);

	private SAXParserFactory factory;
	private SAXParser saxParser;
	private StringBuilder stringBuilder = new StringBuilder();
	private Boolean isCurrentElementOdd = true;
	private List<String> childsOfEvenTagList = new ArrayList<>();
	private boolean isEndElementJustCalled;
	private String firstLineOfDoc = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?> \n";

	public CustomSaxXmlHandler() {
	}

	public void readAndWriteFile(String sourceFile, String newFile) throws Exception {
		if (sourceFile == null || newFile == null) {
			logger.error("source or new filename is null", new NullPointerException("source or new filename is null"));
			throw new NullPointerException("source or new filename is null");
		}
		if (sourceFile.length() == 0 || newFile.length() == 0) {
			logger.error("source or new filename is incorrect",
					new IllegalArgumentException("source or new filename is incorrect"));
			throw new IllegalArgumentException("source or new filename is incorrect");
		}
		initializeFactoryAndParser();
		saxParser.parse(sourceFile, this);
		writeDocIntoNewFile(newFile);
	}

	@Override
	public void startDocument() throws SAXException {
		logger.info("Document is started");
		stringBuilder.append(firstLineOfDoc);
	}

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		isEndElementJustCalled = false;
		logger.info(qName + " Element is started");
		String tempString = "";
		if (isCurrentElementOdd) {
			stringBuilder.append("<" + qName);

			for (int i = 0; i < attributes.getLength(); i++) {
				tempString = " " + attributes.getQName(i) + "=\"" + attributes.getValue(i) + "\"";
				stringBuilder.append(tempString);
			}
			stringBuilder.append(">");
		} else {
			childsOfEvenTagList.add(qName);
		}

	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		logger.info(qName + " Element is ended");
		if (isCurrentElementOdd || isEndElementJustCalled) {
			stringBuilder.append("</" + qName + ">");
			isCurrentElementOdd = false;
			isEndElementJustCalled = true;
		} else if (childsOfEvenTagList.isEmpty()) {
			isCurrentElementOdd = true;
		} else {
			childsOfEvenTagList.remove(childsOfEvenTagList.size() - 1);
			if (childsOfEvenTagList.isEmpty()) {
				isCurrentElementOdd = true;
			}
		}
	}

	@Override
	public void endDocument() throws SAXException {
		logger.info("Document is ended");
	}

	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		if (isCurrentElementOdd || isEndElementJustCalled) {
			stringBuilder.append(new String(ch, start, length));
		}
		logger.info(new String(ch, start, length) + ": Characters are read");
	}

	private void initializeFactoryAndParser() {

		try {
			factory = SAXParserFactory.newInstance();
			saxParser = factory.newSAXParser();
		} catch (Exception e) {
			logger.error("cannot initialize a parser", e);
			throw new IllegalArgumentException("cannot initialize a parser", e);
		}
	}

	private void writeDocIntoNewFile(String newFile) {
		File file = new File(newFile);

		try {
			if (!file.exists()) {
				file.createNewFile();
			}
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			try (BufferedWriter bw = new BufferedWriter(fw)) {
				bw.write(stringBuilder.toString());
				logger.info(newFile + ": file is written");
			}
		} catch (Exception e) {
			logger.error("cannot write a file", e);
			throw new IllegalArgumentException("cannot write a file", e);
		}
	}

	public StringBuilder getStringBuilder() {
		return stringBuilder;
	}

	public void setStringBuilder(StringBuilder stringBuilder) {
		this.stringBuilder = stringBuilder;
	}

}
