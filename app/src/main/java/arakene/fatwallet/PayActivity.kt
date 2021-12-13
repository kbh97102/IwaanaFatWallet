package arakene.fatwallet

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import arakene.fatwallet.databinding.TestMenuLayoutBinding
import arakene.fatwallet.recyclerView.PayListAdapter
import arakene.fatwallet.viewModel.PayViewModel

class PayActivity : AppCompatActivity() {

    private lateinit var binding: TestMenuLayoutBinding
    private lateinit var payAdapter: PayListAdapter
    private val model: PayViewModel by viewModels()

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.test_menu_layout)
        binding.vm = model

        initPayListView()

        model.getPayList().observe(this, {
            payAdapter.setItems(it)
            payAdapter.notifyDataSetChanged()
        })
    }

    private fun initPayListView() {
        payAdapter = PayListAdapter()
        binding.payListRecyclerview.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@PayActivity)
            adapter = payAdapter
        }
    }

}
