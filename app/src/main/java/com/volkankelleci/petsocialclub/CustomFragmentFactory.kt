package com.volkankelleci.petsocialclub

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.volkankelleci.petsocialclub.generalchatroom.GeneralChatRoomFragment
import com.volkankelleci.petsocialclub.lastprivatemessagelist.LastPrivateMessageListAdapter
import com.volkankelleci.petsocialclub.lastprivatemessagelist.LastPrivateMessageListFragment
import com.volkankelleci.petsocialclub.pm.PmRoomAdapter
import com.volkankelleci.petsocialclub.pm.PmRoomFragment
import com.volkankelleci.petsocialclub.postandhome.UserPostAdapter
import com.volkankelleci.petsocialclub.userslist.UserListAdapter
import com.volkankelleci.petsocialclub.userslist.UserListFragment
import javax.inject.Inject

class CustomFragmentFactory @Inject constructor(
    private val lastPrivateMessageListAdapter: LastPrivateMessageListAdapter,
    private val generalChatRoomAdapter: GeneralChatRoomAdapter,
    private val pmRoomAdapter: PmRoomAdapter,
    private val userPostAdapter: UserPostAdapter,
    private val userListAdapter: UserListAdapter

):FragmentFactory() {
    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when (className) {
            UsersHomeFragment::class.java.name -> UsersHomeFragment(userPostAdapter)
            PmRoomFragment::class.java.name -> PmRoomFragment(pmRoomAdapter)
            UserListFragment::class.java.name -> UserListFragment(userListAdapter)
            GeneralChatRoomFragment::class.java.name -> GeneralChatRoomFragment(generalChatRoomAdapter)
            LastPrivateMessageListFragment::class.java.name -> LastPrivateMessageListFragment(lastPrivateMessageListAdapter)
            else -> super.instantiate(classLoader, className)
        }
    }
}