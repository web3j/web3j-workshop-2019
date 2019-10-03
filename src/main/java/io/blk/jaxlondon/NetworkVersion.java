package io.blk.jaxlondon;

import okhttp3.*;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.NetVersion;
import org.web3j.protocol.http.HttpService;


/**
 * Check network version
 */
public class NetworkVersion {

    public static void main(String[] args) throws Exception {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .authenticator((route, response) -> {
                    String credential = Credentials.basic("epirus", "epirus-rocks");
                    return response.request().newBuilder().header("Authorization", credential).build();
                })
                .build();

        // Connecting Web3j to a Blockchain Node
        Web3j web3j = Web3j.build(
                new HttpService(
                        "https://epirus:epirus-rocks@geth.epirus.web3labs.com/rinkeby", client, false));
        // Querying the node for the network version
        NetVersion netVersion =
                web3j.netVersion().send();

        System.out.println("Network Version = " + netVersion.getNetVersion());
    }
}