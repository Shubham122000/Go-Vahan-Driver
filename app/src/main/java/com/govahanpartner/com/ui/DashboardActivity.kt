package com.govahanpartner.com.ui

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import android.widget.AdapterView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import com.bumptech.glide.Glide
import com.google.android.material.navigation.NavigationView
import com.govahanpartner.com.R
import com.govahanpartner.com.adapter.MenuListAdapter
import com.govahanpartner.com.base.BaseActivity
import com.govahanpartner.com.databinding.ActivityDashboardBinding
import com.govahanpartner.com.databinding.DialogLogoutBinding
import com.govahanpartner.com.model.DashboardMenuModel
import com.govahanpartner.com.ui.common.*
import com.govahanpartner.com.ui.fragments.HomeFragment
import com.govahanpartner.com.ui.vendor.*
import com.govahanpartner.com.utils.CommonUtils
import com.govahanpartner.com.utils.toast
import com.govahanpartner.com.viewmodel.ProfileViewModel

import dagger.hilt.android.AndroidEntryPoint
import de.hdodenhof.circleimageview.CircleImageView

@AndroidEntryPoint
class DashboardActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var binding: ActivityDashboardBinding
    private var menuList: ArrayList<DashboardMenuModel>? = null
    private var menuListAdapter: MenuListAdapter? = null
    private var exit = false
    private lateinit var drawerLayout: DrawerLayout
    private var drawerToggle: ActionBarDrawerToggle? = null
    private val viewModel: ProfileViewModel by viewModels()
    lateinit var name: String
    lateinit var email: String
    lateinit var photo: CircleImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_dashboard)


        menuList = ArrayList<DashboardMenuModel>()

        binding.drawerToolbar.profilePic.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }
        binding.drawerToolbar.ivNotifi.setOnClickListener {
            startActivity(Intent(this, NotificationActivity::class.java))
        }
        binding.drawerToolbar.wallet.setOnClickListener {
            startActivity(Intent(this, WalletActivity::class.java).putExtra("flag1","1"))
        }
        viewModel.progressBarStatus.observe(this) {
            if (it) {
                showProgressDialog()
            } else {
                hideProgressDialog()
            }
           }
        viewModel.PrivacyPolicyModel1.observe(this){
            if (it.status==1){
            }else{
                userPref.clearPref()
                startActivity(Intent(this, LoginAsActivity::class.java))
                finishAffinity()
            }
        }

        binding.drawerToolbar.opendrawer.setOnClickListener {
            viewModel.GetProfileAPI(
                "Bearer " + userPref.getToken().toString(),
            )
        }
        viewModel.GetProfileAPI(
            "Bearer " + userPref.getToken().toString(),
        )
        viewModel.getProfileResponse.observe(this) {
            if (it?.status == 1) {
                binding.header.tvUserName.text = it.data?.name.toString()
                binding.header.tvEmail.text = it.data?.email
                Glide.with(this).load(it.data?.profileImage).placeholder(R.drawable.profile)
                    .into(binding.header.imgUser)
                Glide.with(this).load(it.data?.profileImage).placeholder(R.drawable.profile)
                    .into(binding.drawerToolbar.profilePic)
            } else {
//                toast(.message)
            }
        }

        binding.drawerLayout.addDrawerListener(object : DrawerLayout.DrawerListener {
            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
            }

            override fun onDrawerOpened(drawerView: View) {
                //setNavigationData();
            }

            override fun onDrawerClosed(drawerView: View) {
            }

            override fun onDrawerStateChanged(newState: Int) {
            }
        })

        CommonUtils.setFragment(HomeFragment(), false, this, R.id.flContent)
        setNavigationBar()
        prepareNavMenuList()
        navMenuClickListener()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        drawerToggle = setupDrawerToggle()
        drawerToggle?.isDrawerIndicatorEnabled = true
        drawerToggle?.syncState()
        drawerToggle?.let { binding.drawerLayout.addDrawerListener(it) }

        binding.drawerToolbar.opendrawer.setOnClickListener {
            // open drawer here
            binding.drawerLayout.openDrawer(GravityCompat.START)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // The action bar home/up action should open or close the drawer.
        when (item.itemId) {
            android.R.id.home -> {
                binding.drawerLayout.openDrawer(GravityCompat.START)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        val drawer: DrawerLayout = findViewById(R.id.drawer_layout)
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        } else {
            if (exit) {
                super.onBackPressed()
                finish() // finish activity
            } else {
                utils.toaster("Press Back again to Exit.")
                exit = true
                Handler().postDelayed({ exit = false }, (3 * 1000).toLong())
              }
        }
    }

    private fun setNavigationBar() {
        binding.nvView.setNavigationItemSelectedListener(this)

        val toggle = ActionBarDrawerToggle(
            this,
            binding.drawerLayout,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )

        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        prepareNavMenuList()
        navMenuClickListener()

        binding.drawerToolbar.toolbar.setNavigationOnClickListener {
            if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
                binding.drawerLayout.closeDrawer(GravityCompat.START)
            } else {
                binding.drawerLayout.openDrawer(GravityCompat.START)
            }
        }

        binding.drawerLayout.addDrawerListener(object : DrawerLayout.DrawerListener {
            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
            }

            override fun onDrawerOpened(drawerView: View) {
            }

            override fun onDrawerClosed(drawerView: View) {

            }

            override fun onDrawerStateChanged(newState: Int) {

            }
        })
    }

    private fun prepareNavMenuList() {

        menuList!!.clear()
        menuList!!.add(DashboardMenuModel(this.getString(R.string.myprofile)))
        menuList!!.add(DashboardMenuModel(this.getString(R.string.transactionreport)))
        menuList!!.add(DashboardMenuModel(this.getString(R.string.mywallet)))
        menuList!!.add(DashboardMenuModel(this.getString(R.string.ratingreviews)))
        menuList!!.add(DashboardMenuModel(this.getString(R.string.notification)))
        menuList!!.add(DashboardMenuModel(this.getString(R.string.terms_and_condition)))
        menuList!!.add(DashboardMenuModel(this.getString(R.string.settings)))
        menuList!!.add(DashboardMenuModel(this.getString(R.string.refer)))
        menuList!!.add(DashboardMenuModel(this.getString(R.string.contactus)))
        menuList!!.add(DashboardMenuModel(this.getString(R.string.aboutus)))
        menuList!!.add(DashboardMenuModel(this.getString(R.string.cancellation_amp_refund_policy)))
        menuList!!.add(DashboardMenuModel(this.getString(R.string.rateapplication)))
        menuList!!.add(DashboardMenuModel(this.getString(R.string.privacypolicy)))
        menuList!!.add(DashboardMenuModel(this.getString(R.string.logout)))

        menuListAdapter = MenuListAdapter(this, menuList!!)
        binding.listVideMenu.adapter = menuListAdapter
    }

    private fun navMenuClickListener() {

        binding.listVideMenu.onItemClickListener =
            AdapterView.OnItemClickListener { parent, view, position, id ->
                when (position) {
                    0 -> {
                        val intent = Intent(this, ProfileActivity::class.java)
                        startActivity(intent)
                    }
                    1 -> {
                        val intent = Intent(this, TransactionReportActivity::class.java)
                        startActivity(intent)
                    }
                    2 -> {
                        val intent = Intent(this, WalletActivity::class.java).
                        putExtra("flag1","1")
                        startActivity(intent)
                    }
                    3 -> {
                        val intent = Intent(this, RatingReviewActivity::class.java)
                        startActivity(intent)
                    }
                    4 -> {
                        val intent = Intent(this, NotificationActivity::class.java)
                        startActivity(intent)
                    }
                    5 -> {
                        val intent = Intent(this, TermsConditionsActivity::class.java)
                        startActivity(intent)
                    }
                    6 -> {
                        val intent = Intent(this, SettingActivity::class.java)
                        startActivity(intent)
                    }
                    7 -> {
                        val intent = Intent(this, ReferAndEarnActivity::class.java)
                        startActivity(intent)
                    }
                    8 -> {
                        val intent = Intent(this, ContactusActivity::class.java)
                        startActivity(intent)
                    }
                    9 -> {
                        val intent = Intent(this, AboutusActivity::class.java)
                        startActivity(intent)
                    }10 -> {
                        val intent = Intent(this, CancellationPolicy::class.java)
                        startActivity(intent)
                    }
                    11 -> {
                        // val intent = Intent(this, Rate::class.java)
                        // startActivity(intent)
                    }
                    12 -> {
                        val intent = Intent(this, PrivacyPolicyActivity::class.java)
                        startActivity(intent)
                    }
                    13 -> {
                        logout()
                    }
                }
                closeDrawer()
            }
    }

    private fun closeDrawer() {
        val drawer: DrawerLayout = findViewById(R.id.drawer_layout)
        drawer.closeDrawer(GravityCompat.START)
    }

    private fun setupDrawerToggle(): ActionBarDrawerToggle? {
        return ActionBarDrawerToggle(
            this, binding.drawerLayout,
            R.string.drawer_open, R.string.drawer_close
        )
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        TODO("Not yet implemented")
    }

    private fun logout() {
        val cDialog = Dialog(this)
        val bindingDialog: DialogLogoutBinding = DataBindingUtil.inflate(
            LayoutInflater.from(this),
            R.layout.dialog_logout,
            null,
            false
        )
        cDialog.setContentView(bindingDialog.root)
        cDialog.setCancelable(false)
        cDialog.window!!.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        cDialog.show()
        bindingDialog.btnCancel.setOnClickListener {
            cDialog.dismiss()
        }
        bindingDialog.btnLogout.setOnClickListener {
            userPref.clearPref()
            startActivity(Intent(this, LoginAsActivity::class.java))
            finishAffinity()
            cDialog.dismiss()
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.GetProfileAPI(
            "Bearer " + userPref.getToken().toString(),
        )
        viewModel.get_user_check_statusApi(
            "Bearer " + userPref.getToken().toString(),
        )
    }
}