package com.study.java.exercise;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Jeffrey
 * @since 2017/06/14 17:57
 */
public class LeetCode {

    /**
     * 二叉树
     */
    public class TreeNode {

        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

    /**
     * 链表
     */
    public static class ListNode {

        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }

        ListNode(int x, ListNode next) {
            val = x;
            this.next = next;
        }
    }

    /**
     * Given nums = [2, 7, 11, 15], target = 9,
     *
     * Because nums[0] + nums[1] = 2 + 7 = 9,
     * return [0, 1].
     */
    public int[] twoSum(int[] nums, int target) {
        for (int i = 0; i < nums.length - 1; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                if (nums[i] + nums[j] == target) {
                    return new int[]{i, j};
                }
            }
        }
        throw new IllegalArgumentException("No two sum solution");
    }

    public int[] twoSum1(int[] nums, int target) {
        Map<Integer, Integer> map = new HashMap<>(nums.length);
        for (int i = 0; i < nums.length; i++) {
            map.put(nums[i], i);
        }

        for (int i = 0; i < nums.length; i++) {
            int complement = target - nums[i];
            if (map.containsKey(complement) && map.get(complement) != i) {
                return new int[]{i, map.get(complement)};
            }
        }
        throw new IllegalArgumentException("No two sum solution");
    }

    public int[] twoSum2(int[] nums, int target) {
        Map<Integer, Integer> map = new HashMap<>(nums.length);
        for (int i = 0; i < nums.length; i++) {
            int complement = target - nums[i];
            if (map.containsKey(complement)) {
                return new int[]{i, map.get(complement)};
            }
            map.put(nums[i], i);
        }
        throw new IllegalArgumentException("No two sum solution");
    }

    /**
     * Input: (2 -> 4 -> 3) + (5 -> 6 -> 4)
     * Output: 7 -> 0 -> 8
     */
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode root = null;
        ListNode tmp = null;
        int lead = 0;
        while (l1 != null || l2 != null || lead != 0) {
            int val = (l1 != null ? l1.val : 0) + (l2 != null ? l2.val : 0) + lead % 10;
            if (root == null) {
                root = new ListNode(val % 10);
                tmp = root;
            } else {
                tmp.next = new ListNode(val % 10);
                tmp = tmp.next;
            }
            lead = val / 10;
            l1 = l1 != null ? l1.next : null;
            l2 = l2 != null ? l2.next : null;
        }
        return root;
    }

    public ListNode addTwoNumbers1(ListNode l1, ListNode l2) {
        ListNode dummyHead = new ListNode(0);
        ListNode p = l1, q = l2, curr = dummyHead;
        int carry = 0;
        while (p != null || q != null) {
            int x = p != null ? p.val : 0;
            int y = q != null ? q.val : 0;
            int sum = x + y + carry;
            carry = sum / 10;
            curr.next = new ListNode(sum % 10);
            curr = curr.next;
            if (p != null) {
                p = p.next;
            }
            if (q != null) {
                q = q.next;
            }
        }
        if (carry > 0) {
            curr.next = new ListNode(carry);
        }
        return dummyHead.next;
    }

    /**
     * Example1: x = 123, return 321
     * Example2: x = -123, return -321
     */
    private static int reverse(int x) {
        String str = String.valueOf(Math.abs(x));
        StringBuilder sb = new StringBuilder();
        for (int i = str.length() - 1; i >= 0; i--) {
            sb.append(str.charAt(i));
        }
        try {
            return x >= 0 ? Integer.parseInt(sb.toString()) : -Integer.parseInt(sb.toString());
        } catch (Exception e) {
            return 0;
        }
    }

    private static int reverse1(int x) {
        long reverse = 0L;
        while (x != 0) {
            reverse = reverse * 10 + x % 10;
            x = x / 10;
        }
        if (reverse > Integer.MAX_VALUE || reverse < Integer.MIN_VALUE) {
            return 0;
        }
        return (int) reverse;
    }

    /**
     * Determine whether an integer is a palindrome. Do this without extra space.
     */
    public static boolean isPalindrome(int x) {
        if (x < 0) {
            return false;
        }
        long reverse = 0L;
        long origin = x;
        while (x != 0) {
            reverse = 10 * reverse + x % 10;
            x = x / 10;
        }
        if (reverse == origin) {
            return true;
        }
        return false;
    }

    public static boolean isPalindrom(int x) {
        if (x < 0 || (x != 0 && x % 10 == 0)) {
            return false;
        }
        int reverse = 0;
        while (x > reverse) {
            reverse = 10 * reverse + x % 10;
            x = x / 10;
        }
        return (x == reverse) || (x == reverse / 10);
    }

    /**
     * Given a roman numeral, convert it to an integer.
     * Input is guaranteed to be within the range from 1 to 3999.
     */
    public static int romanToInt(String s) {
        Map<Character, Integer> map = new HashMap<Character, Integer>(7) {{
            put('I', 1);
            put('V', 5);
            put('X', 10);
            put('L', 50);
            put('C', 100);
            put('D', 500);
            put('M', 1000);
        }};
        int sum = 0;
        for (int i = 0; i < s.length(); i++) {
            Integer tmp = map.get(s.charAt(i));
            if ((i != s.length() - 1) && (tmp < map.get(s.charAt(i + 1)))) {
                sum -= tmp;
            } else {
                sum += tmp;
            }
        }
        return sum;
    }

    /**
     * Write a function to find the longest common prefix string amongst an array of strings.
     */
    public static String longestCommonPrefix(String[] strs) {
        if (strs == null || strs.length == 0) {
            return "";
        }
        String pre = strs[0];
        int i = 1;
        while (i < strs.length) {
            while (strs[i].indexOf(pre) != 0) {
                pre = pre.substring(0, pre.length() - 1);
                if (pre.isEmpty()) {
                    return "";
                }
            }
            i++;
        }
        return pre;
    }

    /**
     * Merge two sorted linked lists and return it as a new list. The new list should be made by
     * splicing together the nodes of the first two lists.
     */
    public static ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        ListNode result = new ListNode(0);
        ListNode curr = result;
        while (l1 != null && l2 != null) {
            if (Integer.compare(l1.val, l2.val) < 0) {
                curr.next = l1;
                l1 = l1.next;
            } else {
                curr.next = l2;
                l2 = l2.next;
            }
            curr = curr.next;
        }
        curr.next = l1 != null ? l1 : (l2 != null ? l2 : null);
        return result.next;
    }

    public static ListNode mergeTwoLists1(ListNode l1, ListNode l2) {
        if (l1 == null) {
            return l2;
        }
        if (l2 == null) {
            return l1;
        }
        ListNode listHead;
        // 递归
        if (l1.val < l2.val) {
            listHead = l1;
            listHead.next = mergeTwoLists1(l1.next, l2);
        } else {
            listHead = l2;
            listHead.next = mergeTwoLists1(l2.next, l1);
        }
        return listHead;
    }

    /**
     * Total Accepted: 229583 Total Submissions: 645464 Difficulty: Easy Contributor: LeetCode Given
     * a sorted array, remove the duplicates in place such that each element appear only once and
     * return the new length.
     *
     * Do not allocate extra space for another array, you must do this in place with constant
     * memory.
     */
    public static int removeDuplicates(int[] nums) {
        int len = 0;
        for (int num : nums) {
            if (len == 0 || num > nums[len - 1]) {
                nums[len++] = num;
            }
        }
        return len;
    }

    /**
     * Given an array and a value, remove all instances of that value in place and return the new
     * length. Do not allocate extra space for another array, you must do this in place with
     * constant memory. The order of elements can be changed. It doesn't matter what you leave
     * beyond the new length.
     */
    public int removeElement(int[] nums, int val) {
        int len = 0;
        for (int num : nums) {
            if (num != val) {
                nums[len++] = num;
            }
        }
        return len;
    }

    /**
     * Returns the index of the first occurrence of needle in haystack, or -1 if needle is not part
     * of haystack.
     */
    public int strStr(String haystack, String needle) {
        return haystack.indexOf(needle);
    }

    public int strStr1(String haystack, String needle) {
        for (int i = 0; ; i++) {
            for (int j = 0; ; j++) {
                if (j == needle.length()) {
                    return i;
                }
                if (i + j == haystack.length()) {
                    return -1;
                }
                if (needle.charAt(j) != haystack.charAt(i + j)) {
                    break;
                }
            }
        }
    }

    /**
     * Given a sorted array and a target value, return the index if the target is found. If not,
     * return the index where it would be if it were inserted in order.
     */
    public int searchInsert(int[] nums, int target) {
        for (int i = 0; i < nums.length; i++) {
            if (target <= nums[i]) {
                return i;
            }
        }
        return nums.length;
    }

    public int searchInsert1(int[] nums, int target) {
        int low = 0, high = nums.length - 1;
        while (low <= high) {
            int mid = (low + high) / 2;
            if (target < nums[mid]) {
                high = --mid;
            } else if (target > nums[mid]) {
                low = ++mid;
            } else {
                return mid;
            }
        }
        return low;
    }

    /**
     * Find the contiguous subarray within an array (containing at least one number) which has the
     * largest sum.
     *
     * For example, given the array [-2,1,-3,4,-1,2,1,-5,4], the contiguous subarray [4,-1,2,1] has
     * the largest sum = 6.
     */
    public int maxSubArray(int[] nums) {
        int maxSoFar = nums[0], maxEndingHere = nums[0];
        for (int i = 1; i < nums.length; i++) {
            maxEndingHere = Math.max(maxEndingHere + nums[i], nums[i]);
            maxSoFar = Math.max(maxEndingHere, maxSoFar);
        }
        return maxSoFar;
    }

    public int maxSubArray1(int[] A) {
        int n = A.length;
        int[] dp = new int[n];//dp[i] means the maximum subarray ending with A[i];
        dp[0] = A[0];
        int max = dp[0];

        for (int i = 1; i < n; i++) {
            dp[i] = A[i] + (dp[i - 1] > 0 ? dp[i - 1] : 0);
            max = Math.max(max, dp[i]);
        }

        return max;
    }

    /**
     * Given a string s consists of upper/lower-case alphabets and empty space characters ' ',
     * return the length of last word in the string.
     *
     * If the last word does not exist, return 0.
     */
    public static int lengthOfLastWord(String s) {
        if (s.trim().isEmpty()) {
            return 0;
        }
        String[] strs = s.split(" ");
        return strs[strs.length - 1].length();
    }

    public static int lengthOfLastWord1(String s) {
        int len = 0;
        boolean isWord = false;
        for (int i = s.length() - 1; i >= 0; i--) {
            if (!isWord && s.charAt(i) == ' ') {
                continue;
            } else {
                isWord = true;
            }
            if (s.charAt(i) == ' ') {
                return len;
            }
            len++;
        }
        return len;
    }

    public static int lengthOfLastWord2(String s) {
        return s.trim().length() - s.trim().lastIndexOf(" ") - 1;
    }

    /**
     * Given a non-negative integer represented as a non-empty array of digits, plus one to the
     * integer.
     * You may assume the integer do not contain any leading zero, except the number 0 itself.
     * The digits are stored such that the most significant digit is at the head of the list.
     */
    public static int[] plusOne(int[] digits) {
        int len = digits.length;
        for (int i = len - 1; i >= 0; i--) {
            if (digits[i] < 9) {
                digits[i] += 1;
                return digits;
            }
            digits[i] = 0;
        }
        int[] newDigits = new int[len + 1];
        newDigits[0] = 1;
        return newDigits;
    }

    /**
     * Given two binary strings, return their sum (also a binary string).
     *
     * For example,
     * a = "11"
     * b = "1"
     * Return "100".
     */
    public String addBinary(String a, String b) {
        return Integer.toBinaryString(Integer.parseInt(a, 2) + Integer.parseInt(b, 2)); //  溢出
    }

    public static String addBinary1(String a, String b) {
        StringBuilder result = new StringBuilder();
        int carry = 0;
        int maxLen = Math.max(a.length(), b.length());
        for (int i = 0; i < maxLen; i++) {
            int sum = carry;
            if (i < a.length()) {
                sum += a.charAt(a.length() - i - 1) - '0';
            }
            if (i < b.length()) {
                sum += b.charAt(b.length() - i - 1) - '0';
            }
            result.append(sum % 2);
            carry = sum / 2;
        }
        return carry > 0 ? result.append(1).reverse().toString() : result.reverse().toString();
    }

    /**
     * Implement int sqrt(int x).
     * Compute and return the square root of x.
     */
    public static int mySqrt(int x) {
        return (int) Math.sqrt(x);
    }

    public static int mySqrt1(int x) {
        if (x == 0) {
            return 0;
        }
        int left = 1, right = x;
        while (true) {
            int mid = (left + right) / 2;
            if (mid < x / mid) {
                right = mid - 1;
            } else {
                if ((mid + 1) > x / (mid + 1)) {
                    return mid;
                }
                left = mid + 1;
            }
        }
    }

    /**
     * You are climbing a stair case. It takes n steps to reach to the top.
     * Each time you can either climb 1 or 2 steps. In how many distinct ways can you climb to the
     * top?
     */
    public static int climbStairs(int n) {
        return climbStairs(0, n);
    }

    public static int climbStairs(int i, int n) {
        if (i > n) {
            return 0;
        }
        if (i == n) {
            return 1;
        }
        return climbStairs(1 + i, n) + climbStairs(i + 2, n);
    }

    /**
     * Given two sorted integer arrays nums1 and nums2, merge nums2 into nums1 as one sorted array.
     */
    public static void merge(int[] nums1, int m, int[] nums2, int n) {
        int i = m - 1, j = n - 1, k = m + n - 1;
        while (i >= 0 && j >= 0) {
            if (nums1[i] > nums2[j]) {
                nums1[k--] = nums1[i--];
            } else {
                nums1[k--] = nums2[j--];
            }
        }
        while (j >= 0) {
            nums1[k--] = nums2[j--];
        }
    }

    public static void merge1(int[] nums1, int m, int[] nums2, int n) {
        while (n > 0) {
            nums1[m + n - 1] = (m == 0 || nums1[m - 1] < nums2[n - 1]) ? nums2[--n] : nums1[--m];
        }
    }

    /**
     * Given a sorted linked list, delete all duplicates such that each element appear only once.
     *
     * For example,
     * Given 1->1->2, return 1->2.
     * Given 1->1->2->3->3, return 1->2->3.
     */
    public static ListNode deleteDuplicates(ListNode head) {
        ListNode tmpListNode = head;
        while (tmpListNode != null && tmpListNode.next != null) {
            if (tmpListNode.val != tmpListNode.next.val) {
                tmpListNode = tmpListNode.next;
            } else {
                tmpListNode.next = tmpListNode.next.next;
            }
        }
        return head;
    }

    /**
     * Given a string, find the length of the longest substring without repeating characters.
     *
     * Examples:
     *
     * Given "abcabcbb", the answer is "abc", which the length is 3.
     *
     * Given "bbbbb", the answer is "b", with the length of 1.
     *
     * Given "pwwkew", the answer is "wke", with the length of 3. Note that the answer must be a
     * substring, "pwke" is a subsequence and not a substring.
     */
    public static int lengthOfLongestSubstring(String s) {
        String result = "";
        for (int i = 0; i < s.length(); i++) {
            StringBuilder tmp = new StringBuilder();
            for (int j = i; j < s.length(); j++) {
                char ch = s.charAt(j);
                if (tmp.toString().contains(ch + "")) {
                    break;
                }
                tmp.append(ch);
            }
            if (tmp.length() > result.length()) {
                result = tmp.toString();
            }
        }
        return result.length();
    }

    public static int lengthOfLongestSubstring1(String s) {
        if (s.length() == 0) {
            return 0;
        }
        Map<Character, Integer> map = new HashMap<>(s.length());
        int max = 0;
        int flag = 0;
        for (int i = 0; i < s.length(); i++) {
            int before = map.getOrDefault(s.charAt(i), -1);
            if (before != -1) {
                int gap = i - flag;
                max = Math.max(max, gap);
                for (int j = flag; j < before; j++) {
                    map.remove(s.charAt(j));
                }
                flag = before + 1;
            }
            map.put(s.charAt(i), i);
        }
        max = Math.max(s.length() - flag, max);
        return max == 0 ? s.length() : max;
    }

    public static int lengthOfLongestSubstring2(String s) {
        Map<Character, Integer> map = new HashMap<>(s.length());
        int max = 0;
        for (int i = 0, j = 0; j < s.length(); j++) {
            if (map.containsKey(s.charAt(j))) {
                i = Math.max(map.get(s.charAt(j)), i);
            }
            max = Math.max(max, j - i + 1);
            map.put(s.charAt(j), j + 1);    //  skip
        }
        return max;
    }

    /**
     * Given a string s, find the longest palindromic substring in s. You may assume that the
     * maximum length of s is 1000.
     *
     * Example:
     * Input: "babad"
     * Output: "bab"
     * Note: "aba" is also a valid answer.
     */
    public static String longestPalindrome(String s) {
        // TODO
        return null;
    }

    /**
     * There are two sorted arrays nums1 and nums2 of size m and n respectively.
     *
     * Find the median of the two sorted arrays. The overall run time complexity should be O(log
     * (m+n)).
     */
    public static double findMedianSortedArrays(int[] nums1, int[] nums2) {
        // TODO
        return 1.0;
    }

    /**
     * main
     */
    public static void main(String[] args) {
        System.out.println(longestPalindrome("babad"));
    }
}
