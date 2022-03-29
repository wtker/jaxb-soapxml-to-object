package pers.jaxb.soapxmlobject.util;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stax.StAXSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import pers.jaxb.soapxmlobject.xml.movies.MoviesResponse;

public class TransformUtil {

	public static void main(String[] args) {

		TransformUtil tfu = new TransformUtil();
		String testXML = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelop/\"><soapenv:Body><b:MoviesResponse xmlns:b=\"http://SoapXmlMessage/MyMoviesService\"><Status>00</Status><Date>1998-01-01</Date><MoviesParam><MovieName>Fly to Sky</MovieName><MovieType>Family</MovieType><Publisher>ABC Film</Publisher></MoviesParam></b:MoviesResponse></soapenv:Body></soapenv:Envelope>";
		
		System.out.println("SOAP XML String ......" + testXML);
		
		tfu.XMLToObjMoviesResponse(testXML);

	}
	
	private MoviesResponse XMLToObjMoviesResponse(String responseXML) {
		// Get SOAP Body then replace XML root element
		try {
			responseXML = getSOAPBodyReplaceRootElement(responseXML, "MoviesResponse", MoviesResponse.class);
		} catch (XMLStreamException | TransformerFactoryConfigurationError | TransformerException | SAXException
				| IOException | ParserConfigurationException e) {
			e.printStackTrace();
			System.err.println(e.getMessage());
		}
		System.out.println("MoviesResponse formatted response ......" + responseXML);
		
		// Transform XML to Object
		MoviesResponse response = null;
		JAXBContext context;
		
		try {
			context = JAXBContext.newInstance(MoviesResponse.class);
			Unmarshaller um = context.createUnmarshaller();
			response = (MoviesResponse) um.unmarshal(new StringReader(responseXML));
		} catch (JAXBException e) {
			e.printStackTrace();
			System.err.println(e.getMessage());
		}
		
		return response;
	}
	
	private <T> String getSOAPBodyReplaceRootElement(String xmlString, String newRootElement, Class<T> classType) throws XMLStreamException, TransformerFactoryConfigurationError, TransformerException, SAXException, IOException, ParserConfigurationException {
		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		
		// Get SOAP Body
		XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
		XMLStreamReader xmlStreamReader = xmlInputFactory.createXMLStreamReader(new StringReader(xmlString));
		xmlStreamReader.nextTag();
		while (!xmlStreamReader.getLocalName().equals(classType.getSimpleName())) {
			xmlStreamReader.nextTag();
		}
		
		// Transform XML stream to document
		Transformer transformer = TransformerFactory.newInstance().newTransformer();
		StringWriter stringWriter = new StringWriter();
		transformer.transform(new StAXSource(xmlStreamReader), new StreamResult(stringWriter));
		Document document = factory.newDocumentBuilder().parse(new InputSource(new StringReader(stringWriter.toString())));
		
		// Replace Root Element
		Element element1 = document.getDocumentElement();
		Element element2 = document.createElement(newRootElement);
		NamedNodeMap attributes = element1.getAttributes();
		for (int i = 0; i < attributes.getLength(); i++) {
			Attr attr2 = (Attr) document.importNode(attributes.item(i), true);
			element2.getAttributes().setNamedItem(attr2);
		}
		while (element1.hasChildNodes()) {
			element2.appendChild(element1.getFirstChild());
		}
		element1.getParentNode().replaceChild(element2, element1);
		
		// Transform document to string
		Transformer tfr = TransformerFactory.newInstance().newTransformer();
		StringWriter sw = new StringWriter();
		tfr.transform(new DOMSource(document), new StreamResult(sw));
		String transformedXMLStr = sw.toString();
		
		return transformedXMLStr;
	}

}
