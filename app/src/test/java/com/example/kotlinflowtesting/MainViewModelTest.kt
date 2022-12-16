package com.example.kotlinflowtesting

import app.cash.turbine.test

import com.example.kotlinflowtesting.viewmodel.MainViewModel
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow

import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

import org.junit.Before
import org.junit.Test


@ExperimentalCoroutinesApi
class MainViewModelTest {

    private lateinit var viewModel: MainViewModel
    private lateinit var testDispatchers: TestDispatchers


    @Before
    fun setUp() {
        testDispatchers = TestDispatchers()
        viewModel = MainViewModel(testDispatchers)
    }

    @Test
    fun `countDownFlow, properly counts down from 5 to 0`() = runBlocking {
        viewModel.countDownFlow.test {
            for (i in 5 downTo 0) {
                testDispatchers.testDispatcher.advanceTimeBy(1000L)
                val emission = awaitItem()
                assertThat(emission).isEqualTo(i)
            }
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun countDownFlow() = runBlocking {
        viewModel.count.test {
            for (i in 4 downTo 2) {
                testDispatchers.testDispatcher.advanceTimeBy(1000L)
                val emission = awaitItem()
                assertThat(emission).isEqualTo(i)
            }
            cancelAndConsumeRemainingEvents()
        }
    }


    @Test
    fun `squareNumber, number properly squared`() = runBlocking {
        val job = launch {
            viewModel.sharedFlow.test {
                val emission = awaitItem()
                assertThat(emission).isEqualTo(9)
                cancelAndConsumeRemainingEvents()
            }
        }
        viewModel.squareNumber(3)
        job.join()
        job.cancel()
    }


    @Test
    fun `triceNumber,number properly trice`() = runBlocking {
        val job = launch {
            viewModel.sharedFlow.test {
                val emits = awaitItem()
                assertThat(emits).isEqualTo(9)
                cancelAndConsumeRemainingEvents()
            }
        }
        viewModel.incrementCounter(+1)
        job.join()
        job.cancel()

    }

    @Test
    fun triceNumber() = runBlocking {
        val job = launch {
            viewModel.sharedFlow.test {
                val emits = awaitItem()
                assertThat(emits).isEqualTo(9)
                cancelAndConsumeRemainingEvents()
            }
        }
        viewModel.DivsionCounter(2 / 2)
        job.join()
        job.cancel()

    }


    @Test
    fun Subtraction() = runBlocking {
        val job = launch {
            viewModel.sharedFlow.test {
                val emits = awaitItem()
                assertThat(emits).isEqualTo(9)
                cancelAndConsumeRemainingEvents()
            }
        }
        viewModel.SubtractionCounter(2 - 2)
        job.join()
        job.cancel()

    }


}