package com.nix.xml;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.custommonkey.xmlunit.Diff;
import org.custommonkey.xmlunit.XMLTestCase;
import org.custommonkey.xmlunit.XMLUnit;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.w3c.dom.Document;

@RunWith(JUnit4.class)
public class CustomSaxXmlHandlerTest extends XMLTestCase {

	private CustomSaxXmlHandler customSaxXmlHandler;
	private String sourceFile;
	private String parsedFile;
	private String expectedFile;
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;

	@Before
	public void initializer() throws Exception {
		customSaxXmlHandler = new CustomSaxXmlHandler();
		sourceFile = "src/test/resources/sourceXmlForParsers.xml";
		parsedFile = "src/test/resources/parsedXml.xml";
		expectedFile = "src/test/resources/expectedXmlForParsers.xml";
		docBuilderFactory = DocumentBuilderFactory.newInstance();
		docBuilder = docBuilderFactory.newDocumentBuilder();
		XMLUnit.setIgnoreWhitespace(true);
	}

	@After
	public void shutDown() throws Exception {
		File f = new File(parsedFile);
		f.delete();
	}

	@Test
	public void fileIsReadPositive() throws Exception {
		customSaxXmlHandler.readAndWriteFile(sourceFile, parsedFile);

		Document doc1 = docBuilder.parse(expectedFile);
		Document doc2 = docBuilder.parse(parsedFile);

		Diff diff = new Diff(doc1, doc2);
		assertTrue("parsed and expected XMLs are not the same", diff.identical());
	}

	@Test(expected = NullPointerException.class)
	public void sourceNameIsNull() throws Exception {
		customSaxXmlHandler.readAndWriteFile(null, parsedFile);
	}

	@Test(expected = NullPointerException.class)
	public void newNameIsNull() throws Exception {
		customSaxXmlHandler.readAndWriteFile(sourceFile, null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void sourceNameIsEmpty() throws Exception {
		customSaxXmlHandler.readAndWriteFile("", parsedFile);
	}

	@Test(expected = IllegalArgumentException.class)
	public void newNameIsEmpty() throws Exception {
		customSaxXmlHandler.readAndWriteFile(sourceFile, "");
	}

}
