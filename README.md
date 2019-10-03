## JAX London Web3j Workshop 2019

#### Prerequisities

1. **Java 8**

2. **IntelliJ Idea/other IDE**

3. **Solidity Compiler (solc)**

   On MacOS:

   ```bash
   brew tap ethereum/ethereum
   brew install solidity
   ```

   On Linux (apt-based):

   ```bash
   sudo add-apt-repository ppa:ethereum/ethereum
   sudo apt-get update
   sudo apt-get install solc
   ```

   On Windows:

   Go to https://github.com/ethereum/solidity/releases and download the latest release, then extract the zip file and add  `solc.exe` to your PATH variable.

   

4. **Web3j command line tools**

   On MacOS/Linux:

   ```bash
   curl -s https://raw.githubusercontent.com/web3j/web3j-installer/master/web3j.sh | bash
   ```

   On Windows:

   Go to https://github.com/web3j/web3j/releases and download the latest release, then extract the zip file and add web3j.bat to your PATH variable.

5. **Hyperledger Besu**

   On any OS:

   Go to https://bintray.com/hyperledger-org/besu-repo/besu#files and download the latest release. Navigate to `besu/bin` inside the archive, besu can be started using the shell script/batch file in that folder.

