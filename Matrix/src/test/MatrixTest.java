package test;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import main.Matrix;
import main.SparseMatrix;
import main.DenseMatrix;


public class MatrixTest
{	
	public static final String MATRIX1_NAME = "m1.txt";
	public static final String MATRIX2_NAME = "m2.txt";
	public static final String RESULT_MATRIX_NAME = "result.txt";
	
	@Test
	public void equalsDS() throws IOException {
		Matrix m1 = new SparseMatrix(MATRIX1_NAME);
		Matrix m2 = new DenseMatrix(MATRIX1_NAME);
		assertEquals(m1,m2);
	}
	
	@Test
	public void mulDD() throws IOException {
		Matrix m1 = new DenseMatrix(MATRIX1_NAME);
		Matrix m2 = new DenseMatrix(MATRIX2_NAME);
		Matrix expected = new DenseMatrix(RESULT_MATRIX_NAME);
		assertEquals(expected, m1.mul(m2));
	}
	
	@Test
	public void mulDS() throws IOException {
		Matrix m1 = new DenseMatrix(MATRIX1_NAME);
		Matrix m2 = new SparseMatrix(MATRIX2_NAME);
		Matrix expected = new DenseMatrix(RESULT_MATRIX_NAME);
		assertEquals(expected, m1.mul(m2));
	}
	
	@Test
	public void mulSD() throws IOException {
		Matrix m1 = new SparseMatrix(MATRIX1_NAME);
		Matrix m2 = new DenseMatrix(MATRIX2_NAME);
		Matrix expected = new DenseMatrix(RESULT_MATRIX_NAME);
		assertEquals(expected, m1.mul(m2));
	}
	

	@Test
	public void mulSS() throws IOException {
		Matrix m1 = new SparseMatrix(MATRIX1_NAME);
		Matrix m2 = new SparseMatrix(MATRIX2_NAME);
		Matrix expected = new DenseMatrix(RESULT_MATRIX_NAME);
		assertEquals(expected, m1.mul(m2));
	}
	
	@Test
	public void dmulDD() throws IOException {
		Matrix m1 = new DenseMatrix(MATRIX1_NAME);
		Matrix m2 = new DenseMatrix(MATRIX2_NAME);
		Matrix expected = new DenseMatrix(RESULT_MATRIX_NAME);
		assertEquals(expected, m1.dmul(m2));
	}
	
	@Test
	public void dmulSD() throws IOException {
		Matrix m1 = new SparseMatrix(MATRIX1_NAME);
		Matrix m2 = new DenseMatrix(MATRIX2_NAME);
		Matrix expected = new DenseMatrix(RESULT_MATRIX_NAME);
		assertEquals(expected, m1.dmul(m2));
	}
	
	@Test
	public void dmulDS() throws IOException {
		Matrix m1 = new DenseMatrix(MATRIX1_NAME);
		Matrix m2 = new SparseMatrix(MATRIX2_NAME);
		Matrix expected = new DenseMatrix(RESULT_MATRIX_NAME);
		assertEquals(expected, m1.dmul(m2));
	}
	
	@Test
	public void dmulSS() throws IOException {
		Matrix m1 = new SparseMatrix(MATRIX1_NAME);
		Matrix m2 = new SparseMatrix(MATRIX2_NAME);
		Matrix expected = new DenseMatrix(RESULT_MATRIX_NAME);
		assertEquals(expected, m1.dmul(m2));
	}
	
}
