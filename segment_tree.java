import java.util.Arrays;

public class segment_tree {
    public static void main(String[] args) {
        SegmentTree s = new SegmentTree(new int[]{1, 2, 3, 4});
        s.root = s.build(0, s.arr.length, s.arr);
        System.out.println(s.rangeSum(s.root, 1, 2));
    }
    public static void dfs(Node root) {
        if (root == null) return;
        System.out.println("Node: " + root.val + ", [" + root.s + ":" + root.e + "]");
        dfs(root.left);
        dfs(root.right);
    }
}

class Node {
    int s;//inclusive
    int e;//inclusive
    int val;
    Node left;
    Node right;
}

class SegmentTree {
    Node root;
    int[] arr;

    SegmentTree(int [] arr) {
        this.arr = arr;
    }

    public Node build(int start, int end, int[] arr) {
        Node temp = new Node();
        if (arr.length == 1) {
            temp.val = arr[0];
            temp.s = start;
            temp.e = end-1;
        } else if (arr.length == 0 || start > end || start < 0 || end < 0) {
            return new Node();// may be better
        } else {
            temp.left = build(start, (start+end)/2 + (arr.length % 2 == 1 ? 1 : 0), Arrays.copyOfRange(arr, 0, arr.length/2 + (arr.length % 2 == 1 ? 1 : 0)));
            temp.right = build((start+end)/2 + (arr.length % 2 == 1 ? 1 : 0), end, Arrays.copyOfRange(arr, arr.length/2 + (arr.length % 2 == 1 ? 1 : 0), arr.length));
            temp.val = temp.left.val + temp.right.val;
            temp.s = start;
            temp.e = end-1;
        }
        return temp;
    }
    public int rangeSum(Node node, int l, int r) {
        if(node == null)
        {
            //Range is completely outside the given range
            return 0;
        }
        if(l <= node.s && node.e <= r)
        {
            //Range is completely inside the given range
            return node.val;
        }
        //Range is partially inside and partially outside the given range
        int mid = (node.s + node.e) / 2;
        int left = 0;
        int right = 0;
        if (l <= mid) {
            left = rangeSum(node.left, l, r);
        } 
        if (r >= mid) {
            right = rangeSum(node.right, l, r);
        }
        return (left + right);
    }
    public void update(Node root, int value, int idx, int oldvalue) {
        if (root == null) {
            this.arr[idx] = value;
            return;
        }
        int mid = (root.e + root.s) / 2;
        if (idx <= root.e && idx >= root.s) {
            root.val -= oldvalue;
            root.val += value;
        } if (idx > mid) {
            update(root.right, value, idx, oldvalue);
        } else if (idx <= mid) {
            update(root.left, value, idx, oldvalue);
        }
    }
}