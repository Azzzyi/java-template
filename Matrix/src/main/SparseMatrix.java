package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *  ласс описывающий работу с разреженными матрицами. 
 * ѕол€ hight и width описывают высоту и ширину матрицы соответственно. 
 * ѕоле listOFRows - массив значений - массив ненулевых срок матрицы.
 * 
 */
public class SparseMatrix implements Matrix
{
		
	private int hight, width;
	/**
	 *  ласс описывающий ненулевую строку разреженной матрицы. 
	 * ѕоле nom - номер строки. value и col - списки значений и номеров 
	 * столбцов, соответственно, ненулевых эллементов. 
	 * 
	 */
	private class Row {
		int nom = 0;
		List<Integer> value;
		List<Integer> col;
		
		public Row(int nom, List<Integer> value, List<Integer> col) {
			this.nom = nom;
			this.value = value;
			this.col = col;
		}
		
	}
	private List<Row> listOfRows;  
	
	/**
	 *  онструктор матрицы, считывающий ее из файла fileName.
	 * @param fileName
	 */
	public SparseMatrix(String fileName) throws FileNotFoundException {
		Scanner scanner = new Scanner(new File(fileName));
		this.hight = scanner.nextInt();
		this.width = scanner.nextInt();
		this.listOfRows =  new ArrayList<Row>();
		for(int i = 1; i <= this.hight; i++)
		{
			List<Integer> value = new ArrayList<Integer>();
			List<Integer> col = new ArrayList<Integer>();
			for(int j = 1; j <= this.width; j++)
			{
				int m = scanner.nextInt();
				if( m != 0) {
					value.add(m);
					col.add(j);
				}
				this.listOfRows.add(new Row(i, value, col));
			}
		}
	}
	
	/**
	 * ¬ыводит матрицу в консль.
	 */
	@Override public void out() {
	System.out.println(hight + " " + width);
		for(int i = 1; i <= hight; i++ ) {
			for(int j = 1;j <= width; j++) {
				System.out.print(this.get(i, j) + " "); 
			}
			System.out.print("\n");
		}
	}
	
	/**
	 * ¬озвращает M[i][j] разреженной мартицы. 
	 * @param i, j
	 */
	@Override public int get(int i, int j) {
		if(i > hight || j > width || i < 1 || j < 1 ) {
			System.out.println("OutOfBounds");
			System.exit(-1);
		}
		for(Row  r:listOfRows) {
			if(r.nom == i) {
				int iInList = r.col.indexOf(j);
				if(iInList != -1)
					return r.value.get(iInList);
			}
		}
		return 0;
	}
	
	/**
	 *  онструктор  инициализирующий разреженную матрицу по высоте и ширине.
	 * @param hight, width
	 */
	public SparseMatrix (int hight, int width) {
		this.hight = hight;
		this.width = width;
		this.listOfRows = new ArrayList<Row>();
	}
	
	/**
	 * ¬озвращает матрицу произведени€ this и o (o - левый сомножитель) 
	 * @param o
	 */
	@Override public Matrix mul(Matrix o)
	{
		
		if(width != o.getHight()){
			System.out.println("InvalidSizesOfMultipliedMatrix");
			System.exit(-1);
		}
		SparseMatrix X = new SparseMatrix(hight, o.getWidth());
		for(int i = 1; i <= X.hight; i++) {
			List<Integer> value = new ArrayList<Integer>();
			List<Integer> col = new ArrayList<Integer>();
			for(int j = 1; j <= X.width; j++) {
				int sum = 0;
				for(int k = 1; k <= width; k++) {
					sum+= this.get(i, k) * o.get(k, j); 
				}
				if(sum != 0)
					{
						value.add(sum);
						col.add(j);
					}
			}
			X.listOfRows.add(new Row(i, value, col));
		}
		return X;
	}

	/**
	 * ћетод многопоточного произведени€ матриц.
	 * ¬озвращает матрицу произведени€ this и o (o - левый сомножитель) 
	 * @param o
	 */
	public Matrix dmul(Matrix o)
	{
		
		if(width != o.getHight()){
			System.out.println("InvalidSizesOfMultipliedMatrix");
			System.exit(-1);
		}
		
		SparseMatrix X = new SparseMatrix(this.hight, o.getWidth());
		
		class ThreadCalc extends Thread{
			final int nom;
			
			public ThreadCalc(int nom) {
				
				this.nom = nom;
			}
			
			@Override public void run(){
				for(int i = 1; i <= hight; i++)
				{
					if( i % THREAD_COUNT == nom ) {

						List<Integer> value = new ArrayList<Integer>();
						List<Integer> col = new ArrayList<Integer>();
						for(int j = 1; j <= X.width; j++) {
							int sum = 0;
							for(int k = 1; k <= width; k++) {
								sum+= get(i, k) * o.get(k, j); 
							}
							if(sum != 0)
								{
									value.add(sum);
									col.add(j);
								}
						}
						X.listOfRows.add(new Row(i, value, col));
					}
				}
			}
		}
		
		List<ThreadCalc> threadList = new ArrayList<ThreadCalc>(THREAD_COUNT);
		
		for(int i = 0; i < THREAD_COUNT; i++)
		{
			threadList.add(new ThreadCalc(i));
		}
		
		for(ThreadCalc i: threadList)
		{
			i.start();
			try
			{
				i.join();
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}
		
		return X;
		
	}

	
	@Override public boolean equals(Object o) {
		if(o == null)
			return false;
		if(!(o instanceof DenseMatrix || o instanceof SparseMatrix ))
			return false;
		Matrix matrix = (Matrix)o;
		if(hight != matrix.getHight())
			return false;
		if(width != matrix.getWidth())
			return false;
		for(int i = 1; i <= hight; i++) {
			for(int j = 1; j <= width; j++) {
				if(this.get(i, j) != matrix.get(i, j))
					return false;
			}
		}
		return true;
	}

	@Override
	public int getHight() {
		return hight;
	}

	@Override
	public int getWidth() {
		return width;
	}

	@Override
	public void setHight(int hight) {
		this.hight = hight;
		
	}

	@Override
	public void setWidth(int width) {
		this.width = width;
	}
}
