package com.company;







import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.jsoup.Jsoup;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;
import org.omg.CORBA.OBJ_ADAPTER;


import javax.sound.midi.Soundbank;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {



        GeoCoordinatesHelper geoCoordinatesHelper = new GeoCoordinatesHelper();

        geoCoordinatesHelper.getCoordinates("Innsbrucker Straße 37 Falkensee");

        String url = "https://nominatim.openstreetmap.org/search/gb/birmingham/pilkington%20avenue/135?format=xml&polygon=1&addressdetails=1";

        String url1 = "https://nominatim.openstreetmap.org/search/Unter%20den%20Linden%201%20Berlin?format=json&addressdetails=1&limit=1&polygon_svg=1";



        try {



           String json = Jsoup.connect(url1).ignoreContentType(true).execute().body();
           System.out.println(json);


            JSONParser jsonParser = new JSONParser();
            JSONArray jsonArray = (JSONArray) jsonParser.parse(json);
            System.out.println(jsonArray.get(0));
            JSONObject jsonObject = (JSONObject) jsonArray.get(0);
            System.out.println(jsonObject.get("lat"));
            System.out.println(jsonObject.get("lon"));

            List<String> list = new ArrayList<>();
            for(int i = 0; i < jsonArray.size(); i++) {
                list.add(jsonArray.get(i).toString());
            }
            System.out.println(jsonArray.toString());





//           Document document = Jsoup.parse(new URL(url).openStream(), "UTF-8", "", Parser.xmlParser());
//           Elements elements = document.select("place");
//           for(Element el: elements) {
//               System.out.println(el.text());
//           }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }


//        XMLFile xmlFileFactory = new XMLFile();
//
//        File file = new File("denkmaleTemp.xml");
//
//
//        DenkmalHelper denkmalHelper = new DenkmalHelper();
//        xmlFileFactory.setDoc(file.getPath());
//        xmlFileFactory.writeIntoXMLFile(file);
//
////        denkmalHelper.getBodyInfo("09090325");
//
//
//
//        NodeList nodeList = xmlFileFactory.getDoc().getElementsByTagName("denkmal");
//        String rootNode = xmlFileFactory.getDoc().getDocumentElement().getNodeName();
//        Node root = xmlFileFactory.getDoc().getDocumentElement();
//        Document document = root.getOwnerDocument();





        //funkst!!!
//        for(int i = 0; i < nodeList.getLength(); i++) {
//            Node node = nodeList.item(i);
//            String id = node.getAttributes().getNamedItem("id").getTextContent();
//            List<String> newInfos = denkmalHelper.getBodyInfo(id);
//            if(id.equals("09030948")) {
//                System.out.println("foo");
//            }
//            for(String info: newInfos) {
//                String[] temp = info.split(":");
//                try {
//                    xmlFileFactory.addElement(node, temp[0], temp[1]);
//                } catch (ArrayIndexOutOfBoundsException e) {
//                    System.out.println(id);
//                }
//            }
//            String detailText = denkmalHelper.getDetaliText(id);
//            xmlFileFactory.addElement(node, "text", detailText);
//            List<String> imageLinks = denkmalHelper.getImageLinks(id);
//            for(String imageLink : imageLinks) {
//                xmlFileFactory.addElement(node, "image", imageLink);
//            }
//        }
//
//
//        xmlFileFactory.writeIntoXMLFile(file);
//        String temp = xmlFileFactory.convertXMLFileToString(file);
//        xmlFileFactory.convertStringToDocument(temp);
//        xmlFileFactory.writeIntoXMLFile(file);


//        List<String> foo = denkmalHelper.getBodyInfo("09030947");
//        System.out.println(foo.size());
//        Node node = xmlFileFactory.getNode("09030947");
//        for(String bar: foo) {
//            xmlFileFactory.addElement(node, "foo", bar);
//        }
//
//        System.out.println(node.getTextContent());







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
//        DenkmalHelper denkmalHelper = new DenkmalHelper();
//        List<Denkmal> denkmals = new ArrayList<>();
//        double counterID = 0.0;
//        double percentageID = 0.1;
//        int icounter = 0;
//        Iterator iterator = ids.iterator();
//        while(iterator.hasNext()) {
//            Denkmal denkmal = denkmalHelper.getObjectDetails(iterator.next().toString());
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
