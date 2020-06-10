package com.github.jntm.web3jspringbootstarter.contract;


import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 4.5.11.
 */
@SuppressWarnings("rawtypes")
public class UscSummary extends Contract {
    public static final String BINARY = "608060405234801561001057600080fd5b506040516102cb3803806102cb8339810160409081528151602080840151928401516060850151608086015160008054600160a060020a03191633179055948601805194969095920193909261006b916004918701906100ad565b5060038054600160a060020a031916600160a060020a038716179055825161009a9060059060208601906100ad565b5060069190915560075550610148915050565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f106100ee57805160ff191683800117855561011b565b8280016001018555821561011b579182015b8281111561011b578251825591602001919060010190610100565b5061012792915061012b565b5090565b61014591905b808211156101275760008155600101610131565b90565b610174806101576000396000f30060806040526004361061004b5763ffffffff7c0100000000000000000000000000000000000000000000000000000000600035041663f43970f38114610050578063ffe6a18e1461006a575b600080fd5b34801561005c57600080fd5b50610068600435610091565b005b34801561007657600080fd5b5061007f610142565b60408051918252519081900360200190f35b60005473ffffffffffffffffffffffffffffffffffffffff16331461013d57604080517f08c379a000000000000000000000000000000000000000000000000000000000815260206004820152602160248201527f6f6e6c79206f776e657220697320616c6c6f77656420746f206f70657261746560448201527f2e00000000000000000000000000000000000000000000000000000000000000606482015290519081900360840190fd5b600755565b600754905600a165627a7a72305820ceea63b6391188007517d861ff7744f9ded33e9e15cd552379e2f44f0c5dfddc0029";

    public static final String FUNC_UPDATEREPUTATION = "updateReputation";

    public static final String FUNC_GETREPUTATION = "getReputation";

    @Deprecated
    protected UscSummary(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected UscSummary(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected UscSummary(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected UscSummary(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteFunctionCall<TransactionReceipt> updateReputation(BigInteger _rep) {
        final Function function = new Function(
                FUNC_UPDATEREPUTATION, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_rep)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<BigInteger> getReputation() {
        final Function function = new Function(FUNC_GETREPUTATION, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    @Deprecated
    public static UscSummary load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new UscSummary(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static UscSummary load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new UscSummary(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static UscSummary load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new UscSummary(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static UscSummary load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new UscSummary(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<UscSummary> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider, String _addr, String _userName, String _profile, BigInteger _registerTime, BigInteger _reputation) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _addr), 
                new org.web3j.abi.datatypes.Utf8String(_userName), 
                new org.web3j.abi.datatypes.Utf8String(_profile), 
                new org.web3j.abi.datatypes.generated.Uint256(_registerTime), 
                new org.web3j.abi.datatypes.generated.Uint256(_reputation)));
        return deployRemoteCall(UscSummary.class, web3j, credentials, contractGasProvider, BINARY, encodedConstructor);
    }

    public static RemoteCall<UscSummary> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider, String _addr, String _userName, String _profile, BigInteger _registerTime, BigInteger _reputation) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _addr), 
                new org.web3j.abi.datatypes.Utf8String(_userName), 
                new org.web3j.abi.datatypes.Utf8String(_profile), 
                new org.web3j.abi.datatypes.generated.Uint256(_registerTime), 
                new org.web3j.abi.datatypes.generated.Uint256(_reputation)));
        return deployRemoteCall(UscSummary.class, web3j, transactionManager, contractGasProvider, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<UscSummary> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, String _addr, String _userName, String _profile, BigInteger _registerTime, BigInteger _reputation) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _addr), 
                new org.web3j.abi.datatypes.Utf8String(_userName), 
                new org.web3j.abi.datatypes.Utf8String(_profile), 
                new org.web3j.abi.datatypes.generated.Uint256(_registerTime), 
                new org.web3j.abi.datatypes.generated.Uint256(_reputation)));
        return deployRemoteCall(UscSummary.class, web3j, credentials, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<UscSummary> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, String _addr, String _userName, String _profile, BigInteger _registerTime, BigInteger _reputation) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _addr), 
                new org.web3j.abi.datatypes.Utf8String(_userName), 
                new org.web3j.abi.datatypes.Utf8String(_profile), 
                new org.web3j.abi.datatypes.generated.Uint256(_registerTime), 
                new org.web3j.abi.datatypes.generated.Uint256(_reputation)));
        return deployRemoteCall(UscSummary.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, encodedConstructor);
    }
}
