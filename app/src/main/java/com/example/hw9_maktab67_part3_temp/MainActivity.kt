package com.example.hw9_maktab67_part3_temp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.hw9_maktab67_part3_temp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var bind: ActivityMainBinding
    private lateinit var timer: CountDownTimer
    val observable = Observable()

    override fun onCreate(savedInstanceState: Bundle?) {
        bind = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(bind.root)

        bind.registerObserver.setOnClickListener {
            observerManager(it)
            Log.d("observerTest", "bind.registeredObserver")
        }

        bind.unRegisterObserver.setOnClickListener {
            observerManager(it)
            Log.d("observerTest", "bind.unRegisteredObserver")
        }

        bind.emit.setOnClickListener { timer.start() }

        timer = object : CountDownTimer(303_000, 5_000) {
            override fun onTick(p0: Long) {
                observable.emit()
            }

            override fun onFinish() {
                TODO("Not yet implemented")
            }
        }
    }

    private fun observerManager(v: View) {
        Toast.makeText(this, "${v.id}", Toast.LENGTH_SHORT).show()
        val observerId = bind.id.text.toString()
        if (v.id == bind.unRegisterObserver.id) {
            for (i in observable.observerIdList) {
                if (i == observerId) observable.unRegister(Observer(i))
            }
        } else if (v.id == bind.registerObserver.id){
            if (observerId.isNotBlank() && !observable.observerIdList.contains(observerId)) observable.register(Observer(observerId))
        }
        bind.observerListTV.text = observable.observerIdList.toString()
        Log.d("observerTest", "observerManager")
    }

    override fun onStop() {
        super.onStop()
        timer.cancel()
    }

    override fun onStart() {
        super.onStart()
        timer.start()
    }

    open class Observable {
        private var list = mutableListOf(
            1, 2, 3, 4, 5, 6, 7, 8, 9, 10,
            11, 12, 13, 14, 15, 16, 17, 18, 19, 20,
            21, 22, 23, 24, 25, 26, 27, 28, 29, 30,
            31, 32, 33, 34, 35, 36, 37, 38, 39, 40,
            41, 42, 43, 44, 45, 46, 47, 48, 49, 50,
            51, 52, 53, 54, 55, 56, 57, 58, 59, 60,
            61, 62, 63, 64, 65, 66, 67, 68, 69, 70,
            71, 72, 73, 74, 75, 76, 77, 78, 79, 80,
            81, 82, 83, 84, 85, 86, 87, 88, 89, 90,
            91, 92, 93, 94, 95, 96, 97, 98, 99, 100
        )
        val observerIdList = mutableListOf<String>()
        private val observerList = mutableListOf<Observer>()
        fun register(observer: Observer) {
            observerList.add(observer)
            observerIdList.add(observer.id)
            Log.d("observerTest", "observable register")
        }
        fun unRegister(observer: Observer) {
            val tempObserverIndex = observerIdList.indexOf(observer.id)
            observerList.removeAt(tempObserverIndex)
            observerIdList.removeAt(tempObserverIndex)
            Log.d("observerTest", "observable unRegister")
        }
        fun emit(){
            for (i in observerList) i.update(list[0])
            list.removeAt(0)
            Log.d("observerTest", "observable emit")
        }
    }

    class Observer(var id: String) {
        fun update(input: Int){
            Log.d("observerTest", "$id: $input")
        }
    }
}