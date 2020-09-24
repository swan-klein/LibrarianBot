package Librarian;

import java.util.Scanner;
import java.io.*;

public class Author {

    public static String booksBy(String author, String path) throws Exception {
        String books[] = new String[500];
        BufferedReader br = new BufferedReader(new FileReader(new File(path.substring(0, path.length()-1) + "GUTINDEX.ALL")));
        String str;
        String info = "";
        String bookList = "";
        int i = 0;
        while ((str = br.readLine()) != null) {
            if(str.length() > 77) {
                info = "";
            }
            if(str.length() > 0) {
                info = info + str + '\n';
            }
            else if(info.contains(", by " + author)) {
                String book = info.substring(0,info.indexOf(", by " + author)) + '\n';
                if(info.contains("[Subtitle: "))
                    book = book + info.substring(info.indexOf("[Subtitle: "), info.indexOf("[Subtitle: ") + info.substring(info.indexOf("[Subtitle: ")).indexOf("\n") + 1);
                if(info.contains("[Language: "))
                    book = book + info.substring(info.indexOf("[Language: "), info.indexOf("[Language: ") + info.substring(info.indexOf("[Language: ")).indexOf("\n") + 1);
                info = "";
                books[i++]=book;
            }
            else {
                info = "";
            }
        }
        i=0;
        if(books[0] == null)
            return("No books by the author " + author);
        else while(books[i] != null) {
            bookList = bookList + books[i++];
        }
        return bookList;
    }
}