package com.vincentbel.compiler;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

/**
 * Author: VincentBel
 * Create at: 2015/1/10.
 */
public class LexicalAnalyzer {


    private BufferedReader sourceBuffer; // 读入的源代码的Buffer
    private char currentChar = ' ';  // 当前读取的字符

    private String currentLineString;  // 当前读取的一行源代码
    private int currentLineStringLength = 0;  // 当前读取的一行源代码的的长度
    private int currentLineStringIndex = 0;  // 读取到的某个具体字符下标

    // 简单的操作符，读取到一个字符后就能确定类型的字符
    // 也就是 . , ; = + - ( ) * /
    private int[] simpleOperators;


    /**
     * Constructor
     *
     * @param filePath 源代码的文件路径
     */
    public LexicalAnalyzer(String filePath) {
        try {
            sourceBuffer = new BufferedReader(new FileReader(filePath));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("错误！ 文件不存在");
        }

        // 初始化简单操作符
        simpleOperators = new int[256]; // 因为char的范围是[0, 255]，所以数组大小最大只需256
        Arrays.fill(simpleOperators, Symbol.NUL); // 对于其他不可识别的字符，全都设置为[NULL]
        simpleOperators['.'] = Symbol.PERIOD;
        simpleOperators[','] = Symbol.COMMA;
        simpleOperators[';'] = Symbol.SEMICOLON;
        simpleOperators['='] = Symbol.EQUAL;
        simpleOperators['+'] = Symbol.PLUS;
        simpleOperators['-'] = Symbol.MINUS;
        simpleOperators['('] = Symbol.LEFT_PARENTHESIS;
        simpleOperators[')'] = Symbol.RIGHT_PARENTHESIS;
        simpleOperators['*'] = Symbol.MULTIPLY;
        simpleOperators['/'] = Symbol.DIVIDE;
    }

    /**
     * 词法分析中获取一个单词
     *
     * @return Symbol
     */
    public Symbol getSymbol() {
        // 每次都提前读取一个字符

        // 跳过空格
        while (isSpace()) {
            getChar();
        }

        if (isLetter()) {
            // 保留字或者标识符
            return getKeywordOrIdentifier();
        } else if (isDigit()) {
            // 无符号整数
            return getNumber();
        } else {
            // 分界符 或者 非法字符
            return getOperator();
        }
    }


    /**
     * 读取下一个字符。为增大读取磁盘IO，实际每次读取一行
     *
     */
    private void getChar() {
        if (currentLineStringIndex == currentLineStringLength) {  // 如果读到了行末尾，则再读取一行
            try {
                String redString = "";

                // 跳过空行
                while (redString.equals("")) {

                    redString = sourceBuffer.readLine();

                    if (redString == null) {
                        // 文件已全部读取，还要继续读取，出错
                        System.out.println("EOF, ERROR!");
                        System.exit(0);
                    }

                    // 去除开头结尾的空白符
                    redString = redString.trim();
                }

                currentLineString = redString;

                // 将字符串中的[tab]替换成[空格]
                currentLineString = currentLineString.replaceAll("\t", " ");

                // 为防止直接跳过[换行符]，在最后加上[空格]代替[换行符]
                currentLineString += " ";

                currentLineStringLength = currentLineString.length();
                currentLineStringIndex = 0;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        currentChar = currentLineString.charAt(currentLineStringIndex);  // 获取下一个字符
        currentLineStringIndex++;  // 下标下移一个位置
    }


    /**
     * 判断当前读取的字符是否是[空格]
     *
     * @return boolean
     */
    private boolean isSpace() {
        return (currentChar == ' ');
    }

    /**
     * 判断当前读取的字符是否是[数字]
     *
     * @return boolean
     */
    private boolean isDigit() {
        return currentChar >= '0' && currentChar <= '9';
    }

    /**
     * 判断当前读取的字符是否是[字母]
     *
     * @return boolean
     */
    private boolean isLetter() {
        return (currentChar >= 'a' && currentChar <= 'z') || (currentChar >= 'A' && currentChar <= 'Z');
    }


    /**
     * 获取符号——标识符或者保留字
     *
     * @return Symbol
     */
    private Symbol getKeywordOrIdentifier() {
        Symbol symbol;
        String token = "";  // 识别的单词

        // 将字符拼接成字符串
        while (isLetter() || isDigit()) {
            token += currentChar;
            getChar();
        }

        // 二分查找保留字
        int index = Arrays.binarySearch(Symbol.WORD, token);

        if (index < 0) { // 未在保留字数组中找到token，说明token是标识符
            symbol = new Symbol(Symbol.IDENTIFIER);
            symbol.setName(token);
        } else { // 在保留字数组中找到token，说明token是保留字
            symbol = new Symbol(Symbol.WORD_SYMBOL[index]);
        }

        return symbol;
    }

    /**
     * 获取符号——无符号整数
     *
     * @return Symbol
     */
    private Symbol getNumber() {
        Symbol symbol = new Symbol(Symbol.NUMBER);
        int value = 0;
        do {
            value = value * 10 + (currentChar - '0');  // 计算无符号整数的值
            getChar();
        } while (isDigit());

        symbol.setNumber(value);  // 设置无符号整数的值

        return symbol;
    }

    /**
     * 获取符号——分界符或操作符
     *
     * @return Symbol
     */
    private Symbol getOperator() {
        Symbol symbol;

        switch (currentChar) {
            case ':':
                getChar();
                if (currentChar == '=') { // 赋值符号 :=
                    symbol = new Symbol(Symbol.BECOMES);
                    getChar();
                } else {
                    // ERROR，设置symbol为null
                    symbol = new Symbol(Symbol.NUL);
                }
                break;
            case '<':
                getChar();
                switch (currentChar) {
                    case '>':  // 不等于 <>
                        symbol = new Symbol(Symbol.NOT_EQUAL);
                        getChar();
                        break;
                    case '=':  // 小于等于 <=
                        symbol = new Symbol(Symbol.LESS_OR_EQUAL);
                        getChar();
                        break;
                    default: // 小于
                        symbol = new Symbol(Symbol.LESS);
                        break;
                }
                break;
            case '>':
                getChar();
                if (currentChar == '=') { // 大于等于 >=
                    symbol = new Symbol(Symbol.GREATER_OR_EQUAL);
                    getChar();
                } else { // 大于
                    symbol = new Symbol(Symbol.GREATER);
                }
                break;
            default:
                // 直接利用simpleOperators[]数组填充即可
                symbol = new Symbol(simpleOperators[currentChar]);

                // 提前读取下一个字符
                getChar();
                break;
        }

        return symbol;
    }
}
