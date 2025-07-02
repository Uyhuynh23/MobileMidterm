package com.example.mobile_midterm

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.example.mobile_midterm.Domain.ItemsModel
import com.example.mobile_midterm.Helper.ManagmentCart
import com.example.mobile_midterm.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {
    lateinit var binding: ActivityDetailBinding
    private lateinit var  item:ItemsModel
    private lateinit var managementCart:ManagmentCart


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        managementCart = ManagmentCart(this)
        item = intent.getSerializableExtra("object") as ItemsModel
        bundle()
        initSizelist()
    }

    private fun setSelectedTextOption(selected: View, others: List<View>, selectedText: TextView, otherTexts: List<TextView>) {
        selected.isSelected = true
        selectedText.setTextColor(ContextCompat.getColor(this, R.color.white))
        selected.setBackgroundResource(R.drawable.shot_selected_background)

        for (i in others.indices) {
            others[i].isSelected = false
            otherTexts[i].setTextColor(ContextCompat.getColor(this, R.color.dark_blue))
            others[i].setBackgroundResource(R.drawable.shot_selected_background)
        }
    }

    private fun setSelectedImageOption(selected: ImageView, others: List<ImageView>) {
        selected.alpha = 1.0f
        for (image in others) {
            image.alpha = 0.3f
        }
    }

    private fun initSizelist() {
        binding.apply {

            setSelectedTextOption(Single, listOf(Double), singleShot, listOf(doubleShot))
            item.shot = "single"

            // Select: default = iced
            setSelectedImageOption(SelectIced, listOf(SelectHot))
            item.select = "iced"

            // Enable ice options
            iceOption1.isEnabled = true
            iceOption2.isEnabled = true
            iceOption3.isEnabled = true
            setSelectedImageOption(iceOption1, listOf(iceOption2, iceOption3))
            item.ice = "less ice"

            // Size: default = small
            setSelectedImageOption(smallSize, listOf(mediumSize, largeSize))
            item.size = "small"


            // ----- SHOT (Single / Double) -----
            Single.setOnClickListener {
                setSelectedTextOption(Single, listOf(Double), singleShot, listOf(doubleShot))
                item.shot="single"
            }

            Double.setOnClickListener {
                setSelectedTextOption(Double, listOf(Single), doubleShot, listOf(singleShot))
                item.shot="double"
            }

            // ----- SELECT (hot/iced)
            SelectHot.setOnClickListener {
                setSelectedImageOption(SelectHot, listOf(SelectIced))
                item.select = "hot"

                // Disable ice
                listOf(iceOption1, iceOption2, iceOption3).forEach {
                    it.alpha = 0.3f
                    it.isEnabled = false
                }
                item.ice = ""
            }

            SelectIced.setOnClickListener {
                setSelectedImageOption(SelectIced, listOf(SelectHot))
                item.select = "iced"

                // Enable ice
                listOf(iceOption1, iceOption2, iceOption3).forEach {
                    it.isEnabled = true
                }
                setSelectedImageOption(iceOption1, listOf(iceOption2, iceOption3))
                item.ice = "less ice"
            }

            // ----- SIZE (Small / Medium / Large) -----
            smallSize.setOnClickListener {
                setSelectedImageOption(smallSize, listOf(mediumSize, largeSize))
                item.size="small"
            }

            mediumSize.setOnClickListener {
                setSelectedImageOption(mediumSize, listOf(smallSize, largeSize))
                item.size="medium"
            }

            largeSize.setOnClickListener {
                setSelectedImageOption(largeSize, listOf(smallSize, mediumSize))
                item.size="large"
            }

            // ----- ICE (less / Medium / Full) -----
            iceOption1.setOnClickListener {
                setSelectedImageOption(iceOption1, listOf(iceOption2, iceOption3))
                item.ice="less ice"

            }

            iceOption2.setOnClickListener {
                setSelectedImageOption(iceOption2, listOf(iceOption1, iceOption3))
                item.ice="medium ice"
            }

            iceOption3.setOnClickListener {
                setSelectedImageOption(iceOption3, listOf(iceOption1, iceOption2))
                item.ice="full ice"
            }

        }

    }

    private fun bundle(){
        binding.apply{

            Glide.with(this@DetailActivity)
                .load(item.picUrl[0])
                .into(binding.picMain)

            coffeeName.text = item.title
            priceTxt.text = "$" + item.price

            AddButton.setOnClickListener{
                item.numberInCart=Integer.valueOf(
                    numberItemTxt.text.toString()
                )
                managementCart.insertItems(item)
            }
        cartIcon.setOnClickListener{
            startActivity(Intent(this@DetailActivity,CartActivity::class.java))
        }
        backButton.setOnClickListener{
            startActivity(Intent(this@DetailActivity,MainActivity::class.java))
        }
        plusCart.setOnClickListener{
            numberItemTxt.text = (item.numberInCart+1).toString()
            item.numberInCart++
        }
        minusCart.setOnClickListener{
            if(item.numberInCart>0){
                numberItemTxt.text = (item.numberInCart-1).toString()
                item.numberInCart--
            }
        }


        }
    }
}