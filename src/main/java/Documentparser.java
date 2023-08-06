import Beans.Node;
import Utils.GeneralController;
import Utils.Locale;
import com.sun.xml.internal.bind.v2.runtime.output.NamespaceContextImpl;
import lombok.Data;
import lombok.extern.java.Log;
import org.jsoup.Jsoup;

import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Tag;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


@Data
@Log
public class Documentparser {

    private Locale loc;

    public Documentparser(Locale locale){
        this.loc=locale;

    }

    public Node parse(Document doc) {
        Node node;
        if(doc==null){
            return null;
        }
        else {
            node=new Node();
            log.info("Parsing the Dom tree...");
//            anchors=loadLinks(doc);
//            images=loadImages(doc);
//            GeneralController.anchorsMap.put(loc,anchors);
//            GeneralController.imgMap.put(loc,images);
            return elementToNode(doc.body());
        }
    }

     List<Element> loadLinks(Document doc){
        List<Element> anchorlist=new ArrayList<Element>();
        Elements anchorElems=doc.getElementsByTag("a");
        Iterator it=anchorElems.iterator();
        while(it.hasNext()) {
            Element ael= (Element) it.next();
            String href=ael.attributes().get("href");
            if(href!=null && !href.equals("") && !href.equals("#"))
                anchorlist.add(ael);
        }
        return anchorlist;
    }


     List<Element> loadImages(Document doc){
        List<Element> imglist=new ArrayList<Element>();
        Elements imgElems=doc.getElementsByTag("img");
        Iterator it=imgElems.iterator();
        while(it.hasNext())
            imglist.add((Element) it.next());

        return imglist;
    }



    private Node elementToNode(Element elem) {

        if (GeneralController.redundantNodes.containsKey(elem.nodeName().trim().toLowerCase())) {
            return null;
        }


        Node n = new Node();
        n.setName(elem.nodeName());
        n.setText(elem.ownText());
        if (elem.attributes().size() > 0) {
            Attributes allAttribs = new Attributes();
            Iterator attributesiterator = elem.attributes().iterator();
            while (attributesiterator.hasNext()) {
                Attribute att = (Attribute) attributesiterator.next();
                if (GeneralController.redundantAttributes.containsKey(att.getKey()))
                    continue;
                else allAttribs.put(att);
            }
            n.setAttributes(allAttribs);
        }

        int childrensize = elem.children().size();
        if (childrensize > 0) {
            int k=0;
            Element[] childrenElems=new Element[childrensize];
            Node[] children=new Node[childrensize];
            Iterator it=elem.children().iterator();
            while(it.hasNext())
                childrenElems[k++]=(Element) it.next();
            for(int i=0;i<childrensize;i++)
                    children[i]=elementToNode(childrenElems[i]);

            n.setChildren(children);
        }

        return n;
    }


}
