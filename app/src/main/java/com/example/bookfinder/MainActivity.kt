package com.example.bookfinder

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bookfinder.adapters.RecyclerItemAdapter
import com.example.bookfinder.views.MainView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private var etSearh: EditText? = null
    private var rvBooks: RecyclerView? = null
    private var mainView: MainView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.setContentView(R.layout.activity_main)

        this.etSearh = et_search
        this.rvBooks = rv_books

        this.etSearh?.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                onLoad(p0.toString())
            }
        })

        this.onLoad(this.etSearh?.text.toString())
    }

    private fun onLoad(q: String) {
        this.mainView = ViewModelProvider(this).get(MainView::class.java)
        this.mainView!!.getObserver().observe(this) {
            it?.let {
                val recyclerItemAdapter = RecyclerItemAdapter(this, it)

                this.rvBooks?.layoutManager = LinearLayoutManager(this)
                this.rvBooks?.adapter = recyclerItemAdapter

                recyclerItemAdapter.notifyDataSetChanged()
            }
        }

        this.mainView!!.call(q)
    }
}