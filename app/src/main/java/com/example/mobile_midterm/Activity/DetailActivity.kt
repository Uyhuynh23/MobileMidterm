package com.example.mobile_midterm.Activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.example.mobile_midterm.Domain.ItemsModel
import com.example.mobile_midterm.Helper.ManagmentCart
import com.example.mobile_midterm.R
import com.example.mobile_midterm.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var item: ItemsModel
    private lateinit var managementCart: ManagmentCart

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        managementCart = ManagmentCart(this)
        item = intent.getSerializableExtra("object") as ItemsModel

        setupItemUI()
        setupSizeSelection()
        setupButtonClicks()
    }

    private fun setupItemUI() = with(binding) {
        // Image
        if (item.isDrawable) {
            val resId = resources.getIdentifier(item.picUrl[0], "drawable", packageName)
            Glide.with(this@DetailActivity).load(resId).into(picMain)
        } else {
            Glide.with(this@DetailActivity).load(item.picUrl[0]).into(picMain)
        }

        coffeeName.text = item.title
        TotalAmount.text = "$${item.price}"
        numberItemTxt.text = item.numberInCart.toString()
    }

    private fun setupButtonClicks() = with(binding) {
        AddButton.setOnClickListener {
            item.numberInCart = numberItemTxt.text.toString().toInt()
            managementCart.insertItems(item)
        }

        cartIcon.setOnClickListener {
            startActivity(Intent(this@DetailActivity, CartActivity::class.java))
        }

        backButton.setOnClickListener {
            startActivity(Intent(this@DetailActivity, MainActivity::class.java))
        }

        plusCart.setOnClickListener {
            item.numberInCart++
            numberItemTxt.text = item.numberInCart.toString()
            updateTotal()
        }

        minusCart.setOnClickListener {
            if (item.numberInCart > 0) {
                item.numberInCart--
                numberItemTxt.text = item.numberInCart.toString()
                updateTotal()
            }
        }
    }

    private fun updateTotal() = with(binding) {
        TotalAmount.text = "$${item.price * item.numberInCart}"
    }

    private fun setupSizeSelection() = with(binding) {
        // Default selections
        setTextSelection(Single, listOf(Double), singleShot, listOf(doubleShot))
        item.shot = "single"

        setImageSelection(SelectIced, listOf(SelectHot))
        item.select = "iced"
        enableIceOptions(true)
        setImageSelection(iceOption1, listOf(iceOption2, iceOption3))
        item.ice = "less ice"

        setImageSelection(smallSize, listOf(mediumSize, largeSize))
        item.size = "small"

        // Shot selection
        Single.setOnClickListener {
            setTextSelection(Single, listOf(Double), singleShot, listOf(doubleShot))
            item.shot = "single"
        }
        Double.setOnClickListener {
            setTextSelection(Double, listOf(Single), doubleShot, listOf(singleShot))
            item.shot = "double"
        }

        // Hot/Iced
        SelectHot.setOnClickListener {
            setImageSelection(SelectHot, listOf(SelectIced))
            item.select = "hot"
            enableIceOptions(false)
            item.ice = ""
        }

        SelectIced.setOnClickListener {
            setImageSelection(SelectIced, listOf(SelectHot))
            item.select = "iced"
            enableIceOptions(true)
            setImageSelection(iceOption1, listOf(iceOption2, iceOption3))
            item.ice = "less ice"
        }

        // Size
        smallSize.setOnClickListener {
            setImageSelection(smallSize, listOf(mediumSize, largeSize))
            item.size = "small"
        }
        mediumSize.setOnClickListener {
            setImageSelection(mediumSize, listOf(smallSize, largeSize))
            item.size = "medium"
        }
        largeSize.setOnClickListener {
            setImageSelection(largeSize, listOf(smallSize, mediumSize))
            item.size = "large"
        }

        // Ice options
        iceOption1.setOnClickListener {
            setImageSelection(iceOption1, listOf(iceOption2, iceOption3))
            item.ice = "less ice"
        }
        iceOption2.setOnClickListener {
            setImageSelection(iceOption2, listOf(iceOption1, iceOption3))
            item.ice = "medium ice"
        }
        iceOption3.setOnClickListener {
            setImageSelection(iceOption3, listOf(iceOption1, iceOption2))
            item.ice = "full ice"
        }
    }

    private fun enableIceOptions(enable: Boolean) = with(binding) {
        listOf(iceOption1, iceOption2, iceOption3).forEach {
            it.isEnabled = enable
            it.alpha = if (enable) 1.0f else 0.3f
        }
    }

    private fun setTextSelection(
        selected: View, others: List<View>,
        selectedText: TextView, otherTexts: List<TextView>
    ) {
        selected.isSelected = true
        selected.setBackgroundResource(R.drawable.shot_selected_background)
        selectedText.setTextColor(ContextCompat.getColor(this, R.color.white))

        for (i in others.indices) {
            others[i].isSelected = false
            others[i].setBackgroundResource(R.drawable.shot_selected_background)
            otherTexts[i].setTextColor(ContextCompat.getColor(this, R.color.dark_blue))
        }
    }

    private fun setImageSelection(selected: ImageView, others: List<ImageView>) {
        selected.alpha = 1.0f
        others.forEach { it.alpha = 0.3f }
    }
}
