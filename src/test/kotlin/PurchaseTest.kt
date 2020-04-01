import io.mockk.mockk
import model.Phone
import model.PhoneService
import model.Purchase
import model.ServiceRegularity
import org.amshove.kluent.`should be`
import org.amshove.kluent.`should not be equal to`
import org.amshove.kluent.shouldBeEmpty
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.provider.Arguments

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



    fun phoneServices() = listOf(
        Arguments.of(PhoneService(phone = Phone("Motorola G99"), price = 800.0, regularity = ServiceRegularity.ONCE)),
        Arguments.of(PhoneService(phone = Phone("iPhone 99"), price = 6000.0, regularity = ServiceRegularity.ONCE)),
        Arguments.of(PhoneService(phone = Phone("Samsung Galaxy 99"), price = 1000.0, regularity = ServiceRegularity.ONCE)),
        Arguments.of(PhoneService(phone = Phone("Sony Xperia 99"), price = 6000.0, regularity = ServiceRegularity.ONCE)),
        Arguments.of(PhoneService(phone = Phone("Huawei 99"), price = 6000.0, regularity = ServiceRegularity.ONCE))
    )

}