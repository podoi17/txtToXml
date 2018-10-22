package com.company;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

public class DenkmalHelper {

    private  final String bezirk = "Bezirk: ";
    private  final String ortsteil = "Ortsteil: ";
    private  final String strasse = "Strasse: ";
    private final String hausnummer = "Hausnummer: ";
    private final String entwurf = "Entwurf:";
    private final String datierung = "Datierung:";
    private final String ausfuehrung = "Ausführung:";
    private final String bauherr = "Bauherr:";
    private final String sachbegriff = "Sachbegriff:";
    private final String teilNr = "Teil-Nr.:";
    private final String literatur = "Literatur:";
    private final String umbau = "Umbau:";
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

    public List<String> getBodyInfo(String id) {
        List<String> newElements = new ArrayList<>();
        try {
            Document doc = Jsoup.connect("http://www.stadtentwicklung.berlin.de/denkmal/liste_karte_datenbank/de/denkmaldatenbank/daobj.php?obj_dok_nr=" + id).get();
            Elements bodies = doc.select(".denkmal_detail_body tr");
            Elements subs = doc.select(".denkmal_detail_sub");
            if(subs.isEmpty()) {
                try {
                    for(Element element : bodies) {
                        String text = element.text();
                        if(text.contains(entwurf)) {
                            text = text.replace(entwurf, "design:");
                        }
                        if(text.contains(datierung)) {
                            text = text.replace(datierung, "date:");
                        }
                        if(text.contains(ausfuehrung)) {
                            text = text.replace(ausfuehrung, "execution:");
                        }
                        if(text.contains(bauherr)) {
                            text = text.replace(bauherr, "builder-owner:");
                        }
                        if(text.contains(literatur)) {
                            text = text.replace(literatur, "literature:");
                        }
                        if(text.contains(umbau)) {
                            text = text.replace(umbau, "reconstruction:");
                        }
                        newElements.add(text);
                    }
                } catch (ArrayIndexOutOfBoundsException e) {
                    System.out.println(bodies.text());
                }

            } else {

            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return newElements;
    }

    public void getBodyTest(String id) {
        try {
            Document doc = Jsoup.connect("http://www.stadtentwicklung.berlin.de/denkmal/liste_karte_datenbank/de/denkmaldatenbank/daobj.php?obj_dok_nr=" + id).get();
            Elements bodies = doc.select(".denkmal_detail_body");
            Element body = bodies.get(2);
            Elements els = body.getElementsByTag("tr");
            for(Element el : els) {
                System.out.println(el.text());
            }

//            for(int i = 0; i < bodies.size(); i++) {
//                System.out.println(bodies.get(i));
//            }
        } catch (IOException e) {

        }
    }


    public String getDetaliText(String id) {
        String text = "";
        try {
            Document doc = Jsoup.connect("http://www.stadtentwicklung.berlin.de/denkmal/liste_karte_datenbank/de/denkmaldatenbank/daobj.php?obj_dok_nr=" + id).get();
            Elements detailText = doc.select(".denkmal_detail_text p");
            for(Element element : detailText) {
                text = text + element.text();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return text;
    }

    //evtl todo
//    public HashMap<String, List<String>> checkForSub(String id) {
//        HashMap<String, List<String>> subInfos = new HashMap<>();
//        try {
//            Document doc = Jsoup.connect("http://www.stadtentwicklung.berlin.de/denkmal/liste_karte_datenbank/de/denkmaldatenbank/daobj.php?obj_dok_nr=" + id).get();
//            Elements subs = doc.select(".denkmal_detail_sub");
//            int subsLength = subs.size();
//            Elements bodies = doc.select(".denkmal_detail_body");
//            int bodiesLength = bodies.size();
//            int startBodyForSubs = bodiesLength % subsLength;
//            if(!subs.isEmpty()) {
//                for(int i = startBodyForSubs; i < bodiesLength; i++) {
//                    String key = subs.get(i - startBodyForSubs).text();
//                    Element subBody = bodies.get(i);
//                    Elements subBodyElements = subBody.getElementsByTag("tr");
//                    List<String> temp = new ArrayList<>();
//                    for(Element el : subBodyElements) {
//                        temp.add(el.text());
//                    }
//                    subInfos.put(key, temp);
//                }
//             }
//        } catch (IOException e) {
//            System.out.println(e.getMessage());
//        }
//        return subInfos;
//    }

    public void saveImages(String id) {

    }

    public List<String> getImageLinks(String id) {
        String link = "http://www.stadtentwicklung.berlin.de/denkmal/liste_karte_datenbank/de/denkmaldatenbank/";
        List<String> images = new ArrayList<>();
        try {
            Document doc = Jsoup.connect("http://www.stadtentwicklung.berlin.de/denkmal/liste_karte_datenbank/de/denkmaldatenbank/daobj.php?obj_dok_nr=" + id).get();
            Elements elements = doc.select(".denkmal_detail_img a");
            for(Element el : elements) {
                String attr = el.attr("href");
                String temp = link + attr;
                images.add(temp);
            }

        } catch (IOException e) {
            System.out.println(e.getMessage() + " " + id);
        }
        return images;
    }






    //




}
