package main.discozoosolver;

import java.io.File;

import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.LinkedHashMap;

import static java.lang.Integer.parseInt;

public class GameDataParser {
    public static Map<String, Location> parseData() {
        Map<String, Location> locations = new LinkedHashMap<>();
        try {
            DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
            Document doc = docBuilder.parse(new File("./src/main/resources/data.xml"));

            NodeList locationNodes = doc.getElementsByTagName("LOCATION");

            for (int i = 0; i < locationNodes.getLength(); i++) {
                Node locationNode = locationNodes.item(i);
                if (locationNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element locationElement = (Element) locationNode;
                    Element locationNameElement = (Element) locationElement.getElementsByTagName("NAME").item(0);
                    String locationName = locationNameElement.getChildNodes().item(0).getNodeValue().trim();
                    NodeList animalNodes = locationElement.getElementsByTagName("ANIMAL");

                    List<Animal> animals = new ArrayList<>();
                    for (int j = 0; j < animalNodes.getLength(); j++) {
                        Node animalNode = animalNodes.item(j);
                        if (animalNode.getNodeType() == Node.ELEMENT_NODE) {
                            Element animalElement = (Element) animalNode;
                            Element nameElement = (Element) animalElement.getElementsByTagName("NAME").item(0);
                            String animalName = nameElement.getChildNodes().item(0).getNodeValue().trim();

                            Element patternElement = (Element) animalElement.getElementsByTagName("PATTERN").item(0);
                            Pattern animalPattern = createPattern(patternElement.getChildNodes().item(0).getNodeValue().trim());

                            Animal animal = new Animal(animalName, animalPattern);
                            animals.add(animal);
                        }
                    }
                    Location location = new Location(locationName, animals);
                    locations.put(locationName, location);
                }
            }
        } catch (SAXParseException err) {
            System.out.println("** Parsing error" + ", line "
                    + err.getLineNumber() + ", uri " + err.getSystemId());
            System.out.println(" " + err.getMessage());

        } catch (SAXException e) {
            Exception x = e.getException();
            ((x == null) ? e : x).printStackTrace();

        } catch (Throwable t) {
            t.printStackTrace();
        }
    return locations;
    }

    private static Pattern createPattern(String input) {
        String[] coords = input.split("\\s+(?=\\()");
        List<Block> blocks = new ArrayList<>();
        for (String coord : coords) {
            coord = coord.replaceAll("([()])", "");
            String[] values = coord.split(" ");
            Block block = new Block(parseInt(values[0]), parseInt(values[1]));
            blocks.add(block);
        }
        return new Pattern(blocks);
    }
}
