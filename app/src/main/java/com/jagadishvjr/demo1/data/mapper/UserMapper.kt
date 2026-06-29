package com.jagadishvjr.demo1.data.mapper

import com.jagadishvjr.demo1.data.remote.dto.UserDto
import com.jagadishvjr.demo1.domin.model.Address
import com.jagadishvjr.demo1.domin.model.User

fun UserDto.toDomain() : User {
    return User(
        id = id,
        name = name,
        username = username,
        email = email,
        website = website,
        phone = phone,
        address = Address(
            city = address.city,
            zipcode = address.zipcode
        )
    )
}