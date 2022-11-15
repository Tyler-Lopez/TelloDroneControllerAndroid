package com.tlopez.datastoreDomain.repository.models;

import static com.amplifyframework.core.model.query.predicate.QueryField.field;

import androidx.core.util.ObjectsCompat;

import com.amplifyframework.core.model.AuthStrategy;
import com.amplifyframework.core.model.Model;
import com.amplifyframework.core.model.ModelOperation;
import com.amplifyframework.core.model.annotations.AuthRule;
import com.amplifyframework.core.model.annotations.HasMany;
import com.amplifyframework.core.model.annotations.Index;
import com.amplifyframework.core.model.annotations.ModelConfig;
import com.amplifyframework.core.model.annotations.ModelField;
import com.amplifyframework.core.model.query.predicate.QueryField;
import com.amplifyframework.core.model.temporal.Temporal;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

/** This is an auto generated class representing the TelloFlight type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "TelloFlights", authRules = {
  @AuthRule(allow = AuthStrategy.PRIVATE, operations = { ModelOperation.CREATE, ModelOperation.UPDATE, ModelOperation.DELETE, ModelOperation.READ })
})
@Index(name = "byChallenge", fields = {"challengeID"})
public final class TelloFlight implements Model {
  public static final QueryField ID = field("TelloFlight", "id");
  public static final QueryField STARTED_MS = field("TelloFlight", "started_ms");
  public static final QueryField LENGTH_MS = field("TelloFlight", "length_ms");
  public static final QueryField CHALLENGE_ID = field("TelloFlight", "challengeID");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="AWSTimestamp", isRequired = true) Temporal.Timestamp started_ms;
  private final @ModelField(targetType="Int") Integer length_ms;
  private final @ModelField(targetType="TelloFlightData") @HasMany(associatedWith = "telloflightID", type = TelloFlightData.class) List<TelloFlightData> TelloFlightTelloFlightData = null;
  private final @ModelField(targetType="ID", isRequired = true) String challengeID;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime createdAt;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime updatedAt;
  public String getId() {
      return id;
  }
  
  public Temporal.Timestamp getStartedMs() {
      return started_ms;
  }
  
  public Integer getLengthMs() {
      return length_ms;
  }
  
  public List<TelloFlightData> getTelloFlightTelloFlightData() {
      return TelloFlightTelloFlightData;
  }
  
  public String getChallengeId() {
      return challengeID;
  }
  
  public Temporal.DateTime getCreatedAt() {
      return createdAt;
  }
  
  public Temporal.DateTime getUpdatedAt() {
      return updatedAt;
  }
  
  private TelloFlight(String id, Temporal.Timestamp started_ms, Integer length_ms, String challengeID) {
    this.id = id;
    this.started_ms = started_ms;
    this.length_ms = length_ms;
    this.challengeID = challengeID;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      TelloFlight telloFlight = (TelloFlight) obj;
      return ObjectsCompat.equals(getId(), telloFlight.getId()) &&
              ObjectsCompat.equals(getStartedMs(), telloFlight.getStartedMs()) &&
              ObjectsCompat.equals(getLengthMs(), telloFlight.getLengthMs()) &&
              ObjectsCompat.equals(getChallengeId(), telloFlight.getChallengeId()) &&
              ObjectsCompat.equals(getCreatedAt(), telloFlight.getCreatedAt()) &&
              ObjectsCompat.equals(getUpdatedAt(), telloFlight.getUpdatedAt());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getStartedMs())
      .append(getLengthMs())
      .append(getChallengeId())
      .append(getCreatedAt())
      .append(getUpdatedAt())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("TelloFlight {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("started_ms=" + String.valueOf(getStartedMs()) + ", ")
      .append("length_ms=" + String.valueOf(getLengthMs()) + ", ")
      .append("challengeID=" + String.valueOf(getChallengeId()) + ", ")
      .append("createdAt=" + String.valueOf(getCreatedAt()) + ", ")
      .append("updatedAt=" + String.valueOf(getUpdatedAt()))
      .append("}")
      .toString();
  }
  
  public static StartedMsStep builder() {
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
  public static TelloFlight justId(String id) {
    return new TelloFlight(
      id,
      null,
      null,
      null
    );
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(id,
      started_ms,
      length_ms,
      challengeID);
  }
  public interface StartedMsStep {
    ChallengeIdStep startedMs(Temporal.Timestamp startedMs);
  }
  

  public interface ChallengeIdStep {
    BuildStep challengeId(String challengeId);
  }
  

  public interface BuildStep {
    TelloFlight build();
    BuildStep id(String id);
    BuildStep lengthMs(Integer lengthMs);
  }
  

  public static class Builder implements StartedMsStep, ChallengeIdStep, BuildStep {
    private String id;
    private Temporal.Timestamp started_ms;
    private String challengeID;
    private Integer length_ms;
    @Override
     public TelloFlight build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new TelloFlight(
          id,
          started_ms,
          length_ms,
          challengeID);
    }
    
    @Override
     public ChallengeIdStep startedMs(Temporal.Timestamp startedMs) {
        Objects.requireNonNull(startedMs);
        this.started_ms = startedMs;
        return this;
    }
    
    @Override
     public BuildStep challengeId(String challengeId) {
        Objects.requireNonNull(challengeId);
        this.challengeID = challengeId;
        return this;
    }
    
    @Override
     public BuildStep lengthMs(Integer lengthMs) {
        this.length_ms = lengthMs;
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
    private CopyOfBuilder(String id, Temporal.Timestamp startedMs, Integer lengthMs, String challengeId) {
      super.id(id);
      super.startedMs(startedMs)
        .challengeId(challengeId)
        .lengthMs(lengthMs);
    }
    
    @Override
     public CopyOfBuilder startedMs(Temporal.Timestamp startedMs) {
      return (CopyOfBuilder) super.startedMs(startedMs);
    }
    
    @Override
     public CopyOfBuilder challengeId(String challengeId) {
      return (CopyOfBuilder) super.challengeId(challengeId);
    }
    
    @Override
     public CopyOfBuilder lengthMs(Integer lengthMs) {
      return (CopyOfBuilder) super.lengthMs(lengthMs);
    }
  }
  
}
