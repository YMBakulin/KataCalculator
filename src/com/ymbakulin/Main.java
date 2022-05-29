package com.ymbakulin;


import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

    private static int arabFlag = 0;  //если arabflag=2 то оба arabic, если -2 roman

    private static String[] romanArray = {"I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX", "X"};
    private static String[] romanArray1 = {"X", "XX", "XXX", "XL", "L", "LX", "LXX", "LXXX", "XC", "C"};

    public static void main(String[] args) {

       Scanner scInput = new Scanner(System.in);
       System.out.println("The digits you enter must be integer, between 1 and 10, only Arabic or only Roman");
       System.out.print("Please enter the arithmetic expression: ");
       String myInput = scInput.nextLine();

       try {
           if (myInput.length()<3) throw new InputMismatchException();
       } catch (InputMismatchException e) {
           System.out.println("Error: invalid input");
           System.out.println(e.getMessage());
           System.exit(0);
       }

       myInput = myInput.trim();

       // метод проверки на правильность введенного выражения
       String result = calc(myInput);
       System.out.println(result);

    }

    public static String calc(String input) {
        String firstNumStr = "", secondNumStr = "", resultStr = null;
        int operation = 0;
        char[] ch = input.toCharArray();
        int flagOperator = 0;
         //Добавить исключение на повторный ввод оператора
        for (int i=0; i < ch.length; i++) {
            switch (ch[i]){
                case '+': operation = 1; flagOperator++; break;
                case '-': operation = 2; flagOperator++; break;
                case '*': operation = 3; flagOperator++; break;
                case '/': operation = 4; flagOperator++; break;
                case ' ': break;
                default: {
                    if (operation == 0) {
                        firstNumStr += ch[i];
                    } else secondNumStr += ch[i];
                }
            }
            if (flagOperator == 2) throw new InputMismatchException("Error: You must enter only 2 operand and 1 operator");  // Исключение на введенный второй операнд
        }

        int firstArabNum = -1;
        int secondArabNum = -1;

        firstArabNum = convertToArabic(firstNumStr);
        secondArabNum = convertToArabic(secondNumStr);

        //проверка являются ли оба числа арабскими или оба римскими
         if ((arabFlag != -2) && (arabFlag != 2)) throw new InputMismatchException("Error: both numbers must be only arabic or only roman");

        //проверка находятся ли оба числа между 1 и 10.
         if (firstArabNum<1 || firstArabNum>10 || secondArabNum<1 || secondArabNum>10) throw new InputMismatchException("Error: both numbers must be between 1 and 10");

        int resultInt = -1;

        switch (operation) {
            case 1 : resultInt = firstArabNum + secondArabNum;
                break;
            case 2: resultInt = firstArabNum - secondArabNum;
                break;
            case 3: resultInt = firstArabNum * secondArabNum;
                break;
            case 4: resultInt = firstArabNum / secondArabNum;
                break;
        }

        //проверка: если римские цифры результат не должен быть отрицательным
        try {
            if ((arabFlag == -2) && (resultInt < 1)) throw new InputMismatchException();
        } catch (InputMismatchException e) {
            System.out.println("Error: when working with roman numerals, the result should be > 0 ");
            System.out.println(e.getMessage());
            System.exit(0);
        }

        if (arabFlag == 2) {
            resultStr = Integer.toString(resultInt);
        } else if (arabFlag==-2 && resultInt<=10){
            resultStr = romanArray[resultInt-1];
        } else if (arabFlag==-2 && resultInt>10) resultStr = convertToRoman(resultInt);

        return resultStr;

    }

    private static int convertToArabic (String s){
        int arabNum = -1;

        for (int r =0; r<romanArray.length; r++) {
            if (romanArray[r].equals(s)) {
                arabNum = r + 1;
                arabFlag-- ;
                break;
            }
        }

        if (arabNum == -1) {
            try {
                arabNum = Integer.parseInt(s);
                arabFlag++;
            }catch (NumberFormatException e){
                System.out.println("Error: incorrect input value");
                System.out.println(e.getMessage());
                System.exit(0);
            }
        }
        return arabNum;
    }

    private static String convertToRoman(int result){
        int arabicResult = result;
        String resultStr = "";
        int romArrIndex = -1;
        if (arabicResult == 100) {
            resultStr = romanArray1[9];
        } else {
            char[] che = Integer.toString(arabicResult).toCharArray();
            for (int c =0; c <= 1; c++){
                romArrIndex = (Character.getNumericValue(che[c])-1);
                switch (c) {
                    case 0: {
                        resultStr += romanArray1[romArrIndex];
                    } break;
                    case 1: {
                        if (romArrIndex == - 1) {
                            break;
                        } else resultStr += romanArray[romArrIndex];
                    } break;
                }
            }

        }

        return resultStr;
    }

}
