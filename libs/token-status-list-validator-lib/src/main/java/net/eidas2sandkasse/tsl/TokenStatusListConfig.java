package net.eidas2sandkasse.tsl;

import java.time.Duration;

/**
 * @param connectTimeout TCP connect timeout against the status list endpoint
 * @param requestTimeout timeout for the whole request, connect and response included
 * @param cacheEnabled   cache status lists in memory (walt.id: max 256 lists, expiry from Cache-Control, exp and ttl)
 */
public record TokenStatusListConfig(Duration connectTimeout, Duration requestTimeout, boolean cacheEnabled) {

    public static TokenStatusListConfig defaults() {
        return new TokenStatusListConfig(Duration.ofSeconds(3), Duration.ofSeconds(5), true);
    }
}
