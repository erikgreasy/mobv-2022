package com.example.semestralka.ui.adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.semestralka.R
import com.example.semestralka.api.User
import com.example.semestralka.data.Bar
import com.example.semestralka.databinding.BarListItemBinding
import com.example.semestralka.databinding.FriendAddedByMeItemBinding
import com.example.semestralka.databinding.FriendItemBinding
import com.example.semestralka.ui.fragments.bars.BarDetailFragmentDirections
import com.example.semestralka.ui.viewmodel.BarViewModel
import com.example.semestralka.ui.viewmodel.FriendViewModel

class FriendsAddedByMeAdapter constructor(val friendViewModel: FriendViewModel) : RecyclerView.Adapter<FriendsAddedByMeAdapter.FriendsAddedByMeViewHolder>() {

    inner class FriendsAddedByMeViewHolder(val binding: FriendAddedByMeItemBinding) : ViewHolder(binding.root)

    private val diffCallback = object : DiffUtil.ItemCallback<User>() {
        override fun areItemsTheSame(
            oldItem: User,
            newItem: User
        ): Boolean {
            return oldItem.user_id == newItem.user_id
        }
        override fun areContentsTheSame(
            oldItem: User,
            newItem: User
        ): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)

    var friends: List<User>
        get() = differ.currentList
        set(value) { differ.submitList(value) }

    override fun getItemCount() = friends.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendsAddedByMeViewHolder {
        return FriendsAddedByMeViewHolder(FriendAddedByMeItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ))
    }

    override fun onBindViewHolder(holder: FriendsAddedByMeViewHolder, position: Int) {
        holder.binding.apply {
            val friend = friends[position]
            friendItemName.text = friend.user_name

            deleteFriendBtn.setOnClickListener {
                friendViewModel.deleteFriend(friend.user_name)
            }
        }

//        holder.binding.friendItemName.setOnClickListener {
//            val action = BarDetailFragmentDirections.actionBarDetail(friends[position].bar_id)
//            holder.itemView.findNavController().navigate(action)
//        }
    }
}