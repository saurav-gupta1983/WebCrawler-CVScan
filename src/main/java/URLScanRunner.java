
import Beans.Node;
import Utils.ConfigController;
import Utils.GeneralController;

import Utils.Locale;
import lombok.Data;
import lombok.extern.java.Log;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.net.URL;
import java.rmi.server.ExportException;
import java.util.*;

@Data
@Log
public class URLScanRunner {
    public static void main(String a[]) throws IOException {
         new GeneralController();
         UVScanInit uvscan= new UVScanInit();
         uvscan.init();
         uvscan.writeResults();
    }




}
