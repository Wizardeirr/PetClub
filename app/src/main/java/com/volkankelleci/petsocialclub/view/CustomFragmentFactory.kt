package com.volkankelleci.petsocialclub.view

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.volkankelleci.petsocialclub.domain.helpers.doneviews.UsersHomeFragment

class CustomFragmentFactory(

):FragmentFactory() {
    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when(className){
            UsersHomeFragment::class.java.name->UsersHomeFragment()
            else->super.instantiate(classLoader, className)
        }
    }
}