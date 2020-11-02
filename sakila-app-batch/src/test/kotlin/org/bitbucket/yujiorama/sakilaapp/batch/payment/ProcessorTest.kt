package org.bitbucket.yujiorama.sakilaapp.batch.payment

import org.bitbucket.yujiorama.sakilaapp.BatchConfig
import org.bitbucket.yujiorama.sakilaapp.model.*
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.TestPropertySource
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig
import java.math.BigDecimal

@SpringJUnitConfig(
    classes = [
        BatchConfig::class,
        Config::class,
    ]
)
@TestPropertySource(
    locations = [
        "classpath:application.properties",
        "classpath:application-test.properties",
    ],
)
class ProcessorTest(
    @Autowired val processor: PaymentProcessor,
) {

    @Test
    fun `process test`() {
        val target = Payment()
            .withId(1374)
            .withCustomer(Customer()
                .withId(1374)
                .withFirstName("first")
                .withLastName("last")
                .withActive(1))
            .withStaff(Staff()
                .withId(1374)
                .withFirstName("first")
                .withLastName("last")
                .withActive(true))
            .withRental(Rental()
                .withId(1374)
                .withInventory(Inventory()
                    .withId(1374)
                    .withFilm(Film()
                        .withId(1374)
                        .withTitle("title")
                        .withRentalRate(BigDecimal("3.2"))
                        .withRating(Rating.PG_13)
                        .withRentalDuration(7))
                    .withStore(Store()
                        .withId(1374)
                        .withAddress(Address()
                            .withId(1374)
                            .withAddress("address")
                            .withPostalCode("111")
                            .withPhone("222")
                            .withCity(City()
                                .withId(1374)
                                .withCity("city")
                                .withCountry(Country()
                                    .withId(1374)
                                    .withCountry("country")))))))
            .withAmount(BigDecimal("123"))
        val actual = processor.process(target)!!

        Assertions.assertEquals(target.id, actual.paymentId)
        Assertions.assertEquals(target.customer.id, actual.customerId)
        Assertions.assertEquals(target.staff.id, actual.staffId)
        Assertions.assertEquals(target.rental.id, actual.rentalId)
        Assertions.assertEquals(target.rental.inventory.id, actual.inventoryId)
        Assertions.assertEquals(target.rental.inventory.film.id, actual.filmId)
        Assertions.assertEquals(target.rental.inventory.store.id, actual.storeId)
        Assertions.assertEquals(target.amount, actual.amount)
    }
}