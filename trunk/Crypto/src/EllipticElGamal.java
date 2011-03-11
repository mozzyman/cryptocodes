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
		long k = (new Random()).nextLong(); 
		
		//compute c1, c2
		Point c[] = new Point[2];
		c[0] = ec.doubleAndAdd(P, k, fp);
		Point Ak = ec.doubleAndAdd(Qa, k, fp);
		c[1] = ec.addition(m, Ak, fp);
		
		return c;
	}
}
public class EllipticElGamal {

	/**
	 * @param args
	 */
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int A = 324;
		int B = 1287;
		int fp = 3851;
		long aliceSecret = 2489;
		int test = 1000;
		
		//y2 = x3 +324x+1287
		EllipticCurve ec = new EllipticCurve(A, B);
		
		//get points in Fp
		List<Point> allPoints = ec.getPoints(fp);
		
		Point chosenP = new Point(920,303);
		Point message = allPoints.get(test); //choose the 1000th point as message
		
		AliceElliptic a = new AliceElliptic(aliceSecret, fp, ec, chosenP);
		BobElliptic b = new BobElliptic(message, fp, ec, chosenP);
		
		Point sharedSecret = a.createKey();
		Point c[] = b.encrypt(sharedSecret);
		Point decreptedM = a.decrypt(c);
		
		System.out.println("Original message->"+message.print());
		System.out.println("Shared secret->"+sharedSecret.print());
		System.out.println("Decrepted message->"+decreptedM.print());
		
	}

}
