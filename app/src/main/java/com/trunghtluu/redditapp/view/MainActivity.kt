package com.trunghtluu.redditapp.view

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.trunghtluu.redditapp.R
import com.trunghtluu.redditapp.adapter.UserAdapter
import com.trunghtluu.redditapp.model.Child
import com.trunghtluu.redditapp.model.Reddit
import com.trunghtluu.redditapp.viewmodel.MainViewModel

import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), UserAdapter.UserAdapterDelegate {
    override fun userSelected(selected: Child) {
        val data = selected.data
        val message = "Check out what " + data.author + " said on Reddit: \"" + data.title + "\""

        Log.d("TAG_X", message)

        val mIntent = Intent(Intent.ACTION_SEND)
        mIntent.data = Uri.parse("mailto:")
        mIntent.type = "text/plain"

        mIntent.putExtra(Intent.EXTRA_TEXT, message)

        try{
            startActivity(Intent.createChooser(mIntent, ""))
        }catch (e : Exception){
            Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
        }
    }

    private lateinit var mainViewModel: MainViewModel

    lateinit var observer: Observer<Reddit>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainViewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        observer = object : Observer<Reddit> {
            override fun onChanged(subreddit: Reddit) {
                setupRV(subreddit.data.children)
            }
        }

        main_search_bt.setOnClickListener{
            mainViewModel.getSubreddit(main_search_sv.query.toString())
        }

        mainViewModel.getResultLiveData().observe(this@MainActivity, observer)

    }

    private fun setupRV(response: List<Child>) {
        val adapter : UserAdapter = UserAdapter(response, this, this)
        val itemDecoration = DividerItemDecoration(this, RecyclerView.VERTICAL)
        main_search_rv.addItemDecoration(itemDecoration)
        main_search_rv.setLayoutManager(LinearLayoutManager(this@MainActivity))
        main_search_rv.setAdapter(adapter)
    }
}
