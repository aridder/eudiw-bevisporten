package net.eidas2sandkasse.tsl;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.time.Instant;

/**
 * Real status lists, fetched 2026-11-18. uri and idx come from real test PIDs. The signing certificates expire
 * from late 2027.
 */
final class TestData {

    static final String JWT = "application/statuslist+jwt";
    static final String CWT = "application/statuslist+cwt";

    // Not the sub of any list below. The real lists keep their own uris, since sub is signed.
    static final String OTHER_URI = "https://status.junit.eidas2sandkasse.dev/lists/1";

    // Diia (UA), mdoc PID. x5chain in the unprotected header of the CWT.
    static final String UA_URI =
            "https://api2s.diia.gov.ua/api/v1/euid/wallet/token_status_list/UA/eu.europa.ec.eudi.pid.1/95d1989e-9318-46c3-9445-65e34a6687dc";
    static final long UA_IDX_VALID = 65826;
    static final long UA_IDX_REVOKED = 36148;
    static final Instant UA_ISSUED_AT = Instant.ofEpochSecond(1795005049);
    static final Instant UA_CWT_EXPIRES_AT = Instant.ofEpochSecond(1795091509);

    // Portugal, mdoc PID. x5chain in the protected header of the CWT.
    static final String PT_URI =
            "https://walletreg.autoservicework.com/token_status_list/PT/eu.europa.ec.eudi.pid.1/76d0ec68-da3c-4a0d-8e5b-41331b269a50";
    static final long PT_IDX_VALID = 986;
    static final Instant PT_ISSUED_AT = Instant.ofEpochSecond(1794960002);
    static final Instant PT_EXPIRES_AT = Instant.ofEpochSecond(1795046402);

    // A-SIT (AT), mdoc PID. x5chain in the unprotected header of the CWT.
    static final String AT_URI = "https://wallet-issuer.a-sit.plus/credentials/status/eu.europa.ec.eudi.pid.1/1";
    static final long AT_IDX_VALID = 696;
    static final Instant AT_ISSUED_AT = Instant.ofEpochSecond(1794753481);

    static byte[] statusList(String name) {
        try (InputStream in = TestData.class.getResourceAsStream("/statuslist/" + name)) {
            if (in == null) {
                throw new IllegalArgumentException("Missing test resource " + name);
            }
            return in.readAllBytes();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private TestData() {
    }
}
