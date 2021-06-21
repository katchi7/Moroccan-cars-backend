package com.ensias.moroccan_cars.Dto;

import com.ensias.moroccan_cars.models.RentRequest;
import com.ensias.moroccan_cars.models.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

@Data
@Log4j2
@AllArgsConstructor
@NoArgsConstructor
public class RentRequestDto {


    public RentRequestDto(RentRequest rentRequest){
        this(rentRequest.getId(),rentRequest.getDate(),rentRequest.getDateStart(),rentRequest.getDateEnd(),new UserDto(rentRequest.getUser()), rentRequest.getVehicule().getId(),new VehiculeDto(rentRequest.getVehicule()),rentRequest.getStatus());
    }

    private int id;
    private Date date;

    @NotNull
    private Date dateStart;
    @NotNull
    private Date dateEnd;

    private UserDto user;

    @Min(1)
    private int vehicule_id;

    private VehiculeDto vehicule;

    private Status status;



    public RentRequest asRentRequest(){
        VehiculeDto vehicule = new VehiculeDto();
        vehicule.setId(vehicule_id);
        return new RentRequest(id,date,dateStart,dateEnd,user.User(),vehicule.asVehicule(),status);
    }

    public void setDateStart(String date) throws ParseException {
        DateTimeFormatter df = DateTimeFormat.forPattern("yyyy-MM-dd").withZone(DateTimeZone.forTimeZone(TimeZone.getTimeZone("GMT")));
        long millis = df.parseMillis(date);
        this.dateStart = new Date(millis);

    }

    public void setDateEnd(String dateEnd) throws ParseException {
        DateTimeFormatter df = DateTimeFormat.forPattern("yyyy-MM-dd").withZone(DateTimeZone.forTimeZone(TimeZone.getTimeZone("GMT")));
        long millis = df.parseMillis(dateEnd);
        this.dateEnd = new Date(millis);
    }

    public String getDate() {
        SimpleDateFormat df =new SimpleDateFormat("dd/MM/yyyy : hh:mm");
        return df.format(date);
    }

    public String getDateStart() {
        SimpleDateFormat df =new SimpleDateFormat("dd/MM/yyyy");
        return df.format(dateStart);
    }

    public String getDateEnd() {
        SimpleDateFormat df =new SimpleDateFormat("dd/MM/yyyy");
        return df.format(dateEnd);
    }
}
