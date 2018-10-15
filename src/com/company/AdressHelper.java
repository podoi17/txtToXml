package com.company;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class AdressHelper {

    private  final String bezirk = "Bezirk: ";
    private  final String ortsteil = "Ortsteil: ";
    private  final String strasse = "Strasse: ";
    private final String hausnummer = "Hausnummer: ";
    private Denkmal denkmal;

    public Denkmal getObjectDetails(String id) {
        try {
            denkmal = new Denkmal(id,"","",null,"");
            Document doc = Jsoup.connect("http://www.stadtentwicklung.berlin.de/denkmal/liste_karte_datenbank/de/denkmaldatenbank/daobj.php?obj_dok_nr=" + id).get();
            Elements discription = doc.select(".mittelspalte_contentseite");
            for(Element element: discription) {
                denkmal.setDiscription(element.getElementsByTag("h2").text());
            }
            Elements elements = doc.select(".denkmal_detail_head tr");
            int counter = 0;
            List<String> tempStreets = new ArrayList<>();
            List<String> tempNums = new ArrayList<>();
            String tempOrtsTeil = "";
            for (Element element : elements) {
                String text = element.text();

                if(text.contains(bezirk)) {
                    counter = counter + 1;
                    text = text.replace(bezirk, "");
                    denkmal.setDistrict(text);
                }
                if(text.contains(ortsteil)) {
                    text = text.replace(ortsteil, "");
                    tempOrtsTeil = text;
                }
                if(text.contains(strasse)) {
                    text = text.replace(strasse, "");
                    tempStreets.add(text);
                }
                if(text.contains(hausnummer)) {
                    text = text.replace(hausnummer, "");
                    text = text.replace(" &", ",");
                    tempNums.add(text);
                }
                if(counter > 1) {
                    System.out.println("for "+ id + " not possible");
                }
            }


            List<String> adresses = new ArrayList<>();
            if(tempStreets.size() == tempNums.size()) {
                for(int i = 0; i < tempNums.size(); i++) {
                    String adress = tempStreets.get(i) + ": " + tempNums.get(i);
                    adresses.add(adress);
                }
                denkmal.setStreets(adresses);
            } else {
                denkmal.setStreets(tempStreets);
            }

            String postal;
            if (!tempStreets.isEmpty()) {
                postal = getPostalCode(tempOrtsTeil, tempStreets.get(0));
            } else {
                postal = getPostalCode(tempOrtsTeil, "");
            }

            denkmal.setPostal(postal);
        } catch (Exception e) {
            System.out.println(e.getCause());
        }
        return denkmal;
    }





    public String getPostalCode(String ortsteil, String strasse) {
        String postal = "";
        try {
            String search = ortsteil + " " + strasse;
            String searchURLBing= "http://www.bing.com/search?q=" + search + "&num=" + 1;
            String searchURL = "http://www.google.com/search?q=" + search + "&num=" + 1;
            //Document doc = Jsoup.connect(searchURL).timeout(30000).userAgent("Mozilla/5.0 (Windows; U; Windows NT 6.1; rv:2.2) Gecko/20110201").get();
            Document doc = Jsoup
                    .connect(searchURL)
                    .userAgent(
                            "Mozilla/5.0 (compatible; Googlebot/2.1; +http://www.google.com/bot.html)")
                    .timeout(50000).get();

            Elements results = doc.select("h3.r > a");
            postal = results.get(0).text();
            if(Pattern.compile("[0-9]+").matcher(postal).find()) {
                postal = postal.substring(postal.length() - 12, postal.length());
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getCause());
            System.out.println(ortsteil + ", " + strasse);
        }
        if(postal.matches("[0-9]+ Berlin")) {
            return postal;
        } else {
            return "-";
        }

    }






    //




}
