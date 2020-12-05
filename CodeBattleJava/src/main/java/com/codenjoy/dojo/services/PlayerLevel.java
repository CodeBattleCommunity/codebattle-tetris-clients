package com.codenjoy.dojo.services;

import lombok.*;

@RequiredArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
public class PlayerLevel {
    private final int current;
    private final int lastPassed;
    private final int total;
}
