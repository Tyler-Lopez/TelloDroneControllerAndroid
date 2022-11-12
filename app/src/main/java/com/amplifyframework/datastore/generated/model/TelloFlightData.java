package com.amplifyframework.datastore.generated.model;

import com.amplifyframework.core.model.temporal.Temporal;

import java.util.List;
import java.util.UUID;
import java.util.Objects;

import androidx.core.util.ObjectsCompat;

import com.amplifyframework.core.model.AuthStrategy;
import com.amplifyframework.core.model.Model;
import com.amplifyframework.core.model.ModelOperation;
import com.amplifyframework.core.model.annotations.AuthRule;
import com.amplifyframework.core.model.annotations.Index;
import com.amplifyframework.core.model.annotations.ModelConfig;
import com.amplifyframework.core.model.annotations.ModelField;
import com.amplifyframework.core.model.query.predicate.QueryField;

import static com.amplifyframework.core.model.query.predicate.QueryField.field;

/** This is an auto generated class representing the TelloFlightData type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "TelloFlightData", authRules = {
  @AuthRule(allow = AuthStrategy.PRIVATE, operations = { ModelOperation.CREATE, ModelOperation.UPDATE, ModelOperation.DELETE, ModelOperation.READ })
})
@Index(name = "byTelloFlight", fields = {"telloflightID"})
public final class TelloFlightData implements Model {
  public static final QueryField ID = field("TelloFlightData", "id");
  public static final QueryField RECEIVED_AT_MS = field("TelloFlightData", "received_at_ms");
  public static final QueryField TELLOFLIGHT_ID = field("TelloFlightData", "telloflightID");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="Int") Integer received_at_ms;
  private final @ModelField(targetType="ID", isRequired = true) String telloflightID;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime createdAt;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime updatedAt;
  public String getId() {
      return id;
  }
  
  public Integer getReceivedAtMs() {
      return received_at_ms;
  }
  
  public String getTelloflightId() {
      return telloflightID;
  }
  
  public Temporal.DateTime getCreatedAt() {
      return createdAt;
  }
  
  public Temporal.DateTime getUpdatedAt() {
      return updatedAt;
  }
  
  private TelloFlightData(String id, Integer received_at_ms, String telloflightID) {
    this.id = id;
    this.received_at_ms = received_at_ms;
    this.telloflightID = telloflightID;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      TelloFlightData telloFlightData = (TelloFlightData) obj;
      return ObjectsCompat.equals(getId(), telloFlightData.getId()) &&
              ObjectsCompat.equals(getReceivedAtMs(), telloFlightData.getReceivedAtMs()) &&
              ObjectsCompat.equals(getTelloflightId(), telloFlightData.getTelloflightId()) &&
              ObjectsCompat.equals(getCreatedAt(), telloFlightData.getCreatedAt()) &&
              ObjectsCompat.equals(getUpdatedAt(), telloFlightData.getUpdatedAt());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getReceivedAtMs())
      .append(getTelloflightId())
      .append(getCreatedAt())
      .append(getUpdatedAt())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("TelloFlightData {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("received_at_ms=" + String.valueOf(getReceivedAtMs()) + ", ")
      .append("telloflightID=" + String.valueOf(getTelloflightId()) + ", ")
      .append("createdAt=" + String.valueOf(getCreatedAt()) + ", ")
      .append("updatedAt=" + String.valueOf(getUpdatedAt()))
      .append("}")
      .toString();
  }
  
  public static TelloflightIdStep builder() {
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
  public static TelloFlightData justId(String id) {
    return new TelloFlightData(
      id,
      null,
      null
    );
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(id,
      received_at_ms,
      telloflightID);
  }
  public interface TelloflightIdStep {
    BuildStep telloflightId(String telloflightId);
  }
  

  public interface BuildStep {
    TelloFlightData build();
    BuildStep id(String id);
    BuildStep receivedAtMs(Integer receivedAtMs);
  }
  

  public static class Builder implements TelloflightIdStep, BuildStep {
    private String id;
    private String telloflightID;
    private Integer received_at_ms;
    @Override
     public TelloFlightData build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new TelloFlightData(
          id,
          received_at_ms,
          telloflightID);
    }
    
    @Override
     public BuildStep telloflightId(String telloflightId) {
        Objects.requireNonNull(telloflightId);
        this.telloflightID = telloflightId;
        return this;
    }
    
    @Override
     public BuildStep receivedAtMs(Integer receivedAtMs) {
        this.received_at_ms = receivedAtMs;
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
    private CopyOfBuilder(String id, Integer receivedAtMs, String telloflightId) {
      super.id(id);
      super.telloflightId(telloflightId)
        .receivedAtMs(receivedAtMs);
    }
    
    @Override
     public CopyOfBuilder telloflightId(String telloflightId) {
      return (CopyOfBuilder) super.telloflightId(telloflightId);
    }
    
    @Override
     public CopyOfBuilder receivedAtMs(Integer receivedAtMs) {
      return (CopyOfBuilder) super.receivedAtMs(receivedAtMs);
    }
  }
  
}
