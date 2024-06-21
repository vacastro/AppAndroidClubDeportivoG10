package com.example.clubdeportivo_grupo10

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerAdapter  (fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity){



    override fun createFragment(position: Int): Fragment {
        // Dependiendo de la posición, devuelve el fragmento correspondiente
        return when (position) {
            0 -> PublicidadFragment1()
            1 -> PublicidadFragment2()
            2 -> PublicidadFragment3()
            else -> throw IllegalStateException("Unexpected position: $position")
        }
    }

    override fun getItemCount(): Int {
        return 3
    }
}