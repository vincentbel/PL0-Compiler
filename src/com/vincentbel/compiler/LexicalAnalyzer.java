package com.vincentbel.compiler;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

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
}
