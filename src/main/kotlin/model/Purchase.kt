package model

import PurchaseException

class Purchase {

    var internetConnection: Boolean = false
        private set

    var phones: MutableList<Phone> = mutableListOf()
        private set

    var phoneLines = 0
        private set

    var price = 0.0
        private set

    fun increasePhoneLines() {
        phoneLines++
        updatePrice(150.0)
    }

    fun decreasePhoneLines() {
        phoneLines--
        updatePrice(-150.0)
    }

    fun toggleInternetConnection() {
        when (internetConnection) {
            true -> updatePrice(200.0)
            false -> updatePrice(-200.0)
        }
        internetConnection = !internetConnection
    }

    fun removePhone(phoneService: PhoneService) {
        if (phones.contains(phoneService.phone)) {
            phones.remove(phoneService.phone)
            updatePrice(-phoneService.price)
        }
    }

    fun addPhone(phoneService: PhoneService) {
        phones.add(phoneService.phone)
        updatePrice(phoneService.price)
    }

    fun completePurchase(): String {
        if (phones.isEmpty()) {
            throw PurchaseException("No phone(s) selected.")
        } else {
            return "Purchase completed!"
        }
    }

    private fun updatePrice(amount: Double) {
        price += amount
    }
}