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
	
	private List<Line> rowList;
	private List<Line> colList;
	
	public void makeColStructure() {
		List<Line> cols = new ArrayList<Line>();
		for(int i = 1; i <= width; i++) {
			cols.add(new Line(i,new ArrayList<Pair>()));
		}
		for(Line l: rowList) {
			for(Pair p: l.list) {
				cols.get(p.key - 1).list.add(new Pair(p.value, l.nom));
			}
		}
		colList = new ArrayList<Line>();
		for(Line l: cols) {
			if(l.list.isEmpty())
				continue;
			colList.add(l);
		}
	}
	
	public void makeRowStructure() {
		List<Line> rows = new ArrayList<Line>();
		for(int i = 1; i <= hight; i++) {
			rows.add(new Line(i,new ArrayList<Pair>()));
		}
		for(Line l: colList) {
			for(Pair p: l.list) {
				rows.get(p.key - 1).list.add(new Pair(p.value, l.nom));
			}
		}
		rowList = new ArrayList<Line>();
		for(Line l: rows) {
			if(l.list.isEmpty())
				continue;
			rowList.add(l);
		}
	}
	/**
	 *  онструктор матрицы, считывающий ее из файла fileName.
	 * @param fileName
	 */
	public SparseMatrix(String fileName) throws FileNotFoundException {
		Scanner scanner = new Scanner(new File(fileName));
		this.hight = scanner.nextInt();
		this.width = scanner.nextInt();
		this.rowList =  new ArrayList<Line>();
		this.colList =  new ArrayList<Line>();
		for(int i = 1; i <= this.hight; i++)
		{
			Line row = new Line(i, new ArrayList<Pair>());
			for(int j = 1; j <= this.width; j++)
			{
				int m = scanner.nextInt();
				if( m != 0) {
					row.list.add(new Pair(m, j));
				}
			}
			if( !row.list.isEmpty()) {
				this.rowList.add(row);
			}
		}
		this.makeColStructure(); 
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
		for(Line l: rowList) {
			if(l.nom == i) {
				for(Pair p: l.list) {
					if(p.key == j) {
						return p.value;
					}
				}
			}
		}
		return 0;
	}
	
	@Override
	public Line getRow(int nom) {
		for(Line l: rowList) {
			if(l.nom == nom) {
				return l;
			}
		}
		return new Line(nom, new ArrayList<Pair>());
	}

	@Override
	public Line getCol(int nom) {
		for(Line l: colList) {
			if(l.nom == nom) {
				return l;
			}
		}
		return new Line(nom, new ArrayList<Pair>());
	}
	
	/**
	 *  онструктор  инициализирующий разреженную матрицу по высоте и ширине.
	 * @param hight, width
	 */
	public SparseMatrix (int hight, int width) {
		this.hight = hight;
		this.width = width;
		this.rowList = new ArrayList<Line>();
		this.colList = new ArrayList<Line>();
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
		for(Line l : rowList) {
			Line n = new Line(l.nom, new ArrayList<Pair>());
			for(int i = 1; i <= X.width; i++) {
				Line c =  o.getCol(i);
				if(c.list.isEmpty())
					continue;
				n.list.add(new Pair(l.lineMul(c), i));
			}
			X.rowList.add(n);
		}
		X.makeColStructure();
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
				for(Line l : rowList) {
					if(l.nom % THREAD_COUNT == nom) {
					Line n = new Line(l.nom, new ArrayList<Pair>());
					for(int i = 1; i <= X.width; i++) {
						Line c =  o.getCol(i);
						if(c.list.isEmpty())
							continue;
						n.list.add(new Pair(l.lineMul(c), i));
					}
					X.rowList.add(n);
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
		X.makeColStructure();
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
