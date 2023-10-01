package com.volkankelleci.petsocialclub.view

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.volkankelleci.petsocialclub.domain.helpers.generalchatroom.GeneralChatRoomFragment
import com.volkankelleci.petsocialclub.domain.helpers.postandhome.UsersHomeFragment
import com.volkankelleci.petsocialclub.domain.helpers.generalchatroom.GeneralChatRoomAdapter
import com.volkankelleci.petsocialclub.domain.helpers.lastprivatemessagelist.LastPrivateMessageListAdapter
import com.volkankelleci.petsocialclub.domain.helpers.lastprivatemessagelist.LastPrivateMessageListFragment
import com.volkankelleci.petsocialclub.domain.helpers.pm.PmRoomAdapter
import com.volkankelleci.petsocialclub.domain.helpers.pm.PmRoomFragment
import com.volkankelleci.petsocialclub.domain.helpers.postandhome.UserPostAdapter
import com.volkankelleci.petsocialclub.domain.helpers.userslist.UserListAdapter
import com.volkankelleci.petsocialclub.domain.helpers.userslist.UserListFragment
import javax.inject.Inject

class CustomFragmentFactory @Inject constructor(
    private val lastPrivateMessageListAdapter: LastPrivateMessageListAdapter,
    private val generalChatRoomAdapter: GeneralChatRoomAdapter,
    private val pmRoomAdapter: PmRoomAdapter,
    private val userPostAdapter: UserPostAdapter,
    private val userListAdapter: UserListAdapter,




):FragmentFactory() {
    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when(className){
            UsersHomeFragment::class.java.name->UsersHomeFragment(userPostAdapter)
            PmRoomFragment::class.java.name->PmRoomFragment(pmRoomAdapter)
            UserListFragment::class.java.name->UserListFragment(userListAdapter)
            GeneralChatRoomFragment::class.java.name->GeneralChatRoomFragment(generalChatRoomAdapter)
            LastPrivateMessageListFragment::class.java.name->LastPrivateMessageListFragment(lastPrivateMessageListAdapter)
            else->super.instantiate(classLoader, className)
        }
    }
}