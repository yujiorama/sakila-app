package org.bitbucket.yujiorama.sakilaapp.batch.payment

import org.bitbucket.yujiorama.sakilaapp.model.Payment
import org.bitbucket.yujiorama.sakilaapp.model.Rating
import java.math.BigDecimal
import java.time.LocalDateTime

data class PaymentDTO(
    val paymentId: Int,
    val paymentDate: LocalDateTime,
    val customerId: Int,
    val customerFirstName: String,
    val customerLastName: String,
    val customerActive: Int,
    val staffId: Int,
    val staffFirstName: String,
    val staffLastName: String,
    val staffActive: Boolean,
    val rentalId: Int,
    val inventoryId: Int,
    val filmId: Int,
    val filmTitle: String,
    val filmRentalRate: BigDecimal,
    val filmRating: Rating,
    val filmRentalDuration: Int,
    val storeId: Int,
    val storeCountry: String,
    val storeCity: String,
    val storeAddress: String,
    val storeAddressPostalCode: String,
    val storeAddressPhone: String,
    val amount: BigDecimal,
) {

    companion object {

        fun of(payment: Payment): PaymentDTO = PaymentDTO(
            paymentId = payment.id!!,
            paymentDate = payment.paymentDate,
            customerId = payment.customer.id!!,
            customerFirstName = payment.customer.firstName,
            customerLastName = payment.customer.lastName,
            customerActive = payment.customer.active!!,
            staffId = payment.staff.id!!,
            staffFirstName = payment.staff.firstName,
            staffLastName = payment.staff.lastName,
            staffActive = payment.staff.active,
            rentalId = payment.rental.id!!,
            inventoryId = payment.rental.inventory.id!!,
            filmId = payment.rental.inventory.film.id!!,
            filmTitle = payment.rental.inventory.film.title,
            filmRentalRate = payment.rental.inventory.film.rentalRate,
            filmRating = payment.rental.inventory.film.rating,
            filmRentalDuration = payment.rental.inventory.film.rentalDuration,
            storeId = payment.rental.inventory.store.id!!,
            storeCountry = payment.rental.inventory.store.address.city.country.country,
            storeCity = payment.rental.inventory.store.address.city.city,
            storeAddress = payment.rental.inventory.store.address.address,
            storeAddressPostalCode = payment.rental.inventory.store.address.postalCode ?: "",
            storeAddressPhone = payment.rental.inventory.store.address.phone,
            amount = payment.amount,
        )

        fun fields(): Array<String> = arrayOf(
            "paymentId",
            "paymentDate",
            "customerId",
            "customerFirstName",
            "customerLastName",
            "customerActive",
            "staffId",
            "staffFirstName",
            "staffLastName",
            "staffActive",
            "rentalId",
            "inventoryId",
            "filmId",
            "filmTitle",
            "filmRentalRate",
            "filmRating",
            "filmRentalDuration",
            "storeId",
            "storeCountry",
            "storeCity",
            "storeAddress",
            "storeAddressPostalCode",
            "storeAddressPhone",
            "amount",
        )
    }
}
