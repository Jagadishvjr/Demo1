package com.jagadishvjr.demo1.data.mapper

import com.jagadishvjr.demo1.data.remote.dto.AddressDto
import com.jagadishvjr.demo1.data.remote.dto.CompanyDto
import com.jagadishvjr.demo1.data.remote.dto.GeoDto
import com.jagadishvjr.demo1.data.remote.dto.UserDto
import com.jagadishvjr.demo1.data.remote.dto.UserDtoItem
import com.jagadishvjr.demo1.domain.model.Address
import com.jagadishvjr.demo1.domain.model.Company
import com.jagadishvjr.demo1.domain.model.Geo
import com.jagadishvjr.demo1.domain.model.User
import com.jagadishvjr.demo1.domain.model.UserItem

fun UserDtoItem.toDomain() = UserItem(
    address = address.toDomain() ,
    company = company.toDomain(),
    email = email,
    id = id,
    name = name,
    phone = phone,
    username = username,
    website = website
)

fun CompanyDto.toDomain() = Company(
    bs = bs,
    catchPhrase = catchPhrase,
    name = name
)

fun AddressDto.toDomain() = Address(
    city = city,
    geo = geo.toDomain(),
    street = street,
    suite = suite,
    zipcode = zipcode
)

fun GeoDto.toDomain() = Geo(
    lat = lat,
    lng = lng
)