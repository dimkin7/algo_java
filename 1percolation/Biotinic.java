public class Biotinic {

    // find index of max element
    static int searchMax(int[] arr) {
        int minIndex = 0;
        int maxIndex = arr.length - 1;
        int index = minIndex + ((maxIndex - minIndex) / 2);

        while (!(arr[index - 1] < arr[index] && arr[index] > arr[index + 1])) {
            index = minIndex + (maxIndex - minIndex) / 2;

            if (arr[index - 1] < arr[index] && arr[index] < arr[index + 1]) {
                minIndex = index + 1;
            }
            else if (arr[index - 1] > arr[index] && arr[index] > arr[index + 1]) {
                maxIndex = index - 1;
            }
        }
        return index;
    }

    // TODO - this is for acsending order
    static int searhBinary(int[] arr, int el, int from, int to) {

        int index = 0;
        while (from < to) {
            index = from + ((to - from) / 2);
            if (arr[index] < el) {
                to = index - 1;
            }
            else if (arr[index] > el) {
                from = index + 1;
            }
            else
                return index;
        }
        if (arr[index] == el)
            return index;

        return -1;
    }

    static int search(int[] arr, int el) {
        if (arr.length < 3) {
            return -1;
        }

        // find index of max element
        int indexMax = searchMax(arr);
        // If the element to be searched is greater than the maximum element return -1,
        if (arr[indexMax] < el) {
            return -1;
        }
        if (arr[indexMax] == el) {
            return indexMax;
        }

        // else search the element in both halves.
        int index = searhBinary(arr, el, 0, indexMax - 1);
        if (index == -1) {
            index = searhBinary(arr, el, indexMax + 1, arr.length - 1);
        }

        return index;
    }


    public static void main(String[] args) {
        int[] arr1 = { 5, 6, 7, 8, 9, 10, 3, 2, 1 };
        int[] arr2 = { -3, 9, 18, 20, 17, 5, 1 };
        int res;

        res = search(arr1, 20);
        System.out.println(res);
        res = search(arr1, 2);
        System.out.println(res);
        res = search(arr2, 20);
        System.out.println(res);
        res = search(arr2, 2);
        System.out.println(res);
    }
}
