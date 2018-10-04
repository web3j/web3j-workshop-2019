package io.blk.jaxlondon;

import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.NetVersion;
import org.web3j.protocol.http.HttpService;

/**
 * Check network version
 */
public class NetworkVersion {

    public static void main(String[] args) throws Exception {
        //FIXME insert your infura token below
        Web3j web3j = Web3j.build(
                new HttpService(
                        "https://rinkeby.infura.io/<infura_token>"));
        NetVersion netVersion =
                web3j.netVersion().send();
        System.out.println("Network Version = " + netVersion.getNetVersion());
    }
}