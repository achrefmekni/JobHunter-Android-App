package com.esprit.jobhunter.OffersFragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.esprit.jobhunter.Entity.Job
import com.esprit.jobhunter.MyUtils.GlobalParams
import com.esprit.jobhunter.R
import com.esprit.jobhunter.RecyclerViewsAdapters.OffersJobsAdapter
import org.json.JSONArray
import org.json.JSONException
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
class OffersJobsFragment : Fragment() {
    //---
    private var recyclerView: RecyclerView? = null
    var layoutManager: RecyclerView.LayoutManager? = null
    private var jobList: ArrayList<Job>? = null
    private var adapter: RecyclerView.Adapter<*>? = null
    var progressBar1: ProgressBar? = null
    var url = GlobalParams.url + "/jobs"
    var job: Job? = null
    var queue: RequestQueue? = null
    var url2 = GlobalParams.url + "/getuser"
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_offers_jobs, container, false)
        //------------------------
        queue = Volley.newRequestQueue(activity!!.applicationContext)

        progressBar1 = view.findViewById<View>(R.id.progressBar1) as ProgressBar
        recyclerView = view.findViewById<View>(R.id.offers_job_list) as RecyclerView
        recyclerView!!.setHasFixedSize(true)
        layoutManager = LinearLayoutManager(activity!!.applicationContext)
        recyclerView!!.layoutManager = layoutManager
        jsonArray
        println("JOBLIST$jobList")

        //------------------------
        // Inflate the layout for this fragment
        setHasOptionsMenu(true)
        return view
    }

    //-----------------------------------------
    //-----------------------------------------


    private val jsonArray: Unit
        private get() {
            progressBar1!!.visibility = View.VISIBLE
            jobList = ArrayList()
            val jsonArrayRequest = JsonObjectRequest(
                    Request.Method.GET,
                    url,
                    null,
                    Response.Listener { response ->
                        var jsonArray: JSONArray? = null
                        try {
                            jsonArray = response.getJSONArray("result")

                            for (i in 0 until jsonArray.length()) {
                                try {
                                    val jsonObject = jsonArray.getJSONObject(i)
                                    val job = Job()
                                    job.id = jsonObject.getInt("id")
                                    job.label = jsonObject.getString("label")
                                    job.description = jsonObject.getString("description")
                                    job.start_date = jsonObject.getString("start_date")
                                    job.contract_type = jsonObject.getString("contract_type")
                                    job.career_req = jsonObject.getString("career_req")
                                    job.skills = jsonObject.getString("skills")
                                    job.user_id = jsonObject.getString("user_id")
                                    job.company_name = jsonObject.getString("name")
                                    job.company_pic = jsonObject.getString("picture")

                                    //-----------------------------------------
                                    //-----------------------------------------
                                    jobList!!.add(job)
                                } catch (e: JSONException) {
                                    e.printStackTrace()
                                }
                            }
                            adapter = OffersJobsAdapter(activity!!, jobList!!)
                            adapter!!.notifyDataSetChanged()
                            recyclerView!!.adapter = adapter
                            adapter!!.notifyDataSetChanged()
                            progressBar1!!.visibility = View.GONE
                        } catch (e: JSONException) {
                            e.printStackTrace()
                        }
                    },
                    Response.ErrorListener { error -> Log.e("ERROR", error.message) }
            )
            val queue = Volley.newRequestQueue(activity!!.applicationContext)
            queue.add(jsonArrayRequest)
        }
}