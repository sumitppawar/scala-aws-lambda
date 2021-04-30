package com.example.lambda

case class Request(
                    body: String,
                    eventType: String,
                    token: Option[String],
                    clientIp: String
                  )
