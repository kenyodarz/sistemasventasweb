package com.bykenyodarz.sistemasventasweb.aspect

import io.micrometer.core.instrument.Counter
import io.micrometer.core.instrument.Gauge
import io.micrometer.core.instrument.MeterRegistry
import io.micrometer.core.instrument.Timer
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Pointcut
import org.springframework.stereotype.Component
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicInteger

@Aspect
@Component
class GoldenSignalsAspect(private val registry: MeterRegistry) {

    private val activeRequests = ConcurrentHashMap<String, AtomicInteger>()

    @Pointcut("within(com.bykenyodarz.sistemasventasweb.controllers..*) || execution(* com.bykenyodarz.sistemasventasweb.shared.GenericRestController+.*(..))")
    fun controllerMethods() {}

    @Around("controllerMethods()")
    fun monitorGoldenSignals(joinPoint: ProceedingJoinPoint): Any? {
        val request = (RequestContextHolder.getRequestAttributes() as? ServletRequestAttributes)?.request
        val uri = request?.requestURI ?: joinPoint.signature.declaringType.simpleName
        val method = request?.method ?: joinPoint.signature.name

        // 4. Saturación (Peticiones activas concurrentes)
        val activeCount = getOrCreateActiveRequestGauge(uri, method)
        activeCount.incrementAndGet()

        val startTime = System.nanoTime()
        var status = "2xx" // Default status class
        var exceptionName = "none"

        try {
            val result = joinPoint.proceed()
            // Intentar resolver el código de estado HTTP exacto
            if (result is org.springframework.http.ResponseEntity<*>) {
                status = "${result.statusCode.value()}"
            }
            return result
        } catch (ex: Throwable) {
            status = "500"
            exceptionName = ex.javaClass.simpleName
            
            // 2. Errores (Conteo de fallos)
            Counter.builder("http_requests_errors_total")
                .description("Total number of errors encountered during requests")
                .tag("uri", uri)
                .tag("method", method)
                .tag("exception", exceptionName)
                .register(registry)
                .increment()

            throw ex
        } finally {
            val duration = System.nanoTime() - startTime
            activeCount.decrementAndGet()

            // 1. Latencia (Tiempo de ejecución)
            Timer.builder("http_requests_latency_seconds")
                .description("Time taken to process the HTTP request")
                .tag("uri", uri)
                .tag("method", method)
                .register(registry)
                .record(duration, TimeUnit.NANOSECONDS)

            // 3. Tráfico (Total de peticiones procesadas)
            Counter.builder("http_requests_total")
                .description("Total number of HTTP requests processed")
                .tag("uri", uri)
                .tag("method", method)
                .tag("status", status)
                .register(registry)
                .increment()
        }
    }

    private fun getOrCreateActiveRequestGauge(uri: String, method: String): AtomicInteger {
        val key = "$method:$uri"
        return activeRequests.computeIfAbsent(key) {
            val atomicValue = AtomicInteger(0)
            Gauge.builder("http_requests_active", { atomicValue.get().toDouble() })
                .description("Number of concurrent active requests representing saturation")
                .tag("uri", uri)
                .tag("method", method)
                .register(registry)
            atomicValue
        }
    }
}
