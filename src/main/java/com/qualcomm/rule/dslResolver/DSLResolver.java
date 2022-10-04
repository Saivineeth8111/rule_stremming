package com.qualcomm.rule.dslResolver;

public interface DSLResolver {
    String getResolverKeyword();
    Object resolveValue(String keyword);
}
