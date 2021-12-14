package arakene.fatwallet

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import arakene.fatwallet.databinding.TestMenuLayoutBinding
import arakene.fatwallet.recyclerView.PayListAdapter
import arakene.fatwallet.recyclerView.SwipeHelper
import arakene.fatwallet.viewModel.PayViewModel

/* TODO 
    1. Pay 태그 추가
    2. Pay 에 날짜 추가
    3. UI 개선
*/

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
        val swipeHelper = SwipeHelper().apply {
            setClamp(400f)
        }
        val itemTouchHelper = ItemTouchHelper(swipeHelper).apply {
            attachToRecyclerView(binding.payListRecyclerview)
        }
        payAdapter = PayListAdapter(model)
        binding.payListRecyclerview.apply {
//            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@PayActivity)
            adapter = payAdapter
            setOnTouchListener { _, _ ->
                swipeHelper.removePreviousClamp(this)
                false
            }
        }

    }

}
