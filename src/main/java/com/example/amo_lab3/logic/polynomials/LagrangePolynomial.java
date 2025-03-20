package com.example.amo_lab3.logic.polynomials;

public class LagrangePolynomial implements Polynomial {
    private float[] arrayX;
    private float[] arrayY;

    @Override
    public void setArrays(float[] arrayX, float[] arrayY) {
        this.arrayX = arrayX;
        this.arrayY = arrayY;
    }

    @Override
    public float calculatePolynomial(float x) {
        validateArrays();

        float sum = 0;
        float k = arrayX[0];
        float jmax = k + arrayX.length;
        for (float j = k; j < jmax; j++) {
            float mult = 1;
            float imax = k + arrayX.length;
            for (float i = k; i < imax; i++) {
                if (i != j) {
                    float num = x - arrayX[(int) (i - k)];
                    float den = arrayX[(int) (j - k)] - arrayX[(int) (i - k)];
                    mult *= num / den;
                }
            }
            sum += arrayY[(int) (j - k)] * mult;
        }

        return sum;
    }

    private void validateArrays() {
        if (arrayX == null || arrayY == null) {
            throw new NullPointerException("arrayX or arrayY is null");
        }
    }

}
