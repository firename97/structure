package org.example.binary;


import org.example.printer.BinaryTreeInfo;
import org.example.printer.BinaryTrees;

import java.util.*;

/**
 * @program: structure
 * @description: 二叉树
 * @author: firename
 * @date: 2020/6/27
 */
public class BinarySearchTree<E> implements BinaryTreeInfo {

    private Comparator<E> comparator;

    private Node root;
    private int size;

    public BinarySearchTree(Comparator<E> comparator) {
        this.comparator = comparator;
    }

    public BinarySearchTree() {
        this.comparator = null;
    }

    public boolean add(E element) {
        if (Objects.isNull(element)) {
            throw new IllegalArgumentException("element不能为空");
        }
        if (root == null) {
            root = new Node(element, null);
            size++;
            return true;
        }

        Node<E> node = root;
        Node<E> parent = null;
        while (node != null) {
            parent = node;
            int comp = compareTo(node.element, element);
            if (comp > 0) {
                node = node.left;
            } else if (comp < 0) {
                node = node.right;
            } else {
                return true;
            }
        }
        Node<E> newNode = new Node<>(element, parent);
        if (compareTo(parent.element, element) > 0) {
            parent.left = newNode;
        } else {
            parent.right = newNode;
        }
//        node = newNode;
        size++;
        return true;

    }

    public boolean remove(E element) {
        Node<E> node = searchNode(element);
        return remove(node);
    }

    /**
     * 移除节点
     * @param node
     * @return
     */
    private boolean remove(Node<E> node) {
        if (node.hasTwoChild()) {
            Node<E> preface = preface(node);
            if (preface != null){
                node.element = preface.element;
            }
            node = preface;
        }
        if (node.parent == null){
            root = null;
            return true;
        }
        if (node.isLeft()){
            if (node.equals(node.parent.left)){
                node.parent.left = null;
                node = null;
                return true;
            }
            if (node.equals(node.parent.right)){
                node.parent.right = null;
                node = null;
                return true;
            }
        } else {
            if (node.equals(node.parent.left)){
                node.parent.left =  node.left != null ? node.left : node.right;
                node = null;
                return true;
            }
            if (node.equals(node.parent.right)){
                node.parent.right =  node.left != null ? node.left : node.right;
                node = null;
                return true;
            }
        }
        return false;
    }

    /**
     * 比较两个元素
     *
     * @param e1
     * @param e2
     * @return
     */
    int compareTo(E e1, E e2) {
        return comparator == null ? ((Comparable) e1).compareTo(e2) : comparator.compare(e1, e2);
    }

    /**
     * 前序遍历，从当前节点开始
     *
     * @param node
     */
    protected void preOrder(Node<E> node) {
        Stack<Node> stack = new Stack<>();
        stack.add(node);
        while (!stack.isEmpty()) {
            node = stack.pop();
            System.out.println(node.element);   // 前序遍历进行操作
            if (Objects.nonNull(node.right)) {
                stack.add(node.right);
            }
            if (Objects.nonNull(node.left)) {
                stack.add(node.left);
            }
        }
    }

    public boolean contains(E element) {
        return false;
    }

    /**
     * 获取前序节点
     *
     * @param node 当前节点
     * @return
     */
    protected Node<E> preface(Node<E> node) {
        Node<E> p = null;
        if (node.left != null) {
            p = node.left;
            while (p.right != null) {
                p = p.right;
            }
            return p;
        } else if (node.parent == null) {
            return null;
        } else if (node.parent.left.equals(node)) {
            p = node.parent;
            while (p.parent != null) {
                if (p.parent.right.equals(p)) {
                    return p.parent;
                } else {
                    p = p.parent;
                }
            }
            return null;
        } else {
            return node.parent;
        }
    }

    /**
     * 搜索对应element对应的节点，如果没有找到则为空
     *
     * @param element 搜索的对象
     * @return
     */
    protected Node<E> searchNode(E element) {
        if (Objects.isNull(element)) {
            return null;
        }
        if (Objects.isNull(root)) {
            return null;
        }
        Node<E> node = root;
        while (node != null) {
            int comp = compareTo(node.element, element);
            if (comp > 0) {
                node = node.left;
            } else if (comp < 0) {
                node = node.right;
            } else {
                return node;
            }
        }
        return null;
    }

    /**
     * who is the root node
     */
    @Override
    public Object root() {
        return root;
    }

    /**
     * how to get the left child of the node
     *
     * @param node
     */
    @Override
    public Object left(Object node) {
        return ((Node<E>) node).left;
    }

    /**
     * how to get the right child of the node
     *
     * @param node
     */
    @Override
    public Object right(Object node) {
        return ((Node<E>) node).right;
    }

    /**
     * how to print the node
     *
     * @param node
     */
    @Override
    public Object string(Object node) {
        return ((Node<E>) node).element.toString();
    }

    /**
     * 节点
     *
     * @param <E> 元素
     */
    private static class Node<E> {
        E element;
        Node<E> left;
        Node<E> right;
        Node<E> parent;

        public Node(E element, Node<E> parent) {
            this.element = element;
            this.parent = parent;
        }

        public Node<E> getLeft() {
            return left;
        }

        public E getElement() {
            return element;
        }

        public void setElement(E element) {
            this.element = element;
        }

        public void setLeft(Node<E> left) {
            this.left = left;
        }

        public Node<E> getRight() {
            return right;
        }

        public void setRight(Node<E> right) {
            this.right = right;
        }

        private boolean hasTwoChild() {
            return this.left != null && this.right != null;
        }

        private boolean isLeft() {
            return this.left == null && this.right == null;
        }
    }

    public static void main(String[] args) {
        Integer[] intArr = {10, 6, 4, 16, 7, 12, 20};
        BinarySearchTree<Integer> tree = new BinarySearchTree<>();
        for (Integer integer : intArr) {
            tree.add(integer);
        }
        BinaryTrees.print(tree);
        tree.remove(10);
        System.out.println("\n -------- 移除数据后 --------");
//        Node<Integer> node = tree.searchNode(12);
//
//        System.out.println(preface.element);
//        System.out.println(tree.size);
        BinaryTrees.print(tree);
//        tree.preOrder(tree.root);

    }
}
