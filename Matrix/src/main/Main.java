package main;

import java.io.IOException;


public class Main {
	public static final String MATRIX1_NAME = "m1.txt";
	public static final String MATRIX2_NAME = "m2.txt";
	  
	public static void main(String[] arg) throws IOException {
		System.out.println("Starting loading dense matrices");
	    Matrix m1 = new DenseMatrix(MATRIX1_NAME);
	    System.out.println("1 loaded");
	    Matrix m2 = new DenseMatrix(MATRIX2_NAME);
	    System.out.println("2 loaded");
	    long start = System.currentTimeMillis();
	    Matrix r1 = m1.mul(m2);
	   // m1.out();
	   // m2.out();
	    r1.out();
	    System.out.println("Dense Matrix time: " +(System.currentTimeMillis() - start));
	    System.out.println("Starting loading sparse matrices");
	    m1 = new SparseMatrix(MATRIX1_NAME);
	    System.out.println("1 loaded");
	    m2 = new SparseMatrix(MATRIX2_NAME);
	    System.out.println("2 loaded");
	    start = System.currentTimeMillis();
	    Matrix r2 = m1.mul(m2);
	   // m1.out();
	   // m2.out();
	    r2.out();
	    System.out.println("Sparse Matrix time: " +(System.currentTimeMillis() - start));
	    System.out.println("equals: " + r1.equals(r2));
	}
}
