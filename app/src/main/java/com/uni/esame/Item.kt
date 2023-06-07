package com.uni.esame

class Item(private var _name: String, private var _quantity: Int) {
    // Getter
    val name: String
        get() = _name

    // Setter
    var nameSetter: String
        get() = _name
        set(value) {
            _name = value
        }

    // Getter
    val quantity: Int
        get() = _quantity

    // Setter
    var quantitySetter: Int
        get() = _quantity
        set(value) {
            _quantity = value
        }
}