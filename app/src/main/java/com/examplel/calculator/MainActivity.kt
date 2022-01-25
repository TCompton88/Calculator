package com.examplel.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import java.lang.ArithmeticException

class MainActivity : AppCompatActivity() {

    private var input: TextView? = null
    var lastNumeric : Boolean = false
    var lastDot : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        input = findViewById(R.id.inputArea)
    }

    fun onDigit(view: View){
        input?.append((view as Button).text)
        lastNumeric = true

    }

    fun onClear(view: View){
        input?.text = null
        lastDot = false
        lastNumeric = false
    }

    fun onOperator(view: View){
        input?.text?.let {
            if (lastNumeric && !isOperatorAdded(it.toString())) {
                input?.append(" " +(view as Button).text + " ")
                lastDot = false
                lastNumeric = false
            }
        }
    }

    private fun removeZero(result : String) : String{
        var value = result
        if(result.contains(".0")){
            value = result.substring(0, result.length - 2)
        }

        return value
    }

    private fun isOperatorAdded(value : String) : Boolean{
        return if(value.startsWith("-")){
            false
        }else{
            value.contains("/")
                    || value.contains("*")
                    || value.contains("+")
                    || value.contains("-")
        }
    }

    fun onDecimal(view: View){
        if(lastNumeric && !lastDot){
            input?.append((view as Button).text)
            lastNumeric = false
            lastDot = true
        }
    }

    fun onEqual(view: View){
        if(lastNumeric){
            var inputValue = input?.text.toString()
            var prefix = ""

            try{
                if(inputValue.startsWith("-")){
                    prefix = "-"
                    inputValue = inputValue.substring(1)
                }

                when {
                    inputValue.contains("-") -> {
                        val splitValue = inputValue.split("-")

                        var one = splitValue[0]
                        var two = splitValue[1]

                        if(prefix.isNotEmpty()){
                            one = prefix + one
                        }

                        input?.text = removeZero((one.toDouble() - two.toDouble()).toString())
                    }
                    inputValue.contains("+") -> {
                        val splitValue = inputValue.split("+")

                        var one = splitValue[0]
                        var two = splitValue[1]

                        if(prefix.isNotEmpty()){
                            one = prefix + one
                        }

                        input?.text = removeZero((one.toDouble() + two.toDouble()).toString())
                    }
                    inputValue.contains("/") -> {
                        val splitValue = inputValue.split("/")

                        var one = splitValue[0]
                        var two = splitValue[1]

                        if(prefix.isNotEmpty()){
                            one = prefix + one
                        }

                        input?.text = removeZero((one.toDouble() / two.toDouble()).toString())
                    }
                    inputValue.contains("*") -> {
                        val splitValue = inputValue.split("*")

                        var one = splitValue[0]
                        var two = splitValue[1]

                        if(prefix.isNotEmpty()){
                            one = prefix + one
                        }

                        input?.text = removeZero((one.toDouble() * two.toDouble()).toString())
                    }
                }

            }catch(e: ArithmeticException){
                e.printStackTrace()
            }
        }
    }
}