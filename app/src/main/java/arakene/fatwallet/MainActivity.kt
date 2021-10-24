package arakene.fatwallet

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class MainActivity : AppCompatActivity() {

    private lateinit var auth : FirebaseAuth

    private val getLoginResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ){
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


        auth = FirebaseAuth.getInstance()

        loginByGoogle()
    }

    private fun loginByEmail() {
        auth.signInWithEmailAndPassword("test@naver.com", "password").addOnCompleteListener {
            task ->
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
            .addOnCompleteListener {
                task ->
                if (task.isSuccessful) {
                    //GoMain
                }
            }
    }
}