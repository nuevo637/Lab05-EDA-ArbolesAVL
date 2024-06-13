package avl;

import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.layout.HierarchicalLayout;
import org.graphstream.ui.view.Viewer;

public class AVL<E extends Comparable<E>> {

  private Node<E> root;

  private static int ALTURA_MINIMA_PERMITIDA = 1;

  public AVL() {
    this.root = null;
  }

  public E search(E x){
    Node<E> res = searchNode(root, x);
    if(res == null) 
  return null;
    return res.data;
  }
  protected Node<E> searchNode(Node<E> actual, E x){
    if(actual == null) return null;
    int resComp = actual.data.compareTo(x);
    if(resComp < 0) return searchNode(actual.right, x);
    if(resComp > 0) return searchNode(actual.left, x);
    return actual;
  }

  public boolean isEmpty(){
    return root == null;
  }

  public E getMin(){
    if(isEmpty()) return null;
    return getMinNode(root).data;
  }
  protected Node<E> getMinNode(Node<E> actual){
    if(actual.left == null) return actual;
    return getMinNode(actual.left);
  }

  protected Node<E> getMaxNode(Node<E> actual){
    if(actual.right == null) return actual;
    return getMaxNode(actual.right);
  }

  public E parent(E x){
    Node<E> res = searchParent(root, null, x);
    if(res == null) return null;
    return res.data;
  }
  protected Node<E> searchParent(Node<E> actual, Node<E> parent, E x){
    if(actual == null) return null;
    int resComp = actual.data.compareTo(x);
    if(resComp < 0) return searchParent(actual.right, actual, x);
    if(resComp > 0) return searchParent(actual.left, actual, x);
    return parent;   
  }

  @SuppressWarnings("unchecked")
  public E[] son(E x){
    Node<E> parent = searchNode(root, x);
    if(parent == null) 
  return null;
    E[] res = (E[]) new Object[2];
    res[0] = parent.left.data;
    res[1] = parent.right.data;
    return res;
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

  public void display(){
    if(isEmpty()) return;
    System.setProperty("org.graphstream.ui", "swing");
    Graph graph = new SingleGraph("Arbol AVL");
    String css = "node { fill-color: black; size: 30px; text-size: 20px; text-color: white; } edge { fill-color: black; size: 2px; arrow-size: 10px, 10px; arrow-shape: arrow; }";
    graph.setAttribute("ui.stylesheet", css);

    graph.addNode(root.toString());
    createTree(graph, root, 50, 5, 40);
    Viewer viewer = graph.display(false);
  }

  private void createTree(Graph graph, Node<E> actual, int x, int y, int xOffset){
    if(actual == null) return;
    graph.getNode(actual.toString()).setAttribute("ui.label", actual.data.toString());
    graph.getNode(actual.toString()).setAttribute("xyz", x, y, 0);
    if(actual.left != null){
      graph.addNode(actual.left.toString());
      graph.addEdge(actual.toString() + actual.left.toString(), actual.toString(), actual.left.toString());
      createTree(graph, actual.left, x - xOffset, y - 10, xOffset / 2);
    }
    if(actual.right != null){
      graph.addNode(actual.right.toString());
      graph.addEdge(actual.toString() + actual.right.toString(), actual.toString(), actual.right.toString());
      createTree(graph, actual.right, x + xOffset, y - 10, xOffset / 2);
    }
  }

  public String toString(){
    return toString(root);
  }
  private String toString(Node<E> actual){
    if(actual == null) return "";
    return actual.data + " " + toString(actual.left) + " " + toString(actual.right);
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

    public String toString(){
      return data.toString();
    }
  }

}
