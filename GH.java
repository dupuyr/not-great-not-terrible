import java.io.*;
import java.net.*;
public class GH {

public static void main(String[] args){


}

 public URL getUrl () throws IOException{
   URL url = new URL("https://api.github.com/repos/d3");
   return url;
 }

 public void getRequest() throws Exception{
   HttpURLConnection c = (HttpURLConnection) getUrl().openConnection();
   c.setRequestMethod("GET");
   BufferedReader in = new BufferedReader (new InputStreamReader(con.getInputStream()));
   String inputLine;
   StringBuffer response = new StringBuffer();
 }
}
