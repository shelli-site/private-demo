package com.helloworld;

public class HelloWorld {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("====输出区别====");
		System.out.println("Hello!" + 'a' + 7);
		System.out.println('a' + 7 + "Hello!");
		System.out.println("======END======");

		System.out.println("====二维数组====");
		int[][] arr = new int[2][3];
		System.out.println("arr:" + arr); // [[I@15db9742
		System.out.println("arr[0]:" + arr[0]); // [I@6d06d69c
		System.out.println("arr[1]:" + arr[1]); // [I@7852e922
		System.out.println("arr[0][0]:" + arr[0][0]); // 0
		System.out.println("======END======");
	}

}
