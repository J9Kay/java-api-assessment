package com.cbfacademy.apiassessment.search;

public class LinearSearch {

    /**
     * Performs a linear search on an array of integers.
     *
     * @param arr The array to search in.
     * @param target The value to search for.
     * @return The index of the target value if found, otherwise -1.
     */
    public static int search(String[] arr, String target) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i].equals(target)) {
                return i; // Target found, return its index.
            }
        }
        return -1; // Target not found.
    }

}
