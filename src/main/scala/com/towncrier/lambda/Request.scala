package com.towncrier.lambda

case class Request(
                    body: String,
                    eventType: String,
                    token: Option[String],
                    clientIp: String
                  )
