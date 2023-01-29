package com.maktabsharif.repository;

import com.maktabsharif.entity.Suggestions;

public class SuggestionsRepository extends BaseRepository<Suggestions> {
    public SuggestionsRepository() {
        super("Suggestions", Suggestions.class);
    }
}
