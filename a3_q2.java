import java.io.*;
import java.util.*;

public class a3_q2 {
   public static class N_simpleHT {
      public Hashtable<String, String> ht;
      public Integer m_count;

      public N_simpleHT() {
	   ht = new Hashtable<String, String>();
	   m_count = 0;
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
	   return val % nn;  
      }

      public void update(String key, String value) { 
	   int p = place(key);
	   ha[p].lookup(key, value);
	   if (p == 0) {                   // Implement redundancy
		   ha[7].lookup(key, value);
	   } else {
		   ha[p-1].lookup(key, value);
	   }
	   if (p == 7){
		   ha[0].lookup(key, value);
	   } else {
		   ha[p+1].lookup(key, value);
	   }
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
      int nn = 8;
      DHT dht = new DHT(nn);

      Scanner scanner = new Scanner(System.in);
      String fqdn, src, val;
      ArrayList<String>keys = new ArrayList<String>(1000);
      int nl = 0, n_missing = 0;
      try {  // Build the DHT
         for (nl = 0; ; nl += 1) {
            fqdn = scanner.next();  src = scanner.next();
            dht.update(fqdn, src);  // fqdn is DHT key
	        keys.add(fqdn);
         }
      } catch (NoSuchElementException e) {
	  System.out.printf("%d lines\n", nl);    
      } finally {
         scanner.close();
      }
      dht.statistics();

      dht.loose_ht(6);
      dht.loose_ht(7);
      for (int n = 0; n != nl; n += 1) {
	 System.out.printf("  %d", n);
	 fqdn = keys.get(n);
         val = dht.find(fqdn);  // fqdn is DHT key
         if (val == null) {
	    System.out.printf("n=%d, fqdn=%s, val=%s\n",
			      n, fqdn, val);
	    n_missing += 1;
	 }
      }
      System.out.printf("\n%d entries now missing\n", n_missing);
   }
}
