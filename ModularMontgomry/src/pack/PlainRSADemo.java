package pack;

import java.math.BigInteger;
import java.util.Random;
import java.util.Vector;

/**
 * Disclaimer:
 * This code is for illustration purposes and shall never be used in any implementation in practice.
 */

public class PlainRSADemo {
	
	
	static Vector<Long>M1=new Vector<Long>();
	static Vector<Long>M2=new Vector<Long>();
	static Vector<Long>M3=new Vector<Long>();
	static Vector<Long>M4=new Vector<Long>();
    static boolean flag=false;
	static BigInteger N = new BigInteger(
			"a12360b5a6d58b1a7468ce7a7158f7a2562611bd163ae754996bc6a2421aa17d3cf6d4d46a06a9d437525571a2bfe9395d440d7b09e9912a2a1f2e6cb072da2d0534cd626acf8451c0f0f1dca1ac0c18017536ea314cf3d2fa5e27a13000c4542e4cf86b407b2255f9819a763797c221c8ed7e7050bc1c9e57c35d5bb0bddcdb98f4a1b58f6d8b8d6edb292fd0f7fa82dc5fdcd78b04ca09e7bc3f4164d901b119c4f427d054e7848fdf7110352c4e612d02489da801ec9ab978d98831fa7f872fa750b092967ff6bdd223199af209383bbce36799a5ed5856f587f7d420e8d76a58b398ef1f7b290bc5b75ef59182bfa02fafb7caeb504bd9f77348aea61ae9",
			16);
	
	// private exponent
	static BigInteger d = new BigInteger(
			"1801d152befc69b1134eda145bf6c94e224fa1acee36f06826436c609840a776a532911ae48101a460699fd9424a1d51329804fa23cbec98bf95cdb0dbc900c05c5a358f48228ab03372b25610b0354d0e4a8c57efe86b1b2fb9ff6580655cdabddb31d7a8cfaf99e7866ba0d93f7ee8d1aab07fc347836c03df537569ab9fcfca8ebf5662feafbdf196bb6c925dbc878f89985096fabd6430511c0ca9c4d99b6f9f5dd9aa3ddfac12f6c2d3194ab99c897ba25bf71e53cd33c1573e242d75c48cd2537d1766bbbf4f7235c40ce3f49b18e00c874932412743dc28b7d3d32e85c922c1d9a8e5bf4c7dd6fe4545dd699295d51945d1fc507c24a709e87561b001",
			16);

	// public exponent (just provided for illustration. The focus in this assignment is on the decryption process)
	static BigInteger e = new BigInteger("10001", 16);
    //static BigInteger R=BigInteger.probablePrime(N.bitLength()*2,new Random());
	//static BigInteger R=N.nextProbablePrime();
	 static BigInteger R=new BigInteger("2",16).pow(N.bitLength());
	 static BigInteger Rs=R.subtract(new BigInteger("1",16));
	static BigInteger Ri=R.modInverse(N);
	static BigInteger Ni=(N.modInverse(R)).negate();
	private static BigInteger encrypt(BigInteger m, BigInteger e, BigInteger modulus) {
		return modExp(m, e, modulus);
	}
	
	private static BigInteger decrypt(BigInteger c, BigInteger d, BigInteger modulus) {
		return modExp(c, d, modulus);
	}
	private static BigInteger decrypt2(BigInteger c, BigInteger d, BigInteger modulus) {
		return modExp2(c, d, modulus);
	}
	private static BigInteger modExp(BigInteger a, BigInteger exponent, BigInteger N) {
		// Note: This code assumes the most significant bit of the exponent is 1, i.e., the exponent is not zero.

		BigInteger result = a;
		
		int expBitlength = exponent.bitLength();
		
		for (int i = expBitlength - 2; i >= 0; i--) {
			result = result.multiply(result).mod(N);
		
			if (exponent.testBit(i)) {
				result = result.multiply(a).mod(N);
		
				
			}
		}
		return result;
		
		
	}

	private static BigInteger modExp2(BigInteger a, BigInteger exponent, BigInteger N) {
		// Note: This code assumes the most significant bit of the exponent is 1, i.e., the exponent is not zero.
		BigInteger ai=(a.multiply(R)).mod(N);
		BigInteger result = ai;
		
		int expBitlength = exponent.bitLength();
		
		for (int i = expBitlength - 2; i >= 0; i--) {
			//result = result.multiply(result).mod(N);
			result=Montg(result,result);
			if (exponent.testBit(i)) {
				//result = result.multiply(a).mod(N);
				result=Montg(result,ai);
				
			}
		}
		return result.multiply(Ri).mod(N);
		
		
	}
	private static BigInteger Montg(BigInteger ai, BigInteger bi) {
		
	/*	BigInteger R=BigInteger.probablePrime(N.bitLength()*2,new Random());
		BigInteger Ri=R.modInverse(N);
		BigInteger Ni=(N.modInverse(R)).negate();
		BigInteger ai=(a.multiply(R)).mod(N);
		BigInteger bi=(b.multiply(R)).mod(N);*/
		BigInteger t=ai.multiply(bi);
		BigInteger m=(t.multiply(Ni)).and(Rs);
		BigInteger ti=(t.add(m.multiply(N))).divide(R);
		BigInteger ci;
		if(ti.compareTo(N) == -1 ) {
			ci = ti;
		}
		else {
			flag = true;
			ci = ti.subtract(N);
		
		}
//		if(ti.max(N)==ti) {
//			ci=ti.subtract(N);
//			flag=true;
//		}
//		else {
//			ci=ti;
//		}
	//return (ci.multiply(Ri)).mod(N);
		return ci;
	}

	public static void main(String[] args) {
		
		// RSA modulus
		System.out.println("part:1");
		BigInteger modulus = new BigInteger(
				"a12360b5a6d58b1a7468ce7a7158f7a2562611bd163ae754996bc6a2421aa17d3cf6d4d46a06a9d437525571a2bfe9395d440d7b09e9912a2a1f2e6cb072da2d0534cd626acf8451c0f0f1dca1ac0c18017536ea314cf3d2fa5e27a13000c4542e4cf86b407b2255f9819a763797c221c8ed7e7050bc1c9e57c35d5bb0bddcdb98f4a1b58f6d8b8d6edb292fd0f7fa82dc5fdcd78b04ca09e7bc3f4164d901b119c4f427d054e7848fdf7110352c4e612d02489da801ec9ab978d98831fa7f872fa750b092967ff6bdd223199af209383bbce36799a5ed5856f587f7d420e8d76a58b398ef1f7b290bc5b75ef59182bfa02fafb7caeb504bd9f77348aea61ae9",
				16);
		
		// private exponent
		BigInteger d = new BigInteger(
				"1801d152befc69b1134eda145bf6c94e224fa1acee36f06826436c609840a776a532911ae48101a460699fd9424a1d51329804fa23cbec98bf95cdb0dbc900c05c5a358f48228ab03372b25610b0354d0e4a8c57efe86b1b2fb9ff6580655cdabddb31d7a8cfaf99e7866ba0d93f7ee8d1aab07fc347836c03df537569ab9fcfca8ebf5662feafbdf196bb6c925dbc878f89985096fabd6430511c0ca9c4d99b6f9f5dd9aa3ddfac12f6c2d3194ab99c897ba25bf71e53cd33c1573e242d75c48cd2537d1766bbbf4f7235c40ce3f49b18e00c874932412743dc28b7d3d32e85c922c1d9a8e5bf4c7dd6fe4545dd699295d51945d1fc507c24a709e87561b001",
				16);

		// public exponent (just provided for illustration. The focus in this assignment is on the decryption process)
		BigInteger e = new BigInteger("10001", 16);
		long av1=0,av2=0;
		for(int i=0;i<100;i++) {
		Random rnd = new Random();
		BigInteger m = new BigInteger(modulus.bitLength() - 1, rnd);
		BigInteger c = encrypt(m, e, modulus);
		long start=System.currentTimeMillis();
		 decrypt(c, d, modulus);
		long e1=System.currentTimeMillis();
		BigInteger m2= decrypt2(c, d, modulus);
		 long e2=System.currentTimeMillis();
		 av1=av1+(e1-start);
		 av2=av2+(e2-e1);
		//System.out.println("Original message = " + m.toString(16));
		//System.out.println("Ciphertext = " + c.toString(16));
		//System.out.println("Decrypted message = " + m2.toString(16));
		if (!m.equals(m2)) {
			System.err.println("There is an error.");
		} 
		}
		System.out.println("average time without montegomry="+av1/100);
		System.out.println("average time with montegomry="+av2/100);
		System.out.println("Part:2");
		int bit[]=new int[] {3,5,10,20,50,100};
		int index=0;
		double Av0=0,Av1=0;
		for(index=0;index<6;index++) {
		  BigInteger t=d.shiftRight((d.bitLength()-bit[index]));
		  System.out.println(bit[index]+" _bit exponent:");
		for(int i=0;i<200000;i++) {
			Random rnd = new Random();
			BigInteger m = new BigInteger(N.bitLength() - 1, rnd);
			long start=System.currentTimeMillis();
			decrypt2(m, t, N);
			long end = System.currentTimeMillis();
		
			long elapsed=end-start;
			m=m.multiply(R).mod(N);
			BigInteger x=m;
			x=Montg(x,x);
			x=Montg(x,m);
			flag=false;
			x=Montg(x,x);
			
			
			if(flag) {
			
				M1.add(elapsed);
			}
			else {
				M2.add(elapsed);
			}
			 x=m;
			x=Montg(x,x);
			flag=false;
			x=Montg(x,x);
			
			if(flag) {
				M3.add(elapsed);
			}
			else {
				M4.add(elapsed);
			}
		}
		
		 double m1=0,m2=0,m3=0,m4=0;
		for(long l:M1) {
			m1+=l;
		}
		for(long l:M2) {
			m2+=l;
		}
		for(long l:M3) {
			m3+=l;
		}
		for(long l:M4) {
			m4+=l;
		}
		if(m1!=0) {
			m1=m1/M1.size();
		}
		if(m2!=0) {
			m2=m2/M2.size();
		}
		if(m3!=0) {
			m3=m3/M3.size();
		}
		if(m4!=0) {
			m4=m4/M4.size();
		}
		System.out.println("m1= "+m1+" m2= "+m2+" m3= "+m3+" m4= "+m4);
		Av1+=Math.abs((m2-m1));Av0+=Math.abs((m4-m3));
		M1.clear();M2.clear();M3.clear();M4.clear();
		}
		System.out.println("mean diff. time if bit is zero="+Av0/6);
		System.out.println("mean diff. time if bit is one="+Av1/6);
		if(Av0>Av1) {System.out.println("second bit is zero");}
		else {
			System.out.println("second bit is one");
		}
		//System.out.print(encrypt(new BigInteger("10",16), new BigInteger("2",16), new BigInteger("3",16)).multiply(new BigInteger("4",16)).mod(new BigInteger("3",16)));
		
	}

}