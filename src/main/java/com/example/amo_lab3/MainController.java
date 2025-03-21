package com.example.amo_lab3;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.example.amo_lab3.logic.functions.Function;
import com.example.amo_lab3.logic.functions.MyFunction;
import com.example.amo_lab3.logic.polynomials.LagrangePolynomial;
import com.example.amo_lab3.logic.polynomials.Polynomial;
import com.example.amo_lab3.logic.range.RangeCalculator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class MainController {
    private final int HIGH_INTER_POW = 80;

    private float[] arrayX;
    private float[] arrayY;

    private float[] arrayHighX;
    private float[] arrayHighY;

    private ArrayList<Float> arrayInterX;
    private ArrayList<Float> arrayInterY;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private LineChart<Number, Number> lineChart;

    @FXML
    private NumberAxis xAxis;

    @FXML
    private NumberAxis yAxis;

    @FXML
    private Label curNodesLabel;

    @FXML
    private Label errorLabel;

    @FXML
    private Label funcHighResLabel;

    @FXML
    private Label funcResLabel;

    @FXML
    private TextField textFieldA;

    @FXML
    private TextField textFieldB;

    @FXML
    private TextField textFieldX;

    @FXML
    private TextField textFieldPOW;

    @FXML
    void calculateFunc(ActionEvent event) {
        Polynomial polynomial = new LagrangePolynomial();
        polynomial.setArrays(arrayX, arrayY);
        float x, y;
        try {
            x = validateInputX();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return;
        }
        y = polynomial.calculatePolynomial(x);

        funcResLabel.setText(String.format("f(x) = %f", y));
        arrayInterX.add(x);
        arrayInterY.add(y);

        // Значення при високому степені інтерполяції
        polynomial.setArrays(arrayHighX, arrayHighY);
        float highY = polynomial.calculatePolynomial(x);
        funcHighResLabel.setText(String.format("При степені інтерполяції %d, f(x) = %f", HIGH_INTER_POW, highY));

        // Оцінка похибки
        float errorResult = (y / highY - 1) * 100;
        errorLabel.setText(String.format("Похибка: %.3f%%", errorResult));
    }

    @FXML
    void rememberRange(ActionEvent event) {
        // Обраховуємо значення X
        int interPow;
        try {
            int[] range = validateInputRange();
            curNodesLabel.setText(String.format("Поточні вузли: [%d, %d]", range[0], range[1]));
            interPow = Integer.parseInt(textFieldPOW.getText());
            RangeCalculator rangeCalculator = new RangeCalculator(range[0], range[1]);
            arrayX = rangeCalculator.getRange(interPow);

            // HighX
            arrayHighX = rangeCalculator.getRange(HIGH_INTER_POW);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            curNodesLabel.setText("Поточні вузли: [-, -]");
            return;
        }

        // Обраховуємо значення Y
        arrayY = new float[interPow + 1];
        Function function = new MyFunction();

        System.out.println();
        for (int i = 0; i < interPow + 1; i++) {
            arrayY[i] = function.calculate(arrayX[i]);
        }

        arrayHighY = new float[HIGH_INTER_POW + 1];
        for (int i = 0; i < HIGH_INTER_POW + 1; i++) {
            arrayHighY[i] = function.calculate(arrayHighX[i]);
            System.out.println(arrayHighY[i]);
        }
    }

    private int[] validateInputRange() {
        int[] result = new int[2];
        try {
            result[0] = Integer.parseInt(textFieldA.getText());
            result[1] = Integer.parseInt(textFieldB.getText());
        } catch (Exception e) {
            throw new NumberFormatException("Вводьте цілі числа в діапазон!");
        }

        return result;
    }

    private float validateInputX() {
        float result;
        try {
            result = Float.parseFloat(textFieldX.getText());
        } catch (Exception e) {
            throw new NumberFormatException("Введіть коректне число X!");
        }

        return result;
    }

    @FXML
    void drawLineChart(ActionEvent event) {
        // Налаштування осей
        xAxis.setLowerBound(arrayX[0]);
        xAxis.setUpperBound(arrayX[arrayX.length - 1]);
        xAxis.setTickUnit((arrayX[arrayX.length - 1] - arrayX[0]) / arrayX.length);

        yAxis.setLowerBound(arrayY[0]);
        yAxis.setUpperBound(arrayY[arrayY.length - 1]);
        yAxis.setTickUnit((arrayY[arrayY.length - 1] - arrayY[0]) / arrayY.length);


        // Додавання серії даних
        XYChart.Series<Number, Number> series = new XYChart.Series<>();
        series.setName("f(x)");

        // Додавання точок на графік
        for (int i = 0; i < arrayX.length; i++) {
            series.getData().add(new XYChart.Data<>(arrayX[i], arrayY[i]));
        }

        // Додавання серії на графік
        lineChart.getData().add(series);


        // ---------Малювання другого графіка------------

        // Додавання серії даних
        XYChart.Series<Number, Number> seriesInter = new XYChart.Series<>();
        seriesInter.setName("f(x) by interpolation");

        // Додавання точок на графік
        for (int i = 0; i < arrayInterX.size(); i++) {
            seriesInter.getData().add(new XYChart.Data<>(arrayInterX.get(i), arrayInterY.get(i)));
        }

        // Додавання серії на графік
        lineChart.getData().add(seriesInter);
    }

    @FXML
    void clearLineChart(ActionEvent event) {
        lineChart.getData().clear();
        arrayInterX.clear();
        arrayInterY.clear();
    }

    @FXML
    void initialize() {
        arrayInterX = new ArrayList<>();
        arrayInterY = new ArrayList<>();
    }

}
