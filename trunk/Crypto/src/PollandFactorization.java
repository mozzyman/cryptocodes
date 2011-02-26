import java.math.BigInteger;


public class PollandFactorization {

	public  long getfactors(long N, long bound)
	{
		long a = 2;
		long d;
		int j = 2;
		
		for(; j<bound; j++)
		{
			//set a = a^j mod N
			a = crypto.fastSq(N, a, j);
			d = crypto.gcd(a-1, N);
			if(d>1 && d<N){
				System.out.println("2^"+j+" -1 = "+a+" (mod "+N+")");
				System.out.println("gcd(2^"+j+"-1,"+N+") = "+d);
				
				return d;
			}
			
		}
		return -1;
	}
	public static void main(String[] args)
	{
		PollandFactorization pf = new PollandFactorization();
		
		String[] allNumbers = {"1739","220459","48356747", "168441398857"}; 
		for(String aNumber:allNumbers)
		{
			System.out.println("Find prime factors of "+aNumber+ ":");

			BigInteger b = new BigInteger(aNumber);
			long N = b.longValue();
			long p = pf.getfactors(N, N/2); //N/2 is an assumed bound
			long q = N/p;
			System.out.println("factors of "+aNumber+",p="+p+", and q="+N+"/"+p+"="+q);
			SquareRoot.factor(p-1);
			SquareRoot.factor(q-1);
			System.out.println("-------------------------------------");
		}
		
		
	}
}
