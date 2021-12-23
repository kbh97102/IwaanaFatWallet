package arakene.fatwallet.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import arakene.fatwallet.data.PayData
import arakene.fatwallet.data.PayType
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*
import kotlin.collections.ArrayList

class PayListViewModel : ViewModel() {

    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()
    private val collection: CollectionReference =
        db.collection(auth.currentUser!!.uid).document("pay").collection("list")

    private var list: List<PayData> = ArrayList()

    private val inputText = MutableLiveData<String>()
    private val outputText = MutableLiveData<String>()

    init {
        collection.addSnapshotListener { value, error ->
            list = value!!.toObjects(PayData::class.java)
            getTotalPay()
        }
    }

    fun getOutput(): MutableLiveData<String> = outputText
    fun getInput(): MutableLiveData<String> = inputText

    private fun getTotalPay() {
        val calendar = Calendar.getInstance()
        val year = calendar[Calendar.YEAR]
        val month = calendar[Calendar.MONTH] + 1
        var inputSum = 0L
        var outputSum = 0L

        list.let {
            it.forEach {
                if (it.date!!.startsWith("$year.$month")) {
                    if (it.type == PayType.input) {
                        inputSum += it.price!!
                    } else {
                        outputSum += it.price!!
                    }
                }
            }
        }
        inputText.value = inputSum.toString()
        outputText.value = outputSum.toString()
    }


}