import java.io.*;
import java.net.*;
import java.util.*;
import org.json.simple.*;
import org.json.simple.parser.*;

public class GH {

 public static class Cons {

  Repos contributedTo;
  String name;
  JSONArray weeks;

  public Cons(Object o, Repos r) {
   JSONObject b;
   b = (JSONObject) o;
   this.contributedTo = r;
   this.name = (String) b.get("login");
   this.weeks = (JSONArray) b.get("weeks");
  }
 }

 public static class Repos {

  static String rep;
  static JSONArray w;

  public Repos(String repo, JSONArray a) {

   this.rep = repo;
   this.w = a;
  }

  }


  static List<String> repos = new ArrayList<>();
  static List<JSONArray> stats = new ArrayList<>();
  static List<Repos> repoStats = new ArrayList<>();
  static List<Cons> contributors = new ArrayList<>();

  public static void main(String[] args) throws IOException, ParseException {
   getRepos();
   getStats();
   getRepoStats();
   getCons();


  }

  public static void getRepos() throws IOException, ParseException {

   URL url = new URL("https://api.github.com/users/d3/repos");
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
   JSONParser p = new JSONParser();
   JSONArray j = (JSONArray) p.parse(b.toString());
   for (Object value : j) {
    o = (JSONObject) value;
    String s = (String) o.get("name");
    repos.add(s);
   }


  }

  public static void getStats() throws IOException, ParseException {

   for (String repo : repos) {
    parseArrays(repo);
   }

  }


  public static void parseArrays(String repo) throws IOException, ParseException {

   URL u;
   HttpURLConnection h;
   StringBuilder s = new StringBuilder();
   BufferedReader r;
   String l;
   u = new URL("https://api.github.com/repos/d3/" + repo + "/stats/contributors");
   h = (HttpURLConnection) u.openConnection();
   h.setRequestMethod("GET");
   r = new BufferedReader(new InputStreamReader(h.getInputStream()));
   while ((l = r.readLine()) != null) {
    s.append(l);
   }
   r.close();
   h.disconnect();
   JSONParser parser = new JSONParser();
   JSONArray a = (JSONArray) parser.parse(s.toString());
   stats.add(a);


  }

  public static void getRepoStats() {

   for (String re : repos) {
    for (JSONArray a : stats) {
     Repos r = new Repos(re, a);
     repoStats.add(r);

    }
   }

  }

 public static void getCons() {

  for (Repos r : repoStats) {
   for (Object o : r.w) {
    Cons c = new Cons(o, r);
    contributors.add(c);
   }
  }

 }
}







