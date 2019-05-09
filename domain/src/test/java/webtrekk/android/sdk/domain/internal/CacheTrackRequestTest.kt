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

package webtrekk.android.sdk.domain.internal

import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import io.mockk.coEvery
import io.mockk.mockkClass
import webtrekk.android.sdk.data.entity.TrackRequest
import webtrekk.android.sdk.data.repository.TrackRequestRepository
import webtrekk.android.sdk.domain.util.cacheTrackRequestParams
import webtrekk.android.sdk.domain.util.trackRequest
import java.io.IOException

internal class CacheTrackRequestTest : StringSpec({

    val trackRequestRepository = mockkClass(TrackRequestRepository::class)
    val cacheTrackRequest = CacheTrackRequest(trackRequestRepository)

    "cache trackRequest and return success" {
        val resultSuccess = Result.success(trackRequest)

        coEvery {
            trackRequestRepository.addTrackRequest(trackRequest)
        } returns resultSuccess

        cacheTrackRequest(cacheTrackRequestParams) shouldBe (resultSuccess)
    }

    "cache trackRequest and return failure encapsulating the exception" {
        val resultFailure = Result.failure<TrackRequest>(IOException("error"))

        coEvery {
            trackRequestRepository.addTrackRequest(trackRequest)
        } returns resultFailure

        cacheTrackRequest(cacheTrackRequestParams) shouldBe (resultFailure)
    }
})
