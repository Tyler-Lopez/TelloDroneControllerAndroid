package com.amplifyframework.datastore.generated.model;

import com.amplifyframework.core.model.annotations.BelongsTo;
import com.amplifyframework.core.model.temporal.Temporal;

import java.util.List;
import java.util.UUID;
import java.util.Objects;

import androidx.core.util.ObjectsCompat;

import com.amplifyframework.core.model.Model;
import com.amplifyframework.core.model.annotations.Index;
import com.amplifyframework.core.model.annotations.ModelConfig;
import com.amplifyframework.core.model.annotations.ModelField;
import com.amplifyframework.core.model.query.predicate.QueryField;

import static com.amplifyframework.core.model.query.predicate.QueryField.field;

/** This is an auto generated class representing the TelloFlightChallenge type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "TelloFlightChallenges")
@Index(name = "byChallenge", fields = {"challengeID"})
@Index(name = "byTelloFlight", fields = {"telloFlightID"})
public final class TelloFlightChallenge implements Model {
  public static final QueryField ID = field("TelloFlightChallenge", "id");
  public static final QueryField CHALLENGE = field("TelloFlightChallenge", "challengeID");
  public static final QueryField TELLO_FLIGHT = field("TelloFlightChallenge", "telloFlightID");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="Challenge", isRequired = true) @BelongsTo(targetName = "challengeID", type = Challenge.class) Challenge challenge;
  private final @ModelField(targetType="TelloFlight", isRequired = true) @BelongsTo(targetName = "telloFlightID", type = TelloFlight.class) TelloFlight telloFlight;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime createdAt;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime updatedAt;
  public String getId() {
      return id;
  }
  
  public Challenge getChallenge() {
      return challenge;
  }
  
  public TelloFlight getTelloFlight() {
      return telloFlight;
  }
  
  public Temporal.DateTime getCreatedAt() {
      return createdAt;
  }
  
  public Temporal.DateTime getUpdatedAt() {
      return updatedAt;
  }
  
  private TelloFlightChallenge(String id, Challenge challenge, TelloFlight telloFlight) {
    this.id = id;
    this.challenge = challenge;
    this.telloFlight = telloFlight;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      TelloFlightChallenge telloFlightChallenge = (TelloFlightChallenge) obj;
      return ObjectsCompat.equals(getId(), telloFlightChallenge.getId()) &&
              ObjectsCompat.equals(getChallenge(), telloFlightChallenge.getChallenge()) &&
              ObjectsCompat.equals(getTelloFlight(), telloFlightChallenge.getTelloFlight()) &&
              ObjectsCompat.equals(getCreatedAt(), telloFlightChallenge.getCreatedAt()) &&
              ObjectsCompat.equals(getUpdatedAt(), telloFlightChallenge.getUpdatedAt());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getChallenge())
      .append(getTelloFlight())
      .append(getCreatedAt())
      .append(getUpdatedAt())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("TelloFlightChallenge {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("challenge=" + String.valueOf(getChallenge()) + ", ")
      .append("telloFlight=" + String.valueOf(getTelloFlight()) + ", ")
      .append("createdAt=" + String.valueOf(getCreatedAt()) + ", ")
      .append("updatedAt=" + String.valueOf(getUpdatedAt()))
      .append("}")
      .toString();
  }
  
  public static ChallengeStep builder() {
      return new Builder();
  }
  
  /**
   * WARNING: This method should not be used to build an instance of this object for a CREATE mutation.
   * This is a convenience method to return an instance of the object with only its ID populated
   * to be used in the context of a parameter in a delete mutation or referencing a foreign key
   * in a relationship.
   * @param id the id of the existing item this instance will represent
   * @return an instance of this model with only ID populated
   */
  public static TelloFlightChallenge justId(String id) {
    return new TelloFlightChallenge(
      id,
      null,
      null
    );
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(id,
      challenge,
      telloFlight);
  }
  public interface ChallengeStep {
    TelloFlightStep challenge(Challenge challenge);
  }
  

  public interface TelloFlightStep {
    BuildStep telloFlight(TelloFlight telloFlight);
  }
  

  public interface BuildStep {
    TelloFlightChallenge build();
    BuildStep id(String id);
  }
  

  public static class Builder implements ChallengeStep, TelloFlightStep, BuildStep {
    private String id;
    private Challenge challenge;
    private TelloFlight telloFlight;
    @Override
     public TelloFlightChallenge build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new TelloFlightChallenge(
          id,
          challenge,
          telloFlight);
    }
    
    @Override
     public TelloFlightStep challenge(Challenge challenge) {
        Objects.requireNonNull(challenge);
        this.challenge = challenge;
        return this;
    }
    
    @Override
     public BuildStep telloFlight(TelloFlight telloFlight) {
        Objects.requireNonNull(telloFlight);
        this.telloFlight = telloFlight;
        return this;
    }
    
    /**
     * @param id id
     * @return Current Builder instance, for fluent method chaining
     */
    public BuildStep id(String id) {
        this.id = id;
        return this;
    }
  }
  

  public final class CopyOfBuilder extends Builder {
    private CopyOfBuilder(String id, Challenge challenge, TelloFlight telloFlight) {
      super.id(id);
      super.challenge(challenge)
        .telloFlight(telloFlight);
    }
    
    @Override
     public CopyOfBuilder challenge(Challenge challenge) {
      return (CopyOfBuilder) super.challenge(challenge);
    }
    
    @Override
     public CopyOfBuilder telloFlight(TelloFlight telloFlight) {
      return (CopyOfBuilder) super.telloFlight(telloFlight);
    }
  }
  
}
