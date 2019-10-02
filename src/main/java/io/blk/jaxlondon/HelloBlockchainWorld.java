package io.blk.jaxlondon;

import io.blk.contracts.generated.Greeter;
import okhttp3.OkHttpClient;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.gas.DefaultGasProvider;

public class HelloBlockchainWorld {

    public static void main(String[] args) throws Exception {
        // Build Http Client
        OkHttpClient client = new OkHttpClient().newBuilder()
                .authenticator((route, response) -> {
                    String credential = okhttp3.Credentials.basic("epirus", "epirus-rocks");
                    return response.request().newBuilder().header("Authorization", credential).build();
                })
                .build();

        // Connect web3j to the rinkeby node
        Web3j web3j = Web3j.build(
                new HttpService(
                        "https://epirus:epirus-rocks@geth.epirus.web3labs.com/rinkeby", client, false));

        // Load personal rinkeby account
        Credentials credentials =
                WalletUtils.loadCredentials(
                        "<password>",
                        "<walletfile>");

        // Deploy smart contract
        Greeter contract = Greeter.deploy(
                web3j, credentials,
                DefaultGasProvider.GAS_PRICE,
                DefaultGasProvider.GAS_LIMIT,
                "Hello blockchain world!").send();

        // Call smart contract method
        String greeting = contract.greet().send();
        System.out.println(greeting);

        // Add a new greeting to the smart contract
        TransactionReceipt transactionReceipt =
                contract.newGreeting("Hello new blockchain world!").send();
        System.out.println(transactionReceipt.getTransactionHash());

        // Call method
        String newGreeting = contract.greet().send();
        System.out.println(newGreeting);
    }
}
