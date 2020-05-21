package com.viewsonic.coroutinesample

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.viewsonic.coroutinesample.Functions.fetchFirstUser
import com.viewsonic.coroutinesample.Functions.fetchSecondUser
import com.viewsonic.coroutinesample.Functions.showUser
import com.viewsonic.coroutinesample.Functions.showUsers
import kotlinx.coroutines.*


//REF: https://blog.mindorks.com/mastering-kotlin-coroutines-in-android-step-by-step-guide?fbclid=IwAR24V-URyf4ze-wf-Q_v0z0JN2jy-89AiS0Nck7RaDTn_fhspEfh1SKIn5E
class MainActivity : AppCompatActivity() {

	val TAG = javaClass.simpleName

	val handler = CoroutineExceptionHandler { _, exception ->
		Log.d(TAG, "$exception handled !")
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)
	}

	// Use withContext when you do not need the parallel execution.
	suspend fun task1() {
		GlobalScope.launch(Dispatchers.Main + handler) {
			val userOne = withContext(Dispatchers.IO) { fetchFirstUser() }
			val userTwo = withContext(Dispatchers.IO) { fetchSecondUser() }
			showUsers(userOne, userTwo)
		}
	}

	// Use async only when you need the parallel execution.
	suspend fun task2() {
		GlobalScope.launch(Dispatchers.Main) {
			val userOne = async(Dispatchers.IO) { fetchFirstUser() }
			val userTwo = async(Dispatchers.IO) { fetchSecondUser() }
			showUsers(userOne.await(), userTwo.await())
		}
	}

	suspend fun fetchUserWay1(): User {
		return GlobalScope.async(Dispatchers.IO) {
			// make network call
			User()
		}.await()
	}

	suspend fun fetchUserWay2(): User {
		return withContext(Dispatchers.IO) {
			// make network call
			User()
		}
	}


	suspend fun fetchAndShowUser() {
		val user = fetchUserWay1() // fetch on IO thread
		showUser(user) // back on UI thread
	}

	suspend fun fetchUserAndSaveInDatabase() {
		// fetch user from network
		// save user in database
		// and do not return anything
	}


	class User()
}
