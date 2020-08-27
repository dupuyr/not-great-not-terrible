import java.io.*;
import java.net.*;
import java.util.*;
import org.json.simple.*;
import org.json.simple.parser.*;

public class GH {

 static List <String> repos = new ArrayList <> ();

 public static void main(String[] args) throws IOException, ParseException{
  getRepos();
  System.out.println(repos.get(1));


 }

 public static void getRepos() throws IOException, ParseException {

  URL url = new URL( "https://api.github.com/users/d3/repos");
  HttpURLConnection c = (HttpURLConnection) url.openConnection();
  c.setRequestMethod("GET");

  StringBuilder b = new StringBuilder();

  String line;
  BufferedReader in = new BufferedReader(new InputStreamReader(c.getInputStream()));
   while ((line = in.readLine()) != null)
     b.append(line);
   in.close();

  c.disconnect();
   JSONObject o;
   JSONParser p  = new JSONParser();
   JSONArray j = (JSONArray) p.parse(b.toString());
  for (Object value : j) {
    o = (JSONObject) value;
    String s = (String) o.get("name");
    repos.add(s);
  }


 }
 }
