package br.com.usemobile.baseactivity.kotlin.core.platform

import android.os.Build
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity;
import android.view.Menu
import android.view.MenuItem
import android.widget.ProgressBar
import br.com.usemobile.baseactivity.kotlin.R
import br.com.usemobile.baseactivity.kotlin.core.extension.activityLogin
import br.com.usemobile.baseactivity.kotlin.core.extension.activityMenu
import br.com.usemobile.baseactivity.kotlin.core.extension.empty
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.progress_bar.*

import kotlinx.android.synthetic.main.toolbar.*

/**
 * Base Activity class.
 *
 * @see AppCompatActivity
 */
abstract class BaseActivity(private val childActivityName: String = String.empty()) : AppCompatActivity() {

    private var doubleBackToExit: Boolean = false
    lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        progressBar = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) menu_frag_progress_bar
        else menu_frag_progress_bar_api21

        setUpActv(savedInstanceState)

    }

    private fun setUpActv(savedInstanceState: Bundle?) {
        when (childActivityName) {
            activityLogin -> {
//                setContentView(R.layout.activity_login)
//                shouldAddFragmentToContainer = false
            }
            activityMenu -> {
//                setContentView(R.layout.menu_activity)
//                setSupportActionBar(menu_toolbar)
//                shouldAddFragmentToContainer = false
            }
            else -> {
                this.overridePendingTransition(R.anim.animation_enter, R.anim.animation_leave)
                setContentView(R.layout.activity_base)
                setSupportActionBar(toolbar_geral)
                img_home?.setOnClickListener { onBackPressed() }
            }
        }

        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(false)

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }


    override fun onBackPressed() {

        this.overridePendingTransition(R.anim.animation_enterback, R.anim.animation_back)
        when {
            doubleBackToExit -> super.onBackPressed()
            childActivityName != activityMenu && childActivityName != activityLogin -> super.onBackPressed()
            else -> showMessage(getString(R.string.title_press_again_to_exit),false)
        }
        this.doubleBackToExit = true
        Handler().postDelayed({ doubleBackToExit = false }, 2000)
    }

    fun showMessage(message: String, isErrorMessage: Boolean = false) {

        if (isErrorMessage) {
            Toasty.error(this, message).show()
        } else {

            Toasty.success(this, message).show()
        }
    }

}
