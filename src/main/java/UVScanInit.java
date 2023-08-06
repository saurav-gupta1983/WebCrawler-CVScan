import Beans.Node;
import Beans.Supermap;
import Utils.ConfigController;
import Utils.ExcelUtility;
import Utils.GeneralController;
import Utils.Locale;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import java.io.IOException;
import java.util.*;

@Slf4j
public class UVScanInit {

     @SneakyThrows
     void init() {
        Map<String, List<String>> urldetails= GeneralController.urlCsvContent;
        String engurlkey= ConfigController.configurations.get("englishurlheader");
        String localekey=ConfigController.configurations.get("localeheader");
        String localurlkey=ConfigController.configurations.get("localizeurlheader");
        int len=urldetails.get(engurlkey).size();
        for(int i=0;i<len;i++)
            loadAndParse(urldetails.get(engurlkey),urldetails.get(localekey),urldetails.get(localurlkey));
    }


    void writeResults(){
        Map<String,List<Supermap>> results=GeneralController.URLvsSupermap;
        ExcelUtility excel=new ExcelUtility("Results.xlsx").loadExcel().createSheet("anchors").createSheet("images");
        TreeMap<String,List<String>> localAnchorresults=new TreeMap<>();
        TreeMap<String,List<String>> localImageresults=new TreeMap<>();
        for(String s:results.keySet()){
            List<Supermap> supermaplist=results.get(s);
            for(Supermap localeResult:supermaplist){
                List<String> anchors=new ArrayList<>();
                for(Element ele:localeResult.getAnchors())
                {
                    anchors.add(ele.attributes().get("href"));
                }
                List<String> images=new ArrayList<>();
                for(Element ele:localeResult.getImages())
                {
                    images.add(ele.attributes().get("href"));
                }
                localImageresults.put(localeResult.getLocale().name(),images);
                localAnchorresults.put(localeResult.getLocale().name(),anchors);

            }

        }

        excel.writeToSheet("anchors",localAnchorresults);
        excel.writeToSheet("images",localImageresults);
        excel.closeSheet();

    }

     private void loadAndParse(List<String> engurls,List<String> locale,List<String> localurl) throws IOException {
        int i=0;
         Locale eng=Locale.en_US;
         Map<String, List<Supermap>> urlvslocalSuperMap=new HashMap<>();

        while(i<engurls.size())
        {
            Locale local= Locale.valueOf(locale.get(i));
            String localURl=localurl.get(i);
            Supermap locMap=getSuperMap(local,localURl);
            String englishURl=engurls.get(i);
            if(urlvslocalSuperMap.containsKey(engurls))
            {
                urlvslocalSuperMap.get(englishURl).add(locMap);
            }
            else{
                Supermap engmap=getSuperMap(eng,englishURl);
                List<Supermap> resultList=new ArrayList<>();
                resultList.add(engmap);
                resultList.add(locMap);
                urlvslocalSuperMap.put(englishURl,resultList);
            }
        i++;
        }

        GeneralController.URLvsSupermap=urlvslocalSuperMap;
    }

    private Supermap getSuperMap(Locale loc,String url) throws IOException {
        Documentparser parser=new Documentparser(loc);
      //  Document doc=BrowserUtils.render(url);
        Document doc=Jsoup.connect(url)
                .header("Accept-Encoding", "gzip, deflate")
                .userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64; rv:23.0) Gecko/20100101 Firefox/23.0")
                .maxBodySize(0)
                .timeout(600000).followRedirects(true).get();
        Node node=parser.parse(doc);
        List<Element> anchors=parser.loadLinks(doc);
        List<Element> images=parser.loadImages(doc);
        Supermap map=new Supermap(loc,url,node,anchors,images);
        return map;
     }
}
