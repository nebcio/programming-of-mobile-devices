package com.pum.studenthardlife

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.pum.studenthardlife.databinding.AllListBinding

class AllListFragment : Fragment() {
    private lateinit var binding: AllListBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = AllListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val listList: MutableList<ListElement> = getLists(requireContext()).toMutableList()
        binding.allListRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = AllListAdapter(listList)
        }

        binding.addButton.setOnClickListener {
            if (binding.listEditText.text?.isNotEmpty() == true) {
                listList.add(
                    ListElement(
                        binding.listEditText.text.toString(),
                        binding.descriptionEditText.text.toString(), ""
                    )
                )
                saveListList(requireContext(), listList)
                binding.allListRecyclerView.adapter?.notifyDataSetChanged()
                binding.listEditText.text?.clear()
                binding.descriptionEditText.text?.clear()
            }
        }
    }
}