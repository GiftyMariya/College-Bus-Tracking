package com.pixieium.austtravels.auth.volunteers

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.pixieium.austtravels.auth.R
import com.pixieium.austtravels.auth.databinding.ActivityVolunteersBinding


import android.content.Intent
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView

import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.ImageLoader
import coil.decode.SvgDecoder
import coil.request.ImageRequest

import models.Volunteer
import com.pixieium.austtravels.auth.settings.SettingsActivity
import kotlinx.coroutines.launch
import com.facebook.shimmer.ShimmerFrameLayout


class VolunteersActivity : AppCompatActivity() {
    private lateinit var mAdapter: VolunteerAdapter
    private lateinit var mLayoutManager
            : RecyclerView.LayoutManager
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mBinding: ActivityVolunteersBinding
    private val mDatabase: VolunteerRepository = VolunteerRepository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityVolunteersBinding.inflate(layoutInflater)
        val view: View = mBinding.root
        setContentView(view)
        setSupportActionBar(mBinding.toolbar)

        lifecycleScope.launch {
            val volunteers: ArrayList<Volunteer> = mDatabase.fetchVolunteers()
            if (volunteers.size > 0) {
                mBinding.shimmer.startShimmer()
                mBinding.shimmer.visibility = View.GONE
                mBinding.vrecyclerView.visibility = View.VISIBLE
                mBinding.notFoundPlaceholder.visibility = View.GONE
                mBinding.topPositionCard.visibility = View.VISIBLE
                mBinding.firstPosition.loadSvg(volunteers[0].userImage)
                mBinding.firstTime.text = volunteers[0].totalContributionFormatted
                mBinding.topNameTv.text = volunteers[0].name
                mBinding.topRollTv.text = volunteers[0].universityId

                volunteers.removeAt(0)
                initRecyclerView(volunteers)
            } else {
                mBinding.notFoundPlaceholder.visibility = View.VISIBLE
                mBinding.topPositionCard.visibility = View.GONE
                mBinding.vrecyclerView.visibility = View.GONE
                mBinding.shimmer.visibility = View.VISIBLE
                mBinding.shimmer.stopShimmer()
            }

        }

    }

    /**
     * By default, ImageViews don't support SVG formats.
     * So, instead we are using the coil library to render svg files
     */
    private fun ImageView.loadSvg(url: String) {
        val imageLoader = ImageLoader.Builder(this.context)
            .componentRegistry { add(SvgDecoder(this@loadSvg.context)) }
            .build()

        val request = ImageRequest.Builder(this.context)
            .crossfade(true)
            .crossfade(2)
            .data(url)
            .target(this)
            .build()

        imageLoader.enqueue(request)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.top_app_bar, menu)
        return true
    }

    private fun initRecyclerView(volunteers: ArrayList<Volunteer>) {
        mRecyclerView = findViewById(R.id.vrecyclerView)
        mLayoutManager = LinearLayoutManager(this)
        mAdapter = VolunteerAdapter(volunteers)
        mBinding.vrecyclerView.layoutManager = mLayoutManager
        mRecyclerView.adapter = mAdapter
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.settings) {
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
            return true
        } else if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return false
    }
}