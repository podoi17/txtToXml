package com.company;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

public class Main {

    public static void main(String[] args) {



        XMLFile xmlFileFactory = new XMLFile();

        File file = new File("denkmaleTest.xml");


        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(file.getPath());

            String rootNode = document.getDocumentElement().getNodeName();
            NodeList nodeList = document.getElementsByTagName(rootNode);
            Node firstNode = document.getElementsByTagName(rootNode).item(0);
            NodeList nList = document.getElementsByTagName("denkmal");
            Node first = nList.item(0);
            Element year = document.createElement("year");
            year.appendChild(document.createTextNode("1999"));
            first.appendChild(year);




            document.getDocumentElement().normalize();



//            for(int i = 0; i < nList.getLength(); i++) {
//                Node child = nList.item(i);
//                Element author = document.createElement("value");
//                author.appendChild(document.createTextNode("foo"));
//                child.appendChild(author);
//
////                if(child.getNodeType() == Node.ELEMENT_NODE) {
////                    Element element = (Element) child;
////
////                    System.out.println(element.getElementsByTagName("id").item(0).getTextContent());
////                }
//
//            }
            document.getDocumentElement().normalize();

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(OutputKeys.METHOD, "xml");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(new File("denkmaleTest.xml"));
            transformer.transform(source, result);

//            Node node = nodeList.item(0);
//
//            String servername = ((Element) nodeList.item(0)).getElementsByTagName("description").
//                    item(1).getChildNodes().item(0).getNodeValue();
//            Element element = ((Element) node).getElementsByTagName("denkmal");
//            System.out.println(servername);

//            NodeList nodeList = document.getElementsByTagName("denkmaeler");
            //NodeList nodeList = node.getChildNodes();
//            for(int i = 0; i < nodeList.getLength(); i++) {
//                Node child = nodeList.item(i);
//                Element element = (Element) child;
//                Element childE = (Element) element.getElementsByTagName("id").item(0);
//                String name = child.getLocalName();
//                if(name != null && name.equals("denkmal")) {
//                    System.out.println(child.getFirstChild());
//                }
//            }


        } catch (ParserConfigurationException e ) {
            System.out.println(e.getCause());
        } catch (SAXException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } catch (TransformerConfigurationException e) {
            System.out.println(e.getMessage());
        } catch (TransformerException e) {
            System.out.println(e.getMessage());
        }
//        xmlFileFactory.enrichXML(file);

//        AdressHelper adressHelper = new AdressHelper();
//        System.out.println(adressHelper.getPostalCode("Spandau", "Brunsbüttler Damm"));
//
//
//        try {
//            //String search = ortsteil + " " + strasse;
//            String searchURL = "http://www.bing.com/search?q=" + "Spandau Brundsbüttler Damm" + "&num=" + 1;
//            //Document doc = Jsoup.connect(searchURL).timeout(30000).userAgent("Mozilla/5.0 (Windows; U; Windows NT 6.1; rv:2.2) Gecko/20110201").get();
//            Document doc = Jsoup
//                    .connect(searchURL)
//                    .userAgent(
//                            "Mozilla/5.0 (compatible; Googlebot/2.1; +http://www.google.com/bot.html)")
//                    .timeout(5000).get();
//
//
//            Elements results = doc.select("h3.r > a");
//            String postal = results.get(0).text();
//            System.out.println(postal);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }


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
