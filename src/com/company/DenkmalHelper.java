package com.company;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class DenkmalHelper {

    private  final String bezirk = "Bezirk: ";
    private  final String ortsteil = "Ortsteil: ";
    private  final String strasse = "Strasse: ";
    private final String hausnummer = "Hausnummer: ";
    private final String entwurf = "Entwurf:";
    private final String ausfuehrungUndEntwurfSpecial = "Ausführung & Entwurf (?):";
    private final String ausfuehrungUndEntwurf = "Ausführung & Entwurf:";
    private final String entwurfUndAusfuehrungSpecial = "Entwurf & Ausführung (?):";
    private final String entwurfUndAusfuerungSpecialSecond = "Entwurf (?) & Ausführung (?):";
    private final String entwurfUndAusfuehrungSpecialThird = "Entwurf & Ausführung?:";
    private final String entwurfUndAusfuehrungSpecialFourth = "Entwurf (?) & Ausführung:";
    private final String entwurfUndAusfuehrungSpecialFifth = "Entwurf? & Ausführung:";
    private final String entwurfUndAusfuehrung = "Entwurf & Ausführung:";
    private final String entwurfUndAusfuehrungUndBauherrSpecial = "Entwurf & Ausführung (?) & Bauherr:";
    private final String entwurfUndAusfuehrungUndBauherr = "Entwurf & Ausführung & Bauherr:";
    private final String bauherrUndEntwurfUndAusführung = "Bauherr & Entwurf & Ausführung:";
    private final String entwurfUndBauherrSpecial = "Entwurf (?) & Bauherr:";
    private final String entwurfUndBauherr = "Entwurf & Bauherr:";
    private final String bauherrUndEntwurf = "Bauherr & Entwurf:";
    private final String ausfuehrungUndBauherr = "Ausführung & Bauherr:";
    private final String ausfuehrungUndBauherrSpecial = "Ausführung (?) & Bauherr:";
    private final String bauherrUndAusfuehrung = "Bauherr & Ausführung:";
    private final String entwurfUndBaubeginn = "Entwurf & Baubeginn:";
    private final String entwurfUndDatierung = "Entwurf & Datierung:";
    private final String designSpecial = "Entwurf (?):";
    private final String datierung = "Datierung:";
    private final String ausfuehrungSpecial = "Ausführung (?):";
    private final String ausfuehrung = "Ausführung:";
    private final String bauherrSpecial = "Bauherr (?):";
    private final String bauherr = "Bauherr:";
    private final String sachbegriff = "Sachbegriff:";
    private final String teilNr = "Teil-Nr.:";
    private final String literatur = "Literatur:";
    private final String umbau = "Umbau:";
    private final String undefined = "---:";
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
                        if(text.contains(undefined)) {
                            text = text.replace(undefined, "undefined:");
                        }
                        if(text.contains(entwurfUndAusfuehrungUndBauherrSpecial)) {
                            text = text.replace(entwurfUndAusfuehrungUndBauherrSpecial, "architect-execution-builder-owner:");
                        }
                        if(text.contains(entwurfUndAusfuehrungUndBauherr)) {
                            text = text.replace(entwurfUndAusfuehrungUndBauherr, "architect-execution-builder-owner:");
                        }
                        if(text.contains(bauherrUndEntwurfUndAusführung)) {
                            text = text.replace(bauherrUndEntwurfUndAusführung, "architect-execution-builder-owner:");
                        }
                        if(text.contains(ausfuehrungUndEntwurfSpecial)) {
                            text = text.replace(ausfuehrungUndEntwurfSpecial, "architect-execution:");
                        }
                        if(text.contains(entwurfUndAusfuerungSpecialSecond)) {
                            text = text.replace(entwurfUndAusfuerungSpecialSecond, "architect-execution:");
                        }
                        if(text.contains(entwurfUndAusfuehrungSpecialThird)) {
                            text = text.replace(entwurfUndAusfuehrungSpecialThird, "architect-execution:");
                        }
                        if(text.contains(entwurfUndAusfuehrungSpecialFourth)) {
                            text = text.replace(entwurfUndAusfuehrungSpecialFourth, "architect-execution:");
                        }
                        if(text.contains(entwurfUndAusfuehrungSpecialFifth)) {
                            text = text.replace(entwurfUndAusfuehrungSpecialFifth, "architect-execution:");
                        }
                        if(text.contains(entwurfUndAusfuehrungSpecial)) {
                            text = text.replace(entwurfUndAusfuehrungSpecial, "architect-execution:");
                        }
                        if(text.contains(entwurfUndAusfuehrung)) {
                            text = text.replace(entwurfUndAusfuehrung, "architect-execution:");
                        }
                        if(text.contains(entwurfUndBauherrSpecial)) {
                            text = text.replace(entwurfUndBauherrSpecial, "architect-builder-owner:");
                        }
                        if(text.contains(entwurfUndBauherr)) {
                            text = text.replace(entwurfUndBauherr,"architect-builder-owner:");
                        }
                        if(text.contains(bauherrUndEntwurf)) {
                            text = text.replace(bauherrUndEntwurf, "architect-builder-owner:");
                        }
                        if(text.contains(ausfuehrungUndBauherrSpecial)) {
                            text = text.replace(ausfuehrungUndBauherrSpecial, "architect-builder-owner:");
                        }
                        if(text.contains(ausfuehrungUndBauherr)) {
                            text = text.replace(ausfuehrungUndBauherr, "execution-builder-owner:");
                        }
                        if(text.contains(ausfuehrungUndEntwurf)) {
                            text = text.replace(ausfuehrungUndEntwurf, "architect-execution:");
                        }
                        if(text.contains(bauherrUndAusfuehrung)) {
                            text = text.replace(bauherrUndAusfuehrung, "execution-builder-owner");
                        }
                        if(text.contains(entwurfUndBaubeginn)) {
                            text = text.replace(entwurfUndBaubeginn, "architect-start-of-construction:");
                        }
                        if(text.contains(entwurfUndDatierung)) {
                            text = text.replace(entwurfUndDatierung, "architect-date");
                        }
                        if(text.contains(designSpecial)) {
                            text = text.replace(designSpecial, "architect:");
                        }
                        if(text.contains(entwurf)) {
                            text = text.replace(entwurf, "architect:");
                        }
                        if(text.contains(datierung)) {
                            text = text.replace(datierung, "date:");
                        }
                        if(text.contains(ausfuehrungSpecial)) {
                            text = text.replace(ausfuehrungSpecial, "execution:");
                        }
                        if(text.contains(ausfuehrung)) {
                            text = text.replace(ausfuehrung, "execution:");
                        }
                        if(text.contains(bauherrSpecial)) {
                            text = text.replace(bauherrSpecial, "builder-owner:");
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

    public String convertStreetForGeoData(String street) {
        street = street.replace(":", "");
        street = street.replace("&", ",");
        String[] temp = street.split(",");
        return temp[0];
    }

    public String convertLocationForGeoData(String location) {
        String controll = "- ";
        if(location.length() > 2 && controll.equals(location.substring(0, 2))) {
            location = location.substring(2, location.length());
        }
        return location;
    }

    public String[] openStreetMapSearch(String url ) throws IOException, ParseException, IndexOutOfBoundsException {
        String[] latiLongi = new String [2];
        String json = Jsoup.connect(url).userAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:47.0) Gecko/20100101 Firefox/47.0").ignoreContentType(true).execute().body();
        JSONParser jsonParser = new JSONParser();
        JSONArray jsonArray = (JSONArray) jsonParser.parse(json);
        if(jsonArray.size() > 0) {
            JSONObject jsonObject = (JSONObject) jsonArray.get(0);
            String lati = jsonObject.get("lat").toString();
            String longi = jsonObject.get("lon").toString();
            latiLongi[0] = lati;
            latiLongi[1] = longi;
        } else {
            latiLongi[0] = "";
            latiLongi[1] = "";
        }
        return latiLongi;
    }

    public String[] getGeoData(String location, String street) {
        String[] geoData = new String[2];
        if(location.contains("Berlin")) {
            location = location.substring(0, 12);
        }
        if(location != null && street != null) {
            location = location.replace(" ", "%20");
            street = street.replace(" ", "%20");
            location = convertLocationForGeoData(location);
            street = convertStreetForGeoData(street);
            String startURL = "https://nominatim.openstreetmap.org/search/";
            String endURL = "?format=json&addressdetails=1&limit=1&polygon_svg=1";
            String url = startURL + street + "%20" + location + "%20"+ endURL;
            try {
                geoData = openStreetMapSearch(url);
                if(geoData[0] == "" && geoData[1] == "") {
                    String urlAlternative = startURL + street + "%20" + "Berlin" + "%20"+ endURL;
                    geoData = openStreetMapSearch(urlAlternative);
                }
            } catch (IOException e) {
                System.out.println("IOexecpt geworfen " + street + " " + location);
                geoData[0] = "-1";
                geoData[1] = "-1";
                return geoData;
            } catch (ParseException e) {
                System.out.println("ParseException " + street + " " + location);
                geoData[0] = "-1";
                geoData[1] = "-1";
                return geoData;
            } catch (IndexOutOfBoundsException e) {
                System.out.println("IndexOutOufBounds " + street + " " + location);
                geoData[0] = "-1";
                geoData[1] = "-1";
                return geoData;
            }
        }
        return geoData;
    }

// Ort durch berlin ersetzen
    // leere suche ignorieren







    //




}
