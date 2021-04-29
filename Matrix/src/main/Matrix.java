package main;

import java.util.Iterator;
import java.util.List;

import main.Matrix.Pair;

public interface Matrix
{
	public class Pair{
		private int value;
		private int key;
		public Pair(int value, int key) {
			this.value = value;
			this.key = key;
		}
		
		public boolean equals(Pair p) {
			return(this.key == p.key && this.value == p.value);
		}
		
		public int getK() {
			return key;
		}
		
		public void setK(int key) {
			this.key = key;
		}
		public int getV() {
			return value;
		}
		public void setV(int value) {
			this.value = value;
		}
		
		@Override public boolean equals(Object o) {
			if(o == null)
				return false;
			if(!(o instanceof Pair))
				return false;
			Pair p = (Pair)o;
			if(p.key != key)
				return false;
			if(p.value != value)
				return false;
			return true;
		}
	}
	
	public class Line {
		private int nom = 0;
		public List<Pair> list;
		
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
		
		@Override public boolean equals(Object o) {
			if(o == null)
				return false;
			if(!(o instanceof Line))
				return false;
			Line l = (Line)o;
			if(l.nom != nom)
				return false;
			Iterator<Pair> i = list.iterator();
			Iterator<Pair> j = l.list.iterator();
			Pair p1,p2;
			while(true) {
				if(!(i.hasNext() ^ j.hasNext()))
					return false;
				if(!i.hasNext())
					break;
				p1 = i.next();
				p2 = j.next();
				if(!p1.equals(p2))
					return false;
			}
			return true;
		}
		
		public int getN()
		{
			return nom;
		}
		
		public void setN(int n)
		{
			this.nom = n;
		}
	}
	
	public final int hight = 0;
	
	public final int width = 0;
	
	public static int THREAD_COUNT = 2;
	
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
