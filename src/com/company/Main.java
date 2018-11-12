package com.company;







import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


import java.io.*;
import java.util.HashSet;

public class Main {

    static HashSet<String> tags = new HashSet<>();
    public static void main(String[] args) {




        String url = "https://nominatim.openstreetmap.org/search/gb/birmingham/pilkington%20avenue/135?format=xml&polygon=1&addressdetails=1";
        String url1 = "https://nominatim.openstreetmap.org/search/Unter%20den%20Linden%201%20Berlin?format=json&addressdetails=1&limit=1&polygon_svg=1";



        int counter = 0;
        String idTemp = "";

        try {

            XMLFile xmlFileFactory = new XMLFile();

            File file = new File("denkmal6.xml");
            File fileTags = new File("tags.txt");

            DenkmalHelper denkmalHelper = new DenkmalHelper();
            FileHelper fileHelper = new FileHelper(fileTags);
            tags = fileHelper.fileToMap();

            xmlFileFactory.setDoc(file.getPath());
            NodeList nodeList = xmlFileFactory.getDoc().getElementsByTagName("denkmal");
//            for(int i = 0; i < nodeList.getLength(); i++) {
//                Node node = nodeList.item(i);
//                xmlFileFactory.addIdToAttributeAndRemoveIdElement(node, "id", 0);
//            }
            System.out.println("adden begonnen");


            //es muss weiter xml erstellt werden und die cases ermittelt werden
            xmlFileFactory.createNewXMLFile(nodeList, tags);
            xmlFileFactory.convertToProperXMLFile(file);
            fileHelper.mapToFile(tags);



        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println(idTemp);
        }


    }
}




//            for (int i = 0; i < nodeList.getLength(); i++) {
//                if(counter == 68) {
//                    System.out.println(counter);
//                }
//                Node node = nodeList.item(i);
//                String id = node.getAttributes().getNamedItem("id").getTextContent();
//                idTemp = id;
//                List<String> newInfos = denkmalHelper.getBodyInfo(id);
//                if (id.equals("09070129")) {
//                    System.out.println("foo");
//                }
//                for (String info : newInfos) {
//                    String[] temp = info.split(":");
//                    try {
//                        xmlFileFactory.addElement(node, temp[0], temp[1]);
//                    } catch (ArrayIndexOutOfBoundsException e) {
//                        System.out.println(id);
//                    }
//                }
//                String detailText = denkmalHelper.getDetaliText(id);
//                xmlFileFactory.addElement(node, "text", detailText);
//                List<String> imageLinks = denkmalHelper.getImageLinks(id);
//                for (String imageLink : imageLinks) {
//                    xmlFileFactory.addElement(node, "image", imageLink);
//                }
//
//                Element element = (Element) node;
//                String location = xmlFileFactory.getTextValueFromElement(element, "location", 0);
//                String street = xmlFileFactory.getTextValueFromElement(element, "street", 0);
//                String[] latiLongi = denkmalHelper.getGeoData(location, street);
//                String latitude = latiLongi[0];
//                String longitude = latiLongi[1];
//                xmlFileFactory.addElement(node,"latitude", latitude);
//                xmlFileFactory.addElement(node, "longitude", longitude);
//                if(counter % 100 == 0) {
//                    System.out.println(counter);
//                }
//                counter = counter + 1;
//
//            }