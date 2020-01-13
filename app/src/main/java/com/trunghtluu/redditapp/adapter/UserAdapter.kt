package com.trunghtluu.redditapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.trunghtluu.redditapp.R
import com.trunghtluu.redditapp.model.Child
import com.trunghtluu.redditapp.model.Data_
import kotlinx.android.synthetic.main.data_card.view.*



class UserAdapter ( var childList: List<Child>,
        var context : Context, var delegate: UserAdapterDelegate) : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    interface UserAdapterDelegate {
        fun userSelected(selected: Child)
    }

    init {
        this.childList = childList
        this.delegate = delegate
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        context = parent.context.applicationContext

        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.data_card, parent, false)

        return UserViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return childList.size
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val data: Data_ = this.childList.get(position).data;
        holder.userTextView.setText(data.author)
        holder.contentTextView.setText(data.title)
        holder.commentTextView.setText(data.numComments.toString())
        holder.upsTextView.setText(data.ups.toString())
        holder.downsTextView.setText(data.downs.toString())

        Glide.with(context)
            .load(data.getThumbnail())
            .apply(RequestOptions.circleCropTransform())
            .into(holder.iconImageView)

        holder.itemView.setOnClickListener {
            delegate.userSelected(childList[position])
        }
    }

    class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val iconImageView = itemView.card_icon_iv
        val userTextView = itemView.card_id_tv
        val contentTextView = itemView.card_content_tv
        val commentTextView = itemView.card_comments_tv
        val upsTextView = itemView.card_ups_tv
        val downsTextView = itemView.card_downs_tv
    }

}