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
  public static final QueryField MPRY = field("TelloFlightData", "mpry");
  public static final QueryField PITCH = field("TelloFlightData", "pitch");
  public static final QueryField ROLL = field("TelloFlightData", "roll");
  public static final QueryField YAW = field("TelloFlightData", "yaw");
  public static final QueryField VGX = field("TelloFlightData", "vgx");
  public static final QueryField VGY = field("TelloFlightData", "vgy");
  public static final QueryField VGZ = field("TelloFlightData", "vgz");
  public static final QueryField TEMPL = field("TelloFlightData", "templ");
  public static final QueryField TEMPH = field("TelloFlightData", "temph");
  public static final QueryField TOF = field("TelloFlightData", "tof");
  public static final QueryField H = field("TelloFlightData", "h");
  public static final QueryField BAT = field("TelloFlightData", "bat");
  public static final QueryField BARO = field("TelloFlightData", "baro");
  public static final QueryField TIME = field("TelloFlightData", "time");
  public static final QueryField AGX = field("TelloFlightData", "agx");
  public static final QueryField AGY = field("TelloFlightData", "agy");
  public static final QueryField AGZ = field("TelloFlightData", "agz");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="Int") Integer received_at_ms;
  private final @ModelField(targetType="ID", isRequired = true) String telloflightID;
  private final @ModelField(targetType="Int") Integer mpry;
  private final @ModelField(targetType="Int") Integer pitch;
  private final @ModelField(targetType="Int") Integer roll;
  private final @ModelField(targetType="Int") Integer yaw;
  private final @ModelField(targetType="Int") Integer vgx;
  private final @ModelField(targetType="Int") Integer vgy;
  private final @ModelField(targetType="Int") Integer vgz;
  private final @ModelField(targetType="Int") Integer templ;
  private final @ModelField(targetType="Int") Integer temph;
  private final @ModelField(targetType="Int") Integer tof;
  private final @ModelField(targetType="Int") Integer h;
  private final @ModelField(targetType="Int") Integer bat;
  private final @ModelField(targetType="Float") Double baro;
  private final @ModelField(targetType="Int") Integer time;
  private final @ModelField(targetType="Int") Integer agx;
  private final @ModelField(targetType="Int") Integer agy;
  private final @ModelField(targetType="Int") Integer agz;
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
  
  public Integer getMpry() {
      return mpry;
  }
  
  public Integer getPitch() {
      return pitch;
  }
  
  public Integer getRoll() {
      return roll;
  }
  
  public Integer getYaw() {
      return yaw;
  }
  
  public Integer getVgx() {
      return vgx;
  }
  
  public Integer getVgy() {
      return vgy;
  }
  
  public Integer getVgz() {
      return vgz;
  }
  
  public Integer getTempl() {
      return templ;
  }
  
  public Integer getTemph() {
      return temph;
  }
  
  public Integer getTof() {
      return tof;
  }
  
  public Integer getH() {
      return h;
  }
  
  public Integer getBat() {
      return bat;
  }
  
  public Double getBaro() {
      return baro;
  }
  
  public Integer getTime() {
      return time;
  }
  
  public Integer getAgx() {
      return agx;
  }
  
  public Integer getAgy() {
      return agy;
  }
  
  public Integer getAgz() {
      return agz;
  }
  
  public Temporal.DateTime getCreatedAt() {
      return createdAt;
  }
  
  public Temporal.DateTime getUpdatedAt() {
      return updatedAt;
  }
  
  private TelloFlightData(String id, Integer received_at_ms, String telloflightID, Integer mpry, Integer pitch, Integer roll, Integer yaw, Integer vgx, Integer vgy, Integer vgz, Integer templ, Integer temph, Integer tof, Integer h, Integer bat, Double baro, Integer time, Integer agx, Integer agy, Integer agz) {
    this.id = id;
    this.received_at_ms = received_at_ms;
    this.telloflightID = telloflightID;
    this.mpry = mpry;
    this.pitch = pitch;
    this.roll = roll;
    this.yaw = yaw;
    this.vgx = vgx;
    this.vgy = vgy;
    this.vgz = vgz;
    this.templ = templ;
    this.temph = temph;
    this.tof = tof;
    this.h = h;
    this.bat = bat;
    this.baro = baro;
    this.time = time;
    this.agx = agx;
    this.agy = agy;
    this.agz = agz;
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
              ObjectsCompat.equals(getMpry(), telloFlightData.getMpry()) &&
              ObjectsCompat.equals(getPitch(), telloFlightData.getPitch()) &&
              ObjectsCompat.equals(getRoll(), telloFlightData.getRoll()) &&
              ObjectsCompat.equals(getYaw(), telloFlightData.getYaw()) &&
              ObjectsCompat.equals(getVgx(), telloFlightData.getVgx()) &&
              ObjectsCompat.equals(getVgy(), telloFlightData.getVgy()) &&
              ObjectsCompat.equals(getVgz(), telloFlightData.getVgz()) &&
              ObjectsCompat.equals(getTempl(), telloFlightData.getTempl()) &&
              ObjectsCompat.equals(getTemph(), telloFlightData.getTemph()) &&
              ObjectsCompat.equals(getTof(), telloFlightData.getTof()) &&
              ObjectsCompat.equals(getH(), telloFlightData.getH()) &&
              ObjectsCompat.equals(getBat(), telloFlightData.getBat()) &&
              ObjectsCompat.equals(getBaro(), telloFlightData.getBaro()) &&
              ObjectsCompat.equals(getTime(), telloFlightData.getTime()) &&
              ObjectsCompat.equals(getAgx(), telloFlightData.getAgx()) &&
              ObjectsCompat.equals(getAgy(), telloFlightData.getAgy()) &&
              ObjectsCompat.equals(getAgz(), telloFlightData.getAgz()) &&
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
      .append(getMpry())
      .append(getPitch())
      .append(getRoll())
      .append(getYaw())
      .append(getVgx())
      .append(getVgy())
      .append(getVgz())
      .append(getTempl())
      .append(getTemph())
      .append(getTof())
      .append(getH())
      .append(getBat())
      .append(getBaro())
      .append(getTime())
      .append(getAgx())
      .append(getAgy())
      .append(getAgz())
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
      .append("mpry=" + String.valueOf(getMpry()) + ", ")
      .append("pitch=" + String.valueOf(getPitch()) + ", ")
      .append("roll=" + String.valueOf(getRoll()) + ", ")
      .append("yaw=" + String.valueOf(getYaw()) + ", ")
      .append("vgx=" + String.valueOf(getVgx()) + ", ")
      .append("vgy=" + String.valueOf(getVgy()) + ", ")
      .append("vgz=" + String.valueOf(getVgz()) + ", ")
      .append("templ=" + String.valueOf(getTempl()) + ", ")
      .append("temph=" + String.valueOf(getTemph()) + ", ")
      .append("tof=" + String.valueOf(getTof()) + ", ")
      .append("h=" + String.valueOf(getH()) + ", ")
      .append("bat=" + String.valueOf(getBat()) + ", ")
      .append("baro=" + String.valueOf(getBaro()) + ", ")
      .append("time=" + String.valueOf(getTime()) + ", ")
      .append("agx=" + String.valueOf(getAgx()) + ", ")
      .append("agy=" + String.valueOf(getAgy()) + ", ")
      .append("agz=" + String.valueOf(getAgz()) + ", ")
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
      null,
      null,
      null,
      null,
      null,
      null,
      null,
      null,
      null,
      null,
      null,
      null,
      null,
      null,
      null,
      null,
      null,
      null
    );
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(id,
      received_at_ms,
      telloflightID,
      mpry,
      pitch,
      roll,
      yaw,
      vgx,
      vgy,
      vgz,
      templ,
      temph,
      tof,
      h,
      bat,
      baro,
      time,
      agx,
      agy,
      agz);
  }
  public interface TelloflightIdStep {
    BuildStep telloflightId(String telloflightId);
  }
  

  public interface BuildStep {
    TelloFlightData build();
    BuildStep id(String id);
    BuildStep receivedAtMs(Integer receivedAtMs);
    BuildStep mpry(Integer mpry);
    BuildStep pitch(Integer pitch);
    BuildStep roll(Integer roll);
    BuildStep yaw(Integer yaw);
    BuildStep vgx(Integer vgx);
    BuildStep vgy(Integer vgy);
    BuildStep vgz(Integer vgz);
    BuildStep templ(Integer templ);
    BuildStep temph(Integer temph);
    BuildStep tof(Integer tof);
    BuildStep h(Integer h);
    BuildStep bat(Integer bat);
    BuildStep baro(Double baro);
    BuildStep time(Integer time);
    BuildStep agx(Integer agx);
    BuildStep agy(Integer agy);
    BuildStep agz(Integer agz);
  }
  

  public static class Builder implements TelloflightIdStep, BuildStep {
    private String id;
    private String telloflightID;
    private Integer received_at_ms;
    private Integer mpry;
    private Integer pitch;
    private Integer roll;
    private Integer yaw;
    private Integer vgx;
    private Integer vgy;
    private Integer vgz;
    private Integer templ;
    private Integer temph;
    private Integer tof;
    private Integer h;
    private Integer bat;
    private Double baro;
    private Integer time;
    private Integer agx;
    private Integer agy;
    private Integer agz;
    @Override
     public TelloFlightData build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new TelloFlightData(
          id,
          received_at_ms,
          telloflightID,
          mpry,
          pitch,
          roll,
          yaw,
          vgx,
          vgy,
          vgz,
          templ,
          temph,
          tof,
          h,
          bat,
          baro,
          time,
          agx,
          agy,
          agz);
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
    
    @Override
     public BuildStep mpry(Integer mpry) {
        this.mpry = mpry;
        return this;
    }
    
    @Override
     public BuildStep pitch(Integer pitch) {
        this.pitch = pitch;
        return this;
    }
    
    @Override
     public BuildStep roll(Integer roll) {
        this.roll = roll;
        return this;
    }
    
    @Override
     public BuildStep yaw(Integer yaw) {
        this.yaw = yaw;
        return this;
    }
    
    @Override
     public BuildStep vgx(Integer vgx) {
        this.vgx = vgx;
        return this;
    }
    
    @Override
     public BuildStep vgy(Integer vgy) {
        this.vgy = vgy;
        return this;
    }
    
    @Override
     public BuildStep vgz(Integer vgz) {
        this.vgz = vgz;
        return this;
    }
    
    @Override
     public BuildStep templ(Integer templ) {
        this.templ = templ;
        return this;
    }
    
    @Override
     public BuildStep temph(Integer temph) {
        this.temph = temph;
        return this;
    }
    
    @Override
     public BuildStep tof(Integer tof) {
        this.tof = tof;
        return this;
    }
    
    @Override
     public BuildStep h(Integer h) {
        this.h = h;
        return this;
    }
    
    @Override
     public BuildStep bat(Integer bat) {
        this.bat = bat;
        return this;
    }
    
    @Override
     public BuildStep baro(Double baro) {
        this.baro = baro;
        return this;
    }
    
    @Override
     public BuildStep time(Integer time) {
        this.time = time;
        return this;
    }
    
    @Override
     public BuildStep agx(Integer agx) {
        this.agx = agx;
        return this;
    }
    
    @Override
     public BuildStep agy(Integer agy) {
        this.agy = agy;
        return this;
    }
    
    @Override
     public BuildStep agz(Integer agz) {
        this.agz = agz;
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
    private CopyOfBuilder(String id, Integer receivedAtMs, String telloflightId, Integer mpry, Integer pitch, Integer roll, Integer yaw, Integer vgx, Integer vgy, Integer vgz, Integer templ, Integer temph, Integer tof, Integer h, Integer bat, Double baro, Integer time, Integer agx, Integer agy, Integer agz) {
      super.id(id);
      super.telloflightId(telloflightId)
        .receivedAtMs(receivedAtMs)
        .mpry(mpry)
        .pitch(pitch)
        .roll(roll)
        .yaw(yaw)
        .vgx(vgx)
        .vgy(vgy)
        .vgz(vgz)
        .templ(templ)
        .temph(temph)
        .tof(tof)
        .h(h)
        .bat(bat)
        .baro(baro)
        .time(time)
        .agx(agx)
        .agy(agy)
        .agz(agz);
    }
    
    @Override
     public CopyOfBuilder telloflightId(String telloflightId) {
      return (CopyOfBuilder) super.telloflightId(telloflightId);
    }
    
    @Override
     public CopyOfBuilder receivedAtMs(Integer receivedAtMs) {
      return (CopyOfBuilder) super.receivedAtMs(receivedAtMs);
    }
    
    @Override
     public CopyOfBuilder mpry(Integer mpry) {
      return (CopyOfBuilder) super.mpry(mpry);
    }
    
    @Override
     public CopyOfBuilder pitch(Integer pitch) {
      return (CopyOfBuilder) super.pitch(pitch);
    }
    
    @Override
     public CopyOfBuilder roll(Integer roll) {
      return (CopyOfBuilder) super.roll(roll);
    }
    
    @Override
     public CopyOfBuilder yaw(Integer yaw) {
      return (CopyOfBuilder) super.yaw(yaw);
    }
    
    @Override
     public CopyOfBuilder vgx(Integer vgx) {
      return (CopyOfBuilder) super.vgx(vgx);
    }
    
    @Override
     public CopyOfBuilder vgy(Integer vgy) {
      return (CopyOfBuilder) super.vgy(vgy);
    }
    
    @Override
     public CopyOfBuilder vgz(Integer vgz) {
      return (CopyOfBuilder) super.vgz(vgz);
    }
    
    @Override
     public CopyOfBuilder templ(Integer templ) {
      return (CopyOfBuilder) super.templ(templ);
    }
    
    @Override
     public CopyOfBuilder temph(Integer temph) {
      return (CopyOfBuilder) super.temph(temph);
    }
    
    @Override
     public CopyOfBuilder tof(Integer tof) {
      return (CopyOfBuilder) super.tof(tof);
    }
    
    @Override
     public CopyOfBuilder h(Integer h) {
      return (CopyOfBuilder) super.h(h);
    }
    
    @Override
     public CopyOfBuilder bat(Integer bat) {
      return (CopyOfBuilder) super.bat(bat);
    }
    
    @Override
     public CopyOfBuilder baro(Double baro) {
      return (CopyOfBuilder) super.baro(baro);
    }
    
    @Override
     public CopyOfBuilder time(Integer time) {
      return (CopyOfBuilder) super.time(time);
    }
    
    @Override
     public CopyOfBuilder agx(Integer agx) {
      return (CopyOfBuilder) super.agx(agx);
    }
    
    @Override
     public CopyOfBuilder agy(Integer agy) {
      return (CopyOfBuilder) super.agy(agy);
    }
    
    @Override
     public CopyOfBuilder agz(Integer agz) {
      return (CopyOfBuilder) super.agz(agz);
    }
  }
  
}
