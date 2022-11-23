package com.tlopez.datastoreDomain.models;

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
  @AuthRule(allow = AuthStrategy.PRIVATE, operations = { ModelOperation.CREATE, ModelOperation.READ }),
  @AuthRule(allow = AuthStrategy.OWNER, ownerField = "owner", identityClaim = "cognito:username", provider = "userPools", operations = { ModelOperation.CREATE, ModelOperation.UPDATE, ModelOperation.DELETE, ModelOperation.READ })
})
@Index(name = "byChallenge", fields = {"challengeID"})
@Index(name = "byOwnerAndStartedMs", fields = {"owner","startedMs"})
public final class TelloFlight implements Model {
  public static final QueryField ID = field("TelloFlight", "id");
  public static final QueryField STARTED_MS = field("TelloFlight", "startedMs");
  public static final QueryField LENGTH_MS = field("TelloFlight", "lengthMs");
  public static final QueryField CHALLENGE_ID = field("TelloFlight", "challengeID");
  public static final QueryField OWNER = field("TelloFlight", "owner");
  public static final QueryField SUCCESSFUL_LAND = field("TelloFlight", "successfulLand");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="AWSTimestamp", isRequired = true) Temporal.Timestamp startedMs;
  private final @ModelField(targetType="Int") Integer lengthMs;
  private final @ModelField(targetType="TelloFlightData") @HasMany(associatedWith = "telloflightID", type = TelloFlightData.class) List<TelloFlightData> TelloFlightTelloFlightData = null;
  private final @ModelField(targetType="ID", isRequired = true) String challengeID;
  private final @ModelField(targetType="String", isRequired = true) String owner;
  private final @ModelField(targetType="Boolean") Boolean successfulLand;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime createdAt;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime updatedAt;
  public String getId() {
      return id;
  }
  
  public Temporal.Timestamp getStartedMs() {
      return startedMs;
  }
  
  public Integer getLengthMs() {
      return lengthMs;
  }
  
  public List<TelloFlightData> getTelloFlightTelloFlightData() {
      return TelloFlightTelloFlightData;
  }
  
  public String getChallengeId() {
      return challengeID;
  }
  
  public String getOwner() {
      return owner;
  }
  
  public Boolean getSuccessfulLand() {
      return successfulLand;
  }
  
  public Temporal.DateTime getCreatedAt() {
      return createdAt;
  }
  
  public Temporal.DateTime getUpdatedAt() {
      return updatedAt;
  }
  
  private TelloFlight(String id, Temporal.Timestamp startedMs, Integer lengthMs, String challengeID, String owner, Boolean successfulLand) {
    this.id = id;
    this.startedMs = startedMs;
    this.lengthMs = lengthMs;
    this.challengeID = challengeID;
    this.owner = owner;
    this.successfulLand = successfulLand;
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
              ObjectsCompat.equals(getOwner(), telloFlight.getOwner()) &&
              ObjectsCompat.equals(getSuccessfulLand(), telloFlight.getSuccessfulLand()) &&
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
      .append(getOwner())
      .append(getSuccessfulLand())
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
      .append("startedMs=" + String.valueOf(getStartedMs()) + ", ")
      .append("lengthMs=" + String.valueOf(getLengthMs()) + ", ")
      .append("challengeID=" + String.valueOf(getChallengeId()) + ", ")
      .append("owner=" + String.valueOf(getOwner()) + ", ")
      .append("successfulLand=" + String.valueOf(getSuccessfulLand()) + ", ")
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
      null,
      null,
      null
    );
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(id,
      startedMs,
      lengthMs,
      challengeID,
      owner,
      successfulLand);
  }
  public interface StartedMsStep {
    ChallengeIdStep startedMs(Temporal.Timestamp startedMs);
  }
  

  public interface ChallengeIdStep {
    OwnerStep challengeId(String challengeId);
  }
  

  public interface OwnerStep {
    BuildStep owner(String owner);
  }
  

  public interface BuildStep {
    TelloFlight build();
    BuildStep id(String id);
    BuildStep lengthMs(Integer lengthMs);
    BuildStep successfulLand(Boolean successfulLand);
  }
  

  public static class Builder implements StartedMsStep, ChallengeIdStep, OwnerStep, BuildStep {
    private String id;
    private Temporal.Timestamp startedMs;
    private String challengeID;
    private String owner;
    private Integer lengthMs;
    private Boolean successfulLand;
    @Override
     public TelloFlight build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new TelloFlight(
          id,
          startedMs,
          lengthMs,
          challengeID,
          owner,
          successfulLand);
    }
    
    @Override
     public ChallengeIdStep startedMs(Temporal.Timestamp startedMs) {
        Objects.requireNonNull(startedMs);
        this.startedMs = startedMs;
        return this;
    }
    
    @Override
     public OwnerStep challengeId(String challengeId) {
        Objects.requireNonNull(challengeId);
        this.challengeID = challengeId;
        return this;
    }
    
    @Override
     public BuildStep owner(String owner) {
        Objects.requireNonNull(owner);
        this.owner = owner;
        return this;
    }
    
    @Override
     public BuildStep lengthMs(Integer lengthMs) {
        this.lengthMs = lengthMs;
        return this;
    }
    
    @Override
     public BuildStep successfulLand(Boolean successfulLand) {
        this.successfulLand = successfulLand;
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
    private CopyOfBuilder(String id, Temporal.Timestamp startedMs, Integer lengthMs, String challengeId, String owner, Boolean successfulLand) {
      super.id(id);
      super.startedMs(startedMs)
        .challengeId(challengeId)
        .owner(owner)
        .lengthMs(lengthMs)
        .successfulLand(successfulLand);
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
     public CopyOfBuilder owner(String owner) {
      return (CopyOfBuilder) super.owner(owner);
    }
    
    @Override
     public CopyOfBuilder lengthMs(Integer lengthMs) {
      return (CopyOfBuilder) super.lengthMs(lengthMs);
    }
    
    @Override
     public CopyOfBuilder successfulLand(Boolean successfulLand) {
      return (CopyOfBuilder) super.successfulLand(successfulLand);
    }
  }
  
}
