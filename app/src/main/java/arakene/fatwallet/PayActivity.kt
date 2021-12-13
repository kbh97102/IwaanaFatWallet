package arakene.fatwallet

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import arakene.fatwallet.databinding.TestMenuLayoutBinding
import arakene.fatwallet.dto.PayDTO
import arakene.fatwallet.dto.PayType
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore

class PayActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var binding: TestMenuLayoutBinding
    private lateinit var db: FirebaseFirestore
    private lateinit var collection: CollectionReference

    private var testID = 1
    private var updateTestNum = 999

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.test_menu_layout)


        auth = FirebaseAuth.getInstance()

        db = FirebaseFirestore.getInstance()

        collection = db.collection(auth.currentUser!!.uid).document("pay").collection("list")

        binding.logoutButton.setOnClickListener {
            auth.signOut()
        }
        binding.addButton.setOnClickListener {
            saveData()
        }
        binding.deleteButton.setOnClickListener {
//            DfAPV3wMhREkNewwJwQW -> test id
            deleteData("2CqSctPSKYRR2iPW2tnl")
        }
        binding.updateButton.setOnClickListener {
            updateData(
                "DfAPV3wMhREkNewwJwQW",
                PayDTO(PayType.input, "999", 456456, "${updateTestNum++}")
            )
        }
    }

    private fun saveData() {
        if (auth.currentUser != null) {
            collection
                .add(PayDTO(PayType.input, "${testID++}", 123, "For Test"))
                .addOnSuccessListener {
                    Toast.makeText(this, "Save Success", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener { e ->
                    Log.w("SaveError", e)
                }
        }

    }

    /*
    삭제
    리스트에서 선택된 작업에 대해서 삭제
    넘어오는 값은
     */
    private fun deleteData(id: String) {
        if (auth.currentUser == null) {
            return
        }
        collection.document(id)
            .delete()
            .addOnCompleteListener {
                Log.d("Delete", "Success")
            }
            .addOnFailureListener {
                Log.d("Delete", "Fail", it)
            }
    }

    private fun updateData(id: String, data: PayDTO) {
        if (auth.currentUser == null) {
            return
        }
        collection.document(id)
            .set(data)
            .addOnCompleteListener {
                Log.d("Update", "Success")
            }
            .addOnFailureListener {
                Log.d("Update", "Fail", it)
            }
    }
}
