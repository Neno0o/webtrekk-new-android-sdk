package com.example.webtrekk.androidsdk

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_url_trakcing.*

import webtrekk.android.sdk.Webtrekk
import webtrekk.android.sdk.events.PageViewEvent
import webtrekk.android.sdk.events.eventParams.CampaignParameters

class UrlActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_url_trakcing)

        button7.setOnClickListener {
            val url =
                Uri.parse("https://testurl.com/?wt_mc=email.newsletter.nov2020.thursday&wt_cc45=parameter45")
            Webtrekk.getInstance().trackUrl(url)
            Webtrekk.getInstance().trackPage(this)
        }

        button8.setOnClickListener {
            val url =
                Uri.parse("https://testurl.com/?abc=email.newsletter.nov2020.thursday&wt_cc12=parameter12")
            Webtrekk.getInstance().trackUrl(url, "abc")
            Webtrekk.getInstance().trackPage(this)
        }

        button9.setOnClickListener {
            val campaignProperties = CampaignParameters("email.newsletter.nov2020.thursday")
            campaignProperties.mediaCode = "abc"
            campaignProperties.oncePerSession = true
            campaignProperties.action = CampaignParameters.CampaignAction.VIEW
            campaignProperties.customParameters = mapOf(12 to "camParam1")

            val event = PageViewEvent(name = "TestCampaign")
            event.campaignParameters = campaignProperties
            Webtrekk.getInstance().trackPage(event)

        }
    }
}