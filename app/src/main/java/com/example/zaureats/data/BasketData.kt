package com.example.zaureats.data

import android.os.Parcel
import android.os.Parcelable

data class BasketData(
    val basketId: String? = null,
    val mealData: List<MealData>? = null
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.createTypedArrayList(MealData)
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(basketId)
        parcel.writeTypedList(mealData)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<BasketData> {
        override fun createFromParcel(parcel: Parcel): BasketData {
            return BasketData(parcel)
        }

        override fun newArray(size: Int): Array<BasketData?> {
            return arrayOfNulls(size)
        }
    }

}
