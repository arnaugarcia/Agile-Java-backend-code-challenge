package com.agiletv.users.infrastructure.persistence;

import com.agiletv.users.domain.model.Gender;

public enum GenderEntity {
    MALE, FEMALE, OTHER;

    public static GenderEntity fromDomain(Gender gender) {
        return GenderEntity.valueOf(gender.name());
    }

    public Gender toDomain() {
        return Gender.valueOf(this.name());
    }
}
