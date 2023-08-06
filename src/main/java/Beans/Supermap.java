package Beans;

import Utils.Locale;
import lombok.Data;
import org.jsoup.nodes.Element;

import java.util.List;

@Data
public class Supermap {

    String url;
    Locale locale;
    List<Element> anchors;
    List<Element> images;
    Node dom;

    public Supermap(Locale loc,String url,Node node,List<Element> anchors,List<Element> images){
        this.locale=loc;
        this.url=url;
        this.anchors=anchors;
        this.images=images;
        this.dom=node;
    }

}
