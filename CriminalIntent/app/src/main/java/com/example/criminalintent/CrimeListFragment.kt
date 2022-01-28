package com.example.criminalintent

import android.content.Context
import android.os.Bundle
import android.text.format.DateFormat
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.*


private const val TAG = "CrimeListFragment"

class CrimeListFragment:Fragment() {

    interface Callbacks {
        fun onCrimeSelected(crimeId: UUID)
    }

    private var callbacks: Callbacks? = null

    private lateinit var crimeRecyclerView:RecyclerView
    private var adapter: CrimeAdapter? = CrimeAdapter(emptyList())

    private val crimeListViewModel: CrimeListViewModel by lazy {
        ViewModelProvider(this).get(CrimeListViewModel::class.java)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callbacks = context as Callbacks?
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_crime_list, container, false)

        crimeRecyclerView = view.findViewById(R.id.crime_recycler_view) as RecyclerView
        crimeRecyclerView.layoutManager = LinearLayoutManager(context)
        crimeRecyclerView.adapter = adapter

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        crimeListViewModel.crimeListLiveData.observe(
            viewLifecycleOwner,
            Observer { crimes ->
                crimes?.let{
                    Log.i(TAG, "Got crimes ${crimes.size}")
                    updateUI(crimes)
                }
            }
        )
    }

    override fun onDetach() {
        super.onDetach()
        callbacks = null
    }

    private fun updateUI(crimes: List<Crime>) {
        adapter = CrimeAdapter(crimes)
        crimeRecyclerView.adapter = adapter
    }

    private inner class CrimeHolder(view: View):RecyclerView.ViewHolder(view), View.OnClickListener{
        private lateinit var crime: Crime
        private val titleTextView: TextView = itemView.findViewById(R.id.crime_title)
        private val dateTextView: TextView = itemView.findViewById(R.id.crime_date)
        private val solvedImageView: ImageView = itemView.findViewById(R.id.crime_solved)

        init{
            itemView.setOnClickListener(this)
        }

        fun bind(crime: Crime){
            this.crime = crime
            titleTextView.text = this.crime.title
//            dateTextView.text = this.crime.date.toString()
            dateTextView.text = DateFormat.format("EEEE, M/d. yyyy", this.crime.date)
            solvedImageView.visibility = if (crime.isSolved){
                View.VISIBLE
            }
            else{
                View.GONE
            }
        }

        override fun onClick(p0: View?) {
            callbacks?.onCrimeSelected(crime.id)
        }
    }

    private inner class CrimeAdapter(var crimes:List<Crime>):RecyclerView.Adapter<CrimeHolder>(){

//        val VIEW_TYPE_1 = 0
//        val VIEW_TYPE_2 = 1

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CrimeHolder {
//            val view = if(viewType == VIEW_TYPE_1){
//                layoutInflater.inflate(R.layout.list_item_crime_police, parent , false)
//            }
//            else{
//                layoutInflater.inflate(R.layout.list_item_crime, parent , false)
//            }
            val view = layoutInflater.inflate(R.layout.list_item_crime, parent , false)
            return CrimeHolder(view)
        }

//        override fun getItemViewType(position: Int): Int {
//            if(crimes[position].requiresPolice){
//                return VIEW_TYPE_1
//            }
//            else{
//                return VIEW_TYPE_2
//            }
//        }

        override fun getItemCount(): Int = crimes.size

        override fun onBindViewHolder(holder: CrimeHolder, position: Int) {
            val crime = crimes[position]
            holder.bind(crime)
        }
    }

    companion object{
        fun newInstance():CrimeListFragment{
            return CrimeListFragment()
        }
    }
}