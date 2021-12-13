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
import com.google.firebase.firestore.FirebaseFirestore

class PayActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var binding: TestMenuLayoutBinding
    private lateinit var db: FirebaseFirestore

    private var testID = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.test_menu_layout)


        auth = FirebaseAuth.getInstance()

        db = FirebaseFirestore.getInstance()

        binding.logoutButton.setOnClickListener {
            auth.signOut()
        }
        binding.addButton.setOnClickListener {
            saveData()
        }
    }

    private fun saveData() {
        if (auth.currentUser != null) {
            db.collection(auth.currentUser!!.uid).document("pay").collection("list")
                .add(PayDTO(PayType.input, "${testID++}", 123, "For Test"))
                .addOnSuccessListener {
                    Toast.makeText(this, "Save Success", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener { e ->
                    Log.w("SaveError", e)
                }
        }

    }

}