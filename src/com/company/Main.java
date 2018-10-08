package com.company;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Main {

    public static void main(String[] args) {
//	// write your code here
        String path = "/Users/Manuel/Documents/HTW/fuenfteSemester/projekt/DenkmallisteBerlin.txt";
        TXTService transform = new TXTService(path);
        transform.getObjectIDS();
//        transformer.printList();
        HashSet<String> ids = transform.getObjectIDS();
        AdressHelper adressHelper = new AdressHelper();
        List<Denkmal> denkmals = new ArrayList<>();
        double counterID = 0.0;
        double percentageID = 0.1;
        for(String id: ids) {
            Denkmal denkmal = adressHelper.getObjectDetails(id);
            denkmals.add(denkmal);
            if(counterID % 100 == 0) {
                System.out.println(counterID + " details generated");
            }
            counterID = counterID + 1;
        }


        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.newDocument();

            Element rootElement = doc.createElement("denkmaeler");
            doc.appendChild(rootElement);
            double counterXML = 0;
            double percentage = 0.25;
            for(Denkmal denkmal : denkmals) {

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
}
