package com.example.demo.model;

import static java.time.OffsetDateTime.now;
import static java.time.ZoneOffset.UTC;

import java.time.Instant;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
@Getter
@Setter
public abstract class BaseEntity {

  private Instant createdAt;
  private Instant updatedAt;

  @PrePersist
  public void addTimestamps() {
    Instant nowInstant = now(UTC).toInstant();
    this.createdAt = nowInstant;
    this.updatedAt = nowInstant;
  }

  @PreUpdate
  public void updateTimestamp() {
    this.updatedAt = now(UTC).toInstant();
  }
}
