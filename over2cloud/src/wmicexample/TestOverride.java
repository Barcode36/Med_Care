package wmicexample;

public class TestOverride
{
	public static void main(String[] args) 
	{
		A a =new A();
		a.display();
		
		
		A b =new B();
		b.display();
		
	}
}


class A
{
	public static void display()
	{
		System.out.println("A hello");
	}
}

class B extends A
{
	public static void display()
	{
		System.out.println("B hello");
	}
}