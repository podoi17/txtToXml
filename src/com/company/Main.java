package com.company;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

public class Main {

    public static void main(String[] args) {



        XMLFile xmlFileFactory = new XMLFile();

        File file = new File("denkmaleTest.xml");


        try {
            Reader fileReader = new FileReader(file);
            BufferedReader bufReader = new BufferedReader(fileReader);

            StringBuilder sb = new StringBuilder();
            String line = bufReader.readLine();
            while( line != null){
                line = bufReader.readLine();

            }
            String xml2String = sb.toString();
            System.out.println("XML to String using BufferedReader : ");
            System.out.println(xml2String);

            bufReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


//        AdressHelper adressHelper = new AdressHelper();
//        xmlFileFactory.setDoc(file.getPath());
//        xmlFileFactory.writeIntoXMLFile(file);
//
//        adressHelper.getBodyInfo("09090494");

//            XMLFile xmlFile = new XMLFile();
//            xmlFile.setDoc(file.getPath());
//            xmlFile.getDoc().getDocumentElement().normalize();
//            String rootNode = xmlFile.getDoc().getDocumentElement().getNodeName();
//            NodeList nodeList = xmlFile.getDoc().getElementsByTagName("denkmal");

//            for(int i = 0; i < nodeList.getLength(); i++) {
//                Node node = nodeList.item(i);
//                xmlFile.addIdToAttributeAndRemoveIdElement(node);
//            }

//            Node first = nodeList.item(0);
//            Element year = xmlFile.getDoc().createElement("year");
//            year.appendChild(xmlFile.getDoc().createTextNode("1000"));
//            first.appendChild(year);
//
//
//            xmlFile.writeIntoXMLFile(file);

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
