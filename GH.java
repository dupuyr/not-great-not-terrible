import java.io.*;
import java.net.*;
import java.util.*;

public class GH {

 public static void main(String[] args) throws IOException{
  //getRequest();
  getContributors();

 }

/* public static void getRequest() throws IOException{

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
  }*/

   public static void getContributors() throws IOException{

       URL u2 = new URL("https://api.github.com/users/d3/repos/d3/stats/contributors");
       HttpURLConnection c2 = (HttpURLConnection) u2.openConnection();
       c2.setRequestMethod("GET");
       BufferedReader in2 = new BufferedReader(new InputStreamReader(c2.getInputStream()));
       BufferedWriter f2 = new BufferedWriter(new FileWriter(new File("d3cons.txt")));
       String inputLine;
       StringBuffer response = new StringBuffer();
        while ((inputLine = in2.readLine()) != null) {
                response.append(inputLine);
            }
            String s = response.toString();
            in2.close();
            f2.write(s);
            f2.close();


  }

 }
