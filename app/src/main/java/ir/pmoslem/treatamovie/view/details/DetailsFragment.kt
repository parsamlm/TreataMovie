package ir.pmoslem.treatamovie.view.details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import ir.pmoslem.treatamovie.R

class DetailsFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.details_fragment, container, false)

        val movie = arguments?.let { DetailsFragmentArgs.fromBundle(it).movie }



        return root
    }



}