package com.github.jntm.web3jspringbootstarter;

import org.web3j.tx.gas.ContractGasProvider;

import java.math.BigInteger;

public class DefaultGasProvider implements ContractGasProvider {
    public static final BigInteger GAS_LIMIT = BigInteger.valueOf(100_000);
    public static final BigInteger GAS_PRICE = BigInteger.valueOf(999999_000L);

    @Override
    public BigInteger getGasPrice() {
        return GAS_PRICE;
    }

    @Override
    public BigInteger getGasLimit() {
        return GAS_LIMIT;
    }

    @Override
    public BigInteger getGasPrice(String contractFunc) {
        return GAS_PRICE;
    }

    @Override
    public BigInteger getGasLimit(String contractFunc) {
        return GAS_LIMIT;
    }
}
