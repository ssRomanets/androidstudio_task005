package com.example.task_005

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    private lateinit var toolbarMain: Toolbar

    private lateinit var firstOperandTimeET: EditText
    private lateinit var secondOperandTimeET: EditText

    private lateinit var buttonTotalTimeBTN: Button
    private lateinit var buttonDifTimeBTN: Button

    private lateinit var resultTimeTV: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        toolbarMain = findViewById(R.id.toolbarMain)
        setSupportActionBar(toolbarMain)
        title = "Калькулятор времени"
        toolbarMain.subtitle = "домашнее задание"
        toolbarMain.setLogo(R.drawable.time_calculator)

        firstOperandTimeET = findViewById(R.id.firstOperandTimeET)
        secondOperandTimeET = findViewById(R.id.secondOperandTimeET)

        buttonTotalTimeBTN = findViewById(R.id.buttonTotalTimeBTN)
        buttonDifTimeBTN = findViewById(R.id.buttonDifTimeBTN)

        resultTimeTV = findViewById(R.id.resultTimeTV)

        onButtonClick(buttonTotalTimeBTN)
        onButtonClick(buttonDifTimeBTN)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return  true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.resetMenuMain -> {
                firstOperandTimeET.text.clear()
                secondOperandTimeET.text.clear()
                resultTimeTV.setTextColor(0xFF000000.toInt())
                resultTimeTV.text = "Результат"
                Toast.makeText(applicationContext, "Данные очищены", Toast.LENGTH_LONG).show()
            }
            R.id.exitMenuMain -> {
                Toast.makeText(applicationContext, "Приложение закрыто", Toast.LENGTH_LONG).show()
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun onButtonClick(v: View) {
        if (firstOperandTimeET.text.isEmpty() || secondOperandTimeET.text.isEmpty()) {
            return
        }

        var timeFirst  = firstOperandTimeET.text.toString()
        var timeSecond = secondOperandTimeET.text.toString()

        var timeFirstData =  defTimeArrayDistrib(timeFirst)
        var timeSecondData = defTimeArrayDistrib(timeSecond)

        var resultTime = when(v.id) {
            R.id.buttonTotalTimeBTN -> totalTimeCalculation(timeFirstData, timeSecondData)
            R.id.buttonDifTimeBTN -> diffTimeCalculation(timeFirstData, timeSecondData)
            else -> 0.0
        }
        resultTimeTV.setTextColor(0xFF8B0000.toInt())
        resultTimeTV.text = resultTime.toString()
        Toast.makeText(applicationContext, "Результат: " + resultTime.toString(), Toast.LENGTH_LONG).show()
    }

    fun defTimeArrayDistrib(inputTimeString: String): ArrayList<Int> {
        var listTimeData = ArrayList<Int>();
        var curTimeString  = inputTimeString;

        if (curTimeString.split("h").size > 1)
        {
            listTimeData.add(curTimeString.split("h").get(0).toInt())
            curTimeString = curTimeString.split("h").get(1)
        }
        else
        {
            listTimeData.add(0)
        }

        if (curTimeString.split("m").size > 1)
        {
            listTimeData.add(curTimeString.split("m").get(0).toInt())
            curTimeString = curTimeString.split("m").get(1)
        }
        else
        {
            listTimeData.add(0)
        }

        if (curTimeString.split("s").size > 1)
        {
            listTimeData.add(curTimeString.split("s").get(0).toInt())
        }
        else
        {
            listTimeData.add(0)
        }

        return listTimeData
    }

    fun totalTimeCalculation(timeFirstData: ArrayList<Int>, timeSecondData: ArrayList<Int>): String
    {
        val totalSeconds = 3600*(timeFirstData.get(0) + timeSecondData.get(0))+
                +60*(timeFirstData.get(1) + timeSecondData.get(1)) +
                +timeFirstData.get(2) + timeSecondData.get(2)

        val finalHours = totalSeconds / 3600
        val finalMinutes = (totalSeconds - finalHours*3600)  / 60
        val finalSeconds = (totalSeconds - finalHours*3600 - finalMinutes*60)
        return finalHours.toString()+"h"+finalMinutes.toString()+"m"+finalSeconds.toString()+"s"
    }

    fun diffTimeCalculation(timeFirstData: ArrayList<Int>, timeSecondData: ArrayList<Int>): String
    {
        val totalSeconds = 3600*(timeFirstData.get(0) - timeSecondData.get(0)) +
                +  60*(timeFirstData.get(1) - timeSecondData.get(1)) +
                + (timeFirstData.get(2) - timeSecondData.get(2))

        val finalHours = totalSeconds / 3600
        val finalMinutes = (totalSeconds - finalHours*3600)  / 60
        val finalSeconds = (totalSeconds - finalHours*3600 - finalMinutes*60)
        return finalHours.toString()+"h"+finalMinutes.toString()+"m"+finalSeconds.toString()+"s"
    }
}