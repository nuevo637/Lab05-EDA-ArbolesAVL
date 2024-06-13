package avl;

public class AVL<E extends Comparable<E>> {

  private Node<E> root;

  private static int ALTURA_MINIMA_PERMITIDA = 1;

  public AVL() {
    this.root = null;
  }

  public void insert(E x){
    root = insert(x, root);
  }

  private Node<E> insert(E x, Node<E> actual){
    if(actual == null) return new Node<E>(x);
    int resComp = actual.data.compareTo(x);
    if(resComp > 0) actual.left = insert(x, actual.left);
    else if(resComp < 0) actual.right = insert(x, actual.right);
    else{}
    return balance(actual);
  }

  private Node<E> balance(Node<E> actual){
    if(height(actual.left) - height(actual.right) > ALTURA_MINIMA_PERMITIDA){
      if(height(actual.left.left) >= height(actual.left.right))
        actual = RSD(actual);
      else
        actual = RDD(actual);
    }else if(height(actual.right) - height(actual.left) > ALTURA_MINIMA_PERMITIDA){
      if(height(actual.right.right) >= height(actual.right.left))
        actual = RSI(actual);
      else
        actual = RDI(actual);
    }
    actual.height = Math.max(height(actual.left), height(actual.right)) + 1;
    return actual;
  }

  private Node<E> RSD(Node<E> actual){
    Node<E> aux = actual.left;
    actual.left = aux.right;
    aux.right = actual;
    actual.height = Math.max(height(actual.left), height(actual.right)) + 1;
    aux.height = Math.max(height(aux.left), height(aux.right)) + 1;
    return aux;
  }

  private Node<E> RSI(Node<E> actual){
    Node<E> aux = actual.right;
    actual.right = aux.left;
    aux.left = actual;
    actual.height = Math.max(height(actual.left), height(actual.right)) + 1;
    aux.height = Math.max(height(aux.left), height(aux.right)) + 1;
    return aux;
  }

  private Node<E> RDD(Node<E> actual){
    actual.left = RSI(actual.left);
    return RSD(actual);
  }

  private Node<E> RDI(Node<E> actual){
    actual.right = RSD(actual.right);
    return RSI(actual);
  }

  private int height(Node<E> node) {
    return node == null ? -1 : node.height;
  }

  private static class Node<E> {
    E data;
    Node<E> left;
    Node<E> right;
    int height;

    public Node(E data) {
      this.data = data;
      this.left = null;
      this.right = null;
      this.height = 0;
    }
  }

}

