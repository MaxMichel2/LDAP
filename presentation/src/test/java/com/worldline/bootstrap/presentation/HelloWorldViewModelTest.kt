package com.worldline.bootstrap.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockito_kotlin.whenever
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.*
import com.worldline.bootstrap.domain.model.ExampleResponse
import com.worldline.bootstrap.domain.api.HelloWorldService
import com.worldline.bootstrap.presentation.ui.helloworld.HelloWorldResultStatus
import com.worldline.bootstrap.presentation.ui.helloworld.HelloWorldViewModel
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import kotlin.NullPointerException


/**
 * @author DAOUD Mohamed
 */
@RunWith(JUnit4::class)
class HelloWorldViewModelTest {

    @Mock
    lateinit var helloWorldService: HelloWorldService

    private lateinit var viewModel: HelloWorldViewModel

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun init() {
        MockitoAnnotations.initMocks(this)
        Dispatchers.setMain(TestCoroutineDispatcher())
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun test_value_to_show_ok() = runBlockingTest {
        whenever(helloWorldService.getValueToShow()).thenReturn(ExampleResponse("FROM TEST"))

        viewModel = HelloWorldViewModel(helloWorldService)
        viewModel.helloWorldResult.observeForever {}

        assertEquals(
            viewModel.helloWorldResult.value?.helloWorldResultStatus,
            HelloWorldResultStatus.OK
        )
        assertEquals(viewModel.helloWorldResult.value?.value, "FROM TEST")
    }

    @Test
    fun test_value_to_show_ko() = runBlockingTest {
        whenever(helloWorldService.getValueToShow()).thenThrow(NullPointerException::class.java)

        viewModel = HelloWorldViewModel(helloWorldService)
        viewModel.helloWorldResult.observeForever {}

        assertEquals(
            viewModel.helloWorldResult.value?.helloWorldResultStatus,
            HelloWorldResultStatus.KO
        )
    }

}