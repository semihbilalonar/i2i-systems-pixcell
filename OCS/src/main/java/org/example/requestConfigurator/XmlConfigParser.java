package org.example.requestConfigurator;


import org.example.requestMessage.AkkaRequestMessage;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;


public class XmlConfigParser {

    public void parseAndAssign(AkkaRequestMessage message, File xmlFile) {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);

            NodeList serviceList = doc.getElementsByTagName("service");

            for (int i = 0; i < serviceList.getLength(); i++) {
                Node node = serviceList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;

                    String type = element.getElementsByTagName("type").item(0).getTextContent();
                    int location = Integer.parseInt(element.getElementsByTagName("location").item(0).getTextContent());
                    String bnumberRegex = null;
                    if(element.getElementsByTagName("bnumber").getLength() > 0) {
                        bnumberRegex = element.getElementsByTagName("bnumber").item(0).getTextContent();
                    }
                    double price = Double.parseDouble(element.getElementsByTagName("price").item(0).getTextContent());

                    int rating = 0; // default value
                    if ("data".equals(type)) {
                        rating = Integer.parseInt(element.getElementsByTagName("rating").item(0).getTextContent());
                    }

                    // Conditions to match
                    boolean matches = type.equals(message.getType()) && location == message.getLocation();

                    if (bnumberRegex != null) {
                        matches = matches && message.getReceiverMSISDN().matches(bnumberRegex);
                    }

                    if ("data".equals(type)) {
                        matches = matches && rating == message.getRatingNumber();
                    }

                    if (matches) {
                        message.setUnitPrice(price);
                        return; // Exit the loop as soon as we find a match
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}