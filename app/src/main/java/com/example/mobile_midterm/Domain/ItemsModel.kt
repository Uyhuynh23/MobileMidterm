package com.example.mobile_midterm.Domain

import java.io.Serializable

data class ItemsModel(
    var title:String="",
    var description:String="",
    var picUrl:ArrayList<String> = ArrayList(),
    var price:Double=0.0,
    var rating:Double=0.0,
    var shot:String="",//single,double
    var size:String="",//small,medium,large
    var select:String="",//hot, iced
    var ice:String="",//less,medium,full
    var numberInCart:Int = 0,
    var extra:String="",
    var orderTime:String="",
    var address:String=""
) : Serializable
