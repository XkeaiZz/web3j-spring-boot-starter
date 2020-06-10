package com.github.jntm.web3jspringbootstarter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.TypeFilter;

import java.io.IOException;
import java.util.Set;

@Slf4j
public class ContainsTypeFilter implements TypeFilter {
    private Set<String> providedContract;
    private String matchType;

    public ContainsTypeFilter(Set<String> providedContract, String matchType) {
        this.providedContract = providedContract;
        this.matchType = matchType;
    }


    @Override
    public boolean match(MetadataReader metadataReader, MetadataReaderFactory metadataReaderFactory) throws IOException {
        if (metadataReader.getClassMetadata().getSuperClassName().equals(matchType)) {
            String contractName = Utils.split(metadataReader.getClassMetadata().getClassName(),".");
            if (providedContract.contains(contractName)) {
                if (log.isDebugEnabled()) {
                    log.debug(String.format("already scan %s and find the config related with this", contractName));
                }
                return true;
            } else {
                if (log.isDebugEnabled()) {
                    log.debug(String.format("already scan %s but not find config related with this", contractName));
                    return false;
                }
            }
        }
        return false;
    }
}
