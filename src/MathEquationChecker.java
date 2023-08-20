import java.util.Scanner;
import java.util.regex.*;

public class MathEquationChecker {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter a mathematical equation:");
        String equation = scanner.nextLine();
        String equationWithoutSpaces = equation.replace(" ", ""); // Remove spaces
        System.out.println(equationWithoutSpaces);

        if (isValidEquation(equationWithoutSpaces)) {
            System.out.println("The entered equation is correct");
            System.out.println("The number of numbers in the equation: " + countNumbersInEquation(equationWithoutSpaces));
        } else {
            System.out.println("The entered equation is incorrect");
        }
    }

    public static boolean isValidEquation(String equation) {
        // Checking for an empty string
        if (equation == null || equation.isEmpty()) {
            System.out.println("Equation is empty");
            return false;
        }

        // Checking for the '=' signs and variable 'x'
        if (!equation.contains("=") || !equation.contains("x")) {
            System.out.println("Equation has no 'x' or '='");
            return false;
        }

        // Checking the number of '=' signs
        int equalsCount = equation.length() - equation.replace("=", "").length();
        if (equalsCount != 1) {
            System.out.println("Equation has more than one '='");
            return false;
        }

        // Check for the correct placement of brackets
        if (!isValidBrackets(equation)) {
            System.out.println("Equation has wrong brackets");
            return false;
        }

        if (!isValidOperands(equation)) {
            System.out.println("Equation has wrong operands");
            return false;
        }

        return true;
    }
    public static boolean isValidBrackets(String equation) {
        int count = 0;
        for (char c : equation.toCharArray()) {
            if (c == '(') {
                count++;
            } else if (c == ')') {
                count--;
                if (count < 0) {
                    return false;
                }
            }
        }
        return count == 0;
    }

    public static boolean isValidOperands(String expression) {
        // A pattern for searching for two signs of mathematical operations in a row
        Pattern pattern = Pattern.compile("[+\\-*/]{2,}");
        Matcher matcher = pattern.matcher(skipCorrectOperands(expression));

        if (matcher.find()) {
            String twoOperands = matcher.group();
            System.out.println("ITS two operands: " + twoOperands);
            return false;
        }
        return true;
    }

    // Method to skip validating the correct order of operands. So the pattern matcher gets an expression without *- or /-
    public static String skipCorrectOperands(String equation) {

        int index = 0; // Початковий індекс
        StringBuilder newString = new StringBuilder();
        while (index < equation.length()) {
            char currentChar = equation.charAt(index);
            if (currentChar == '*' || currentChar == '/') {
                if (index + 1 < equation.length()) {
                    char nextChar = equation.charAt(index + 1);
                    if (nextChar == '-') {
                        // We continue from the position after the sign '-'
                        index += 2;
                        continue;
                    }
                }
            }
            // New line where we missed *- or /-
            newString.append(currentChar);
            index++;
        }
        return newString.toString();
    }

    public static int countNumbersInEquation(String equation) {
        int numberCount = 0;
        boolean inNumber = false; // A flag that indicates if we are in a number

        for (char c : equation.toCharArray()) {
            if (Character.isDigit(c) || c == '.') {
                if (!inNumber) {
                    inNumber = true;
                    numberCount++;
                }
            } else {
                inNumber = false;
            }
        }

        return numberCount;
    }



}
