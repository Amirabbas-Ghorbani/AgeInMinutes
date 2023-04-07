package com.amir.mycalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast


class MainActivity : AppCompatActivity() {

    private var tvCalDigit: TextView? = null
    private var lastNumeric: Boolean = false
    private var lastDot: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvCalDigit = findViewById(R.id.tvCalDigit)
    }

    fun onDigit(view: View) {
        tvCalDigit?.append((view as Button).text)
        lastNumeric = true
        lastDot = false
    }

    fun onClean(view: View) {
        tvCalDigit?.text = ""
    }

    fun onBackSpace(view: View) {
        removeLastNumber(view)
    }

    fun onDecimalPoint(view: View) {
        if (lastNumeric && !lastDot) {
            tvCalDigit?.append(".")
            lastNumeric = false
            lastDot = true
        }
    }

    fun onOperator(view: View) {
        tvCalDigit?.text?.let {
            if (lastNumeric && !isOperatorAdded(it.toString())) {
                tvCalDigit?.append((view as Button).text)
                lastNumeric = false
                lastDot = false
            }
        }
    }

    fun onEqual(view: View) {
        if (lastNumeric) {
            var tvValue = tvCalDigit?.text.toString()
            var prefix = ""
            try {
                if (tvValue.startsWith("-")) {
                    prefix = "-"
                    tvValue = tvValue.substring(1)  // -99 -> 99
                }
                if (tvValue.contains("-")) {
                    val tvSplit = tvValue.split("-") // 99 - 1
                    var one = tvSplit[0] // 99
                    var two = tvSplit[1] // 1
                    if (prefix.isNotEmpty()) {
                        one = prefix + one // -99
                    }
                    tvCalDigit?.text =
                        removeZeroAfterDot((one.toDouble() - two.toDouble()).toString()) // -99.0 - 1.0 or 99.0 - 1.0
                } else if (tvValue.contains("+")) {
                    val tvSplit = tvValue.split("+")
                    var one = tvSplit[0]
                    var two = tvSplit[1]
                    if (prefix.isNotEmpty()) {
                        one = prefix + one
                    }
                    tvCalDigit?.text =
                        removeZeroAfterDot((one.toDouble() + two.toDouble()).toString())
                } else if (tvValue.contains("*")) {
                    val tvSplit = tvValue.split("*")
                    var one = tvSplit[0]
                    var two = tvSplit[1]
                    if (prefix.isNotEmpty()) {
                        one = prefix + one
                    }
                    tvCalDigit?.text =
                        removeZeroAfterDot((one.toDouble() * two.toDouble()).toString())
                } else if (tvValue.contains("/")) {
                    val tvSplit = tvValue.split("/")
                    var one = tvSplit[0]
                    var two = tvSplit[1]
                    if (prefix.isNotEmpty()) {
                        one = prefix + one
                    }
                    tvCalDigit?.text =
                        removeZeroAfterDot((one.toDouble() / two.toDouble()).toString())
                }
            } catch (e: ArithmeticException) {
                e.printStackTrace()
            }
        }
    }

    private fun isOperatorAdded(value: String): Boolean {
        return if (value.startsWith("-")) {
            false
        } else {
            value.contains("+") || value.contains("-") || value.contains("*") || value.contains("/")
        }
    }

    private fun removeZeroAfterDot(result: String): String {
        var value = result
        if (result.contains(".0")) {
            value = result.substring(0, result.length - 2)
        }
        return value
    }

    private fun removeLastNumber(view: View){
        var value = tvCalDigit?.text.toString()
        if (value.isNotEmpty()) {
            if (value.length > 1){
               value = value.substring(0, value.length - 1)
                tvCalDigit?.text = value
            }else if (value.length <= 1) {
                tvCalDigit?.text = "0"
            }
        }
    }
}