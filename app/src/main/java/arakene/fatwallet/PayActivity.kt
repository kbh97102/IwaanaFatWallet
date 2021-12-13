package arakene.fatwallet

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import arakene.fatwallet.databinding.TestMenuLayoutBinding
import arakene.fatwallet.dto.PayDTO
import arakene.fatwallet.dto.PayType
import arakene.fatwallet.viewModel.PayViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore

class PayActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var binding: TestMenuLayoutBinding
    private lateinit var db: FirebaseFirestore
    private lateinit var collection: CollectionReference
    private val model : PayViewModel by viewModels()

    private var testID = 1
    private var updateTestNum = 999

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.test_menu_layout)
        binding.vm = model

//        model.getPayList().observe(this, )

    }

}
