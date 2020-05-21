package com.viewsonic.coroutinesample

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.viewsonic.coroutinesample.Functions.fetchFirstUser
import com.viewsonic.coroutinesample.Functions.fetchSecondUser
import com.viewsonic.coroutinesample.Functions.showUsers
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class SecondActivity() : AppCompatActivity(), CoroutineScope {
    val TAG = javaClass.simpleName

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job + handler
    private lateinit var job: Job

    private val handler = CoroutineExceptionHandler { _, exception ->
        Log.d(TAG, "$exception handled !")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
        job = Job()

        launch {
            val userOne = async(Dispatchers.IO) { fetchFirstUser() }
            val userTwo = async(Dispatchers.IO) { fetchSecondUser() }
            showUsers(userOne.await(), userTwo.await())
        }
    }

    override fun onDestroy() {
        job.cancel()
        super.onDestroy()
    }
}
