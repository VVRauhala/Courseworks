/*
 * Tietorakenteet 2016
 * Harjoitustyö
 * Vili-Veikko Rauhala
 * Rauhala.Vili.V@Student.uta.fi
 * 
 */

/* Solmu jolla tietoina int tyyppinen avain, String merkkijono sekä
 * vasen ja oikea lapsisolmu
 */

public class Node {
   private int key;
   private String data;
   private Node left;
   private Node right;
   private Node parent;
     
   public Node() {  
   }
   
   public Node(int key, String mj) {
      this.key = key;
      this.data = mj;
   }
   
   public void setKey(int k) {
      key = k;
   }
   
   public int getKey() {
      return key;
   }
   
   public String getData() {
      return data;
   }
   
   public void setData(String str) {
      data = str;
   }
   
   public Node getParent() {
      return parent;
   }
   
   public void setParent(Node parent) {
      this.parent = parent;
   }
   
   public Node getLeftChild() {
      return left;
   }
   
   public void setLeftChild(Node left) {
      this.left = left;
   }
   
   public Node getRightChild() {
      return right;
   }
   
   public void setRightChild(Node right) {
      this.right = right;
   }
 }
