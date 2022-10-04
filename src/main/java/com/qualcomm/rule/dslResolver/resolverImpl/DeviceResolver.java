package com.qualcomm.rule.dslResolver.resolverImpl;

import com.qualcomm.rule.dslResolver.DSLResolver;
import org.springframework.stereotype.Component;

@Component
public class DeviceResolver implements DSLResolver {
    private static final String RESOLVER_KEYWORD = "device";
    private static final String GTP = "Global Terrestrial Positioning";
    private static final String GNSS = "Global navigation satellite system";

    @Override
    public String getResolverKeyword() {
        return RESOLVER_KEYWORD;
    }

    @Override
    public Object resolveValue(String keyword) {
        if (keyword.equalsIgnoreCase(GTP)){
            return "GTP2.0";
        }

        if (keyword.equalsIgnoreCase(GNSS)){
            return "GNSS";
        }

        return null;
    }
}
