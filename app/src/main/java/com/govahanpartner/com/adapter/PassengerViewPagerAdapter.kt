package com.govahanpartner.com.adapter

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

import com.govahanpartner.com.ui.fragments.PassengerCompleted
import com.govahanpartner.com.ui.fragments.PassengerOngoing
import com.govahanpartner.com.ui.fragments.PendingBookingHistory
import com.govahanpartner.com.ui.vendor.passenger.PassengerCancelledTrip

class PassengerViewPagerAdapter (
    fragmentManager: FragmentManager
) : FragmentPagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private val titles =
        arrayOf("Pending","Ongoing", "Completed","Cancelled")

    override fun getItem(position: Int): Fragment {

        val bundle = Bundle()
        val fragment : Fragment
        return when (position) {
            0 -> {
                fragment = PendingBookingHistory()
                bundle.putInt("type",0)
                bundle.putString("VehicleType","Passenger")
                fragment.arguments = bundle
                fragment

            }
            1-> {

                fragment = PassengerOngoing()
                bundle.putInt("type",1)
                fragment.arguments = bundle
                fragment
            }
            1-> {

                fragment = PassengerCompleted()
                bundle.putInt("type",2)
                fragment.arguments = bundle
                fragment
            }

            else -> {
                fragment = PassengerCancelledTrip()
                bundle.putInt("type",3)
                fragment.arguments = bundle
                fragment
            }
        }
    }

    override fun getCount(): Int {
        return titles.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return titles[position]
    }
}