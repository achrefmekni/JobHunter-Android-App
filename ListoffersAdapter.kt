package com.esprit.jobhunter.Adapters

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.esprit.jobhunter.CompanyFragments.Listoffre_applicants
import com.esprit.jobhunter.CompanyFragments.UpdateactionFragment
import com.esprit.jobhunter.Entity.Job
import com.esprit.jobhunter.Main2Activity
import com.esprit.jobhunter.R
import java.util.*

class ListoffersAdapter(private val context: Context, list: ArrayList<Job>) : RecyclerView.Adapter<ListoffersAdapter.ViewHolder>() {
    private val list: List<Job>
    var fragment: Fragment? = null
    var lastPosition = -1
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(context).inflate(R.layout.offer_jobprofile_list_item, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val job = list[position]
        fragment = Listoffre_applicants()
        holder.textLabel.text = job.label
        holder.type.text = job.contract_type
        holder.show.setOnClickListener {
            val myActivity = context as Main2Activity
            val bundle = Bundle()
            bundle.putSerializable("id", job.id)
            val myFragment: Fragment = Listoffre_applicants()
            myFragment.arguments = bundle
            myActivity.supportFragmentManager.beginTransaction().replace(R.id.flContent, myFragment).addToBackStack(null).commit()
        }
        holder.update.setOnClickListener {
            val myActivity = context as Main2Activity
            val bundle = Bundle()
            bundle.putSerializable("job", job)
            val myFragment: Fragment = UpdateactionFragment()
            myFragment.arguments = bundle
            myActivity.supportFragmentManager.beginTransaction().replace(R.id.flContent, myFragment).addToBackStack(null).commit()
        }
        setAnimation(holder.itemView, position)

    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var textLabel: TextView
        var textCompanyName: TextView
        var type: TextView
        var imgCompany: ImageView? = null
        var update: ImageView
        var show: Button

        init {
            show = itemView.findViewById(R.id.show)
            textLabel = itemView.findViewById(R.id.job_label)
            type = itemView.findViewById(R.id.type)
            textCompanyName = itemView.findViewById(R.id.company_name)
            update = itemView.findViewById(R.id.update)


        }
    }

    private fun setAnimation(viewToAnimate: View, position: Int) {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition) {
            val animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left)
            viewToAnimate.startAnimation(animation)
            lastPosition = position
        }
    }

    init {
        this.list = list
    }
}