package com.cbfacademy.apiassessment.search;

public class LinearSearch {

    /**
     * Performs a linear search on an array of integers.
     *
     * @param arr The array to search in.
     * @param target The value to search for.
     * @return The index of the target value if found, otherwise -1.
     */
    public static int search(int[] arr, int target) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == target) {
                return i; // Target found, return its index.
            }
        }
        return -1; // Target not found.
    }

    public static void main(String[] args) {
        int[] data = {3, 45, 1, 2, 99, 5, 78, 56}; // Example array
        int target = 99; // Value to search for

        int result = search(data, target);
        if (result == -1) {
            System.out.println(target + " not found in the array.");
        } else {
            System.out.println(target + " found at index: " + result);
        }
    }
}
