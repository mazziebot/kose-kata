/*
 * Copyright (c) 2023 mazziechai
 */

package cafe.ferret.kosekata

import cafe.ferret.kosekata.database.database
import cafe.ferret.kosekata.extensions.*
import dev.kord.common.entity.Snowflake
import dev.kord.gateway.Intents
import dev.kord.gateway.NON_PRIVILEGED
import dev.kord.rest.builder.message.allowedMentions
import dev.kordex.core.ExtensibleBot
import dev.kordex.core.i18n.SupportedLocales
import dev.kordex.core.utils.env
import dev.kordex.i18n.Bundle
import dev.kordex.i18n.I18n.defaultBundle
import io.ktor.client.request.forms.*
import io.ktor.utils.io.*

val TEST_SERVER_ID = Snowflake(
    env("TEST_SERVER").toLong()
)

val ENVIRONMENT = env("ENVIRONMENT")

private val token = env("TOKEN")

suspend fun main() {
    val bot = ExtensibleBot(token) {
        database(true)

        intents(addDefaultIntents = false) {
            +Intents.NON_PRIVILEGED  // kordex automatically adds message contnet intent because chat commands on
        }

        applicationCommands {
            if (ENVIRONMENT == "dev") {
                defaultScope.limitToGuild(TEST_SERVER_ID)
            }
        }

        chatCommands {
            enabled = true
            invokeOnMention = true
        }

        i18n {
            defaultLocale = SupportedLocales.ENGLISH
            defaultBundle = Bundle("kose.strings")
        }

        errorResponse { message, error ->
            allowedMentions { }
            content = buildString {
                append("There was an **unexpected error** while handling this command. ")
                appendLine("Please let us know on Discord or GitHub about it!")
                appendLine("`$error`")
            }
            addFile(
                "trace.txt",
                ChannelProvider { ByteReadChannel(error.error.stackTraceToString()) }
            )
        }

        extensions {
//            sentry {
//                enable = true
//
//                if (ENVIRONMENT == "dev") {
//                    debug = true
//                }
//
//                dsn = sentry_dsn
//                environment = ENVIRONMENT
//            }

            add(::CreationExtension)
            add(::ManagementExtension)
            add(::ViewingExtension)
            add(::UtilityExtension)
            add(::DebugExtension)
            add(::AliasExtension)
            add(::ChatCommandsExtension)
            add(::InfoExtension)
        }
    }

    bot.start()
}
