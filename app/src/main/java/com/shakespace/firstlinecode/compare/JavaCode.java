package com.shakespace.firstlinecode.compare;

public class JavaCode {


    // 1 constant
    public final String aFinalString = "a final string";

    // 2 raw String
    String html = "<!DOCTYPE html>\n" +
            "<html>\n" +
            "    <head>\n" +
            "        <title>这是个标题</title>\n" +
            "    </head>\n" +
            "    <body>\n" +
            "        <h1>这是一个一个简单的HTML</h1>\n" +
            "        <p>Hello World！</p>\n" +
            "    </body>\n" +
            "</html>\n" +
            "————————————————\n";

    // switch
    public void select(int num) {
        switch (num) {
            case 1:
                break;
            case 2:
                break;
            default:
                break;
        }
    }


    public static void main(String[] args) {
        System.out.println(new JavaCode().html);

        for (int i = 0; i < 10; i++) {
            System.out.println(i);
        }

        int a = 10;
        int b = 11;

        System.out.println("a + b = " + (a+b));
    }


}
