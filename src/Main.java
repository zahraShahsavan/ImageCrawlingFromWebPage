
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.net.URLConnection;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.cert.X509Certificate;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.security.Provider;
import java.util.Properties;
import java.util.Scanner;

import javax.imageio.ImageIO;



/*
 *
*<h1> crawling image from  url of a web page </h1>
<p>This program use jsoup for crawling image from a url and download them in a directory with a integer name </p>
* @author  Zahra Shahsavan
* @version 1.0
* @since   2020-01
	
	
* command in windows for add web site certificate : keytool -importcert -file C:\Users\Asus\Desktop\-digikalacom.crt -alias -digikalacom -keystore C:\Program Files\Java\jre-9.0.4\lib\security\cacerts

 */
public class Main {
   
	public static void main(String[] args) throws Exception {
		//get url from user
    	System.out.println("if you want to crawl  webpage enter url");
    	Scanner sc=new Scanner(System.in);
   
    	boolean bool=true;
    	while(bool) {
        String url = "";
        url=sc.nextLine();
        print("Fetching %s...", url);
       
		//prepare security for connect to url
        Connector c=new Connector();
    	c.connecter(url);

        //connect to url with jsoup
		int counter=0;
        Document doc = Jsoup.connect(url).get();
		
		//select some tags that may be contain img 
        Elements media = doc.select("[src]");
        Elements media2 = doc.select("[data-src]");
        Elements media3 = doc.select("[data-src-swiper]");
		
		//look for img attribute of src tag
        System.out.println("Images :");
        for (Element src : media) {
            URL urlimg = new URL(src.attr("abs:src"));
            BufferedImage img = ImageIO.read(urlimg);
            if (src.tagName().equals("img") && img!=null) {
                print(counter+" * %s: <%s> %sx%s (%s)",
                        src.tagName(), src.attr("abs:src"), src.attr("width"), src.attr("height"),
                        trim(src.attr("alt"), 20));
                int width = img.getWidth();
                int height = img.getHeight();
                BufferedImage output = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
                int px[] = new int[width * height];
                img.getRGB(0, 0, width, height, px, 0, width);
                output.setRGB(0, 0, width, height, px, 0, width);                
                File file = new File("D://img/"+String.valueOf(counter)+".jpg");
                counter++;
                ImageIO.write(output, "jpg",file);
            }

        }
        
		//look for img attribute of data-src tag
        for (Element src : media2) {
            
        	URL urlimg = new URL(src.attr("abs:data-src"));
            BufferedImage img = ImageIO.read(urlimg);
            
            if (src.tagName().equals("img")&& img!=null) {
            	
                print(counter+" * %s: <%s> %sx%s (%s)",
                        src.tagName(), src.attr("abs:data-src"), src.attr("width"), src.attr("height"),
                        trim(src.attr("alt"), 20));
                int width = img.getWidth();
                int height = img.getHeight();
                BufferedImage output = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
                int px[] = new int[width * height];
                img.getRGB(0, 0, width, height, px, 0, width);
                output.setRGB(0, 0, width, height, px, 0, width);                
                File file = new File("D://img/"+String.valueOf(counter)+".jpg");
                counter++;
                ImageIO.write(output, "jpg",file);
            }

        }
        
		//look for img attribute of data-src-swiper tag
        for (Element src : media3) {
            
        	URL urlimg = new URL(src.attr("data-src-swiper"));
            BufferedImage img = ImageIO.read(urlimg);
            
            if (src.tagName().equals("img")&& img!=null) {
            	
                print(counter+" * %s: <%s> %sx%s (%s)",
                        src.tagName(), src.attr("data-src-swiper"), src.attr("width"), src.attr("height"),
                        trim(src.attr("alt"), 20));
                int width = img.getWidth();
                int height = img.getHeight();
                BufferedImage output = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
                int px[] = new int[width * height];
                img.getRGB(0, 0, width, height, px, 0, width);
                output.setRGB(0, 0, width, height, px, 0, width);                
                File file = new File("D://img/"+String.valueOf(counter)+".jpg");
                counter++;
                ImageIO.write(output, "jpg",file);
            }

        }
        System.out.println(counter+" Image  crewled");
        System.out.println("if you want to crawl another webpage enter y");
        String c1=sc.nextLine();
        if(!c1.equals("y"))
        	bool=false;
        
    	}

    }
    
    
       
    
    private static void print(String msg, Object... args) {
        System.out.println(String.format(msg, args));
    }

    private static String trim(String s, int width) {
        if (s.length() > width)
            return s.substring(0, width-1) + ".";
        else
            return s;
    }
}