package evenrunners;

public class Sort {

    //sorts values from biggest to lowest
    public double[] sort(double[] unsorted) {

        for (int i = 1; i < unsorted.length; i++) {
            double value = unsorted[i];
            int j = i - 1;

            while (j >= 0 && unsorted[j] < value) {
                unsorted[j + 1] = unsorted[j];
                j = j - 1;
            }
            unsorted[j + 1] = value;
        }

        return unsorted;
    }
}
