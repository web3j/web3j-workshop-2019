pragma solidity ^0.5.11;

contract Mortal {
    address owner;

    constructor() public {
        owner = msg.sender;
    }

    function kill() public {
        if (msg.sender == owner) selfdestruct(msg.sender);
    }
}

contract Greeter is Mortal {
    string greeting;

    constructor(string memory _greeting) public {
        greeting = _greeting;
    }

    // getter
    function greet() public view returns (string memory) {
        return greeting;
    }
}
