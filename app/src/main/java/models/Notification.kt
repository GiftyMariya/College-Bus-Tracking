package models

class Notification(
    var title: String,
    var message: String,
    var action: String?,
    var actionDestination: String?
) {
    constructor() : this("", "", null, null)
}