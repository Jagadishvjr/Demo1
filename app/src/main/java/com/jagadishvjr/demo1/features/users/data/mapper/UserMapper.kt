package com.jagadishvjr.demo1.features.users.data.mapper

import com.jagadishvjr.demo1.features.users.data.remote.dto.UserDto
import com.jagadishvjr.demo1.features.users.domain.model.Address
import com.jagadishvjr.demo1.features.users.domain.model.Geo
import com.jagadishvjr.demo1.features.users.domain.model.User

fun UserDto.toDomain(): User {
    return User(
        id = id,
        name = name,
        address = Address(
            street = address.street,
            suite = address.suite,
            city = address.city,
            zipcode = address.zipcode
        ),
        geo = Geo(
            latitude = address.geo.latitude.toDoubleOrNull() ?: 0.0,
            longitude = address.geo.longitude.toDoubleOrNull() ?: 0.0
        )
    )
}
