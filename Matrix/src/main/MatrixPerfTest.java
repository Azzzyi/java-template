package main;

import java.io.IOException;

import main.*;

public class MatrixPerfTest
{	
	public static final String MATRIX1_NAME = "test1.txt";
	public static final String MATRIX2_NAME = "test2.txt";
	public static final int SEED1 = 1;
	public static final int SEED2 = 2;
	public static final int EMPTY_ROW_FRACTION = 5;
	public static final int SIZE = 50;
	
	public static void main(String s[]) throws IOException
	{	
		new MatrixGenerator(SEED1, EMPTY_ROW_FRACTION, MATRIX1_NAME, SIZE).generate();
		new MatrixGenerator(SEED2, EMPTY_ROW_FRACTION, MATRIX2_NAME, SIZE).generate();
		System.out.println("Starting loading dense matrices");
		Matrix m1 = new DenseMatrix(MATRIX1_NAME);
		System.out.println("1 loaded");
		Matrix m2 = new DenseMatrix(MATRIX2_NAME);
		System.out.println("2 loaded");
		long start = System.currentTimeMillis();
		Matrix r1 = m1.dmul(m2);
		System.out.println("Dense Matrix time: " +(System.currentTimeMillis() - start));

		System.out.println("Starting loading sparse matrices");
		m1 = new SparseMatrix(MATRIX1_NAME);
		System.out.println("1 loaded");
		m2 = new SparseMatrix(MATRIX2_NAME);
		System.out.println("2 loaded");
		start = System.currentTimeMillis();
		Matrix r2 = m1.dmul(m2);
		System.out.println("Sparse Matrix time: " +(System.currentTimeMillis() - start));
		System.out.println("equals: " + r1.equals(r2));
	}
}
