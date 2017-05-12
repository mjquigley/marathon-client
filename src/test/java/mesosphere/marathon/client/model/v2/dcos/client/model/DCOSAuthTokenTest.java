package mesosphere.marathon.client.model.v2.dcos.client.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.text.ParseException;
import org.junit.Test;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JOSEObjectType;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import com.nimbusds.jwt.util.DateUtils;

import mesosphere.dcos.client.model.DCOSAuthToken;

/**
 * Created by tt044106 on 2/27/17.
 */
public class DCOSAuthTokenTest {
    @Test
    public void testExpiry1() throws ParseException, JOSEException, NoSuchAlgorithmException, NoSuchProviderException {

        final String issuer = "https://mydomain.com/";
        final long issuedAtTime = System.currentTimeMillis() / 1000L;
        final long expirationTime = issuedAtTime + 86400L;

        final JWSHeader header = new JWSHeader.Builder(JWSAlgorithm.RS256).type(JOSEObjectType.JWT).build();
        final JWTClaimsSet payload = new JWTClaimsSet.Builder()
                .issuer(issuer)
                .issueTime(DateUtils.fromSecondsSinceEpoch(issuedAtTime))
                .expirationTime(DateUtils.fromSecondsSinceEpoch(expirationTime))
                .build();

        final SignedJWT signedJWT = new SignedJWT(header, payload);

        signedJWT.sign(new RSASSASigner(generatePrivateKey()));

        assertFalse(new DCOSAuthToken(signedJWT.serialize()).requiresRefresh());
    }

    @Test
    public void testExpiry2() throws ParseException, JOSEException, NoSuchAlgorithmException, NoSuchProviderException {

        final String issuer = "https://mydomain.com/";
        final long issuedAtTime = System.currentTimeMillis() / 1000L - 86400L;
        final long expirationTime = issuedAtTime + 60L;

        final JWSHeader header = new JWSHeader.Builder(JWSAlgorithm.RS256).type(JOSEObjectType.JWT).build();
        final JWTClaimsSet payload = new JWTClaimsSet.Builder()
                .issuer(issuer)
                .issueTime(DateUtils.fromSecondsSinceEpoch(issuedAtTime))
                .expirationTime(DateUtils.fromSecondsSinceEpoch(expirationTime))
                .build();

        final SignedJWT signedJWT = new SignedJWT(header, payload);

        signedJWT.sign(new RSASSASigner(generatePrivateKey()));

        assertTrue(new DCOSAuthToken(signedJWT.serialize()).requiresRefresh());
    }

    private static PrivateKey generatePrivateKey() throws NoSuchProviderException, NoSuchAlgorithmException {
        KeyPairGenerator keyGenerator = KeyPairGenerator.getInstance("RSA");
        keyGenerator.initialize(1024);
        return keyGenerator.generateKeyPair().getPrivate();
    }
}
