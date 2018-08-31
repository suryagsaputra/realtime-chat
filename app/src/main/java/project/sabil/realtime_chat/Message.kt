package project.sabil.realtime_chat

data class Message(var token: String? = "",
                   var title: String? = "",
                   var group: String? = "",
                   var message: String? = "")