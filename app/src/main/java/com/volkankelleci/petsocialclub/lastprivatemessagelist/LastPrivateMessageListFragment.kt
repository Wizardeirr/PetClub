    package com.volkankelleci.petsocialclub.lastprivatemessagelist

    import android.annotation.SuppressLint
    import android.os.Bundle
    import android.view.LayoutInflater
    import android.view.View
    import android.view.ViewGroup
    import androidx.activity.addCallback
    import androidx.fragment.app.Fragment
    import androidx.navigation.Navigation
    import androidx.navigation.fragment.findNavController
    import androidx.recyclerview.widget.LinearLayoutManager
    import com.google.firebase.firestore.Query
    import com.volkankelleci.petsocialclub.R
    import com.volkankelleci.petsocialclub.data.PrivateMessage
    import com.volkankelleci.petsocialclub.data.UserInfo
    import com.volkankelleci.petsocialclub.databinding.FragmentPrivateMessageListBinding
    import com.volkankelleci.petsocialclub.util.Util.auth
    import com.volkankelleci.petsocialclub.util.Util.database
    import com.volkankelleci.petsocialclub.util.Util.getToUUIDFromSharedPreferences

    class LastPrivateMessageListFragment: Fragment(R.layout.fragment_private_message_list),
        LastPrivateMessageListAdapter.Listener {
        private  var _binding:FragmentPrivateMessageListBinding?=null
        private val binding get() =_binding!!
        var user=ArrayList<PrivateMessage>()
        private lateinit var adapter: LastPrivateMessageListAdapter
        val userInfoForAdapter=ArrayList<UserInfo>()


        override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View {
            _binding=FragmentPrivateMessageListBinding.inflate(inflater,container,false)
            val view=binding.root

            return view
        }
        @SuppressLint("NotifyDataSetChanged")
        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)

            //fun

            val toUUID = getToUUIDFromSharedPreferences(requireContext())
            takeAllUsers()
            val layoutManager=LinearLayoutManager(activity)
            binding.userChatPartRV.layoutManager=layoutManager
            adapter= LastPrivateMessageListAdapter(user,this@LastPrivateMessageListFragment,userInfoForAdapter)
            binding.userChatPartRV.adapter=adapter

            binding.fabForPM.setOnClickListener {
                val action = LastPrivateMessageListFragmentDirections.actionLastPrivateMessageListFragmentToUserListFragment()
                Navigation.findNavController(requireView()).navigate(action)
            }
            database.collection("privateChatInfo/$toUUID/${auth.currentUser!!.uid}").orderBy("userDate",Query.Direction.DESCENDING).limit(1)
                .addSnapshotListener { value, error ->
                    if (error != null) {
                    } else
                        if (value != null) {
                            if (value.isEmpty == false) {
                                val documents = value.documents
                                user.clear()
                                for (document in documents) {
                                    document.get("privateChatInfo")
                                    val privateMessageUserText = document.get("userText").toString()
                                    val privateChatUserUUID = document.get("PrivateChatUserUUID").toString()
                                    val privateChatUserEmail = document.get("PrivateChatUserEmail").toString()
                                    val privateChatUserDate = document.get("userDate").toString()
                                    val privateChatToUUID = document.get("toUUID").toString()
                                    val downloadInfos = PrivateMessage(privateMessageUserText,privateChatUserUUID,privateChatToUUID,privateChatUserDate,privateChatUserEmail)
                                    user.add(downloadInfos)

                                }
                                adapter.notifyDataSetChanged()
                            }
                        }
                }
        }
        fun takeAllUsers(){
                database.collection("userProfileInfo")
                    .addSnapshotListener { value, error ->
                        if(error!=null){
                        }else
                            if (value!=null){
                                if (value.isEmpty==false){
                                    val documents=value.documents
                                    userInfoForAdapter.clear()
                                    for (document in documents){
                                        document.get("userProfileInfo")
                                        val userEmail=document.get("userEmail").toString()
                                        val userUUID=document.get("userUUID").toString()
                                        val userName=document.get("userName").toString()
                                        val userPetName=document.get("petName").toString()
                                        val userImage=document.get("userImage").toString()
                                        val userPassword=document.get("password").toString()
                                        val downloadInfos= UserInfo(userUUID,userEmail,userName,userPetName,userImage,userPassword)
                                        userInfoForAdapter.add(downloadInfos)
                                    }
                                    adapter.notifyDataSetChanged()
                                }
                            }
                    }

        }
        override fun onItemClickListener(privateMessage: PrivateMessage) {
            val toUUID = getToUUIDFromSharedPreferences(requireContext())
            val action=LastPrivateMessageListFragmentDirections.actionLastPrivateMessageListFragmentToPmRoomFragment("",toUUID)
            findNavController().navigate(action)
            println(privateMessage.timestamp)

        }


        override fun onResume() {
            super.onResume()
            requireActivity().onBackPressedDispatcher.addCallback(this) {
                val action= LastPrivateMessageListFragmentDirections.actionLastPrivateMessageListFragmentToUsersHomeFragment()
                Navigation.findNavController(requireView()).navigate(action)
            }
        }
    }

