package Librarian;

import java.util.Scanner;
import java.io.*;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Book {

    public static String quoteSearch(String quote) throws Exception {
        org.jsoup.nodes.Document doc = org.jsoup.Jsoup.connect("https://www.goodreads.com/quotes/search?utf8=%E2%9C%93&q=" + quote + "&commit=Search").get();
        org.jsoup.select.Elements els = doc.getElementsByClass("authorOrTitle");
        StringBuilder elList = new StringBuilder();
        int index = 0;
        for (org.jsoup.nodes.Element el : els) {
            if(!elList.toString().contains(el.text())) {
                elList.append(el.text());
                if (index++ % 2 == 0) {
                    elList.append(" ");
                } else {
                    elList.append("\n");
                }
            }
        }
        return elList.toString();
    }
}
