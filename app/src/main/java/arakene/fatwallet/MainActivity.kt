package arakene.fatwallet

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import arakene.fatwallet.dto.PayDTO
import arakene.fatwallet.dto.PayType
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    private
    val getLoginResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == Activity.RESULT_OK) {
            val result = Auth.GoogleSignInApi.getSignInResultFromIntent(it.data!!)!!
            if (result.isSuccess) {
                firebaseAuthWithGoogle(result.signInAccount)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//
//        auth = FirebaseAuth.getInstance()
//        db = Firebase.firestore
//
//        loginByEmail()
//
//        saveData()
//        saveData()
//        saveData()
//        saveData()



    }

    private var test = 1

    private fun saveData() {
        db.collection("arakene").document("input").collection("list")
            .add(PayDTO(PayType.input, "${test++}", 123, "For Test"))
            .addOnSuccessListener {
                Toast.makeText(this, "Save Success", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener { e ->
                Log.w("SaveError", e)
            }
    }

    private fun loginByEmail() {
        auth.signInWithEmailAndPassword("test@naver.com", "password")
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "${task.result.user}", Toast.LENGTH_LONG).show()
                }
            }
    }

    private fun loginByGoogle() {
        val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        val googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions)

        getLoginResult.launch(googleSignInClient.signInIntent)
    }

    private fun firebaseAuthWithGoogle(account: GoogleSignInAccount?) {
        val credential = GoogleAuthProvider.getCredential(account?.idToken!!, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    //GoMain
                }
            }
    }
}