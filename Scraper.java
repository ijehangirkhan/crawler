import java.io.*;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class Scraper {
    private static void crawl(String url) {
        try {
            Document doc = Jsoup.connect(url).get();
            Elements rows = doc.select("table#table-announcements tbody tr");
            PrintWriter pw = new PrintWriter(new File("NewData.csv"));
            StringBuilder builder = new StringBuilder();
            String columnNamesList = "No,Date,Company Name,Announcement";
            builder.append(columnNamesList +"\n");

            for (Element row : rows) {
                if (row.select("td").size() == 4) {
                    String no = row.select("td").get(0).text();
                    String date = row.select("td").get(1).text();
                    String compName = row.select("td").get(2).text();
                    String announcement = row.select("td").get(3).text();
                    System.out.println(no);
                    System.out.println(date);
                    System.out.println(compName);
                    System.out.println(announcement);
                    builder.append(no+",");
                    builder.append(date+",");
                    builder.append(compName+",");
                    builder.append(announcement);
                    builder.append('\n');
                    pw.write(builder.toString());
                }
            }
            pw.close();
        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
}

    public static void main(String[] args) throws IOException {
        String url = "https://www.bursamalaysia.com/market_information/announcements/company_announcement?keyword=&cat=SB%2CSBBA&sub_type=&company=&mkt=&alph=&sec=&subsec=&dt_ht=";
        crawl(url);
    }
}
