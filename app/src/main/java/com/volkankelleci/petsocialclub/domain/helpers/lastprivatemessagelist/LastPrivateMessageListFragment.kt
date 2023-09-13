    package com.volkankelleci.petsocialclub.domain.helpers.lastprivatemessagelist

    import android.annotation.SuppressLint
    import android.content.Context
    import android.os.Bundle
    import android.util.Log
    import android.view.LayoutInflater
    import android.view.View
    import android.view.ViewGroup
    import androidx.activity.addCallback
    import androidx.fragment.app.Fragment
    import androidx.fragment.app.FragmentManager
    import androidx.navigation.Navigation
    import androidx.navigation.fragment.findNavController
    import androidx.recyclerview.widget.LinearLayoutManager
    import com.google.firebase.database.FirebaseDatabase
    import com.google.firebase.firestore.Query
    import com.volkankelleci.petsocialclub.R
    import com.volkankelleci.petsocialclub.data.PrivateMessage
    import com.volkankelleci.petsocialclub.data.UserInfo
    import com.volkankelleci.petsocialclub.databinding.FragmentPrivateMessageListBinding
    import com.volkankelleci.petsocialclub.domain.helpers.SharedPreferencesHelpers.getToUUIDFromSharedPreferences
    import com.volkankelleci.petsocialclub.util.Util.auth
    import com.volkankelleci.petsocialclub.util.Util.database
    import kotlinx.android.synthetic.main.fragment_private_message_list.userChatPartRV
    import com.google.firebase.firestore.FirebaseFirestore
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
            //Adapter Determinedd
            val layoutManager=LinearLayoutManager(activity)
            userChatPartRV.layoutManager=layoutManager
            adapter= LastPrivateMessageListAdapter(user,this@LastPrivateMessageListFragment,requireContext())
            userChatPartRV.adapter=adapter
            //Title
            getActivity()?.setTitle("PetSocialClub")

            binding.fabForPM.setOnClickListener {
                val action = LastPrivateMessageListFragmentDirections.actionLastPrivateMessageListFragmentToUserListFragment()
                Navigation.findNavController(requireView()).navigate(action)
            }

            //Data consume from Firebase
            database.collection("privateChatInfo")
                .document(toUUID)
                .collection(auth.currentUser!!.uid)
                .orderBy("userDate",Query.Direction.DESCENDING)
                .limit(1)
                .get()
                .addOnSuccessListener { result ->
                    if (result != null) {
                        val documents = result.documents
                        for (document in documents) {
                            val privateMessageUserText = document.get("userText").toString()
                            val privateChatUserUUID = document.get("PrivateChatUserUUID").toString()
                            val privateChatUserEmail = document.get("PrivateChatUserEmail").toString()
                            val privateChatUserDate = document.get("userDate").toString()
                            val privateChatToUUID = document.getString("toUUID")?:""
                            val downloadInfos = PrivateMessage(
                                privateMessageUserText,
                                privateChatUserUUID,
                                privateChatToUUID,
                                privateChatUserDate,
                                privateChatUserEmail
                            )
                            user.clear()
                            user.add(downloadInfos)
                            adapter.notifyDataSetChanged()

                        }
                    }
                }
                .addOnFailureListener { exception ->
                    Log.d("Error Tag", "onViewCreated: ERROR UUID")
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
            //when i click to user toUUID is sending to PmRoomFragment who you talk with it
            val action=LastPrivateMessageListFragmentDirections.actionLastPrivateMessageListFragmentToPmRoomFragment("","")
            findNavController().navigate(action)
        }
        override fun onResume() {
            super.onResume()
            fragmentManager?.popBackStack("UserListFragment", FragmentManager.POP_BACK_STACK_INCLUSIVE)

        }
}
