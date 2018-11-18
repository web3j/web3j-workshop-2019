package io.blk.jaxlondon;

import okhttp3.*;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.NetVersion;
import org.web3j.protocol.http.HttpService;

import java.io.IOException;

/**
 * Check network version
 */
public class NetworkVersion {

    public static void main(String[] args) throws Exception {

// There is an issue with the root authority we used not being included in version of java prior
// to 8u101.
// According to https://bugs.openjdk.java.net/browse/JDK-8154757 the IdenTrust CA will be included in Oracle Java 8u101.]
//
// Exception in thread "main" javax.net.ssl.SSLHandshakeException: sun.security.validator.ValidatorException: PKIX path building failed: sun.security.provider.certpath.SunCertPathBuilderException: unable to find valid certification path to requested target
//
// The certificate required is included in the root repo and can be installed using keytool.
// e.g sudo keytool -trustcacerts -keystore /Library/Java/JavaVirtualMachines/jdk1.8.0_65.jdk/Contents/Home/jre/lib/security/cacerts -storepass SOMETHING_SECURE -noprompt -importcert -alias lets-encrypt-x3-cross-signed -file lets-encrypt-x3-cross-signed.der

        OkHttpClient client = new OkHttpClient().newBuilder()
                .authenticator((route, response) -> {
                    String credential = Credentials.basic("epirus", "epirus-rocks");
                    return response.request().newBuilder().header("Authorization", credential).build();
                })
                .build();

        Web3j web3j = Web3j.build(
                new HttpService(
                        "https://rinkby-geth.clients.epirus.blk.io", client, false));
        NetVersion netVersion =
                web3j.netVersion().send();
        System.out.println("Network Version = " + netVersion.getNetVersion());
    }
}