package com.appsnado.mvvmapplication.views

import android.R
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemClickListener
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import androidx.annotation.Nullable
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.appsnado.mvvmapplication.BaseApplication.getPublishSubject
import com.appsnado.mvvmapplication.helper.KeyboardHelper
import com.appsnado.mvvmapplication.interfaces.OnNewPacketReceivedListener
import com.appsnado.mvvmapplication.manager.SharedPreferenceManager
import com.appsnado.mvvmapplication.widget.Titlebar
import io.reactivex.disposables.Disposable


abstract class BaseFragment : Fragment(), View.OnClickListener,
    OnItemClickListener,
    OnNewPacketReceivedListener {
    protected var vieww: View? = null
    var sharedPreferenceManager: SharedPreferenceManager? = null
    var TAG = "Logging Tag"
    var onCreated = false
    var subscription: Disposable? = null
    //  var webCall: Call<WebResponse<Any>>? = null

    /**
     * This is an abstract class, we should inherit our fragment from this class
     */

    override fun onCreate(@Nullable savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPreferenceManager = context?.let { SharedPreferenceManager.getInstance(it) }
        onCreated = false
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        vieww = inflater.inflate(fragmentLayout, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        baseActivity!!.titlebar?.resetViews()
        baseActivity!!.drawerLayout!!.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED) // Default Locked in this project
        baseActivity!!.drawerLayout!!.closeDrawer(GravityCompat.START)
        //subscribeToNewPacket(this)
    }

    /*  val currentUser: UserModel
          get() = sharedPreferenceManager.getCurrentUser()

      val token: String?
          get() = sharedPreferenceManager!!.getString(AppConstants.KEY_TOKEN)

      val oneTimeToken: String?
          get() = sharedPreferenceManager!!.getString(AppConstants.KEY_ONE_TIME_TOKEN)

      fun putOneTimeToken(token: String?) {
          sharedPreferenceManager!!.putValue(AppConstants.KEY_ONE_TIME_TOKEN, token)
      }*/

    abstract val drawerLockMode: Int

    // Use  UIHelper.showSpinnerDialog
    @Deprecated("")
    fun setSpinner(
        adaptSpinner: ArrayAdapter<*>?,
        textView: TextView?,
        spinner: Spinner?
    ) {
        if (adaptSpinner == null || spinner == null) return
        //selected item will look like a spinner set from XML
//        simple_list_item_single_choice
        adaptSpinner.setDropDownViewResource(R.layout.simple_list_item_single_choice)
        spinner.adapter = adaptSpinner
        spinner.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val str = spinner.getItemAtPosition(position).toString()
                if (textView != null) textView.text = str
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    protected abstract val fragmentLayout: Int
    val baseActivity: BaseActivity?
        get() = getActivity() as BaseActivity?

/*
    val homeActivity: HomeActivity
        get() = getActivity() as HomeActivity
*/

    abstract fun setTitlebar(titlebar: Titlebar?)
    abstract fun setListeners()
    override fun onResume() {
        super.onResume()
        onCreated = true
        setListeners()
        if (baseActivity != null) {
            setTitlebar(baseActivity!!.titlebar)
        }
        if (baseActivity != null && baseActivity!!.window.decorView != null) {
            KeyboardHelper.hideSoftKeyboard(
                baseActivity,
                baseActivity!!.window.decorView
            )
        }
    }

    override fun onPause() {
        if (baseActivity != null && baseActivity!!.window.decorView != null) {
            KeyboardHelper.hideSoftKeyboard(
                baseActivity,
                baseActivity!!.window.decorView
            )
        }
        super.onPause()
    }

    fun notifyToAll(event: Int, data: Any?) {
        getPublishSubject()!!.onNext(Pair(event, data))
    }

    /* protected fun subscribeToNewPacket(newPacketReceivedListener: OnNewPacketReceivedListener) {
         subscription = getPublishSubject()
             .observeOn(AndroidSchedulers.mainThread())
             .subscribe(object : Consumer<Pair?>() {
                 @Throws(Exception::class)
                 fun accept(@NonNull pair: Pair) {
                     Log.e("abc", "on accept")
                     newPacketReceivedListener.onNewPacket(pair.first as Int, pair.second)
                 }
             })
     }*/

    override fun onDestroyView() {
        super.onDestroyView()
        Log.e("abc", "onDestroyView")
        if (subscription != null) {
            subscription!!.dispose()
        }
        /* if (webCall != null) {
             webCall.cancel()
         }*/
    }

    fun showNextBuildToast() {
        //UIHelper.showToast(getContext(), "This feature is in progress")
    }

/*
    fun saveAndOpenFile(webResponse: WebResponse<String?>) {
        val fileName: String =
            AppConstants.FILE_NAME + DateManager.getTime(DateManager.getCurrentMillis())
                .toString() + ".pdf"
        val path: String = FileManager.writeResponseBodyToDisk(
            getContext(),
            webResponse.result,
            fileName,
            AppConstants.getUserFolderPath(getContext()),
            true,
            true
        )

//                                final File file = new File(AppConstants.getUserFolderPath(getContext())
//                                        + "/" + fileName + ".pdf");
        val file = File(path)
        Handler().postDelayed(Runnable { FileManager.openFile(getContext(), file) }, 100)
    }
*/

    override fun onNewPacket(event: Int, data: Any?) {
        when (event) {
        }
    }

/*
    val resideMenu: ResideMenu
        get() = homeActivity.getResideMenu()

    // FOR RESIDE MENU
    fun closeMenu() {
        homeActivity.getResideMenu().closeMenu()
    }
*/

    fun logoutClick() {
/*        UIHelper.showAlertDialog(
            "Do you want to logout?",
            "Logout",
            DialogInterface.OnClickListener { dialog, which ->
                dialog.dismiss()
                *//*webCall = getBaseWebServices(true).postAPIAnyObject(
                    PATH_LOGOUT,
                    "",
                    object : IRequestWebResponseAnyObjectCallBack() {
                        fun requestDataResponse(webResponse: WebResponse<Any?>?) {
                            getApp().getBoxStore()
                                .boxFor(MaterialHistoryModelDataBase::class.java).removeAll()
                            sharedPreferenceManager.clearDB()
                            baseActivity!!.clearAllActivitiesExceptThis(MainActivity::class.java)
                        }

                        fun onError(`object`: Any?) {}
                    })*//*
            },
            "Yes",
            DialogInterface.OnClickListener { dialog, which -> dialog.dismiss() },
            "No",
            getContext()
        )*/
    }

/*    fun getBaseWebServices(isShowLoader: Boolean): WebServices {
        return WebServices(baseActivity, token, BaseURLTypes.BASE_URL, isShowLoader)
    }

    val gson: Gson
        get() = baseActivity.getGson()

    val mainActivity: MainActivity
        get() = getActivity() as MainActivity*/
}