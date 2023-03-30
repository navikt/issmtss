package no.nav.syfo.tss.service

import no.nav.syfo.log
import no.nav.syfo.mq.producerForQueue
import javax.jms.Connection
import javax.jms.Session
import no.nav.syfo.objectMapper
import no.nav.syfo.securelog

suspend fun findBestTssIdEmottak(
    samhandlerfnr: String,
    connection: Connection,
    tssQueue: String,
): String? {
    return try {
        val session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE)
        val tssProducer = session.producerForQueue("queue:///$tssQueue?targetClient=1")

        val enkeltSamhandler = fetchTssSamhandlerData(samhandlerfnr, tssProducer, session)
        securelog.info("enkeltSamhandler: ${ objectMapper.writeValueAsString(enkeltSamhandler)}")

        enkeltSamhandler?.firstOrNull()?.samhandlerAvd125?.samhAvd?.find {
            it.avdNr == "01"
        }?.idOffTSS
    } catch (e: Exception) {
        log.error("Call to tss throws error", e)
        null
    }
}

suspend fun findBestTssInfotrygdId(
    samhandlerfnr: String,
    connection: Connection,
    tssQueue: String,
): String? {
    return try {
        val session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE)
        val tssProducer = session.producerForQueue("queue:///$tssQueue?targetClient=1")

        val enkeltSamhandler = fetchTssSamhandlerData(samhandlerfnr, tssProducer, session)
        securelog.info("enkeltSamhandler: ${ objectMapper.writeValueAsString(enkeltSamhandler)}")

        enkeltSamhandler?.firstOrNull()?.samhandlerAvd125?.samhAvd?.find {
            it.avdNr == "01"
        }?.idOffTSS
    } catch (e: Exception) {
        log.error("Call to tss throws error", e)
        null
    }
}
