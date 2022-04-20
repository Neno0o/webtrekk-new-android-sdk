/*
 *  MIT License
 *
 *  Copyright (c) 2019 Webtrekk GmbH
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy
 *  of this software and associated documentation files (the "Software"), to deal
 *  in the Software without restriction, including without limitation the rights
 *  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in all
 *  copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  FITNESS FOR A PARTICULAR PURPOSE AND NON INFRINGEMENT. IN NO EVENT SHALL THE
 *  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 *  SOFTWARE.
 *
 */

package webtrekk.android.sdk

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.work.Constraints
import java.util.concurrent.TimeUnit
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Assert.assertEquals
import org.junit.Assert.fail
import org.junit.Before
import org.junit.Test

internal class WebtrekkConfigurationTest {

    lateinit var appContext: Context

    private lateinit var webtrekkConfiguration: WebtrekkConfiguration

    private val dispatcher = Dispatchers.Unconfined

    @Before
    fun setup() {
        appContext = ApplicationProvider.getApplicationContext<Context>()
        webtrekkConfiguration =
            WebtrekkConfiguration.Builder(listOf("123456789", "123"), "www.webtrekk.com")
                .requestsInterval(interval = 20, timeUnit = TimeUnit.MINUTES)
                .disableAutoTracking()
                .build()
        Webtrekk.getInstance().init(appContext, webtrekkConfiguration)
    }

    @Test
    fun throw_error_if_trackIds_has_null_or_empty_values() {
        val errorMsg =
            "trackIds is missing in the configurations. trackIds is required in the configurations."

        try {
            val configuration = WebtrekkConfiguration.Builder(listOf(), "www.webtrekk.com").build()
            // Invoke trackIds
            configuration.trackIds
            fail("Expected an IllegalStateException to be thrown!")
        } catch (e: IllegalStateException) {
            assertThat(e.message, `is`(errorMsg))
        }
    }

    @Test
    fun throw_error_if_trackDomain_is_null_or_blank() {
        val errorMsg =
            "trackDomain is missing in the configurations. trackDomain is required in the configurations."

        try {
            val configuration = WebtrekkConfiguration.Builder(listOf("123"), "").build()
            // Invoke trackDomain
            configuration.trackDomain
            fail("Expected an IllegalStateException to be thrown!")
        } catch (e: IllegalStateException) {
            assertThat(e.message, `is`(errorMsg))
        }
    }

    @Test
    fun test_default_values() {
        val defaultWebtrekkConfiguration =
            WebtrekkConfiguration.Builder(listOf("123"), "www.webtrekk.com").build()

        val expectedWebtrekkConfiguration =
            WebtrekkConfiguration.Builder(listOf("123"), "www.webtrekk.com")
                .logLevel(DefaultConfiguration.LOG_LEVEL_VALUE)
                .requestsInterval(interval = DefaultConfiguration.REQUESTS_INTERVAL)
                .workManagerConstraints(DefaultConfiguration.WORK_MANAGER_CONSTRAINTS)
                .okHttpClient(DefaultConfiguration.OKHTTP_CLIENT).build()

        assertEquals(defaultWebtrekkConfiguration, expectedWebtrekkConfiguration)
    }

    @Test
    fun test_webtrekk_configurations_are_set() {
        assertEquals(webtrekkConfiguration.trackIds, listOf("123456789", "123"))
        assertEquals(webtrekkConfiguration.trackDomain, "www.webtrekk.com")
        assertEquals(
            webtrekkConfiguration.logLevel,
            DefaultConfiguration.LOG_LEVEL_VALUE
        )
        assertEquals(webtrekkConfiguration.requestsInterval, 20)
        assertEquals(webtrekkConfiguration.autoTracking, false)
        assertEquals(webtrekkConfiguration.fragmentsAutoTracking, false)
        assertEquals(
            webtrekkConfiguration.workManagerConstraints,
            DefaultConfiguration.WORK_MANAGER_CONSTRAINTS
        )
        assertEquals(webtrekkConfiguration.okHttpClient, DefaultConfiguration.OKHTTP_CLIENT)
    }

    @Test
    fun test_fragments_auto_track_is_disabled_when_auto_track_is_disabled() {
        val defaultWebtrekkConfiguration =
            WebtrekkConfiguration.Builder(listOf("123"), "www.webtrekk.com")
                .disableAutoTracking().build()

        assertEquals(defaultWebtrekkConfiguration.fragmentsAutoTracking, false)
    }

    @Test
    fun testToJsonMethod() {
        val json = webtrekkConfiguration.toJson()
        val config = WebtrekkConfiguration.fromJson(json)
        assertEquals(config.trackIds, webtrekkConfiguration.trackIds)
    }
}