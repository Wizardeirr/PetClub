    package com.volkankelleci.petsocialclub.domain.helpers.lastprivatemessagelist

    import android.annotation.SuppressLint
    import android.os.Bundle
    import android.util.Log
    import android.view.LayoutInflater
    import android.view.View
    import android.view.ViewGroup
    import androidx.core.os.bundleOf
    import androidx.fragment.app.Fragment
    import androidx.fragment.app.FragmentManager
    import androidx.navigation.Navigation
    import androidx.navigation.fragment.findNavController
    import androidx.recyclerview.widget.LinearLayoutManager
    import com.google.firebase.firestore.CollectionReference
    import com.google.firebase.firestore.FirebaseFirestore
    import com.google.firebase.firestore.Query
    import com.volkankelleci.petsocialclub.R
    import com.volkankelleci.petsocialclub.data.PrivateMessage
    import com.volkankelleci.petsocialclub.databinding.FragmentPrivateMessageListBinding
    import com.volkankelleci.petsocialclub.util.Constants.APP_NAME
    import com.volkankelleci.petsocialclub.util.Util
    import com.volkankelleci.petsocialclub.util.Util.auth
    import kotlinx.android.synthetic.main.fragment_private_message_list.userChatPartRV
    import javax.inject.Inject


    class LastPrivateMessageListFragment @Inject constructor(
        private var adapter: LastPrivateMessageListAdapter

    ): Fragment(R.layout.fragment_private_message_list),
        LastPrivateMessageListAdapter.Listener {
        private  var _binding:FragmentPrivateMessageListBinding?=null
        private val binding get() =_binding!!
        var user=ArrayList<PrivateMessage>()
        private lateinit var toUUID: String
        val bundle=arguments?.getString("toUUID")

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

            takesUUIDData()
            //Adapter Determinedd
            val layoutManager=LinearLayoutManager(activity)
            userChatPartRV.layoutManager=layoutManager
            userChatPartRV.adapter=adapter
            //Title
            activity?.title = APP_NAME

            binding.fabForPM.setOnClickListener {
                val action = LastPrivateMessageListFragmentDirections.actionLastPrivateMessageListFragmentToUserListFragment()
                Navigation.findNavController(requireView()).navigate(action)
            }
        }

        override fun onItemClickListener(privateMessage: PrivateMessage) {
            //when i click to user toUUID is sending to PmRoomFragment who you talk with it

            val action=LastPrivateMessageListFragmentDirections.actionLastPrivateMessageListFragmentToPmRoomFragment("",toUUID)
            findNavController().navigate(action)
        }
        override fun onResume() {
            super.onResume()
            fragmentManager?.popBackStack("UserListFragment", FragmentManager.POP_BACK_STACK_INCLUSIVE)

        }
        private fun takesUUIDData(){
            Util.database.collection("privateChatInfo")
                .document(bundle.toString())
                .collection(auth.currentUser!!.uid)
                .orderBy("userDate", Query.Direction.DESCENDING)
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
                            val privateChatToUUID = document.getString(toUUID)?:""
                            val downloadInfos = PrivateMessage(
                                privateMessageUserText,
                                privateChatUserUUID,
                                privateChatToUUID,
                                privateChatUserDate,
                                privateChatUserEmail
                            )
                            user.add(downloadInfos)
                            adapter.notifyDataSetChanged()

                        }
                    }
                }
                .addOnFailureListener { exception ->
                    Log.d("Error Tag", "onViewCreated: ERROR UUID")
                }

        }
}
