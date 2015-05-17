import java.io.*;
import java.util.*;

public class a3_q1 {
    public static class N_simpleHT {  // Use this as supplied
      public Hashtable<String, String> ht;
      public String m_key;  public Integer m_count;

      public N_simpleHT() {
	 ht = new Hashtable<String, String>();
	 m_count = 0;  m_key = null;
      }

      public void lookup(String key, String value) {
	 String v = ht.get(key);
	 if (v == null) {   // Not there yet
	    ht.put(key, value);
	 } else {
	    ht.put(key, value);  // Set its new value
	 }
      }

      public Integer size() {
	 return ht.size();
      }

      public void dump() {
	 Enumeration e = ht.keys();
	 while (e.hasMoreElements() ) {
	    String key = e.nextElement().toString();
	    System.out.printf("%s: %s\n", ht.get(key), key);
	 }  
      }
   }


   public static class DHT {
      public int nn = 0;
      public N_simpleHT ha[];

      public DHT(int pnn) {
	nn = pnn;
	ha = new N_simpleHT [nn];
	for (int j = 0; j != pnn; j += 1)  // Create the hash tables
	  ha[j] = new N_simpleHT();
	}

      public void loose_ht(int w) {
	ha[w] = null;
	}

      public int place(String key) {
       char[] ca = key.toCharArray();
       int val = 0, klen = key.length();
       for (int j = 0; j != klen; j += 1) val += (int)ca[j];
       return val % nn;  // Index of table to use
       }

       public void update(String key, String value) {
       int p = place(key);
       ha[p].lookup(key, value);
       }

       public String find(String key) {
       int p = place(key);
       return "p";  
       }

       public void statistics() {  
    	   int max = ha[0].size(), min = ha[0].size(), count = 0;
    	   
    	   System.out.printf("Entries in the SimpleHTs:\n");
    	   for (int i = 0; i <= ha.length - 1; i++) {
    	   if (max < ha[i].size())
    	   max = ha[i].size();
    	   if (min > ha[i].size())
    	   min = ha[i].size();
    	   count += ha[i].size();
    	   System.out.printf("ht  %d =%6d entries\n", i, ha[i].size());
    	   }
    	   System.out.printf("Total of %d entries\n", count);
    	   System.out.printf("max ht size = %d, min ht size = %d\n", max, min);
    	   System.out.printf("difference between max and min = %d\n", max-min);
       }
   }


    public static void main(String[] args) throws IOException {
      Integer nn = 8;
      DHT dht = new DHT(nn);

      Scanner scanner = new Scanner(System.in);
      String fqdn, src;
      int nl = 0;
 
      try {  // Build the DHT
         for (nl = 0; ; nl += 1) {
            fqdn = scanner.next();  src = scanner.next();
            dht.update(fqdn, src); 
         }
      } catch (NoSuchElementException e) {
	  System.out.printf("%d lines\n", nl);    
      } finally {
         scanner.close();
      }
      dht.statistics();
   }
