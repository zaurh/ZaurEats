package com.example.zaureats.data.order

import android.os.Parcel
import android.os.Parcelable
import com.example.zaureats.data.BasketData
import com.example.zaureats.data.restaurant.RestaurantData
import com.example.zaureats.data.user.UserData
import com.google.firebase.Timestamp

data class OrderData(
    val orderId: String? = null,
    val restaurantData: RestaurantData? = null,
    val basketData: BasketData? = null,
    val userData: UserData? = null,
    val price: String? = null,
    val time: Timestamp? = Timestamp.now(),
    val orderStatus: OrderStatus? = null,

    ): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readParcelable(RestaurantData::class.java.classLoader),
        parcel.readParcelable(BasketData::class.java.classLoader),
        parcel.readParcelable(UserData::class.java.classLoader),
        parcel.readString(),
        parcel.readParcelable(Timestamp::class.java.classLoader),
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(orderId)
        parcel.writeParcelable(restaurantData, flags)
        parcel.writeParcelable(basketData, flags)
        parcel.writeParcelable(userData, flags)
        parcel.writeString(price)
        parcel.writeParcelable(time, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<OrderData> {
        override fun createFromParcel(parcel: Parcel): OrderData {
            return OrderData(parcel)
        }

        override fun newArray(size: Int): Array<OrderData?> {
            return arrayOfNulls(size)
        }
    }

}
