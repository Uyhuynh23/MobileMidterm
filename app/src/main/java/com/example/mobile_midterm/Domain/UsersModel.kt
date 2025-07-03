package com.example.mobile_midterm.Domain

import java.io.Serializable

data class UsersModel(
    var fullName: String = "Anderson",
    var phoneNumber: String = "+60134589525",
    var email: String = "Anderson@email.com",
    var address: String = "3 Addersion Court\nChino Hills, HO56824, United State",
    var loyaltyCups: Int = 8,
    var points: Int = 1350
) : Serializable