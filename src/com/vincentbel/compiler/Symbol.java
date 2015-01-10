package com.vincentbel.compiler;

/**
 * Author: VincentBel
 * Create at: 2015/1/10.
 */
public class Symbol {


    public static final int SYMBOL_NUMBER = 35;       // 单词的总数
    public static final int NUMBER_MAX = 1000000;     // PL/0中允许的无符号整数的最大值



    /**------------------------------------
     * 分界符或操作符
     * . , ; = := + - ( ) * / <> < <= > > >=
     * -------------------------------------
     */
    public static final int NUL = 0;                  // NULL
    public static final int PLUS = 1;                 // 加号+
    public static final int MINUS = 2;                // 减号-
    public static final int MULTIPLY = 3;             // 乘号*
    public static final int DIVIDE = 4;               // 除号/
    public static final int EQUAL = 5;                // 等于号=(equal)
    public static final int NOT_EQUAL = 6;            // 不等于<>(not equal)
    public static final int LESS = 7;                 // 小于<(less)
    public static final int LESS_OR_EQUAL = 8;        // 小于等于<=(less or equal)
    public static final int GREATER = 9;              // 大于>(greater)
    public static final int GREATER_OR_EQUAL = 10;    // 大于等于>=(greater or equal)
    public static final int LEFT_PARENTHESIS = 11;    // 左括号(
    public static final int RIGHT_PARENTHESIS = 12;   // 右括号 )
    public static final int COMMA = 13;               // 逗号,
    public static final int SEMICOLON = 14;           // 分号;
    public static final int PERIOD = 15;              // 句号.
    public static final int BECOMES = 16;             // 赋值符号 :=


    /**------------------------------------
     * 标识符 <标识符> ::= <字母>{<字母>|<数字>}
     * -------------------------------------
     */
    public static final int IDENTIFIER = 17;


    /**------------------------------------
     * 常数中只有无符号整数 <无符号整数> ::= <数字>{<数字>}
     * -------------------------------------
     */
    public static final int NUMBER = 18;


    /**------------------------------------
     * 保留字
     * const, var, procedure, odd,
     * if, then, else, while,
     * do, call, begin, end,
     * repeat, until, read, write
     * -------------------------------------
     */
    public static final int CONST_SYMBOL = 19;
    public static final int VAR_SYMBOL = 20;
    public static final int PROCEDURE_SYMBOL = 21;
    public static final int ODD_SYMBOL = 22;
    public static final int IF_SYMBOL = 23;
    public static final int THEN_SYMBOL = 24;
    public static final int ELSE_SYMBOL = 25;
    public static final int WHILE_SYMBOL = 26;
    public static final int DO_SYMBOL = 27;
    public static final int CALL_SYMBOL = 28;
    public static final int BEGIN_SYMBOL = 29;
    public static final int END_SYMBOL = 30;
    public static final int REPEAT_SYMBOL = 31;
    public static final int UNTIL_SYMBOL = 32;
    public static final int READ_SYMBOL = 33;
    public static final int WRITE_SYMBOL = 34;



    private int symbolType;  // 符号的类型，即前面35种中的一种

    private int number = 0; // 如果符号是无符号整数，则记录其值

    private String name = ""; // 如果符号是保留字，则记录其名字

    public Symbol(int symbolType) {
        this.symbolType = symbolType;
    }


    /*------------------------------
     * Getter
     * -----------------------------
     */

    public String getName() {
        return name;
    }

    public int getSymbolType() {
        return symbolType;
    }

    public int getNumber() {
        return number;
    }



    /*------------------------------
     * Setter
     * -----------------------------
     */

    public void setNumber(int number) {
        this.number = number;
    }

    public void setName(String name) {
        this.name = name;
    }

}
