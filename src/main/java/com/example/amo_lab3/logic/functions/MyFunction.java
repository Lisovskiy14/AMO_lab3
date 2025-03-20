package com.example.amo_lab3.logic.functions;

public class MyFunction implements Function {

    @Override
    public float calculate(float x) {
        float result;

        result = (float) (Math.pow((float) Math.E, Math.cos(x)) * Math.cos(x * x));

        return result;
    }
}
