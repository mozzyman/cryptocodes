import java.util.List;
import java.util.Random;

/**
 * 
 */

/**
 * @author sheetal
 *
 */
class AliceElliptic{
	
	long nA; //Alice's secret
	EllipticCurve ec; //shared curve
	long fp; //shared prime
	Point P; //shared point
	
	public AliceElliptic(long nA, long fp, EllipticCurve ec, Point P)
	{
		this.nA = nA;
		this.fp = fp;
		this.ec = ec;
		this.P = P;
	}
	//returns Alice's public key
	public Point createKey()
	{
		//compute public key, Qa = nA*P mod fp
		Point Qa = ec.doubleAndAdd(P, nA, fp);
		return Qa;
	}
	
	public Point decrypt(Point[] c)
	{
		//m = c2-na*c1
		
		//compute nc = nA*c1
		Point nc = ec.doubleAndAdd(c[0], nA, fp);
		
		Point nc_minus = new Point(nc.x, -nc.y);
		
		Point message = ec.addition(c[1], nc_minus, fp);
		
		return message;
		
		
	}
	//Menezes–Vanstone variant of ElGamal (Exercises 5.16, 5.17)
	public String decryptMV(Point R, long c1, long c2)
	{
		//m = c2-na*c1
		
		//compute nc = nA*c1
		Point T = ec.doubleAndAdd(R, nA, fp);
		
		long xtInverse = crypto.inverse(fp, (long)T.x);
		long ytInverse = crypto.inverse(fp, (long)T.y);
		
		long m1 = (xtInverse*c1)%fp;
		long m2 = (ytInverse*c2)%fp;
		
		String message= m1+","+m2;
		
		return message;
	}
	
}
class BobElliptic{
	
	Point m; //Bob's message
	EllipticCurve ec; //shared curve
	long fp; //shared prime
	Point P; //shared point
	
	
	public BobElliptic(Point m, long fp, EllipticCurve ec, Point P)
	{
		this.m = m;
		this.fp = fp;
		this.ec = ec;
		this.P = P;
	}
	
	
	public Point[] encrypt(Point Qa)
	{
		//choose k, ephemeral key
		long k = (new Random()).nextInt(); 
		
		//compute c1, c2
		Point c[] = new Point[2];
		c[0] = ec.doubleAndAdd(P, k, fp);
		Point Ak = ec.doubleAndAdd(Qa, k, fp);
		c[1] = ec.addition(m, Ak, fp);
		
		return c;
	}
	//Menezes–Vanstone variant of ElGamal (Exercises 5.16, 5.17)
	public String encryptMV(Point Qa, long m1, long m2)
	{
		//choose k, ephemeral key
		long k = (new Random()).nextInt(); 
		
		//compute R
		Point R = ec.doubleAndAdd(P, k, fp);
		
		//compute S
		Point S = ec.doubleAndAdd(Qa, k, fp);
		
		//compute c1, c2
		long c[] = new long[2];
		c[0] = (long)(S.x*m1)%fp;
		c[1] = (long)(S.y*m2)%fp;
		
		String valueReturn = R.x+","+R.y+","+c[0]+","+c[1];
		
		return valueReturn;
	}
}
public class EllipticElGamal {

	/**
	 * @param args
	 */
	
	public static void main(String[] args) {
		
		int A = 19;
		int B = 17;
		int fp = 1201;
		long aliceSecret = 595;
		int test = 500;
		int sharedPointX = 278;
		int sharedPointY = 285;
		
		//y2 = x3 +324x+1287
		EllipticCurve ec = new EllipticCurve(A, B);
		
		//get points in Fp
		List<Point> allPoints = ec.getPoints(fp);
		
		Point chosenP = new Point(sharedPointX,sharedPointY);
		Point message = allPoints.get(test); //choose the 1000th point as message
		
		AliceElliptic a = new AliceElliptic(aliceSecret, fp, ec, chosenP);
		BobElliptic b = new BobElliptic(message, fp, ec, chosenP);
		
		Point sharedSecret = a.createKey();
		Point c[] = b.encrypt(sharedSecret);
		Point decryptedM = a.decrypt(c);
		
		System.out.println("Original message->"+message.print());
		System.out.println("Shared secret->"+sharedSecret.print());
		System.out.println("Decrepted message->"+decryptedM.print());
		//AliceElliptic
		
		//question 5.16
		Point R = new Point(1147,640);
		long c1= 279;
		long c2 = 1189;
		String decryptedMV = a.decryptMV(R, c1, c2);
		System.out.println("Decrypted with MV->"+decryptedMV);
		
		
	}

}
