package com.volkankelleci.petsocialclub.viewmodel

import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.volkankelleci.petsocialclub.data.Post
import com.volkankelleci.petsocialclub.data.UserInfo
import com.volkankelleci.petsocialclub.repo.UUIDRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class UsersHomeFragmentVM @Inject constructor(
    private val repository: UUIDRepository
):ViewModel() {
    private var database: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val _postListFlow = MutableStateFlow<List<Post>>(emptyList())
    val postListFlow: StateFlow<List<Post>> = _postListFlow
    private var pp = ArrayList<UserInfo>()

    fun takesData() {
        database.collection("Post").orderBy("date", Query.Direction.DESCENDING)
            .addSnapshotListener { value, error ->
                if (error != null) {
                    // Handle error
                    return@addSnapshotListener
                }

                value?.let { querySnapshot ->
                    val newPostList = mutableListOf<Post>()
                    newPostList.clear()
                    for (document in querySnapshot.documents) {
                        val userTitle = document.getString("usertitle") ?: ""
                        val userComment = document.getString("usercomment") ?: ""
                        val userImage = document.getString("imageurl") ?: ""
                        val userEmail = document.getString("useremail") ?: ""

                        val downloadInfos = Post(userTitle, userComment, userImage, userEmail)
                        newPostList.add(downloadInfos)
                    }

                    _postListFlow.value = newPostList
                }
            }
    }

}