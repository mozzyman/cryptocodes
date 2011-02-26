
public class RSA {

	//find x from x^e = c (mod p)
	 public static long findRoot(long e, long c, long p)
	 {
		 long x = 0;
		 
		 //find e^-1 (mod p-1)
		 long eInverse = crypto.inverse(p-1, e);
		 
		 //x = c^d (mod p)
		 x = crypto.fastSq(p, c, eInverse);
		 
		 return x;
	 }
	 public static void main(String[] args) {
			
		    long e = 1583;
		    long c = 4714;
		    long p = 7919;
		    
			long x = RSA.findRoot(e,c, p);
			System.out.println("Solution to x^"+e+" = "+c+" (mod "+p+"): x= "+x);
	 }

}
