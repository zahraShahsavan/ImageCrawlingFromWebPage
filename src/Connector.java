import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.net.URLConnection;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class Connector {

	 public static  void connecter(String urll) throws Exception {
         /* Start of Fix */
         TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
             public java.security.cert.X509Certificate[] getAcceptedIssuers() { return null; }
             public void checkClientTrusted(X509Certificate[] certs, String authType) { }
             public void checkServerTrusted(X509Certificate[] certs, String authType) { }

         } };

         SSLContext sc = SSLContext.getInstance("SSL");
         sc.init(null, trustAllCerts, new java.security.SecureRandom());
         HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

         // Create all-trusting host name verifier
         HostnameVerifier allHostsValid = new HostnameVerifier() {
             public boolean verify(String hostname, SSLSession session) { return true; }
         };
         // Install the all-trusting host verifier
         HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
         /* End of the fix*/

         URL url = new URL(urll);
         URLConnection con = url.openConnection();
         Reader reader = new InputStreamReader(con.getInputStream());

     }
}
