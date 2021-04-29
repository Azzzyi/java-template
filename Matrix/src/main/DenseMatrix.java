package main;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Класс описывающий работу с плотными матрицами. 
 * Поля hight и width описывают высоту и ширину матрицы соответственно. 
 * Поле value - массив значений, M.value[i][j] - эллемент матрицы в строке i, столбце j.
 * 
 */
public class DenseMatrix implements Matrix
{
	
	private int hight, width;
	private int[][] value;
	
	/**
	 * Конструктор матрицы, считывающий ее из файла fileName 
	 * @param fileName
	 */
	public DenseMatrix(String fileName) throws IOException {
		
		Scanner scanner = new Scanner(new File(fileName));
		this.hight = scanner.nextInt();
		this.width = scanner.nextInt();
		this.value = new int[this.hight + 1][this.width + 1];
		for(int i = 1; i <= this.hight; i++)
		{
			for(int j = 1; j <= this.width; j++)
			{
				this.value[i][j] = scanner.nextInt();
			}
		}
	}
	
	/**
	 * Возвращает M[i][j] плотной мартицы  
	 * @param i, j
	 */
	@Override public int get(int i, int j) {
		if(i > hight || j > width || i < 1 || j < 1) {
			System.out.println("OutOfBounds");
			System.exit(-1);
		}
		return this.value[i][j];
	}
	
	/**
	 * Конструктор  инициализирующий плотную матрицу по высоте и ширине.
	 * @param hight, width
	 */
	public DenseMatrix (int hight, int width) {
		this.hight = hight;
		this.width = width;
		this.value = new int[this.hight + 1][this.width + 1];
	}
	
	@Override
	public Line getRow(int nom) {
		Line l = new Line(nom, new ArrayList<Pair>());
		for(int i = 1; i <= width; i++) {
			if(value[nom][i] != 0) {
				l.list.add(new Pair(value[nom][i], i));
			}
		}
		return l;
	}

	@Override
	public Line getCol(int nom) {
		Line l = new Line(nom, new ArrayList<Pair>());
		for(int i = 1; i <= hight; i++) {
			if(value[i][nom] != 0) {
				l.list.add(new Pair(value[i][nom], i));
			}
		}
		return l;
	}
	
	/**
	 * Выводит матрицу в консль.
	 */
	@Override public void out() {
		System.out.println(hight + " " + width);
		for(int i = 1; i <= this.hight; i++ ) {
			for(int j = 1; j <= width; j++ ) {
				System.out.print(value[i][j] + " ");
			}
			System.out.print("\n");
		}
	}
	/**
	 * Возвращает матрицу произведения this и o (o - левый сомножитель) 
	 * @param o
	 */
	@Override public Matrix mul(Matrix o)
	{
		if(width != o.getHight()){
			System.out.println("InvalidSizesOfMultipliedMatrix");
			System.exit(-1);
		}
		DenseMatrix X = new DenseMatrix(this.hight, o.getWidth());
		for(int i = 1; i <= X.hight; i++)
			for(int j = 1; j <= X.width; j++) {
				X.value[i][j] = getRow(i).lineMul(o.getCol(j));
			}
		return X;
	}
	/**
	 * Метод многопоточного произведения матриц.
	 * Возвращает матрицу произведения this и o (o - левый сомножитель) 
	 * @param o
	 */
	
	public Matrix dmul(Matrix o)
	{
		
		if(width != o.getHight()){
			System.out.println("InvalidSizesOfMultipliedMatrix");
			System.exit(-1);
		}
		
		DenseMatrix X = new DenseMatrix(this.hight, o.getWidth());
		
		class ThreadCalc extends Thread{
			final int nom;
			
			public ThreadCalc(int nom) {
				
				this.nom = nom;
			}
			
			@Override public void run(){
				for(int i = 1; i <= X.hight; i++)
					if( i % THREAD_COUNT == nom ) 
					for(int j = 1; j <= X.width; j++) {
						X.value[i][j] = getRow(i).lineMul(o.getCol(j));
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

	/**
	 * Метод сравнения this c произвольным o.
	 * @param o
	 */
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
				if((this.get(i, j) != matrix.get(i, j)))
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
