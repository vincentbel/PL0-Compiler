package com.vincentbel;

import com.vincentbel.compiler.LexicalAnalyzer;
import com.vincentbel.compiler.Symbol;

/**
 * Author: VincentBel
 * Create at: 2015/1/10.
 */
public class Main {

    public static void main(String[] args) {
	    lexicalTest();
    }

    /**
     * 词法分析测试
     */
    public static void lexicalTest() {
        LexicalAnalyzer lexicalAnalyzer = new LexicalAnalyzer("test/test_1");
        Symbol symbol = lexicalAnalyzer.getSymbol();

        System.out.println("所有单词列表：");
        String format = "%6s%12s%20s%16s%8s";
        System.out.format(format, "counter", "type", "type name", "value", "number");
        System.out.println();
        for (int counter = 1; symbol.getSymbolType() != Symbol.PERIOD; counter++) {
            System.out.format (
                    format,
                    counter,
                    symbol.getSymbolType(),
                    symbol.getSymbolTypeName(),
                    symbol.getName(),
                    symbol.getNumber()
            );
            System.out.println();
            symbol = lexicalAnalyzer.getSymbol();
        }
        System.out.println();
    }
}
