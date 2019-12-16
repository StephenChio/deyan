package com.OneTech.web.test;

import com.alibaba.fastjson.JSONObject;

import java.lang.reflect.Array;
import java.util.*;

public class Solution {
//    public static ArrayList<Integer> printMatrix(int[][] matrix) {
//        ArrayList<Integer> arrayList = new ArrayList<Integer>();
//        int row = matrix.length;
//        int col = matrix[0].length;
//        Set<Integer> test = new TreeSet<Integer>();
//        for (int i = 0; i < row * col; i++) {
//            //TODO
//        }
//        return arrayList;
//    }

    public static ListNode Merge(ListNode list1, ListNode list2) {
        if (list1 == null) return list2;
        if (list2 == null) return list1;
        if (list1 == null && list2 == null) return null;
        Queue<ListNode> queue = new LinkedList<>();
        ListNode head = list1;
        while (head != null) {
            queue.add(head);
            head = head.next;
        }
        head = list2;
        while (!queue.isEmpty()) {
            ListNode node = queue.poll();
            if (head.val > node.val) {
                node.next = head;
                head = node;
            }
            boolean flag = false;
            ListNode nextNode = head;
            while (nextNode.next != null) {
                if (nextNode.next.val > node.val) {
                    ListNode next = nextNode.next;
                    nextNode.next = node;
                    node.next = next;
                    flag = true;
                    break;
                }
                nextNode = nextNode.next;
            }
            if (!flag) {
                nextNode.next = node;
                break;
            }
        }
        return head;
    }

    public static ListNode ReverseList(ListNode head) {
        if (head == null) return null;
        Stack<ListNode> stack = new Stack<>();
        while (head != null) {
            stack.add(head);
            head = head.next;
        }
        ListNode newNode = stack.pop();
        ;
        ListNode next = newNode;
        while (!stack.isEmpty()) {
            ListNode node = stack.pop();
            node.next = null;
            next.next = node;
            next = node;
        }
        return newNode;
    }

    public ListNode FindFirstCommonNode(ListNode pHead1, ListNode pHead2) {
        ListNode current1 = pHead1;
        ListNode current2 = pHead2;
        HashMap<ListNode, Integer> hashMap = new HashMap<ListNode, Integer>();
        while (current1 != null) {
            hashMap.put(current1, null);
            current1 = current1.next;
        }
        while (current2 != null) {
            if (hashMap.containsKey(current2))
                return current2;
            current2 = current2.next;
        }

        return null;

    }

    public int GetNumberOfK(int[] array, int k) {
        Arrays.sort(array);
        int time = 0;
        for (int i = 0; i < array.length; i++) {
            if (array[i] > k) {
                break;
            }
            if (array[i] == k) {
                time = time + 1;
            }
        }
        return time;
    }


    public static ArrayList<Integer> FindNumbersWithSum(int[] array, int sum) {
        ArrayList<Integer> list = new ArrayList<>();
        int max = 0;
        for (int i = 0; i < array.length; i++) {
            for (int j = i; j < array.length; j++) {
                if (array[i] + array[j] == sum) {
                    if (max == 0) {
                        max = array[i] * array[j];
                        list.clear();
                        list.add(array[i]);
                        list.add(array[j]);
                    }
                    if (array[i] * array[j] < max) {
                        max = array[i] * array[j];
                        list.clear();
                        list.add(array[i]);
                        list.add(array[j]);
                    }
                    ;

                }
            }
        }
        return list;
    }

    public static String LeftRotateString(String str, int n) {
        if (n > str.length()) return "";
        return str.substring(n, str.length()) + str.substring(0, n);
    }


    public static String ReverseSentence(String str) {
        int length = str.length();
        if (str.length() <= 1) return str;
        Stack<String> stack = new Stack<>();
        String[] newStr = str.split(" ");
        for (String s : newStr) {
            stack.push(s);
        }
        String returnStr = stack.pop();
        while (!stack.isEmpty()) {
            returnStr = returnStr + " " + stack.pop();
        }
        return returnStr;
    }


    public static ListNode deleteDuplication(ListNode pHead) {
        if (pHead == null) return null;
        if (pHead.next == null) return pHead;
        ListNode node = pHead;
        ListNode next = node;
        ListNode returnNode = new ListNode(1);
        ListNode point = returnNode;
        boolean flag = false;
        while (next != null) {
            if (next.next == null) {
                point.next = next;
                flag = true;
                break;
            } else {
                if (next.val != next.next.val) {
                    point.next = next;
                    point = next;
                    flag = true;
                } else {
                    while (next.next != null && (next.val == next.next.val)) {
                        next = next.next;
                    }
                    if (next.next == null) {
                        point.next = null;
                    }
                }
                next = next.next;
            }
        }
        if (!flag) {
            return null;
        }
        return returnNode.next;
    }


    public static ArrayList<ArrayList<Integer>> Print1(TreeNode pRoot) {
        ArrayList<ArrayList<Integer>> List = new ArrayList<>();
        if (pRoot == null) return List;
        Queue<TreeNode> queue = new LinkedList<>();
        Queue<Integer> deepList = new LinkedList<>();
        queue.add(pRoot);
        deepList.add(0);
        int nowDeep = 0;
        HashMap<Integer, ArrayList<Integer>> map = new HashMap<>();
        ArrayList<Integer> list = new ArrayList<>();
        map.put(nowDeep, list);
        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();
            int deep = deepList.poll();
            if (map.get(deep) == null) {
                list = new ArrayList<>();
                list.add(node.val);
                map.put(deep, list);
            } else {
                list = map.get(deep);
                list.add(node.val);
            }
            if (node.left != null) {
                queue.add(node.left);
                deepList.add(deep + 1);
            }
            if (node.right != null) {
                queue.add(node.right);
                deepList.add(deep + 1);
            }
        }
        for (ArrayList<Integer> arrayList : map.values()) {
            List.add(arrayList);
        }
        return List;
    }


    static String Serialize(TreeNode root) {
        if (root == null) return null;
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        String returnStr = "";
        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();
            if (node.val == -12333) {
                returnStr = returnStr + "#!";
            } else {
                returnStr = returnStr + node.val + "!";
                if (node.left != null) {
                    queue.add(node.left);
                } else {
                    node.left = new TreeNode(-12333);
                    queue.add(node.left);
                }
                if (node.right != null) {
                    queue.add(node.right);
                } else {
                    node.right = new TreeNode(-12333);
                    queue.add(node.right);
                }
            }
        }
        return returnStr;
    }


    static TreeNode Deserialize(String str) {
        if (str == null) return null;
        String[] strings = str.split("!");
        Queue<TreeNode> queue = new LinkedList<>();
        Queue<String> stringQueue = new LinkedList<>();
        for (String s : strings) {
            stringQueue.add(s);
        }
        TreeNode treeNode = new TreeNode(Integer.valueOf(stringQueue.poll()));
        queue.add(treeNode);
        while (!stringQueue.isEmpty()) {
//            String val = stringQueue.poll();
            TreeNode node = queue.poll();

            String left = stringQueue.poll();
            if (left.equals("#")) {
                node.left = null;
            } else {
                node.left = new TreeNode(Integer.valueOf(left));
                queue.add(node.left);
            }
            String right = stringQueue.poll();
            if (right.equals("#")) {
                node.right = null;
            } else {
                node.right = new TreeNode(Integer.valueOf(right));
                queue.add(node.right);
            }

        }
        return treeNode;
    }

    public boolean IsBalanced_Solution(TreeNode root) {
        if (root == null) return true;
        if (root.left == null && root.right != null) {
            if (deep(root.right) > 1) return false;
        } else if (root.left != null && root.right == null) {
            if (deep(root.left) > 1) return false;
        } else if (root.left != null && root.right != null) {
            int leftDeep = deep(root.left);
            int rightDeep = deep(root.right);
            if (Math.max(leftDeep, rightDeep) - Math.min(leftDeep, rightDeep) > 1) return false;
        }
        return true;
    }

    public int deep(TreeNode root) {
        if (root == null) return 0;
        if (root.left != null && root.right == null) {
            return deep(root.left) + 1;
        } else if (root.left == null && root.right != null) {
            return deep(root.right) + 1;
        } else {
            return Math.max(deep(root.left), deep(root.right)) + 1;
        }
    }


    TreeNode KthNode(TreeNode pRoot, int k) {
        if (pRoot == null) return null;
        HashMap<Integer, TreeNode> map = new HashMap<>();
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(pRoot);
        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();
            map.put(node.val, node);
            if (node.left != null) queue.add(node.left);
            if (node.right != null) queue.add(node.right);
        }
        int i = 1;
        TreeNode kthNode = null;
        for (TreeNode treeNode : map.values()) {
            if (i == k) {
                kthNode = treeNode;
            }
            i = i + 1;
        }
        return kthNode;
    }


    public static boolean hasPath(char[] matrix, int rows, int cols, char[] str) {
        if (matrix == null) return false;
        if (str.length > matrix.length) return false;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (matrix[cols * i + j] == str[0]) {
                    int[][] array = new int[rows][cols];
                    for (int m = 0; m < rows; m++) {
                        for (int n = 0; n < cols; n++) {
                            array[m][n] = 0;
                        }
                    }
                    if (start(matrix, rows, cols, str, i, j, array)) return true;
                }
            }
        }
        return false;
    }

    public static boolean start(char[] matrix, int rows, int cols, char[] str, int rowsPoint, int colsPoint, int[][] array) {
        if (array[rowsPoint][colsPoint] == 1) return false;
        if (matrix[cols * rowsPoint + colsPoint] == str[0]) {
            char[] res = str;
            array[rowsPoint][colsPoint] = 1;
            if (str.length == 1) {
                return true;
            } else {
                res = new char[str.length - 1];
                for (int i = 0; i < res.length; i++) {
                    res[i] = str[i + 1];
                }
            }
            if (res.length != 0) {
                if (colsPoint - 1 >= 0) {//左
                    if (start(matrix, rows, cols, res, rowsPoint, colsPoint - 1, array)) return true;
                }
                if (colsPoint + 1 < cols) {//右
                    if (start(matrix, rows, cols, res, rowsPoint, colsPoint + 1, array)) return true;
                }
                if (rowsPoint - 1 >= 0) {//上
                    if (start(matrix, rows, cols, res, rowsPoint - 1, colsPoint, array)) return true;
                }
                if (rowsPoint + 1 < rows) {//下
                    if (start(matrix, rows, cols, res, rowsPoint + 1, colsPoint, array)) return true;
                }
            }
        }
        return false;
    }


    public static int GetUglyNumber_Solution(int index) {
        if (index <= 0) return 0;
        if (index < 7) return index;
        int[] res = new int[index];
        int t2 = 0;
        int t3 = 0;
        int t5 = 0;
        res[0] = 1;
        int temp = 0;
        for (int k = 1; k < index; k++) {
            temp = Math.min(res[t2] * 2, Math.min(res[t3] * 3, res[t5] * 5));
            if (temp == res[t2] * 2) t2++;
            if (temp == res[t3] * 3) t3++;
            if (temp == res[t5] * 5) t5++;
            res[k] = temp;
        }

        return res[index - 1];
    }

    static int FirstNotRepeatingChar(String str) {
        int[] words = new int[58];
        for (int i = 0; i < str.length(); i++) {
            words[((int) str.charAt(i)) - 65] += 1;
        }
        for (int i = 0; i < str.length(); i++) {
            if (words[((int) str.charAt(i)) - 65] == 1)
                return i;
        }
        return -1;
    }


    public static int[] multiply(int[] A) {
        int[] B = new int[A.length];
        for (int i = 0; i < B.length; i++) {
            B[i] = x(A, i);
        }
        return B;
    }

    public static int x(int[] A, int n) {
        int num = 1;
        for (int i = 0; i < A.length; i++) {
            if (i != n) {
                num = num * A[i];
            }
        }
        return num;
    }

    /**
     * @param pRoot
     * @return
     */
    public ArrayList<ArrayList<Integer>> Print(TreeNode pRoot) {
        ArrayList<ArrayList<Integer>> List = new ArrayList<>();
        Stack<Integer> deepList = new Stack<>();
        Stack<TreeNode> stack = new Stack<>();
        stack.add(pRoot);
        int nowDeep = 0;
        deepList.add(nowDeep);
        HashMap<Integer, ArrayList<Integer>> map = new HashMap<>();
        ArrayList<Integer> list = new ArrayList<>();
        map.put(nowDeep, list);
        while (!stack.isEmpty()) {
            TreeNode node = stack.pop();
            int deep = deepList.pop();
            if (map.get(deep) == null) {
                list = new ArrayList<>();
                list.add(node.val);
                map.put(deep, list);
            } else {
                list = map.get(deep);
                list.add(node.val);
            }
            if (node.left != null) {
                stack.add(node.left);
                deepList.add(deep + 1);
            }
            if (node.right != null) {
                stack.add(node.right);
                deepList.add(deep + 1);
            }
        }
        for (ArrayList<Integer> arrayList : map.values()) {
            List.add(arrayList);
        }
        return List;
    }


    public static ArrayList<Integer> printMatrix(int[][] matrix) {
        int[][] array = new int[matrix.length][matrix[0].length];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                array[i][j] = 0;
            }
        }
        ArrayList<Integer> list = new ArrayList<>();
        return startPrint(matrix, 0, 0, array, list);

    }

    public static ArrayList<Integer> startPrint(int[][] matrix, int rows, int cols, int[][] array, ArrayList<Integer> list) {
        array[rows][cols] = 1;
        list.add(matrix[rows][cols]);
        if (cols < matrix[0].length - 1 && array[rows][cols + 1] != 1) {//右边
            return startPrint(matrix, rows, cols + 1, array, list);
        } else if (rows < matrix.length - 1 && array[rows + 1][cols] != 1) {//下边
            return startPrint(matrix, rows + 1, cols, array, list);
        } else if (cols - 1 >= 0 && array[rows][cols - 1] != 1) {//左边
            return startPrint(matrix, rows, cols - 1, array, list);
        } else if (rows - 1 >= 0 && array[rows - 1][cols] != 1) {//上边
            return startPrint(matrix, rows - 1, cols, array, list);
        }
        return list;
    }


    public static ArrayList<ArrayList<Integer>> FindPath(TreeNode root, int target) {
        ArrayList<ArrayList<Integer>> arr = new ArrayList<ArrayList<Integer>>();
        if (root == null)
            return arr;
        ArrayList<Integer> a1 = new ArrayList<Integer>();
        int sum = 0;
        searchPath(root, target, arr, a1, sum);
        return arr;
    }

    public static void searchPath(TreeNode root, int target, ArrayList<ArrayList<Integer>> ArrayList, ArrayList<Integer> list, int sum) {
        if (root == null)
            return;
        sum += root.val;

        if (root.left == null && root.right == null) {
            if (sum == target) {
                list.add(root.val);
                ArrayList.add(new ArrayList<Integer>(list));
                list.remove(list.size() - 1);

            }
            return;

        }

        list.add(root.val);
        searchPath(root.left, target, ArrayList, list, sum);
        searchPath(root.right, target, ArrayList, list, sum);
        list.remove(list.size() - 1);

    }

    public int run(TreeNode root) {
        if (root == null) return 0;
        if (root.left != null && root.right == null) return 1 + run(root.left);
        if (root.left == null && root.right != null) return 1 + run(root.right);
        return Math.min(run(root.left), run(root.right)) + 1;
    }

    public int evalRPN(String[] tokens) {
        Stack<Integer> stack = new Stack();
        for (String str : tokens) {
            if (str.equals("+")) stack.add(stack.pop() + stack.pop());
            else if (str.equals("-")) stack.add(stack.pop() - stack.pop());
            else if (str.equals("*")) stack.add(stack.pop() * stack.pop());
            else if (str.equals("/")) stack.add(stack.pop() / stack.pop());
            else {
                stack.add(Integer.valueOf(str));
            }
        }
        return stack.pop();
    }


    public static int maxPoints(Point[] points) {
        if (points.length <= 2) return points.length;
        int num = 0;
        for (int i = 0; i < points.length-1; i++) {
            for (int j = i + 1; j < points.length; j++) {
                num = Math.max(num, findPoint(points, i, j));
            }
        }
        return num+2;
    }

    public static int findPoint(Point[] points, int point1, int point2) {
        int num = 0;
        for (int i = 0; i < points.length; i++) {
                if (i != point1 && i != point2) {
                    if (judge(points[point1], points[i], points[point2]))
                        num = num + 1;
                }
            }
        return num;
    }

    public static boolean judge(Point point1, Point point2, Point point3) {
        double A = distance(point1,point2);
        double B = distance(point2,point3);
        double C = distance(point1,point3);
        double p=0.5*(A+B+C);
        if (p*(p-A)*(p-B)*(p-C)>0){
            return false;
        } else {
            return true;
        }
    }
    public static double distance(Point point1, Point point2) {
        int x1 = point1.x - point2.x;
        int y1 = point1.y - point2.y;
        return Math.sqrt(x1 * x1 + y1 * y1);
    }


    public ListNode sortList(ListNode head) {
        return new ListNode(1);
    }


    public static void main(String arg[]) {
        ListNode list1 = new ListNode(1);
        list1.next = new ListNode(1);
        list1.next.next = new ListNode(2);
        list1.next.next.next = new ListNode(3);
        list1.next.next.next.next = new ListNode(3);
        list1.next.next.next.next.next = new ListNode(4);
        list1.next.next.next.next.next.next = new ListNode(5);
        list1.next.next.next.next.next.next.next = new ListNode(5);
        int[] array = {1, 2, 4, 7, 11, 15};


        TreeNode node = new TreeNode(8);
        node.left = new TreeNode(6);

        node.left.left = new TreeNode(5);
        node.left.right = new TreeNode(7);

        char[] array2 = {'A', 'B', 'C', 'E', 'H', 'J', 'I', 'G', 'S', 'F', 'C', 'S', 'L', 'O', 'P', 'Q', 'A', 'D', 'E', 'E', 'M', 'N', 'O', 'E', 'A', 'D', 'I', 'D', 'E', 'J', 'F', 'M', 'V', 'C', 'E', 'I', 'F', 'G', 'G', 'S'};
        char[] chars = {'S', 'L', 'H', 'E', 'C', 'C', 'E', 'I', 'D', 'E', 'J', 'F', 'G', 'G', 'F', 'I', 'E'};
        String str = "zabbbbbac";
        int[][] matrix = {{1}};
        Point point1 = new Point(84, 250);
        Point point2 = new Point(0, 0);
        Point point3 = new Point(1, 0);
        Point[] points = new Point[9];
        points[0] = point1;
        points[1] = point2;
        points[2] = point3;
        points[3] = new Point(0,-70);
        points[4] = new Point(0,-70);
        points[5] = new Point(1,-1);
        points[6] = new Point(21,10);
        points[7] = new Point(42,90);
        points[8] = new Point(-42,-230);

        System.out.println(Solution.maxPoints(points));
    }
}