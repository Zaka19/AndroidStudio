package com.example.calculadorakotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import java.util.stream.Collectors.toList

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private var action: actions? = null
    private var valorAnterior: String ? = null

    var tv : TextView? = null;
    var tvR : TextView? = null;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tv = findViewById<TextView>(R.id.tvOperacion)
        tvR = findViewById<TextView>(R.id.tvResultado)

        implement_buttons();
    }

    fun implement_buttons(){
        val button_0 = findViewById<Button>(R.id.btn_0);
        button_0.setOnClickListener { onClick(button_0) }

        val button_1 = findViewById<Button>(R.id.btn_1);
        button_1.setOnClickListener { onClick(button_1) }

        val button_2 = findViewById<Button>(R.id.btn_2);
        button_2.setOnClickListener { onClick(button_2) }

        val button_3 = findViewById<Button>(R.id.btn_3);
        button_3.setOnClickListener { onClick(button_3) }

        val button_4 = findViewById<Button>(R.id.btn_4);
        button_4.setOnClickListener { onClick(button_4) }

        val button_5 = findViewById<Button>(R.id.btn_5);
        button_5.setOnClickListener { onClick(button_5) }

        val button_6 = findViewById<Button>(R.id.btn_6);
        button_6.setOnClickListener { onClick(button_6) }

        val button_7 = findViewById<Button>(R.id.btn_7);
        button_7.setOnClickListener { onClick(button_7) }

        val button_8 = findViewById<Button>(R.id.btn_8);
        button_8.setOnClickListener { onClick(button_8) }

        val button_9 = findViewById<Button>(R.id.btn_9);
        button_9.setOnClickListener { onClick(button_9) }

        val btn_coma = findViewById<Button>(R.id.btn_coma);
        btn_coma.setOnClickListener { onClick(btn_coma) }

        val btn_mas = findViewById<Button>(R.id.btn_mas);
        btn_mas.setOnClickListener { onClick(btn_mas) }

        val btn_menos = findViewById<Button>(R.id.btn_menos);
        btn_menos.setOnClickListener { onClick(btn_menos) }

        val btn_mult = findViewById<Button>(R.id.btn_mult);
        btn_mult.setOnClickListener { onClick(btn_mult) }

        val btn_div = findViewById<Button>(R.id.btn_div);
        btn_div.setOnClickListener { onClick(btn_div) }

        val btn_result = findViewById<Button>(R.id.btn_result);
        btn_result.setOnClickListener { onClick(btn_result) }

        val btn_c = findViewById<Button>(R.id.btn_c);
        btn_c.setOnClickListener { onClick(btn_c) }

        val btn_del = findViewById<Button>(R.id.btn_del);
        btn_del.setOnClickListener { onClick(btn_del) }
    }

    fun Create_Operation(numOrcharacter: String?){
        if(!tv!!.text.isEmpty()) {
            if (numOrcharacter.equals(",") && !tv?.text.toString().contains(",")) {
                tv!!.setText(tv!!.text.toString() + numOrcharacter)
            }
            else if(!numOrcharacter.equals(","))
                tv!!.setText(tv!!.text.toString() + numOrcharacter)
        }
        else
            tv!!.setText(tv!!.text.toString() + numOrcharacter)
    }

    fun result() {
        var result: Float = 0f
        var string = !tv?.text.toString().isEmpty ? tv?.text.toString().replace(",",".") : "0"
        valorAnterior = valorAnterior?.replace(",",".")

        when (action){
            actions.SUMAR->{
                result = valorAnterior?.toFloat()!! + string.toFloat()
                tv?.setText( result.toString().replace(".", ","));
            }
            actions.RESTAR->{
                result = valorAnterior?.toFloat()!! - string.toFloat()
                tv?.setText( result.toString().replace(".", ","));
            }
            actions.MULTIPLICAR->{
                result = valorAnterior?.toFloat()!! * string.toFloat()
                tv?.setText( result.toString().replace(".", ","));
            }
            actions.DIVIDIR->{
                result = valorAnterior?.toFloat()!! / string.toFloat()
                tv?.setText( result.toString().replace(".", ","));
            }
        }
    }

    override fun onClick(p0: View?) {

        when(p0!!.id){
            R.id.btn_0 -> {
                Create_Operation("0")
            }
            R.id.btn_1 -> {
                Create_Operation("1")
            }
            R.id.btn_2 -> {
                Create_Operation("2")
            }
            R.id.btn_3 -> {
                Create_Operation("3")
            }
            R.id.btn_4 -> {
                Create_Operation("4")
            }
            R.id.btn_5 -> {
                Create_Operation("5")
            }
            R.id.btn_6 -> {
                Create_Operation("6")
            }
            R.id.btn_7 -> {
                Create_Operation("7")
            }
            R.id.btn_8 -> {
                Create_Operation("8")
            }
            R.id.btn_9 -> {
                Create_Operation("9")
            }
            R.id.btn_coma -> {
                Create_Operation(",")
            }
            R.id.btn_c -> {
                tv?.setText("")!!
                tvR?.setText("")!!
                action = null
            }
            R.id.btn_del -> {
                if(!tv?.text.toString()!!.isEmpty())
                    tv?.setText(tv?.text.toString().substring(0, tv?.text.toString().length - 1))
            }
            R.id.btn_coma -> {
                Create_Operation(",")
            }
            R.id.btn_mas -> {
                valorAnterior = tv?.text.toString()
                tv?.setText("")
                action = actions.SUMAR
                tvR?.setText("+")!!
            }
            R.id.btn_menos -> {
                valorAnterior = tv?.text.toString()
                tv?.setText("")
                action = actions.RESTAR
                tvR?.setText("-")!!
            }
            R.id.btn_mult -> {
                valorAnterior = tv?.text.toString()
                tv?.setText("")
                action = actions.MULTIPLICAR
                tvR?.setText("*")!!
            }
            R.id.btn_div -> {
                valorAnterior = tv?.text.toString()
                tv?.setText("")
                action = actions.DIVIDIR
                tvR?.setText("/")!!
            }
            R.id.btn_result -> {
               result()
                tvR?.setText("")
            }
        }
    }
}
