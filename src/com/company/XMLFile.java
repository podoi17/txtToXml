package com.company;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class XMLFile {




    public void createXML(List<Denkmal> denkmals) {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.newDocument();

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

    public void enrichXML(File file) {
//        try {
//            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
//            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
//            Document document = documentBuilder.parse(file.getAbsolutePath());
//
//
//            NodeList nodeList = document.getElementsByTagName("denkmaeler");
//            //NodeList nodeList = node.getChildNodes();
//            for(int i = 0; i < nodeList.getLength(); i++) {
//                Node child = nodeList.item(i);
//                Element element = (Element) child;
//                Element childE = (Element) element.getElementsByTagName("id").item(0);
//                String name = child.getLocalName();
//                if(name != null && name.equals("denkmal")) {
//                    System.out.println(child.getFirstChild());
//                }
//            }
//
//
//        } catch (ParserConfigurationException e ) {
//            System.out.println(e.getCause());
//        } catch (SAXException e) {
//            System.out.println(e.getMessage());
//        } catch (IOException e) {
//            System.out.println(e.getMessage());
//        }


    }
}
