package com.example.amo_lab3;

import java.net.URL;
import java.util.ResourceBundle;

import com.example.amo_lab3.logic.functions.Function;
import com.example.amo_lab3.logic.functions.MyFunction;
import com.example.amo_lab3.logic.polynomials.LagrangePolynomial;
import com.example.amo_lab3.logic.polynomials.Polynomial;
import com.example.amo_lab3.logic.range.RangeCalculator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class MainController {

    private float[] arrayX;
    private float[] arrayY;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label curNodesLabel;

    @FXML
    private Label errorLabel;

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

        funcResLabel.setText("f(x) = " + y);
    }

    @FXML
    void rememberRange(ActionEvent event) {
        // Обраховуємо значення X
        try {
            int[] range = validateInputRange();
            curNodesLabel.setText(String.format("Поточні вузли: [%d, %d]", range[0], range[1]));
            RangeCalculator rangeCalculator = new RangeCalculator(range[0], range[1]);
            arrayX = rangeCalculator.getRange(10);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            curNodesLabel.setText("Поточні вузли: [-, -]");
            return;
        }

        // Обраховуємо значення Y
        arrayY = new float[11];
        Function function = new MyFunction();
        System.out.println();
        for (int i = 0; i < 11; i++) {
            arrayY[i] = function.calculate(arrayX[i]);
            System.out.println(arrayY[i]);
        }
    }

    public int[] validateInputRange() {
        int[] result = new int[2];
        try {
            result[0] = Integer.parseInt(textFieldA.getText());
            result[1] = Integer.parseInt(textFieldB.getText());
        } catch (Exception e) {
            throw new NumberFormatException("Вводьте цілі числа в діапазон!");
        }

        return result;
    }

    public float validateInputX() {
        float result;
        try {
            result = Float.parseFloat(textFieldX.getText());
        } catch (Exception e) {
            throw new NumberFormatException("Введіть коректне число X!");
        }

        return result;
    }

    @FXML
    void initialize() {
        assert curNodesLabel != null : "fx:id=\"curNodesLabel\" was not injected: check your FXML file 'main-view.fxml'.";
        assert errorLabel != null : "fx:id=\"errorLabel\" was not injected: check your FXML file 'main-view.fxml'.";
        assert funcResLabel != null : "fx:id=\"funcResLabel\" was not injected: check your FXML file 'main-view.fxml'.";
        assert textFieldA != null : "fx:id=\"textFieldA\" was not injected: check your FXML file 'main-view.fxml'.";
        assert textFieldB != null : "fx:id=\"textFieldB\" was not injected: check your FXML file 'main-view.fxml'.";
        assert textFieldX != null : "fx:id=\"textFieldX\" was not injected: check your FXML file 'main-view.fxml'.";

    }

}
