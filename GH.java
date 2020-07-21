import java.io.*;
import java.net.*;
import java.util.*;
public class GH {

public static void main(String[] args) throws IOException{
getRequest();

}

 public static URL getUrl () throws IOException{
   URL url = new URL("https://api.github.com/users/d3/repos");
   return url;
 }

 public static void getRequest() throws IOException{
   HttpURLConnection c = (HttpURLConnection) getUrl().openConnection();
   c.setRequestMethod("GET");
   toFile(c);
 }

 public static void toFile(HttpURLConnection h) throws IOException{
   BufferedReader in = new BufferedReader(new InputStreamReader(h.getInputStream()));
   BufferedWriter f = new BufferedWriter(new FileWriter(new File("test.txt")));
   String inputLine;
   StringBuffer response = new StringBuffer();
    while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        String s = response.toString();
        in.close();
        f.write(s);
        f.close();
 }
}
