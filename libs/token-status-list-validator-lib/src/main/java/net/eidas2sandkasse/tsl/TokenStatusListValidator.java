package net.eidas2sandkasse.tsl;

import io.ktor.client.engine.HttpClientEngine;
import net.eidas2sandkasse.tsl.walt.WaltStatusListClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Clock;

/**
 * Validates a credential against an IETF Token Status List, as statuslist+jwt or statuslist+cwt.
 * Status 0 (VALID) gives true, every other status gives false.
 */
public class TokenStatusListValidator {

    private static final Logger log = LoggerFactory.getLogger(TokenStatusListValidator.class);

    private final WaltStatusListClient client;

    public TokenStatusListValidator() {
        this(TokenStatusListConfig.defaults());
    }

    public TokenStatusListValidator(TokenStatusListConfig config) {
        this.client = new WaltStatusListClient(
                config.connectTimeout().toMillis(),
                config.requestTimeout().toMillis(),
                config.cacheEnabled());
    }

    // For tests
    TokenStatusListValidator(TokenStatusListConfig config, Clock clock, HttpClientEngine engine) {
        this.client = new WaltStatusListClient(
                config.connectTimeout().toMillis(),
                config.requestTimeout().toMillis(),
                config.cacheEnabled(),
                clock,
                engine);
    }

    /**
     * Validates a token status list entry.
     *
     * @throws TokenStatusListException if the status could not be determined
     */
    public boolean validate(String uri, Long index) {
        if (uri == null || uri.isBlank() || index == null || index < 0 || index > Integer.MAX_VALUE) {
            throw new IllegalArgumentException("Status list uri and a non-negative int index are required");
        }
        try {
            return client.resolveStatus(uri, index.intValue()) == 0;
        } catch (Exception e) {
            log.warn("Status list {} could not be resolved for idx {}", uri, index);
            throw new TokenStatusListException("Could not resolve token status list status", e);
        }
    }
}
