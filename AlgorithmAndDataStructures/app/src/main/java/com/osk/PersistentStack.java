package com.osk;

class PersistentStack<T> {

    private Node top = null;

    T pop() {
        T data = top.getData();
        top = top.next;
        return data;
    }

    void push(T data) {
        Node node = new Node(data, top);
        top = node;
    }

    class Node {
        private T data;
        private Node next;

        public Node(T data, Node next) {
            this.data = data;
            this.next = next;
        }

        public T getData() {
            return data;
        }

        public void setData(T data) {
            this.data = data;
        }

        public Node getNext() {
            return next;
        }

        public void setNext(Node next) {
            this.next = next;
        }
    }

    private String getData() {
        StringBuilder sb = new StringBuilder(top.getData().toString());
        Node nextNode = top.next;
        while(nextNode != null) {
            sb.append(", ");
            sb.append(nextNode.getData().toString());
            nextNode = nextNode.next;
        }
        return sb.toString();
    }

    @Override
    public String toString() {
        return "PersistentStack{" +
                getData() +
                '}';
    }
}
