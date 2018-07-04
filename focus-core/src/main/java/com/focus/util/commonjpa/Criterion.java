package com.focus.util.commonjpa;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public interface Criterion {
    enum Operator {
        EQ, NE, LIKE, GT, LT, GTE, LTE, AND, OR, IS_MEMBER, IS_NOT_MEMBER
    }

    Predicate toPredicate(Root<?> root, CriteriaQuery<?> query,
                          CriteriaBuilder builder);
}