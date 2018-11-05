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
    DenkmalHelper denkmalHelper;

    public XMLFile() {
        try {
            documentBuilderFactory  = DocumentBuilderFactory.newInstance();
            documentBuilder = documentBuilderFactory.newDocumentBuilder();
            doc = documentBuilder.newDocument();
            denkmalHelper = new DenkmalHelper();
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


    public String getTextValueFromElement(Node node, String elementTagName, int itemIndex) {
        Element element = (Element) node;
        String value = "";
        try {
            value = element.getElementsByTagName(elementTagName).item(itemIndex).getTextContent();
        } catch (NullPointerException e) {
            return e.getMessage();
        }
        return value;
    }

    public void convertToProperXMLFile(File file) {
        writeIntoXMLFile(file);
        String temp = convertXMLFileToString(file);
        convertStringToDocument(temp);
        writeIntoXMLFile(file);
    }

    public void createNewXMLFile(NodeList nodeList) {
        int counter = 0;
        String idTemp = "";
        try {
            for (int i = 0; i < nodeList.getLength(); i++) {
                if(counter == 68) {
                    System.out.println(counter);
                }
                Node node = nodeList.item(i);
                String id = node.getAttributes().getNamedItem("id").getTextContent();
                idTemp = id;
                List<String> newInfos = denkmalHelper.getBodyInfo(id);
                if (id.equals("09070129")) {
                    System.out.println("foo");
                }
                for (String info : newInfos) {
                    String[] temp = info.split(":");
                    if(temp.length == 2) {
                        addElement(node, temp[0].toLowerCase(), temp[1]);
                    }
                }
                String detailText = denkmalHelper.getDetaliText(id);
                addElement(node, "text", detailText);
                List<String> imageLinks = denkmalHelper.getImageLinks(id);
                for (String imageLink : imageLinks) {
                    addElement(node, "image", imageLink);
                }

                Element element = (Element) node;
                String location = getTextValueFromElement(element, "location", 0);
                String street = getTextValueFromElement(element, "street", 0);
                String[] latiLongi = denkmalHelper.getGeoData(location, street);
                String latitude = latiLongi[0];
                String longitude = latiLongi[1];
                addElement(node,"latitude", latitude);
                addElement(node, "longitude", longitude);
                if(counter % 100 == 0) {
                    System.out.println(counter);
                }
                counter = counter + 1;

            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
            System.out.println(idTemp);
        }

    }

    //Elemente auslesen
//    xmlFileFactory.setDoc(file.getPath());
//    NodeList nodeList = xmlFileFactory.getDoc().getElementsByTagName("denkmaeler");
//            System.out.println(xmlFileFactory.getDoc().getDocumentElement().getNodeName());
//    NodeList nlist = xmlFileFactory.getDoc().getElementsByTagName("denkmal");
//    Node node = nlist.item(0);
//    Element element = (Element) node;
//            System.out.println(element.getElementsByTagName("location").item(0).getTextContent());
}
