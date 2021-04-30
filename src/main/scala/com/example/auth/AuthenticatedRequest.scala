package com.example.auth

case class AuthenticatedRequest(
                                 user: User,
                                 body: String,
                                 eventType: String,
                                 clientIp: String
                               )

