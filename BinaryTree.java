package Program3v3;


import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class BinaryTree<E> {
	protected static class Node<E> {
		public E data;
		public Node<E> leftChild;
		public Node<E> rightChild;

		public Node(E data) {
			this.data = data;
		}

		public String toString() {
			return "" + data;
		}
	}

	protected Node<E> root;

	public BinaryTree() {
	}

	public BinaryTree(Node<E> root) {
		this.root = root;
	}

	public List<Node<E>> preOrderNodes() {
		List<Node<E>> list = new ArrayList<Node<E>>();
		preOrderTraverse(root, list);
		return list;
	}

	private void preOrderTraverse(Node<E> n, List<Node<E>> list) {
		if (n == null)
			return;
		list.add(n);
		preOrderTraverse(n.leftChild, list);
		preOrderTraverse(n.rightChild, list);
	}

	public List<Node<E>> inOrderNodes() {
		List<Node<E>> list = new ArrayList<Node<E>>();
		inOrderTraverse(root, list);
		return list;
	}

	private void inOrderTraverse(Node<E> n, List<Node<E>> list) {

		if (n == null)
			return;
		inOrderTraverse(n.leftChild, list);
		list.add(n);
		inOrderTraverse(n.rightChild, list);
	}

	public List<Node<E>> postOrderNodes() {
		List<Node<E>> list = new ArrayList<Node<E>>();
		postOrderTraverse(root, list);
		return list;
	}

	private void postOrderTraverse(Node<E> n, List<Node<E>> list) {
		if (n == null)
			return;
		postOrderTraverse(n.leftChild, list);
		postOrderTraverse(n.rightChild, list);
		list.add(n);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		List<Node<E>> nodes = preOrderNodes();
		for (Node n : nodes)
			sb.append(n.data.toString());
		return sb.toString();
	}

	private void displayHelper(Node<E> n, int indent) {
		if (n == null) {
			System.out.printf("%" + indent + "s\n", "null");
			return;
		}
		System.out.printf("%" + indent + "s\n", n);
		displayHelper(n.leftChild, indent + 3);
		displayHelper(n.rightChild, indent + 3);
	}

	public void display() {
		displayHelper(root, 3);
	}

	public static BinaryTree<String> read(String input) {
		Scanner s = new Scanner(input);
		return new BinaryTree<String>(readHelper(s));
	}

	private static Node<String> readHelper(Scanner s) {
		if (!s.hasNext())
			return null;
		String data = s.next();
		if (data.equals("null"))
			return null;
		Node<String> n = new Node<String>(data);
		n.leftChild = readHelper(s);
		n.rightChild = readHelper(s);
		return n;
	}

	public static void main(String[] args) {

		BinaryTree<String> tree = BinaryTree
				.read("a b null null c d null null null");
		tree.display();
		System.out.println("preorder :" + tree.preOrderNodes());
		System.out.println("inorder  :" + tree.inOrderNodes());
		System.out.println("postorder:" + tree.postOrderNodes());

	}
}