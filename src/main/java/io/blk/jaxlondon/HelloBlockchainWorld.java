package io.blk.jaxlondon;

import io.blk.contracts.generated.Greeter;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.quorum.Quorum;
import org.web3j.quorum.tx.ClientTransactionManager;
import org.web3j.tx.gas.DefaultGasProvider;

import java.math.BigInteger;
import java.util.Collections;

public class HelloBlockchainWorld {

    public static void main(String[] args) throws Exception {

        // TODO: Please run epirus.sh first and verify that block are being created
        // https://github.com/blk-io/epirus
        Quorum quorum = Quorum.build(new HttpService(
                "http://localhost:22001"));

        ClientTransactionManager transactionManager =
                new ClientTransactionManager(quorum,
                        "0xed9d02e382b34818e88b88a309c7fe71e65f419d",
                        "BULeR8JyUWhiuuCMU/HLA0Q5pzkYT+cHII3ZKBey3Bo=",
                        Collections.singletonList("oNspPPgszVUFw0qmGFfWwh1uxVUXgvBxleXORHj07g8="));

        Greeter contract = Greeter.deploy(
                quorum, transactionManager,
                BigInteger.ZERO, // gas price is 0 in quorum
                DefaultGasProvider.GAS_LIMIT,
                "Hello quorum world!").send();

        String greeting = contract.greet().send();
        System.out.println(greeting);

        TransactionReceipt transactionReceipt =
                contract.newGreeting("Hello new quorum world!").send();
        System.out.println(transactionReceipt.getTransactionHash());

        String newGreeting = contract.greet().send();
        System.out.println(newGreeting);
    }
}
