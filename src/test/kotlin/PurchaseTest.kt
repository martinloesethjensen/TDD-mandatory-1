import io.mockk.mockk
import model.Phone
import model.PhoneService
import model.Purchase
import model.ServiceRegularity
import org.amshove.kluent.*
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

class PurchaseTest {

    private val purchase = Purchase()

    @Nested
    inner class `phone list` {
        @Test
        fun `should add a phone`() {
            // given
            val phoneService = PhoneService(phone = Phone("iPhone 99"), price = 6000.0, regularity = ServiceRegularity.ONCE)

            val oldSize = purchase.phones.size

            // when
            purchase.addPhone(phoneService)

            // then
            purchase.phones.size `should be` oldSize + 1
        }

        @Test
        fun `should remove a phone if in the list`() {
            // given
            val phoneService = PhoneService(phone = Phone("iPhone 99"), price = 6000.0, regularity = ServiceRegularity.ONCE)

            purchase.addPhone(phoneService)

            // when
            purchase.removePhone(phoneService)

            purchase.phones.shouldBeEmpty()
        }
    }

    @Nested
    inner class `phone lines`{

        @Test
        fun `number should not exceed 8`(){
            // when
            for (e in 0..98){
                purchase.increasePhoneLines()
            }

            // then
            purchase.phoneLines `should not be greater than` 8
        }

        @Test
        fun `should decrease` (){

            // when
            for (e in 1..5){
                purchase.increasePhoneLines()
            }
            purchase.decreasePhoneLines()

            // then
            purchase.phoneLines `should be` 4
        }
    }

    @Nested
    inner class `service` {

        @Test
        fun `should toggle internet connection`() {
            val oldInternetConnection = purchase.internetConnection

            // when
            purchase.toggleInternetConnection()

            // then
            purchase.internetConnection `should not be equal to` oldInternetConnection
        }
    }

    @Nested
    inner class `complete purchase` {

        @Test
        fun `should throw purchase exeption if phone list is empty`() {
            invoking { purchase.completePurchase() } `should throw` PurchaseException::class
        }

        @Test
        fun `should not throw purchase exeption if phone list is not empty`() {

            // given
            val phoneService =
                PhoneService(phone = Phone("iPhone 99"), price = 6000.0, regularity = ServiceRegularity.ONCE)

            // when
            purchase.addPhone(phoneService)

            // then
            invoking { purchase.completePurchase() } `should not throw` PurchaseException::class
        }
    }

    @Nested
    inner class `price`{

        fun phoneServices() = listOf(
            Arguments.of(PhoneService(phone = Phone("Motorola G99"), price = 800.0, regularity = ServiceRegularity.ONCE), 800.0),
            Arguments.of(PhoneService(phone = Phone("iPhone 99"), price = 6000.0, regularity = ServiceRegularity.ONCE), 6000.0),
            Arguments.of(PhoneService(phone = Phone("Samsung Galaxy 99"), price = 1000.0, regularity = ServiceRegularity.ONCE), 1000.0),
            Arguments.of(PhoneService(phone = Phone("Sony Xperia 99"), price = 6000.0, regularity = ServiceRegularity.ONCE), 6000.0),
            Arguments.of(PhoneService(phone = Phone("Huawei 99"), price = 6000.0, regularity = ServiceRegularity.ONCE), 6000.0)
        )

        @ParameterizedTest
        @MethodSource("phoneServices")
        fun `should be updated to the price of the phone`(phoneService: PhoneService, expected:Double){
            purchase.addPhone(phoneService)
            phoneService.price `should be` expected
        }

    }
}