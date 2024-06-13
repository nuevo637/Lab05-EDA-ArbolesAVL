package avl;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);
    //INPUT
    System.out.println("INGRESE SU PALABRA EN MAYUSCULAS:");
    String palabra = sc.next();
    AVL<Integer> arbol = new AVL<>();
    for (char c : palabra.toCharArray()) {
        arbol.insert((int)c);
    }
    //OUTPUT
    System.out.println(arbol);
    }
}
