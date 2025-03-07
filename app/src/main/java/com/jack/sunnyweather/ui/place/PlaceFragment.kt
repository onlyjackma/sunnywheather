package com.jack.sunnyweather.ui.place

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jack.sunnyweather.R
import com.jack.sunnyweather.logic.model.Place

class PlaceFragment : Fragment() {
    private val TAG = "PlaceFragment"
    val viewModel by lazy { ViewModelProvider(this).get(PlaceViewModel::class.java) }
    private lateinit var adapter: PlaceAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var searchPlaceEdit: EditText
    private lateinit var bgImageView: ImageView
    private lateinit var view: View

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        view = inflater.inflate(R.layout.fragment_place, container, false)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val layoutManager = LinearLayoutManager(activity)
        recyclerView = view.findViewById(R.id.recyclerView)
        searchPlaceEdit = view.findViewById(R.id.searchPlaceEdit)
        bgImageView = view.findViewById(R.id.bgImageView)
        recyclerView.layoutManager = layoutManager

        adapter = PlaceAdapter(this, viewModel.placeList)
        recyclerView.adapter = adapter
        searchPlaceEdit.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // You can leave this method empty if you don't need to do anything before the text changes
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val content = s.toString()
                if (content.isNotEmpty()) {
                    viewModel.searchPlaces(content)
                } else {
                    recyclerView.visibility = View.GONE
                    bgImageView.visibility = View.VISIBLE
                    viewModel.placeList.clear()
                    adapter.notifyDataSetChanged()
                }
            }

            override fun afterTextChanged(s: Editable?) {
                Log.d(TAG, "Text change over")
            }
        })

        viewModel.placeLiveData.observe(viewLifecycleOwner, Observer { result ->
            val places = result.getOrNull()
            Log.d(TAG,"places = $places")
            if (places != null) {
                recyclerView.visibility = View.VISIBLE
                bgImageView.visibility = View.GONE
                viewModel.placeList.clear()
                viewModel.placeList.addAll(places as Collection<out Place>)
                adapter.notifyDataSetChanged()
            } else {
                Toast.makeText(activity, "Can't Find any places.", Toast.LENGTH_SHORT).show()
                result.exceptionOrNull()?.printStackTrace()
            }
        })
    }
}