package arakene.fatwallet.test

import android.app.Application
import arakene.fatwallet.db.PayDB
import arakene.fatwallet.repository.PayRepository
import arakene.fatwallet.repository.TagRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class PayApplication : Application() {

    val applicationScope = CoroutineScope(SupervisorJob())

    val db by lazy { PayDB.getDatabase(this, applicationScope) }
    val payRepository by lazy { PayRepository(db.payDao()) }
    val tagRepository by lazy { TagRepository(db.tagDao()) }
}