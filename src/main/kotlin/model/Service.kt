package model

data class Service(val name: String, val price: Double, val regularity: ServiceRegularity)
data class PhoneService(val phone: Phone, val price: Double, val regularity: ServiceRegularity)