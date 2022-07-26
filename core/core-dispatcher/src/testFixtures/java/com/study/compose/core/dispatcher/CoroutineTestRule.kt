package com.study.compose.core.dispatcher

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestCoroutineScheduler
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.rules.TestWatcher
import org.junit.runner.Description

// Currently, Android Kotlin test fixtures are not supported there fully yet
// tracked at https://youtrack.jetbrains.com/issue/KT-50667
@OptIn(ExperimentalCoroutinesApi::class)
class CoroutineTestRule(
    val testDispatcher: TestDispatcher = StandardTestDispatcher(
        TestCoroutineScheduler(),
        "scheduler"
    )
) : TestWatcher() {

    val testDispatcherProvider = object : CoroutineDispatchers {
        override val io: CoroutineDispatcher =
            StandardTestDispatcher(testDispatcher.scheduler, "IO")
        override val main: CoroutineDispatcher =
            StandardTestDispatcher(testDispatcher.scheduler, "main")
        override val computation: CoroutineDispatcher =
            UnconfinedTestDispatcher(testDispatcher.scheduler)
    }

    override fun starting(description: Description) {
        super.starting(description)
        Dispatchers.setMain(testDispatcher)
    }

    override fun finished(description: Description) {
        super.finished(description)
        Dispatchers.resetMain()
    }
}