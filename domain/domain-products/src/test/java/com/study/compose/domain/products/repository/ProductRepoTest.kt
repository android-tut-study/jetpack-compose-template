package com.study.compose.domain.products.repository

import com.study.compose.core.domain.model.ProductDomain
import com.study.compose.domain.products.CoroutineTestRule
import com.study.compose.domain.products.di.ProductApiService
import com.study.compose.domain.products.mapper.ProductResponseToDomainMapper
import com.study.compose.domain.products.model.response.ProductRatingResponse
import com.study.compose.domain.products.model.response.ProductsResponse
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ProductRepoTest {

    @get:Rule
    val coroutineDispatchers = CoroutineTestRule()

    private val apiService = mockk<ProductApiService>()

    private val mapper = mockk<ProductResponseToDomainMapper>()

    val productRepo = ProductRepoImpl(
        apiService = apiService,
        productDomainMapper = mapper,
        dispatcher = coroutineDispatchers.testDispatcherProvider
    )

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @Test
    fun getProducts() {
    }

    @Test
    fun `fetch Products successfully`() = runTest {
        val expectedDomains = ProductDomain(
            id = 1,
            title = "title 1",
            price = 1.0f,
            description = "1",
            category = " Category 1",
            imageUrl = "imgUrl 1",
        )
        every { mapper.invoke(any()) } returns expectedDomains
        coEvery { apiService.fetchProducts() }
            .coAnswers {
                listOf(
                    ProductsResponse(
                        id = 1,
                        title = "title 1",
                        price = 1.0f,
                        description = "1",
                        category = " Category 1",
                        imageUrl = "imgUrl 1",
                        rating = ProductRatingResponse(0.1f, 1)
                    )
                )
            }
        productRepo.fetchProducts().onEach { actual ->
            assertTrue(actual.isNotEmpty())
            assertEquals(expectedDomains, actual[0])
        }.first()
        coVerify { apiService.fetchProducts() }
    }

    @Test
    fun getCurrentProducts() {
    }
}