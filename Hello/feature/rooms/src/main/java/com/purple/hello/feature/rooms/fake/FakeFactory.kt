package com.purple.hello.feature.rooms.fake

import com.purple.hello.domain.feature.model.Room
import com.purple.hello.domain.feature.model.User

object FakeFactory {
    fun makeUser(id: Int, name: String, profileUrl: String): User {
        return User(id, name, profileUrl)
    }

    fun makeRoom(name: String): Room {
        return Room(1, name, makeMembers())
    }

    fun makeMembers(): List<User> {
        return listOf(
            makeUser(1, "홍길동(자식)", "https://imgv3.fotor.com/images/share/Unblur-image-online-automatically-with-Fotor-AI-image-deburring-tool.jpg"),
            makeUser(2, "홍동길(자식)", "https://images.unsplash.com/photo-1503023345310-bd7c1de61c7d?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxzZWFyY2h8Mnx8aHVtYW58ZW58MHx8MHx8&w=1000&q=80"),
            makeUser(3, "홍파파(아빠)", "https://www.adobe.com/content/dam/cc/us/en/creativecloud/photography/discover/cut-out-an-image/thumbnail.jpeg"),
            makeUser(4, "김마마(엄마)", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTP6-MfoJ0MLH3ZH7oIyNvP_PfLRoYI-ZgPeQ&usqp=CAU"),
            makeUser(5, "김마마(엄마)", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTP6-MfoJ0MLH3ZH7oIyNvP_PfLRoYI-ZgPeQ&usqp=CAU"),
            makeUser(6, "김마마(엄마)", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTP6-MfoJ0MLH3ZH7oIyNvP_PfLRoYI-ZgPeQ&usqp=CAU"),
        )
    }
}
