package tests;
import java.util.Scanner;
import avl.AVL;

public class Main {

    public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);
    //INPUT
    System.out.println("INGRESE SU PALABRA EN MAYUSCULAS:");
    String palabra = sc.next().toUpperCase();
    AVL<Character> arbol = new AVL<>();
    for (char c : palabra.toCharArray()) {
      arbol.insert(c);
    }
    //OUTPUT
    arbol.display();
    }
}
