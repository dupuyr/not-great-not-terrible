import java.io.*;
import java.net.*;
import java.util.*;
import org.json.simple.*;
import org.json.simple.parser.*;
import java.time.*;
import java.text.SimpleDateFormat;
import com.opencsv.*;

public class GH {

 // class for creating contributor object
 public static class Cons {
  // variables for repo contributed to, con name, weeks they've contributed, monthly contribution.
  Repos contributedTo;
  String name;
  JSONArray weeks;
  List<Month> months;
  long lines;


  //creating Cons object containing repo, their name, weeks active, activity for each month
  public Cons(Object o, Repos r) {
   JSONObject b;
   b = (JSONObject) o;
   this.contributedTo = r;
   this.name = (String) b.get("login");
   this.weeks = (JSONArray) b.get("weeks");
   months = new ArrayList<>();


  }

  public Cons() {
   this.lines = 0;
  }

  //converting unix to readable date for each month
  public static Month convertMonth(Month m) {

   Instant i = Instant.ofEpochSecond(m.month * 1000);
   Date d = Date.from(i);
   SimpleDateFormat formatter = new SimpleDateFormat("MM/yyyy");
   String dt = formatter.format(d);
   m.date = dt;
   return m;
  }
 }

 //class for repositories
 public static class Repos {

  static String rep;
  static JSONArray w;
  static List<Cons> cons;
  static List<Month> topMonth;


  public Repos(String repo, JSONArray a) {

   this.rep = repo;
   this.w = a;
   cons = new ArrayList<>();
   topMonth = new ArrayList<>();


  }


 }

 public static class Month {

  static long codeLines;
  static long month;
  static String date;
  static Cons t;
  static Cons s;
  static Cons th;


  public Month(long code, long month) {

   this.codeLines = code;
   this.month = month;

  }

  public Month(String date, Cons top, Cons sec, Cons thd) {
   this.date = date;
   this.t = top;
   this.s = sec;
   this.th = thd;

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
  calculateLines();
  getExactMonth();
  createCSVFiles();
  writeToCSV();

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

 public static void calculateLines() {
  long code = 0;
  long m = 0;
  for (Cons c : contributors) {
   for (Object o : c.weeks) {
    JSONObject j;
    j = (JSONObject) o;
    if (m < 2419200) {
     code += (long) j.get("a");
     m += 604800;
    } else if (m == 2419200) {
     Month mth = new Month(code, (long) j.get("w"));
     c.months.add(c.convertMonth(mth));
     //System.out.println( c.months.get(0)+ " " + code);
     code = 0;
     m = 0;
    }
   }


  }
  assignToRepo();
 }

 public static void assignToRepo() {

  for (Cons c : contributors) {
   Repos r = c.contributedTo;
   r.cons.add(c);
  }

 }

 public static void getExactMonth() throws IOException {

  for (Repos r : repoStats) {
   for (Month m : r.cons.get(0).months) {
    getTopCons(m.date, r);
   }
  }

 }

 public static void getTopCons(String m, Repos r) {

  long top = 0;
  long second = 0;
  long third = 0;
  Cons c1 = new Cons();
  Cons c2 = new Cons();
  Cons c3 = new Cons();

  Month mo = new Month(m, c1, c2, c3);
  r.topMonth.add(mo);
  for (Cons c : r.cons) {
   for (Month mt : c.months) {
    if (mt.date.equals(m)) {
     if (mt.codeLines > top) {
      top = mt.codeLines;
      c1.lines = mt.codeLines;
      c1.name = c.name;

     } else if (mt.codeLines < top && mt.codeLines > second) {
      second = mt.codeLines;
      c2.lines = mt.codeLines;
      c2.name = c.name;

     } else if (mt.codeLines < top && mt.codeLines < second && mt.codeLines > third) {
      third = mt.codeLines;
      c3.lines = mt.codeLines;
      c3.name = c.name;


     }
    }

   }

  }


 }

 public static void createCSVFiles(){

  for(String r: repos){
    File f = new File("C:\\Users\\maxri\\OneDrive\\Desktop\\CSV\\" + r + ".csv");
  }

 }

 public static void writeToCSV(){
   for(Repos r : repoStats){
    try {

     FileWriter output = new FileWriter("C:\\Users\\maxri\\OneDrive\\Desktop\\CSV" + r.rep + ".csv");
     CSVWriter w = new CSVWriter(output);

     String[] header = { "Month", "Name", "Lines" };
     w.writeNext(header);
     for(Month m : r.topMonth){
      String[] data = { m.date, m.t.name ,Long.toString(m.t.lines)};
      w.writeNext(data);
      String[] data2 = {m.date, m.s.name, Long.toString(m.s.lines)};
      w.writeNext(data2);
      String[] data3 = {m.date,m.th.name, Long.toString(m.th.lines)};
      w.writeNext(data3);
     }

     w.close();
    }
    catch (IOException e) {
     e.printStackTrace();
    }
   }
 }
}

















