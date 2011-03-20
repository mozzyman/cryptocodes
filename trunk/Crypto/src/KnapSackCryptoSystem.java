
public class KnapSackCryptoSystem {

	public static void main(String[] args)
	{
		long A = 4392;
		long B = 8387;
		long M[] = {5186, 2779, 5955, 2307, 6599, 6771, 6296, 7306, 4115, 7039};
		long S = 26560;
		long aInv = crypto.inverse(B, A);
		System.out.println("A inverse->"+aInv);
		
		long snew = (S*aInv)%B;
		System.out.println("new S >"+snew);
		
		
		long[] r = findR (M, aInv, B);
		System.out.println("R is->");
		for(long ar:r)
			System.out.print(ar+",");
		System.out.println();
		
		long[] x = solveSubsetSum(r,snew);
		System.out.println("Decrypted message is->");
		
		for(long ar:x)
			System.out.print(ar+",");
		System.out.println();
		
		
		System.out.println("Verify: Encrypted message "+S);
		long s = 0;
		for(int i=0; i< x.length; i++)
		{
			if(x[i]==1) s+=M[i];
				
		}
		if(s==S)
			System.out.println("Encryption is correct");
		
		
	}

	private static long[] solveSubsetSum(long[] r, long snew) {
		long[] x = new long[r.length];
		
		int i = r.length-1;
		while(i>=0)
		{
			if(snew>=r[i])
			{
				x[i] = 1;
				snew-=r[i];
				System.out.println("x["+i+"]=1,s="+snew);
			}
			else{
				x[i] = 0;
				System.out.println("x["+i+"]=0");
			}
			i--;
		}
		return x;
	}

	private static long[] findR(long[] m, long a, long b) {
		
		long[] r = new long[m.length];
		for(int i=0; i<m.length;i++)
		{
			r[i] = (m[i]*a)%b;
		}
		return r;
	}
    
	
}
