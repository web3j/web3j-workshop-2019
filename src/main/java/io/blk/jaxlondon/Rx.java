package io.blk.jaxlondon;

import io.reactivex.Single;
import io.reactivex.disposables.Disposable;
import okhttp3.Credentials;
import okhttp3.OkHttpClient;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.EthBlock;
import org.web3j.protocol.core.methods.response.Transaction;
import org.web3j.protocol.http.HttpService;
import org.web3j.utils.Convert;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Demonstrations of working with RxJava's Flowable in web3j.
 */
public class Rx {
    private static final int COUNT = 10;

    private final Web3j web3j;

    public Rx() {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .authenticator((route, response) -> {
                    String credential = Credentials.basic("epirus", "epirus-rocks");
                    return response.request().newBuilder().header("Authorization", credential).build();
                })
                .build();

        // Connecting Web3j to a Blockchain Node
        web3j = Web3j.build(
                new HttpService(
                        "https://epirus:epirus-rocks@geth.epirus.web3labs.com/rinkeby", client, false));
    }

    private void run() throws Exception {
        System.out.println("Doing simpleFilterExample");
        simpleFilterExample();

        System.out.println("Doing blockInfoExample");
        blockInfoExample();

        System.out.println("Doing countingEtherExample");
        countingEtherExample();

        System.out.println("Doing clientVersionExample");
        clientVersionExample();

        System.exit(0);  // we explicitly call the exit to clean up our ScheduledThreadPoolExecutor used by web3j
    }

    public static void main(String[] args) throws Exception {
        new Rx().run();
    }

    void simpleFilterExample() throws Exception {

        Disposable subscription = web3j.blockFlowable(false).subscribe(block -> {
            System.out.println("Sweet, block number " + block.getBlock().getNumber()
                    + " has just been created");
        }, Throwable::printStackTrace);

        TimeUnit.MINUTES.sleep(1);
        subscription.dispose();
    }

    void blockInfoExample() throws Exception {
        CountDownLatch countDownLatch = new CountDownLatch(COUNT);

        System.out.println("Waiting for " + COUNT + " blocks...");
        Disposable subscription = web3j.blockFlowable(true)
                .take(COUNT)
                .subscribe(ethBlock -> {
                    EthBlock.Block block = ethBlock.getBlock();
                    LocalDateTime timestamp = Instant.ofEpochSecond(
                            block.getTimestamp()
                                    .longValueExact()).atZone(ZoneId.of("UTC")).toLocalDateTime();
                    int transactionCount = block.getTransactions().size();
                    String hash = block.getHash();
                    String parentHash = block.getParentHash();

                    System.out.println(
                            timestamp + " " +
                                    "Tx count: " + transactionCount + ", " +
                                    "Hash: " + hash + ", " +
                                    "Parent hash: " + parentHash
                    );
                    countDownLatch.countDown();
                }, Throwable::printStackTrace);

        countDownLatch.await(5, TimeUnit.MINUTES);
        subscription.dispose();
    }

    void countingEtherExample() throws Exception {
        CountDownLatch countDownLatch = new CountDownLatch(1);

        System.out.println("Waiting for " + COUNT + " transactions...");
        Single<BigInteger> transactionValue = web3j.transactionFlowable()
                .take(COUNT)
                .map(Transaction::getValue)
                .reduce(BigInteger.ZERO, BigInteger::add);

        Disposable subscription = transactionValue.subscribe(total -> {
            BigDecimal value = new BigDecimal(total);
            System.out.println("Transaction value: " +
                    Convert.fromWei(value, Convert.Unit.ETHER) + " Ether (" +  value + " Wei)");
            countDownLatch.countDown();
        }, Throwable::printStackTrace);

        countDownLatch.await(5, TimeUnit.MINUTES);
        subscription.dispose();
    }

    void clientVersionExample() throws Exception {
        CountDownLatch countDownLatch = new CountDownLatch(1);

        Disposable subscription = web3j.web3ClientVersion().flowable().subscribe(x -> {
            System.out.println("Client is running version: " + x.getWeb3ClientVersion());
            countDownLatch.countDown();
        });

        countDownLatch.await();
        subscription.dispose();
    }
}
