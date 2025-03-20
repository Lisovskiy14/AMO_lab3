package com.example.amo_lab3.logic.range;

public class RangeCalculator {
    private int a;
    private int b;

    public RangeCalculator(int a, int b) {
        this.a = a;
        this.b = b;
    }

    public float[] getRange(int range) {
        float[] result = new float[range + 1];
        float h = (float) (b - a) / (float) range;
        for (int i = 0; i <= range; i++) {
            result[i] = a + i * h;
            System.out.println(result[i]);
        }

        return result;
    }
}
