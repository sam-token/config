package com.typesafe.config.impl;

import org.pcollections.HashPMap;
import org.pcollections.HashTreePMap;

/**
 * This exists because we have to memoize resolved substitutions as we go
 * through the config tree; otherwise we could end up creating multiple copies
 * of values or whole trees of values as we follow chains of substitutions.
 */
final class ResolveMemos {
    // note that we can resolve things to undefined (represented as Java null,
    // rather than ConfigNull) so this map can have null values.
    final private HashPMap<MemoKey, AbstractConfigValue> memos;

    private ResolveMemos(HashPMap<MemoKey, AbstractConfigValue> memos) {
        this.memos = memos;
    }

    ResolveMemos() {
        this(HashTreePMap.empty());
    }

    AbstractConfigValue get(MemoKey key) {
        return memos.get(key);
    }

    ResolveMemos put(MemoKey key, AbstractConfigValue value) {
        return new ResolveMemos(memos.plus(key, value));
    }
}
