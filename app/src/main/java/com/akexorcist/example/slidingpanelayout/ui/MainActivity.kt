package com.akexorcist.example.slidingpanelayout.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.akexorcist.example.slidingpanelayout.databinding.ActivityMainBinding
import com.akexorcist.example.slidingpanelayout.vo.Mock

class MainActivity : AppCompatActivity() {
    private val viewModel: BookViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel.openBooksPaneLiveData.observe(this, openBooksPaneObserver)
        viewModel.closeBooksPaneLiveData.observe(this, closeBooksPaneObserver)
        viewModel.setBooks(Mock.getBooks(this))
        openBooksPane()
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.openBooksPaneLiveData.removeObserver(openBooksPaneObserver)
        viewModel.closeBooksPaneLiveData.removeObserver(closeBooksPaneObserver)
    }

    override fun onBackPressed() {
        if (binding.slidingPaneLayout.isOpen) {
            super.onBackPressed()
        } else {
            openBooksPane()
        }
    }

    private val openBooksPaneObserver = Observer<Unit> {
        openBooksPane()
    }

    private val closeBooksPaneObserver = Observer<Unit> {
        closeBooksPane()
    }

    private fun openBooksPane() {
        binding.slidingPaneLayout.openPane()
    }

    private fun closeBooksPane() {
        binding.slidingPaneLayout.closePane()
    }
}
