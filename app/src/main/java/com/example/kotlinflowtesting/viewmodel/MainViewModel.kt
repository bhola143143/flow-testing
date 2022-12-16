package com.example.kotlinflowtesting.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kotlinflowtesting.DispatcherProvider


import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class MainViewModel(
    private val dispatchers: DispatcherProvider


) : ViewModel() {

    val countDownFlow = flow<Int> {
        val startingValue = 5
        var currentValue = startingValue
        emit(startingValue)
        while (currentValue > 0) {
            delay(1000L)
            currentValue--
            emit(currentValue)
        }
    }.flowOn(dispatchers.main)


    val count = flow<Int> {
        val currentvalue = 4
        var previousvalue = currentvalue
        emit(currentvalue)
        while (previousvalue > 2) {
            delay(1000L)
            previousvalue--
            emit(previousvalue)
        }
    }.flowOn(dispatchers.main)

    private val _stateFlow = MutableStateFlow(0)
    val stateFlow = _stateFlow.asStateFlow()

    private val _sharedFlow = MutableSharedFlow<Int>(replay = 5)
    val sharedFlow = _sharedFlow.asSharedFlow()


    init {
        squareNumber(3)
        viewModelScope.launch(dispatchers.main) {
            sharedFlow.collect {
                delay(2000L)
                println("FIRST FLOW: The received number is $it")
            }
        }
        viewModelScope.launch(dispatchers.main) {
            sharedFlow.collect {
                delay(3000L)
                println("SECOND FLOW: The received number is $it")
            }
        }


    }

    fun squareNumber(number: Int) {
        viewModelScope.launch(dispatchers.main) {
            _sharedFlow.emit(number * number)
        }
    }

    fun incrementCounter(number: Int) {
        viewModelScope.launch(dispatchers.main) {
            _stateFlow.emit(number + number)
        }

    }

    fun DivsionCounter(number: Int) {
        viewModelScope.launch(dispatchers.main) {
            _stateFlow.emit(number / number)
        }
    }

    fun SubtractionCounter(number: Int) {
        viewModelScope.launch(dispatchers.main) {
            _stateFlow.emit(number - number)
        }
    }

   /* private fun collectFlow() {
        val flow = flow {
            delay(250L)
            emit("Appetizer")
            delay(1000L)
            emit("Main dish")
            delay(100L)
            emit("Dessert")
        }
        viewModelScope.launch {
            flow.onEach {
                println("FLOW: $it is delivered")
            }
                .collectLatest {
                    println("FLOW: Now eating $it")
                    delay(1500L)
                    println("FLOW: Finished eating $it")
                }
        }
    }*/

}