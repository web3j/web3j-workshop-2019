package io.blk.jaxlondon;

import okhttp3.OkHttpClient;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.model.Greeter;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.gas.DefaultGasProvider;

public class HelloBlockchainWorld {

    public static void main(String[] args) throws Exception {

        OkHttpClient client = new OkHttpClient().newBuilder()
                .authenticator((route, response) -> {
                    String credential = okhttp3.Credentials.basic("epirus", "epirus-rocks");
                    return response.request().newBuilder().header("Authorization", credential).build();
                })
                .build();

        Web3j web3j = Web3j.build(
                new HttpService(
                        "https://rinkby-geth.clients.epirus.blk.io", client, false));

        Credentials credentials =
                WalletUtils.loadCredentials(
                        "<password>",
                        "<walletfile>.json");

        Greeter contract = Greeter.deploy(
                web3j, credentials,
                DefaultGasProvider.GAS_PRICE,
                DefaultGasProvider.GAS_LIMIT,
                "Hello blockchain world!").send();

        String greeting = contract.greet().send();
        System.out.println(greeting);

        TransactionReceipt transactionReceipt =
                contract.newGreeting("Hello new blockchain world!").send();
        System.out.println(transactionReceipt.getTransactionHash());

        String newGreeting = contract.greet().send();
        System.out.println(newGreeting);
    }
}
