package com.example.zaureats.data

import android.os.Parcel
import android.os.Parcelable

data class MealData(
    val restaurantId: String? = null,
    val mealId: String? = null,
    val name: String? = null,
    val description: String? = null,
    val category: String? = null,
    val image: String? = null,
    val price: Double? = null,
    val hasDiscount: Int? = 0,
    val vegan: Boolean? = false,
    val spicy: Boolean? = false,
    val lactoseFree: Boolean? = false,
    val choiceDataSingle: ChoiceData? = null,
    val choiceData: List<ChoiceData>? = null
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Double::class.java.classLoader) as? Double,
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Boolean::class.java.classLoader) as? Boolean,
        parcel.readValue(Boolean::class.java.classLoader) as? Boolean,
        parcel.readValue(Boolean::class.java.classLoader) as? Boolean,
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(restaurantId)
        parcel.writeString(mealId)
        parcel.writeString(name)
        parcel.writeString(description)
        parcel.writeString(category)
        parcel.writeString(image)
        parcel.writeValue(price)
        parcel.writeValue(hasDiscount)
        parcel.writeValue(vegan)
        parcel.writeValue(spicy)
        parcel.writeValue(lactoseFree)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MealData> {
        override fun createFromParcel(parcel: Parcel): MealData {
            return MealData(parcel)
        }

        override fun newArray(size: Int): Array<MealData?> {
            return arrayOfNulls(size)
        }
    }

}
