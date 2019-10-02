pragma solidity >=0.4.21 <0.6.0;

// Example taken from https://www.ethereum.org/greeter, also used in
// https://github.com/ethereum/go-ethereum/wiki/Contract-Tutorial#your-first-citizen-the-greeter

contract Mortal {
    /* Define variable owner of the type address*/
    address payable owner;

    /* this function is executed at initialization and sets the owner of the contract */
    constructor() public { owner = msg.sender; }

    /* Function to recover the funds on the contract */
    function kill() public { if (msg.sender == owner) selfdestruct(owner); }
}

contract Greeter is Mortal {
    /* define variable greeting of the type string */
    string greeting;

    /* this runs when the contract is executed */
    constructor(string memory _greeting) public {
        greeting = _greeting;
    }

    function newGreeting(string memory _greeting) public {
        greeting = _greeting;
    }

    /* main function */
    function greet() public view returns (string memory) {
        return greeting;
    }
}
