package mesosphere.dcos.client;

import feign.Feign;
import feign.FeignException;
import feign.Response;
import feign.codec.Decoder;
import feign.codec.ErrorDecoder;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;
import mesosphere.client.common.HttpClientUtils;
import mesosphere.client.common.ModelUtils;
import mesosphere.dcos.client.model.DCOSAuthCredentials;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;

public class DCOSClient {
    public static DCOS getInstance(String endpoint) {
        return getInstance(endpoint, Config.builder().build());
    }

    public static DCOS getInstance(String endpoint, final DCOSAuthCredentials authCredentials) {
        return getInstance(endpoint, Config.builder().withCredentials(authCredentials).build());
    }

    public static DCOS getInstance(String endpoint, final Config config) {
        GsonDecoder decoder = new GsonDecoder(ModelUtils.GSON);
        GsonEncoder encoder = new GsonEncoder(ModelUtils.GSON);

        Feign.Builder builder = Feign.builder()
                .client(HttpClientUtils.createHttpClient(config))
                .encoder(encoder)
                .decoder(new MesosDownloadDecoder(decoder))
                .errorDecoder(new DCOSErrorDecoder());

        if (config.getCredentials() != null) {
            // Have to create a DCOS instance without authentication credentials that will be responsible for refreshing the authentication token.
            final Config noAuthConfig = Config.builder(config).withCredentials(null).build();
            builder.requestInterceptor(new DCOSAuthTokenHeaderInterceptor(config.getCredentials(), getInstance(endpoint, noAuthConfig)));
        }

        builder.requestInterceptor(new DCOSAPIInterceptor());

        return builder.target(DCOS.class, endpoint);
    }

    private static class MesosDownloadDecoder implements Decoder {
        private final Decoder delegate;

        MesosDownloadDecoder(final Decoder delegate) {
            super();
            this.delegate = delegate;
        }

        @Override
        public Object decode(Response response, Type type) throws IOException, FeignException {
            if (response.headers().get("Content-Type").contains("application/octet-stream")) {
                Reader reader = response.body().asReader();
                int charsExpected = response.body().length();
                char[] charArray = new char[charsExpected];
                int charsRead = reader.read(charArray);

                if (charsRead == charsExpected) {
                    return new String(charArray);
                }
            }

            return delegate.decode(response, type);
        }
    }

    private static class DCOSErrorDecoder implements ErrorDecoder {
        @Override
        public Exception decode(String methodKey, Response response) {

            String details;
            try {
                details = IOUtils.toString(response.body().asInputStream(), "UTF-8");
            } catch (NullPointerException | IOException e) {
                details = "Unable to read response body";
            }
            return new DCOSException(response.status(), response.reason(), methodKey, details);
        }
    }
}
