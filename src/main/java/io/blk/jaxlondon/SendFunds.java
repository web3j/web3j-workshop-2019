package io.blk.jaxlondon;

import okhttp3.OkHttpClient;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.Transfer;
import org.web3j.utils.Convert;

import java.math.BigDecimal;

public class SendFunds {
    public static void main(String... args) throws Exception {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .authenticator((route, response) -> {
                    String credential = okhttp3.Credentials.basic("epirus", "epirus-rocks");
                    return response.request().newBuilder().header("Authorization", credential).build();
                })
                .build();

        // Connecting Web3j to a Blockchain Node
        Web3j web3j = Web3j.build(
                new HttpService(
                        "https://epirus:epirus-rocks@geth.epirus.web3labs.com/rinkeby", client, false));

        Credentials credentials = WalletUtils.loadCredentials(
                "<password>",
                "<walletfile>");

        TransactionReceipt transactionReceipt =
                Transfer.sendFunds(
                        web3j,
                        credentials, "0x2dfBf35bb7c3c0A466A6C48BEBf3eF7576d3C420",
                        new BigDecimal("0.1"), Convert.Unit.ETHER).send();

        System.out.println("Fund transfer transaction receipt: " + transactionReceipt);
    }
}
