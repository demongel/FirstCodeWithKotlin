package com.shakespace.firstlinecode.compare

import android.graphics.Color

class KotlinCode {


    companion object{
        operator fun invoke(i:Int){
            println("companion object $i")
        }
    }


    val aFinalString = "a final string"

    val html = """
        <!DOCTYPE html>
        <html>
            <head>
                <title>这是个标题</title>
            </head>
            <body>
                <h1>这是一个一个简单的HTML</h1>
                <p>Hello World！</p>
            </body>
        </html>
    """.trimIndent()

    var num: Any = 0
    val result = when (num) {
        is Int -> println("is int ")
        is Long -> println("is Double")
        else -> println("else")
    }

    var string: String = "a"


    val result2 = when {
        string.length > 10 -> println("long string")
        string.contains("a") -> println("contain a")
        else -> println("common string")
    }

    val result3 = when (string) {
        "a" -> println("long string")
        "b" -> println("contain a")
        else -> println("common string")
    }

    fun mix(c1: Color, c2: Color) = when (setOf(c1, c2)) {
        setOf(Color.RED, Color.GREEN) -> println("red and green")
        setOf(Color.BLUE, Color.YELLOW) -> println("blue and yellow")
        setOf(Color.GRAY, Color.CYAN) -> println("gray and cyan")
        else -> println("other color ")
    }


}

fun main() {
    println(KotlinCode().html)

    for (i in 1..10) {
        print(i)
    }

    println("")

    for ( i in 10 downTo 1 step 2){
        print(i)
    }

    KotlinCode(1)

    val a = 10
    val b = 11
    println("a + b = ${a+b}")
}