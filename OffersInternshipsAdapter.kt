package com.esprit.jobhunter.RecyclerViewsAdapters

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.esprit.jobhunter.Entity.Internship
import com.esprit.jobhunter.MainActivity
import com.esprit.jobhunter.MyUtils.GlobalParams
import com.esprit.jobhunter.OffersFragments.InternshipDetailsFragment
import com.esprit.jobhunter.R
import com.squareup.picasso.Picasso
import java.util.*

class OffersInternshipsAdapter(private val context: Context, list: ArrayList<Internship>) : RecyclerView.Adapter<OffersInternshipsAdapter.ViewHolder>() {
    private val list: List<Internship>
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(context).inflate(R.layout.offer_job_list_item, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val job = list[position]
        holder.textLabel.text = job.label
        holder.type.text = job.duration
        holder.textCompanyName.text = job.company_name.toString()

        if (job.company_pic!!.length > 60) {
            Picasso.with(context as MainActivity).load(job.company_pic).into(holder.imgCompany)
        } else {
            Picasso.with(context as MainActivity).load(GlobalParams.ressourceUrl + "/" + job.company_pic).into(holder.imgCompany)
        }
        holder.layout.setOnClickListener {
            val fragment: Fragment = InternshipDetailsFragment()
            (fragment as InternshipDetailsFragment).setData(list[position])
            context.supportFragmentManager.beginTransaction().replace(R.id.flContent, fragment).addToBackStack(null).commit()
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var textLabel: TextView
        var textCompanyName: TextView
        var type: TextView
        var imgCompany: ImageView
        var layout: RelativeLayout

        init {
            textLabel = itemView.findViewById(R.id.job_label)
            textCompanyName = itemView.findViewById(R.id.company_name)
            imgCompany = itemView.findViewById(R.id.job_image)
            type = itemView.findViewById(R.id.type)
            layout = itemView.findViewById(R.id.ojli_layout)
        }
    }

    init {
        this.list = list
    }
}