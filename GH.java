import java.io.*;
import java.net.*;
import java.util.*;

public class GH {

 public static void main(String[] args) throws IOException{
  getRequest();

 }

 public static void getRequest() throws IOException{

   URL u = new URL("https://api.github.com/users/d3/repos");
   HttpURLConnection c = (HttpURLConnection) u.openConnection();
   c.setRequestMethod("GET");
   BufferedReader in = new BufferedReader(new InputStreamReader(c.getInputStream()));
   BufferedWriter f = new BufferedWriter(new FileWriter(new File("d3.txt")));
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
