package Utils;

import Beans.Supermap;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.jsoup.nodes.Element;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class GeneralController {

    public static Map<String,String> redundantNodes;
    public static Map<String,String> redundantAttributes;
    public static Map<String, List<Supermap>> URLvsSupermap;
    public static Map<String,List<String>> urlCsvContent;


    public GeneralController(){

    }


    private static void readURLCSVFile() throws IOException {

        FileProvider fp=FileProvider.createInstance();
        String inputCSVpath=ConfigController.configurations.get("inputcsv");
        Reader reader= new FileReader(fp.getFile(inputCSVpath));
        CSVParser parser=new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());
        List<String> engUrlsList=new ArrayList<String>();
        List<String> locale=new ArrayList<String>();
        List<String> localizedurl=new ArrayList<String>();
        String engurlkeyword=ConfigController.configurations.get("englishurlheader");
        String localeKeyword=ConfigController.configurations.get("localeheader");
        String localizedUrlKey=ConfigController.configurations.get("localizeurlheader");

        for(CSVRecord rec:parser){
            engUrlsList.add(rec.get(engurlkeyword));
            locale.add(rec.get(localeKeyword));
            localizedurl.add(rec.get(localizedUrlKey));
        }
        urlCsvContent.put(engurlkeyword,engUrlsList);
        urlCsvContent.put(localeKeyword,locale);
        urlCsvContent.put(localizedUrlKey,localizedurl);
    }


    static {
        ConfigController.loadConfig();
        urlCsvContent=new HashMap<String, List<String>>();
        redundantNodes=new HashMap<String, String>();
        redundantAttributes=new HashMap<String, String>();
        URLvsSupermap=new HashMap<String, List<Supermap>>();

        //initialize the map to set ignore nodes
       ConfigUtil config=new ConfigUtil();
        String nodesToIgnore=config.load("Exclusion").getProperty("ignorednodes");
        String attributesToIgnore=config.getProperty("ignoredattributes");
        for(String node:nodesToIgnore.split(","))
            redundantNodes.put(node,node);

        for(String attr:attributesToIgnore.split(","))
            redundantAttributes.put(attr,attr);

        try {
            readURLCSVFile();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }



//
//    public static void analyzeInnerUrls(Node engNode, Node locNode){
//
//        Stack<Node> engstack=new Stack<Node>();
//        engstack.push(engNode);
//        Stack<Node> locStack=new Stack<Node>();
//        locStack.push(locNode);
//
//        while(!engstack.empty() || !locStack.empty()){
//            Node n1=engstack.pop();
//            Node n2=locStack.pop();
//            for(Node n:n1.getChildren())engstack.push(n);
//            for(Node n:n2.getChildren())locStack.push(n);
//
//            if(n1.getName().equalsIgnoreCase("a") && n2.getName().equalsIgnoreCase("a"))
//            {
//                String hrefEng = "";String locHref="";
//                for(Attributes a:n1.getAttributes())
//                {
//                    if(a.getName().equalsIgnoreCase("href")) {
//                        hrefEng = a.getValue();
//                        break;
//                    }
//                }
//                for(Attributes a:n2.getAttributes())
//                {
//                    if(a.getName().equalsIgnoreCase("href")) {
//                        locHref = a.getValue();
//                        break;
//                    }
//                }
//                anchorsMap.put(hrefEng,locHref);
//
//            }
//
//
//            if(n1.getName().equalsIgnoreCase("img") && n2.getName().equalsIgnoreCase("img"))
//            {
//                String hrefEng = "";String locHref="";
//                for(Attributes a:n1.getAttributes())
//                {
//                    if(a.getName().equalsIgnoreCase("href")) {
//                        hrefEng = a.getValue();
//                        break;
//                    }
//                }
//                for(Attributes a:n2.getAttributes())
//                {
//                    if(a.getName().equalsIgnoreCase("href")) {
//                        locHref = a.getValue();
//                        break;
//                    }
//                }
//                imgMap.put(hrefEng,locHref);
//
//            }
//
//
//
//        }





}
