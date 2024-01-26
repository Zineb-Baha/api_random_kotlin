package com.example.my_android_api

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley

class MainActivity : AppCompatActivity(), SwipeRefreshLayout.OnRefreshListener {

    private val apiUrl = "https://randomuser.me/api/?results=8"
    private var userList = arrayListOf<User>()
    private var recyclerView: RecyclerView? = null
    private var swipeRefreshLayout: SwipeRefreshLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout)

        swipeRefreshLayout?.setOnRefreshListener(this)

        fetchData()

    }

    private fun fetchData() {
        val reqQueue: RequestQueue = Volley.newRequestQueue(this)
        val request = JsonObjectRequest(Request.Method.GET, apiUrl, null, { result ->
            val jsonArray = result.getJSONArray("results")
            userList.clear()

            for (i in 0 until jsonArray.length()) {
                val jsonObj = jsonArray.getJSONObject(i)
                val nameObject = jsonObj.getJSONObject("name")
                val pictureObject = jsonObj.getJSONObject("picture")

                val user = User(
                    fname = nameObject.getString("first"),
                    lname = nameObject.getString("last"),
                    email = jsonObj.getString("email"),
                    phone = jsonObj.getString("phone"),
                    img = pictureObject.getString("large")
                )
                userList.add(user)
            }

            recyclerView?.layoutManager = LinearLayoutManager(this)
            recyclerView?.adapter = UserAdapter(userList)

            // Arrêter l'animation de rafraîchissement
            swipeRefreshLayout?.isRefreshing = false

        }, { err ->
            Log.d("errors : ", err.message.toString())
            // Arrêter l'animation de rafraîchissement en cas d'erreur
            swipeRefreshLayout?.isRefreshing = false
        })
        reqQueue.add(request)
    }

    override fun onRefresh() {
        // Rafraîchir les données
        fetchData()
    }
}