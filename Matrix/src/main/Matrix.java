package main;

public interface Matrix
{
 
	public final int hight = 0;
	
	public final int width = 0;
	
	public static int THREAD_COUNT = 4;
	
	public int getHight();
	
	public int getWidth();
	
	public void setHight(int hight);
	
	public void setWidth(int width);
	
	public void out();
	
    public int get(int i, int j);
    
	Matrix mul(Matrix o);
	
	Matrix dmul(Matrix o);
	
}
