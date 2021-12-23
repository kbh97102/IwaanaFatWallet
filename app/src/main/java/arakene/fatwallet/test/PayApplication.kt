package arakene.fatwallet.test

import android.app.Application
import arakene.fatwallet.db.PayDB
import arakene.fatwallet.repository.PayRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class PayApplication : Application() {

    val applicationScope = CoroutineScope(SupervisorJob())

    val db by lazy { PayDB.getDatabase(this, applicationScope) }
    val repository by lazy { PayRepository(db.payDao()) }
}