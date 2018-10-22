package com.company;

import org.w3c.dom.*;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class XMLFile {



    DocumentBuilderFactory documentBuilderFactory;
    DocumentBuilder documentBuilder;
    Document doc;

    public XMLFile() {
        try {
            documentBuilderFactory  = DocumentBuilderFactory.newInstance();
            documentBuilder = documentBuilderFactory.newDocumentBuilder();
            doc = documentBuilder.newDocument();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


    public void createNewXML(Element element) {
        this.doc.appendChild(element);
    }

    public void createXML(List<Denkmal> denkmals) {
        try {


            Element rootElement = doc.createElement("denkmaeler");
            doc.appendChild(rootElement);
            double counterXML = 0;
            double percentage = 0.25;
            for(Denkmal denkmal : denkmals) {


                try {
                    // supercars element
                    Element object = doc.createElement("denkmal");
                    rootElement.appendChild(object);


                    // carname element
                    Element id = doc.createElement("id");
                    id.appendChild(doc.createTextNode(denkmal.getId()));
                    object.appendChild(id);

                    Element description = doc.createElement("description");
                    description.appendChild(doc.createTextNode(denkmal.getDiscription()));
                    object.appendChild(description);

                    Element location = doc.createElement("location");
                    location.appendChild(doc.createTextNode(denkmal.getPostal() + " " + denkmal.getDistrict()));
                    object.appendChild(location);

                    for(int i = 0; i < denkmal.getStreets().size(); i ++) {
                        Element street = doc.createElement("street");
                        street.appendChild(doc.createTextNode(denkmal.getStreets().get(i)));
                        object.appendChild(street);
                    }
                    if(counterXML % 100 == 0) {
                        System.out.println(counterXML + " to xml");
                    }

                    counterXML = counterXML + 1;
                } catch (DOMException e) {
                    e.printStackTrace();
                } catch (NullPointerException e) {
                    System.out.println(e.getCause());
                }
            }
            // write the content into xml file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(OutputKeys.METHOD, "xml");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File("denkmale.xml"));
            transformer.transform(source, result);

            // Output to console for testing
            StreamResult consoleResult = new StreamResult(System.out);
            transformer.transform(source, consoleResult);
        } catch (Exception e) {
            System.out.println(e.getCause());
        }
    }

    public void addIdToAttributeAndRemoveIdElement(Node node, String tagName, int elementIndex) {
        try {
            Element el = (Element) node;
            Element idElement = (Element)el.getElementsByTagName(tagName).item(elementIndex);
            String id = idElement.getTextContent();
            Attr attribute = doc.createAttribute(tagName);
            attribute.setValue(id);
            el.setAttributeNode(attribute);
            node.removeChild(idElement);
        } catch (NullPointerException e) {
            System.out.println("no element named: " + tagName);
        }

    }

    public Document getDoc() {
        return doc;
    }

    public void setDoc(String docPath) {
        try {
            this.doc = documentBuilder.parse(docPath);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    public void writeIntoXMLFile(File file) {
        try {
            doc.getDocumentElement().normalize();
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(OutputKeys.METHOD, "xml");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(file);
            transformer.transform(source, result);
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    public String convertXMLFileToString(File file) {
        String xml2String = "";
        try {
            Reader fileReader = new FileReader(file);
            BufferedReader bufReader = new BufferedReader(fileReader);

            StringBuilder sb = new StringBuilder();
            String line = bufReader.readLine();
            while ((line = bufReader.readLine()) != null) {
                sb.append(line.trim());

            }
            xml2String = sb.toString();
            bufReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return xml2String;
    }


    public void convertStringToDocument(String xmlString) {
        try {
            doc = documentBuilder.parse(new InputSource(new StringReader(xmlString)));
        } catch (SAXException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void addBodys(Node node) {

    }

    public void addSubs(Node node) {

    }

    public List<String> getElementIds(String tagName, String attributeItem) {
        List<String> ids = new ArrayList<>();
        NodeList nodeList = this.doc.getElementsByTagName(tagName);
        List<String> attributList = new ArrayList<>();
        for(int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            String id = node.getAttributes().getNamedItem(attributeItem).getTextContent();
            ids.add(id);
        }
        return ids;
    }

    public void addElement(Node node, String element, String textNode) {
        Element el = this.doc.createElement(element);
        el.appendChild(this.doc.createTextNode(textNode));
        node.appendChild(el);

    }
    public Node getNode(String id) {
        XPath xPath = XPathFactory.newInstance().newXPath();
        String expression = "/denkmaeler/denkmal[@id=" + "'" + id + "'" + "]";
        Node node = null;
        try {
            node = (Node) xPath.compile(expression).evaluate(this.doc, XPathConstants.NODE);
        } catch (XPathExpressionException e) {

        }
        return node;
    }

    public void enrichXML(File file) {


    }
}
