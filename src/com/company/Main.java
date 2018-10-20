package com.company;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

public class Main {

    public static void main(String[] args) {



        XMLFile xmlFileFactory = new XMLFile();

        File file = new File("denkmaleTemp.xml");


        AdressHelper adressHelper = new AdressHelper();
        xmlFileFactory.setDoc(file.getPath());
        xmlFileFactory.writeIntoXMLFile(file);

//        adressHelper.getBodyInfo("09090325");



        NodeList nodeList = xmlFileFactory.getDoc().getElementsByTagName("denkmal");
        String rootNode = xmlFileFactory.getDoc().getDocumentElement().getNodeName();
        Node root = xmlFileFactory.getDoc().getDocumentElement();
        Document document = root.getOwnerDocument();

        for(int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            String id = node.getAttributes().getNamedItem("id").getTextContent();
            List<String> newInfos = adressHelper.getBodyInfo(id);
            if(id.equals("09030948")) {
                System.out.println("foo");
            }
            for(String info: newInfos) {
                String[] temp = info.split(":");
                try {
                    xmlFileFactory.addElement(node, temp[0], temp[1]);
                } catch (ArrayIndexOutOfBoundsException e) {
                    System.out.println(id);
                }

            }
        }


        xmlFileFactory.writeIntoXMLFile(file);
        String temp = xmlFileFactory.convertXMLFileToString(file);
        xmlFileFactory.convertStringToDocument(temp);
        xmlFileFactory.writeIntoXMLFile(file);


//        List<String> foo = adressHelper.getBodyInfo("09030947");
//        System.out.println(foo.size());
//        Node node = xmlFileFactory.getNode("09030947");
//        for(String bar: foo) {
//            xmlFileFactory.addElement(node, "foo", bar);
//        }
//
//        System.out.println(node.getTextContent());



//        for(String id: ids) {
//
//        }

//        XMLFile xmlFile = new XMLFile();
//        xmlFile.setDoc(file.getPath());
//        xmlFile.getDoc().getDocumentElement().normalize();
//        String rootNode = xmlFile.getDoc().getDocumentElement().getNodeName();
//        NodeList nodeList = xmlFile.getDoc().getElementsByTagName("denkmal");


//        xmlFile.getAttributes("denkmal", "id");
//        for (int i = 0; i < nodeList.getLength(); i++) {
//            Node node = nodeList.item(i);
//            xmlFile.addIdToAttributeAndRemoveIdElement(node);
//        }
//
//        xmlFile.writeIntoXMLFile(file);
//        for (int i = 0; i < nodeList.getLength(); i++) {
//            Node node = nodeList.item(i);
//            System.out.println(node.getAttributes().getNamedItem("id").getTextContent());
//        }




//        XMLFile xmlFile = new XMLFile();
//        xmlFile.setDoc(file.getPath());
//        xmlFile.getDoc().getDocumentElement().normalize();
//        String rootNode = xmlFile.getDoc().getDocumentElement().getNodeName();
//        NodeList nodeList = xmlFile.getDoc().getElementsByTagName("denkmal");
//
//        for (int i = 0; i < nodeList.getLength(); i++) {
//            Node node = nodeList.item(i);
//            xmlFile.addIdToAttributeAndRemoveIdElement(node);
//        }
//
//        Node first = nodeList.item(0);
//        Element year = xmlFile.getDoc().createElement("year");
//        year.appendChild(xmlFile.getDoc().createTextNode("1000"));
//        first.appendChild(year);
//
//
//        xmlFile.writeIntoXMLFile(file);

//

//	// write your code here
//        String path = "DenkmallisteBerlin.txt";
//        TXTService transform = new TXTService(path);
//        transform.getObjectIDS();
//        HashSet<String> ids = transform.getObjectIDS();
//        AdressHelper adressHelper = new AdressHelper();
//        List<Denkmal> denkmals = new ArrayList<>();
//        double counterID = 0.0;
//        double percentageID = 0.1;
//        int icounter = 0;
//        Iterator iterator = ids.iterator();
//        while(iterator.hasNext()) {
//            Denkmal denkmal = adressHelper.getObjectDetails(iterator.next().toString());
//            denkmals.add(denkmal);
//            if(counterID % 100 == 0) {
//                System.out.println(counterID + " details generated");
//            }
//            counterID = counterID + 1;
//            try {
//                if(counterID % 600 == 0) {
//                    System.out.println("kurz pausiert für 100 Sekunden. Reconnect!!!!!!!!");
//                    System.out.println("kurz pausiert für 100 Sekunden. Reconnect!!!!!!!!");
//                    Thread.sleep(100000);
//                    System.out.println("Weiter gehts");
//                }
//            } catch (Exception e) {
//                System.out.println(e.getCause());
//            }
//            icounter++;
//        }



    }
}
