package com.nix.xml;

import java.io.File;
import java.io.FileWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class CustomDomXmlHandler {

	Document doc;

	private static final Logger logger = LoggerFactory.getLogger(CustomDomXmlHandler.class);

	public CustomDomXmlHandler() {
		logger.debug("CustomDomXmlHandler is created");
	}

	public void readAndWriteFile(String sourceFile, String newFile) {
		if (sourceFile == null || newFile == null) {
			logger.error("source or new filename is null", new NullPointerException("source or new filename is null"));
			throw new NullPointerException("source or new filename is null");
		}
		if (sourceFile.length() == 0 || newFile.length() == 0) {
			logger.error("source or new filename is incorrect",
					new IllegalArgumentException("source or new filename is incorrect"));
			throw new IllegalArgumentException("source or new filename is incorrect");
		}
		doc = this.createDocument(sourceFile);
		Node node = doc.getDocumentElement();
		removeEvenChildNodes(node);
		writeNewFile(newFile);
	}

	private Document createDocument(String fileName) {
		if (fileName == null) {
			logger.error("cannot handle a null reference", new NullPointerException("cannot handle a null reference"));
			throw new NullPointerException("cannot handle a null reference");
		}
		DocumentBuilder documentBuilder;
		Document document;
		try {
			documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			document = documentBuilder.parse(fileName);

		} catch (Exception e) {
			logger.error("Something went wrong upon Documnet creation", e);
			throw new IllegalArgumentException("Something went wrong upon Documnet creation", e);
		}
		logger.debug("Document is created");
		return document;
	}

	private void writeNewFile(String fileName) {
		if (fileName == null) {
			logger.error("CustomDomXmlHandler is created", new NullPointerException("cannot handle null node"));
			throw new NullPointerException("cannot handle a null reference");
		}
		DOMSource source = new DOMSource(doc);

		try (FileWriter writer = new FileWriter(new File(fileName))) {

			StreamResult result = new StreamResult(writer);

			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			transformer.transform(source, result);

		} catch (Exception e) {
			logger.error("something went wrong upon output file writing", e);
			throw new IllegalArgumentException("something went wrong upon output file writing", e);
		}
		logger.debug("New File is written");
	}

	private void removeEvenChildNodes(Node node) {
		if (node == null) {
			logger.error("CustomDomXmlHandler is created", new NullPointerException("cannot handle null node"));
			throw new NullPointerException("cannot handle null node");
		}
		if (node.hasChildNodes()) {
			int counter = 1;
			NodeList nodeList = node.getChildNodes();
			for (int i = 0; i < nodeList.getLength(); i++) {
				if (nodeList.item(i).getNodeType() == Node.ELEMENT_NODE) {
					if (counter % 2 == 0) {
						node.removeChild(nodeList.item(i));
					} else if (counter % 2 != 0) {
						removeEvenChildNodes(nodeList.item(i));
					}
					counter++;
				}
			}
		}
	}
}
