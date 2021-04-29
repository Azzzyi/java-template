package main;

import java.util.Iterator;
import java.util.List;

public interface Matrix
{
	public class Pair{
		public int value;
		public int key;
		public Pair(int value, int key) {
			this.value = value;
			this.key = key;
		}
		
		public boolean equals(Pair p) {
			return(this.key == p.key && this.value == p.value);
		}
	}
	
	public class Line {
		int nom = 0;
		List<Pair> list;
		
		public Line(int nom, List<Pair> list) {
			this.nom = nom;
			this.list = list;
		}
		
		public int lineMul(Line l) {
			int sum = 0;
			if(list.size() * l.list.size() == 0)
				return 0;
			Iterator<Pair> i = list.iterator();
			Iterator<Pair> j = l.list.iterator();
			Pair p1 = i.next();
			Pair p2 = j.next();
			while(true) {
				while(j.hasNext() && p1.key > p2.key)
					p2 = j.next();
				if(p1.key == p2.key) 
				{
					sum+= p1.value * p2.value;
				}
					
				if(i.hasNext()) {
					p1 = i.next();
				}
				else
					break;
			}
		return sum;
		}
	}
	
	public final int hight = 0;
	
	public final int width = 0;
	
	public static int THREAD_COUNT = 4;
	
	public Line getRow(int nom);
	
	public Line getCol(int nom);
	
	public int getHight();
	
	public int getWidth();
	
	public void setHight(int hight);
	
	public void setWidth(int width);
	
	public void out();
	
    public int get(int i, int j);
    
	Matrix mul(Matrix o);
	
	Matrix dmul(Matrix o);
	
}
