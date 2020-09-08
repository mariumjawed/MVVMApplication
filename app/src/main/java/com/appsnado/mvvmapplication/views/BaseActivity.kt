package com.appsnado.mvvmapplication.views

import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.appsnado.mvvmapplication.constant.AppConstants
import com.appsnado.mvvmapplication.widget.LeftSideMenuFragment
import com.appsnado.mvvmapplication.widget.Titlebar


abstract class BaseActivity : AppCompatActivity() {

    var drawerLayout: DrawerLayout? = null
        protected set
    var titlebar: Titlebar? = null
        protected set
    val leftSideMenuFragment: LeftSideMenuFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(viewId)
        setAndBindTitleBar()
        drawerLayout = findViewById(drawerLayoutId)
        drawerLayout?.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_SECURE,
            WindowManager.LayoutParams.FLAG_SECURE
        )
        //     getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        addDrawerFragment()

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
//        }
    }

    /** window.setFlags
     * Give Resource id of the view you want to inflate
     *
     * @return
     */
    protected abstract val viewId: Int
    protected abstract val titlebarLayoutId: Int
    protected abstract val drawerLayoutId: Int
    protected abstract val dockableFragmentId: Int
    protected abstract val drawerFragmentId: Int

    fun addDrawerFragment() {
        //        leftSideMenuFragment = LeftSideMenuFragment.newInstance();
//        getSupportFragmentManager().beginTransaction().replace(getDrawerFragmentId(), leftSideMenuFragment).commit();
    }

    private fun setAndBindTitleBar() {
        /*  titleBar = findViewById(titlebarLayoutId)
          titleBar?.visibility = View.GONE
          titleBar?.resetViews()*/
    }

    fun addDockableFragment(fragment: Fragment, isTransition: Boolean) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        if (isTransition) {
/*
            fragmentTransaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left)
*/
        }
        fragmentTransaction.replace(dockableFragmentId, fragment)
            .addToBackStack(fragment.javaClass.simpleName)
            .commit()
    }

    fun openActivity(tClass: Class<*>?) {
        val i = Intent(this, tClass)
        startActivity(i)
    }


    fun openActivity(tClass: Class<*>?, `object`: String?) {
        val i = Intent(this, tClass)
        i.putExtra(AppConstants.JSON_STRING_KEY, `object`)
        startActivity(i)
    }

    fun reload() {
        val intent = intent
        overridePendingTransition(0, 0)
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
        finish()
        overridePendingTransition(0, 0)
        startActivity(intent)
    }

    fun clearAllActivitiesExceptThis(cls: Class<*>?) {
        val intents = Intent(this, cls)
        intents.addFlags(
            Intent.FLAG_ACTIVITY_NEW_TASK
                    or Intent.FLAG_ACTIVITY_CLEAR_TOP
                    or Intent.FLAG_ACTIVITY_CLEAR_TASK
        )
        startActivity(intents)
        finish()
    }

    fun emptyBackStack() {
        val fm = supportFragmentManager ?: return
        for (i in 0 until fm.backStackEntryCount) {
            fm.popBackStack()
        }
    }

    fun popBackStack() {
        if (supportFragmentManager == null) {
            return
        } else {
            supportFragmentManager.popBackStack()
        }
    }

    fun popStackTill(stackNumber: Int) {
        val fm = supportFragmentManager ?: return
        for (i in stackNumber until fm.backStackEntryCount) {
            fm.popBackStack()
        }
    }

    fun popStackTill(tag: String?) {
        val fm = supportFragmentManager ?: return
        val backStackEntryCount = fm.backStackEntryCount
        for (i in backStackEntryCount - 1 downTo 1) {
            if (fm.getBackStackEntryAt(i).name.equals(tag, ignoreCase = true)) {
                return
            } else {
                fm.popBackStack()
            }
        }
    }
}