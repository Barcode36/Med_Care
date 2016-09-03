package com.Over2Cloud.Rnd;

class A
{
	A()
	{
		System.out.println("A ");
	}
	
}
class MainClass extends A{

	
	MainClass()
	{
		super();
		System.out.println("B");
	}
	public static void main(String ar[])
	{
		System.out.println("INSIDE A");
	}
   /* public static void main(String[] args)
    {*/

      /*  //Animal animal = new Animal();
        Bird bird = new Bird();
        Dog dog = new Dog();
        System.out.println();

        //animal.sleep();
       // animal.eat();
        bird.sleep();
        bird.eat();
        dog.sleep();
        dog.eat();*/
    	
   // }//
}



