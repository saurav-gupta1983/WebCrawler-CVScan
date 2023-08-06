package Beans;

import Utils.ArrayUtils;
import Utils.GeneralController;
import lombok.Data;
import org.jsoup.nodes.Attributes;

import java.util.Collections;
import java.util.Comparator;

@Data
public class Node {

    private String name;
    public Node[] children;
    private Attributes attributes;
    private String text;

public Node()
{
    this.name="";
    this.children=null;
    this.attributes=null;
    this.text="";
}
    public Node(String name,String text)
    {
        this.name=name;
        this.text=text;
    }


//
//    public static class NodeNameComparator implements Comparator<Node> {
//
//        public int compare(Node o1, Node o2) {
//            int nameCompare = o1.name.compareTo(o2.name)==0 ? 0:1;
//            return nameCompare;
//        }
//
//    }
//
//    public static class NodeAttributesComparator implements Comparator<Node> {
//
//        public int compare(Node o1, Node o2) {
//            int attributeCompare = ArrayUtils.arrayEquals(Attributes.Sorter.sortAttributes(o1.attributes), Attributes.Sorter.sortAttributes(o2.attributes) ) ? 0 : 1;
//            return attributeCompare;
//        }
//
//    }



}
