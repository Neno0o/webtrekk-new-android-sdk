package com.example.webtrekk.androidsdk.mapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.webtrekk.androidsdk.R
import kotlinx.android.synthetic.main.activity_user_matching.btnCustomActionWithCustomUserId
import kotlinx.android.synthetic.main.activity_user_matching.btnCustomActionWithMappUserId
import kotlinx.android.synthetic.main.activity_user_matching.btnDisableUserMatching
import kotlinx.android.synthetic.main.activity_user_matching.btnEnableUserMatching
import kotlinx.android.synthetic.main.activity_user_matching.btnSendData
import webtrekk.android.sdk.Webtrekk
import webtrekk.android.sdk.events.ActionEvent
import webtrekk.android.sdk.events.eventParams.EventParameters
import webtrekk.android.sdk.events.eventParams.SessionParameters
import webtrekk.android.sdk.events.eventParams.UserCategories

class UserMatchingActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_matching)

        btnEnableUserMatching.setOnClickListener {
            Webtrekk.getInstance().setUserMatchingEnabled(true)
        }

        btnDisableUserMatching.setOnClickListener {
            Webtrekk.getInstance().setUserMatchingEnabled(false)
        }

        btnCustomActionWithCustomUserId.setOnClickListener {
            trackCustomActionWithCustomUserId()
        }

        btnCustomActionWithMappUserId.setOnClickListener {
            trackCustomActionWithMappUserId()
        }

        btnSendData.setOnClickListener {
            Webtrekk.getInstance().sendRequestsNowAndClean()
        }
    }

    private fun trackCustomActionWithCustomUserId() {
        val eventParameters = EventParameters(mapOf(Pair(20, "ck20Param1")))
        //user properties
        val userCategories = UserCategories()
        userCategories.customCategories = mapOf(Pair(20, "userParam1"))
        userCategories.birthday = UserCategories.Birthday(12, 1, 1993)
        userCategories.city = "Paris"
        userCategories.country = "France"
        userCategories.customerId = "CustomerID"
        userCategories.gender = UserCategories.Gender.FEMALE
        userCategories.emailReceiverId="111111111"

        //sessionproperties
        val sessionParameters = SessionParameters(mapOf(Pair(10, "sessionParam1")))

        val event = ActionEvent("TestAction")
        event.eventParameters = eventParameters
        event.userCategories = userCategories
        event.sessionParameters = sessionParameters

        Webtrekk.getInstance().trackAction(event)
    }

    private fun trackCustomActionWithMappUserId() {
        val eventParameters = EventParameters(mapOf(Pair(20, "ck20Param1")))
        //user properties
        val userCategories = UserCategories()
        userCategories.customCategories = mapOf(Pair(20, "userParam1"))
        userCategories.birthday = UserCategories.Birthday(4, 7, 1984)
        userCategories.city = "Novi Pazar"
        userCategories.country = "Serbia"
        userCategories.customerId = "1324324534"
        userCategories.gender = UserCategories.Gender.MALE

        //sessionproperties
        val sessionParameters = SessionParameters(mapOf(Pair(10, "sessionParam1")))

        val event = ActionEvent("CustomActionWithMappUserId")
        event.eventParameters = eventParameters
        event.userCategories = userCategories
        event.sessionParameters = sessionParameters

        Webtrekk.getInstance().trackAction(event)
    }
}