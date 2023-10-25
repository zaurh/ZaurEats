package com.example.zaureats.data.restaurant

import android.os.Parcel
import android.os.Parcelable

data class RestaurantData(
    val restaurantId: String? = null,
    val restaurantName: String? = null,
    val restaurantTags: String? = null,
    val restaurantDescription: String? = null,
    val restaurantNumber: String? = null,
    val restaurantAddress: String? = null,
    val restaurantImage: String? = null,
    val deliveryTime: String? = null,
    val deliveryPrice: String? = null,
    val rating: String? = null,
    val deliveryOptions: DeliveryOptions? = null,
    val openForDelivery: OpenForDelivery? = null,
    val openHours: OpenHours? = null,

    ): Parcelable {


    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(restaurantId)
        parcel.writeString(restaurantName)
        parcel.writeString(restaurantTags)
        parcel.writeString(restaurantDescription)
        parcel.writeString(restaurantNumber)
        parcel.writeString(restaurantAddress)
        parcel.writeString(restaurantImage)
        parcel.writeString(deliveryTime)
        parcel.writeString(deliveryPrice)
        parcel.writeString(rating)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<RestaurantData> {
        override fun createFromParcel(parcel: Parcel): RestaurantData {
            return RestaurantData(parcel)
        }

        override fun newArray(size: Int): Array<RestaurantData?> {
            return arrayOfNulls(size)
        }
    }


}
