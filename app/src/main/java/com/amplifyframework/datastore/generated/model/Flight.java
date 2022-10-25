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

/** This is an auto generated class representing the Flight type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "Flights", authRules = {
  @AuthRule(allow = AuthStrategy.PUBLIC, operations = { ModelOperation.CREATE, ModelOperation.UPDATE, ModelOperation.DELETE, ModelOperation.READ })
})
public final class Flight implements Model {
  public static final QueryField ID = field("Flight", "id");
  public static final QueryField FLIGHT_LENGTH = field("Flight", "flight_length");
  public static final QueryField PILOT_USERNAME = field("Flight", "pilot_username");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="Int", isRequired = true) Integer flight_length;
  private final @ModelField(targetType="String") String pilot_username;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime createdAt;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime updatedAt;
  public String getId() {
      return id;
  }
  
  public Integer getFlightLength() {
      return flight_length;
  }
  
  public String getPilotUsername() {
      return pilot_username;
  }
  
  public Temporal.DateTime getCreatedAt() {
      return createdAt;
  }
  
  public Temporal.DateTime getUpdatedAt() {
      return updatedAt;
  }
  
  private Flight(String id, Integer flight_length, String pilot_username) {
    this.id = id;
    this.flight_length = flight_length;
    this.pilot_username = pilot_username;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      Flight flight = (Flight) obj;
      return ObjectsCompat.equals(getId(), flight.getId()) &&
              ObjectsCompat.equals(getFlightLength(), flight.getFlightLength()) &&
              ObjectsCompat.equals(getPilotUsername(), flight.getPilotUsername()) &&
              ObjectsCompat.equals(getCreatedAt(), flight.getCreatedAt()) &&
              ObjectsCompat.equals(getUpdatedAt(), flight.getUpdatedAt());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getFlightLength())
      .append(getPilotUsername())
      .append(getCreatedAt())
      .append(getUpdatedAt())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("Flight {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("flight_length=" + String.valueOf(getFlightLength()) + ", ")
      .append("pilot_username=" + String.valueOf(getPilotUsername()) + ", ")
      .append("createdAt=" + String.valueOf(getCreatedAt()) + ", ")
      .append("updatedAt=" + String.valueOf(getUpdatedAt()))
      .append("}")
      .toString();
  }
  
  public static FlightLengthStep builder() {
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
  public static Flight justId(String id) {
    return new Flight(
      id,
      null,
      null
    );
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(id,
      flight_length,
      pilot_username);
  }
  public interface FlightLengthStep {
    BuildStep flightLength(Integer flightLength);
  }
  

  public interface BuildStep {
    Flight build();
    BuildStep id(String id);
    BuildStep pilotUsername(String pilotUsername);
  }
  

  public static class Builder implements FlightLengthStep, BuildStep {
    private String id;
    private Integer flight_length;
    private String pilot_username;
    @Override
     public Flight build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new Flight(
          id,
          flight_length,
          pilot_username);
    }
    
    @Override
     public BuildStep flightLength(Integer flightLength) {
        Objects.requireNonNull(flightLength);
        this.flight_length = flightLength;
        return this;
    }
    
    @Override
     public BuildStep pilotUsername(String pilotUsername) {
        this.pilot_username = pilotUsername;
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
    private CopyOfBuilder(String id, Integer flightLength, String pilotUsername) {
      super.id(id);
      super.flightLength(flightLength)
        .pilotUsername(pilotUsername);
    }
    
    @Override
     public CopyOfBuilder flightLength(Integer flightLength) {
      return (CopyOfBuilder) super.flightLength(flightLength);
    }
    
    @Override
     public CopyOfBuilder pilotUsername(String pilotUsername) {
      return (CopyOfBuilder) super.pilotUsername(pilotUsername);
    }
  }
  
}
