package com.example.zaureats.data.user

import android.os.Parcel
import android.os.Parcelable

data class UserData(
    val userId: String? = null,
    val name: String? = null,
    val surname: String? = null,
    val address: String? = null,
    val phoneNumber: String? = null,
    val addressDetails: AddressDetails? = null,
    val balance: Double? = 100.0,
    val promoList: List<String>? = listOf()
): Parcelable {

    fun toMap() = mapOf(
        "userId" to userId,
        "name" to name,
        "surname" to surname,
        "address" to address,
        "phoneNumber" to phoneNumber,
        "addressDetails" to addressDetails,
        "balance" to balance,
        "promoList" to promoList
    )
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(userId)
        parcel.writeString(name)
        parcel.writeString(surname)
        parcel.writeString(address)
        parcel.writeString(phoneNumber)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<UserData> {
        override fun createFromParcel(parcel: Parcel): UserData {
            return UserData(parcel)
        }

        override fun newArray(size: Int): Array<UserData?> {
            return arrayOfNulls(size)
        }
    }
}
