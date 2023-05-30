    package com.volkankelleci.petsocialclub.lastprivatemessagelist

    import android.content.Context
    import android.os.Bundle
    import android.view.LayoutInflater
    import android.view.View
    import android.view.ViewGroup
    import androidx.activity.addCallback
    import androidx.fragment.app.Fragment
    import androidx.navigation.Navigation
    import androidx.recyclerview.widget.LinearLayoutManager
    import com.google.firebase.firestore.Query
    import com.volkankelleci.petsocialclub.R
    import com.volkankelleci.petsocialclub.data.PrivateMessage
    import com.volkankelleci.petsocialclub.databinding.FragmentPrivateMessageListBinding
    import com.volkankelleci.petsocialclub.util.Util
    import com.volkankelleci.petsocialclub.util.Util.database
    import kotlinx.android.synthetic.main.fragment_private_message_list.userChatPartRV

    class LastPrivateMessageListFragment: Fragment(R.layout.fragment_private_message_list),
        LastPrivateMessageListAdapter.Listener {
        private  var _binding:FragmentPrivateMessageListBinding?=null
        private val binding get() =_binding!!
        var userMessage=ArrayList<PrivateMessage>()
        private lateinit var adapter: LastPrivateMessageListAdapter

        override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View {
            _binding=FragmentPrivateMessageListBinding.inflate(inflater,container,false)
            val view=binding.root

            return view
        }
        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)

            //fun
            val toUUID = getToUUIDFromSharedPreferences()

            val layoutManager=LinearLayoutManager(activity)
            userChatPartRV.layoutManager=layoutManager
            adapter= LastPrivateMessageListAdapter(userMessage,this@LastPrivateMessageListFragment)
            userChatPartRV.adapter=adapter

            binding.fabForPM.setOnClickListener {
                val action = LastPrivateMessageListFragmentDirections.actionLastPrivateMessageListFragmentToUserListFragment()
                Navigation.findNavController(requireView()).navigate(action)
            }
            database.collection("privateChatInfo/$toUUID/${Util.auth.currentUser!!.uid}").orderBy("userDate",
                Query.Direction.DESCENDING).limit(1)
                .addSnapshotListener { value, error ->
                    if (error != null) {
                    } else if (value != null && !value.isEmpty){

                        val documents = value.documents
                        userMessage.clear() // Önceki mesajları temizle
                        for (document in documents) {

                            document.get("privateChatInfo")
                            val privateMessageUserText = document.get("userText").toString()
                            val privateChatUserUUID = document.get("PrivateChatUserUUID").toString()
                            val privateChatUserEmail = document.get("PrivateChatUserEmail").toString()
                            val privateChatUserDate = document.get("userDate").toString()
                            val privateChatToUUID = document.get("${toUUID}").toString()
                            val downloadInfos = PrivateMessage(privateMessageUserText,privateChatUserUUID,privateChatToUUID,privateChatUserDate,privateChatUserEmail,)
                            userMessage.add(downloadInfos)

                        }
                        adapter.notifyDataSetChanged()
                    }
                }

        }
        override fun onItemClickListener(privateMessage: PrivateMessage) {
            println("")
        }
        // SharedPreferences'ten toUUID değerini okuyoruz
        private fun getToUUIDFromSharedPreferences(): String {
            val sharedPreferences = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
            return sharedPreferences.getString("toUUID", "") ?: ""
        }

        override fun onResume() {
            super.onResume()
            requireActivity().onBackPressedDispatcher.addCallback(this) {
                val action= LastPrivateMessageListFragmentDirections.actionLastPrivateMessageListFragmentToUsersHomeFragment()
                Navigation.findNavController(requireView()).navigate(action)
            }
        }
    }

